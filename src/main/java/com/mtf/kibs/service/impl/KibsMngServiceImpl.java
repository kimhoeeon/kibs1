package com.mtf.kibs.service.impl;

import com.google.gson.JsonObject;
import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.mapper.CommMapper;
import com.mtf.kibs.mapper.KibsMngMapper;
import com.mtf.kibs.service.CalculationService;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.util.StringUtil;
import lombok.Setter;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 서비스의 비즈니스 로직을 구현하는 곳입니다.
 * - 해당 *ServiceImpl 에서는 @Service 어노테이션을 필수적으로 사용합니다.
 */
@Service
public class KibsMngServiceImpl implements KibsMngService {

    @Autowired
    private CalculationService calculationService; // 1. 공통 계산 서비스 주입

    @Setter(onMethod_ = {@Autowired})
    private KibsMngMapper kibsMngMapper;

    @Setter(onMethod_ = {@Autowired})
    private CommMapper commMapper;

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
    public StatisticsDTO processSelectProductCount(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectProductCount");
        return kibsMngMapper.selectProductCount(statisticsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public StatisticsDTO processSelectProductQtyCount(StatisticsDTO statisticsDTO) {
        System.out.println("KibsMngServiceImpl > processSelectProductQtyCount");
        return kibsMngMapper.selectProductQtyCount(statisticsDTO);
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

    @Override
    public BrochureDTO getBrochureInfo(String year) {
        return kibsMngMapper.selectBrochureInfo(year);
    }

    @Override
    @Transactional
    public void saveOrUpdateBrochureInfo(BrochureDTO dto) {
        // 1. 먼저 해당 연도의 데이터가 있는지 확인
        BrochureDTO existing = kibsMngMapper.selectBrochureInfo(dto.getTransferYear());

        if (existing != null) {
            // 2. 데이터가 있으면 UPDATE
            kibsMngMapper.updateBrochureInfo(dto);
        } else {
            // 3. 데이터가 없으면 INSERT
            kibsMngMapper.insertBrochureInfo(dto);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ContestDTO> processSelectContestList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectContestList");
        return kibsMngMapper.selectContestList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ContestDTO processSelectContestSingle(ContestDTO contestDTO) {
        System.out.println("KibsMngServiceImpl > processSelectContestSingle");
        return kibsMngMapper.selectContestSingle(contestDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteContest(ContestDTO contestDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteContest");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(contestDTO.getId() != null){
                result = kibsMngMapper.deleteContest(contestDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + contestDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteContest ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateContest(ContestDTO contestDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateContest");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(contestDTO.getId())){

                result = kibsMngMapper.updateContest(contestDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + contestDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateContest ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processInsertContest(ContestDTO contestDTO) {
        System.out.println("KibsMngServiceImpl > processInsertContest");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            String getId = kibsMngMapper.getContestId();
            contestDTO.setId(getId);
            result = kibsMngMapper.insertContest(contestDTO);

            responseDTO.setCustomValue(getId);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Insert Fail]";
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processInsertContest ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public List<ExhibitorNewDTO> processSelectListExhibitorNew(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectListExhibitorNew");
        return kibsMngMapper.selectListExhibitorNew(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorSingle");
        return kibsMngMapper.selectExhibitorSingle(exhibitorDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewSingle(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewSingle");
        return kibsMngMapper.selectExhibitorNewSingle(exhibitorNewDTO);
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
    public ResponseDTO processDeleteExhibitorNew(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorNew");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(exhibitorNewDTO.getSeq() != null){
                result = kibsMngMapper.deleteExhibitorNew(exhibitorNewDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Seq : " + exhibitorNewDTO.getSeq();
                }else{
                    /* charge_new */
                    ChargeNewDTO chargeNewDTO = new ChargeNewDTO();
                    chargeNewDTO.setExSeq(exhibitorNewDTO.getSeq());
                    Integer chargeNewResult = kibsMngMapper.deleteChargeNew(chargeNewDTO);

                    /* product_new */
                    ProductNewDTO productNewDTO = new ProductNewDTO();
                    productNewDTO.setExSeq(exhibitorNewDTO.getSeq());
                    Integer productNewResult = kibsMngMapper.deleteProductNew(productNewDTO);

                    /* online_new */
                    OnlineNewDTO onlineNewDTO = new OnlineNewDTO();
                    onlineNewDTO.setExSeq(exhibitorNewDTO.getSeq());
                    Integer onlineNewResult = kibsMngMapper.deleteOnlineNew(onlineNewDTO);

                    /* pass_new */
                    PassNewDTO passNewDTO = new PassNewDTO();
                    passNewDTO.setExSeq(exhibitorNewDTO.getSeq());
                    Integer passNewResult = kibsMngMapper.deletePassNew(passNewDTO);

                    /* gift_new */
                    GiftNewDTO giftNewDTO = new GiftNewDTO();
                    giftNewDTO.setExSeq(exhibitorNewDTO.getSeq());
                    Integer giftNewResult = kibsMngMapper.deleteGiftNew(giftNewDTO);

                    /* buyer_new */
                    BuyerNewDTO buyerNewDTO = new BuyerNewDTO();
                    buyerNewDTO.setExSeq(exhibitorNewDTO.getSeq());
                    Integer buyerNewResult = kibsMngMapper.deleteBuyerNew(buyerNewDTO);

                    /* invoice_booth */
                    InvoiceBoothDTO invoiceBoothDTO = new InvoiceBoothDTO();
                    invoiceBoothDTO.setExhibitorSeq(exhibitorNewDTO.getSeq());
                    Integer invoiceBoothResult = kibsMngMapper.deleteExhibitorNewInvoiceBooth(invoiceBoothDTO);

                    /* invoice_utility */
                    InvoiceUtilityDTO invoiceUtilityDTO = new InvoiceUtilityDTO();
                    invoiceUtilityDTO.setExhibitorSeq(exhibitorNewDTO.getSeq());
                    Integer invoiceUtilityResult = kibsMngMapper.deleteExhibitorNewInvoiceUtility(invoiceUtilityDTO);

                    /* file */
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setUserId(exhibitorNewDTO.getSeq());
                    commMapper.deleteFileUserId(fileDTO);
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorNew ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
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
                        if(partnerDTO.getSeq() != null && !partnerDTO.getSeq().isEmpty()){
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
                result = kibsMngMapper.deletePartnerSeq(partnerDTO);
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
    public List<ProductNewDTO> processSelectProductNewList(ProductNewDTO productNewDTO) {
        System.out.println("KibsMngServiceImpl > processSelectProductNewList");
        return kibsMngMapper.selectProductNewList(productNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineDTO> processSelectOnlineList(OnlineDTO onlineDTO) {
        System.out.println("KibsMngServiceImpl > processSelectOnlineList");
        return kibsMngMapper.selectOnlineList(onlineDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<OnlineNewDTO> processSelectOnlineNewList(OnlineNewDTO onlineNewDTO) {
        System.out.println("KibsMngServiceImpl > processSelectOnlineNewList");
        return kibsMngMapper.selectOnlineNewList(onlineNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectMngOnlineList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMngOnlineList");
        return kibsMngMapper.selectMngOnlineList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorNewDTO> processSelectMngOnlineNewList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMngOnlineNewList");
        return kibsMngMapper.selectMngOnlineNewList(searchDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCompanyBadge(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > updateCompanyBadge");
        return kibsMngMapper.updateCompanyBadge(exhibitorNewDTO);
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
    public ResponseDTO processUpdateExhibitorNewOnlineViewYn(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewOnlineViewYn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(exhibitorNewDTO.getSeq() != null && !exhibitorNewDTO.getSeq().isEmpty()){

                result = kibsMngMapper.updateExhibitorNewOnlineViewYn(exhibitorNewDTO);

                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Seq : " + exhibitorNewDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewOnlineViewYn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngProductDTO> processSelectMngProductNewList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectMngProductNewList");
        return kibsMngMapper.selectMngProductNewList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerDTO> processSelectBuyerList(BuyerDTO buyerDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBuyerList");
        return kibsMngMapper.selectBuyerList(buyerDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BuyerNewDTO> processSelectBuyerNewList(BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBuyerNewList");
        return kibsMngMapper.selectBuyerNewList(buyerNewDTO);
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
    public List<ChargeNewDTO> processSelectChargeNewList(ChargeNewDTO chargeNewDTO) {
        System.out.println("KibsMngServiceImpl > processSelectChargeNewList");
        return kibsMngMapper.selectChargeNewList(chargeNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorDTO> processSelectBoothList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectBoothList");
        return kibsMngMapper.selectBoothList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorNewDTO> processSelectExhibitorNewBoothList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewBoothList");
        return kibsMngMapper.selectExhibitorNewBoothList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectBoothSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectBoothSingle");
        return kibsMngMapper.selectBoothSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewBoothSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewBoothSingle");
        return kibsMngMapper.selectExhibitorNewBoothSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<InvoiceBoothDTO> processSelectInvoiceBoothExSeqList(InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngServiceImpl > processSelectInvoiceBoothExSeqList");
        return kibsMngMapper.selectInvoiceBoothExSeqList(invoiceBoothDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public InvoiceBoothDTO processSelectInvoiceBoothSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectInvoiceBoothSingle");
        return kibsMngMapper.selectInvoiceBoothSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public InvoiceUtilityDTO processSelectInvoiceUtilitySingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectInvoiceUtilitySingle");
        return kibsMngMapper.selectInvoiceUtilitySingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO getExhibitorInfoForLogin(String exhibitorSeq) {
        System.out.println("KibsMngServiceImpl > getExhibitorInfoForLogin");
        return kibsMngMapper.getExhibitorInfoForLogin(exhibitorSeq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public int processUpdateExhibitorNewSpecialDiscount(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewSpecialDiscount");
        // 1. 먼저 DB에서 현재 업체의 전체 정보를 조회합니다.
        ExhibitorNewDTO currentData = kibsMngMapper.selectExhibitorNewInvoiceDetail(exhibitorNewDTO.getSeq());
        if (currentData == null) {
            // 데이터가 없으면 오류 처리
            return 0;
        }

        // 2. CalculationInputDTO 조립 (DB 조회 정보 + 관리자 입력)
        CalculationInputDTO input = new CalculationInputDTO();

        // [부스 정보] - DB 조회 (currentData)
        input.setRegistrationCnt(currentData.getRegistrationCnt());
        input.setStandAloneBoothCnt(currentData.getStandAloneBoothCnt());
        input.setAssemblyBoothCnt(currentData.getAssemblyBoothCnt());
        input.setOnlineBoothCnt(currentData.getOnlineBoothCnt());

        // [기본 할인 정보] - DB 조회 (currentData)
        // (주의: 만약 관리자가 기본 할인도 수정한다면 exhibitorNewDTO에서 값을 가져와야 함)
        input.setDiscountEarly1(currentData.getDiscountEarly1());
        input.setDiscountEarly2(currentData.getDiscountEarly2());
        input.setDiscountFirstUnder10(currentData.getDiscountFirstUnder10());
        input.setDiscountFirstOver10(currentData.getDiscountFirstOver10());
        input.setDiscountRe(currentData.getDiscountRe());
        input.setDiscountScale1(currentData.getDiscountScale1());
        input.setDiscountScale2(currentData.getDiscountScale2());
        input.setDiscountScale3(currentData.getDiscountScale3());
        input.setDiscountScale4(currentData.getDiscountScale4());
        input.setDiscountScale5(currentData.getDiscountScale5());
        input.setDiscountScale6(currentData.getDiscountScale6());
        input.setDiscountLeisure(currentData.getDiscountLeisure());

        // [특별 할인 정보] - 관리자 입력 (exhibitorNewDTO)
        input.setDiscountSpecial1Yn(exhibitorNewDTO.isDiscountSpecial1Yn());
        input.setDiscountSpecial2Yn(exhibitorNewDTO.isDiscountSpecial2Yn());
        input.setDiscountSpecial2Amount(exhibitorNewDTO.getDiscountSpecial2Amount());
        input.setDiscountSpecial3Yn(exhibitorNewDTO.isDiscountSpecial3Yn());
        input.setDiscountSpecial3Amount(exhibitorNewDTO.getDiscountSpecial3Amount());

        // [기타 정보]
        input.setUtilityPrcSum(currentData.getUtilityPrcSum()); // DB 값
        input.setMemberCompanyYn(currentData.getMemberCompanyYn()); // DB 값
        input.setDeposit(Integer.parseInt(currentData.getDeposit())); // DB 값

        // 3. *** 공통 서비스 호출 ***
        CalculationResultDTO result = calculationService.calculateTotals(input);

        // 4. DTO에 계산 결과 반영
        // (특별 할인 정보는 이미 exhibitorNewDTO에 있음. 최종 금액만 덮어쓰기)
        // exhibitorNewDTO.setDevelopmentFund(result.getDevelopmentFund()); // DB 컬럼이 있다면 저장
        exhibitorNewDTO.setPrcSum(result.getPrcSum());
        exhibitorNewDTO.setPrcVat(result.getPrcVat());
        exhibitorNewDTO.setPrcTotal(result.getPrcTotal());
        exhibitorNewDTO.setBalance(String.valueOf(result.getBalance()));

        // 잔액(balance)도 함께 재계산
        int depositAmount = currentData.getDeposit() != null ? Integer.parseInt(currentData.getDeposit()) : 0;
        exhibitorNewDTO.setBalance(String.valueOf(result.getPrcTotal() - depositAmount));

        // 8. 확장된 UPDATE 쿼리를 호출하여 DB에 모든 정보를 한번에 저장합니다.
        return kibsMngMapper.updateExhibitorNewSpecialDiscount(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<DepositHistoryDTO> selectDepositHistoryList(String exhibitorSeq) {
        System.out.println("KibsMngServiceImpl > selectDepositHistoryList");
        return kibsMngMapper.selectDepositHistoryList(exhibitorSeq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public DepositHistoryDTO insertDepositHistory(DepositHistoryDTO dto) {

        ExhibitorNewDTO exhibitorInfo = new ExhibitorNewDTO();
        exhibitorInfo.setSeq(dto.getExhibitorSeq());
        String prcYn = "";
        switch (dto.getPaymentStatus()){
            case "미납":
                prcYn = "0";
                break;
            case "참가비 납부":
                prcYn = "1";
                break;
            case "50% 납부":
                prcYn = "2";
                break;
            case "전액 납부":
                prcYn = "3";
                break;
            case "완납(부대시설비)":
                prcYn = "4";
                break;
        }
        exhibitorInfo.setPrcYn(prcYn);
        exhibitorInfo.setDeposit(String.valueOf(dto.getAmount()));
        kibsMngMapper.updateExhibitorNewPrcYn(exhibitorInfo);

        int result = kibsMngMapper.insertDepositHistory(dto); // 이 호출 후, dto 객체의 depositSeq 필드에 값이 채워집니다.
        if (result > 0) {

            recalculateAndUpdatePayments(dto.getExhibitorSeq());

            return dto; // 성공 시, seq가 채워진 dto 객체를 반환
        }
        return null; // 실패 시 null 반환
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public Map<String, Object> updateDepositHistory(DepositHistoryDTO dto) {
        System.out.println("KibsMngServiceImpl > updateDepositHistory");
        Map<String, Object> resultMap = new HashMap<>();

        ExhibitorNewDTO exhibitorInfo = new ExhibitorNewDTO();
        exhibitorInfo.setSeq(dto.getExhibitorSeq());
        String prcYn = "";
        switch (dto.getPaymentStatus()){
            case "미납":
                prcYn = "0";
                break;
            case "참가비 납부":
                prcYn = "1";
                break;
            case "50% 납부":
                prcYn = "2";
                break;
            case "전액 납부":
                prcYn = "3";
                break;
            case "완납(부대시설비)":
                prcYn = "4";
                break;
        }
        exhibitorInfo.setPrcYn(prcYn);
        exhibitorInfo.setDeposit(String.valueOf(dto.getAmount()));
        kibsMngMapper.updateExhibitorNewPrcYn(exhibitorInfo);

        // 1. Mapper를 호출하고, 반환된 int 값(영향받은 행의 수)을 받습니다.
        int result = kibsMngMapper.updateDepositHistory(dto);

        // 2. int 결과를 바탕으로 성공/실패 Map을 직접 만듭니다.
        if (result > 0) { // 1개 이상의 행이 영향을 받았다면 성공

            recalculateAndUpdatePayments(dto.getExhibitorSeq());

            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "수정되었습니다.");
        } else {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "데이터 수정에 실패했습니다.");
        }

        // 3. 완성된 Map을 Controller로 반환합니다.
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public Map<String, Object> deleteDepositHistory(int depositSeq) {
        // 삭제 전에 exhibitor_seq를 먼저 조회해야 합니다.
        String exhibitorSeq = kibsMngMapper.getExhibitorSeqByDepositSeq(depositSeq);

        System.out.println("KibsMngServiceImpl > deleteDepositHistory");
        Map<String, Object> resultMap = new HashMap<>();

        // 1. Mapper를 호출하고, 반환된 int 값(영향받은 행의 수)을 받습니다.
        int result = kibsMngMapper.deleteDepositHistory(depositSeq);

        // 2. int 결과를 바탕으로 성공/실패 Map을 직접 만듭니다.
        if (result > 0 && exhibitorSeq != null) {
            // 잔액 재계산 로직 호출
            recalculateAndUpdatePayments(exhibitorSeq);

            // 5. exhibitor_new 테이블에 prc_yn 상태 업데이트
            kibsMngMapper.updateExhibitorPrcYnFromLatestDeposit(exhibitorSeq);

            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "삭제되었습니다.");
        } else {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "데이터 삭제에 실패했습니다.");
        }

        // 3. 완성된 Map을 Controller로 반환합니다.
        return resultMap;
    }

    private void recalculateAndUpdatePayments(String exhibitorSeq) {
        // 1. 해당 업체의 최종 합계(prc_total)를 조회합니다.
        ExhibitorNewDTO exhibitorInfo = kibsMngMapper.selectExhibitorNewInvoiceDetail(exhibitorSeq);
        if (exhibitorInfo == null) {
            // 예외 처리 또는 로그
            return;
        }
        int prcTotal = exhibitorInfo.getPrcTotal() != null ? exhibitorInfo.getPrcTotal() : 0;

        // 2. 해당 업체의 총 입금액을 deposit_history 테이블에서 합산하여 조회합니다.
        Integer totalDeposit = kibsMngMapper.sumDepositAmountByExhibitor(exhibitorSeq);
        int newDepositSum = (totalDeposit != null) ? totalDeposit : 0;

        // 3. 새로운 잔액을 계산합니다. (잔액 = 최종 합계 - 총 입금액)
        int newBalance = prcTotal - newDepositSum;

        // 4. 계산된 잔액을 exhibitor_new 테이블에 업데이트합니다.
        kibsMngMapper.updateExhibitorPaymentStatus(exhibitorSeq, newDepositSum, newBalance);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO getExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > getExhibitorNewInfo");
        return kibsMngMapper.getExhibitorNewInfo(exhibitorNewDTO);
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
            result = kibsMngMapper.updateBooth(exhibitorDTO);

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
    public ResponseDTO processUpdateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewBooth");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.updateExhibitorNewBooth(exhibitorNewDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewBooth ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}) // 기존 어노테이션 유지
    @Override
    public InvoiceBoothDTO createAndInsertInvoiceBooth(String exhibitorSeq) throws Exception {
        // 1. 인보이스를 생성할 참가업체의 최신 정보를 불러옵니다.
        // ※ 주의: 참가업체 상세 정보를 가져오는 메서드가 KibsMngMapper에 있어야 합니다.
        // ※ (예: selectExhibitorNewForInvoice) 없다면 KibsMapper.getExhibitorNewDetail 사용
        // ※ 기존 코드의 selectExhibitorNewInvoiceDetail 메서드가 금액 관련 모든 필드를 반환하는지 확인 필요
        ExhibitorNewDTO currentExhibitorInfo = kibsMngMapper.selectExhibitorNewInvoiceDetail(exhibitorSeq);
        if (currentExhibitorInfo == null) {
            throw new Exception("참가업체 정보가 존재하지 않습니다.");
        }

        // 2. *** CalculationInputDTO 조립 (현재 DB 정보 기준) ***
        CalculationInputDTO input = new CalculationInputDTO();
        input.setRegistrationCnt(currentExhibitorInfo.getRegistrationCnt());
        input.setStandAloneBoothCnt(currentExhibitorInfo.getStandAloneBoothCnt());
        input.setAssemblyBoothCnt(currentExhibitorInfo.getAssemblyBoothCnt());
        input.setOnlineBoothCnt(currentExhibitorInfo.getOnlineBoothCnt());
        input.setUtilityPrcSum(currentExhibitorInfo.getUtilityPrcSum()); // 현재 유틸리티 금액
        input.setDiscountEarly1(currentExhibitorInfo.getDiscountEarly1());
        input.setDiscountEarly2(currentExhibitorInfo.getDiscountEarly2());
        input.setDiscountFirstUnder10(currentExhibitorInfo.getDiscountFirstUnder10());
        input.setDiscountFirstOver10(currentExhibitorInfo.getDiscountFirstOver10());
        input.setDiscountRe(currentExhibitorInfo.getDiscountRe());
        input.setDiscountScale1(currentExhibitorInfo.getDiscountScale1());
        input.setDiscountScale2(currentExhibitorInfo.getDiscountScale2());
        input.setDiscountScale3(currentExhibitorInfo.getDiscountScale3());
        input.setDiscountScale4(currentExhibitorInfo.getDiscountScale4());
        input.setDiscountScale5(currentExhibitorInfo.getDiscountScale5());
        input.setDiscountScale6(currentExhibitorInfo.getDiscountScale6());
        input.setDiscountLeisure(currentExhibitorInfo.getDiscountLeisure());
        input.setDiscountSpecial1Yn(currentExhibitorInfo.isDiscountSpecial1Yn());
        input.setDiscountSpecial2Yn(currentExhibitorInfo.isDiscountSpecial2Yn());
        input.setDiscountSpecial2Amount(currentExhibitorInfo.getDiscountSpecial2Amount());
        input.setDiscountSpecial3Yn(currentExhibitorInfo.isDiscountSpecial3Yn());
        input.setDiscountSpecial3Amount(currentExhibitorInfo.getDiscountSpecial3Amount());
        input.setMemberCompanyYn(currentExhibitorInfo.getMemberCompanyYn());
        input.setDeposit(Integer.parseInt(currentExhibitorInfo.getDeposit()));

        // 3. *** 공통 계산 서비스 호출 ***
        CalculationResultDTO calcResult = calculationService.calculateTotals(input);

        // 4. [기존 로직] DB에서 해당 참가업체의 기존 인보이스 발급 횟수를 조회합니다.
        int invoiceCount = kibsMngMapper.countInvoiceBoothByExhibitorSeq(exhibitorSeq);

        // 5. [기존 로직] 새로운 인보이스 코드를 생성합니다. (발급 횟수 + 1)
        int nextInvoiceNumber = invoiceCount + 1;
        // exhibitorSeq가 "EN0000058" 형태라고 가정하고 뒤 4자리 사용
        String invoiceCodeCount = exhibitorSeq.length() >= 4 ? exhibitorSeq.substring(exhibitorSeq.length() - 4) : exhibitorSeq;
        String invoiceCode = String.format("KIBS-B%s-%d", invoiceCodeCount, nextInvoiceNumber);

        // 6. 새로운 InvoiceBoothDTO 객체를 생성하고 값을 채웁니다.
        InvoiceBoothDTO newInvoice = new InvoiceBoothDTO();
        newInvoice.setExhibitorSeq(exhibitorSeq); // 참가업체 Seq
        newInvoice.setRecipientEmail(currentExhibitorInfo.getEmail()); // 수신 이메일 (업체 대표 이메일)
        newInvoice.setTitle(currentExhibitorInfo.getCompanyNameKo() + " - 전시부스"); // 인보이스 제목
        newInvoice.setInvoiceCode(invoiceCode); // 생성된 인보이스 코드
        // newInvoice.setIssueDate(LocalDate.now()); // issueDate 컬럼 없음

        // --- ★★★ CalculationService 계산 결과 DTO -> 인보이스 DTO 값 복사 (DDL 기준) ★★★ ---
        newInvoice.setBoothPrcSum(calcResult.getBoothPrcSum()); // 계산된 부스 총액
        // DDL: discount_prc_sum = 총 할인액 (기본+특별)
        newInvoice.setDiscountPrcSum(calcResult.getBasicDiscountSum() + calcResult.getSpecialDiscountTotal()); // 계산된 총 할인액
        newInvoice.setPrcSum(calcResult.getPrcSum());         // 계산된 공급가액(소계)
        newInvoice.setPrcVat(calcResult.getPrcVat());         // 계산된 부가세
        newInvoice.setPrcTotal(calcResult.getPrcTotal());     // 계산된 최종 합계(총액)
        // --- ▲▲▲ ---

        // --- 기타 정보 설정 (기존 코드 또는 필요에 따라 수정) ---
        newInvoice.setEndDttm(String.valueOf(LocalDate.now().plusDays(14))); // 납부 기한 (end_dttm 컬럼)
        newInvoice.setSendStatus("미발송"); // 초기 상태
        newInvoice.setDelYn("N");
        // newInvoice.setRegId("admin"); // regId 컬럼 없음
        newInvoice.setFinalRegiPic("admin"); // 최종 수정자 (예시)

        // 7. Mapper를 호출하여 DB에 인보이스를 INSERT 합니다.
        // ※※※ kibsMngMapper.insertInvoiceBooth 메서드는 useGeneratedKeys="true" keyProperty="invoiceSeq" 설정이 필요합니다. ※※※
        int insertResult = kibsMngMapper.insertInvoiceBooth(newInvoice);

        // 8. INSERT 성공 여부 및 생성된 PK 확인
        if (insertResult <= 0) {
            throw new Exception("인보이스 생성(INSERT)에 실패했습니다.");
        }

        int generatedSeq = newInvoice.getInvoiceSeq(); // MyBatis가 자동으로 채워준 PK 값

        // 9. 만약 seq가 0이거나 유효하지 않으면 예외를 발생시킵니다.
        if (generatedSeq <= 0) {
            throw new Exception("인보이스 생성 후 PK(invoiceSeq)를 가져오지 못했습니다.");
        }

        // 10. 생성된 seq를 이용해 DB에서 방금 저장된 인보이스 데이터를 다시 조회하여 반환합니다.
        // ※※※ kibsMngMapper.selectInvoiceBoothBySeq 메서드가 필요합니다. (selectInvoiceBoothSingle 과 유사) ※※※
        InvoiceBoothDTO createdInvoice = kibsMngMapper.selectInvoiceBoothBySeq(generatedSeq); // 메서드 이름 확인 필요
        if (createdInvoice == null) {
            throw new Exception("생성된 인보이스를 다시 조회하는 데 실패했습니다.");
        }

        return createdInvoice; // 최종적으로 생성 및 조회된 인보이스 객체 반환
    }

    @Override
    public InvoiceUtilityDTO createAndInsertInvoiceUtility(String exhibitorSeq) throws Exception {
        ExhibitorNewDTO currentInfo = kibsMngMapper.selectExhibitorNewInvoiceDetail(exhibitorSeq);
        if (currentInfo == null) {
            throw new Exception("참가업체 정보가 존재하지 않습니다.");
        }

        int invoiceCount = kibsMngMapper.countInvoiceUtilityByExhibitorSeq(exhibitorSeq);
        int nextInvoiceNumber = invoiceCount + 1;
        String invoiceCodeCount = exhibitorSeq.substring(exhibitorSeq.length() - 4);
        String invoiceCode = String.format("KIBS-U%s-%d", invoiceCodeCount, nextInvoiceNumber);

        InvoiceUtilityDTO newInvoice = new InvoiceUtilityDTO();
        newInvoice.setExhibitorSeq(exhibitorSeq);
        newInvoice.setRecipientEmail(currentInfo.getEmail());
        newInvoice.setTitle(currentInfo.getCompanyNameKo() + " - 유틸리티");
        newInvoice.setInvoiceCode(invoiceCode);

        // 유틸리티 신청 내역 스냅샷 복사
        newInvoice.setUtilityJuganCnt(currentInfo.getUtilityJuganCnt());
        newInvoice.setUtilityJuganFee(currentInfo.getUtilityJuganFee());
        newInvoice.setUtilityDayCnt(currentInfo.getUtilityDayCnt());
        newInvoice.setUtilityDayFee(currentInfo.getUtilityDayFee());
        newInvoice.setUtilityCompressedAirCnt(currentInfo.getUtilityCompressedAirCnt());
        newInvoice.setUtilityCompressedAirFee(currentInfo.getUtilityCompressedAirFee());
        newInvoice.setUtilityWaterBasicCnt(currentInfo.getUtilityWaterBasicCnt());
        newInvoice.setUtilityWaterBasicFee(currentInfo.getUtilityWaterBasicFee());
        newInvoice.setUtilityInternetCnt(currentInfo.getUtilityInternetCnt());
        newInvoice.setUtilityInternetFee(currentInfo.getUtilityInternetFee());
        newInvoice.setUtilityPytexNewCnt(currentInfo.getUtilityPytexNewCnt());
        newInvoice.setUtilityPytexNewFee(currentInfo.getUtilityPytexNewFee());
        newInvoice.setUtilityPytexReCnt(currentInfo.getUtilityPytexReCnt());
        newInvoice.setUtilityPytexReFee(currentInfo.getUtilityPytexReFee());
        newInvoice.setUtilityBarcodeCnt(currentInfo.getUtilityBarcodeCnt());
        newInvoice.setUtilityBarcodeFee(currentInfo.getUtilityBarcodeFee());
        newInvoice.setUtilityPrcSum(currentInfo.getUtilityPrcSum());

        // 유틸리티 금액 기준으로 최종 합계 계산
        int prcSum = currentInfo.getUtilityPrcSum();
        int prcVat = (int) Math.floor(prcSum * 0.1);
        int prcTotal = prcSum + prcVat;

        newInvoice.setPrcSum(prcSum);
        newInvoice.setPrcVat(prcVat);
        newInvoice.setPrcTotal(prcTotal);

        kibsMngMapper.insertInvoiceUtility(newInvoice);

        // 2. newInvoice 객체에 채워진 invoiceSeq 값을 가져옵니다.
        int generatedSeq = newInvoice.getInvoiceSeq();

        // 3. 만약 seq가 0이거나 유효하지 않으면 예외를 발생시킵니다.
        if (generatedSeq <= 0) {
            throw new Exception("인보이스 생성 후 PK(seq)를 가져오지 못했습니다.");
        }

        // 4. 생성된 seq를 이용해 DB에서 방금 저장된 인보이스 데이터를 다시 조회하여 반환합니다.
        return kibsMngMapper.selectInvoiceUtilityBySeq(generatedSeq);
    }

    @Override
    public List<InvoiceBoothDTO> getInvoiceBoothList(String exhibitorSeq) {
        return kibsMngMapper.selectInvoiceBoothList(exhibitorSeq);
    }

    @Override
    public List<InvoiceUtilityDTO> getInvoiceUtilityList(String exhibitorSeq) {
        return kibsMngMapper.selectInvoiceUtilityList(exhibitorSeq);
    }

    @Override
    public boolean updateInvoiceBoothFilePath(InvoiceBoothDTO invoiceDto) {
        return kibsMngMapper.updateInvoiceBoothFilePath(invoiceDto) > 0;
    }

    @Override
    public boolean deleteInvoiceBooth(int invoiceSeq) {
        return kibsMngMapper.deleteInvoiceBooth(invoiceSeq) > 0;
    }

    @Override
    public boolean deleteInvoiceUtility(int invoiceSeq) {
        return kibsMngMapper.deleteInvoiceUtility(invoiceSeq) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteExhibitorNewBoothInvoice(InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorNewBoothInvoice");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            result = kibsMngMapper.updateInvoiceBoothDelYn(invoiceBoothDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorNewBoothInvoice ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteExhibitorNewUtilityInvoice(InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorNewUtilityInvoice");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            result = kibsMngMapper.updateInvoiceUtilityDelYn(invoiceUtilityDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorNewUtilityInvoice ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewBoothInvoiceFilePath(InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewBoothInvoiceFilePath");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {
            result = kibsMngMapper.updateInvoiceBoothFilePath(invoiceBoothDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewBoothInvoiceFilePath ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewUtilityInvoiceFilePath(InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewUtilityInvoiceFilePath");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {
            result = kibsMngMapper.updateInvoiceUtilityFilePath(invoiceUtilityDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewUtilityInvoiceFilePath ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewInvoiceDetail(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewInvoiceDetail");
        return kibsMngMapper.selectExhibitorNewInvoiceDetail(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewBoothInvoiceSendResult(InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewBoothInvoiceSendResult");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            InvoiceSendHistoryDTO historyDto = new InvoiceSendHistoryDTO();
            historyDto.setHistorySeq(invoiceBoothDTO.getHistorySeq());
            historyDto.setInvoiceSeq(invoiceBoothDTO.getInvoiceSeq());
            historyDto.setInvoiceType(invoiceBoothDTO.getInvoiceType());
            historyDto.setRecipientEmail(invoiceBoothDTO.getRecipientEmail());
            historyDto.setSendStatus(invoiceBoothDTO.getSendStatus());
            historyDto.setSendResult(invoiceBoothDTO.getSendResult());
            historyDto.setSendResultMsg(invoiceBoothDTO.getSendResultMsg());
            result = kibsMngMapper.updateInvoiceSendHistoryResult(historyDto);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewBoothInvoiceSendResult ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewUtilityInvoiceSendResult(InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewUtilityInvoiceSendResult");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            InvoiceSendHistoryDTO historyDto = new InvoiceSendHistoryDTO();
            historyDto.setHistorySeq(invoiceUtilityDTO.getHistorySeq());
            historyDto.setInvoiceSeq(invoiceUtilityDTO.getInvoiceSeq());
            historyDto.setInvoiceType(invoiceUtilityDTO.getInvoiceType());
            historyDto.setRecipientEmail(invoiceUtilityDTO.getRecipientEmail());
            historyDto.setSendStatus(invoiceUtilityDTO.getSendStatus());
            historyDto.setSendResult(invoiceUtilityDTO.getSendResult());
            historyDto.setSendResultMsg(invoiceUtilityDTO.getSendResultMsg());
            result = kibsMngMapper.updateInvoiceSendHistoryResult(historyDto);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewUtilityInvoiceSendResult ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    // 2. 이력 조회를 위한 새로운 서비스 메소드 추가
    public List<InvoiceSendHistoryDTO> getInvoiceSendHistory(int invoiceSeq, String invoiceType) {
        return kibsMngMapper.selectInvoiceSendHistory(invoiceSeq, invoiceType);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ExhibitorNewDTO> processSelectExhibitorNewMaritimeList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewMaritimeList");
        return kibsMngMapper.selectExhibitorNewMaritimeList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewMaritimeSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewMaritimeSingle");
        return kibsMngMapper.selectExhibitorNewMaritimeSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateExhibitorNewMaritime(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewMaritime");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.updateExhibitorNewMaritime(exhibitorNewDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewMaritime ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public List<ExhibitorNewDTO> processSelectExhibitorNewSignList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewSignList");
        return kibsMngMapper.selectExhibitorNewSignList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectSignSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectSignSingle");
        return kibsMngMapper.selectSignSingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewSignSingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewSignSingle");
        return kibsMngMapper.selectExhibitorNewSignSingle(seq);
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
    public ResponseDTO processUpdateExhibitorNewSign(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewSign");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.updateExhibitorNewSign(exhibitorNewDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewSign ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public List<ExhibitorNewDTO> processSelectExhibitorNewUtilityList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewUtilityList");
        return kibsMngMapper.selectExhibitorNewUtilityList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorDTO processSelectUtilitySingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectUtilitySingle");
        return kibsMngMapper.selectUtilitySingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ExhibitorNewDTO processSelectExhibitorNewUtilitySingle(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewUtilitySingle");
        return kibsMngMapper.selectExhibitorNewUtilitySingle(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<InvoiceUtilityDTO> processSelectInvoiceUtilityExSeqList(InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngServiceImpl > processSelectInvoiceUtilityExSeqList");
        return kibsMngMapper.selectInvoiceUtilityExSeqList(invoiceUtilityDTO);
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
    public ResponseDTO processUpdateExhibitorNewUtility(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewUtility");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            /* exhibitor table update */
            result = kibsMngMapper.updateExhibitorNewUtility(exhibitorNewDTO);

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Update Fail]";
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewUtility ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void updateInvoiceHistoryStatusToOpen(int historySeq) {
        System.out.println("KibsMngServiceImpl > processUpdateInvoiceMailOpen");
        kibsMngMapper.updateInvoiceHistoryStatusToOpen(historySeq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngPassDTO> processSelectPassList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectPassList");
        return kibsMngMapper.selectPassList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngPassDTO> processSelectExhibitorNewPassList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewPassList");
        return kibsMngMapper.selectExhibitorNewPassList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngPassDTO> processSelectPassDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectPassDetailList");
        return kibsMngMapper.selectPassDetailList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngPassDTO> processSelectExhibitorNewPassDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewPassDetailList");
        return kibsMngMapper.selectExhibitorNewPassDetailList(seq);
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
    public ResponseDTO processUpdateExhibitorNewPass(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewPass");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String exhibitorSeq = exhibitorNewDTO.getSeq();

            List<PassNewDTO> passList = exhibitorNewDTO.getPassList();
            if(!passList.isEmpty()){
                for(PassNewDTO passDTO : passList){
                    passDTO.setExSeq(exhibitorSeq);
                    if(passDTO.getSeq() != null && !passDTO.getSeq().isEmpty()){
                        Integer upd_result = kibsMngMapper.updateExhibitorNewPass(passDTO);
                    }else{
                        Integer ist_result = kibsMngMapper.insertExhibitorNewPass(passDTO);
                    }
                }
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewPass ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public ResponseDTO processDeleteExhibitorNewPass(PassNewDTO passNewDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorNewPass");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deleteExhibitorNewPass(passNewDTO);
            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + passNewDTO.getSeq();
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorNewPass ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public List<MngBuyerDTO> processSelectExhibitorNewBuyerList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewBuyerList");
        return kibsMngMapper.selectExhibitorNewBuyerList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngBuyerDTO> processSelectBuyerDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectBuyerDetailList");
        return kibsMngMapper.selectBuyerDetailList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngBuyerDTO> processSelectExhibitorNewBuyerDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewBuyerDetailList");
        return kibsMngMapper.selectExhibitorNewBuyerDetailList(seq);
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
    public ResponseDTO processUpdateExhibitorNewBuyer(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewBuyer");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String exhibitorSeq = exhibitorNewDTO.getSeq();

            List<BuyerNewDTO> buyerList = exhibitorNewDTO.getBuyerList();
            if(!buyerList.isEmpty()){
                for(BuyerNewDTO buyerDTO : buyerList){
                    buyerDTO.setExSeq(exhibitorSeq);
                    if(buyerDTO.getSeq() != null && !buyerDTO.getSeq().isEmpty()){
                        Integer upd_result = kibsMngMapper.updateExhibitorNewBuyer(buyerDTO);
                    }else{
                        Integer ist_result = kibsMngMapper.insertExhibitorNewBuyer(buyerDTO);
                    }
                }
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewBuyer ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processDeleteExhibitorNewBuyer(BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorNewBuyer");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(buyerNewDTO.getSeq() != null){
                result = kibsMngMapper.deleteExhibitorNewBuyer(buyerNewDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Delete Fail] Id : " + buyerNewDTO.getSeq();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Seq Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorNewBuyer ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public List<MngGiftDTO> processSelectExhibitorNewGiftList(SearchDTO searchDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewGiftList");
        return kibsMngMapper.selectExhibitorNewGiftList(searchDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngGiftDTO> processSelectGiftDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectGiftDetailList");
        return kibsMngMapper.selectGiftDetailList(seq);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<MngGiftDTO> processSelectExhibitorNewGiftDetailList(String seq) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewGiftDetailList");
        return kibsMngMapper.selectExhibitorNewGiftDetailList(seq);
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
    public ResponseDTO processUpdateExhibitorNewGift(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewGift");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;

        try {

            String exhibitorSeq = exhibitorNewDTO.getSeq();

            List<GiftNewDTO> giftList = exhibitorNewDTO.getGiftList();
            if(!giftList.isEmpty()){
                for(GiftNewDTO giftDTO : giftList){
                    giftDTO.setExSeq(exhibitorSeq);
                    if(giftDTO.getSeq() != null && !giftDTO.getSeq().isEmpty()){
                        Integer upd_result = kibsMngMapper.updateGiftNew(giftDTO);
                    }else{
                        Integer ist_result = kibsMngMapper.insertGiftNew(giftDTO);
                    }
                }
            }

        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateExhibitorNewGift ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public ResponseDTO processDeleteExhibitorNewGift(GiftNewDTO giftNewDTO) {
        System.out.println("KibsMngServiceImpl > processDeleteExhibitorNewGift");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            result = kibsMngMapper.deleteExhibitorNewGift(giftNewDTO);

            if(giftNewDTO.getGiftPhoto() != null && !giftNewDTO.getGiftPhoto().isEmpty()){
                FileDTO photoReq = new FileDTO();
                photoReq.setUserId(giftNewDTO.getExSeq());
                photoReq.setNote(giftNewDTO.getGiftPhoto());
                Integer upd_photo_result = kibsMngMapper.updateFileYn(photoReq);
            }

            if(giftNewDTO.getGiftCompanyLogo() != null && !giftNewDTO.getGiftCompanyLogo().isEmpty()){
                FileDTO photoReq = new FileDTO();
                photoReq.setUserId(giftNewDTO.getExSeq());
                photoReq.setNote(giftNewDTO.getGiftCompanyLogo());
                Integer upd_photo_result = kibsMngMapper.updateFileYn(photoReq);
            }

            if(result == 0){
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Data Delete Fail] Seq : " + giftNewDTO.getSeq();
            }
            //System.out.println(result);
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processDeleteExhibitorNewGift ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
                        resultMessage = "사용 가능한 팝업의 갯수는 최대 3개입니다. 팝업을 새로 추가하시려면 사용중인 팝업을 미사용으로 변경해 주세요.";
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
                    resultMessage = "사용 가능한 팝업의 갯수는 최대 3개입니다. 팝업을 새로 추가하시려면 사용중인 팝업을 미사용으로 변경해 주세요.";
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
    public List<ExhibitorNewDetailDTO> processSelectExhibitorNewDetailList(ExhibitorNewDetailDTO exhibitorNewDetailDTO) {
        System.out.println("KibsMngServiceImpl > processSelectExhibitorNewDetailList");
        return kibsMngMapper.selectExhibitorNewDetailList(exhibitorNewDetailDTO);
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
    public ResponseDTO processUpdateExhibitorNewApprovalStatus(List<ExhibitorNewDTO> exhibitorList) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewApprovalStatus");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            for(ExhibitorNewDTO exhibitorInfo : exhibitorList){
                if(!StringUtil.isEmpty(exhibitorInfo.getSeq())){

                    result = kibsMngMapper.updateExhibitorNewApprovalStatus(exhibitorInfo);
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
            resultMessage = "[processUpdateExhibitorNewApprovalStatus ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public ResponseDTO processUpdateExhibitorNewPrcYn(List<ExhibitorNewDTO> exhibitorList) {
        System.out.println("KibsMngServiceImpl > processUpdateExhibitorNewPrcYn");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {

            for(ExhibitorNewDTO exhibitorInfo : exhibitorList){
                if(!StringUtil.isEmpty(exhibitorInfo.getSeq())){

                    result = kibsMngMapper.updateExhibitorNewPrcYn(exhibitorInfo);
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
            resultMessage = "[processUpdateExhibitorNewPrcYn ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
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
    public List<String> getExhibitorNewEmailList(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngServiceImpl > getExhibitorNewEmailList");
        return kibsMngMapper.getExhibitorNewEmailList(exhibitorNewDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<BoothDetailDTO> processSelectExcelBoothDetailList(String transferYear) {
        System.out.println("KibsMngServiceImpl > processSelectExcelBoothDetailList");
        return kibsMngMapper.selectExcelBoothDetailList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<PassDetailDTO> processSelectExcelPassDetailList(String transferYear) {
        System.out.println("KibsMngServiceImpl > processSelectExcelPassDetailList");
        return kibsMngMapper.selectExcelPassDetailList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<SignDetailDTO> processSelectExcelSignDetailList(String transferYear) {
        System.out.println("KibsMngServiceImpl > processSelectExcelSignDetailList");
        return kibsMngMapper.selectExcelSignDetailList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<UtilityDetailDTO> processSelectExcelUtilityDetailList(String transferYear) {
        System.out.println("KibsMngServiceImpl > processSelectExcelUtilityDetailList");
        return kibsMngMapper.selectExcelUtilityDetailList(transferYear);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<ProductDetailDTO> processSelectExcelProductDetailList(String transferYear) {
        System.out.println("KibsMngServiceImpl > processSelectExcelProductDetailList");
        return kibsMngMapper.selectExcelProductDetailList(transferYear);
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

    public void createAndDownloadProductExcel(HttpServletResponse response) throws Exception {
        // DB에서 데이터 가져오기
        //List<ProductDTO> productList = productMapper.selectProductList();

        // *** 1. 수정 필요: 실제 템플릿 파일 경로와 이름으로 변경 ***
        ClassPathResource resource = new ClassPathResource("/file/보트쇼_통계_엑셀.xlsx");

        try (InputStream inputStream = resource.getInputStream();
             XSSFWorkbook templateWorkbook = new XSSFWorkbook(inputStream);
             // 2. 템플릿을 기반으로 SXSSFWorkbook 생성 (스트리밍을 위한 객체)
             SXSSFWorkbook workbook = new SXSSFWorkbook(templateWorkbook)) {

            // ========================================================================
            // 스타일 생성
            // ========================================================================
            CellStyle centerStyle = workbook.createCellStyle();
            centerStyle.setAlignment(HorizontalAlignment.CENTER); // 수평 가운데 정렬
            centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 수직 가운데 정렬

            // ============ 시트 0: 참가 신청 현황 ============
            SXSSFSheet sheet0 = (SXSSFSheet) workbook.getSheetAt(0);
            sheet0.trackAllColumnsForAutoSizing();
            List<StatDTO> dayList0 = kibsMngMapper.selectPartApplyDayList();
            List<StatDTO> weekList0 = kibsMngMapper.selectPartApplyWeekList();
            List<StatDTO> monthList0 = kibsMngMapper.selectPartApplyMonthList();

            // 4. 데이터 채우기 (Sheet 2와 동일한 방식으로 수정)
            int maxSize0 = Math.max(dayList0.size(), Math.max(weekList0.size(), monthList0.size()));
            int startRowNum0 = 3; // 데이터 시작 행

            for (int i = 0; i < maxSize0; i++) {
                Row row = sheet0.createRow(startRowNum0 + i);

                // --- DAY 데이터 ---
                if (i < dayList0.size()) {
                    StatDTO dayInfo = dayList0.get(i);
                    createCell(row, 0, dayInfo.getEventDate(), centerStyle);
                    createCell(row, 1, dayInfo.getExhibitorCount(), centerStyle);
                    createCell(row, 2, dayInfo.getVisitorCount(), centerStyle);
                }

                // --- WEEK 데이터 ---
                if (i < weekList0.size()) {
                    StatDTO weekInfo = weekList0.get(i);
                    createCell(row, 3, weekInfo.getWeekPeriod(), centerStyle);
                    createCell(row, 4, weekInfo.getExhibitorCount(), centerStyle);
                    createCell(row, 5, weekInfo.getVisitorCount(), centerStyle);
                }

                // --- MONTH 데이터 ---
                if (i < monthList0.size()) {
                    StatDTO monthInfo = monthList0.get(i);
                    createCell(row, 6, monthInfo.getMonthPeriod(), centerStyle);
                    createCell(row, 7, monthInfo.getExhibitorCount(), centerStyle);
                    createCell(row, 8, monthInfo.getVisitorCount(), centerStyle);
                }
            }
            // 너비 자동 조정
            for (int i = 0; i < 9; i++) {
                sheet0.setColumnWidth(i, Math.min(255*256, sheet0.getColumnWidth(i) + 1024));
            }
            // ============ 시트 0: 참가 신청 현황 ============

            // ============ 시트 1: 부스별 신청업체 수 ============
            SXSSFSheet sheet1 = (SXSSFSheet) workbook.getSheetAt(1);
            sheet1.trackAllColumnsForAutoSizing();
            List<StatDTO> boothList = kibsMngMapper.selectExhNewBoothStatList();

            // 4. 데이터 채우기
            int boothStatRowNum  = 2; // *** 수정 필요: 데이터 시작 행 번호 ***
            for (StatDTO info : boothList ) {
                // createRow()는 새 행을 생성 (기존 행이 있으면 덮어씀)
                Row row = sheet1.createRow(boothStatRowNum++);
                // *** 수정 필요: 실제 엑셀의 열 순서에 맞게 DTO 데이터 매핑 ***
                createCell(row, 0, info.getBoothCount1(), centerStyle);
                createCell(row, 1, info.getBoothCount2(), centerStyle);
                createCell(row, 2, info.getBoothCount3(), centerStyle);
            }
            // 너비 자동 조정
            for (int i = 0; i < 9; i++) {
                sheet1.setColumnWidth(i, Math.min(255*256, sheet1.getColumnWidth(i) + 1024));
            }
            // ============ 시트 1: 부스별 신청업체 수 ============

            // ============ 시트 2: 홈페이지 방문 현황 ============
            SXSSFSheet sheet2 = (SXSSFSheet) workbook.getSheetAt(2);
            sheet2.trackAllColumnsForAutoSizing();
            List<StatDTO> dayList2 = kibsMngMapper.selectStatAccessDayList();
            List<StatDTO> weekList2 = kibsMngMapper.selectStatAccessWeekList();
            List<StatDTO> monthList2 = kibsMngMapper.selectStatAccessMonthList();

            // DAY, WEEK, MONTH 리스트 중 가장 큰 사이즈를 기준으로 반복
            int maxSize1 = Math.max(dayList2.size(), Math.max(weekList2.size(), monthList2.size()));
            int startRowNum1 = 2; // 데이터 시작 행

            for (int i = 0; i < maxSize1; i++) {
                // 루프마다 새로운 행을 하나 생성
                Row row = sheet2.createRow(startRowNum1 + i);

                // --- DAY 데이터 채우기 (해당 인덱스에 데이터가 있을 경우) ---
                if (i < dayList2.size()) {
                    StatDTO dayInfo = dayList2.get(i);
                    createCell(row, 0, dayInfo.getStatisticDate(), centerStyle);
                    createCell(row, 1, dayInfo.getTotalCount(), centerStyle);
                }

                // --- WEEK 데이터 채우기 (해당 인덱스에 데이터가 있을 경우) ---
                if (i < weekList2.size()) {
                    StatDTO weekInfo = weekList2.get(i);
                    createCell(row, 2, weekInfo.getWeekPeriod(), centerStyle);
                    createCell(row, 3, weekInfo.getTotalCount(), centerStyle);
                }

                // --- MONTH 데이터 채우기 (해당 인덱스에 데이터가 있을 경우) ---
                if (i < monthList2.size()) {
                    StatDTO monthInfo = monthList2.get(i);
                    createCell(row, 4, monthInfo.getMonthPeriod(), centerStyle);
                    createCell(row, 5, monthInfo.getTotalCount(), centerStyle);
                }
            }
            // 너비 자동 조정
            for (int i = 0; i < 6; i++) {
                sheet2.setColumnWidth(i, Math.min(255*256, sheet2.getColumnWidth(i) + 1024));
            }
            // ============ 시트 2: 홈페이지 방문 현황 ============

            // ============ 시트 3: 참가분야별 신청업체 수 ============
            SXSSFSheet sheet3 = (SXSSFSheet) workbook.getSheetAt(3);
            sheet3.trackAllColumnsForAutoSizing();
            List<StatDTO> fieldList = kibsMngMapper.selectExhNewFieldList();

            // 4. 데이터 채우기
            int fieldPartRowNum  = 2; // *** 수정 필요: 데이터 시작 행 번호 ***
            for (StatDTO info : fieldList) {
                // createRow()는 새 행을 생성 (기존 행이 있으면 덮어씀)
                Row row = sheet3.createRow(fieldPartRowNum++);
                createCell(row, 0, info.getFieldCount1(), centerStyle);
                createCell(row, 1, info.getFieldCount2(), centerStyle);
                createCell(row, 2, info.getFieldCount3(), centerStyle);
                createCell(row, 3, info.getFieldCount4(), centerStyle);
                createCell(row, 4, info.getFieldCount5(), centerStyle);
                createCell(row, 5, info.getFieldCount6(), centerStyle);
                createCell(row, 6, info.getFieldCount7(), centerStyle);
                createCell(row, 7, info.getFieldCount8(), centerStyle);
                createCell(row, 8, info.getFieldCount9(), centerStyle);
                createCell(row, 9, info.getFieldCount10(), centerStyle);
            }
            // 너비 자동 조정
            for (int i = 0; i < 10; i++) {
                sheet3.setColumnWidth(i, Math.min(255*256, sheet3.getColumnWidth(i) + 1024));
            }
            // ============ 시트 3: 참가분야별 신청업체 수 ============

            // 5. 생성된 파일을 Response의 OutputStream에 직접 작성
            try (OutputStream out = response.getOutputStream()) {
                workbook.write(out);
            }

        } finally {
            // SXSSFWorkbook은 임시 파일을 생성하므로, dispose()를 호출하여 확실하게 정리해주는 것이 좋습니다.
            // try-with-resources 구문이 close()를 호출하고, close()가 내부적으로 dispose()를 호출하지만,
            // 명시적으로 호출하는 것이 더 안전할 수 있습니다. (필수는 아님)
            // workbook.dispose();
        }
    }

    /**
     * 셀 생성과 스타일 적용을 한 번에 처리하는 헬퍼 메서드 추가
     * (코드 중복을 줄이고 가독성을 높입니다)
     */
    private void createCell(Row row, int cellNum, Object value, CellStyle style) {
        Cell cell = row.createCell(cellNum);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } // 필요에 따라 다른 타입 추가

        cell.setCellStyle(style);
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

            //인보이스 발송에만 해당되는 프로세스
            boolean isInvoiceMail = (mailRequestDTO.getGbn() != null &&
                    ("BOOTH".equals(mailRequestDTO.getGbn()) || "UTILITY".equals(mailRequestDTO.getGbn())));
            int historySeq = 0;
            if(isInvoiceMail) {
                InvoiceSendHistoryDTO historyDto = new InvoiceSendHistoryDTO();
                historyDto.setInvoiceSeq(mailRequestDTO.getInvoiceSeq());
                historyDto.setInvoiceType(mailRequestDTO.getInvoiceType());
                historyDto.setRecipientEmail(mailRequestDTO.getRecipientEmail()); // 실제 발송될 이메일 주소
                historyDto.setSendStatus("발송중"); // 초기 상태

                // 2. 이력을 먼저 DB에 INSERT 하고, 생성된 history_seq를 받아옴
                kibsMngMapper.insertInvoiceSendHistory(historyDto);
                historySeq = historyDto.getHistorySeq();
                //note1 = URLEncoder.encode("https://kibs.com/mng/exhibitorNew/application/invoice/mail/open/update.do?hseq=" + historySeq, "UTF-8");
            }

            //수신자 정보 추가 - 필수 입력(주소록 미사용시), 치환문자 미사용시 치환문자 데이터를 입력하지 않고 사용할수 있습니다.
            //치환문자 미사용시 {\"email\":\"aaaa@naver.com\"} 이메일만 입력 해주시기 바랍니다.
            JSONArray jsonArray = new JSONArray();
            for(int i=0; i<mailRequestDTO.getReceiver().size(); i++){
                JsonObject jsonObject = new JsonObject();
                MailRequestDTO.Receiver receiverInfo = mailRequestDTO.getReceiver().get(i);
//                jsonObject.addProperty("name", receiverInfo.getName());
                jsonObject.addProperty("email", receiverInfo.getEmail());

                if(receiverInfo.getNote1() != null) {
                    jsonObject.addProperty("note1", receiverInfo.getNote1()); // 예: 인보이스 링크
                }
                if(receiverInfo.getNote2() != null) {
                    jsonObject.addProperty("note2", receiverInfo.getNote2()); // 예: 회사명
                }
                if(receiverInfo.getNote3() != null) {
                    jsonObject.addProperty("note3", receiverInfo.getNote3()); // 예: 인보이스 코드
                }
                if(receiverInfo.getNote4() != null) {
                    jsonObject.addProperty("note4", receiverInfo.getNote4()); // 예: 발행일자
                }

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
            String duplicate_yn = "1";

            //실제 발송성공실패 여부를 받기 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 URL 번호를 입력해주시기 바랍니다.
            int return_url = 6;

            //open, click 등의 결과를 받기 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 URL 번호를 입력해주시기 바랍니다.
            //등록된 도메인이 http://domain 와 같을 경우, http://domain?type=[click | open | reject]&mail_id=[MailID]&email=[Email]&sendtime=[SendTime]&mail_reserve_id=[MailReserveID] 과 같은 형식으로 request를 보내드립니다.
            int option_return_url = 3;

            int open = 1;	// open 결과를 받으려면 아래 주석을 해제 해주시기 바랍니다.
            //int click = 1;	// click 결과를 받으려면 아래 주석을 해제 해주시기 바랍니다.
            int check_period = 7;	// 트래킹 기간을 지정하며 3 / 7 / 10 / 15 일을 기준으로 지정하여 발송해 주시기 바랍니다. (단, 지정을 하지 않을 경우 결과를 받을 수 없습니다.)

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

            String file_url = null;
            String file_name = null;

            // 1. 파일 URL과 파일명을 담을 리스트 생성
            List<String> fileUrlList = new ArrayList<>();
            List<String> fileNameList = new ArrayList<>();

            String imageBaseUrl = "https://kibs.com/img/mailling/";
            if (isInvoiceMail && mailRequestDTO.getFolderPath() != null) {
                // 인보이스 메일이면 PDF 파일 경로 사용
                imageBaseUrl = "https://kibs.com/" + mailRequestDTO.getFolderPath();
            }

            if (mailRequestDTO.getFileUrl() != null && !mailRequestDTO.getFileUrl().isEmpty()) {
                for (MailRequestDTO.FileUrl fileInfo : mailRequestDTO.getFileUrl()) {
                    String dynamicFileNameEncoded = fileInfo.getName(); // JS에서 encodeURI된 파일명
                    if (dynamicFileNameEncoded != null && !dynamicFileNameEncoded.isEmpty()) {
                        fileUrlList.add(imageBaseUrl + dynamicFileNameEncoded);
                        fileNameList.add(dynamicFileNameEncoded);
                    }
                }
            }

            // 3. 인보이스 메일일 경우, 정적 통장사본 파일 추가
            if (isInvoiceMail) {
                try {
                    String staticFileNameUnencoded = "2026 경기국제보트쇼 통장사본.pdf";
                    String staticBaseUrl = "https://kibs.com/file/invoice/";

                    // JS encodeURI와 동일하게 Java에서 인코딩 (공백->%20, 특수문자 유지)
                    String staticFileNameEncoded = URLEncoder.encode(staticFileNameUnencoded, "UTF-8")
                            .replaceAll("\\+", "%20")
                            .replaceAll("\\%21", "!")
                            .replaceAll("\\%27", "'")
                            .replaceAll("\\%28", "(")
                            .replaceAll("\\%29", ")")
                            .replaceAll("\\%7E", "~");

                    fileUrlList.add(staticBaseUrl + staticFileNameEncoded);
                    fileNameList.add(staticFileNameEncoded); // JS에서 보낸 것과 동일하게 encoded된 값 추가

                } catch (UnsupportedEncodingException e) {
                    // UTF-8은 항상 지원되어야 하므로 이 예외는 거의 발생하지 않음.
                    System.out.println("Static bank account file name encoding failed");
                    // 통장사본 첨부만 실패하고 메일은 계속 발송되도록 함 (오류 방지)
                }
            }

            // 4. (기존 로직) 최종 file_url, file_name 문자열 생성
            if (!fileUrlList.isEmpty()) {
                file_url = String.join("|", fileUrlList);
                file_name = String.join("|", fileNameList); // file_name도 encoded된 상태로 | 연결
            }

            /* 여기까지 수정해주시기 바랍니다. */

            String urlParameters = "\"subject\":\"" + subject + "\" "
                    + ", \"body\":\"" + body + "\" "
                    + ", \"sender\":\"" + sender + "\" "
                    + ", \"sender_name\":\"" + sender_name + "\" "
                    + ", \"username\":\"" + username + "\" "
                    + ", \"receiver\":" + receiver;

                    if(mailRequestDTO.getTemplate() != null && !mailRequestDTO.getTemplate().isEmpty()){
                        urlParameters += ", \"template\":\"" + mailRequestDTO.getTemplate() + "\" ";		//템플릿 사용할 경우 주석 해제  //발송 할 템플릿 번호
                    }
                    //+ ", \"address_books\":\"" + address_books + "\" "	//주소록 사용할 경우 주석 해제
                    urlParameters += ", \"duplicate_yn\":\"" + duplicate_yn + "\" ";      //중복 발송을 허용할 경우 주석 해제

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

                    // --- ▼▼▼ 수정 4: API 추적 기능 파라미터 추가 ▼▼▼ ---
                    if(isInvoiceMail) { // 인보이스 메일일 때만 추적 옵션 추가
                        // 메일 발송결과를 받고 싶은 URL     return_url이 있는 경우 주석해제 바랍니다.
                        if (return_url > 0) {
                            urlParameters += ", \"return_url_yn\": " + true;        //return_url 사용시 필수 입력
                            urlParameters += ", \"return_url\":\"" + return_url + "\" ";            //return_url 사용시 필수 입력
                        }

                        urlParameters += ", \"open\":\"" + open + "\" ";
                        urlParameters += ", \"check_period\":\"" + check_period + "\" ";

                        if (option_return_url > 0) { // Webhook URL ID가 설정된 경우에만 추가
                            urlParameters += ", \"option_return_url\":\"" + option_return_url + "\" ";
                        }
                    }

                    // 첨부 파일이 있는 경우 주석 해제
                    if(file_url != null && !file_url.isEmpty()) {
                        urlParameters += ", \"file_url\":\"" + file_url + "\" "
                                + ", \"file_name\":\"" + URLDecoder.decode(file_name, "UTF-8") + "\" ";
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
                    responseDto.setCustomValue(String.valueOf(historySeq));

                    // --- 발송 ID(mail_id) 저장 ---
                    if (isInvoiceMail && responseObj.get("mail_id") != null) {
                        String mailId = String.valueOf(responseObj.get("mail_id"));

                        // (ds_reserve_id 컬럼에 mail_id를 저장)
                        kibsMngMapper.updateHistoryWithReserveId(historySeq, mailId);
                    }

                }else{
                    responseDto.setResultCode(CommConstants.RESULT_CODE_FAIL);
                    if(responseObj.get("msg_detail") != null){
                        responseDto.setResultMessage("[" + mailResponseCode + "]" + responseObj.get("msg") + " - " + responseObj.get("msg_detail"));
                    }else{
                        responseDto.setResultMessage("[" + mailResponseCode + "]" + responseObj.get("msg"));
                    }
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

    // Webhook이 ds_reserve_id로 이력 상태를 업데이트
    @Override
    @Transactional
    public void updateInvoiceHistoryStatusByMailId(String reserveId, String status) {
        // ds_reserve_id로 현재 상태 조회
        String currentStatus = kibsMngMapper.selectInvoiceHistoryStatusByMailId(reserveId);

        // '미열람' 상태일 때만 '열람'으로 업데이트
        if ("미열람".equals(currentStatus) || "발송중".equals(currentStatus) || "발송성공".equals(currentStatus)) {
            kibsMngMapper.updateInvoiceHistoryStatusByMailId(reserveId, status);
        }
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