package com.mtf.kibs.service.impl;

import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.mapper.KibsMapper;
import com.mtf.kibs.service.CalculationService;
import com.mtf.kibs.service.KibsService;
import com.mtf.kibs.util.AES256Util;
import com.mtf.kibs.util.StringUtil;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class KibsServiceImpl implements KibsService {

    private static final String STR_RESULT_H = "%s - %s";

    @Autowired
    private CalculationService calculationService; // 1. 공통 계산 서비스 주입

    @Setter(onMethod_ = {@Autowired})
    private KibsMapper kibsMapper;

    @Autowired
    private AES256Util aes256Util;

    @Value("${security.login.account-max-fail:10}")
    private int accountMaxFail;

    @Value("${security.login.account-lock-minutes:10}")
    private int accountLockMinutes;

    @Value("${security.login.ip-check-minutes:10}")
    private int ipCheckMinutes;

    @Value("${security.login.ip-max-fail:30}")
    private int ipMaxFail;

    @Value("${security.login.ip-lock-minutes:30}")
    private int ipLockMinutes;

    private static final Logger log = LoggerFactory.getLogger(KibsServiceImpl.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Integer checkDuplicateId(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > checkDuplicateId : ======");
        /*KibsMapper dm = sqlSession.getMapper(KibsMapper.class);
        return dm.checkDuplicateId(id);*/
        return kibsMapper.checkDuplicateId(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public InstaTokenDTO processSelectInstaRefresh() {
        //System.out.println("KibsServiceImpl > processSelectInstaRefresh");
        return kibsMapper.selectInstaRefresh();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateInstaRefresh(InstaTokenDTO instaTokenDTO) {
        //System.out.println("KibsServiceImpl > processUpdateInstaRefresh : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer result = kibsMapper.updateInstaRefresh(instaTokenDTO);
            if (result == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[ERROR] processUpdateInstaRefresh Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public Map<String, Object> loginWithFailCheck(ExhibitorNewDTO exhibitorNewDTO, HttpSession session, String clientIp) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", clientIp);
        paramMap.put("userId", exhibitorNewDTO.getId());
        paramMap.put("ipCheckMinutes", ipCheckMinutes);
        paramMap.put("ipLockMinutes", ipLockMinutes);

        // 유저 전용 로그인 실패 로그 테이블이 필요 (ex: user_login_fail_log)
        // 아래 쿼리들은 KibsMapper에 추가해 주셔야 합니다.
        Map<String, Object> failLog = kibsMapper.selectUserLoginFailLog(paramMap);

        // ==============================================================
        // [1차 방어] 단일 IP 기준 다중 계정 공격 방어
        // ==============================================================
        int ipFailCount = kibsMapper.selectUserRecentFailCountByIp(paramMap);

        if (ipFailCount >= ipMaxFail) {
            int ipBlockCount = kibsMapper.selectUserIpBlockCheck(paramMap);
            if (ipBlockCount > 0) {
                // [로그 기록] IP 차단 이벤트
                log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: IP_BLOCKED, IP누적실패: {}, 차단시간: {}분",
                        exhibitorNewDTO.getId(), clientIp, ipFailCount, ipLockMinutes);

                resultMap.put("resultCode", "LIMIT");
                resultMap.put("resultMsg", "비정상적인 로그인 시도가 감지되어 접근이 일시적으로 제한되었습니다. 잠시 후 다시 시도해 주세요.");
                return resultMap;
            }
        }

        // ==============================================================
        // [2차 방어] 특정 계정 연속 실패 제한
        // ==============================================================
        if (failLog != null) {
            String blockYn = (String) failLog.get("block_yn");
            int failCnt = ((Number) failLog.get("fail_cnt")).intValue();

            // [영구 차단(100회 이상)] 확인
            if ("Y".equals(blockYn) || failCnt >= 100) {
                log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_PERMA_BLOCKED, 계정누적실패: {}", exhibitorNewDTO.getId(), clientIp, failCnt);
                resultMap.put("resultCode", "BLOCKED");
                resultMap.put("resultMsg", "보안 정책에 의해 접근이 차단되었습니다. 관리자에게 문의해 주세요.");
                return resultMap;
            }

            // [임시 제한] 확인
            if (failCnt >= accountMaxFail) {
                Object dttmObj = failLog.get("last_fail_dttm");
                if (dttmObj != null) {

                    // 에러 방지: DB 타입(Date, Timestamp, LocalDateTime)에 관계없이 안전하게 밀리초 추출
                    long lastFailTimeMs = 0;
                    if (dttmObj instanceof java.sql.Timestamp) {
                        lastFailTimeMs = ((java.sql.Timestamp) dttmObj).getTime();
                    } else if (dttmObj instanceof java.time.LocalDateTime) {
                        lastFailTimeMs = java.sql.Timestamp.valueOf((java.time.LocalDateTime) dttmObj).getTime();
                    } else if (dttmObj instanceof java.util.Date) {
                        lastFailTimeMs = ((java.util.Date) dttmObj).getTime();
                    }

                    // 경과 시간(분) 계산
                    long diffMin = (System.currentTimeMillis() - lastFailTimeMs) / 60000;

                    if (diffMin < accountLockMinutes) {
                        log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_LOCKED, 계정누적실패: {}, 잔여차단시간: {}분", exhibitorNewDTO.getId(), clientIp, failCnt, (accountLockMinutes - diffMin));
                        resultMap.put("resultCode", "LIMIT");
                        resultMap.put("resultMsg", "로그인 실패 횟수를 초과하여 계정이 일시적으로 보호 조치 되었습니다. 잠시 후 다시 시도해 주세요.");
                        return resultMap;
                    } else {
                        // 10분이 경과했으므로 패스워드 검증 전 실패 횟수를 미리 0으로 초기화
                        kibsMapper.resetUserLoginFailLog(paramMap);
                        log.info("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_UNLOCK, 메시지: 차단 시간 경과로 카운트 초기화", exhibitorNewDTO.getId(), clientIp);
                    }
                }
            }
        }

        // ==============================================================
        // 3. 실제 ID/PW 검증
        // ==============================================================
        String getSalt = kibsMapper.getExhibitorNewSalt(exhibitorNewDTO);

        // 아이디가 존재하지 않을 경우 Salt값이 null일 수 있으므로 방어 코드 추가
        if (getSalt == null) {
            kibsMapper.upsertUserLoginFailLog(paramMap); // 실패 카운트 증가
            log.info("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: FAIL_MISMATCH", exhibitorNewDTO.getId(), clientIp);
            resultMap.put("resultCode", "FAIL");
            resultMap.put("resultMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return resultMap;
        }

        //암호화
        String pw_encrypt = SHA512(exhibitorNewDTO.getPassword(), getSalt);
        exhibitorNewDTO.setPassword(pw_encrypt);

        String seq = kibsMapper.checkLoginExhibitorNew(exhibitorNewDTO);

        if (seq != null && !seq.isEmpty()) {
            kibsMapper.resetUserLoginFailLog(paramMap);

            // [로그 기록] 로그인 성공
            log.info("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: SUCCESS", exhibitorNewDTO.getId(), clientIp);

            exhibitorNewDTO.setSeq(seq);
            resultMap.put("resultCode", "SUCCESS");
            resultMap.put("customValue", seq);
        } else {
            kibsMapper.upsertUserLoginFailLog(paramMap);

            Map<String, Object> updatedLog = kibsMapper.selectUserLoginFailLog(paramMap);
            int currentFailCnt = updatedLog != null ? ((Number) updatedLog.get("fail_cnt")).intValue() : 1;

            if (currentFailCnt >= 100) {
                kibsMapper.updateUserLoginBlock(paramMap);
                log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_LOCKED_100_FAIL, 누적실패: {}", exhibitorNewDTO.getId(), clientIp, currentFailCnt);

                resultMap.put("resultCode", "BLOCKED");
                resultMap.put("resultMsg", "보안 정책에 의해 접근이 차단되었습니다.");
            } else if (currentFailCnt >= accountMaxFail) {
                log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_LOCKED_LIMIT, 누적실패: {}", exhibitorNewDTO.getId(), clientIp, currentFailCnt);

                resultMap.put("resultCode", "LIMIT");
                resultMap.put("resultMsg", "로그인 실패 횟수를 초과하여 계정이 일시적으로 보호 조치 되었습니다. 잠시 후 다시 시도해 주세요.");
            } else {
                // [로그 기록] 단순 비밀번호/아이디 오류
                log.info("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: FAIL_MISMATCH, 누적실패: {}", exhibitorNewDTO.getId(), clientIp, currentFailCnt);

                resultMap.put("resultCode", "FAIL");
                resultMap.put("resultMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            }
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String getExhibitorNewEmail(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > getExhibitorNewEmail : ======");
        String encryptedEmail = kibsMapper.getExhibitorNewEmail(exhibitorNewDTO);
        return safeDecrypt(encryptedEmail);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String getExhibitorNewRepEmail(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > getExhibitorNewRepEmail : ======");
        String encryptedEmail = kibsMapper.getExhibitorNewRepEmail(exhibitorNewDTO);
        return safeDecrypt(encryptedEmail);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<String> preGetExhibitorEmail(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > preGetExhibitorEmail : ======");
        List<String> encryptedList = kibsMapper.preGetExhibitorEmail(exhibitorDTO);
        List<String> decryptedList = new ArrayList<>();

        for (String email : encryptedList) {
            decryptedList.add(safeDecrypt(email)); // 각각 수동 복호화
        }
        return decryptedList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewPasswordInit(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewPasswordInit : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            // 새로 salt값 생성 & 새로운 비밀번호로 Update
            String salt = Salt();
            exhibitorNewDTO.setSalt(salt);

            String initPw = "aa134!@cc";
            String newPw_encrypt = SHA512(initPw, salt);
            exhibitorNewDTO.setPassword(newPw_encrypt);

            Integer result = kibsMapper.updateExhibitorNewPassword(exhibitorNewDTO);
            if (result == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[Find PW] processUpdateExhibitorNewPasswordInit Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public ResponseDTO processLoginExhibit(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processLoginExhibit : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer loginCheck = kibsMapper.checkLoginExhibit(exhibitorDTO);
            if (loginCheck == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "ID not found";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[login] processLoginExhibit Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Override
    public void logoutCheck(HttpSession session) {
        //System.out.println("KibsServiceImpl > logoutCheck : ======");
        session.invalidate(); // 세션 초기화
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processStatisticsAccessor() {
        //System.out.println("KibsServiceImpl > processStatisticsAccessor : ======");

        try {
            String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
            String inDttm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")); // yyyy-MM-dd HH:mm:ss
            StatisticsDTO reqDto = new StatisticsDTO();
            reqDto.setGbn("Accessor");
            reqDto.setTransferYear(transferYear);
            reqDto.setInDttm(inDttm);
            String id = kibsMapper.checkStatisticsAccessor(reqDto);
            if (id != null) { /* update */
                reqDto.setId(id);
                kibsMapper.updateStatisticsAccessor(reqDto);
            } else { /* insert */
                reqDto.setInCount("1");
                kibsMapper.insertStatisticsAccessor(reqDto);
            }

        } catch (Exception e) {
            String eMessage = "[main] processStatisticsAccessor Error : ";
            //System.out.println(e.getMessage() == null ? "" : e.getMessage());
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitorNew(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processInsertExhibitorNew : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer existCheck = kibsMapper.checkDuplicateId(exhibitorNewDTO);
            if (existCheck == 0) {

                String note = "step01";

                exhibitorNewDTO.setApprovalStatus(CommConstants.APPROVAL_STATUS_ING); //작성중
                exhibitorNewDTO.setApplyComplt("N"); //작성중
                exhibitorNewDTO.setPrcYn("0"); //참가비수납여부

                exhibitorNewDTO.setNote(note);

                String exhibitorNewSeq = kibsMapper.getExhibitorNewSeq();
                exhibitorNewDTO.setSeq(exhibitorNewSeq);

                if("KO".equals(exhibitorNewDTO.getLang())){
                    exhibitorNewDTO.setBoothType("등록비");
                    exhibitorNewDTO.setDeposit("0");
                    exhibitorNewDTO.setBalance("110000");
                    exhibitorNewDTO.setRegistrationCnt(1);
                    exhibitorNewDTO.setRegistrationFee(100000);
                    exhibitorNewDTO.setBoothPrcSum(100000);
                    exhibitorNewDTO.setPrcSum(100000);
                    exhibitorNewDTO.setPrcVat(10000);
                    exhibitorNewDTO.setPrcTotal(110000);
                }else{
                    exhibitorNewDTO.setBoothType("");
                    exhibitorNewDTO.setDeposit("0");
                    exhibitorNewDTO.setBalance("0");
                    exhibitorNewDTO.setRegistrationCnt(0);
                    exhibitorNewDTO.setRegistrationFee(0);
                    exhibitorNewDTO.setBoothPrcSum(0);
                    exhibitorNewDTO.setPrcSum(0);
                    exhibitorNewDTO.setPrcVat(0);
                    exhibitorNewDTO.setPrcTotal(0);
                }

                //salt값 생성
                String salt = Salt();
                exhibitorNewDTO.setSalt(salt);

                //암호화
                String pw_encrypt = SHA512(exhibitorNewDTO.getPassword(), salt);
                exhibitorNewDTO.setPassword(pw_encrypt);

                Integer step01_ex_result = kibsMapper.insertExhibitorNew(exhibitorNewDTO);
                if (step01_ex_result > 0) {

                    responseDTO.setCustomValue(exhibitorNewSeq);

                    /* 부담당자정보 insert */
                    if (!StringUtil.isEmpty(exhibitorNewDTO.getChargePersonList())) {
                        for (int i = 0; i < exhibitorNewDTO.getChargePersonList().size(); i++) {
                            ChargeNewDTO chargeNewDTO = exhibitorNewDTO.getChargePersonList().get(i);
                            chargeNewDTO.setExSeq(exhibitorNewSeq);
                            Integer step01_chg_result = kibsMapper.insertChargeNew(chargeNewDTO);
                        }
                    }

                    /* 온라인 전시관 정보 insert */
                    if (!StringUtil.isEmpty(exhibitorNewDTO.getOnlineList())) {
                        for (int i = 0; i < exhibitorNewDTO.getOnlineList().size(); i++) {
                            OnlineNewDTO onlineNewDTO = exhibitorNewDTO.getOnlineList().get(i);
                            onlineNewDTO.setExSeq(exhibitorNewSeq);
                            Integer step01_online_result = kibsMapper.insertOnlineNew(onlineNewDTO);
                        }

                        /* 이전 참가한 업체라면 이전 정보 온라인전시회 노출 여부 'N'으로 변경 */
                        ExhibitorDTO preExhibitorDTO = new ExhibitorDTO();
                        preExhibitorDTO.setCompanyName(exhibitorNewDTO.getCompanyName());
                        preExhibitorDTO.setCompanyCeo(exhibitorNewDTO.getCompanyCeo());
                        Integer updOnlineViewYn = kibsMapper.updateExhibitorOnlineViewYn(preExhibitorDTO);
                    }

                    /* 바이어 insert */
                    if (!StringUtil.isEmpty(exhibitorNewDTO.getBuyerList())) {
                        for (int i = 0; i < exhibitorNewDTO.getBuyerList().size(); i++) {
                            BuyerNewDTO buyerNewDTO = exhibitorNewDTO.getBuyerList().get(i);
                            buyerNewDTO.setExSeq(exhibitorNewSeq);
                            Integer step01_buyer_result = kibsMapper.insertBuyerNew(buyerNewDTO);
                        }
                    }

                }
            }

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step01] processInsertExhibitor Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNew(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String note = "step01";

            /* exhibitor table seq get */
            String exhibitorNewSeq = exhibitorNewDTO.getSeq();

            if ("Y".equals(exhibitorNewDTO.getPasswordYn())) {    //비밀번호 항목 존재 여부
                String inputPw = exhibitorNewDTO.getPassword();
                if (!Objects.equals(inputPw, "암호화된비밀번호복사불가능")) {
                    // 비밀번호칸 입력 O , 기존 비밀번호와 비교
                    ExhibitorNewDTO exhibitorNewInfo = kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);
                    String preSalt = exhibitorNewInfo.getSalt();
                    String prePw = exhibitorNewInfo.getPassword();

                    String afPw = SHA512(inputPw, preSalt);
                    if (!Objects.equals(prePw, afPw)) {

                        ExhibitorNewDTO updateExhibitorNewInfo = new ExhibitorNewDTO();
                        updateExhibitorNewInfo.setSeq(exhibitorNewSeq);

                        // 새로 salt값 생성 & 새로운 비밀번호로 Update
                        String salt = Salt();
                        updateExhibitorNewInfo.setSalt(salt);

                        String afPw_encrypt = SHA512(inputPw, salt);
                        updateExhibitorNewInfo.setPassword(afPw_encrypt);

                        kibsMapper.updateExhibitorNewPassword(updateExhibitorNewInfo);

                    }
                }
            }

            /* exhibitor table update */
            result = kibsMapper.updateExhibitorNew(exhibitorNewDTO);

            if (result == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            } else {
                /* charge_new table update */
                List<ChargeNewDTO> chargeNewList = exhibitorNewDTO.getChargePersonList();
                if (chargeNewList != null) {
                    for (ChargeNewDTO chargeNew : chargeNewList) {
                        String chargeNewSeq = chargeNew.getSeq();
                        if (chargeNewSeq != null & !Objects.equals(chargeNewSeq, "")) {
                            Integer updateResult = kibsMapper.updateChargeNew(chargeNew);
                        } else {
                            Integer insertResult = kibsMapper.insertChargeNew(chargeNew);
                        }
                    }
                }

                /* online table update */
                List<OnlineNewDTO> onlineNewList = exhibitorNewDTO.getOnlineList();
                if (onlineNewList != null) {
                    for (OnlineNewDTO onlineNew : onlineNewList) {
                        String onlineNewSeq = onlineNew.getSeq();
                        if (onlineNewSeq != null & !Objects.equals(onlineNewSeq, "")) {
                            Integer updateResult = kibsMapper.updateOnlineNew(onlineNew);
                        } else {
                            Integer insertResult = kibsMapper.insertOnlineNew(onlineNew);
                        }

                        /* File Note */
                        //processUpdateOnlineFileNote(exhibitorSeq, request.getNote());
                    }

                    /* 이전 참가한 업체라면 이전 정보 온라인전시회 노출 여부 'N'으로 변경 */
                    ExhibitorDTO preExhibitorDTO = new ExhibitorDTO();
                    preExhibitorDTO.setTransferYear(exhibitorNewDTO.getTransferYear());
                    preExhibitorDTO.setCompanyName(exhibitorNewDTO.getCompanyName());
                    preExhibitorDTO.setCompanyCeo(exhibitorNewDTO.getCompanyCeo());
                    Integer updOnlineViewYn = kibsMapper.updateExhibitorOnlineViewYn(preExhibitorDTO);
                }

                /* buyer table update */
                List<BuyerNewDTO> buyerNewList = exhibitorNewDTO.getBuyerList();
                if (buyerNewList != null) {
                    for (BuyerNewDTO buyerNew : buyerNewList) {
                        String buyerNewSeq = buyerNew.getSeq();
                        if (buyerNewSeq != null & !Objects.equals(buyerNewSeq, "")) {
                            Integer updateResult = kibsMapper.updateBuyerNew(buyerNew);
                        } else {
                            Integer insertResult = kibsMapper.insertBuyerNew(buyerNew);
                        }
                    }
                }

            }
            responseDTO.setCustomValue(exhibitorNewSeq);
            //System.out.println(result);
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitor ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processUpdateProductNewFileNote(String exhibitorSeq, String productNote) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(exhibitorSeq);
        fileDTO.setNote(productNote);
        List<String> productNewSeqList = kibsMapper.selectProductNewFileSeq(fileDTO);
        for (int i = 0; i < productNewSeqList.size(); i++) {
            FileDTO updFileDTO = new FileDTO();
            updFileDTO.setId(productNewSeqList.get(i));
            updFileDTO.setNote("productImage" + productNote + "_" + (i + 1));
            Integer updFileNote = kibsMapper.updateImageFileNote(updFileDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processUpdateOnlineNewFileNote(String exhibitorSeq, String onlineNote) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(exhibitorSeq);
        fileDTO.setNote(onlineNote);
        List<String> onlineNewSeqList = kibsMapper.selectOnlineFileSeq(fileDTO);
        for (int i = 0; i < onlineNewSeqList.size(); i++) {
            FileDTO updFileDTO = new FileDTO();
            updFileDTO.setId(onlineNewSeqList.get(i));
            updFileDTO.setNote("onlineImage" + onlineNote + "_" + (i + 1));
            Integer updFileNote = kibsMapper.updateImageFileNote(updFileDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processSaveProductNew(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processSaveProductNew : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer result = kibsMapper.updateExhibitorNewBoatEntryYn(exhibitorNewDTO);
            if (result > 0) {
                String boatEntryYn = exhibitorNewDTO.getBoatEntryYn();
                if("Y".equals(boatEntryYn)) {
                    List<ProductNewDTO> productNewList = exhibitorNewDTO.getProductList();
                    if (productNewList != null && !productNewList.isEmpty()) {
                        for (ProductNewDTO productNewDTO : productNewList) {
                            String productNewSeq = productNewDTO.getSeq();
                            if (productNewSeq != null & !Objects.equals(productNewSeq, "")) {
                                kibsMapper.updateProductNew(productNewDTO);
                            }else{
                                kibsMapper.insertProductNew(productNewDTO);
                            }
                        }
                    }
                }else{
                    String exSeq = exhibitorNewDTO.getSeq();
                    kibsMapper.deleteProductNewExSeq(exSeq);
                }

            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "updateExhibitorNewBoatEntryYn FAIL : " + exhibitorNewDTO.getSeq();
            }

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_9] processSaveProductNew Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewSingle(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectExhibitorNewSingle");
        ExhibitorNewDTO info = kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);

        // --- 참가업체 마이페이지 노출 전 개인정보/회사정보 복호화 ---
        if (info != null) {
            try {
                // 대표담당자 전화번호 복호화
                if (info.getTel() != null && !info.getTel().trim().isEmpty()) {
                    String decryptedTel = safeDecrypt(info.getTel());
                    if (decryptedTel != null && !decryptedTel.isEmpty()) info.setTel(decryptedTel);
                }

                // 대표담당자 휴대전화 복호화
                if (info.getPhone() != null && !info.getPhone().trim().isEmpty()) {
                    String decryptedPhone = safeDecrypt(info.getPhone());
                    if (decryptedPhone != null && !decryptedPhone.isEmpty()) info.setPhone(decryptedPhone);
                }

                // 대표담당자 이메일 복호화
                if (info.getEmail() != null && !info.getEmail().trim().isEmpty()) {
                    String decryptedEmail = safeDecrypt(info.getEmail());
                    if (decryptedEmail != null && !decryptedEmail.isEmpty()) info.setEmail(decryptedEmail);
                }

                // 회사 전화번호 복호화
                if (info.getCompanyTel() != null && !info.getCompanyTel().trim().isEmpty()) {
                    String decryptedCompanyTel = safeDecrypt(info.getCompanyTel());
                    if (decryptedCompanyTel != null && !decryptedCompanyTel.isEmpty()) info.setCompanyTel(decryptedCompanyTel);
                }

                // 회사 팩스번호 복호화
                if (info.getCompanyFax() != null && !info.getCompanyFax().trim().isEmpty()) {
                    String decryptedCompanyFax = safeDecrypt(info.getCompanyFax());
                    if (decryptedCompanyFax != null && !decryptedCompanyFax.isEmpty()) info.setCompanyFax(decryptedCompanyFax);
                }

                // 주소 복호화
                if (info.getCompanyAddress() != null && !info.getCompanyAddress().trim().isEmpty()) {
                    String decryptedCompanyAddress = safeDecrypt(info.getCompanyAddress());
                    if (decryptedCompanyAddress != null && !decryptedCompanyAddress.isEmpty()) {
                        info.setCompanyAddress(decryptedCompanyAddress);
                    }
                }

                // 상세 주소 복호화
                if (info.getCompanyAddressDetail() != null && !info.getCompanyAddressDetail().trim().isEmpty()) {
                    String decryptedCompanyAddressDetail = safeDecrypt(info.getCompanyAddressDetail());
                    if (decryptedCompanyAddressDetail != null && !decryptedCompanyAddressDetail.isEmpty()) {
                        info.setCompanyAddressDetail(decryptedCompanyAddressDetail);
                    }
                }

            } catch (Exception e) {
                // 복호화 에러 시(평문일 경우 등) 로그만 남기고 원래 값 유지
                log.error("[Exhibitor Decrypt Error] seq: {}, msg: {}", info.getSeq(), e.getMessage());
            }
        }
        // -----------------------------------------------------------------

        return info;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargeNewDTO> processSelectChargeNewList(ChargeNewDTO chargeNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectChargeNewList");
        List<ChargeNewDTO> chargeList = kibsMapper.selectChargeNewList(chargeNewDTO);

        // --- 부담당자 마이페이지 노출 전 개인정보 복호화 ---
        if (chargeList != null && !chargeList.isEmpty()) {
            for (ChargeNewDTO charge : chargeList) {
                try {
                    // 부담당자 전화번호 복호화
                    if (charge.getChargePersonTel() != null && !charge.getChargePersonTel().trim().isEmpty()) {
                        String decryptedTel = safeDecrypt(charge.getChargePersonTel());
                        if (decryptedTel != null && !decryptedTel.isEmpty()) charge.setChargePersonTel(decryptedTel);
                    }

                    // 부담당자 휴대전화 복호화
                    if (charge.getChargePersonPhone() != null && !charge.getChargePersonPhone().trim().isEmpty()) {
                        String decryptedPhone = safeDecrypt(charge.getChargePersonPhone());
                        if (decryptedPhone != null && !decryptedPhone.isEmpty()) charge.setChargePersonPhone(decryptedPhone);
                    }

                    // 부담당자 이메일 복호화
                    if (charge.getChargePersonEmail() != null && !charge.getChargePersonEmail().trim().isEmpty()) {
                        String decryptedEmail = safeDecrypt(charge.getChargePersonEmail());
                        if (decryptedEmail != null && !decryptedEmail.isEmpty()) charge.setChargePersonEmail(decryptedEmail);
                    }
                } catch (Exception e) {
                    log.error("[ChargeNew Decrypt Error] seq: {}, msg: {}", charge.getSeq(), e.getMessage());
                }
            }
        }
        // -----------------------------------------------------------

        return chargeList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductNewDTO> processSelectProductNewList(ProductNewDTO productNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectProductNewList");
        return kibsMapper.selectProductNewList(productNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewList(OnlineNewDTO onlineNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectOnlineNewList");
        return kibsMapper.selectOnlineNewList(onlineNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerNewDTO> processSelectBuyerNewList(BuyerNewDTO buyerNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectBuyerNewList");
        List<BuyerNewDTO> buyerList = kibsMapper.selectBuyerNewList(buyerNewDTO);

        // --- 바이어 마이페이지 노출 전 개인정보 복호화 ---
        if (buyerList != null && !buyerList.isEmpty()) {
            for (BuyerNewDTO buyer : buyerList) {
                try {
                    // 바이어 전화번호 복호화
                    if (buyer.getBuyerCompanyTel() != null && !buyer.getBuyerCompanyTel().trim().isEmpty()) {
                        String decryptedTel = safeDecrypt(buyer.getBuyerCompanyTel());
                        if (decryptedTel != null && !decryptedTel.isEmpty()) buyer.setBuyerCompanyTel(decryptedTel);
                    }

                    // 바이어 휴대전화 복호화
                    if (buyer.getBuyerCompanyPhone() != null && !buyer.getBuyerCompanyPhone().trim().isEmpty()) {
                        String decryptedPhone = safeDecrypt(buyer.getBuyerCompanyPhone());
                        if (decryptedPhone != null && !decryptedPhone.isEmpty()) buyer.setBuyerCompanyPhone(decryptedPhone);
                    }

                    // 바이어 이메일 복호화
                    if (buyer.getBuyerCompanyEmail() != null && !buyer.getBuyerCompanyEmail().trim().isEmpty()) {
                        String decryptedEmail = safeDecrypt(buyer.getBuyerCompanyEmail());
                        if (decryptedEmail != null && !decryptedEmail.isEmpty()) buyer.setBuyerCompanyEmail(decryptedEmail);
                    }
                } catch (Exception e) {
                    log.error("[BuyerNew Decrypt Error] seq: {}, msg: {}", buyer.getSeq(), e.getMessage());
                }
            }
        }
        // --------------------------------------------------------

        return buyerList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewBooth : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {

            // 1. DB에서 현재 업체의 전체 정보(특히 '특별 할인' 정보)를 조회합니다.
            ExhibitorNewDTO currentData = kibsMapper.selectExhibitorNewInvoiceDetail(exhibitorNewDTO.getSeq());
            if (currentData == null) {
                throw new Exception("참가업체 정보를 찾을 수 없습니다.");
            }

            // 2. CalculationInputDTO 조립 (사용자 입력 + DB 조회 정보)
            CalculationInputDTO input = new CalculationInputDTO();

            input.setLang(currentData.getLang() != null ? currentData.getLang() : "KO");

            // [부스 정보] - 사용자 입력 (exhibitorNewDTO)
            input.setRegistrationCnt(exhibitorNewDTO.getRegistrationCnt() != null ? exhibitorNewDTO.getRegistrationCnt() : 0);
            input.setStandAloneBoothCnt(exhibitorNewDTO.getStandAloneBoothCnt() != null ? exhibitorNewDTO.getStandAloneBoothCnt() : 0);
            input.setAssemblyBoothCnt(exhibitorNewDTO.getAssemblyBoothCnt() != null ? exhibitorNewDTO.getAssemblyBoothCnt() : 0);
            //input.setOnlineBoothCnt(exhibitorNewDTO.getOnlineBoothCnt());

            // [기본 할인 정보] - 사용자 입력 (exhibitorNewDTO)
            input.setDiscountEarly1(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountEarly1()));
            input.setDiscountEarly2(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountEarly2()));
            input.setDiscountFirstUnder10(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountFirstUnder10()));
            input.setDiscountFirstOver10(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountFirstOver10()));
            input.setDiscountRe(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountRe()));
            input.setDiscountScale1(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountScale1()));
            input.setDiscountScale2(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountScale2()));
            input.setDiscountScale3(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountScale3()));
            input.setDiscountScale4(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountScale4()));
            input.setDiscountScale5(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountScale5()));
            input.setDiscountScale6(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountScale6()));
            input.setDiscountLeisure(Boolean.TRUE.equals(exhibitorNewDTO.getDiscountLeisure()));

            // [기타 정보] - DB 조회 (currentData)
            input.setUtilityPrcSum(currentData.getUtilityPrcSum() != null ? currentData.getUtilityPrcSum() : 0); // 현재 유틸리티비
            int depositVal = 0;
            if (currentData.getDeposit() != null && !String.valueOf(currentData.getDeposit()).trim().isEmpty()) {
                try {
                    depositVal = Integer.parseInt(String.valueOf(currentData.getDeposit()).trim());
                } catch (NumberFormatException e) {
                    depositVal = 0;
                }
            }
            input.setDeposit(depositVal);

            // 5. [특별할인 정보] Null 방어
            input.setDiscountSpecial1Yn(currentData.isDiscountSpecial1Yn());
            input.setDiscountSpecial2Yn(currentData.isDiscountSpecial2Yn());
            input.setDiscountSpecial2Amount(currentData.getDiscountSpecial2Amount());
            input.setDiscountSpecial3Yn(currentData.isDiscountSpecial3Yn());
            input.setDiscountSpecial3Amount(currentData.getDiscountSpecial3Amount());

            input.setMemberCompanyYn(currentData.getMemberCompanyYn() != null ? currentData.getMemberCompanyYn() : "N"); // 회원사 여부

            // 3. *** 공통 서비스 호출 ***
            CalculationResultDTO result = calculationService.calculateTotals(input, currentData.getTransferYear());

            // 4. DTO에 계산 결과 반영
            // (기본 할인, 특별 할인 정보는 이미 exhibitorNewDTO에 있음)
            exhibitorNewDTO.setBoothPrcSum(result.getBoothPrcSum());
            exhibitorNewDTO.setDiscountPrcSum(result.getBasicDiscountSum()); // 기본 할인 총액 저장
            exhibitorNewDTO.setPrcSum(result.getPrcSum());
            exhibitorNewDTO.setPrcVat(result.getPrcVat());
            exhibitorNewDTO.setPrcTotal(result.getPrcTotal());

            // 9. 최종본 DTO를 DB에 업데이트합니다.
            exhibitorNewDTO.setNote("step2_1");
            Integer step2_1_ex_result = kibsMapper.updateExhibitorNewBooth(exhibitorNewDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_1] processUpdateExhibitorNewBooth Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Override
    public BrochureDTO getBrochureInfo(String year) {
        return kibsMapper.selectBrochureInfo(year);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewMaritime(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewMaritime : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_10";
            exhibitorNewDTO.setNote(note);
            Integer step2_10_ex_result = kibsMapper.updateExhibitorNewMaritime(exhibitorNewDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_10] processUpdateExhibitorNewMaritime Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewCompanySign(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewCompanySign : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_2";
            exhibitorNewDTO.setNote(note);
            Integer step2_2_ex_result = kibsMapper.updateExhibitorNewCompanySign(exhibitorNewDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_2] processUpdateExhibitorNewCompanySign Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewUtility(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewUtility : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {

            // 1. DB에서 현재 업체의 전체 정보(부스비, 할인, 특별할인 등)를 조회합니다.
            ExhibitorNewDTO currentData = kibsMapper.selectExhibitorNewInvoiceDetail(exhibitorNewDTO.getSeq());
            if (currentData == null) {
                throw new Exception("참가업체 정보를 찾을 수 없습니다.");
            }

            // 2. CalculationInputDTO 조립 (DB 조회 정보 + 사용자 입력)
            CalculationInputDTO input = new CalculationInputDTO();

            input.setLang(currentData.getLang() != null ? currentData.getLang() : "KO");

            // [부스 정보] - DB 조회 (currentData)
            input.setRegistrationCnt(currentData.getRegistrationCnt() != null ? currentData.getRegistrationCnt() : 0);
            input.setStandAloneBoothCnt(currentData.getStandAloneBoothCnt() != null ? currentData.getStandAloneBoothCnt() : 0);
            input.setAssemblyBoothCnt(currentData.getAssemblyBoothCnt() != null ? currentData.getAssemblyBoothCnt() : 0);
            //input.setOnlineBoothCnt(currentData.getOnlineBoothCnt());

            // [기본 할인 정보] - DB 조회 (currentData)
            input.setDiscountEarly1(Boolean.TRUE.equals(currentData.getDiscountEarly1()));
            input.setDiscountEarly2(Boolean.TRUE.equals(currentData.getDiscountEarly2()));
            input.setDiscountFirstUnder10(Boolean.TRUE.equals(currentData.getDiscountFirstUnder10()));
            input.setDiscountFirstOver10(Boolean.TRUE.equals(currentData.getDiscountFirstOver10()));
            input.setDiscountRe(Boolean.TRUE.equals(currentData.getDiscountRe()));
            input.setDiscountScale1(Boolean.TRUE.equals(currentData.getDiscountScale1()));
            input.setDiscountScale2(Boolean.TRUE.equals(currentData.getDiscountScale2()));
            input.setDiscountScale3(Boolean.TRUE.equals(currentData.getDiscountScale3()));
            input.setDiscountScale4(Boolean.TRUE.equals(currentData.getDiscountScale4()));
            input.setDiscountScale5(Boolean.TRUE.equals(currentData.getDiscountScale5()));
            input.setDiscountScale6(Boolean.TRUE.equals(currentData.getDiscountScale6()));
            input.setDiscountLeisure(Boolean.TRUE.equals(currentData.getDiscountLeisure()));

            // [기타 정보] - DB 조회 (currentData)
            input.setUtilityPrcSum(exhibitorNewDTO.getUtilityPrcSum() != null ? exhibitorNewDTO.getUtilityPrcSum() : 0);

            int depositVal = 0;
            if (currentData.getDeposit() != null && !String.valueOf(currentData.getDeposit()).trim().isEmpty()) {
                try {
                    depositVal = Integer.parseInt(String.valueOf(currentData.getDeposit()).trim());
                } catch (NumberFormatException e) {
                    depositVal = 0;
                }
            }
            input.setDeposit(depositVal);

            // [특별 할인 정보] - DB 조회 (currentData)
            input.setDiscountSpecial1Yn(currentData.isDiscountSpecial1Yn());
            input.setDiscountSpecial2Yn(currentData.isDiscountSpecial2Yn());
            input.setDiscountSpecial2Amount(currentData.getDiscountSpecial2Amount());
            input.setDiscountSpecial3Yn(currentData.isDiscountSpecial3Yn());
            input.setDiscountSpecial3Amount(currentData.getDiscountSpecial3Amount());

            input.setMemberCompanyYn(currentData.getMemberCompanyYn() != null ? currentData.getMemberCompanyYn() : "N");

            // 3. *** 공통 서비스 호출 ***
            CalculationResultDTO result = calculationService.calculateTotals(input, currentData.getTransferYear());

            // 4. DTO에 계산 결과 반영
            // (부스비, 기본/특별 할인은 이미 exhibitorNewDTO에 있음 - 유틸리티만 덮어쓰기)
            exhibitorNewDTO.setBoothPrcSum(result.getBoothPrcSum());
            exhibitorNewDTO.setDiscountPrcSum(result.getBasicDiscountSum());
            exhibitorNewDTO.setPrcSum(result.getPrcSum());
            exhibitorNewDTO.setPrcVat(result.getPrcVat());
            exhibitorNewDTO.setPrcTotal(result.getPrcTotal());

            String note = "step2_3";
            exhibitorNewDTO.setNote(note);
            Integer step2_3_ex_result = kibsMapper.updateExhibitorNewUtility(exhibitorNewDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_3] processUpdateExhibitorNewUtility Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertPassNew(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processInsertPassNew : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            List<PassNewDTO> passList = exhibitorNewDTO.getPassList();
            if (!passList.isEmpty()) {
                for (PassNewDTO passDTO : passList) {
                    passDTO.setExSeq(exhibitorNewDTO.getSeq());

                    if (passDTO.getSeq() != null && !passDTO.getSeq().isEmpty()) {
                        Integer upd_pass_result = kibsMapper.updatePassNew(passDTO);
                    } else {
                        Integer ist_pass_result = kibsMapper.insertPassNew(passDTO);
                    }
                }
            }

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_4] processInsertPassNew Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PassNewDTO> processSelectPassNewList(PassNewDTO passNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectPassNewList");
        return kibsMapper.selectPassNewList(passNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSearchExhibitorNewSeq(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processSearchExhibitorNewSeq");
        return kibsMapper.searchExhibitorNewSeq(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GiftNewDTO> processSelectGiftNewList(GiftNewDTO giftNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectGiftNewList");
        return kibsMapper.selectGiftNewList(giftNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertGiftNew(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processInsertGiftNew : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String exhibitorNewSeq = exhibitorNewDTO.getSeq();
            String giftApplyYn = exhibitorNewDTO.getGiftApplyYn();

            // 1. 미신청(N) 처리 로직: 기존 데이터 및 파일 Soft Delete
            if ("N".equals(giftApplyYn)) {

                GiftNewDTO searchDTO = new GiftNewDTO();
                searchDTO.setExSeq(exhibitorNewSeq);
                List<GiftNewDTO> existingGifts = kibsMapper.selectGiftNewList(searchDTO);

                if (existingGifts != null && !existingGifts.isEmpty()) {
                    List<String> fileIds = new ArrayList<>();

                    // 기존 경품들의 등록된 사진, 로고 파일 ID 수집
                    for (GiftNewDTO gift : existingGifts) {
                        if (gift.getGiftPhoto() != null && !gift.getGiftPhoto().isEmpty()) {
                            fileIds.add(gift.getGiftPhoto());
                        }
                        if (gift.getGiftCompanyLogo() != null && !gift.getGiftCompanyLogo().isEmpty()) {
                            fileIds.add(gift.getGiftCompanyLogo());
                        }
                    }

                    // 파일 테이블 USE_YN='N' 업데이트 (Soft Delete)
                    if (!fileIds.isEmpty()) {
                        kibsMapper.updateGiftFileUnused(fileIds);
                    }

                    // 경품 마스터 데이터 삭제 (Hard Delete)
                    kibsMapper.deleteAllGiftNew(exhibitorNewSeq);
                }
            }
            // 2. 신청(Y) 처리 로직: 기존 로직 유지
            else {
                List<GiftNewDTO> giftList = exhibitorNewDTO.getGiftList();
                if (giftList != null && !giftList.isEmpty()) {
                    for (GiftNewDTO giftDTO : giftList) {
                        giftDTO.setExSeq(exhibitorNewSeq);
                        if (giftDTO.getSeq() != null && !giftDTO.getSeq().isEmpty()) {
                            Integer upd_pass_result = kibsMapper.updateGiftNew(giftDTO);
                        } else {
                            Integer ist_pass_result = kibsMapper.insertGiftNew(giftDTO);
                        }
                    }
                }
            }

            responseDTO.setCustomValue(exhibitorNewSeq);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_5] processInsertGiftNew Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertDirectory(DirectoryDTO directoryDTO) {
        //System.out.println("KibsServiceImpl > processInsertDirectory : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            /*
            Integer directoryCheck = kibsMapper.checkDirectory(directoryDTO);
            directoryDTO.setNote(note);
            if(directoryCheck > 0){
                Integer step2_8_dir_upd_result = kibsMapper.updateDirectory(directoryDTO);
            }else{
                Integer step2_8_dir_ist_result = kibsMapper.insertDirectory(directoryDTO);
            }

            if(!StringUtil.isEmpty(directoryDTO.getChargeList())){
                for(int i=0; i<directoryDTO.getChargeList().size(); i++){
                    ChargePersonDTO chargePersonDTO = directoryDTO.getChargeList().get(i);
                    chargePersonDTO.setId(directoryDTO.getId());
                    chargePersonDTO.setUserId(directoryDTO.getUserId());
                    chargePersonDTO.setTransferYear(directoryDTO.getTransferYear());
                    chargePersonDTO.setNote(note);
                    Integer step2_8_chr_result = kibsMapper.updateChargePersonInfo(chargePersonDTO);
                }
            }

            if(!StringUtil.isEmpty(directoryDTO.getDisplayList())){
                for(int i=0; i<directoryDTO.getDisplayList().size(); i++){
                    DisplayDTO displayDTO = directoryDTO.getDisplayList().get(i);
                    displayDTO.setId(directoryDTO.getId());
                    displayDTO.setUserId(directoryDTO.getUserId());
                    displayDTO.setTransferYear(directoryDTO.getTransferYear());
                    displayDTO.setNote(note);
                    Integer step2_8_dis_result = kibsMapper.updateDisplayInfo(displayDTO);
                }
            }*/

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_8] processInsertDirectory Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewApprovalStatus(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewApprovalStatus : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step03";
            exhibitorNewDTO.setNote(note);
            Integer step03_ex_result = kibsMapper.updateExhibitorNewApprovalStatus(exhibitorNewDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step03] processUpdateExhibitorNewApprovalStatus Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public ResponseDTO processLoginExhibitorNew(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processLoginExhibitorNew : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String getSalt = kibsMapper.getExhibitorNewSalt(exhibitorNewDTO);
            //암호화
            String pw_encrypt = SHA512(exhibitorNewDTO.getPassword(), getSalt);
            exhibitorNewDTO.setPassword(pw_encrypt);

            String seq = kibsMapper.checkLoginExhibitorNew(exhibitorNewDTO);
            if (seq == null || seq.isEmpty()) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "ID not found";
            }

            responseDTO.setCustomValue(seq);
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[login] processLoginExhibitorNew Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewPrc(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectExhibitorNewPrc");
        return kibsMapper.selectExhibitorNewPrc(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitorNewInfo : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {

            String exhibitorNewSeq = exhibitorNewDTO.getSeq();

            String inputPw = exhibitorNewDTO.getPassword();
            if (!Objects.equals(inputPw, "암호화된비밀번호복사불가능")) {
                // 비밀번호칸 입력 O , 기존 비밀번호와 비교
                ExhibitorNewDTO exhibitorNewInfo = kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);
                String preSalt = exhibitorNewInfo.getSalt();
                String prePw = exhibitorNewInfo.getPassword();

                String afPw = SHA512(inputPw, preSalt);
                if (!Objects.equals(prePw, afPw)) {

                    ExhibitorNewDTO updateExhibitorNewInfo = new ExhibitorNewDTO();
                    updateExhibitorNewInfo.setSeq(exhibitorNewSeq);

                    // 새로 salt값 생성 & 새로운 비밀번호로 Update
                    String salt = Salt();
                    updateExhibitorNewInfo.setSalt(salt);

                    String afPw_encrypt = SHA512(inputPw, salt);
                    updateExhibitorNewInfo.setPassword(afPw_encrypt);

                    kibsMapper.updateExhibitorNewPassword(updateExhibitorNewInfo);

                }
            }

            Integer ex_result = kibsMapper.updateExhibitorNewInfo(exhibitorNewDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[EXHIBITOR mypage] processUpdateExhibitorNewInfo Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePassNew(PassNewDTO passNewDTO) {
        //System.out.println("KibsServiceImpl > processDeletePassNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMapper.deletePassNew(passNewDTO);
            if (result == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + passNewDTO.getSeq();
            }
            //System.out.println(result);
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeletePassNew ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteChargeNew(ChargeNewDTO chargeNewDTO) {
        //System.out.println("KibsServiceImpl > processDeleteChargeNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (chargeNewDTO.getSeq() != null) {

                result = kibsMapper.deleteChargeNew(chargeNewDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + chargeNewDTO.getSeq();
                }
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteChargeNew ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteProductNew(ProductNewDTO productNewDTO) {
        //System.out.println("KibsServiceImpl > processDeleteProductNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (productNewDTO.getSeq() != null) {

                result = kibsMapper.deleteProductNew(productNewDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + productNewDTO.getSeq();
                }
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteProductNew ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteOnlineNew(OnlineNewDTO onlineNewDTO) {
        //System.out.println("KibsServiceImpl > processDeleteOnlineNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (onlineNewDTO.getSeq() != null) {

                result = kibsMapper.deleteOnlineNew(onlineNewDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + onlineNewDTO.getSeq();
                }
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteOnlineNew ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteFile(FileDTO fileDTO) {
        //System.out.println("KibsServiceImpl > processDeleteFile");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (fileDTO.getId() != null) {

                result = kibsMapper.deleteFile(fileDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + fileDTO.getId();
                }
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteFile ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectPreExhibitorSingle(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processSelectPreExhibitorSingle");
        return kibsMapper.selectPreExhibitorSingle(exhibitorDTO);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitBooth(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitBooth : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_1";
            exhibitorDTO.setNote(note);
            Integer step2_1_ex_result = kibsMapper.updateExhibitBooth(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_1] processUpdateExhibitBooth Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateCompanySign(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateCompanySign : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_2";
            exhibitorDTO.setNote(note);
            Integer step2_2_ex_result = kibsMapper.updateCompanySign(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_2] processUpdateCompanySign Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitUtility(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitUtility : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_3";
            exhibitorDTO.setNote(note);
            Integer step2_3_ex_result = kibsMapper.updateExhibitUtility(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_3] processUpdateExhibitUtility Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitPass(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processInsertExhibitPass : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_4";
            exhibitorDTO.setNote(note);

            String exhibitorSeq = kibsMapper.searchExhibitorSeq(exhibitorDTO);
            Integer step2_4_ex_result = kibsMapper.updateExhibitNote(exhibitorDTO);

            List<PassDTO> passList = exhibitorDTO.getPassList();
            if (!passList.isEmpty()) {
                for (PassDTO passDTO : passList) {
                    passDTO.setNote(note);
                    passDTO.setId(exhibitorSeq);

                    if (passDTO.getSeq() != null && !"".equals(passDTO.getSeq())) {
                        Integer upd_pass_result = kibsMapper.updateExhibitPass(passDTO);
                    } else {
                        Integer ist_pass_result = kibsMapper.insertExhibitPass(passDTO);
                    }
                }
            }

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_4] processInsertExhibitPass Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitGift(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processInsertExhibitGift : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_5";
            exhibitorDTO.setNote(note);

            String exhibitorSeq = kibsMapper.searchExhibitorSeq(exhibitorDTO);
            Integer step2_5_ex_result = kibsMapper.updateExhibitNote(exhibitorDTO);

            List<GiftDTO> giftList = exhibitorDTO.getGiftList();
            if (!giftList.isEmpty()) {
                for (GiftDTO giftDTO : giftList) {
                    giftDTO.setNote(note);
                    giftDTO.setId(exhibitorSeq);
                    if (giftDTO.getSeq() != null && !giftDTO.getSeq().equals("")) {
                        Integer upd_pass_result = kibsMapper.updateExhibitGift(giftDTO);
                    } else {
                        Integer ist_pass_result = kibsMapper.insertExhibitGift(giftDTO);
                    }
                }
            }

            responseDTO.setCustomValue(exhibitorSeq);
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_5] processInsertExhibitGift Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateWebbanner(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateWebbanner : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_6";
            exhibitorDTO.setNote(note);
            Integer step2_6_ex_result = kibsMapper.updateWebbanner(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_6] processUpdateWebbanner Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorInfo(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processSelectExhibitorInfo");
        return kibsMapper.selectExhibitorInfo(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorPrc(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processSelectExhibitorPrc");
        return kibsMapper.selectExhibitorPrc(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processSelectExhibitorSingle");
        return kibsMapper.selectExhibitorSingle(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeListSeq(ChargePersonDTO chargePersonDTO) {
        //System.out.println("KibsServiceImpl > processSelectChargeListSeq");
        return kibsMapper.selectChargeListSeq(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeEmail(ChargePersonDTO chargePersonDTO) {
        //System.out.println("KibsServiceImpl > processSelectChargeEmail");
        return kibsMapper.selectChargeEmail(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayListSeq(DisplayDTO displayDTO) {
        //System.out.println("KibsServiceImpl > processSelectDisplayListSeq");
        return kibsMapper.selectDisplayListSeq(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineListSeq(OnlineDTO onlineDTO) {
        //System.out.println("KibsServiceImpl > processSelectOnlineListSeq");
        return kibsMapper.selectOnlineListSeq(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerDTO> processSelectBuyerListSeq(BuyerDTO buyerDTO) {
        //System.out.println("KibsServiceImpl > processSelectBuyerListSeq");
        return kibsMapper.selectBuyerListSeq(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public BuyerNewDTO processSelectBuyerNewSingle(BuyerNewDTO buyerNewDTO) {
        //System.out.println("KibsServiceImpl > processSelectBuyerNewSingle");
        return kibsMapper.selectBuyerNewSingle(buyerNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBuyerNew(BuyerNewDTO buyerNewDTO) {
        //System.out.println("KibsServiceImpl > processDeleteBuyerNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (buyerNewDTO.getSeq() != null) {
                result = kibsMapper.deleteBuyerNew(buyerNewDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + buyerNewDTO.getSeq();
                }
                //System.out.println(result);
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteBuyerNew ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeInfo(ChargePersonDTO chargePersonDTO) {
        //System.out.println("KibsServiceImpl > processSelectChargeInfo");
        return kibsMapper.selectChargeInfo(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayInfo(DisplayDTO displayDTO) {
        //System.out.println("KibsServiceImpl > processSelectDisplayInfo");
        return kibsMapper.selectDisplayInfo(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectFileInfo(FileDTO fileDTO) {
        //System.out.println("KibsServiceImpl > processSelectFileInfo");
        return kibsMapper.selectFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitApprovalStatus : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step03";
            exhibitorDTO.setNote(note);
            Integer step03_ex_result = kibsMapper.updateExhibitApprovalStatus(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step03] processUpdateExhibitApprovalStatus Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MainOnlineDTO> processSelectOnlineList(String transferYear) {
        //System.out.println("KibsServiceImpl > processSelectOnlineList");
        return kibsMapper.selectOnlineList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MainOnlineDTO> processSelectEnOnlineListPaging(MainOnlineDTO mainOnlineDTO) {
        //System.out.println("KibsServiceImpl > processSelectEnOnlineListPaging");
        return kibsMapper.selectEnOnlineListPaging(mainOnlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MainOnlineDTO> processSelectOnlineListPaging(MainOnlineDTO mainOnlineDTO) {
        //System.out.println("KibsServiceImpl > processSelectOnlineListPaging");
        return kibsMapper.selectOnlineListPaging(mainOnlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSearchExhibitorSeq(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processGetExhibitorSeq");
        return kibsMapper.searchExhibitorSeq(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectOnlineExhibitorInfo(String seq) {
        //System.out.println("KibsServiceImpl > processSelectOnlineExhibitorInfo");
        return kibsMapper.selectOnlineExhibitorInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectOnlineExhibitorNewInfo(String seq) {
        //System.out.println("KibsServiceImpl > processSelectOnlineExhibitorNewInfo");
        return kibsMapper.selectOnlineExhibitorNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineInfoList(String seq) {
        //System.out.println("KibsServiceImpl > processSelectOnlineInfoList");
        return kibsMapper.selectOnlineInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductNewDTO> processSelectProductNewInfoList(String seq) {
        //System.out.println("KibsServiceImpl > processSelectProductNewInfoList");
        return kibsMapper.selectProductNewInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewInfoList(String seq) {
        //System.out.println("KibsServiceImpl > processSelectOnlineNewInfoList");
        return kibsMapper.selectOnlineNewInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectFileList(String seq) {
        //System.out.println("KibsServiceImpl > processSelectFileList");
        return kibsMapper.selectFileList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public OnlineDTO processSelectOnlineInfo(String seq) {
        //System.out.println("KibsServiceImpl > processSelectOnlineInfo");
        return kibsMapper.selectOnlineInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ProductNewDTO processSelectProductNewInfo(String seq) {
        //System.out.println("KibsServiceImpl > processSelectProductNewInfo");
        return kibsMapper.selectProductNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public OnlineNewDTO processSelectOnlineNewInfo(String seq) {
        //System.out.println("KibsServiceImpl > processSelectOnlineNewInfo");
        return kibsMapper.selectOnlineNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineRelatedDTO> processSelectOnlineRelatedList(OnlineDTO onlineDTO) {
        //System.out.println("KibsServiceImpl > processSelectOnlineRelatedList");
        return kibsMapper.selectOnlineRelatedList(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectProductFileInfo(FileDTO fileDTO) {
        //System.out.println("KibsServiceImpl > processSelectProductFileInfo");
        return kibsMapper.selectProductFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectLogoFileInfo(FileDTO fileDTO) {
        //System.out.println("KibsServiceImpl > processSelectLogoFileInfo");
        return kibsMapper.selectLogoFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineRelatedDTO> processSelectOnlineProductListPaging(OnlineRelatedDTO onlineRelatedDTO) {
        //System.out.println("KibsServiceImpl > processSelectOnlineProductListPaging");
        return kibsMapper.selectOnlineProductListPaging(onlineRelatedDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectNoticeList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectNoticeList");
        return kibsMapper.selectNoticeList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectMainNoticeList(String lang) {
        //System.out.println("KibsServiceImpl > processSelectMainNoticeList");
        return kibsMapper.selectMainNoticeList(lang);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectMainColumnList() {
        //System.out.println("KibsServiceImpl > processSelectMainColumnList");
        return kibsMapper.selectMainColumnList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectMainKibstvList(String lang) {
        //System.out.println("KibsServiceImpl > processSelectMainKibstvList");
        return kibsMapper.selectMainKibstvList(lang);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNoticeViewCnt(String id) {
        //System.out.println("KibsServiceImpl > processUpdateNoticeViewCnt : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer result = kibsMapper.updateNoticeViewCnt(id);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[ERROR] processUpdateNoticeViewCnt : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public NoticeDTO processSelectNoticeSingle(String id) {
        //System.out.println("KibsServiceImpl > processSelectNoticeSingle");
        return kibsMapper.selectNoticeSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FaqDTO> processSelectFaqList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectFaqList");
        return kibsMapper.selectFaqList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PressDTO> processSelectPressList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectPressList");
        return kibsMapper.selectPressList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePressViewCnt(String id) {
        //System.out.println("KibsServiceImpl > processUpdatePressViewCnt : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer result = kibsMapper.updatePressViewCnt(id);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[ERROR] processUpdatePressViewCnt : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public PressDTO processSelectPressSingle(String id) {
        //System.out.println("KibsServiceImpl > processSelectPressSingle");
        return kibsMapper.selectPressSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectColumnList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectColumnList");
        return kibsMapper.selectColumnList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateColumnViewCnt(String id) {
        //System.out.println("KibsServiceImpl > processUpdateColumnViewCnt : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer result = kibsMapper.updateColumnViewCnt(id);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[ERROR] processUpdateColumnViewCnt : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ColumnDTO processSelectColumnSingle(String id) {
        //System.out.println("KibsServiceImpl > processSelectColumnSingle");
        return kibsMapper.selectColumnSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NewsletterDTO> processSelectNewsletterList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectNewsletterList");
        return kibsMapper.selectNewsletterList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNewsletterViewCnt(String id) {
        //System.out.println("KibsServiceImpl > processUpdateNewsletterViewCnt : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer result = kibsMapper.updateNewsletterViewCnt(id);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[ERROR] processUpdateNewsletterViewCnt : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public NewsletterDTO processSelectNewsletterSingle(String id) {
        //System.out.println("KibsServiceImpl > processSelectNewsletterSingle");
        return kibsMapper.selectNewsletterSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectKibstvList");
        return kibsMapper.selectKibstvList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvYearList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectKibstvYearList");
        return kibsMapper.selectKibstvYearList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ContestDTO> processSelectContestList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectContestList");
        return kibsMapper.selectContestList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DataroomDTO> processSelectGalleryList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectGalleryList");
        return kibsMapper.selectGalleryList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<EnExhibitorListDTO> processSelectEnExhibitorList(SearchDTO searchDTO) {
        //System.out.println("KibsServiceImpl > processSelectEnExhibitorList");
        return kibsMapper.selectEnExhibitorList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitBoothSeq(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitBoothSeq : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer step2_1_ex_result = kibsMapper.updateExhibitBoothSeq(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[my_step2_1] processUpdateExhibitBoothSeq Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateCompanySignSeq(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateCompanySignSeq : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer step2_2_ex_result = kibsMapper.updateCompanySignSeq(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_2] processUpdateCompanySignSeq Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitUtilitySeq(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitUtilitySeq : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer step2_3_ex_result = kibsMapper.updateExhibitUtilitySeq(exhibitorDTO);

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[step2_3] processUpdateExhibitUtilitySeq Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PassDTO> processSelectPassList(PassDTO passDTO) {
        //System.out.println("KibsServiceImpl > processSelectPassList");
        return kibsMapper.selectPassList(passDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitPassSeq(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitPassSeq : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {

            List<PassDTO> passList = exhibitorDTO.getPassList();
            if (!passList.isEmpty()) {
                for (PassDTO passDTO : passList) {
                    if (passDTO.getSeq() != null && !passDTO.getSeq().equals("")) {
                        Integer upd_pass_result = kibsMapper.updateExhibitPass(passDTO);
                    } else {
                        Integer ist_pass_result = kibsMapper.insertExhibitPass(passDTO);
                    }
                }
            }

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[my_step2_4] processUpdateExhibitPassSeq Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public GiftDTO processSelectExhibitorGift(GiftDTO giftDTO) {
        //System.out.println("KibsServiceImpl > processSelectExhibitorGift");
        return kibsMapper.selectExhibitorGift(giftDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GiftDTO> processSelectGiftList(GiftDTO giftDTO) {
        //System.out.println("KibsServiceImpl > processSelectGiftList");
        return kibsMapper.selectGiftList(giftDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitGiftSeq(ExhibitorDTO exhibitorDTO) {
        //System.out.println("KibsServiceImpl > processUpdateExhibitGiftSeq : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {

            List<GiftDTO> giftList = exhibitorDTO.getGiftList();
            if (!giftList.isEmpty()) {
                for (GiftDTO giftDTO : giftList) {
                    if (giftDTO.getSeq() != null && !giftDTO.getSeq().equals("")) {
                        Integer upd_pass_result = kibsMapper.updateExhibitGift(giftDTO);
                    } else {
                        Integer ist_pass_result = kibsMapper.insertExhibitGift(giftDTO);
                    }
                }
            }

        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[my_step2_5] processUpdateExhibitGiftSeq Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteExhibitGift(GiftDTO giftDTO) {
        //System.out.println("KibsServiceImpl > processDeleteExhibitGift");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (giftDTO.getSeq() != null) {
                result = kibsMapper.deleteExhibitGift(giftDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + giftDTO.getSeq();
                }
                //System.out.println(result);
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteGift ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public DirectoryDTO processSelectDirectoryInfo(DirectoryDTO directoryDTO) {
        //System.out.println("KibsServiceImpl > processSelectDirectoryInfo");
        return kibsMapper.selectDirectoryInfo(directoryDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PopupDTO> processSelectPopupList(PopupDTO popupDTO) {
        //System.out.println("KibsServiceImpl > processSelectPopupList");
        return kibsMapper.selectPopupList(popupDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public PopupDTO processSelectPopupSingle(PopupDTO popupDTO) {
        //System.out.println("KibsServiceImpl > processSelectPopupSingle");
        return kibsMapper.selectPopupSingle(popupDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VisitorDTO processSelectPreVisitorCheck(VisitorDTO visitorDTO, String clientIp) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", clientIp);
        paramMap.put("userId", "VISITOR_" + clientIp); // 참관객용 임시 키 (IP 기반)
        paramMap.put("ipCheckMinutes", ipCheckMinutes);
        paramMap.put("ipLockMinutes", ipLockMinutes);

        // [방어 로직] 10분 내 30회 초과 조회 실패 시 30분 차단
        int ipFailCount = kibsMapper.selectUserRecentFailCountByIp(paramMap);
        if (ipFailCount >= ipMaxFail) {
            int ipBlockCount = kibsMapper.selectUserIpBlockCheck(paramMap);
            if (ipBlockCount > 0) {
                log.warn("[VISITOR-LOGIN-EVENT] IP: {}, 상태: IP_BLOCKED, IP누적실패: {}", clientIp, ipFailCount);
                VisitorDTO errorDto = new VisitorDTO();
                errorDto.setSeq("IP_BLOCKED");
                return errorDto;
            }
        }

        // 실제 참관객 데이터 조회
        VisitorDTO result = kibsMapper.selectPreVisitorCheck(visitorDTO);

        if (result == null) {
            // 조회 실패 시 카운트 증가
            kibsMapper.upsertUserLoginFailLog(paramMap);
            log.info("[VISITOR-LOGIN-EVENT] IP: {}, 상태: FAIL_NOT_FOUND", clientIp);
        } else {
            // 조회 성공 시 카운트 초기화
            kibsMapper.resetUserLoginFailLog(paramMap);
            log.info("[VISITOR-LOGIN-EVENT] IP: {}, 상태: SUCCESS", clientIp);
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processSaveVisitor(VisitorDTO visitorDTO) {
        //System.out.println("KibsServiceImpl > processSaveVisitor");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {

            //업체 존재 여부 체크
            Integer result = 0;

            if (visitorDTO.getSeq() != null && !"".equals(visitorDTO.getSeq())) {
                //visitor table Update
                result = kibsMapper.updateVisitor(visitorDTO);

                if ("N".equals(visitorDTO.getPartnerYn())) {
                    Integer deleteResult = kibsMapper.deletePartnerAll(visitorDTO.getSeq());
                }
            } else {
                //visitor table Insert
                String seq = kibsMapper.getVisitorSeq();
                visitorDTO.setSeq(seq);
                result = kibsMapper.insertVisitor(visitorDTO);
            }

            if (result == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[processSaveVisitor Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
            } else {
                List<PartnerDTO> partnerList = visitorDTO.getPartner();
                if (!partnerList.isEmpty()) {
                    for (PartnerDTO partnerDTO : partnerList) {
                        if (partnerDTO.getSeq() != null && !"".equals(partnerDTO.getSeq())) {
                            Integer updPartnerResult = kibsMapper.updatePartner(partnerDTO);
                        } else {
                            String partSeq = kibsMapper.getPartnerSeq();
                            partnerDTO.setSeq(partSeq);
                            partnerDTO.setVisitorSeq(visitorDTO.getSeq());
                            Integer istPartnerResult = kibsMapper.insertPartner(partnerDTO);
                        }
                    }
                }
            }

            responseDTO.setCustomValue(visitorDTO.getSeq());
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processSaveVisitor ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public SearchCompanyResponseDTO processSearchCompany(SearchCompanyRequestDTO searchCompanyRequestDTO) {
        //System.out.println("=============== [API 호출 시작] ===============");
        //System.out.println("입력된 검색어: " + searchCompanyRequestDTO.getWkplNm());

        SearchCompanyResponseDTO response = new SearchCompanyResponseDTO();

        // 공공데이터포털에서 발급받은 '인코딩된' 인증키를 그대로 넣으세요. (디코딩된 키라면 URLEncoder 필요)
        // 만약 키에 % 문자가 포함되어 있다면 이미 인코딩된 키일 확률이 높습니다.
        String serviceKey = "nngY%2FlASnTg%2FKJlWdupohRX699RJx6xxaPIsfw3WMoP74fL3ElwqhwmIWWYrlYYABP%2B7SUiOfhPGiVY%2BRDSxdg%3D%3D";

        try {
            // 1. URL 생성
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireServiceV2/getBassInfoSearchV2");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("wkplNm", "UTF-8") + "=" + URLEncoder.encode(searchCompanyRequestDTO.getWkplNm(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); // 한 번에 가져올 개수
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));

            URL url = new URL(urlBuilder.toString());

            // 2. 연결 객체 생성 (부모 타입으로 받기)
            URLConnection connection = url.openConnection();

            // HTTPS일 경우에만 호스트명 검증 무시 설정 적용
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) connection;

                // 모든 호스트 이름을 허용하는 검증기 설정
                httpsConn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true; // 무조건 통과 (인증서 도메인 불일치 무시)
                    }
                });

                // 필요하다면 SSLContext도 초기화 (일부 환경에서 필요할 수 있음)
                // 만약 위 HostnameVerifier만으로 안 된다면, TrustManager 설정도 추가해야 합니다.
            }

            HttpURLConnection conn = (HttpURLConnection) connection;
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            // 3. 응답 코드 확인
            int responseCode = conn.getResponseCode();
            //System.out.println("HTTP 응답 코드: " + responseCode); // [체크포인트 2] 200이 아니면 오류입니다.

            // 4. 응답 데이터 읽기
            BufferedReader rd;
            if (responseCode >= 200 && responseCode <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String responseBody = sb.toString();
            //System.out.println("응답 본문(Raw Data): " + responseBody); // [체크포인트 3] XML 내용 확인

            // 5. XML 파싱 (JAXB)
            // 응답이 정상 XML이 아닐 경우(서비스키 에러 등) 파싱 에러가 발생할 수 있습니다.
            if (responseBody != null && responseBody.contains("<response>")) {
                // JAXB 파싱 로직
                StringReader sr = new StringReader(responseBody);
                JAXBContext jaxbContext = JAXBContext.newInstance(SearchCompanyResponseDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                response = (SearchCompanyResponseDTO) unmarshaller.unmarshal(sr);
            } else {
                System.out.println("API 호출 오류 또는 데이터 포맷 이상: " + responseBody);
            }

        } catch (Exception e) {
            // [체크포인트 4] 어떤 예외가 발생했는지 확인
            System.out.println("=============== [에러 발생] ===============");
            e.printStackTrace();
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void insertBusiness(BusinessDTO businessDTO) {
        //System.out.println("KibsServiceImpl > insertBusiness");
        Integer dupCheck = kibsMapper.checkBusiness(businessDTO);
        if (dupCheck == 0) {
            kibsMapper.insertBusiness(businessDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VisitorDTO processSelectVisitorSingle(String seq) {
        //System.out.println("KibsServiceImpl > processSelectVisitorSingle : ======");
        VisitorDTO visitor = kibsMapper.selectVisitorSingle(seq);

        // --- 마이페이지 노출 전 개인정보 복호화 ---
        if (visitor != null) {
            try {
                // 전화번호 복호화
                if (visitor.getTel() != null && !visitor.getTel().trim().isEmpty()) {
                    String decryptedTel = safeDecrypt(visitor.getTel());
                    if (decryptedTel != null && !decryptedTel.isEmpty()) {
                        visitor.setTel(decryptedTel);
                    }
                }

                // 휴대전화 복호화
                if (visitor.getPhone() != null && !visitor.getPhone().trim().isEmpty()) {
                    String decryptedPhone = safeDecrypt(visitor.getPhone());
                    if (decryptedPhone != null && !decryptedPhone.isEmpty()) {
                        visitor.setPhone(decryptedPhone);
                    }
                }

                // 이메일 복호화
                if (visitor.getEmail() != null && !visitor.getEmail().trim().isEmpty()) {
                    String decryptedEmail = safeDecrypt(visitor.getEmail());
                    if (decryptedEmail != null && !decryptedEmail.isEmpty()) {
                        visitor.setEmail(decryptedEmail);
                    }
                }

                // 이메일 도메인 복호화
                if (visitor.getDomain() != null && !visitor.getDomain().trim().isEmpty()) {
                    visitor.setDomain(visitor.getDomain());
                }

                // 주소 복호화
                if (visitor.getCompanyAddress() != null && !visitor.getCompanyAddress().trim().isEmpty()) {
                    String decryptedCompanyAddress = safeDecrypt(visitor.getCompanyAddress());
                    if (decryptedCompanyAddress != null && !decryptedCompanyAddress.isEmpty()) {
                        visitor.setCompanyAddress(decryptedCompanyAddress);
                    }
                }

                // 상세 주소 복호화
                if (visitor.getCompanyAddressDetail() != null && !visitor.getCompanyAddressDetail().trim().isEmpty()) {
                    String decryptedCompanyAddressDetail = safeDecrypt(visitor.getCompanyAddressDetail());
                    if (decryptedCompanyAddressDetail != null && !decryptedCompanyAddressDetail.isEmpty()) {
                        visitor.setCompanyAddressDetail(decryptedCompanyAddressDetail);
                    }
                }

            } catch (Exception e) {
                // 복호화 에러 시 로그만 남기고 원래 값 유지
                log.error("[Visitor Decrypt Error] seq: {}, msg: {}", seq, e.getMessage());
            }
        }
        // ------------------------------------------------

        return visitor;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PartnerDTO> processSelectPartnerList(String seq) {
        //System.out.println("KibsServiceImpl > processSelectPartnerList");
        return kibsMapper.selectPartnerList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePartner(PartnerDTO partnerDTO) {
        //System.out.println("KibsServiceImpl > processDeletePartner");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if (partnerDTO.getSeq() != null) {

                result = kibsMapper.deletePartner(partnerDTO);
                if (result == 0) {
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + partnerDTO.getSeq();
                }
            } else {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        } catch (Exception e) {
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeletePartner ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    public String Salt() {

        String salt = "";
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            salt = new String(Base64.getEncoder().encode(bytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt;
    }

    public String SHA512(String password, String hash) {
        String salt = hash + password;
        String hex = null;
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-512");
            msg.update(salt.getBytes());

            hex = String.format("%128x", new BigInteger(1, msg.digest()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hex;
    }

    // ==========================================
    // 공통 안전 복호화 헬퍼 메서드
    // ==========================================
    private String safeDecrypt(String text) {
        if (text == null || text.trim().isEmpty()) return text;
        String original = text.trim();
        String decrypted = original;

        try {
            String cipher = original.replace(" ", "+");
            // [수정 핵심] safeDecrypt가 아닌 aes256Util.decrypt()를 호출해야 합니다!
            String dec = aes256Util.decrypt(cipher);

            if (dec != null && !dec.equals(cipher)) {
                // 이중 암호화 방어
                String secondDec = aes256Util.decrypt(dec);
                if (secondDec != null && !secondDec.equals(dec)) {
                    decrypted = secondDec;
                } else {
                    decrypted = dec;
                }
            } else if (original.contains("@")) {
                // 이메일 등 분리 복호화 시도
                String[] parts = original.split("@", 2);
                String cipherId = parts[0].replace(" ", "+");
                String decId = aes256Util.decrypt(cipherId);
                if (decId != null && !decId.equals(cipherId)) {
                    decrypted = decId + "@" + parts[1];
                }
            }
        } catch (Exception e) {
            // 복호화 에러 발생 시 원본 텍스트 유지
        }
        return decrypted;
    }
}