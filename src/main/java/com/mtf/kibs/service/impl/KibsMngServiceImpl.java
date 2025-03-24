package com.mtf.kibs.service.impl;

import com.google.gson.JsonObject;
import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.mapper.KibsMngMapper;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.util.StringUtil;
import lombok.Setter;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 서비스의 비즈니스 로직을 구현하는 곳입니다.
 * - 해당 *ServiceImpl 에서는 @Service 어노테이션을 필수적으로 사용합니다.
 */
@Service
public class KibsMngServiceImpl implements KibsMngService {

    @Setter(onMethod_ = {@Autowired})
    private KibsMngMapper kibsMngMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public AdminDTO login(AdminDTO adminDTO, HttpSession session) {
        System.out.println("KibsMngServiceImpl > loginCheck : ======");
        AdminDTO admin = kibsMngMapper.login(adminDTO);
        if(admin != null){
            session.setAttribute("status", "logon");
            session.setAttribute("id", admin.getId());
            session.setAttribute("note", admin.getNote());
        }
        return admin;
    }

    @Override
    public void logoutCheck(HttpSession session) {
        System.out.println("KibsMngServiceImpl > logoutCheck : ======");
        session.invalidate(); // 세션 초기화
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processSaveAccessIp(List<AccessDTO> accessDTOList) {
        System.out.println("KibsMngServiceImpl > processSaveAccessIp");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!ObjectUtils.isEmpty(accessDTOList)){

                Integer initRst = kibsMngMapper.deleteAccessIp();

                for(AccessDTO info : accessDTOList){
                    if(!StringUtil.isEmpty(info.getIp())){
                        result = kibsMngMapper.saveAccessIp(info);
                        if(result == 0){
                            resultCode = CommConstants.RESULT_CODE_FAIL;
                            resultMessage = "[Data Save Fail] Seq : " + info.getIp();
                            break;
                        }
                    }
                }
            }else{
                Integer initRst = kibsMngMapper.deleteAccessIp();
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processSaveAccessIp ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processSelectAccessIp(AccessDTO accessDTO) {
        System.out.println("KibsMngServiceImpl > processSelectAccessIp");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(!StringUtil.isEmpty(accessDTO.getIp())){

                result = kibsMngMapper.selectAccessIp(accessDTO);

                responseDTO.setCustomValue(String.valueOf(result));

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Save Fail] Seq : " + accessDTO.getIp();
                }
                //System.out.println(result);

            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Ip Not Found Error]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processSaveAccessIp ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public String processSelectAccessIpList() {
        System.out.println("KibsMngServiceImpl > processSelectAccessIpList");
        return kibsMngMapper.selectAccessIpList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public StatisticsDTO processSelectCompanyCount(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectCompanyCount");
        return kibsMngMapper.selectCompanyCount(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public StatisticsDTO processSelectVisitorCount(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectVisitorCount");
        return kibsMngMapper.selectVisitorCount(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsAccessorDay(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsAccessorDay");
        return kibsMngMapper.selectStatisticsAccessorDay(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsAccessorMonth(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsAccessorMonth");
        return kibsMngMapper.selectStatisticsAccessorMonth(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsAccessorWeek(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsAccessorWeek");
        return kibsMngMapper.selectStatisticsAccessorWeek(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsParticipantCompanyDay(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsParticipantCompanyDay");
        return kibsMngMapper.selectStatisticsParticipantCompanyDay(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsParticipantVisitorDay(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsParticipantVisitorDay");
        return kibsMngMapper.selectStatisticsParticipantVisitorDay(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsParticipantCompanyMonth(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsParticipantCompanyMonth");
        return kibsMngMapper.selectStatisticsParticipantCompanyMonth(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsParticipantVisitorMonth(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsParticipantVisitorMonth");
        return kibsMngMapper.selectStatisticsParticipantVisitorMonth(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsParticipantCompanyWeek(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsParticipantCompanyWeek");
        return kibsMngMapper.selectStatisticsParticipantCompanyWeek(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<StatisticsDTO> processSelectStatisticsParticipantVisitorWeek(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsParticipantVisitorWeek");
        return kibsMngMapper.selectStatisticsParticipantVisitorWeek(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public StatisticsDTO processSelectStatisticsCompanyBooth(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsCompanyBooth");
        return kibsMngMapper.selectStatisticsCompanyBooth(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public StatisticsDTO processSelectStatisticsCompanyFieldPart(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectStatisticsCompanyFieldPart");
        return kibsMngMapper.selectStatisticsCompanyFieldPart(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertExhibitorComp(ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngServiceImpl > processInsertExhibitorComp");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {

            //업체 존재 여부 체크
            Integer result = 0;

            //exhibitor_comp table check
            Integer existCheck = kibsMngMapper.checkExhibitorComp(participantCompanyDTO);

            if(existCheck > 0){
                //exhibitor_comp table Update
                result = kibsMngMapper.updateExhibitorComp(participantCompanyDTO);
            }else{
                //exhibitor_comp table Insert
                result = kibsMngMapper.insertExhibitorComp(participantCompanyDTO);
            }

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[processInsertExhibitorComp exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertExhibitorComp ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ParticipantCompanyDTO> processSelectListExhibitorComp(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectListExhibitorComp");
        return kibsMngMapper.selectListExhibitorComp(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteExhibitorComp(ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorComp");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deleteExhibitorComp(participantCompanyDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + participantCompanyDTO.getSeq();
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorComp ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ParticipantCompanyDTO processSelectExhibitorComp(ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorComp");
        return kibsMngMapper.selectExhibitorComp(participantCompanyDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorComp(ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorComp");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.updateExhibitorComp(participantCompanyDTO);

            String exSeq = participantCompanyDTO.getExSeq();
            //업체 존재 여부 체크
            Integer exResult = 0;

            //exhibitor table check
            String companyNameKo = participantCompanyDTO.getCompanyNameKo();
            String companyCeo = participantCompanyDTO.getCompanyCeo();
            if((companyNameKo != null && !"".equals(companyNameKo)) && companyCeo != null && !"".equals(companyCeo)) {

                String exhibitorSeq = null;

                String match = "[^가-힣0-9a-zA-Z]";
                companyNameKo = companyNameKo.replaceAll(" ", "").replaceAll("(주)", "").replaceAll(match, "");
                companyCeo = companyCeo.replaceAll(" ", "");

                String param = companyNameKo + "_" + companyCeo;

                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("transferYear", participantCompanyDTO.getTransferYear());
                paramMap.put("paramVal", param);
                ExhibitorDTO updExhibitorInfo = kibsMngMapper.getExhibitorInfo(paramMap);
                // seq , lang , id , transfer_year

                if (!ObjectUtils.isEmpty(updExhibitorInfo)) {
                    exhibitorSeq = updExhibitorInfo.getSeq();

                    updExhibitorInfo.setApprovalStatus(CommConstants.APPROVAL_STATUS_ING);
                    updExhibitorInfo.setDeposit("0");
                    updExhibitorInfo.setBalance("100000");
                    updExhibitorInfo.setBoothType("등록비");
                    updExhibitorInfo.setPrcYn("0");
                    updExhibitorInfo.setTaxYn("N");
                    updExhibitorInfo.setCompanyNameKo(participantCompanyDTO.getCompanyNameKo());
                    updExhibitorInfo.setCompanyNameEn("");
                    updExhibitorInfo.setPrcSum(100000);
                    updExhibitorInfo.setPrcVat(10000);
                    updExhibitorInfo.setPrcTotal(110000);
                    updExhibitorInfo.setCompanyAddress(participantCompanyDTO.getCompanyAddress());
                    if (participantCompanyDTO.getCompanyAddress() != null && !"".equals(participantCompanyDTO.getCompanyAddress())) {
                        updExhibitorInfo.setCompanyAddressDetail("");
                    }
                    updExhibitorInfo.setCompanyCeo(participantCompanyDTO.getCompanyCeo());
                    updExhibitorInfo.setCompanyHomepage(participantCompanyDTO.getCompanyHomepage());
                    updExhibitorInfo.setCompanyFax(participantCompanyDTO.getCompanyFax());
                    String prePartYear = participantCompanyDTO.getPrePartYear();
                    if (prePartYear != null && !"".equals(prePartYear)) {
                        prePartYear = prePartYear.replaceAll("(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/)", "").replaceAll(" ", "");

                        String[] prePartYearArr = prePartYear.split(",");
                        StringBuilder prePartYear_rst = new StringBuilder();
                        for (int i = 0; i < prePartYearArr.length; i++) {
                            String year = prePartYearArr[i];
                            if (year.length() == 2) {
                                prePartYear_rst.append("20").append(year);
                            } else if (year.length() == 4) {
                                prePartYear_rst.append(year);
                            }

                            if ((i + 1) != prePartYearArr.length) {
                                prePartYear_rst.append(",");
                            }
                        }

                        updExhibitorInfo.setPrePartYear(prePartYear_rst.toString());
                    }

                    if (participantCompanyDTO.getPartWantYn() != null && !"".equals(participantCompanyDTO.getPartWantYn())) {
                        String partWantYn = "Y";
                        if ("2".equals(participantCompanyDTO.getPartWantYn()) || "3".equals(participantCompanyDTO.getPartWantYn())) {
                            partWantYn = "N";
                        }
                        updExhibitorInfo.setPartWantYn(partWantYn);
                    }

                    //exhibitor table update
                    updExhibitorInfo.setInitRegiPic("EXHI_COMP");
                    updExhibitorInfo.setFinalRegiPic("EXHI_COMP");
                    exResult = kibsMngMapper.updateParticipantCompany(updExhibitorInfo);

                } else {
                    //exhibitor table get Seq
                    exhibitorSeq = kibsMngMapper.getExhibitorSeq();

                    //exhibitor table Insert
                    ExhibitorDTO istExhibitorInfo = new ExhibitorDTO();
                    istExhibitorInfo.setSeq(exhibitorSeq);
                    istExhibitorInfo.setLang(participantCompanyDTO.getLang());
                    istExhibitorInfo.setTransferYear(participantCompanyDTO.getTransferYear());
                    istExhibitorInfo.setApprovalStatus(CommConstants.APPROVAL_STATUS_ING);

                    istExhibitorInfo.setDeposit("0");
                    istExhibitorInfo.setBalance("100000");
                    istExhibitorInfo.setBoothType("등록비");
                    istExhibitorInfo.setRegistrationCnt(1);
                    istExhibitorInfo.setRegistrationFee(100000);
                    istExhibitorInfo.setBoothPrcSum(100000);
                    istExhibitorInfo.setCompanyNameKo(participantCompanyDTO.getCompanyNameKo());
                    istExhibitorInfo.setCompanyNameEn("");
                    istExhibitorInfo.setPrcYn("0");
                    istExhibitorInfo.setTaxYn("N");
                    istExhibitorInfo.setPrcSum(100000);
                    istExhibitorInfo.setPrcVat(10000);
                    istExhibitorInfo.setPrcTotal(110000);

                    istExhibitorInfo.setCompanyAddress(participantCompanyDTO.getCompanyAddress());
                    if (participantCompanyDTO.getCompanyAddress() != null && !"".equals(participantCompanyDTO.getCompanyAddress())) {
                        istExhibitorInfo.setCompanyAddressDetail("");
                    }
                    istExhibitorInfo.setCompanyCeo(participantCompanyDTO.getCompanyCeo());
                    istExhibitorInfo.setCompanyHomepage(participantCompanyDTO.getCompanyHomepage());
                    istExhibitorInfo.setCompanyFax(participantCompanyDTO.getCompanyFax());
                    String prePartYear = participantCompanyDTO.getPrePartYear();
                    if (prePartYear != null && !"".equals(prePartYear)) {
                        prePartYear = prePartYear.replaceAll("(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/)", "").replaceAll(" ", "");

                        String[] prePartYearArr = prePartYear.split(",");
                        StringBuilder prePartYearRst = new StringBuilder();
                        for (int i = 0; i < prePartYearArr.length; i++) {
                            String year = prePartYearArr[i];
                            if (year.length() == 2) {
                                prePartYearRst.append("20").append(year);
                            } else if (year.length() == 4) {
                                prePartYearRst.append(year);
                            }

                            if ((i + 1) != prePartYearArr.length) {
                                prePartYearRst.append(",");
                            }
                        }

                        istExhibitorInfo.setPrePartYear(prePartYearRst.toString());
                    }

                    if (participantCompanyDTO.getPartWantYn() != null && !"".equals(participantCompanyDTO.getPartWantYn())) {
                        String partWantYn = "Y";
                        if ("2".equals(participantCompanyDTO.getPartWantYn()) || "3".equals(participantCompanyDTO.getPartWantYn())) {
                            partWantYn = "N";
                        }
                        istExhibitorInfo.setPartWantYn(partWantYn);
                    }

                    //exhibitor table insert
                    istExhibitorInfo.setInitRegiPic("EXHI_COMP");
                    istExhibitorInfo.setFinalRegiPic("EXHI_COMP");
                    exResult = kibsMngMapper.insertParticipantCompany(istExhibitorInfo);

                }

                if (exResult == 0) {

                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[processInsertExhibitorComp exhibitor Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";

                } else {

                    //exhibitor_comp table check
                    Integer existCheck = kibsMngMapper.checkExhibitorComp(participantCompanyDTO);

                    participantCompanyDTO.setExSeq(exhibitorSeq);
                    if (existCheck > 0) {
                        //exhibitor_comp table Update
                        result = kibsMngMapper.updateExhibitorComp(participantCompanyDTO);
                    } else {
                        //exhibitor_comp table Insert
                        result = kibsMngMapper.insertExhibitorComp(participantCompanyDTO);
                    }

                    if (result == 0) {
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[processInsertExhibitorComp exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                    }

                }
            }


            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail] Seq : " + participantCompanyDTO.getSeq();
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorComp ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MemberDTO> processSelectMemberList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMemberList");
        return kibsMngMapper.selectMemberList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NoticeDTO> processSelectNoticeList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectNoticeList");
        return kibsMngMapper.selectNoticeList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public NoticeDTO processSelectNoticeSingle(NoticeDTO noticeDTO) {
        System.out.println("KibsMngServiceImpl > processSelectNoticeSingle");
        return kibsMngMapper.selectNoticeSingle(noticeDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBoardNotice(NoticeDTO noticeDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteBoardNotice");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(noticeDTO.getId() != null){
                result = kibsMngMapper.deleteBoardNotice(noticeDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + noticeDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteBoardNotice ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateBoardNotice(NoticeDTO noticeDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateBoardNotice");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(noticeDTO.getId())){
                if(StringUtil.isEmpty(noticeDTO.getNoticeGbn()) || "off".equals(noticeDTO.getNoticeGbn())){
                    noticeDTO.setNoticeGbn("0");
                }else{
                    noticeDTO.setNoticeGbn("1");
                }
                if(StringUtil.isEmpty(noticeDTO.getGbn1()) || "off".equals(noticeDTO.getGbn1())){
                    noticeDTO.setGbn1("0");
                }else{
                    noticeDTO.setGbn1("1");
                }
                if(StringUtil.isEmpty(noticeDTO.getGbn2()) || "off".equals(noticeDTO.getGbn2())){
                    noticeDTO.setGbn2("0");
                }else{
                    noticeDTO.setGbn2("1");
                }
                if(StringUtil.isEmpty(noticeDTO.getGbn3()) || "off".equals(noticeDTO.getGbn3())){
                    noticeDTO.setGbn3("0");
                }else{
                    noticeDTO.setGbn3("1");
                }

                String content = noticeDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                noticeDTO.setContent(content);
                result = kibsMngMapper.updateBoardNotice(noticeDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + noticeDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateBoardNotice ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertBoardNotice(NoticeDTO noticeDTO) {
        System.out.println("KibsMngServiceImpl > processInsertBoardNotice");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(noticeDTO.getNoticeGbn()) || "off".equals(noticeDTO.getNoticeGbn())){
                noticeDTO.setNoticeGbn("0");
            }else{
                noticeDTO.setNoticeGbn("1");
            }
            if(StringUtil.isEmpty(noticeDTO.getGbn1()) || "off".equals(noticeDTO.getGbn1())){
                noticeDTO.setGbn1("0");
            }else{
                noticeDTO.setGbn1("1");
            }
            if(StringUtil.isEmpty(noticeDTO.getGbn2()) || "off".equals(noticeDTO.getGbn2())){
                noticeDTO.setGbn2("0");
            }else{
                noticeDTO.setGbn2("1");
            }
            if(StringUtil.isEmpty(noticeDTO.getGbn3()) || "off".equals(noticeDTO.getGbn3())){
                noticeDTO.setGbn3("0");
            }else{
                noticeDTO.setGbn3("1");
            }

            String getId = kibsMngMapper.getNoticeId();
            noticeDTO.setId(getId);

            String content = noticeDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            noticeDTO.setContent(content);
            result = kibsMngMapper.insertBoardNotice(noticeDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertBoardNotice ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<NewsletterDTO> processSelectNewsletterList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectNewsletterKoList");
        return kibsMngMapper.selectNewsletterList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public NewsletterDTO processSelectNewsletterSingle(NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngServiceImpl > processSelectNewsletterSingle");
        return kibsMngMapper.selectNewsletterSingle(newsletterDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteNewsletter(NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteNewsletter");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(newsletterDTO.getId() != null){
                result = kibsMngMapper.deleteNewsletter(newsletterDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + newsletterDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteNewsletter ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateNewsletter(NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateNewsletter");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(newsletterDTO.getId())){
                if(StringUtil.isEmpty(newsletterDTO.getNoticeGbn()) || "off".equals(newsletterDTO.getNoticeGbn())){
                    newsletterDTO.setNoticeGbn("0");
                }else{
                    newsletterDTO.setNoticeGbn("1");
                }
                if(StringUtil.isEmpty(newsletterDTO.getGbn1()) || "off".equals(newsletterDTO.getGbn1())){
                    newsletterDTO.setGbn1("0");
                }else{
                    newsletterDTO.setGbn1("1");
                }
                if(StringUtil.isEmpty(newsletterDTO.getGbn2()) || "off".equals(newsletterDTO.getGbn2())){
                    newsletterDTO.setGbn2("0");
                }else{
                    newsletterDTO.setGbn2("1");
                }
                if(StringUtil.isEmpty(newsletterDTO.getGbn3()) || "off".equals(newsletterDTO.getGbn3())){
                    newsletterDTO.setGbn3("0");
                }else{
                    newsletterDTO.setGbn3("1");
                }

                String content = newsletterDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                newsletterDTO.setContent(content);

                result = kibsMngMapper.updateNewsletter(newsletterDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + newsletterDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateNewsletter ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertNewsletter(NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngServiceImpl > processInsertNewsletter");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(newsletterDTO.getNoticeGbn()) || "off".equals(newsletterDTO.getNoticeGbn())){
                newsletterDTO.setNoticeGbn("0");
            }else{
                newsletterDTO.setNoticeGbn("1");
            }
            if(StringUtil.isEmpty(newsletterDTO.getGbn1()) || "off".equals(newsletterDTO.getGbn1())){
                newsletterDTO.setGbn1("0");
            }else{
                newsletterDTO.setGbn1("1");
            }
            if(StringUtil.isEmpty(newsletterDTO.getGbn2()) || "off".equals(newsletterDTO.getGbn2())){
                newsletterDTO.setGbn2("0");
            }else{
                newsletterDTO.setGbn2("1");
            }
            if(StringUtil.isEmpty(newsletterDTO.getGbn3()) || "off".equals(newsletterDTO.getGbn3())){
                newsletterDTO.setGbn3("0");
            }else{
                newsletterDTO.setGbn3("1");
            }

            String getId = kibsMngMapper.getNewsletterId();
            newsletterDTO.setId(getId);

            String content = newsletterDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            newsletterDTO.setContent(content);

            result = kibsMngMapper.insertNewsletter(newsletterDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertNewsletter ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PressDTO> processSelectPressList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectPressList");
        return kibsMngMapper.selectPressList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public PressDTO processSelectPressSingle(PressDTO pressDTO) {
        System.out.println("KibsMngServiceImpl > processSelectPressSingle");
        return kibsMngMapper.selectPressSingle(pressDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePress(PressDTO pressDTO) {
        System.out.println("KibsMngServiceImpl > processDeletePress");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(pressDTO.getId() != null){
                result = kibsMngMapper.deletePress(pressDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + pressDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeletePress ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePress(PressDTO pressDTO) {
        System.out.println("KibsMngServiceImpl > processUpdatePress");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(pressDTO.getId())){
                if(StringUtil.isEmpty(pressDTO.getNoticeGbn()) || "off".equals(pressDTO.getNoticeGbn())){
                    pressDTO.setNoticeGbn("0");
                }else{
                    pressDTO.setNoticeGbn("1");
                }
                if(StringUtil.isEmpty(pressDTO.getGbn1()) || "off".equals(pressDTO.getGbn1())){
                    pressDTO.setGbn1("0");
                }else{
                    pressDTO.setGbn1("1");
                }
                if(StringUtil.isEmpty(pressDTO.getGbn2()) || "off".equals(pressDTO.getGbn2())){
                    pressDTO.setGbn2("0");
                }else{
                    pressDTO.setGbn2("1");
                }
                if(StringUtil.isEmpty(pressDTO.getGbn3()) || "off".equals(pressDTO.getGbn3())){
                    pressDTO.setGbn3("0");
                }else{
                    pressDTO.setGbn3("1");
                }

                String content = pressDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                pressDTO.setContent(content);

                result = kibsMngMapper.updatePress(pressDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + pressDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdatePress ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertPress(PressDTO pressDTO) {
        System.out.println("KibsMngServiceImpl > processInsertPress");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(pressDTO.getNoticeGbn()) || "off".equals(pressDTO.getNoticeGbn())){
                pressDTO.setNoticeGbn("0");
            }else{
                pressDTO.setNoticeGbn("1");
            }
            if(StringUtil.isEmpty(pressDTO.getGbn1()) || "off".equals(pressDTO.getGbn1())){
                pressDTO.setGbn1("0");
            }else{
                pressDTO.setGbn1("1");
            }
            if(StringUtil.isEmpty(pressDTO.getGbn2()) || "off".equals(pressDTO.getGbn2())){
                pressDTO.setGbn2("0");
            }else{
                pressDTO.setGbn2("1");
            }
            if(StringUtil.isEmpty(pressDTO.getGbn3()) || "off".equals(pressDTO.getGbn3())){
                pressDTO.setGbn3("0");
            }else{
                pressDTO.setGbn3("1");
            }

            String getId = kibsMngMapper.getPressId();
            pressDTO.setId(getId);

            String content = pressDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            pressDTO.setContent(content);
            result = kibsMngMapper.insertPress(pressDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertPress ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FaqDTO> processSelectFaqList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectFaqList");
        return kibsMngMapper.selectFaqList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FaqDTO processSelectFaqSingle(FaqDTO faqDTO) {
        System.out.println("KibsMngServiceImpl > processSelectFaqSingle");
        return kibsMngMapper.selectFaqSingle(faqDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteFaq(FaqDTO faqDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteFaq");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(faqDTO.getId() != null){
                result = kibsMngMapper.deleteFaq(faqDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + faqDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteFaq ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateFaq(FaqDTO faqDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateFaq");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(faqDTO.getId())){

                String content = faqDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                faqDTO.setContent(content);
                result = kibsMngMapper.updateFaq(faqDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + faqDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateFaq ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertFaq(FaqDTO faqDTO) {
        System.out.println("KibsMngServiceImpl > processInsertFaq");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            String getId = kibsMngMapper.getFaqId();
            faqDTO.setId(getId);
            String content = faqDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            faqDTO.setContent(content);
            result = kibsMngMapper.insertFaq(faqDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertFaq ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ColumnDTO> processSelectColumnList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectColumnList");
        return kibsMngMapper.selectColumnList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ColumnDTO processSelectColumnSingle(ColumnDTO columnDTO) {
        System.out.println("KibsMngServiceImpl > processSelectColumnSingle");
        return kibsMngMapper.selectColumnSingle(columnDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteColumn(ColumnDTO columnDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteColumn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(columnDTO.getId() != null){
                result = kibsMngMapper.deleteColumn(columnDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + columnDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteColumn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateColumn(ColumnDTO columnDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateColumn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(columnDTO.getId())){
                if(StringUtil.isEmpty(columnDTO.getGbn1()) || "off".equals(columnDTO.getGbn1())){
                    columnDTO.setGbn1("0");
                }else{
                    columnDTO.setGbn1("1");
                }
                if(StringUtil.isEmpty(columnDTO.getGbn2()) || "off".equals(columnDTO.getGbn2())){
                    columnDTO.setGbn2("0");
                }else{
                    columnDTO.setGbn2("1");
                }
                if(StringUtil.isEmpty(columnDTO.getGbn3()) || "off".equals(columnDTO.getGbn3())){
                    columnDTO.setGbn3("0");
                }else{
                    columnDTO.setGbn3("1");
                }

                String content = columnDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                columnDTO.setContent(content);

                result = kibsMngMapper.updateColumn(columnDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + columnDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateColumn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertColumn(ColumnDTO columnDTO) {
        System.out.println("KibsMngServiceImpl > processInsertColumn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(columnDTO.getGbn1()) || "off".equals(columnDTO.getGbn1())){
                columnDTO.setGbn1("0");
            }else{
                columnDTO.setGbn1("1");
            }
            if(StringUtil.isEmpty(columnDTO.getGbn2()) || "off".equals(columnDTO.getGbn2())){
                columnDTO.setGbn2("0");
            }else{
                columnDTO.setGbn2("1");
            }
            if(StringUtil.isEmpty(columnDTO.getGbn3()) || "off".equals(columnDTO.getGbn3())){
                columnDTO.setGbn3("0");
            }else{
                columnDTO.setGbn3("1");
            }

            String getId = kibsMngMapper.getColumnId();
            columnDTO.setId(getId);

            String content = columnDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            columnDTO.setContent(content);

            result = kibsMngMapper.insertColumn(columnDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertColumn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BrochureDTO> processSelectBrochureList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBrochureList");
        return kibsMngMapper.selectBrochureList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public BrochureDTO processSelectBrochureSingle(BrochureDTO brochureDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBrochureSingle");
        return kibsMngMapper.selectBrochureSingle(brochureDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBrochure(BrochureDTO brochureDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteBrochure");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(brochureDTO.getId() != null){
                result = kibsMngMapper.deleteBrochure(brochureDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + brochureDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteBrochure ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateBrochure(BrochureDTO brochureDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateBrochure");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(brochureDTO.getId())){
                if(StringUtil.isEmpty(brochureDTO.getNoticeGbn()) || "off".equals(brochureDTO.getNoticeGbn())){
                    brochureDTO.setNoticeGbn("0");
                }else{
                    brochureDTO.setNoticeGbn("1");
                }
                if(StringUtil.isEmpty(brochureDTO.getGbn1()) || "off".equals(brochureDTO.getGbn1())){
                    brochureDTO.setGbn1("0");
                }else{
                    brochureDTO.setGbn1("1");
                }
                if(StringUtil.isEmpty(brochureDTO.getGbn2()) || "off".equals(brochureDTO.getGbn2())){
                    brochureDTO.setGbn2("0");
                }else{
                    brochureDTO.setGbn2("1");
                }
                if(StringUtil.isEmpty(brochureDTO.getGbn3()) || "off".equals(brochureDTO.getGbn3())){
                    brochureDTO.setGbn3("0");
                }else{
                    brochureDTO.setGbn3("1");
                }

                result = kibsMngMapper.updateBrochure(brochureDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + brochureDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateBrochure ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertBrochure(BrochureDTO brochureDTO) {
        System.out.println("KibsMngServiceImpl > processInsertBrochure");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(brochureDTO.getNoticeGbn()) || "off".equals(brochureDTO.getNoticeGbn())){
                brochureDTO.setNoticeGbn("0");
            }else{
                brochureDTO.setNoticeGbn("1");
            }
            if(StringUtil.isEmpty(brochureDTO.getGbn1()) || "off".equals(brochureDTO.getGbn1())){
                brochureDTO.setGbn1("0");
            }else{
                brochureDTO.setGbn1("1");
            }
            if(StringUtil.isEmpty(brochureDTO.getGbn2()) || "off".equals(brochureDTO.getGbn2())){
                brochureDTO.setGbn2("0");
            }else{
                brochureDTO.setGbn2("1");
            }
            if(StringUtil.isEmpty(brochureDTO.getGbn3()) || "off".equals(brochureDTO.getGbn3())){
                brochureDTO.setGbn3("0");
            }else{
                brochureDTO.setGbn3("1");
            }

            String getId = kibsMngMapper.getBrochureId();
            brochureDTO.setId(getId);
            result = kibsMngMapper.insertBrochure(brochureDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertBrochure ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DataroomDTO> processSelectDataroomList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectDataroomList");
        return kibsMngMapper.selectDataroomList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public DataroomDTO processSelectDataroomSingle(DataroomDTO dataroomDTO) {
        System.out.println("KibsMngServiceImpl > processSelectDataroomSingle");
        return kibsMngMapper.selectDataroomSingle(dataroomDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteDataroom(DataroomDTO dataroomDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteDataroom");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(dataroomDTO.getId() != null){
                result = kibsMngMapper.deleteDataroom(dataroomDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + dataroomDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteDataroom ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateDataroom(DataroomDTO dataroomDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateDataroom");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(dataroomDTO.getId())){
                if(StringUtil.isEmpty(dataroomDTO.getSiteGbn()) || "off".equals(dataroomDTO.getSiteGbn())){
                    dataroomDTO.setSiteGbn("0");
                }else{
                    dataroomDTO.setSiteGbn("1");
                }

                result = kibsMngMapper.updateDataroom(dataroomDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + dataroomDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateDataroom ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertDataroom(DataroomDTO dataroomDTO) {
        System.out.println("KibsMngServiceImpl > processInsertDataroom");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(dataroomDTO.getSiteGbn()) || "off".equals(dataroomDTO.getSiteGbn())){
                dataroomDTO.setSiteGbn("0");
            }else{
                dataroomDTO.setSiteGbn("1");
            }

            String getId = kibsMngMapper.getDataroomId();
            dataroomDTO.setId(getId);
            result = kibsMngMapper.insertDataroom(dataroomDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertDataroom ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<GalleryDTO> processSelectGalleryList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectGalleryList");
        return kibsMngMapper.selectGalleryList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public GalleryDTO processSelectGallerySingle(GalleryDTO galleryDTO) {
        System.out.println("KibsMngServiceImpl > processSelectGallerySingle");
        return kibsMngMapper.selectGallerySingle(galleryDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteGallery(GalleryDTO galleryDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteGallery");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(galleryDTO.getId() != null){
                result = kibsMngMapper.deleteGallery(galleryDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + galleryDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteGallery ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateGallery(GalleryDTO galleryDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateGallery");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(galleryDTO.getId())){
                if(StringUtil.isEmpty(galleryDTO.getSiteGbn()) || "off".equals(galleryDTO.getSiteGbn())){
                    galleryDTO.setSiteGbn("0");
                }else{
                    galleryDTO.setSiteGbn("1");
                }

                String content = galleryDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                galleryDTO.setContent(content);

                result = kibsMngMapper.updateGallery(galleryDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + galleryDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateGallery ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertGallery(GalleryDTO galleryDTO) {
        System.out.println("KibsMngServiceImpl > processInsertGallery");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(galleryDTO.getSiteGbn()) || "off".equals(galleryDTO.getSiteGbn())){
                galleryDTO.setSiteGbn("0");
            }else{
                galleryDTO.setSiteGbn("1");
            }

            String getId = kibsMngMapper.getGalleryId();
            galleryDTO.setId(getId);

            String content = galleryDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            galleryDTO.setContent(content);

            result = kibsMngMapper.insertGallery(galleryDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertGallery ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<KibstvDTO> processSelectKibstvList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectKibstvList");
        return kibsMngMapper.selectKibstvList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public KibstvDTO processSelectKibstvSingle(KibstvDTO kibstvDTO) {
        System.out.println("KibsMngServiceImpl > processSelectKibstvSingle");
        return kibsMngMapper.selectKibstvSingle(kibstvDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteKibstv(KibstvDTO kibstvDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteKibstv");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(kibstvDTO.getId() != null){
                result = kibsMngMapper.deleteKibstv(kibstvDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + kibstvDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteKibstv ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateKibstv(KibstvDTO kibstvDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateKibstv");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(kibstvDTO.getId())){
                if(StringUtil.isEmpty(kibstvDTO.getSiteGbn()) || "off".equals(kibstvDTO.getSiteGbn())){
                    kibstvDTO.setSiteGbn("0");
                }else{
                    kibstvDTO.setSiteGbn("1");
                }

                String content = kibstvDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                kibstvDTO.setContent(content);

                result = kibsMngMapper.updateKibstv(kibstvDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + kibstvDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateKibstv ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertKibstv(KibstvDTO kibstvDTO) {
        System.out.println("KibsMngServiceImpl > processInsertKibstv");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            if(StringUtil.isEmpty(kibstvDTO.getSiteGbn()) || "off".equals(kibstvDTO.getSiteGbn())){
                kibstvDTO.setSiteGbn("0");
            }else{
                kibstvDTO.setSiteGbn("1");
            }

            String content = kibstvDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            kibstvDTO.setContent(content);

            result = kibsMngMapper.insertKibstv(kibstvDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertKibstv ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileResponseDTO processInsertFileInfo(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processInsertFileInfo");
        FileResponseDTO responseDTO = new FileResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            String fileId = kibsMngMapper.getFileId();
            fileDTO.setId(fileId);
            result = kibsMngMapper.insertFileInfo(fileDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            responseDTO.setFileId(fileId);
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertFileInfo ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileResponseDTO processUpdateFileUseN(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateFileUseN");
        FileResponseDTO responseDTO = new FileResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(fileDTO.getId())){

                result = kibsMngMapper.updateFileUseN(fileDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + fileDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateFileUseN ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectFileList(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processSelectFileList");
        return kibsMngMapper.selectFileList(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileDTO processSelectFileInfo(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processSelectFileInfo");
        return kibsMngMapper.selectFileInfo(fileDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public FileResponseDTO processUpdateFileInfo(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateFileInfo");
        FileResponseDTO responseDTO = new FileResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(fileDTO.getId())){

                result = kibsMngMapper.updateFileInfo(fileDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + fileDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateFileInfo ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateFileUserId(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateFileUserId");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(fileDTO.getId())){

                result = kibsMngMapper.updateFileUserId(fileDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + fileDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateFileUserId ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectListExhibitor(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectListExhibitor");
        return kibsMngMapper.selectListExhibitor(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorSingle");
        return kibsMngMapper.selectExhibitorSingle(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertParticipantCompany(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processInsertParticipantCompany");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            Integer companyCheck = kibsMngMapper.checkExhibitor(exhibitorDTO);

            if(companyCheck == 0){ // 없으면 insert

                /* exhibitor table seq get */
                String exhibitorSeq = kibsMngMapper.getExhibitorSeq();
                exhibitorDTO.setSeq(exhibitorSeq);

                exhibitorDTO.setDeposit("0");
                exhibitorDTO.setBalance("100000");
                exhibitorDTO.setBoothType("등록비");
                exhibitorDTO.setRegistrationCnt(1);
                exhibitorDTO.setRegistrationFee(100000);
                exhibitorDTO.setBoothPrcSum(100000);
                exhibitorDTO.setPrcYn("0");
                exhibitorDTO.setTaxYn("N");
                exhibitorDTO.setPrcSum(100000);
                exhibitorDTO.setPrcVat(10000);
                exhibitorDTO.setPrcTotal(110000);

                /* exhibitor table insert */
                exhibitorDTO.setInitRegiPic("EXHIBIT_AD");
                exhibitorDTO.setFinalRegiPic("EXHIBIT_AD");
                result = kibsMngMapper.insertParticipantCompany(exhibitorDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Insert Fail]";
                }else {
                    /* charge table insert */
                    List<ChargePersonDTO> chargeList = exhibitorDTO.getChargePersonList();
                    if(chargeList != null) {
                        for (int i = 0; i < chargeList.size(); i++) {
                            ChargePersonDTO request = chargeList.get(i);
                            request.setId(exhibitorSeq);
                            Integer chargeResult = kibsMngMapper.insertParticipantCompanyCharge(request);
                        }
                    }

                    /* display table insert */
                    List<DisplayDTO> displayList = exhibitorDTO.getDisplayList();
                    if (displayList != null) {
                        for (int i = 0; i < displayList.size(); i++) {
                            DisplayDTO request = displayList.get(i);
                            request.setId(exhibitorSeq);
                            Integer displayResult = kibsMngMapper.insertParticipantCompanyDisplay(request);
                        }
                    }

                    /* online table insert */
                    List<OnlineDTO> onlineList = exhibitorDTO.getOnlineExhibitList();
                    if (onlineList != null) {
                        for (int i = 0; i < onlineList.size(); i++) {
                            OnlineDTO request = onlineList.get(i);
                            request.setId(exhibitorSeq);
                            Integer onlineResult = kibsMngMapper.insertParticipantCompanyOnline(request);
                        }
                    }

                    /* buyer table insert */
                    List<BuyerDTO> buyerList = exhibitorDTO.getBuyerList();
                    if (buyerList != null) {
                        for (int i = 0; i < buyerList.size(); i++) {
                            BuyerDTO request = buyerList.get(i);
                            request.setId(exhibitorSeq);
                            Integer buyerResult = kibsMngMapper.insertParticipantCompanyBuyer(request);
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
                        ParticipantCompanyDTO updExhibitorCompInfo = kibsMngMapper.getExhibitorCompInfo(paramMap);
                        //seq , ex_seq , lang , id , transfer_year

                        if(!ObjectUtils.isEmpty(updExhibitorCompInfo)){
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
                            exhibitorCompRst = kibsMngMapper.updateExhibitorComp(updExhibitorCompInfo);
                        }else{
                            ParticipantCompanyDTO istExhibitorCompInfo = new ParticipantCompanyDTO();
                            String exhibitorCompSeq = kibsMngMapper.getExhibitorCompSeq();

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
                            exhibitorCompRst = kibsMngMapper.insertExhibitorComp(istExhibitorCompInfo);
                        }

                        if(exhibitorCompRst == 0){
                            resultCode = CommConstants.RESULT_CODE_FAIL;
                            resultMessage = "[processUpdateParticipantCompany exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                        }

                    }*/
                }
                responseDTO.setCustomValue(exhibitorSeq);
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[ID : " + exhibitorDTO.getId() + "][연도 : " + exhibitorDTO.getTransferYear() + "] 데이터 중복";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertParticipantCompany ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateParticipantCompany(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateParticipantCompany");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        Integer exhibitorCompRst = 0;

        try {

            /* exhibitor table seq get */
            String exhibitorSeq = exhibitorDTO.getSeq();

            /* exhibitor table update */
            result = kibsMngMapper.updateParticipantCompany(exhibitorDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }else {
                /* charge table update */
                List<ChargePersonDTO> chargeList = exhibitorDTO.getChargePersonList();
                if(chargeList != null){
                    for(ChargePersonDTO request : chargeList) {
                        Integer chargeCheck = kibsMngMapper.checkParticipantCompanyCharge(request);
                        if (chargeCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyCharge(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyCharge(request);
                        }
                    }
                }

                /* display table update */
                List<DisplayDTO> displayList = exhibitorDTO.getDisplayList();
                if (displayList != null) {
                    for (DisplayDTO request : displayList) {
                        Integer displayCheck = kibsMngMapper.checkParticipantCompanyDisplay(request);
                        if (displayCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyDisplay(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyDisplay(request);
                        }
                    }
                }

                /* online table update */
                List<OnlineDTO> onlineList = exhibitorDTO.getOnlineExhibitList();
                if (onlineList != null) {
                    for (OnlineDTO request : onlineList) {
                        Integer onlineCheck = kibsMngMapper.checkParticipantCompanyOnline(request);
                        if (onlineCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyOnline(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyOnline(request);
                        }

                        processUpdateOnlineFileNote(exhibitorSeq, request.getNote());
                    }
                }

                /* buyer table update */
                List<BuyerDTO> buyerList = exhibitorDTO.getBuyerList();
                if (buyerList != null) {
                    for (BuyerDTO request : buyerList) {
                        Integer buyerCheck = kibsMngMapper.checkParticipantCompanyBuyer(request);
                        if (buyerCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyBuyer(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyBuyer(request);
                        }
                    }
                }

                /* exhibitor_comp update */
                //exhibitor table check
                /*String companyNameKo = exhibitorDTO.getCompanyNameKo();
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
                    ParticipantCompanyDTO updExhibitorCompInfo = kibsMngMapper.getExhibitorCompInfo(paramMap);
                    //seq , ex_seq , lang , id , transfer_year

                    if(!ObjectUtils.isEmpty(updExhibitorCompInfo)) {
                        if ("Y".equals(exhibitorDTO.getPartWantYn())) {
                            updExhibitorCompInfo.setPartWantYn("1");
                        } else {
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
                        if (chargeList_comp != null) {
                            for (int i = 0; i < chargeList_comp.size(); i++) {
                                ChargePersonDTO info = chargeList_comp.get(i);
                                if (i == 0) {
                                    updExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                } else if (i == 1) {
                                    updExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                } else if (i == 2) {
                                    updExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                }
                            }
                        }

                        //exhibitor_comp table Update
                        exhibitorCompRst = kibsMngMapper.updateExhibitorComp(updExhibitorCompInfo);
                    }else{
                        ParticipantCompanyDTO istExhibitorCompInfo = new ParticipantCompanyDTO();
                        String exhibitorCompSeq = kibsMngMapper.getExhibitorCompSeq();
                        istExhibitorCompInfo.setSeq(exhibitorCompSeq);
                        istExhibitorCompInfo.setExSeq(exhibitorDTO.getSeq());
                        istExhibitorCompInfo.setLang(exhibitorDTO.getLang());
                        istExhibitorCompInfo.setId(exhibitorDTO.getId());
                        istExhibitorCompInfo.setTransferYear(exhibitorDTO.getTransferYear());
                        if ("Y".equals(exhibitorDTO.getPartWantYn())) {
                            istExhibitorCompInfo.setPartWantYn("1");
                        } else {
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
                        if (chargeList_comp != null) {
                            for (int i = 0; i < chargeList_comp.size(); i++) {
                                ChargePersonDTO info = chargeList_comp.get(i);
                                if (i == 0) {
                                    istExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                } else if (i == 1) {
                                    istExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                } else if (i == 2) {
                                    istExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                }
                            }
                        }

                        //exhibitor_comp table Insert
                        exhibitorCompRst = kibsMngMapper.insertExhibitorComp(istExhibitorCompInfo);
                    }

                    if(exhibitorCompRst == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[processUpdateParticipantCompany exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                    }

                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[processUpdateParticipantCompany PARAM IS NULL OR EMPTY] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                }*/

            }
            responseDTO.setCustomValue(exhibitorSeq);
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateParticipantCompany ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    private void processUpdateOnlineFileNote(String exhibitorSeq, String onlineNote) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(exhibitorSeq);
        fileDTO.setNote(onlineNote);
        List<String> onlineSeqList = kibsMngMapper.selectFileSeq(fileDTO);
        for(int i=0; i<onlineSeqList.size(); i++){
            FileDTO updFileDTO = new FileDTO();
            updFileDTO.setId(onlineSeqList.get(i));
            updFileDTO.setNote("productImage" + onlineNote + "_" + (i+1));
            Integer updFileNote = kibsMngMapper.updateProductImageFileNote(updFileDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteParticipantCompany(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteParticipantCompany");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(exhibitorDTO.getSeq() != null){
                result = kibsMngMapper.deleteParticipantCompany(exhibitorDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + exhibitorDTO.getSeq();
                }else{
                    /* display */
                    DisplayDTO displayDTO = new DisplayDTO();
                    displayDTO.setId(exhibitorDTO.getSeq());
                    Integer displayResult = kibsMngMapper.deleteParticipantCompanyDisplay(displayDTO);

                    /* online */
                    OnlineDTO onlineDTO = new OnlineDTO();
                    onlineDTO.setId(exhibitorDTO.getSeq());
                    Integer onlineResult = kibsMngMapper.deleteParticipantCompanyOnline(onlineDTO);

                    /* buyer */
                    BuyerDTO buyerDTO = new BuyerDTO();
                    buyerDTO.setId(exhibitorDTO.getSeq());
                    Integer buyerResult = kibsMngMapper.deleteParticipantCompanyBuyer(buyerDTO);
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteParticipantCompany ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<VisitorDTO> processSelectVisitorList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectVisitorList");
        return kibsMngMapper.selectVisitorList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public VisitorDTO processSelectVisitorSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectVisitorSingle");
        return kibsMngMapper.selectVisitorSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateVisitor(VisitorDTO visitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateVisitor");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {

            //업체 존재 여부 체크
            Integer result = 0;

            if(visitorDTO.getSeq() != null && !"".equals(visitorDTO.getSeq())){
                //visitor table Update
                result = kibsMngMapper.updateVisitor(visitorDTO);
            }else{
                //visitor table Insert
                String seq = kibsMngMapper.getVisitorSeq();
                visitorDTO.setSeq(seq);
                result = kibsMngMapper.insertVisitor(visitorDTO);
            }

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[processUpdateVisitor Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
            }else{
                List<PartnerDTO> partnerList = visitorDTO.getPartner();
                if(!partnerList.isEmpty()){
                    for(PartnerDTO partnerDTO : partnerList){
                        if(partnerDTO.getSeq() != null && !"".equals(partnerDTO.getSeq())){
                            Integer updPartnerResult = kibsMngMapper.updatePartner(partnerDTO);
                        }else{
                            String partSeq = kibsMngMapper.getPartnerSeq();
                            partnerDTO.setSeq(partSeq);
                            partnerDTO.setVisitorSeq(visitorDTO.getSeq());
                            Integer istPartnerResult = kibsMngMapper.insertPartner(partnerDTO);
                        }
                    }
                }
            }

            responseDTO.setCustomValue(visitorDTO.getSeq());
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateVisitor ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteVisitor(VisitorDTO visitorDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteVisitor");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(visitorDTO.getSeq() != null){
                result = kibsMngMapper.deleteVisitor(visitorDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + visitorDTO.getSeq();
                }else{
                    PartnerDTO partnerDTO = new PartnerDTO();
                    partnerDTO.setVisitorSeq(visitorDTO.getSeq());
                    Integer dltPartnerResult = kibsMngMapper.deletePartner(partnerDTO);
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteVisitor ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PartnerDTO> processSelectPartnerList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectPartnerList");
        return kibsMngMapper.selectPartnerList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePartner(PartnerDTO partnerDTO) {
        System.out.println("KibsMngServiceImpl > processDeletePartner");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(partnerDTO.getSeq() != null){
                result = kibsMngMapper.deletePartner(partnerDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + partnerDTO.getSeq();
                }
                //System.out.println(result);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DisplayDTO> processSelectDisplayList(DisplayDTO displayDTO) {
        System.out.println("KibsMngServiceImpl > processSelectDisplayList");
        return kibsMngMapper.selectDisplayList(displayDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineList(OnlineDTO onlineDTO) {
        System.out.println("KibsMngServiceImpl > processSelectOnlineList");
        return kibsMngMapper.selectOnlineList(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectMngOnlineList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMngOnlineList");
        return kibsMngMapper.selectMngOnlineList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateViewYn(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateViewYn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(exhibitorDTO.getSeq() != null && !exhibitorDTO.getSeq().equals("")){

                result = kibsMngMapper.updateViewYn(exhibitorDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Seq : " + exhibitorDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateViewYn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerDTO> processSelectBuyerList(BuyerDTO buyerDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBuyerList");
        return kibsMngMapper.selectBuyerList(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public BuyerDTO processSelectBuyerSingle(BuyerDTO buyerDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBuyerSingle");
        return kibsMngMapper.selectBuyerSingle(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteBuyer(BuyerDTO buyerDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteBuyer");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(buyerDTO.getSeq() != null){
                result = kibsMngMapper.deleteBuyer(buyerDTO);
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
    public ResponseDTO processDeleteCharge(ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteCharge");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(chargePersonDTO.getSeq() != null){

                result = kibsMngMapper.deleteCharge(chargePersonDTO);
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
    public ResponseDTO processDeleteOnline(OnlineDTO onlineDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteOnline");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(onlineDTO.getSeq() != null){

                result = kibsMngMapper.deleteOnline(onlineDTO);
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
    public ResponseDTO processDeleteDisplay(DisplayDTO displayDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteDisplay");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(displayDTO.getSeq() != null){

                result = kibsMngMapper.deleteDisplay(displayDTO);
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
    public ResponseDTO processDeleteFile(FileDTO fileDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteFile");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(fileDTO.getId() != null){

                result = kibsMngMapper.deleteFile(fileDTO);
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
    public List<EdmHstDTO> processSelectEdmHstList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectEdmHstList");
        return kibsMngMapper.selectEdmHstList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertEdmHst(EdmHstDTO edmHstDTO) {
        System.out.println("KibsMngServiceImpl > processInsertEdmHst");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {
            String content = edmHstDTO.getSendContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
            edmHstDTO.setSendContent(content);

            Integer result = kibsMngMapper.insertEdmHst(edmHstDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertEdmHst ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteEdmHst(EdmHstDTO edmHstDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteEdmHst");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {
            Integer result = kibsMngMapper.deleteEdmHst(edmHstDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteEdmHst ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectListTransferCompany(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectListTransferCompany");
        return kibsMngMapper.selectListTransferCompany(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<VisitorDTO> processSelectListTransferVisitor(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectListTransferVisitor");
        return kibsMngMapper.selectListTransferVisitor(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateTransferCompany(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateTransferCompany");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        Integer exhibitorCompRst = 0;

        try {

            /* exhibitor table seq get */
            String exhibitorSeq = exhibitorDTO.getSeq();

            /* exhibitor table update */
            result = kibsMngMapper.updateParticipantCompany(exhibitorDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }else {
                /* charge table update */
                List<ChargePersonDTO> chargeList = exhibitorDTO.getChargePersonList();
                if(chargeList != null){
                    for(ChargePersonDTO request : chargeList) {
                        Integer chargeCheck = kibsMngMapper.checkParticipantCompanyCharge(request);
                        if (chargeCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyCharge(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyCharge(request);
                        }
                    }
                }

                /* display table update */
                List<DisplayDTO> displayList = exhibitorDTO.getDisplayList();
                if (displayList != null) {
                    for (DisplayDTO request : displayList) {
                        Integer displayCheck = kibsMngMapper.checkParticipantCompanyDisplay(request);
                        if (displayCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyDisplay(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyDisplay(request);
                        }
                    }
                }

                /* online table update */
                List<OnlineDTO> onlineList = exhibitorDTO.getOnlineExhibitList();
                if (onlineList != null) {
                    for (OnlineDTO request : onlineList) {
                        Integer onlineCheck = kibsMngMapper.checkParticipantCompanyOnline(request);
                        if (onlineCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyOnline(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyOnline(request);
                        }

                        processUpdateOnlineFileNote(exhibitorSeq, request.getNote());
                    }
                }

                /* buyer table update */
                List<BuyerDTO> buyerList = exhibitorDTO.getBuyerList();
                if (buyerList != null) {
                    for (BuyerDTO request : buyerList) {
                        Integer buyerCheck = kibsMngMapper.checkParticipantCompanyBuyer(request);
                        if (buyerCheck == 0) {
                            request.setId(exhibitorSeq);
                            Integer insertResult = kibsMngMapper.insertParticipantCompanyBuyer(request);
                        } else {
                            Integer updateResult = kibsMngMapper.updateParticipantCompanyBuyer(request);
                        }
                    }
                }

                /* exhibitor_comp update */
                //exhibitor table check
                /*String companyNameKo = exhibitorDTO.getCompanyNameKo();
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
                    ParticipantCompanyDTO updExhibitorCompInfo = kibsMngMapper.getExhibitorCompInfo(paramMap);
                    //seq , ex_seq , lang , id , transfer_year

                    if(!ObjectUtils.isEmpty(updExhibitorCompInfo)) {
                        if ("Y".equals(exhibitorDTO.getPartWantYn())) {
                            updExhibitorCompInfo.setPartWantYn("1");
                        } else {
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
                        if (chargeList_comp != null) {
                            for (int i = 0; i < chargeList_comp.size(); i++) {
                                ChargePersonDTO info = chargeList_comp.get(i);
                                if (i == 0) {
                                    updExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                } else if (i == 1) {
                                    updExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                } else if (i == 2) {
                                    updExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                    updExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                    updExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                    updExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                    updExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                }
                            }
                        }

                        //exhibitor_comp table Update
                        exhibitorCompRst = kibsMngMapper.updateExhibitorComp(updExhibitorCompInfo);
                    }else{
                        ParticipantCompanyDTO istExhibitorCompInfo = new ParticipantCompanyDTO();
                        String exhibitorCompSeq = kibsMngMapper.getExhibitorCompSeq();
                        istExhibitorCompInfo.setSeq(exhibitorCompSeq);
                        istExhibitorCompInfo.setExSeq(exhibitorDTO.getSeq());
                        istExhibitorCompInfo.setLang(exhibitorDTO.getLang());
                        istExhibitorCompInfo.setId(exhibitorDTO.getId());
                        istExhibitorCompInfo.setTransferYear(exhibitorDTO.getTransferYear());
                        if ("Y".equals(exhibitorDTO.getPartWantYn())) {
                            istExhibitorCompInfo.setPartWantYn("1");
                        } else {
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
                        if (chargeList_comp != null) {
                            for (int i = 0; i < chargeList_comp.size(); i++) {
                                ChargePersonDTO info = chargeList_comp.get(i);
                                if (i == 0) {
                                    istExhibitorCompInfo.setChargePersonName1(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition1(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel1(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone1(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail1(info.getChargePersonEmail());
                                } else if (i == 1) {
                                    istExhibitorCompInfo.setChargePersonName2(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition2(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel2(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone2(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail2(info.getChargePersonEmail());
                                } else if (i == 2) {
                                    istExhibitorCompInfo.setChargePersonName3(info.getChargePersonName());
                                    istExhibitorCompInfo.setChargePersonPosition3(info.getChargePersonPosition());
                                    istExhibitorCompInfo.setChargePersonTel3(info.getChargePersonTel());
                                    istExhibitorCompInfo.setChargePersonPhone3(info.getChargePersonPhone());
                                    istExhibitorCompInfo.setChargePersonEmail3(info.getChargePersonEmail());
                                }
                            }
                        }

                        //exhibitor_comp table Insert
                        exhibitorCompRst = kibsMngMapper.insertExhibitorComp(istExhibitorCompInfo);
                    }

                    if(exhibitorCompRst == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[processUpdateTransferCompany exhibitor_comp Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                    }

                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[processUpdateTransferCompany PARAM IS NULL OR EMPTY] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
                }*/

            }
            responseDTO.setCustomValue(exhibitorSeq);
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateTransferCompany ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateTransferVisitor(VisitorDTO visitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateTransferVisitor");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        try {

            //업체 존재 여부 체크
            Integer result = 0;

            if(visitorDTO.getSeq() != null && !"".equals(visitorDTO.getSeq())){
                //visitor table Update
                result = kibsMngMapper.updateVisitor(visitorDTO);
            }else{
                //visitor table Insert
                String seq = kibsMngMapper.getVisitorSeq();
                visitorDTO.setSeq(seq);
                result = kibsMngMapper.insertVisitor(visitorDTO);
            }

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[processUpdateTransferVisitor Table DB ERROR] " + CommConstants.RESULT_MSG_FAIL + " , DB 작업이 수행 실패하였습니다.";
            }else{
                List<PartnerDTO> partnerList = visitorDTO.getPartner();
                if(!partnerList.isEmpty()){
                    for(PartnerDTO partnerDTO : partnerList){
                        if(partnerDTO.getSeq() != null && !"".equals(partnerDTO.getSeq())){
                            Integer updPartnerResult = kibsMngMapper.updatePartner(partnerDTO);
                        }else{
                            String partSeq = kibsMngMapper.getPartnerSeq();
                            partnerDTO.setSeq(partSeq);
                            partnerDTO.setVisitorSeq(visitorDTO.getSeq());
                            Integer istPartnerResult = kibsMngMapper.insertPartner(partnerDTO);
                        }
                    }
                }
            }

            responseDTO.setCustomValue(visitorDTO.getSeq());
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateTransferVisitor ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<AwardDTO> processSelectEventProductList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectEventProductList");
        return kibsMngMapper.selectEventProductList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<CompanyDTO> processSelectEventCompanyList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectEventCompanyList");
        return kibsMngMapper.selectEventCompanyList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public AwardDTO processSelectEventProductSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectEventProductSingle");
        return kibsMngMapper.selectEventProductSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public CompanyDTO processSelectEventCompanySingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectEventCompanySingle");
        return kibsMngMapper.selectEventCompanySingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ChargePersonDTO> processSelectChargeList(ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsMngServiceImpl > processSelectChargeList");
        return kibsMngMapper.selectChargeList(chargePersonDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectBoothList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBoothList");
        return kibsMngMapper.selectBoothList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectBoothSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectBoothSingle");
        return kibsMngMapper.selectBoothSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateBooth(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateBooth");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.UpdateBooth(exhibitorDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateBooth ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectSignList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectSignList");
        return kibsMngMapper.selectSignList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectSignSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectSignSingle");
        return kibsMngMapper.selectSignSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateSign(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateSign");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.updateSign(exhibitorDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateSign ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectUtilityList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectUtilityList");
        return kibsMngMapper.selectUtilityList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectUtilitySingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectUtilitySingle");
        return kibsMngMapper.selectUtilitySingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateUtility(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateUtility");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.updateUtility(exhibitorDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateUtility ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngPassDTO> processSelectPassList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectPassList");
        return kibsMngMapper.selectPassList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngPassDTO> processSelectPassDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectPassDetailList");
        return kibsMngMapper.selectPassDetailList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePass(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdatePass");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String exhibitorSeq = exhibitorDTO.getSeq();
            ExhibitorDTO exInfo = kibsMngMapper.getExhibitorId(exhibitorSeq);
            String userId = exInfo.getId();
            String transferYear = exInfo.getTransferYear();

            List<PassDTO> passList = exhibitorDTO.getPassList();
            if(!passList.isEmpty()){
                for(PassDTO passDTO : passList){
                    passDTO.setUserId(userId);
                    passDTO.setTransferYear(transferYear);
                    if(passDTO.getSeq() != null && !passDTO.getSeq().equals("")){
                        Integer upd_result = kibsMngMapper.updatePass(passDTO);
                    }else{
                        Integer ist_result = kibsMngMapper.insertPass(passDTO);
                    }
                }
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdatePass ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePass(PassDTO passDTO) {
        System.out.println("KibsMngServiceImpl > processDeletePass");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deletePass(passDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + passDTO.getSeq();
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeletePass ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngBuyerDTO> processSelectMngBuyerList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMngBuyerList");
        return kibsMngMapper.selectMngBuyerList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngBuyerDTO> processSelectBuyerDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectBuyerDetailList");
        return kibsMngMapper.selectBuyerDetailList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateBuyer(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateBuyer");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String exhibitorSeq = exhibitorDTO.getSeq();
            ExhibitorDTO exInfo = kibsMngMapper.getExhibitorId(exhibitorSeq);
            String userId = exInfo.getId();
            String transferYear = exInfo.getTransferYear();

            List<BuyerDTO> buyerList = exhibitorDTO.getBuyerList();
            if(!buyerList.isEmpty()){
                for(BuyerDTO buyerDTO : buyerList){
                    buyerDTO.setUserId(userId);
                    buyerDTO.setTransferYear(transferYear);
                    if(buyerDTO.getSeq() != null && !buyerDTO.getSeq().equals("")){
                        Integer upd_result = kibsMngMapper.updateParticipantCompanyBuyer(buyerDTO);
                    }else{
                        Integer ist_result = kibsMngMapper.insertParticipantCompanyBuyer(buyerDTO);
                    }
                }
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateBuyer ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngGiftDTO> processSelectMngGiftList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMngGiftList");
        return kibsMngMapper.selectMngGiftList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngGiftDTO> processSelectGiftDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectGiftDetailList");
        return kibsMngMapper.selectGiftDetailList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateGift(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateGift");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String exhibitorSeq = exhibitorDTO.getSeq();
            ExhibitorDTO exInfo = kibsMngMapper.getExhibitorId(exhibitorSeq);
            String userId = exInfo.getId();
            String transferYear = exInfo.getTransferYear();

            List<GiftDTO> giftList = exhibitorDTO.getGiftList();
            if(!giftList.isEmpty()){
                for(GiftDTO giftDTO : giftList){
                    giftDTO.setUserId(userId);
                    giftDTO.setTransferYear(transferYear);
                    if(giftDTO.getSeq() != null && !giftDTO.getSeq().equals("")){
                        Integer upd_result = kibsMngMapper.updateExhibitGift(giftDTO);
                    }else{
                        Integer ist_result = kibsMngMapper.insertExhibitGift(giftDTO);
                    }
                }
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateGift ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteGift(GiftDTO giftDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteGift");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deleteGift(giftDTO);

            if(giftDTO.getGiftPhoto() != null && !giftDTO.getGiftPhoto().equals("")){
                FileDTO photoReq = new FileDTO();
                photoReq.setUserId(giftDTO.getId());
                photoReq.setNote(giftDTO.getGiftPhoto());
                Integer upd_photo_result = kibsMngMapper.updateFileYn(photoReq);
            }

            if(giftDTO.getGiftCompanyLogo() != null && !giftDTO.getGiftCompanyLogo().equals("")){
                FileDTO photoReq = new FileDTO();
                photoReq.setUserId(giftDTO.getId());
                photoReq.setNote(giftDTO.getGiftCompanyLogo());
                Integer upd_photo_result = kibsMngMapper.updateFileYn(photoReq);
            }

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + giftDTO.getSeq();
            }
            //System.out.println(result);
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
    public  List<PopupDTO> processSelectPopupList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectPopupList");
        return kibsMngMapper.selectPopupList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public PopupDTO processSelectPopupSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectPopupSingle");
        return kibsMngMapper.selectPopupSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePopup(PopupDTO popupDTO) {
        System.out.println("KibsMngServiceImpl > processUpdatePopup");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(popupDTO.getId())){

                Boolean updFlag = false;
                if(popupDTO.getUseYn().equals("Y")){
                    /* 팝업파일정보 */
                    PopupDTO reqDTO = new PopupDTO();
                    reqDTO.setLang("KO");
                    reqDTO.setUseYn("Y");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String today = dateFormat.format(new Date());
                    reqDTO.setToday(today);

                    Integer activePopupCount = kibsMngMapper.getActivePopupCount(reqDTO);
                    if(activePopupCount < 4){
                        updFlag = true;
                    }else{
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "사용 가능한 팝업의 갯수는 최대 3개입니다. 팝업을 새로 추가하시려면 사용중인 팝업을 미사용으로 변경해주세요.";
                    }
                }else{
                    updFlag = true;
                }

                if(updFlag){

                    String content = popupDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                    popupDTO.setContent(content);
                    result = kibsMngMapper.updatePopup(popupDTO);
                    if(result == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[Data Update Fail] Id : " + popupDTO.getId();
                    }
                    //System.out.println(result);
                }
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdatePopup ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertPopup(PopupDTO popupDTO) {
        System.out.println("KibsMngServiceImpl > processInsertPopup");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            Boolean istFlag = false;
            if(popupDTO.getUseYn().equals("Y")){
                /* 팝업파일정보 */
                PopupDTO reqDTO = new PopupDTO();
                reqDTO.setLang("KO");
                reqDTO.setUseYn("Y");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String today = dateFormat.format(new Date());
                reqDTO.setToday(today);

                Integer activePopupCount = kibsMngMapper.getActivePopupCount(reqDTO);
                if(activePopupCount < 4){
                    istFlag = true;
                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "사용 가능한 팝업의 갯수는 최대 3개입니다. 팝업을 새로 추가하시려면 사용중인 팝업을 미사용으로 변경해주세요.";
                }
            }else{
                istFlag = true;
            }

            if(istFlag){
                String id = kibsMngMapper.getPopupId();
                popupDTO.setId(id);

                String content = popupDTO.getContent().replaceAll("&lt;","<").replaceAll("&gt;",">").trim();
                popupDTO.setContent(content);

                result = kibsMngMapper.insertPopup(popupDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Insert Fail]";
                }
                responseDTO.setCustomValue(id);
                //System.out.println(result);
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertPopup ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeletePopup(PopupDTO popupDTO) {
        System.out.println("KibsMngServiceImpl > processDeletePopup");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(popupDTO.getId() != null){
                result = kibsMngMapper.deletePopup(popupDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + popupDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeletePopup ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DirectoryDTO> processSelectDirectoryList(DirectoryDTO directoryDTO) {
        System.out.println("KibsMngServiceImpl > processSelectDirectoryList");
        return kibsMngMapper.selectDirectoryList(directoryDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDetailDTO> processSelectExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorDetailList");
        return kibsMngMapper.selectExhibitorDetailList(exhibitorDetailDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDetailDTO> processSelectTransferExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO) {
        System.out.println("KibsMngServiceImpl > processSelectTransferExhibitorDetailList");
        return kibsMngMapper.selectTransferExhibitorDetailList(exhibitorDetailDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<VisitorDetailDTO> processSelectVisitorDetailList(VisitorDetailDTO visitorDetailDTO) {
        System.out.println("KibsMngServiceImpl > processSelectVisitorDetailList");
        return kibsMngMapper.selectVisitorDetailList(visitorDetailDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<VisitorDetailDTO> processSelectTransferVisitorDetailList(VisitorDetailDTO visitorDetailDTO) {
        System.out.println("KibsMngServiceImpl > processSelectTransferVisitorDetailList");
        return kibsMngMapper.selectTransferVisitorDetailList(visitorDetailDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateApprovalStatus(List<ExhibitorDTO> exhibitorList) {
        System.out.println("KibsMngServiceImpl > processUpdateApprovalStatus");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            for(ExhibitorDTO exhibitorInfo : exhibitorList){
                if(!StringUtil.isEmpty(exhibitorInfo.getSeq())){

                    result = kibsMngMapper.updateApprovalStatus(exhibitorInfo);
                    if(result == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[Data Update Fail] Seq : " + exhibitorInfo.getSeq();
                        break;
                    }
                    //System.out.println(result);

                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Seq Not Found Error]";
                }
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateApprovalStatus ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdatePrcYn(List<ExhibitorDTO> exhibitorList) {
        System.out.println("KibsMngServiceImpl > processUpdatePrcYn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            for(ExhibitorDTO exhibitorInfo : exhibitorList){
                if(!StringUtil.isEmpty(exhibitorInfo.getSeq())){

                    result = kibsMngMapper.updatePrcYn(exhibitorInfo);
                    if(result == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[Data Update Fail] Seq : " + exhibitorInfo.getSeq();
                        break;
                    }
                    //System.out.println(result);

                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Seq Not Found Error]";
                }
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdatePrcYn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateEventProduct(AwardDTO awardDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateEventProduct");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String seq = awardDTO.getSeq();
            if(seq != null && !"".equals(seq)){
                result = kibsMngMapper.updateEventProduct(awardDTO);
            }else{
                seq = kibsMngMapper.getEventProductSeq();
                awardDTO.setSeq(seq);
                result = kibsMngMapper.insertEventProduct(awardDTO);
            }

            responseDTO.setCustomValue(seq);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data I/U Fail] Seq : " + seq;
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateEventProduct ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteEventProduct(AwardDTO awardDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteEventProduct");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deleteEventProduct(awardDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + awardDTO.getSeq();
            }else{
                FileDTO photoReq = new FileDTO();
                photoReq.setUserId(awardDTO.getSeq());
                photoReq.setNote("productPhoto");
                Integer upd_photo_result = kibsMngMapper.updateFileYn(photoReq);
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteEventProduct ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateEventCompany(CompanyDTO companyDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateEventCompany");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String seq = companyDTO.getSeq();
            if(seq != null && !"".equals(seq)){
                result = kibsMngMapper.updateEventCompany(companyDTO);
            }else{
                seq = kibsMngMapper.getEventCompanySeq();
                companyDTO.setSeq(seq);
                result = kibsMngMapper.insertEventCompany(companyDTO);
            }

            responseDTO.setCustomValue(seq);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data I/U Fail] Seq : " + seq;
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateEventCompany ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteEventCompany(CompanyDTO companyDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteEventCompany");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deleteEventCompany(companyDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + companyDTO.getSeq();
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteEventCompany ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public  List<PassDetailDTO> processSelectExcelPassDetailList(PassDetailDTO passDetailDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExcelPassDetailList");
        return kibsMngMapper.selectExcelPassDetailList(passDetailDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<RequestDTO> processSelectRequestList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectRequestList");
        return kibsMngMapper.selectRequestList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public RequestDTO processSelectRequestSingle(RequestDTO requestDTO) {
        System.out.println("KibsMngServiceImpl > processSelectRequestSingle");
        return kibsMngMapper.selectRequestSingle(requestDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertRequest(RequestDTO requestDTO) {
        System.out.println("KibsMngServiceImpl > processInsertRequest");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            String getSeq = kibsMngMapper.getRequestSeq();
            requestDTO.setSeq(getSeq);

            result = kibsMngMapper.insertRequest(requestDTO);

            responseDTO.setCustomValue(getSeq);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertRequest ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateRequest(RequestDTO requestDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateRequest");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(requestDTO.getSeq())){

                result = kibsMngMapper.updateRequest(requestDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Seq : " + requestDTO.getSeq();
                }
                responseDTO.setCustomValue(requestDTO.getSeq());
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateRequest ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteRequest(RequestDTO requestDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteRequest");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(requestDTO.getSeq() != null){
                result = kibsMngMapper.deleteRequest(requestDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + requestDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteRequest ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ReplyDTO> processSelectReplyList(String requestSeq) {
        System.out.println("KibsMngServiceImpl > processSelectReplyList");
        return kibsMngMapper.selectReplyList(requestSeq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertReply(ReplyDTO replyDTO) {
        System.out.println("KibsMngServiceImpl > processInsertReply");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            String getSeq = kibsMngMapper.getReplySeq();
            replyDTO.setSeq(getSeq);

            result = kibsMngMapper.insertReply(replyDTO);

            responseDTO.setCustomValue(getSeq);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertReply ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteReply(ReplyDTO replyDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteReply");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(replyDTO.getSeq() != null){
                result = kibsMngMapper.deleteReply(replyDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + replyDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteReply ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateRequestProgressStep(List<RequestDTO> requestList) {
        System.out.println("KibsMngServiceImpl > processUpdateRequestProgressStep");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            for(RequestDTO info : requestList){
                if(!StringUtil.isEmpty(info.getSeq())){

                    result = kibsMngMapper.updateRequestProgressStep(info);
                    if(result == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[Data Update Fail] Seq : " + info.getSeq();
                        break;
                    }
                    //System.out.println(result);

                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Seq Not Found Error]";
                }
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateRequestProgressStep ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateRequestCompleteExpect(List<RequestDTO> requestList) {
        System.out.println("KibsMngServiceImpl > processUpdateRequestCompleteExpect");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            for(RequestDTO info : requestList){
                if(!StringUtil.isEmpty(info.getSeq())){

                    result = kibsMngMapper.updateRequestCompleteExpect(info);
                    if(result == 0){
                        resultCode = CommConstants.RESULT_CODE_FAIL;
                        resultMessage = "[Data Update Fail] Seq : " + info.getSeq();
                        break;
                    }
                    //System.out.println(result);

                }else{
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Seq Not Found Error]";
                }
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateRequestCompleteExpect ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    /*******************************************
     * Excel File Upload Service Impl
     * *****************************************/

    public List<?> uploadExcelFile(MultipartFile excelFile){
        List<?> list = new ArrayList<>();
        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

            // 첫번째 시트 불러오기
            XSSFSheet sheet = workbook.getSheetAt(0);

            for(int i=1; i<sheet.getLastRowNum() + 1; i++) {
//                CreateUserVo createUserVo = new CreateUserVo();
                XSSFRow row = sheet.getRow(i);

                // 행이 존재하기 않으면 패스
                if(null == row) {
                    continue;
                }

                for(int j=0; j<row.getLastCellNum() + 1; j++){

                    // 행의 1번째 열(아이디)
                    XSSFCell cell = row.getCell(j);

                    //System.out.println(cell.getStringCellValue());
                }

                /*if(null != cell)
                    createUserVo.setUser_id(cell.getStringCellValue());
                // 행의 2번째 열(이름)
                cell = row.getCell(1);
                if(null != cell)
                    createUserVo.setUser_name(cell.getStringCellValue());

                list.add(createUserVo);*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    /*******************************************
     * Mail Send Service Impl
     * *****************************************/

    public ResponseDTO processMailSend(MailRequestDTO mailRequestDTO) {
        System.out.println("KibsMngServiceImpl > processMailSend");
        ResponseDTO responseDto = new ResponseDTO();
        // URL
        String url = "https://directsend.co.kr/index.php/api_v2/mail_change_word";

        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("Accept", "application/json");

            /*
             * subject  : 받을 mail 제목, 치환 문자열 사용 가능.
             *   치환 문자열 : [$NAME] - 이름 (한글 10글자/영문 30byte 처리), [$EMAIL] - 이메일, [$MOBILE] - 휴대폰,
             *     [$NOTE1] - 비고1 (한글/영문 128자 처리), [$NOTE2] - 비고2 (한글/영문 128자 처리), [$NOTE3] - 비고3 (한글/영문 128자 처리), [$NOTE4] - 비고4 (한글/영문 128자 처리), [$NOTE5] - 비고5 (한글/영문 128자 처리)
             *   템플릿 사용시 템플릿에 입력된 메일 제목이 우선적으로 적용됩니다. 빌더로 템플릿을 저장할 경우 메일 제목은 저장되지 않으므로 subject값을 입력해주시기 바랍니다.
             * body  : 받을 mail 본문, 치환 문자열 사용 가능.
             *   치환 문자열 : [$NAME] - 이름 (한글 10글자/영문 30byte 처리), [$EMAIL] - 이메일, [$MOBILE] - 휴대폰,
             *     [$NOTE1] - 비고1 (한글/영문 128자 처리), [$NOTE2] - 비고2 (한글/영문 128자 처리), [$NOTE3] - 비고3 (한글/영문 128자 처리), [$NOTE4] - 비고4 (한글/영문 128자 처리), [$NOTE5] - 비고5 (한글/영문 128자 처리)
             * template : 사이트에 등록한 발송 할 템플릿 번호
             * sender : 발송자 메일주소
             * sender_name : 발송자 이름 (35자 제한)
             * username : directsend 발급 ID
             * receiver : 발송 할 고객 수신자 정보
             *   json array. ex)
             *      [
             *          {"name": "강길동", "email":"test1@directsend.co.kr", "mobile":"", "note1":"", "note2":"", "note3":"", "note4":"", "note5":""}
             *          , {"name": "홍길동", "email":"test2@directsend.co.kr", "mobile":"수신자번호", "note1":"다이렉트 센드 2", "note2":"다이렉트센드 2", "note3":"다이렉트센드 3", "note4":"다이렉트센드 4", "note5":"다이렉트센드 5"}
             *      ]
             * address_books : 사이트에 등록한 발송 할 주소록 번호 , 로 구분함 (ex. 0,1,2)
             * duplicate_yn : 수신자 정보가 중복될 경우 중복발송을 할지에 대한 여부
             * key : directsend 발급 api key
             *
             * 각 내용이 유효하지않을 경우에는 발송이 되지 않습니다.
             * 비고 내용이 최대 길이(한글/영문 128자 처리)를 넘는 경우 최대 길이 만큼 잘려서 치환 됩니다.
             * 상업성 광고 메일이나 업체 홍보 메일을 발송하는 경우, 제목에 (광고) 문구를 표기해야 합니다.
             * 영리광고 발송 시, 명시적인 사전 동의를 받은 이에게만 광고 메일 발송이 가능합니다.
             * 수신동의 여부에 대한 분쟁이 발생하는 경우 이에 대한 입증책임은 광고성 정보 전송자에게 있습니다.
             * 수신자가 수신거부 또는 수신동의 철회 의사를 쉽게 표시할 수 있는 안내문을 명시해야 합니다.
             * 스팸 메일 발송 용도로 악용하실 경우 이용에 제한이 있을 수 있으니 이용 시 주의 부탁 드립니다.
             * 불법 스팸 메일 발송 시 예고없이 서비스 이용이 정지될 수 있으며 이용정지 시 해당 아이디의 주소록과 잔액은 소멸되며, 환불되지 않으니 서비스 이용에 주의를 부탁드립니다.
             *
             * API 연동 발송시 다량의 주소를 한번에 입력하여도 수신자에게는 1:1로 보내는 것으로 표기되며, 동일한 내용의 메일을 한건씩 발송하는 것보다 다량으로 한번에 보내는 것이 발송 효율이 더 높습니다.
             * 동일한 내용의 메일을 일부 글자만 변경하여 다수에게 발송하시는 경우 수신자 정보를 Json Array [{...}, {...}]로 구분하시어 한번에 발송하시는 것을 권장 드립니다.
             */

            // 여기서부터 수정해주시기 바랍니다.

            String subject = mailRequestDTO.getSubject();   //필수입력(템플릿 사용시 23 line 설명 참조)
            String body = mailRequestDTO.getBody().replaceAll("\"","'");		//필수입력, 템플릿 사용시 빈값을 입력 하시기 바랍니다. 예시) String body = "";
            //String sender = "business@meetingfan.com";        //필수입력(미팅팬 발송테스트용)
            String sender = "kibs@kibs.com";        //필수입력(보트쇼 회사메일)
            String sender_name = "경기국제보트쇼";
            String username = "meetingfan";              //필수입력
            String key = "L7QNsEQIyrAzNHO";           //필수입력

            //수신자 정보 추가 - 필수 입력(주소록 미사용시), 치환문자 미사용시 치환문자 데이터를 입력하지 않고 사용할수 있습니다.
            //치환문자 미사용시 {\"email\":\"aaaa@naver.com\"} 이메일만 입력 해주시기 바랍니다.
            JSONArray jsonArray = new JSONArray();
            for(int i=0; i<mailRequestDTO.getReceiver().size(); i++){
                JsonObject jsonObject = new JsonObject();
                MailRequestDTO.Receiver receiverInfo = mailRequestDTO.getReceiver().get(i);
//                jsonObject.addProperty("name", receiverInfo.getName());
                jsonObject.addProperty("email", receiverInfo.getEmail());
//                jsonObject.addProperty("phone", receiverInfo.getPhone());
                jsonArray.add(jsonObject);
            }
            String receiver = "{\"email\":\"" + mailRequestDTO.getReceiver().get(0).getEmail() + "\"}";
            //receiver = "[" + jsonObject.toString() + "]";
            receiver = jsonArray.toJSONString();

            //템플릿을 사용하길 원하실 경우 아래 주석을 해제하신후, 사이트에 등록한 템플릿 번호를 입력해주시기 바랍니다.
            //String template = ""; //발송 할 템플릿 번호
            //주소록을 사용하길 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 주소록 번호를 입력해주시기 바랍니다.
            //String address_books = "0,1,2";      //발송 할 주소록 번호 , 로 구분함 (ex. 0, 1, 2)

            //수신자 정보가 중복이고 내용이 다를 경우 아래 주석을 해제하시고 발송해주시기 바랍니다.
            //String duplicate_yn = "1";

            //실제 발송성공실패 여부를 받기 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 URL 번호를 입력해주시기 바랍니다.
            //int return_url = 0;

            //open, click 등의 결과를 받기 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 URL 번호를 입력해주시기 바랍니다.
            //등록된 도메인이 http://domain 와 같을 경우, http://domain?type=[click | open | reject]&mail_id=[MailID]&email=[Email]&sendtime=[SendTime]&mail_reserve_id=[MailReserveID] 과 같은 형식으로 request를 보내드립니다.
            //int option_return_url = 0;

            //int open = 1;	// open 결과를 받으려면 아래 주석을 해제 해주시기 바랍니다.
            //int click = 1;	// click 결과를 받으려면 아래 주석을 해제 해주시기 바랍니다.
            //int check_period = 3;	// 트래킹 기간을 지정하며 3 / 7 / 10 / 15 일을 기준으로 지정하여 발송해 주시기 바랍니다. (단, 지정을 하지 않을 경우 결과를 받을 수 없습니다.)

            // 예약발송 정보 추가
            //String mail_type = "NORMAL"; // NORMAL - 즉시발송 / ONETIME - 1회예약 / WEEKLY - 매주정기예약 / MONTHLY - 매월정기예약
            //String start_reserve_time = "2019-03-08 12:11:00";// 발송하고자 하는 시간
            //String end_reserve_time = "2019-03-08 12:11:00";// 발송이 끝나는 시간 1회 예약일 경우 start_reserve_time = end_reserve_time
            // WEEKLY | MONTHLY 일 경우에 시작 시간부터 끝나는 시간까지 발송되는 횟수 Ex) type = WEEKLY, start_reserve_time = '2017-05-17 13:00:00', end_reserve_time = '2017-05-24 13:00:00' 이면 remained_count = 2 로 되어야 합니다.
            //int remained_count = 1;
            // 예약 수정/취소 API는 소스 하단을 참고 해주시기 바랍니다.

            //필수안내문구 추가
            //String agreement_text = "본메일은 [$NOW_DATE] 기준, 회원님의 수신동의 여부를 확인한 결과 회원님께서 수신동의를 하셨기에 발송되었습니다.";
            //String deny_text = "메일 수신을 원치 않으시면 [$DENY_LINK]를 클릭하세요. \\nIf you don't want this type of information or e-mail, please click the [$EN_DENY_LINK]";
            //String sender_info_text = "사업자 등록번호:-- 소재지:ㅇㅇ시(도) ㅇㅇ구(군) ㅇㅇ동 ㅇㅇㅇ번지 TEL:-- \\nEmail: <a href='mailto:test@directsend.co.kr'>test@directsend.co.kr</a>";
            //int logo_state = 1; // logo 사용시 1 / 사용안할 시 0
            //String logo_path = "http://logoimage.com/image.png';  //사용하실 로고 이미지를 입력하시기 바랍니다.";
            //String logo_sort = "CENTER";  //로고 정렬 LEFT - 왼쪽 정렬 / CENTER - 가운데 정렬 / RIGHT - 오른쪽 정렬
            //String footer_sort = "CENTER";  //메일내용, 풋터(수신옵션) 정렬 LEFT - 왼쪽 정렬 / CENTER - 가운데 정렬 / RIGHT - 오른쪽 정렬

            // 첨부파일의 URL을 보내면 DirectSend에서 파일을 download 받아 발송처리를 진행합니다. 첨부파일은 전체 10MB 이하로 발송을 해야 하며, 파일의 구분자는 '|(shift+\)'로 사용하며 5개까지만 첨부가 가능합니다.
            //String file_url = "http://localhost:8080/static/img/mail/00c75c02-f7e6-404a-89fc-967175c43da9_23_main_bg.png|https://directsend.co.kr/test1.png";
            // 첨부파일의 이름을 지정할 수 있도록 합니다.
            // 첨부파일의 이름은 순차적(https://directsend.co.kr/test.png - image.png, https://directsend.co.kr/test1.png - image2.png) 와 같이 적용이 되며, file_name을 지정하지 않은 경우 마지막의 파일의 이름으로 메일에 보여집니다.
            //String file_name = "image.png|image2.png";

            StringBuilder fileUrlSb = new StringBuilder();
            String file_url = null;
            String file_name = null;
            String imageBaseUrl = "https://www.kibs.com/static/img/mail/";
            if(mailRequestDTO.getFileUrl() != null){
                if(mailRequestDTO.getFileUrl().size() > 0){
                    for(int i=0; i<mailRequestDTO.getFileUrl().size(); i++){
                        fileUrlSb.append(imageBaseUrl);
                        fileUrlSb.append(mailRequestDTO.getFileUrl().get(i).getName());
                        if((i+1) != mailRequestDTO.getFileUrl().size()){
                            fileUrlSb.append("|");
                        }
                    }
                }
                file_url = fileUrlSb.toString();

                StringBuilder fileNameSb = new StringBuilder();
                if(mailRequestDTO.getFileUrl().size() > 0){
                    for(int i=0; i<mailRequestDTO.getFileUrl().size(); i++){
                        fileNameSb.append(mailRequestDTO.getFileUrl().get(i).getName().substring(mailRequestDTO.getFileUrl().get(i).getName().indexOf('_') + 1));
                        if((i+1) != mailRequestDTO.getFileUrl().size()){
                            fileNameSb.append("|");
                        }
                    }
                }
                file_name = fileNameSb.toString();
            }

            /* 여기까지 수정해주시기 바랍니다. */

            String urlParameters = "\"subject\":\"" + subject + "\" "
                    + ", \"body\":\"" + body + "\" "
                    + ", \"sender\":\"" + sender + "\" "
                    + ", \"sender_name\":\"" + sender_name + "\" "
                    + ", \"username\":\"" + username + "\" "
                    + ", \"receiver\":" + receiver;

                    if(mailRequestDTO.getTemplate() != null && !"".equals(mailRequestDTO.getTemplate())){
                        urlParameters += ", \"template\":\"" + mailRequestDTO.getTemplate() + "\" ";		//템플릿 사용할 경우 주석 해제  //발송 할 템플릿 번호
                    }
                    //+ ", \"address_books\":\"" + address_books + "\" "	//주소록 사용할 경우 주석 해제
                    //+ ", \"duplicate_yn\":\"" + duplicate_yn + "\" "      //중복 발송을 허용할 경우 주석 해제

                    // 예약 관련 파라미터 주석 해제
                    //+ ", \"mail_type\":\"" + mail_type + "\" "
                    //+ ", \"start_reserve_time\":\"" + start_reserve_time + "\" "
                    //+ ", \"end_reserve_time\":\"" + end_reserve_time + "\" "
                    //+ ", \"remained_count\":\"" + remained_count + "\" "

                    // 필수 안내문구 관련 파라미터 주석 해제
                    //+ ", \"agreement_text\":\"" + agreement_text + "\" "
                    //+ ", \"deny_text\":\"" + deny_text + "\" "
                    //+ ", \"sender_info_text\":\"" + sender_info_text + "\" "
                    //+ ", \"logo_path\":\"" + logo_path + "\" "
                    //+ ", \"logo_state\":\"" + logo_state + "\" "
                    //+ ", \"logo_sort\":\"" + logo_sort + "\" "

                    // 메일내용, 풋터(수신옵션) 정렬 사용할 경우 주석 해제
                    //+ ", \"footer_sort\":\"" + footer_sort + "\" "

                    // 메일 발송결과를 받고 싶은 URL     return_url이 있는 경우 주석해제 바랍니다.
                    //+ ", \"return_url_yn\": " + true        //return_url 사용시 필수 입력
                    //+ ", \"return_url\":\"" + return_url + "\" "		    //return_url 사용시 필수 입력

                    // 발송 결과 측정 항목을 사용할 경우 주석 해제
                    //+ ", \"open\":\"" + open + "\" "
                    //+ ", \"click\":\"" + click + "\" "
                    //+ ", \"check_period\":\"" + check_period + "\" "
                    //+ ", \"option_return_url\":\"" + option_return_url + "\" "

                    // 첨부 파일이 있는 경우 주석 해제
                    if(file_url != null && !"".equals(file_url)) {
                        urlParameters += ", \"file_url\":\"" + file_url + "\" "
                                + ", \"file_name\":\"" + file_name + "\" ";
                    }

            urlParameters +=  ", \"key\":\"" + key + "\" ";
            urlParameters = "{"+ urlParameters  +"}";		//JSON 데이터
            System.out.println("urlParameters : " + urlParameters);

            System.setProperty("jsse.enableSNIExtension", "false");
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter (con.getOutputStream(), StandardCharsets.UTF_8);
            wr.write(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println(responseCode); // 200

            /*
             * responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
             * directsend 관리자에게 문의해주시기 바랍니다.
             */

            java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            System.out.println("mail send response : " + response.toString()); // {"status":"0"}
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(response.toString());
            String mailResponseCode = "";
            if(responseObj.get("status") != null){
                mailResponseCode = String.valueOf(responseObj.get("status"));

                if("0".equals(mailResponseCode)){
                    responseDto.setResultCode(CommConstants.RESULT_CODE_SUCCESS);
                    responseDto.setResultMessage(CommConstants.RESULT_MSG_SUCCESS);
                }else{
                    responseDto.setResultCode(CommConstants.RESULT_CODE_FAIL);
                    responseDto.setResultMessage("[" + mailResponseCode + "]" + responseObj.get("msg"));
                }
            }else{
                responseDto.setResultCode(CommConstants.RESULT_CODE_FAIL);
                responseDto.setResultMessage(CommConstants.RESULT_MSG_FAIL);
            }


            /*
             * response의 실패
             * {"status":101, "msg":"UTF-8 인코딩이 아닙니다."}
             * 실패 코드번호, 내용
             */

            /*
             * response 성공
             * {"status":0}
             * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
             *
             * 잘못된 이메일 주소가 포함된 경우
             * {"status":0, "msg":"유효하지 않는 이메일을 제외하고 발송 완료 하였습니다.", "msg_detail":"error email : test2@test2, test3@test"}
             * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.), 내용, 잘못된 데이터
             *
             */

            /*
                status code
                0   : 정상발송 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
                100 : POST validation 실패
                101 : 회원정보가 일치하지 않음
                102 : Subject, Body 정보가 없습니다.
                103 : Sender 이메일이 유효하지 않습니다.
                104 : receiver 이메일이 유효하지 않습니다.
                105 : 본문에 포함되면 안되는 확장자가 있습니다.
                106 : body validation 실패
                107 : 받는사람이 없습니다.
                108 : 예약정보가 유효하지 않습니다.
                109 : return_url이 없습니다.
                110 : 첨부파일이 없습니다.
                111 : 첨부파일의 개수가 5개를 초과합니다.
                112 : 파일의 총Size가 10 MB를 넘어갑니다.
                113 : 첨부파일이 다운로드 되지 않았습니다.
                114 : utf-8 인코딩 에러 발생
                115 : 템플릿 validation 실패
                200 : 동일 예약시간으로는 200회 이상 API 호출을 할 수 없습니다.
                201 : 분당 300회 이상 API 호출을 할 수 없습니다.
                202 : 발송자명이 최대길이를 초과 하였습니다.
                205 : 잔액부족
                999 : Internal Error.
             */

        }catch (IOException | ParseException me){
            me.printStackTrace();
        }

        return responseDto;
    }

    public ResponseDTO memberExcelUp(MultipartFile excelFile) {
        ResponseDTO rrVO = new ResponseDTO();
        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream()); // 파일 읽어옴
            XSSFWorkbook workbook =  new XSSFWorkbook(opcPackage);

            XSSFSheet sheet = workbook.getSheetAt(0);

            // 입력된 행의 수만큼 반복
            for(int i=1;i<=sheet.getLastRowNum();i++) {
                XSSFRow row = sheet.getRow(i); // i번째 행 가져옴
                XSSFCell cell = null;

                if(row == null) continue;

                // 0번째 열
                cell = row.getCell(0);
                // Cell 값이 null 일 수도 있으므로 체크
                if(cell != null)
                {
                    cell.setCellType(CellType.STRING); // 숫자만 입력받는 경우를 대비해 STRING 처리
                    //System.out.println(cell.getStringCellValue().replace(" ", "")); // 공백처리
                }

            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rrVO;
    }

}