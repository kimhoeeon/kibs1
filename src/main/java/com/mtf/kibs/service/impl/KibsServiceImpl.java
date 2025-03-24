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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class KibsServiceImpl implements KibsService {

    private static final String STR_RESULT_H = "%s - %s";

    //private final SqlSession sqlSession;

    @Setter(onMethod_ = {@Autowired})
    private KibsMapper kibsMapper;

    /*public KibsServiceImpl(SqlSession ss) {
        this.sqlSession = ss;
    }*/

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Integer checkDuplicateId(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > checkDuplicateId : ======");
        /*KibsMapper dm = sqlSession.getMapper(KibsMapper.class);
        return dm.checkDuplicateId(id);*/
        return kibsMapper.checkDuplicateId(exhibitorDTO);
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
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }
        }catch (Exception e){
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
    public String getExhibitorEmail(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > getExhibitorEmail : ======");
        return kibsMapper.getExhibitorEmail(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorPasswordInit(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitorPasswordInit : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            exhibitorDTO.setPassword("aa134!@cc");
            Integer result = kibsMapper.updateExhibitPasswordInit(exhibitorDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[Find PW] processUpdateExhibitorPasswordInit Error : ";
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
            if(loginCheck == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "ID not found";
            }
        }catch (Exception e){
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
            if(id != null){ /* update */
                reqDto.setId(id);
                kibsMapper.updateStatisticsAccessor(reqDto);
            }else{ /* insert */
                reqDto.setInCount("1");
                kibsMapper.insertStatisticsAccessor(reqDto);
            }

        }catch (Exception e){
            String eMessage = "[main] processStatisticsAccessor Error : ";
            System.out.println(e.getMessage() == null ? "" : e.getMessage());
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitor(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processInsertExhibitor : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer existCheck = kibsMapper.checkDuplicateId(exhibitorDTO);
            if(existCheck == 0){

                String note = "step01";

                exhibitorDTO.setApprovalStatus(CommConstants.APPROVAL_STATUS_ING); //작성중
                exhibitorDTO.setApplyComplt("N"); //작성중
                exhibitorDTO.setPrcYn("0"); //참가비수납여부

                exhibitorDTO.setNote(note);

                String exhibitorSeq = kibsMapper.getExhibitorSeq();
                exhibitorDTO.setSeq(exhibitorSeq);

                exhibitorDTO.setBoothType("등록비");
                exhibitorDTO.setBalance("110000");
                exhibitorDTO.setRegistrationCnt(1);
                exhibitorDTO.setRegistrationFee(100000);
                exhibitorDTO.setBoothPrcSum(100000);
                exhibitorDTO.setPrcSum(100000);
                exhibitorDTO.setPrcVat(10000);
                exhibitorDTO.setPrcTotal(110000);

                Integer step01_ex_result = kibsMapper.insertExhibitor(exhibitorDTO);
                if(step01_ex_result > 0){

                    responseDTO.setCustomValue(exhibitorSeq);

                    /* 담당자정보 insert */
                    if(!StringUtil.isEmpty(exhibitorDTO.getChargePersonList())){
                        for(int i=0; i<exhibitorDTO.getChargePersonList().size(); i++){
                            ChargePersonDTO chargePersonDTO = exhibitorDTO.getChargePersonList().get(i);
                            chargePersonDTO.setId(exhibitorSeq);
                            chargePersonDTO.setNote(note);
                            Integer step01_chg_result = kibsMapper.insertChargePersonInfo(chargePersonDTO);
                        }
                    }

                    /* 전시정보 insert */
                    if(!StringUtil.isEmpty(exhibitorDTO.getDisplayList())){
                        for(int i=0; i<exhibitorDTO.getDisplayList().size(); i++){
                            DisplayDTO displayDTO = exhibitorDTO.getDisplayList().get(i);
                            displayDTO.setId(exhibitorSeq);
                            displayDTO.setNote(note);
                            Integer step01_dis_result = kibsMapper.insertDisplayInfo(displayDTO);
                        }
                    }

                    /* 온라인전시회 insert */
                    if(!StringUtil.isEmpty(exhibitorDTO.getOnlineExhibitList())){
                        for(int i=0; i<exhibitorDTO.getOnlineExhibitList().size(); i++){
                            OnlineDTO onlineDTO = exhibitorDTO.getOnlineExhibitList().get(i);
                            onlineDTO.setId(exhibitorSeq);
                            Integer step01_online_result = kibsMapper.insertOnlineInfo(onlineDTO);
                        }

                        /* 이전 참가한 업체라면 이전 정보 온라인전시회 노출 여부 'N'으로 변경 */
                        Integer updOnlineViewYn = kibsMapper.updateExhibitorOnlineViewYn(exhibitorDTO);
                    }

                    /* 바이어 insert */
                    if(!StringUtil.isEmpty(exhibitorDTO.getBuyerList())){
                        for(int i=0; i<exhibitorDTO.getBuyerList().size(); i++){
                            BuyerDTO buyerDTO = exhibitorDTO.getBuyerList().get(i);
                            buyerDTO.setId(exhibitorSeq);
                            buyerDTO.setUserId(exhibitorDTO.getId());
                            buyerDTO.setNote(note);
                            Integer step01_buyer_result = kibsMapper.insertBuyerInfo(buyerDTO);
                        }
                    }

                    /* exhibitor_comp update */
                    //exhibitor table check
                    /*Integer exhibitorCompRst = 0;
                    String companyNameKo = exhibitorDTO.getCompanyNameKo();
                    String companyCeo = exhibitorDTO.getCompanyCeo();
                    if((companyNameKo != null && !"".equals(companyNameKo)) && companyCeo != null && !"".equals(companyCeo)) {

                        String match = "[^가-힣0-9a-zA-Z]";
                        companyNameKo = companyNameKo.replaceAll(" ", "").replaceAll("(주)","").replaceAll(match, "");
                        companyCeo = companyCeo.replaceAll(" ", "");

                        String param = companyNameKo + "_" + companyCeo;

                        //exhibitor_comp table check
                        Map<String, String> paramMap = new HashMap<>();
                        paramMap.put("transferYear", exhibitorDTO.getTransferYear());
                        paramMap.put("paramVal", param);
                        ParticipantCompanyDTO updExhibitorCompInfo = kibsMapper.getExhibitorCompInfo(paramMap);
                        //seq , ex_seq , lang , id , transfer_year

                        if(updExhibitorCompInfo != null){
                            if("Y".equals(exhibitorDTO.getPartWantYn())){
                                updExhibitorCompInfo.setPartWantYn("1");
                            }else{
                                updExhibitorCompInfo.setPartWantYn("3");
                            }
                            updExhibitorCompInfo.setPrePartYear(exhibitorDTO.getPrePartYear());
                            updExhibitorCompInfo.setCompanyNameKo(exhibitorDTO.getCompanyNameKo());
                            updExhibitorCompInfo.setCompanyHomepage(exhibitorDTO.getCompanyHomepage());
                            updExhibitorCompInfo.setCompanyAddress(exhibitorDTO.getCompanyAddress() + " " + exhibitorDTO.getCompanyAddressDetail());
                            updExhibitorCompInfo.setCompanyCeo(exhibitorDTO.getCompanyCeo());
                            updExhibitorCompInfo.setCompanyFax(exhibitorDTO.getCompanyFax());

                            *//* charge table update *//*
                            List<ChargePersonDTO> chargeList_comp = exhibitorDTO.getChargePersonList();
                            if(chargeList_comp != null){
                                for(int i=0; i<chargeList_comp.size(); i++) {
                                    ChargePersonDTO info = chargeList_comp.get(i);
                                    if(i == 0){
                                        updExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                        updExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                        updExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                        updExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                        updExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                    }else if(i == 1){
                                        updExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                        updExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                        updExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                        updExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                        updExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                    }else if(i == 2){
                                        updExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                        updExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                        updExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                        updExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                        updExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                    }
                                }
                            }

                            //exhibitor_comp table Update
                            exhibitorCompRst = kibsMapper.updateExhibitorComp(updExhibitorCompInfo);
                        }else{
                            ParticipantCompanyDTO istExhibitorCompInfo = new ParticipantCompanyDTO();
                            String exhibitorCompSeq = kibsMapper.getExhibitorCompSeq();

                            istExhibitorCompInfo.setSeq(exhibitorCompSeq);
                            istExhibitorCompInfo.setExSeq(exhibitorDTO.getSeq());
                            istExhibitorCompInfo.setLang(exhibitorDTO.getLang());
                            istExhibitorCompInfo.setId(exhibitorDTO.getId());
                            istExhibitorCompInfo.setTransferYear(exhibitorDTO.getTransferYear());
                            if("Y".equals(exhibitorDTO.getPartWantYn())){
                                istExhibitorCompInfo.setPartWantYn("1");
                            }else{
                                istExhibitorCompInfo.setPartWantYn("3");
                            }
                            istExhibitorCompInfo.setPrePartYear(exhibitorDTO.getPrePartYear());
                            istExhibitorCompInfo.setCompanyNameKo(exhibitorDTO.getCompanyNameKo());
                            istExhibitorCompInfo.setCompanyHomepage(exhibitorDTO.getCompanyHomepage());
                            istExhibitorCompInfo.setCompanyAddress(exhibitorDTO.getCompanyAddress() + " " + exhibitorDTO.getCompanyAddressDetail());
                            istExhibitorCompInfo.setCompanyCeo(exhibitorDTO.getCompanyCeo());
                            istExhibitorCompInfo.setCompanyFax(exhibitorDTO.getCompanyFax());

                            *//* charge table update *//*
                            List<ChargePersonDTO> chargeList_comp = exhibitorDTO.getChargePersonList();
                            if(chargeList_comp != null){
                                for(int i=0; i<chargeList_comp.size(); i++) {
                                    ChargePersonDTO info = chargeList_comp.get(i);
                                    if(i == 0){
                                        istExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                        istExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                        istExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                        istExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                        istExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                    }else if(i == 1){
                                        istExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                        istExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                        istExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                        istExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                        istExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                    }else if(i == 2){
                                        istExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                        istExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                        istExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                        istExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                        istExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                    }
                                }
                            }

                            //exhibitor_comp table Insert
                            exhibitorCompRst = kibsMapper.insertExhibitorComp(istExhibitorCompInfo);
                        }

                        if(exhibitorCompRst == 0){
                            resultCode = CommConstants.RESULT_CODE_FAIL;
                            resultMessage = "[step01] processInsertExhibitor exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                        }

                    }*/

                }
            }

        }catch (Exception e){
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
    public ResponseDTO processUpdateExhibitor(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitor");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String note = "step01";

            /* exhibitor table seq get */
            String exhibitorSeq = exhibitorDTO.getSeq();

            /* exhibitor table update */
            result = kibsMapper.UpdateExhibitor(exhibitorDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }else {
                /* charge table update */
                List<ChargePersonDTO> chargeList = exhibitorDTO.getChargePersonList();
                if(chargeList != null){
                    for(ChargePersonDTO request : chargeList) {
                        String chargeSeq = request.getSeq();
                        if (chargeSeq != null & !Objects.equals(chargeSeq, "")) {
                            Integer updateResult = kibsMapper.updateExhibitorCharge(request);
                        } else {
                            request.setNote(note);
                            Integer insertResult = kibsMapper.insertChargePersonInfo(request);
                        }
                    }
                }

                /* display table update */
                List<DisplayDTO> displayList = exhibitorDTO.getDisplayList();
                if (displayList != null) {
                    for (DisplayDTO request : displayList) {
                        String displaySeq = request.getSeq();
                        if (displaySeq != null & !Objects.equals(displaySeq, "")) {
                            Integer updateResult = kibsMapper.updateExhibitorDisplay(request);
                        } else {
                            request.setNote(note);
                            Integer insertResult = kibsMapper.insertDisplayInfo(request);
                        }
                    }
                }

                /* online table update */
                List<OnlineDTO> onlineList = exhibitorDTO.getOnlineExhibitList();
                if (onlineList != null) {
                    for (OnlineDTO request : onlineList) {
                        String displaySeq = request.getSeq();
                        if (displaySeq != null & !Objects.equals(displaySeq, "")) {
                            Integer updateResult = kibsMapper.updateExhibitorOnline(request);
                        } else {
                            Integer insertResult = kibsMapper.insertOnlineInfo(request);
                        }

                        /* File Note */
                        //processUpdateOnlineFileNote(exhibitorSeq, request.getNote());

                    }

                    /* 이전 참가한 업체라면 이전 정보 온라인전시회 노출 여부 'N'으로 변경 */
                    Integer updOnlineViewYn = kibsMapper.updateExhibitorOnlineViewYn(exhibitorDTO);
                }

                /* buyer table update */
                List<BuyerDTO> buyerList = exhibitorDTO.getBuyerList();
                if (buyerList != null) {
                    for (BuyerDTO request : buyerList) {
                        String buyerSeq = request.getSeq();
                        if (buyerSeq != null & !Objects.equals(buyerSeq, "")) {
                            Integer updateResult = kibsMapper.updateExhibitorBuyer(request);
                        } else {
                            request.setNote(note);
                            Integer insertResult = kibsMapper.insertBuyerInfo(request);
                        }
                    }
                }

                /* exhibitor_comp update */
                //exhibitor table check
                /*Integer exhibitorCompRst = 0;
                String companyNameKo = exhibitorDTO.getCompanyNameKo();
                String companyCeo = exhibitorDTO.getCompanyCeo();
                if((companyNameKo != null && !"".equals(companyNameKo)) && companyCeo != null && !"".equals(companyCeo)) {

                    String match = "[^가-힣0-9a-zA-Z]";
                    companyNameKo = companyNameKo.replaceAll(" ", "").replaceAll("(주)","").replaceAll(match, "");
                    companyCeo = companyCeo.replaceAll(" ", "");

                    String param = companyNameKo + "_" + companyCeo;

                    //exhibitor_comp table check
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("transferYear", exhibitorDTO.getTransferYear());
                    paramMap.put("paramVal", param);
                    ParticipantCompanyDTO updExhibitorCompInfo = kibsMapper.getExhibitorCompInfo(paramMap);
                    //seq , ex_seq , lang , id , transfer_year

                    if(updExhibitorCompInfo != null){
                        if("Y".equals(exhibitorDTO.getPartWantYn())){
                            updExhibitorCompInfo.setPartWantYn("1");
                        }else{
                            updExhibitorCompInfo.setPartWantYn("3");
                        }
                        updExhibitorCompInfo.setPrePartYear(exhibitorDTO.getPrePartYear());
                        updExhibitorCompInfo.setCompanyNameKo(exhibitorDTO.getCompanyNameKo());
                        updExhibitorCompInfo.setCompanyHomepage(exhibitorDTO.getCompanyHomepage());
                        updExhibitorCompInfo.setCompanyAddress(exhibitorDTO.getCompanyAddress() + " " + exhibitorDTO.getCompanyAddressDetail());
                        updExhibitorCompInfo.setCompanyCeo(exhibitorDTO.getCompanyCeo());
                        updExhibitorCompInfo.setCompanyFax(exhibitorDTO.getCompanyFax());

                        *//* charge table update *//*
                        List<ChargePersonDTO> chargeList_comp = exhibitorDTO.getChargePersonList();
                        if(chargeList_comp != null){
                            for(int i=0; i<chargeList_comp.size(); i++) {
                                ChargePersonDTO info = chargeList_comp.get(i);
                                if(i == 0){
                                    updExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                }else if(i == 1){
                                    updExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                }else if(i == 2){
                                    updExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                }
                            }
                        }

                        //exhibitor_comp table Update
                        exhibitorCompRst = kibsMapper.updateExhibitorComp(updExhibitorCompInfo);
                    }else{
                        ParticipantCompanyDTO istExhibitorCompInfo = new ParticipantCompanyDTO();
                        String exhibitorCompSeq = kibsMapper.getExhibitorCompSeq();

                        istExhibitorCompInfo.setSeq(exhibitorCompSeq);
                        istExhibitorCompInfo.setExSeq(exhibitorDTO.getSeq());
                        istExhibitorCompInfo.setLang(exhibitorDTO.getLang());
                        istExhibitorCompInfo.setId(exhibitorDTO.getId());
                        istExhibitorCompInfo.setTransferYear(exhibitorDTO.getTransferYear());
                        if("Y".equals(exhibitorDTO.getPartWantYn())){
                            istExhibitorCompInfo.setPartWantYn("1");
                        }else{
                            istExhibitorCompInfo.setPartWantYn("3");
                        }
                        istExhibitorCompInfo.setPrePartYear(exhibitorDTO.getPrePartYear());
                        istExhibitorCompInfo.setCompanyNameKo(exhibitorDTO.getCompanyNameKo());
                        istExhibitorCompInfo.setCompanyHomepage(exhibitorDTO.getCompanyHomepage());
                        istExhibitorCompInfo.setCompanyAddress(exhibitorDTO.getCompanyAddress() + " " + exhibitorDTO.getCompanyAddressDetail());
                        istExhibitorCompInfo.setCompanyCeo(exhibitorDTO.getCompanyCeo());
                        istExhibitorCompInfo.setCompanyFax(exhibitorDTO.getCompanyFax());

                        *//* charge table update *//*
                        List<ChargePersonDTO> chargeList_comp = exhibitorDTO.getChargePersonList();
                        if(chargeList_comp != null){
                            for(int i=0; i<chargeList_comp.size(); i++) {
                                ChargePersonDTO info = chargeList_comp.get(i);
                                if(i == 0){
                                    istExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                }else if(i == 1){
                                    istExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                }else if(i == 2){
                                    istExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                }
                            }
                        }

                        //exhibitor_comp table Insert
                        exhibitorCompRst = kibsMapper.insertExhibitorComp(istExhibitorCompInfo);
                    }

                    if(exhibitorCompRst == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[processUpdateExhibitor exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                    }

                }*/

            }
            responseDTO.setCustomValue(exhibitorSeq);
            //System.out.println(result);
        }catch (Exception e){
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
    public void processUpdateOnlineFileNote(String exhibitorSeq, String onlineNote) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(exhibitorSeq);
        fileDTO.setNote(onlineNote);
        List<String> onlineSeqList = kibsMapper.selectFileSeq(fileDTO);
        for(int i=0; i<onlineSeqList.size(); i++){
            FileDTO updFileDTO = new FileDTO();
            updFileDTO.setId(onlineSeqList.get(i));
            updFileDTO.setNote("productImage" + onlineNote + "_" + (i+1));
            Integer updFileNote = kibsMapper.updateProductImageFileNote(updFileDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitPersonalInfo(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitPersonalInfo : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            Integer ex_result = kibsMapper.updateExhibitPersonalInfo(exhibitorDTO);

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            String eMessage = "[exhibitor mypage] processUpdateExhibitPersonalInfo Error : ";
            resultMessage = String.format(STR_RESULT_H, eMessage, e.getMessage() == null ? "" : e.getMessage());
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
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

        }catch (Exception e){
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

        }catch (Exception e){
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

        }catch (Exception e){
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
            if(!passList.isEmpty()){
                for(PassDTO passDTO : passList){
                    passDTO.setNote(note);
                    passDTO.setId(exhibitorSeq);

                    if(passDTO.getSeq() != null && !"".equals(passDTO.getSeq())){
                        Integer upd_pass_result = kibsMapper.updateExhibitPass(passDTO);
                    }else{
                        Integer ist_pass_result = kibsMapper.insertExhibitPass(passDTO);
                    }
                }
            }

        }catch (Exception e){
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
            if(!giftList.isEmpty()){
                for(GiftDTO giftDTO : giftList){
                    giftDTO.setNote(note);
                    giftDTO.setId(exhibitorSeq);
                    if(giftDTO.getSeq() != null && !giftDTO.getSeq().equals("")){
                        Integer upd_pass_result = kibsMapper.updateExhibitGift(giftDTO);
                    }else{
                        Integer ist_pass_result = kibsMapper.insertExhibitGift(giftDTO);
                    }
                }
            }

            responseDTO.setCustomValue(exhibitorSeq);
        }catch (Exception e){
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

        }catch (Exception e){
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
    public BuyerDTO processSelectBuyerSingle(BuyerDTO buyerDTO) {
        System.out.println("KibsServiceImpl > processSelectBuyerSingle");
        return kibsMapper.selectBuyerSingle(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBuyer(BuyerDTO buyerDTO) {
        System.out.println("KibsServiceImpl > processDeleteBuyer");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(buyerDTO.getSeq() != null){
                result = kibsMapper.deleteBuyer(buyerDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + buyerDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteBuyer ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public ResponseDTO processInsertDirectory(DirectoryDTO directoryDTO) {
        System.out.println("KibsServiceImpl > processInsertDirectory : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step2_8";
            ExhibitorDTO exhibitorDTO = new ExhibitorDTO();
            exhibitorDTO.setId(directoryDTO.getUserId());
            exhibitorDTO.setTransferYear(directoryDTO.getTransferYear());
            exhibitorDTO.setNote(note);
            Integer step2_8_ex_result = kibsMapper.updateExhibitNote(exhibitorDTO);

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

        }catch (Exception e){
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
    public ResponseDTO processUpdateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsServiceImpl > processUpdateExhibitApprovalStatus : ======");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
            String note = "step03";
            exhibitorDTO.setNote(note);
            Integer step03_ex_result = kibsMapper.updateExhibitApprovalStatus(exhibitorDTO);

        }catch (Exception e){
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
    public List<OnlineDTO> processSelectOnlineInfoList(String seq) {
        System.out.println("KibsServiceImpl > processSelectOnlineInfoList");
        return kibsMapper.selectOnlineInfoList(seq);
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

        }catch (Exception e){
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

        }catch (Exception e){
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

        }catch (Exception e){
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

        }catch (Exception e){
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

        }catch (Exception e){
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

        }catch (Exception e){
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

        }catch (Exception e){
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
    public ResponseDTO processDeleteExhibitPass(PassDTO passDTO) {
        System.out.println("KibsServiceImpl > processDeleteExhibitPass");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMapper.deleteExhibitPass(passDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + passDTO.getSeq();
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitPass ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
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
            if(!passList.isEmpty()){
                for(PassDTO passDTO : passList){
                    if(passDTO.getSeq() != null && !passDTO.getSeq().equals("")){
                        Integer upd_pass_result = kibsMapper.updateExhibitPass(passDTO);
                    }else{
                        Integer ist_pass_result = kibsMapper.insertExhibitPass(passDTO);
                    }
                }
            }

        }catch (Exception e){
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
            if(!giftList.isEmpty()){
                for(GiftDTO giftDTO : giftList){
                    if(giftDTO.getSeq() != null && !giftDTO.getSeq().equals("")){
                        Integer upd_pass_result = kibsMapper.updateExhibitGift(giftDTO);
                    }else{
                        Integer ist_pass_result = kibsMapper.insertExhibitGift(giftDTO);
                    }
                }
            }

        }catch (Exception e){
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
            if(giftDTO.getSeq() != null){
                result = kibsMapper.deleteExhibitGift(giftDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + giftDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
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
    public ResponseDTO processDeleteCharge(ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsServiceImpl > processDeleteCharge");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(chargePersonDTO.getSeq() != null){

                result = kibsMapper.deleteCharge(chargePersonDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + chargePersonDTO.getSeq();
                }
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteCharge ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteDisplay(DisplayDTO displayDTO) {
        System.out.println("KibsServiceImpl > processDeleteDisplay");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(displayDTO.getSeq() != null){

                result = kibsMapper.deleteDisplay(displayDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + displayDTO.getSeq();
                }
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteDisplay ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteOnline(OnlineDTO onlineDTO) {
        System.out.println("KibsServiceImpl > processDeleteOnline");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(onlineDTO.getSeq() != null){

                result = kibsMapper.deleteOnline(onlineDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + onlineDTO.getSeq();
                }
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteOnline ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
            if(fileDTO.getId() != null){

                result = kibsMapper.deleteFile(fileDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + fileDTO.getId();
                }
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
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
    public List<PopupDTO> processSelectPopupList(PopupDTO popupDTO) {
        System.out.println("KibsServiceImpl > processSelectPopupList");
        return kibsMapper.selectPopupList(popupDTO);
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

            if(visitorDTO.getSeq() != null && !"".equals(visitorDTO.getSeq())){
                //visitor table Update
                result = kibsMapper.updateVisitor(visitorDTO);

                if("N".equals(visitorDTO.getPartnerYn())){
                    Integer deleteResult = kibsMapper.deletePartnerAll(visitorDTO.getSeq());
                }
            }else{
                //visitor table Insert
                String seq = kibsMapper.getVisitorSeq();
                visitorDTO.setSeq(seq);
                result = kibsMapper.insertVisitor(visitorDTO);
            }

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[processSaveVisitor Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
            }else{
                List<PartnerDTO> partnerList = visitorDTO.getPartner();
                if(!partnerList.isEmpty()){
                    for(PartnerDTO partnerDTO : partnerList){
                        if(partnerDTO.getSeq() != null && !"".equals(partnerDTO.getSeq())){
                            Integer updPartnerResult = kibsMapper.updatePartner(partnerDTO);
                        }else{
                            String partSeq = kibsMapper.getPartnerSeq();
                            partnerDTO.setSeq(partSeq);
                            partnerDTO.setVisitorSeq(visitorDTO.getSeq());
                            Integer istPartnerResult = kibsMapper.insertPartner(partnerDTO);
                        }
                    }
                }
            }

            responseDTO.setCustomValue(visitorDTO.getSeq());
        }catch (Exception e){
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
        try{

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "nngY%2FlASnTg%2FKJlWdupohRX699RJx6xxaPIsfw3WMoP74fL3ElwqhwmIWWYrlYYABP%2B7SUiOfhPGiVY%2BRDSxdg%3D%3D"); /*Service Key*/
            //urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_dg_cd","UTF-8") + "=" + URLEncoder.encode("41", "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
            //urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_cd","UTF-8") + "=" + URLEncoder.encode("117", "UTF-8")); /*시군구(행정자치부 법정동 주소코드 참조)*/
            //urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_emd_cd","UTF-8") + "=" + URLEncoder.encode("101", "UTF-8")); /*읍면동(행정자치부 법정동 주소코드 참조)*/
            urlBuilder.append("&" + URLEncoder.encode("wkpl_nm","UTF-8") + "=" + URLEncoder.encode(searchCompanyRequestDTO.getWkplNm(), "UTF-8")); /*사업장명*/
            //urlBuilder.append("&" + URLEncoder.encode("bzowr_rgst_no","UTF-8") + "=" + URLEncoder.encode("124815", "UTF-8")); /*사업자등록번호(앞에서 6자리)*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
            if(!xmlData.contains("OpenAPI_")){
                JAXBContext jaxbContext = JAXBContext.newInstance(SearchCompanyResponseDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                response = (SearchCompanyResponseDTO) unmarshaller.unmarshal(sr);
            }

        }catch (IOException ioe){

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
        if(dupCheck == 0){
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
            if(partnerDTO.getSeq() != null){

                result = kibsMapper.deletePartner(partnerDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + partnerDTO.getSeq();
                }
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeletePartner ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

}