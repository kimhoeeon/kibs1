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
        return kibsMapper.checkDuplicateId(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public InstaTokenDTO processSelectInstaRefresh() {
        return kibsMapper.selectInstaRefresh();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateInstaRefresh(InstaTokenDTO instaTokenDTO) {
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

        Map<String, Object> failLog = kibsMapper.selectUserLoginFailLog(paramMap);

        // ==============================================================
        // [1차 방어] 단일 IP 기준 다중 계정 공격 방어
        // ==============================================================
        int ipFailCount = kibsMapper.selectUserRecentFailCountByIp(paramMap);

        if (ipFailCount >= ipMaxFail) {
            int ipBlockCount = kibsMapper.selectUserIpBlockCheck(paramMap);
            if (ipBlockCount > 0) {
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

            if ("Y".equals(blockYn) || failCnt >= 100) {
                log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_PERMA_BLOCKED, 계정누적실패: {}", exhibitorNewDTO.getId(), clientIp, failCnt);
                resultMap.put("resultCode", "BLOCKED");
                resultMap.put("resultMsg", "보안 정책에 의해 접근이 차단되었습니다. 관리자에게 문의해 주세요.");
                return resultMap;
            }

            if (failCnt >= accountMaxFail) {
                Object dttmObj = failLog.get("last_fail_dttm");
                if (dttmObj != null) {
                    long lastFailTimeMs = 0;
                    if (dttmObj instanceof java.sql.Timestamp) {
                        lastFailTimeMs = ((java.sql.Timestamp) dttmObj).getTime();
                    } else if (dttmObj instanceof java.time.LocalDateTime) {
                        lastFailTimeMs = java.sql.Timestamp.valueOf((java.time.LocalDateTime) dttmObj).getTime();
                    } else if (dttmObj instanceof java.util.Date) {
                        lastFailTimeMs = ((java.util.Date) dttmObj).getTime();
                    }

                    long diffMin = (System.currentTimeMillis() - lastFailTimeMs) / 60000;

                    if (diffMin < accountLockMinutes) {
                        log.warn("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: ACCOUNT_LOCKED, 계정누적실패: {}, 잔여차단시간: {}분", exhibitorNewDTO.getId(), clientIp, failCnt, (accountLockMinutes - diffMin));
                        resultMap.put("resultCode", "LIMIT");
                        resultMap.put("resultMsg", "로그인 실패 횟수를 초과하여 계정이 일시적으로 보호 조치 되었습니다. 잠시 후 다시 시도해 주세요.");
                        return resultMap;
                    } else {
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

        if (getSalt == null) {
            kibsMapper.upsertUserLoginFailLog(paramMap);
            log.info("[LOGIN-EVENT-USER] 계정: {}, IP: {}, 상태: FAIL_MISMATCH", exhibitorNewDTO.getId(), clientIp);
            resultMap.put("resultCode", "FAIL");
            resultMap.put("resultMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return resultMap;
        }

        String pw_encrypt = SHA512(exhibitorNewDTO.getPassword(), getSalt);
        exhibitorNewDTO.setPassword(pw_encrypt);

        String seq = kibsMapper.checkLoginExhibitorNew(exhibitorNewDTO);

        if (seq != null && !seq.isEmpty()) {
            kibsMapper.resetUserLoginFailLog(paramMap);
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
        String encryptedEmail = kibsMapper.getExhibitorNewEmail(exhibitorNewDTO);
        return aes256Util.safeDecrypt(encryptedEmail);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String getExhibitorNewRepEmail(ExhibitorNewDTO exhibitorNewDTO) {
        String encryptedEmail = kibsMapper.getExhibitorNewRepEmail(exhibitorNewDTO);
        return aes256Util.safeDecrypt(encryptedEmail);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<String> preGetExhibitorEmail(ExhibitorDTO exhibitorDTO) {
        List<String> encryptedList = kibsMapper.preGetExhibitorEmail(exhibitorDTO);
        List<String> decryptedList = new ArrayList<>();
        for (String email : encryptedList) {
            decryptedList.add(aes256Util.safeDecrypt(email));
        }
        return decryptedList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewPasswordInit(ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
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
        session.invalidate();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processStatisticsAccessor() {
        try {
            String transferYear = String.valueOf(LocalDateTime.now().getYear());
            String inDttm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
            StatisticsDTO reqDto = new StatisticsDTO();
            reqDto.setGbn("Accessor");
            reqDto.setTransferYear(transferYear);
            reqDto.setInDttm(inDttm);
            String id = kibsMapper.checkStatisticsAccessor(reqDto);
            if (id != null) {
                reqDto.setId(id);
                kibsMapper.updateStatisticsAccessor(reqDto);
            } else {
                reqDto.setInCount("1");
                kibsMapper.insertStatisticsAccessor(reqDto);
            }
        } catch (Exception e) {
            String eMessage = "[main] processStatisticsAccessor Error : ";
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitorNew(ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            // ★ 개인정보 저장 전 DB 암호화 적용 ★
            encryptExhibitorNewDTO(exhibitorNewDTO);

            Integer existCheck = kibsMapper.checkDuplicateId(exhibitorNewDTO);
            if (existCheck == 0) {

                String note = "step01";

                exhibitorNewDTO.setApprovalStatus(CommConstants.APPROVAL_STATUS_ING);
                exhibitorNewDTO.setApplyComplt("N");
                exhibitorNewDTO.setPrcYn("0");
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

                String salt = Salt();
                exhibitorNewDTO.setSalt(salt);

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

                        ExhibitorDTO preExhibitorDTO = new ExhibitorDTO();
                        preExhibitorDTO.setCompanyName(exhibitorNewDTO.getCompanyName());
                        preExhibitorDTO.setCompanyCeo(exhibitorNewDTO.getCompanyCeo());
                        Integer updOnlineViewYn = kibsMapper.updateExhibitorOnlineViewYn(preExhibitorDTO);
                    }

                    /* 바이어 insert */
                    if (!StringUtil.isEmpty(exhibitorNewDTO.getBuyerList())) {
                        for (int i = 0; i < exhibitorNewDTO.getBuyerList().size(); i++) {
                            BuyerNewDTO buyerNewDTO = exhibitorNewDTO.getBuyerList().get(i);

                            // 회사명이 비어있거나 쓰레기값(//)이면 저장 건너뜀
                            String compName = buyerNewDTO.getBuyerCompanyName();
                            if (compName == null || compName.trim().isEmpty() || compName.trim().equals("//") || compName.trim().equals("-")) {
                                continue;
                            }

                            buyerNewDTO.setExSeq(exhibitorNewSeq);
                            kibsMapper.insertBuyerNew(buyerNewDTO);
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
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {
            // ★ 수정 전 DB 암호화 적용 ★
            encryptExhibitorNewDTO(exhibitorNewDTO);

            String note = "step01";
            String exhibitorNewSeq = exhibitorNewDTO.getSeq();

            if ("Y".equals(exhibitorNewDTO.getPasswordYn())) {
                String inputPw = exhibitorNewDTO.getPassword();
                if (!Objects.equals(inputPw, "암호화된비밀번호복사불가능")) {
                    ExhibitorNewDTO exhibitorNewInfo = kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);
                    String preSalt = exhibitorNewInfo.getSalt();
                    String prePw = exhibitorNewInfo.getPassword();

                    String afPw = SHA512(inputPw, preSalt);
                    if (!Objects.equals(prePw, afPw)) {

                        ExhibitorNewDTO updateExhibitorNewInfo = new ExhibitorNewDTO();
                        updateExhibitorNewInfo.setSeq(exhibitorNewSeq);

                        String salt = Salt();
                        updateExhibitorNewInfo.setSalt(salt);

                        String afPw_encrypt = SHA512(inputPw, salt);
                        updateExhibitorNewInfo.setPassword(afPw_encrypt);

                        kibsMapper.updateExhibitorNewPassword(updateExhibitorNewInfo);
                    }
                }
            }

            result = kibsMapper.updateExhibitorNew(exhibitorNewDTO);

            if (result == 0) {
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            } else {
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

                List<OnlineNewDTO> onlineNewList = exhibitorNewDTO.getOnlineList();
                if (onlineNewList != null) {
                    for (OnlineNewDTO onlineNew : onlineNewList) {
                        String onlineNewSeq = onlineNew.getSeq();
                        if (onlineNewSeq != null & !Objects.equals(onlineNewSeq, "")) {
                            Integer updateResult = kibsMapper.updateOnlineNew(onlineNew);
                        } else {
                            Integer insertResult = kibsMapper.insertOnlineNew(onlineNew);
                        }
                    }

                    ExhibitorDTO preExhibitorDTO = new ExhibitorDTO();
                    preExhibitorDTO.setTransferYear(exhibitorNewDTO.getTransferYear());
                    preExhibitorDTO.setCompanyName(exhibitorNewDTO.getCompanyName());
                    preExhibitorDTO.setCompanyCeo(exhibitorNewDTO.getCompanyCeo());
                    Integer updOnlineViewYn = kibsMapper.updateExhibitorOnlineViewYn(preExhibitorDTO);
                }

                List<BuyerNewDTO> buyerNewList = exhibitorNewDTO.getBuyerList();
                if (buyerNewList != null) {
                    for (BuyerNewDTO buyerNew : buyerNewList) {

                        // 회사명이 비어있거나 쓰레기값(//)이면 처리 건너뜀
                        String compName = buyerNew.getBuyerCompanyName();
                        if (compName == null || compName.trim().isEmpty() || compName.trim().equals("//") || compName.trim().equals("-")) {
                            continue;
                        }

                        String buyerNewSeq = buyerNew.getSeq();
                        if (buyerNewSeq != null & !Objects.equals(buyerNewSeq, "")) {
                            kibsMapper.updateBuyerNew(buyerNew);
                        } else {
                            buyerNew.setExSeq(exhibitorNewSeq);
                            kibsMapper.insertBuyerNew(buyerNew);
                        }
                    }
                }
            }
            responseDTO.setCustomValue(exhibitorNewSeq);
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
        ExhibitorNewDTO info = kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);

        // --- 참가업체 마이페이지 노출 전 개인정보/회사정보 복호화 ---
        if (info != null) {
            try {
                if (info.getTel() != null && !info.getTel().trim().isEmpty()) {
                    String decryptedTel = aes256Util.safeDecrypt(info.getTel());
                    if (decryptedTel != null && !decryptedTel.isEmpty()) info.setTel(decryptedTel);
                }

                if (info.getPhone() != null && !info.getPhone().trim().isEmpty()) {
                    String decryptedPhone = aes256Util.safeDecrypt(info.getPhone());
                    if (decryptedPhone != null && !decryptedPhone.isEmpty()) info.setPhone(decryptedPhone);
                }

                if (info.getEmail() != null && !info.getEmail().trim().isEmpty()) {
                    String decryptedEmail = aes256Util.safeDecrypt(info.getEmail());
                    if (decryptedEmail != null && !decryptedEmail.isEmpty()) info.setEmail(decryptedEmail);
                }

                if (info.getCompanyTel() != null && !info.getCompanyTel().trim().isEmpty()) {
                    String decryptedCompanyTel = aes256Util.safeDecrypt(info.getCompanyTel());
                    if (decryptedCompanyTel != null && !decryptedCompanyTel.isEmpty()) info.setCompanyTel(decryptedCompanyTel);
                }

                if (info.getCompanyFax() != null && !info.getCompanyFax().trim().isEmpty()) {
                    String decryptedCompanyFax = aes256Util.safeDecrypt(info.getCompanyFax());
                    if (decryptedCompanyFax != null && !decryptedCompanyFax.isEmpty()) info.setCompanyFax(decryptedCompanyFax);
                }

                if (info.getCompanyAddress() != null && !info.getCompanyAddress().trim().isEmpty()) {
                    String decryptedCompanyAddress = aes256Util.safeDecrypt(info.getCompanyAddress());
                    if (decryptedCompanyAddress != null && !decryptedCompanyAddress.isEmpty()) {
                        info.setCompanyAddress(decryptedCompanyAddress);
                    }
                }

                if (info.getCompanyAddressDetail() != null && !info.getCompanyAddressDetail().trim().isEmpty()) {
                    String decryptedCompanyAddressDetail = aes256Util.safeDecrypt(info.getCompanyAddressDetail());
                    if (decryptedCompanyAddressDetail != null && !decryptedCompanyAddressDetail.isEmpty()) {
                        info.setCompanyAddressDetail(decryptedCompanyAddressDetail);
                    }
                }

                if (info.getFactoryAddress() != null && !info.getFactoryAddress().trim().isEmpty()) {
                    String decryptedFactoryAddress = aes256Util.safeDecrypt(info.getFactoryAddress());
                    if (decryptedFactoryAddress != null && !decryptedFactoryAddress.isEmpty()) {
                        info.setFactoryAddress(decryptedFactoryAddress);
                    }
                }

                if (info.getFactoryAddressDetail() != null && !info.getFactoryAddressDetail().trim().isEmpty()) {
                    String decryptedFactoryAddressDetail = aes256Util.safeDecrypt(info.getFactoryAddressDetail());
                    if (decryptedFactoryAddressDetail != null && !decryptedFactoryAddressDetail.isEmpty()) {
                        info.setFactoryAddressDetail(decryptedFactoryAddressDetail);
                    }
                }

            } catch (Exception e) {
                log.error("[Exhibitor Decrypt Error] seq: {}, msg: {}", info.getSeq(), e.getMessage());
            }
        }
        return info;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargeNewDTO> processSelectChargeNewList(ChargeNewDTO chargeNewDTO) {
        List<ChargeNewDTO> chargeList = kibsMapper.selectChargeNewList(chargeNewDTO);

        // --- 부담당자 마이페이지 노출 전 개인정보 복호화 ---
        if (chargeList != null && !chargeList.isEmpty()) {
            for (ChargeNewDTO charge : chargeList) {
                try {
                    if (charge.getChargePersonTel() != null && !charge.getChargePersonTel().trim().isEmpty()) {
                        String decryptedTel = aes256Util.safeDecrypt(charge.getChargePersonTel());
                        if (decryptedTel != null && !decryptedTel.isEmpty()) charge.setChargePersonTel(decryptedTel);
                    }

                    if (charge.getChargePersonPhone() != null && !charge.getChargePersonPhone().trim().isEmpty()) {
                        String decryptedPhone = aes256Util.safeDecrypt(charge.getChargePersonPhone());
                        if (decryptedPhone != null && !decryptedPhone.isEmpty()) charge.setChargePersonPhone(decryptedPhone);
                    }

                    if (charge.getChargePersonEmail() != null && !charge.getChargePersonEmail().trim().isEmpty()) {
                        String decryptedEmail = aes256Util.safeDecrypt(charge.getChargePersonEmail());
                        if (decryptedEmail != null && !decryptedEmail.isEmpty()) charge.setChargePersonEmail(decryptedEmail);
                    }
                } catch (Exception e) {
                    log.error("[ChargeNew Decrypt Error] seq: {}, msg: {}", charge.getSeq(), e.getMessage());
                }
            }
        }
        return chargeList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductNewDTO> processSelectProductNewList(ProductNewDTO productNewDTO) {
        return kibsMapper.selectProductNewList(productNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewList(OnlineNewDTO onlineNewDTO) {
        return kibsMapper.selectOnlineNewList(onlineNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerNewDTO> processSelectBuyerNewList(BuyerNewDTO buyerNewDTO) {
        List<BuyerNewDTO> buyerList = kibsMapper.selectBuyerNewList(buyerNewDTO);

        // --- 바이어 마이페이지 노출 전 개인정보 복호화 ---
        if (buyerList != null && !buyerList.isEmpty()) {
            for (BuyerNewDTO buyer : buyerList) {
                try {
                    if (buyer.getBuyerCompanyTel() != null && !buyer.getBuyerCompanyTel().trim().isEmpty()) {
                        String decryptedTel = aes256Util.safeDecrypt(buyer.getBuyerCompanyTel());
                        if (decryptedTel != null && !decryptedTel.isEmpty()) buyer.setBuyerCompanyTel(decryptedTel);
                    }

                    if (buyer.getBuyerCompanyPhone() != null && !buyer.getBuyerCompanyPhone().trim().isEmpty()) {
                        String decryptedPhone = aes256Util.safeDecrypt(buyer.getBuyerCompanyPhone());
                        if (decryptedPhone != null && !decryptedPhone.isEmpty()) buyer.setBuyerCompanyPhone(decryptedPhone);
                    }

                    if (buyer.getBuyerCompanyEmail() != null && !buyer.getBuyerCompanyEmail().trim().isEmpty()) {
                        String decryptedEmail = aes256Util.safeDecrypt(buyer.getBuyerCompanyEmail());
                        if (decryptedEmail != null && !decryptedEmail.isEmpty()) buyer.setBuyerCompanyEmail(decryptedEmail);
                    }
                } catch (Exception e) {
                    log.error("[BuyerNew Decrypt Error] seq: {}, msg: {}", buyer.getSeq(), e.getMessage());
                }
            }
        }
        return buyerList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            ExhibitorNewDTO currentData = kibsMapper.selectExhibitorNewInvoiceDetail(exhibitorNewDTO.getSeq());
            if (currentData == null) {
                throw new Exception("참가업체 정보를 찾을 수 없습니다.");
            }

            CalculationInputDTO input = new CalculationInputDTO();

            input.setLang(currentData.getLang() != null ? currentData.getLang() : "KO");

            input.setRegistrationCnt(exhibitorNewDTO.getRegistrationCnt() != null ? exhibitorNewDTO.getRegistrationCnt() : 0);
            input.setStandAloneBoothCnt(exhibitorNewDTO.getStandAloneBoothCnt() != null ? exhibitorNewDTO.getStandAloneBoothCnt() : 0);
            input.setAssemblyBoothCnt(exhibitorNewDTO.getAssemblyBoothCnt() != null ? exhibitorNewDTO.getAssemblyBoothCnt() : 0);

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

            input.setUtilityPrcSum(currentData.getUtilityPrcSum() != null ? currentData.getUtilityPrcSum() : 0);
            int depositVal = 0;
            if (currentData.getDeposit() != null && !String.valueOf(currentData.getDeposit()).trim().isEmpty()) {
                try {
                    depositVal = Integer.parseInt(String.valueOf(currentData.getDeposit()).trim());
                } catch (NumberFormatException e) {
                    depositVal = 0;
                }
            }
            input.setDeposit(depositVal);

            input.setDiscountSpecial1Yn(currentData.isDiscountSpecial1Yn());
            input.setDiscountSpecial2Yn(currentData.isDiscountSpecial2Yn());
            input.setDiscountSpecial2Amount(currentData.getDiscountSpecial2Amount());
            input.setDiscountSpecial3Yn(currentData.isDiscountSpecial3Yn());
            input.setDiscountSpecial3Amount(currentData.getDiscountSpecial3Amount());

            input.setMemberCompanyYn(currentData.getMemberCompanyYn() != null ? currentData.getMemberCompanyYn() : "N");

            CalculationResultDTO result = calculationService.calculateTotals(input, currentData.getTransferYear());

            exhibitorNewDTO.setBoothPrcSum(result.getBoothPrcSum());
            exhibitorNewDTO.setDiscountPrcSum(result.getBasicDiscountSum());
            exhibitorNewDTO.setPrcSum(result.getPrcSum());
            exhibitorNewDTO.setPrcVat(result.getPrcVat());
            exhibitorNewDTO.setPrcTotal(result.getPrcTotal());

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
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            ExhibitorNewDTO currentData = kibsMapper.selectExhibitorNewInvoiceDetail(exhibitorNewDTO.getSeq());
            if (currentData == null) {
                throw new Exception("참가업체 정보를 찾을 수 없습니다.");
            }

            CalculationInputDTO input = new CalculationInputDTO();

            input.setLang(currentData.getLang() != null ? currentData.getLang() : "KO");

            input.setRegistrationCnt(currentData.getRegistrationCnt() != null ? currentData.getRegistrationCnt() : 0);
            input.setStandAloneBoothCnt(currentData.getStandAloneBoothCnt() != null ? currentData.getStandAloneBoothCnt() : 0);
            input.setAssemblyBoothCnt(currentData.getAssemblyBoothCnt() != null ? currentData.getAssemblyBoothCnt() : 0);

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

            input.setDiscountSpecial1Yn(currentData.isDiscountSpecial1Yn());
            input.setDiscountSpecial2Yn(currentData.isDiscountSpecial2Yn());
            input.setDiscountSpecial2Amount(currentData.getDiscountSpecial2Amount());
            input.setDiscountSpecial3Yn(currentData.isDiscountSpecial3Yn());
            input.setDiscountSpecial3Amount(currentData.getDiscountSpecial3Amount());

            input.setMemberCompanyYn(currentData.getMemberCompanyYn() != null ? currentData.getMemberCompanyYn() : "N");

            CalculationResultDTO result = calculationService.calculateTotals(input, currentData.getTransferYear());

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
        return kibsMapper.selectPassNewList(passNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSearchExhibitorNewSeq(ExhibitorNewDTO exhibitorNewDTO) {
        return kibsMapper.searchExhibitorNewSeq(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GiftNewDTO> processSelectGiftNewList(GiftNewDTO giftNewDTO) {
        return kibsMapper.selectGiftNewList(giftNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertGiftNew(ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String exhibitorNewSeq = exhibitorNewDTO.getSeq();
            String giftApplyYn = exhibitorNewDTO.getGiftApplyYn();

            if ("N".equals(giftApplyYn)) {

                GiftNewDTO searchDTO = new GiftNewDTO();
                searchDTO.setExSeq(exhibitorNewSeq);
                List<GiftNewDTO> existingGifts = kibsMapper.selectGiftNewList(searchDTO);

                if (existingGifts != null && !existingGifts.isEmpty()) {
                    List<String> fileIds = new ArrayList<>();

                    for (GiftNewDTO gift : existingGifts) {
                        if (gift.getGiftPhoto() != null && !gift.getGiftPhoto().isEmpty()) {
                            fileIds.add(gift.getGiftPhoto());
                        }
                        if (gift.getGiftCompanyLogo() != null && !gift.getGiftCompanyLogo().isEmpty()) {
                            fileIds.add(gift.getGiftCompanyLogo());
                        }
                    }

                    if (!fileIds.isEmpty()) {
                        kibsMapper.updateGiftFileUnused(fileIds);
                    }

                    kibsMapper.deleteAllGiftNew(exhibitorNewSeq);
                }
            }
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
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {

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
        return kibsMapper.selectExhibitorNewPrc(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            // ★ 수정 전 DB 암호화 적용 ★
            encryptExhibitorNewDTO(exhibitorNewDTO);

            String exhibitorNewSeq = exhibitorNewDTO.getSeq();

            String inputPw = exhibitorNewDTO.getPassword();
            if (!Objects.equals(inputPw, "암호화된비밀번호복사불가능")) {
                ExhibitorNewDTO exhibitorNewInfo = kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);
                String preSalt = exhibitorNewInfo.getSalt();
                String prePw = exhibitorNewInfo.getPassword();

                String afPw = SHA512(inputPw, preSalt);
                if (!Objects.equals(prePw, afPw)) {

                    ExhibitorNewDTO updateExhibitorNewInfo = new ExhibitorNewDTO();
                    updateExhibitorNewInfo.setSeq(exhibitorNewSeq);

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

        // 1. 신규 테이블(26년도 이후)에서 먼저 이전 참가 이력 조회
        ExhibitorDTO latestInfo = kibsMapper.selectPreExhibitorNewSingle(exhibitorDTO);

        if (latestInfo != null) {
            // ★ 핵심: exhibitor_new 테이블의 데이터는 암호화되어 있으므로 반드시 복호화(Decrypt) 처리 ★
            try {
                if (latestInfo.getCompanyAddress() != null && !latestInfo.getCompanyAddress().trim().isEmpty()) {
                    latestInfo.setCompanyAddress(aes256Util.safeDecrypt(latestInfo.getCompanyAddress()));
                }
                if (latestInfo.getCompanyAddressDetail() != null && !latestInfo.getCompanyAddressDetail().trim().isEmpty()) {
                    latestInfo.setCompanyAddressDetail(aes256Util.safeDecrypt(latestInfo.getCompanyAddressDetail()));
                }
                if (latestInfo.getFactoryAddress() != null && !latestInfo.getFactoryAddress().trim().isEmpty()) {
                    latestInfo.setFactoryAddress(aes256Util.safeDecrypt(latestInfo.getFactoryAddress()));
                }
                if (latestInfo.getFactoryAddressDetail() != null && !latestInfo.getFactoryAddressDetail().trim().isEmpty()) {
                    latestInfo.setFactoryAddressDetail(aes256Util.safeDecrypt(latestInfo.getFactoryAddressDetail()));
                }
                if (latestInfo.getCompanyTel() != null && !latestInfo.getCompanyTel().trim().isEmpty()) {
                    latestInfo.setCompanyTel(aes256Util.safeDecrypt(latestInfo.getCompanyTel()));
                }
                if (latestInfo.getCompanyFax() != null && !latestInfo.getCompanyFax().trim().isEmpty()) {
                    latestInfo.setCompanyFax(aes256Util.safeDecrypt(latestInfo.getCompanyFax()));
                }
                if (latestInfo.getTel() != null && !latestInfo.getTel().trim().isEmpty()) {
                    latestInfo.setTel(aes256Util.safeDecrypt(latestInfo.getTel()));
                }
                if (latestInfo.getPhone() != null && !latestInfo.getPhone().trim().isEmpty()) {
                    latestInfo.setPhone(aes256Util.safeDecrypt(latestInfo.getPhone()));
                }
                if (latestInfo.getEmail() != null && !latestInfo.getEmail().trim().isEmpty()) {
                    latestInfo.setEmail(aes256Util.safeDecrypt(latestInfo.getEmail()));
                }
            } catch (Exception e) {
                log.error("[PreExhibitorNew Decrypt Error] id: {}, msg: {}", latestInfo.getId(), e.getMessage());
            }

            return latestInfo;

        } else {
            // 2. 신규 테이블에 이력이 없다면 구형 테이블(25년도 이전)에서 조회
            // (기존 구형 테이블은 평문 형태이므로 별도의 복호화 없이 기존 로직 그대로 반환)
            return kibsMapper.selectPreExhibitorSingle(exhibitorDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitBooth(ExhibitorDTO exhibitorDTO) {
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
        return kibsMapper.selectExhibitorInfo(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorPrc(ExhibitorDTO exhibitorDTO) {
        return kibsMapper.selectExhibitorPrc(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO) {
        return kibsMapper.selectExhibitorSingle(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeListSeq(ChargePersonDTO chargePersonDTO) {
        return kibsMapper.selectChargeListSeq(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeEmail(ChargePersonDTO chargePersonDTO) {
        return kibsMapper.selectChargeEmail(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayListSeq(DisplayDTO displayDTO) {
        return kibsMapper.selectDisplayListSeq(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineListSeq(OnlineDTO onlineDTO) {
        return kibsMapper.selectOnlineListSeq(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerDTO> processSelectBuyerListSeq(BuyerDTO buyerDTO) {
        return kibsMapper.selectBuyerListSeq(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public BuyerNewDTO processSelectBuyerNewSingle(BuyerNewDTO buyerNewDTO) {
        return kibsMapper.selectBuyerNewSingle(buyerNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBuyerNew(BuyerNewDTO buyerNewDTO) {
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
        return kibsMapper.selectChargeInfo(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayInfo(DisplayDTO displayDTO) {
        return kibsMapper.selectDisplayInfo(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectFileInfo(FileDTO fileDTO) {
        return kibsMapper.selectFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO) {
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
        return kibsMapper.selectOnlineList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MainOnlineDTO> processSelectEnOnlineListPaging(MainOnlineDTO mainOnlineDTO) {
        return kibsMapper.selectEnOnlineListPaging(mainOnlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MainOnlineDTO> processSelectOnlineListPaging(MainOnlineDTO mainOnlineDTO) {
        return kibsMapper.selectOnlineListPaging(mainOnlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSearchExhibitorSeq(ExhibitorDTO exhibitorDTO) {
        return kibsMapper.searchExhibitorSeq(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectOnlineExhibitorInfo(String seq) {
        ExhibitorDTO info = kibsMapper.selectOnlineExhibitorInfo(seq);

        if (info != null) {
            if (info.getCompanyTel() == null || info.getCompanyTel().trim().isEmpty() || info.getCompanyTel().trim().equals("-")) {
                String fallback = "";
                if (info.getTel() != null && !info.getTel().trim().isEmpty()) fallback = info.getTel();
                else if (info.getPhone() != null && !info.getPhone().trim().isEmpty()) fallback = info.getPhone();
                info.setCompanyTel(fallback);
            }
        }
        return info;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectOnlineExhibitorNewInfo(String seq) {
        ExhibitorNewDTO info = kibsMapper.selectOnlineExhibitorNewInfo(seq);

        if (info != null) {
            // 1. 쿼리에서 transfer_year를 안 가져올 경우를 대비해 단건 조회로 연도를 확실히 가져옵니다.
            String year = info.getTransferYear();
            if (year == null || year.trim().isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setSeq(seq);
                ExhibitorNewDTO baseInfo = kibsMapper.selectExhibitorNewSingle(reqDTO);
                if (baseInfo != null) {
                    year = baseInfo.getTransferYear();
                }
            }

            // 2. 공백 제거 및 숫자 변환으로 "2027년 이상" 여부 완벽 검증
            boolean isTargetYear = false;
            try {
                if (year != null && Integer.parseInt(year.trim()) >= 2027) {
                    isTargetYear = true;
                }
            } catch (NumberFormatException e) {
                // 연도가 숫자가 아닌 쓰레기값이면 안전하게 복호화를 시도합니다.
                isTargetYear = true;
            }

            // 3. 2027년 이상 데이터만 복호화 진행
            if (isTargetYear) {
                try {
                    if (info.getCompanyTel() != null && !info.getCompanyTel().trim().isEmpty()) {
                        info.setCompanyTel(aes256Util.safeDecrypt(info.getCompanyTel()));
                    }
                    if (info.getCompanyFax() != null && !info.getCompanyFax().trim().isEmpty()) {
                        info.setCompanyFax(aes256Util.safeDecrypt(info.getCompanyFax()));
                    }
                    if (info.getTel() != null && !info.getTel().trim().isEmpty()) {
                        info.setTel(aes256Util.safeDecrypt(info.getTel()));
                    }
                    if (info.getPhone() != null && !info.getPhone().trim().isEmpty()) {
                        info.setPhone(aes256Util.safeDecrypt(info.getPhone()));
                    }
                    if (info.getEmail() != null && !info.getEmail().trim().isEmpty()) {
                        info.setEmail(aes256Util.safeDecrypt(info.getEmail()));
                    }
                    if (info.getCompanyAddress() != null && !info.getCompanyAddress().trim().isEmpty()) {
                        info.setCompanyAddress(aes256Util.safeDecrypt(info.getCompanyAddress()));
                    }
                    if (info.getCompanyAddressDetail() != null && !info.getCompanyAddressDetail().trim().isEmpty()) {
                        info.setCompanyAddressDetail(aes256Util.safeDecrypt(info.getCompanyAddressDetail()));
                    }
                    if (info.getFactoryAddress() != null && !info.getFactoryAddress().trim().isEmpty()) {
                        info.setFactoryAddress(aes256Util.safeDecrypt(info.getFactoryAddress()));
                    }
                    if (info.getFactoryAddressDetail() != null && !info.getFactoryAddressDetail().trim().isEmpty()) {
                        info.setFactoryAddressDetail(aes256Util.safeDecrypt(info.getFactoryAddressDetail()));
                    }

                    // [UI 보정] 대표 연락처(companyTel)가 비어있다면, 담당자의 tel이나 phone으로 화면에 보여줌
                    if (info.getCompanyTel() == null || info.getCompanyTel().trim().isEmpty() || info.getCompanyTel().trim().equals("-")) {
                        String fallback = "";
                        if (info.getTel() != null && !info.getTel().trim().isEmpty()) fallback = info.getTel();
                        else if (info.getPhone() != null && !info.getPhone().trim().isEmpty()) fallback = info.getPhone();
                        info.setCompanyTel(fallback);
                    }
                } catch (Exception e) {
                    log.error("[OnlineView Decrypt Error] seq: {}, msg: {}", seq, e.getMessage());
                }
            }
        }
        return info;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineInfoList(String seq) {
        return kibsMapper.selectOnlineInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductNewDTO> processSelectProductNewInfoList(String seq) {
        return kibsMapper.selectProductNewInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewInfoList(String seq) {
        return kibsMapper.selectOnlineNewInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectFileList(String seq) {
        return kibsMapper.selectFileList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public OnlineDTO processSelectOnlineInfo(String seq) {
        return kibsMapper.selectOnlineInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ProductNewDTO processSelectProductNewInfo(String seq) {
        return kibsMapper.selectProductNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public OnlineNewDTO processSelectOnlineNewInfo(String seq) {
        return kibsMapper.selectOnlineNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineRelatedDTO> processSelectOnlineRelatedList(OnlineDTO onlineDTO) {
        return kibsMapper.selectOnlineRelatedList(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectProductFileInfo(FileDTO fileDTO) {
        return kibsMapper.selectProductFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectLogoFileInfo(FileDTO fileDTO) {
        return kibsMapper.selectLogoFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineRelatedDTO> processSelectOnlineProductListPaging(OnlineRelatedDTO onlineRelatedDTO) {
        return kibsMapper.selectOnlineProductListPaging(onlineRelatedDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectNoticeList(SearchDTO searchDTO) {
        return kibsMapper.selectNoticeList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectMainNoticeList(String lang) {
        return kibsMapper.selectMainNoticeList(lang);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectMainColumnList() {
        return kibsMapper.selectMainColumnList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectMainKibstvList(String lang) {
        return kibsMapper.selectMainKibstvList(lang);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNoticeViewCnt(String id) {
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
        return kibsMapper.selectNoticeSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FaqDTO> processSelectFaqList(SearchDTO searchDTO) {
        return kibsMapper.selectFaqList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PressDTO> processSelectPressList(SearchDTO searchDTO) {
        return kibsMapper.selectPressList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePressViewCnt(String id) {
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
        return kibsMapper.selectPressSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectColumnList(SearchDTO searchDTO) {
        return kibsMapper.selectColumnList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateColumnViewCnt(String id) {
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
        return kibsMapper.selectColumnSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NewsletterDTO> processSelectNewsletterList(SearchDTO searchDTO) {
        return kibsMapper.selectNewsletterList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNewsletterViewCnt(String id) {
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
        return kibsMapper.selectNewsletterSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvList(SearchDTO searchDTO) {
        return kibsMapper.selectKibstvList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvYearList(SearchDTO searchDTO) {
        return kibsMapper.selectKibstvYearList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ContestDTO> processSelectContestList(SearchDTO searchDTO) {
        return kibsMapper.selectContestList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DataroomDTO> processSelectGalleryList(SearchDTO searchDTO) {
        return kibsMapper.selectGalleryList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<EnExhibitorListDTO> processSelectEnExhibitorList(SearchDTO searchDTO) {
        return kibsMapper.selectEnExhibitorList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitBoothSeq(ExhibitorDTO exhibitorDTO) {
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
        return kibsMapper.selectPassList(passDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitPassSeq(ExhibitorDTO exhibitorDTO) {
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
        return kibsMapper.selectExhibitorGift(giftDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GiftDTO> processSelectGiftList(GiftDTO giftDTO) {
        return kibsMapper.selectGiftList(giftDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitGiftSeq(ExhibitorDTO exhibitorDTO) {
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
        return kibsMapper.selectDirectoryInfo(directoryDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PopupDTO> processSelectPopupList(PopupDTO popupDTO) {
        return kibsMapper.selectPopupList(popupDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public PopupDTO processSelectPopupSingle(PopupDTO popupDTO) {
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

        // ★ 핵심 수정: 국가 코드 Loop를 감안하여 실패 카운트 누적 조건 변경 ★
        if (result != null) {
            // 매칭 성공 시 카운트 초기화 (이전 로직 동일)
            kibsMapper.resetUserLoginFailLog(paramMap);
            log.info("[VISITOR-LOGIN-EVENT] IP: {}, 상태: SUCCESS", clientIp);
        } else {
            // 실패 시, 현재 들어온 phone 번호가 "마지막 국가 코드"까지 조회된 결과인지 검증
            String reqPhone = visitorDTO.getPhone() != null ? visitorDTO.getPhone() : "";

            // 만약 프론트에서 보낸 번호가 마지막 순회 코드인 "+91" 로 시작하거나,
            // 애초에 국가코드를 지원하지 않는 국문 환경(하이픈만 제거된 순수 숫자)일 경우에만 카운트를 1회 올림
            if (reqPhone.startsWith("+91 ") || reqPhone.matches("^[0-9]+$")) {
                kibsMapper.upsertUserLoginFailLog(paramMap);
                log.info("[VISITOR-LOGIN-EVENT] IP: {}, 상태: FINAL_FAIL_NOT_FOUND, 카운트 1 증가", clientIp);
            } else {
                // 중간 순회 과정의 실패는 로그만 찍고 DB 카운트를 올리지 않음
                log.info("[VISITOR-LOGIN-EVENT] IP: {}, 상태: LOOP_FAIL_NOT_FOUND (카운트 누적 안함)", clientIp);
            }
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processSaveVisitor(VisitorDTO visitorDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {
            // ★ 참관객 정보 저장 전 DB 암호화 적용 ★
            encryptVisitorDTO(visitorDTO);

            Integer result = 0;

            if (visitorDTO.getSeq() != null && !"".equals(visitorDTO.getSeq())) {
                result = kibsMapper.updateVisitor(visitorDTO);

                if ("N".equals(visitorDTO.getPartnerYn())) {
                    Integer deleteResult = kibsMapper.deletePartnerAll(visitorDTO.getSeq());
                }
            } else {
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
        SearchCompanyResponseDTO response = new SearchCompanyResponseDTO();
        String serviceKey = "nngY%2FlASnTg%2FKJlWdupohRX699RJx6xxaPIsfw3WMoP74fL3ElwqhwmIWWYrlYYABP%2B7SUiOfhPGiVY%2BRDSxdg%3D%3D";

        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireServiceV2/getBassInfoSearchV2");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("wkplNm", "UTF-8") + "=" + URLEncoder.encode(searchCompanyRequestDTO.getWkplNm(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            URLConnection connection = url.openConnection();

            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) connection;
                httpsConn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }

            HttpURLConnection conn = (HttpURLConnection) connection;
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            int responseCode = conn.getResponseCode();

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

            if (responseBody != null && responseBody.contains("<response>")) {
                StringReader sr = new StringReader(responseBody);
                JAXBContext jaxbContext = JAXBContext.newInstance(SearchCompanyResponseDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                response = (SearchCompanyResponseDTO) unmarshaller.unmarshal(sr);
            } else {
                System.out.println("API 호출 오류 또는 데이터 포맷 이상: " + responseBody);
            }

        } catch (Exception e) {
            System.out.println("=============== [에러 발생] ===============");
            e.printStackTrace();
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void insertBusiness(BusinessDTO businessDTO) {
        Integer dupCheck = kibsMapper.checkBusiness(businessDTO);
        if (dupCheck == 0) {
            kibsMapper.insertBusiness(businessDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VisitorDTO processSelectVisitorSingle(String seq) {
        VisitorDTO visitor = kibsMapper.selectVisitorSingle(seq);

        // --- 마이페이지 노출 전 개인정보 복호화 ---
        if (visitor != null) {
            try {
                if (visitor.getTel() != null && !visitor.getTel().trim().isEmpty()) {
                    String decryptedTel = aes256Util.safeDecrypt(visitor.getTel());
                    if (decryptedTel != null && !decryptedTel.isEmpty()) visitor.setTel(decryptedTel);
                }
                if (visitor.getPhone() != null && !visitor.getPhone().trim().isEmpty()) {
                    String decryptedPhone = aes256Util.safeDecrypt(visitor.getPhone());
                    if (decryptedPhone != null && !decryptedPhone.isEmpty()) visitor.setPhone(decryptedPhone);
                }
                if (visitor.getEmail() != null && !visitor.getEmail().trim().isEmpty()) {
                    String decryptedEmail = aes256Util.safeDecrypt(visitor.getEmail());
                    if (decryptedEmail != null && !decryptedEmail.isEmpty()) visitor.setEmail(decryptedEmail);
                }
                if (visitor.getCompanyAddress() != null && !visitor.getCompanyAddress().trim().isEmpty()) {
                    String decryptedCompanyAddress = aes256Util.safeDecrypt(visitor.getCompanyAddress());
                    if (decryptedCompanyAddress != null && !decryptedCompanyAddress.isEmpty()) {
                        visitor.setCompanyAddress(decryptedCompanyAddress);
                    }
                }
                if (visitor.getCompanyAddressDetail() != null && !visitor.getCompanyAddressDetail().trim().isEmpty()) {
                    String decryptedCompanyAddressDetail = aes256Util.safeDecrypt(visitor.getCompanyAddressDetail());
                    if (decryptedCompanyAddressDetail != null && !decryptedCompanyAddressDetail.isEmpty()) {
                        visitor.setCompanyAddressDetail(decryptedCompanyAddressDetail);
                    }
                }
                // 도메인 등 기타 필드도 필요에 따라 복호화 가능
            } catch (Exception e) {
                log.error("[Visitor Decrypt Error] seq: {}, msg: {}", seq, e.getMessage());
            }
        }
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
    // 이중 암호화 및 +82 중복 누적 원천 차단 모듈 (사용자용)
    // ==========================================
    private String cleanAndEncrypt(String input) {
        if (input == null || input.trim().isEmpty()) return input;
        String text = input.trim();

        while (text.startsWith("+82 +82 ")) {
            text = text.replace("+82 +82 ", "+82 ");
        }

        boolean hasCountryCode = false;
        if (text.startsWith("+82 ")) {
            hasCountryCode = true;
            text = text.substring(4).trim();
        }

        String decrypted = aes256Util.safeDecrypt(text).trim();

        if (decrypted.matches("^[a-zA-Z0-9+/]+={0,2}$") && decrypted.length() >= 20 && decrypted.matches(".*[a-zA-Z].*")) {
            decrypted = "";
        }

        if (decrypted.isEmpty()) return "";

        if (hasCountryCode && !decrypted.startsWith("+82")) {
            decrypted = "+82 " + decrypted;
        }

        return aes256Util.encrypt(decrypted);
    }

    // ==========================================
    // DTO DB 저장/수정 전 일괄 암호화 헬퍼 메서드 (사용자용)
    // ==========================================
    private void encryptExhibitorNewDTO(ExhibitorNewDTO dto) {
        try {
            // ★ 2027년 및 그 이후 참가업체 데이터만 암호화 진행 ★
            if (dto.getTransferYear() == null || dto.getTransferYear().compareTo("2027") < 0) return;

            dto.setTel(cleanAndEncrypt(dto.getTel()));
            dto.setPhone(cleanAndEncrypt(dto.getPhone()));
            dto.setEmail(cleanAndEncrypt(dto.getEmail()));
            dto.setCompanyTel(cleanAndEncrypt(dto.getCompanyTel()));
            dto.setCompanyFax(cleanAndEncrypt(dto.getCompanyFax()));
            dto.setCompanyAddress(cleanAndEncrypt(dto.getCompanyAddress()));
            dto.setCompanyAddressDetail(cleanAndEncrypt(dto.getCompanyAddressDetail()));

            if (dto.getChargePersonList() != null) {
                for (ChargeNewDTO charge : dto.getChargePersonList()) {
                    charge.setChargePersonTel(cleanAndEncrypt(charge.getChargePersonTel()));
                    charge.setChargePersonPhone(cleanAndEncrypt(charge.getChargePersonPhone()));
                    charge.setChargePersonEmail(cleanAndEncrypt(charge.getChargePersonEmail()));
                }
            }

            if (dto.getBuyerList() != null) {
                for (BuyerNewDTO buyer : dto.getBuyerList()) {
                    buyer.setBuyerCompanyTel(cleanAndEncrypt(buyer.getBuyerCompanyTel()));
                    buyer.setBuyerCompanyPhone(cleanAndEncrypt(buyer.getBuyerCompanyPhone()));
                    buyer.setBuyerCompanyEmail(cleanAndEncrypt(buyer.getBuyerCompanyEmail()));
                }
            }
        } catch (Exception e) {
            log.error("ExhibitorNew Encrypt Error (User)", e);
        }
    }

    private void encryptVisitorDTO(VisitorDTO dto) {
        try {
            // ★ 2027년 및 그 이후 참관객 데이터만 암호화 진행 ★
            if (dto.getJoinYear() == null || dto.getJoinYear().compareTo("2027") < 0) return;

            dto.setTel(cleanAndEncrypt(dto.getTel()));
            dto.setPhone(cleanAndEncrypt(dto.getPhone()));
            dto.setEmail(cleanAndEncrypt(dto.getEmail()));
            dto.setCompanyAddress(cleanAndEncrypt(dto.getCompanyAddress()));
            dto.setCompanyAddressDetail(cleanAndEncrypt(dto.getCompanyAddressDetail()));
        } catch (Exception e) {
            log.error("Visitor Encrypt Error (User)", e);
        }
    }

}