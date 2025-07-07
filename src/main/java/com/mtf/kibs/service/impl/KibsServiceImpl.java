package com.mtf.kibs.service.impl;

import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.mapper.KibsMapper;
import com.mtf.kibs.service.KibsService;
import com.mtf.kibs.util.StringUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Setter(onMethod_ = {@Autowired})
    private KibsMapper kibsMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Integer checkDuplicateId(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > checkDuplicateId : ======");
        /*KibsMapper dm = sqlSession.getMapper(KibsMapper.class);
        return dm.checkDuplicateId(id);*/
        return kibsMapper.checkDuplicateId(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public InstaTokenDTO processSelectInstaRefresh() {
        System.out.println("KibsServiceImpl > processSelectInstaRefresh");
        return kibsMapper.selectInstaRefresh();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateInstaRefresh(InstaTokenDTO instaTokenDTO) {
        System.out.println("KibsServiceImpl > processUpdateInstaRefresh : ======");
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String getExhibitorNewEmail(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > getExhibitorNewEmail : ======");
        return kibsMapper.getExhibitorNewEmail(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<String> preGetExhibitorEmail(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > preGetExhibitorEmail : ======");
        return kibsMapper.preGetExhibitorEmail(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewPasswordInit(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitorNewPasswordInit : ======");
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
        System.out.println("KibsServiceImpl > processLoginExhibit : ======");
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
        System.out.println("KibsServiceImpl > logoutCheck : ======");
        session.invalidate(); // 세션 초기화
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processStatisticsAccessor() {
        System.out.println("KibsServiceImpl > processStatisticsAccessor : ======");

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
            System.out.println(e.getMessage() == null ? "" : e.getMessage());
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitorNew(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processInsertExhibitorNew : ======");
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

                exhibitorNewDTO.setBoothType("등록비");
                exhibitorNewDTO.setDeposit("0");
                exhibitorNewDTO.setBalance("110000");
                exhibitorNewDTO.setRegistrationCnt(1);
                exhibitorNewDTO.setRegistrationFee(100000);
                exhibitorNewDTO.setBoothPrcSum(100000);
                exhibitorNewDTO.setPrcSum(100000);
                exhibitorNewDTO.setPrcVat(10000);
                exhibitorNewDTO.setPrcTotal(110000);

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

                    /* 전시품 정보 insert */
                    if (!StringUtil.isEmpty(exhibitorNewDTO.getProductList())) {
                        for (int i = 0; i < exhibitorNewDTO.getProductList().size(); i++) {
                            ProductNewDTO productNewDTO = exhibitorNewDTO.getProductList().get(i);
                            productNewDTO.setExSeq(exhibitorNewSeq);
                            Integer step01_dis_result = kibsMapper.insertProductNew(productNewDTO);
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
        System.out.println("KibsServiceImpl > processUpdateExhibitorNew");
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

                /* product_new table update */
                List<ProductNewDTO> productNewList = exhibitorNewDTO.getProductList();
                if (productNewList != null) {
                    for (ProductNewDTO productNew : productNewList) {
                        String productNewSeq = productNew.getSeq();
                        if (productNewSeq != null & !Objects.equals(productNewSeq, "")) {
                            Integer updateResult = kibsMapper.updateProductNew(productNew);
                        } else {
                            Integer insertResult = kibsMapper.insertProductNew(productNew);
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
    public ExhibitorNewDTO processSelectExhibitorNewSingle(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processSelectExhibitorNewSingle");
        return kibsMapper.selectExhibitorNewSingle(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargeNewDTO> processSelectChargeNewList(ChargeNewDTO chargeNewDTO) {
        System.out.println("KibsServiceImpl > processSelectChargeNewList");
        return kibsMapper.selectChargeNewList(chargeNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductNewDTO> processSelectProductNewList(ProductNewDTO productNewDTO) {
        System.out.println("KibsServiceImpl > processSelectProductNewList");
        return kibsMapper.selectProductNewList(productNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewList(OnlineNewDTO onlineNewDTO) {
        System.out.println("KibsServiceImpl > processSelectOnlineNewList");
        return kibsMapper.selectOnlineNewList(onlineNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerNewDTO> processSelectBuyerNewList(BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsServiceImpl > processSelectBuyerNewList");
        return kibsMapper.selectBuyerNewList(buyerNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitorNewBooth : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_1";
            exhibitorNewDTO.setNote(note);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewCompanySign(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitorNewCompanySign : ======");
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
        System.out.println("KibsServiceImpl > processUpdateExhibitorNewUtility : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
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
        System.out.println("KibsServiceImpl > processInsertPassNew : ======");
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
        System.out.println("KibsServiceImpl > processSelectPassNewList");
        return kibsMapper.selectPassNewList(passNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSearchExhibitorNewSeq(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processSearchExhibitorNewSeq");
        return kibsMapper.searchExhibitorNewSeq(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GiftNewDTO> processSelectGiftNewList(GiftNewDTO giftNewDTO) {
        System.out.println("KibsServiceImpl > processSelectGiftNewList");
        return kibsMapper.selectGiftNewList(giftNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertGiftNew(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processInsertGiftNew : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String exhibitorNewSeq = exhibitorNewDTO.getSeq();
            List<GiftNewDTO> giftList = exhibitorNewDTO.getGiftList();
            if (!giftList.isEmpty()) {
                for (GiftNewDTO giftDTO : giftList) {
                    giftDTO.setExSeq(exhibitorNewSeq);
                    if (giftDTO.getSeq() != null && !giftDTO.getSeq().isEmpty()) {
                        Integer upd_pass_result = kibsMapper.updateGiftNew(giftDTO);
                    } else {
                        Integer ist_pass_result = kibsMapper.insertGiftNew(giftDTO);
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
        System.out.println("KibsServiceImpl > processInsertDirectory : ======");
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
        System.out.println("KibsServiceImpl > processUpdateExhibitorNewApprovalStatus : ======");
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
        System.out.println("KibsServiceImpl > processLoginExhibitorNew : ======");
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
        System.out.println("KibsServiceImpl > processSelectExhibitorNewPrc");
        return kibsMapper.selectExhibitorNewPrc(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitorNewInfo : ======");
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
        System.out.println("KibsServiceImpl > processDeletePassNew");
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
        System.out.println("KibsServiceImpl > processDeleteChargeNew");
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
        System.out.println("KibsServiceImpl > processDeleteProductNew");
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
        System.out.println("KibsServiceImpl > processDeleteOnlineNew");
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
        System.out.println("KibsServiceImpl > processDeleteFile");
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
        System.out.println("KibsServiceImpl > processSelectPreExhibitorSingle");
        return kibsMapper.selectPreExhibitorSingle(exhibitorDTO);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitBooth(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitBooth : ======");
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
        System.out.println("KibsServiceImpl > processUpdateCompanySign : ======");
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
        System.out.println("KibsServiceImpl > processUpdateExhibitUtility : ======");
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
        System.out.println("KibsServiceImpl > processInsertExhibitPass : ======");
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
        System.out.println("KibsServiceImpl > processInsertExhibitGift : ======");
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
        System.out.println("KibsServiceImpl > processUpdateWebbanner : ======");
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
        System.out.println("KibsServiceImpl > processSelectExhibitorInfo");
        return kibsMapper.selectExhibitorInfo(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorPrc(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processSelectExhibitorPrc");
        return kibsMapper.selectExhibitorPrc(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processSelectExhibitorSingle");
        return kibsMapper.selectExhibitorSingle(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeListSeq(ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsServiceImpl > processSelectChargeListSeq");
        return kibsMapper.selectChargeListSeq(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeEmail(ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsServiceImpl > processSelectChargeEmail");
        return kibsMapper.selectChargeEmail(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayListSeq(DisplayDTO displayDTO) {
        System.out.println("KibsServiceImpl > processSelectDisplayListSeq");
        return kibsMapper.selectDisplayListSeq(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineListSeq(OnlineDTO onlineDTO) {
        System.out.println("KibsServiceImpl > processSelectOnlineListSeq");
        return kibsMapper.selectOnlineListSeq(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerDTO> processSelectBuyerListSeq(BuyerDTO buyerDTO) {
        System.out.println("KibsServiceImpl > processSelectBuyerListSeq");
        return kibsMapper.selectBuyerListSeq(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public BuyerNewDTO processSelectBuyerNewSingle(BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsServiceImpl > processSelectBuyerNewSingle");
        return kibsMapper.selectBuyerNewSingle(buyerNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBuyerNew(BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsServiceImpl > processDeleteBuyerNew");
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
        System.out.println("KibsServiceImpl > processSelectChargeInfo");
        return kibsMapper.selectChargeInfo(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayInfo(DisplayDTO displayDTO) {
        System.out.println("KibsServiceImpl > processSelectDisplayInfo");
        return kibsMapper.selectDisplayInfo(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectFileInfo(FileDTO fileDTO) {
        System.out.println("KibsServiceImpl > processSelectFileInfo");
        return kibsMapper.selectFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitApprovalStatus : ======");
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
        System.out.println("KibsServiceImpl > processSelectOnlineList");
        return kibsMapper.selectOnlineList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MainOnlineDTO> processSelectOnlineListPaging(MainOnlineDTO mainOnlineDTO) {
        System.out.println("KibsServiceImpl > processSelectOnlineListPaging");
        return kibsMapper.selectOnlineListPaging(mainOnlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSearchExhibitorSeq(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processGetExhibitorSeq");
        return kibsMapper.searchExhibitorSeq(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectOnlineExhibitorInfo(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineExhibitorInfo");
        return kibsMapper.selectOnlineExhibitorInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectOnlineExhibitorNewInfo(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineExhibitorNewInfo");
        return kibsMapper.selectOnlineExhibitorNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineInfoList(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineInfoList");
        return kibsMapper.selectOnlineInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductNewDTO> processSelectProductNewInfoList(String seq) {
        System.out.println("KibsServiceImpl > processSelectProductNewInfoList");
        return kibsMapper.selectProductNewInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewInfoList(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineNewInfoList");
        return kibsMapper.selectOnlineNewInfoList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectFileList(String seq) {
        System.out.println("KibsServiceImpl > processSelectFileList");
        return kibsMapper.selectFileList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public OnlineDTO processSelectOnlineInfo(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineInfo");
        return kibsMapper.selectOnlineInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ProductNewDTO processSelectProductNewInfo(String seq) {
        System.out.println("KibsServiceImpl > processSelectProductNewInfo");
        return kibsMapper.selectProductNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public OnlineNewDTO processSelectOnlineNewInfo(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineNewInfo");
        return kibsMapper.selectOnlineNewInfo(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineRelatedDTO> processSelectOnlineRelatedList(OnlineDTO onlineDTO) {
        System.out.println("KibsServiceImpl > processSelectOnlineRelatedList");
        return kibsMapper.selectOnlineRelatedList(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectProductFileInfo(FileDTO fileDTO) {
        System.out.println("KibsServiceImpl > processSelectProductFileInfo");
        return kibsMapper.selectProductFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectLogoFileInfo(FileDTO fileDTO) {
        System.out.println("KibsServiceImpl > processSelectLogoFileInfo");
        return kibsMapper.selectLogoFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineRelatedDTO> processSelectOnlineProductListPaging(OnlineRelatedDTO onlineRelatedDTO) {
        System.out.println("KibsServiceImpl > processSelectOnlineProductListPaging");
        return kibsMapper.selectOnlineProductListPaging(onlineRelatedDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectNoticeList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectNoticeList");
        return kibsMapper.selectNoticeList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectMainNoticeList(String lang) {
        System.out.println("KibsServiceImpl > processSelectMainNoticeList");
        return kibsMapper.selectMainNoticeList(lang);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectMainColumnList() {
        System.out.println("KibsServiceImpl > processSelectMainColumnList");
        return kibsMapper.selectMainColumnList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectMainKibstvList(String lang) {
        System.out.println("KibsServiceImpl > processSelectMainKibstvList");
        return kibsMapper.selectMainKibstvList(lang);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNoticeViewCnt(String id) {
        System.out.println("KibsServiceImpl > processUpdateNoticeViewCnt : ======");
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
        System.out.println("KibsServiceImpl > processSelectNoticeSingle");
        return kibsMapper.selectNoticeSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FaqDTO> processSelectFaqList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectFaqList");
        return kibsMapper.selectFaqList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PressDTO> processSelectPressList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectPressList");
        return kibsMapper.selectPressList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePressViewCnt(String id) {
        System.out.println("KibsServiceImpl > processUpdatePressViewCnt : ======");
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
        System.out.println("KibsServiceImpl > processSelectPressSingle");
        return kibsMapper.selectPressSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectColumnList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectColumnList");
        return kibsMapper.selectColumnList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateColumnViewCnt(String id) {
        System.out.println("KibsServiceImpl > processUpdateColumnViewCnt : ======");
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
        System.out.println("KibsServiceImpl > processSelectColumnSingle");
        return kibsMapper.selectColumnSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NewsletterDTO> processSelectNewsletterList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectNewsletterList");
        return kibsMapper.selectNewsletterList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNewsletterViewCnt(String id) {
        System.out.println("KibsServiceImpl > processUpdateNewsletterViewCnt : ======");
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
        System.out.println("KibsServiceImpl > processSelectNewsletterSingle");
        return kibsMapper.selectNewsletterSingle(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectKibstvList");
        return kibsMapper.selectKibstvList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvYearList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectKibstvYearList");
        return kibsMapper.selectKibstvYearList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GalleryDTO> processSelectGalleryList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectGalleryList");
        return kibsMapper.selectGalleryList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<EnExhibitorListDTO> processSelectEnExhibitorList(SearchDTO searchDTO) {
        System.out.println("KibsServiceImpl > processSelectEnExhibitorList");
        return kibsMapper.selectEnExhibitorList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitBoothSeq(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitBoothSeq : ======");
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
        System.out.println("KibsServiceImpl > processUpdateCompanySignSeq : ======");
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
        System.out.println("KibsServiceImpl > processUpdateExhibitUtilitySeq : ======");
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
        System.out.println("KibsServiceImpl > processSelectPassList");
        return kibsMapper.selectPassList(passDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitPassSeq(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitPassSeq : ======");
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
        System.out.println("KibsServiceImpl > processSelectExhibitorGift");
        return kibsMapper.selectExhibitorGift(giftDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GiftDTO> processSelectGiftList(GiftDTO giftDTO) {
        System.out.println("KibsServiceImpl > processSelectGiftList");
        return kibsMapper.selectGiftList(giftDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitGiftSeq(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitGiftSeq : ======");
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
        System.out.println("KibsServiceImpl > processDeleteExhibitGift");
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
        System.out.println("KibsServiceImpl > processSelectDirectoryInfo");
        return kibsMapper.selectDirectoryInfo(directoryDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PopupDTO> processSelectPopupList(PopupDTO popupDTO) {
        System.out.println("KibsServiceImpl > processSelectPopupList");
        return kibsMapper.selectPopupList(popupDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public PopupDTO processSelectPopupSingle(PopupDTO popupDTO) {
        System.out.println("KibsServiceImpl > processSelectPopupSingle");
        return kibsMapper.selectPopupSingle(popupDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VisitorDTO processSelectPreVisitorCheck(VisitorDTO visitorDTO) {
        System.out.println("KibsServiceImpl > processSelectPreVisitorCheck : ======");
        return kibsMapper.selectPreVisitorCheck(visitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processSaveVisitor(VisitorDTO visitorDTO) {
        System.out.println("KibsServiceImpl > processSaveVisitor");
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
        System.out.println("KibsServiceImpl > processSearchCompany");
        SearchCompanyResponseDTO response = new SearchCompanyResponseDTO();
        try {

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "nngY%2FlASnTg%2FKJlWdupohRX699RJx6xxaPIsfw3WMoP74fL3ElwqhwmIWWYrlYYABP%2B7SUiOfhPGiVY%2BRDSxdg%3D%3D"); /*Service Key*/
            //urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_dg_cd","UTF-8") + "=" + URLEncoder.encode("41", "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
            //urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_cd","UTF-8") + "=" + URLEncoder.encode("117", "UTF-8")); /*시군구(행정자치부 법정동 주소코드 참조)*/
            //urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_emd_cd","UTF-8") + "=" + URLEncoder.encode("101", "UTF-8")); /*읍면동(행정자치부 법정동 주소코드 참조)*/
            urlBuilder.append("&" + URLEncoder.encode("wkpl_nm", "UTF-8") + "=" + URLEncoder.encode(searchCompanyRequestDTO.getWkplNm(), "UTF-8")); /*사업장명*/
            //urlBuilder.append("&" + URLEncoder.encode("bzowr_rgst_no","UTF-8") + "=" + URLEncoder.encode("124815", "UTF-8")); /*사업자등록번호(앞에서 6자리)*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());

            String xmlData = sb.toString();

            StringReader sr = new StringReader(xmlData);
            if (!xmlData.contains("OpenAPI_")) {
                JAXBContext jaxbContext = JAXBContext.newInstance(SearchCompanyResponseDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                response = (SearchCompanyResponseDTO) unmarshaller.unmarshal(sr);
            }

        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void insertBusiness(BusinessDTO businessDTO) {
        System.out.println("KibsServiceImpl > insertBusiness");
        Integer dupCheck = kibsMapper.checkBusiness(businessDTO);
        if (dupCheck == 0) {
            kibsMapper.insertBusiness(businessDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VisitorDTO processSelectVisitorSingle(String seq) {
        System.out.println("KibsServiceImpl > processSelectVisitorSingle : ======");
        return kibsMapper.selectVisitorSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PartnerDTO> processSelectPartnerList(String seq) {
        System.out.println("KibsServiceImpl > processSelectPartnerList");
        return kibsMapper.selectPartnerList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePartner(PartnerDTO partnerDTO) {
        System.out.println("KibsServiceImpl > processDeletePartner");
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
}