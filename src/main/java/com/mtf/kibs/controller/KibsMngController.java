package com.mtf.kibs.controller;

import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.service.KibsMngService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The type Kibs page controller.
 */
@Controller
public class KibsMngController {

    // 필드 주입이 아닌 생성자 주입형태로 사용합니다. '생성자 주입 형태'로 사용합니다.
    private final KibsMngService kibsMngService;

    private final CommService commService;

    /**
     * Instantiates a new Kibs controller.
     *
     * @param kms         the kms
     * @param cs
     */
    public KibsMngController(KibsMngService kms, CommService cs){
        this.kibsMngService = kms;
        this.commService = cs;
    }

    // Customer Folder

    /**
     * mng login model and view.
     *
     * @return the model and view
     */
    @RequestMapping(value = "/mng/index.do")
    public ModelAndView mng_index() {
        System.out.println("KibsMngController > mng_index");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/index");
        return mv;
    }

    @RequestMapping(value = "/mng/main.do")
    public ModelAndView mng_main() {
        System.out.println("KibsMngController > mng_main");
        ModelAndView mv = new ModelAndView();

        // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate now = LocalDate.now();

        // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)
        String fullYear = String.valueOf(now.getYear());

        /* 참가기업 (취소) */
        StatisticsDTO companyReq = new StatisticsDTO();
        companyReq.setTransferYear(fullYear);
        StatisticsDTO companyStat = kibsMngService.processSelectCompanyCount(companyReq);
        mv.addObject("companyStat", companyStat);

        /* 참관객 */
        StatisticsDTO visitorReq = new StatisticsDTO();
        visitorReq.setTransferYear(fullYear);
        StatisticsDTO visitorStat = kibsMngService.processSelectVisitorCount(visitorReq);
        mv.addObject("visitorStat", visitorStat);

        mv.setViewName("/mng/main");
        return mv;
    }

    @RequestMapping(value = "/mng/main/statistics/accessor/day.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StatisticsDTO>> mng_main_statistics_accessor_day() {
        System.out.println("KibsMngController > mng_main_statistics_accessor_day");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setGbn("Accessor");
        reqDto.setTransferYear(transferYear);
        List<StatisticsDTO> responseList = kibsMngService.processSelectStatisticsAccessorDay(reqDto);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/accessor/month.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StatisticsDTO>> mng_main_statistics_accessor_month() {
        System.out.println("KibsMngController > mng_main_statistics_accessor_month");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setGbn("Accessor");
        List<StatisticsDTO> responseList = kibsMngService.processSelectStatisticsAccessorMonth(reqDto);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/accessor/week.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StatisticsDTO>> mng_main_statistics_accessor_week() {
        System.out.println("KibsMngController > mng_main_statistics_accessor_week");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setGbn("Accessor");
        List<StatisticsDTO> responseList = kibsMngService.processSelectStatisticsAccessorWeek(reqDto);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/participant/day.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<SplineStatisticsDTO>> mng_main_statistics_participant_day() {
        System.out.println("KibsMngController > mng_main_statistics_participant_day");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setTransferYear(transferYear);

        List<StatisticsDTO> companyList = kibsMngService.processSelectStatisticsParticipantCompanyDay(reqDto);

        List<StatisticsDTO> visitorList = kibsMngService.processSelectStatisticsParticipantVisitorDay(reqDto);

        for(StatisticsDTO info: visitorList){
            boolean existFlag = false;
            for(StatisticsDTO info2: companyList){
                if(info.getX().equals(info2.getX())){
                    existFlag = true;
                }
            }

            if(!existFlag){
                StatisticsDTO newStat = new StatisticsDTO();
                newStat.setX(info.getX());
                newStat.setY("0");
                companyList.add(newStat);
            }
        }

        for(StatisticsDTO info: companyList){
            boolean flag = false;
            for(StatisticsDTO info2: visitorList) {
                if(info.getX().equals(info2.getX())){
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                StatisticsDTO newStat = new StatisticsDTO();
                newStat.setX(info.getX());
                newStat.setY("0");
                visitorList.add(newStat);
            }
        }
        Collections.sort( visitorList, (o1, o2) -> o1.getX().compareTo(o2.getX()));

        SplineStatisticsDTO companyResult = new SplineStatisticsDTO();
        companyResult.setData(companyList);

        SplineStatisticsDTO visitorResult = new SplineStatisticsDTO();
        visitorResult.setData(visitorList);

        List<SplineStatisticsDTO> resultList = new ArrayList<>();
        resultList.add(companyResult);
        resultList.add(visitorResult);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/participant/month.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<SplineStatisticsDTO>> mng_main_statistics_participant_month() {
        System.out.println("KibsMngController > mng_main_statistics_participant_month");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setTransferYear(transferYear);

        List<StatisticsDTO> companyList = kibsMngService.processSelectStatisticsParticipantCompanyMonth(reqDto);

        List<StatisticsDTO> visitorList = kibsMngService.processSelectStatisticsParticipantVisitorMonth(reqDto);

        for(StatisticsDTO info: visitorList){
            boolean existFlag = false;
            for(StatisticsDTO info2: companyList){
                if(info.getX().equals(info2.getX())){
                    existFlag = true;
                }
            }

            if(!existFlag){
                StatisticsDTO newStat = new StatisticsDTO();
                newStat.setX(info.getX());
                newStat.setY("0");
                companyList.add(newStat);
            }
        }

        for(StatisticsDTO info: companyList){
            boolean flag = false;
            for(StatisticsDTO info2: visitorList) {
                if(info.getX().equals(info2.getX())){
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                StatisticsDTO newStat = new StatisticsDTO();
                newStat.setX(info.getX());
                newStat.setY("0");
                visitorList.add(newStat);
            }
        }
        Collections.sort( visitorList, (o1, o2) -> o1.getX().compareTo(o2.getX()));

        SplineStatisticsDTO companyResult = new SplineStatisticsDTO();
        companyResult.setData(companyList);

        SplineStatisticsDTO visitorResult = new SplineStatisticsDTO();
        visitorResult.setData(visitorList);

        List<SplineStatisticsDTO> resultList = new ArrayList<>();
        resultList.add(companyResult);
        resultList.add(visitorResult);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/participant/week.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<SplineStatisticsDTO>> mng_main_statistics_participant_week() {
        System.out.println("KibsMngController > mng_main_statistics_participant_week");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setTransferYear(transferYear);

        List<StatisticsDTO> companyList = kibsMngService.processSelectStatisticsParticipantCompanyWeek(reqDto);

        List<StatisticsDTO> visitorList = kibsMngService.processSelectStatisticsParticipantVisitorWeek(reqDto);

        for(StatisticsDTO info: visitorList){
            boolean existFlag = false;
            for(StatisticsDTO info2: companyList){
                if(info.getX().equals(info2.getX())){
                    existFlag = true;
                }
            }

            if(!existFlag){
                StatisticsDTO newStat = new StatisticsDTO();
                newStat.setX(info.getX());
                newStat.setY("0");
                companyList.add(newStat);
            }
        }

        for(StatisticsDTO info: companyList){
            boolean flag = false;
            for(StatisticsDTO info2: visitorList) {
                if(info.getX().equals(info2.getX())){
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                StatisticsDTO newStat = new StatisticsDTO();
                newStat.setX(info.getX());
                newStat.setY("0");
                visitorList.add(newStat);
            }
        }
        Collections.sort( visitorList, (o1, o2) -> o1.getX().compareTo(o2.getX()));

        SplineStatisticsDTO companyResult = new SplineStatisticsDTO();
        companyResult.setData(companyList);

        SplineStatisticsDTO visitorResult = new SplineStatisticsDTO();
        visitorResult.setData(visitorList);

        List<SplineStatisticsDTO> resultList = new ArrayList<>();
        resultList.add(companyResult);
        resultList.add(visitorResult);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/company/booth.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SplineStatisticsDTO> mng_main_statistics_company_booth() {
        System.out.println("KibsMngController > mng_main_statistics_company_booth");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setTransferYear(transferYear);
        StatisticsDTO boothInfo = kibsMngService.processSelectStatisticsCompanyBooth(reqDto);
        String[] boothSplit = boothInfo.getInCount().split(",");

        SplineStatisticsDTO result = new SplineStatisticsDTO();
        List<Integer> series = new ArrayList<>();
        series.add(Integer.parseInt(boothSplit[0])); //독립부스
        series.add(Integer.parseInt(boothSplit[1])); //조립부스
        series.add(Integer.parseInt(boothSplit[2])); //온라인부스
        result.setSeries(series);

        List<String> labels = new ArrayList<>();
        labels.add("독립부스");
        labels.add("조립부스");
        labels.add("온라인부스");
        result.setLabels(labels);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/main/statistics/company/fieldPart.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Integer>> mng_main_statistics_company_fieldPart() {
        System.out.println("KibsMngController > mng_main_statistics_company_fieldPart");
        //System.out.println(searchDTO.toString());

        String transferYear = String.valueOf(LocalDateTime.now().getYear()); //2025
        StatisticsDTO reqDto = new StatisticsDTO();
        reqDto.setTransferYear(transferYear);
        StatisticsDTO fieldPartInfo = kibsMngService.processSelectStatisticsCompanyFieldPart(reqDto);

        String[] fieldPartSplit = fieldPartInfo.getInCount().split(",");
        List<Integer> data = new ArrayList<>();
        for(String fieldPart : fieldPartSplit){
            data.add(Integer.parseInt(fieldPart));
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * loginCheck model and view.
     *
     * @return the model and view
     */
    @RequestMapping(value = "/mng/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AdminDTO> login(@RequestBody AdminDTO adminDTO, HttpSession session) {
        System.out.println("KibsMngController > login");
        AdminDTO result = kibsMngService.login(adminDTO, session);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping("/mng/logoutCheck.do")
    public ModelAndView logoutCheck(HttpSession session, ModelAndView mv) {
        System.out.println("KibsMngController > logoutCheck");
        kibsMngService.logoutCheck(session);
        mv.setViewName("/mng/index");
        return mv;
    }

    @RequestMapping(value = "/mng/admin/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_admin_detail() {
        System.out.println("KibsMngController > mng_admin_detail");
        ModelAndView mv = new ModelAndView();
        String ipList = kibsMngService.processSelectAccessIpList();
        mv.addObject("ipList", ipList);
        mv.setViewName("/mng/admin/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/admin/access/save.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_admin_access_save(@RequestBody List<AccessDTO> accessDTOList) {
        System.out.println("KibsMngController > mng_admin_access_save");
        //System.out.println(searchDTO.toString());

        ResponseDTO response = kibsMngService.processSaveAccessIp(accessDTOList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/admin/access/selectIp.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_admin_access_selectIp(@RequestBody AccessDTO accessDTO) {
        System.out.println("KibsMngController > mng_admin_access_selectIp");
        //System.out.println(searchDTO.toString());

        ResponseDTO response = kibsMngService.processSelectAccessIp(accessDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //***************************************************************************
    // exhibitor/participant Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/exhibitor/participant/company.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_participant_company() {
        System.out.println("KibsMngController > mng_exhibitor_company");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/participant/company");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorDTO>> mng_exhibitor_participant_company_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorDTO> responseList = kibsMngService.processSelectListExhibitor(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_company_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_company");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            /* 전시업체정보 */
            ExhibitorDTO requestDto = new ExhibitorDTO();
            requestDto.setSeq(seq);
            ExhibitorDTO info = kibsMngService.processSelectExhibitorSingle(requestDto);
            mv.addObject("info", info);

            /* 담당자정보*/
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(seq);
            List<ChargePersonDTO> chargeList = kibsMngService.processSelectChargeList(chargeReq);
            mv.addObject("chargeList", chargeList);

            /* 전시정보 */
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(seq);
            List<DisplayDTO> displayList = kibsMngService.processSelectDisplayList(displayReq);
            mv.addObject("displayList", displayList);

            /* 온라인정보 */
            OnlineDTO onlineReq = new OnlineDTO();
            onlineReq.setId(seq);
            List<OnlineDTO> onlineList = kibsMngService.processSelectOnlineList(onlineReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerDTO buyerReq = new BuyerDTO();
            buyerReq.setId(seq);
            List<BuyerDTO> buyerList = kibsMngService.processSelectBuyerList(buyerReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            List<FileDTO> promotionImageFileList = new ArrayList<>();
            List<FileDTO> productImageFileList = new ArrayList<>();
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                switch (fileNote){
                    case "companyLicense":
                        mv.addObject("companyLicenseFile", fileInfo);
                        break;
                    case "logo":
                        mv.addObject("logoFile", fileInfo);
                        break;
                    case "promotionImage":
                        promotionImageFileList.add(fileInfo);
                        break;
                    case "productImage":
                        productImageFileList.add(fileInfo);
                        break;
                    default:
                        break;
                }
            }
            mv.addObject("promotionImageFileList", promotionImageFileList);
            mv.addObject("productImageFileList", productImageFileList);
        }
        mv.setViewName("/mng/exhibitor/participant/company/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ExhibitorDTO> mng_exhibitor_participant_company_selectSingle(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_selectSingle");
        //System.out.println(searchDTO.toString());

        ExhibitorDTO response = kibsMngService.processSelectExhibitorSingle(exhibitorDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/insert.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_insert(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_insert");
        //System.out.println(exhibitorDTO.toString());
        
        ResponseDTO responseDTO = kibsMngService.processInsertParticipantCompany(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_update");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateParticipantCompany(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_delete(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_delete");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteParticipantCompany(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/deleteCharge.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_deleteCharge(@RequestBody ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_deleteCharge");
        //System.out.println(onlineDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteCharge(chargePersonDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/deleteOnline.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_deleteOnline(@RequestBody OnlineDTO onlineDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_deleteOnline");
        //System.out.println(onlineDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteOnline(onlineDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/deleteDisplay.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_deleteDisplay(@RequestBody DisplayDTO displayDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_deleteDisplay");
        //System.out.println(displayDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteDisplay(displayDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/deleteFile.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_deleteFile(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_deleteFile");
        //System.out.println(fileDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteFile(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_visitor() {
        System.out.println("KibsMngController > mng_exhibitor_visitor");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/participant/visitor");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<VisitorDTO>> mng_exhibitor_visitor_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_visitor_selectList");
        //System.out.println(searchDTO.toString());

        List<VisitorDTO> responseList = kibsMngService.processSelectVisitorList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_visitor_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_visitor_detail");
        ModelAndView mv = new ModelAndView();

        if(seq != null && !"".equals(seq)){
            VisitorDTO info = kibsMngService.processSelectVisitorSingle(seq);
            mv.addObject("info", info);

            List<PartnerDTO> partnerList = kibsMngService.processSelectPartnerList(info.getSeq());
            mv.addObject("partnerList", partnerList);
        }

        mv.setViewName("/mng/exhibitor/participant/visitor/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_visitor_update(@RequestBody VisitorDTO visitorDTO) {
        System.out.println("KibsController > mng_exhibitor_visitor_update");
        ResponseDTO response = kibsMngService.processUpdateVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_visitor_delete(@RequestBody VisitorDTO visitorDTO) {
        System.out.println("KibsController > mng_exhibitor_visitor_delete");
        ResponseDTO response = kibsMngService.processDeleteVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "'/mng/exhibitor/participant/visitor/deletePartner.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_visitor_deletePartner(@RequestBody PartnerDTO partnerDTO) {
        System.out.println("KibsController > mng_exhibitor_visitor_deletePartner");
        ResponseDTO response = kibsMngService.processDeletePartner(partnerDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/member.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_participant_member() {
        System.out.println("KibsMngController > mng_exhibitor_participant_member");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/participant/member");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/participant/member/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MemberDTO>> mng_exhibitor_participant_member_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_participant_member_selectList");
        //System.out.println(searchDTO.toString());

        List<MemberDTO> responseList = kibsMngService.processSelectMemberList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/member/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_member_detail() {
        System.out.println("KibsMngController > mng_exhibitor_member_detail");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/participant/member/detail");
        return mv;
    }

    //***************************************************************************
    // exhibitor/transfer Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/exhibitor/transfer/company.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_transfer_company() {
        System.out.println("KibsMngController > mng_exhibitor_transfer_company");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/transfer/company");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_transfer_company_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_transfer_company_detail");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            /* 전시업체정보 */
            ExhibitorDTO requestDto = new ExhibitorDTO();
            requestDto.setSeq(seq);
            ExhibitorDTO info = kibsMngService.processSelectExhibitorSingle(requestDto);
            mv.addObject("info", info);

            /* 담당자정보*/
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(seq);
            List<ChargePersonDTO> chargeList = kibsMngService.processSelectChargeList(chargeReq);
            mv.addObject("chargeList", chargeList);

            /* 전시정보 */
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(seq);
            List<DisplayDTO> displayList = kibsMngService.processSelectDisplayList(displayReq);
            mv.addObject("displayList", displayList);

            /* 온라인정보 */
            OnlineDTO onlineReq = new OnlineDTO();
            onlineReq.setId(seq);
            List<OnlineDTO> onlineList = kibsMngService.processSelectOnlineList(onlineReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerDTO buyerReq = new BuyerDTO();
            buyerReq.setId(seq);
            List<BuyerDTO> buyerList = kibsMngService.processSelectBuyerList(buyerReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            List<FileDTO> promotionImageFileList = new ArrayList<>();
            List<FileDTO> productImageFileList = new ArrayList<>();
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                switch (fileNote){
                    case "companyLicense":
                        mv.addObject("companyLicenseFile", fileInfo);
                        break;
                    case "logo":
                        mv.addObject("logoFile", fileInfo);
                        break;
                    case "promotionImage":
                        promotionImageFileList.add(fileInfo);
                        break;
                    case "productImage":
                        productImageFileList.add(fileInfo);
                        break;
                    default:
                        break;
                }
            }
            mv.addObject("promotionImageFileList", promotionImageFileList);
            mv.addObject("productImageFileList", productImageFileList);
        }
        mv.setViewName("/mng/exhibitor/transfer/company/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorDTO>> mng_exhibitor_transfer_company_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_transfer_company_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorDTO> responseList = kibsMngService.processSelectListTransferCompany(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/company/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ExhibitorDTO> mng_exhibitor_transfer_company_selectSingle(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_transfer_company_selectSingle");
        //System.out.println(searchDTO.toString());

        ExhibitorDTO response = kibsMngService.processSelectExhibitorSingle(exhibitorDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/company/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_transfer_company_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_transfer_company_update");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateTransferCompany(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/visitor.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_transfer_visitor() {
        System.out.println("KibsMngController > mng_exhibitor_transfer_visitor");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/transfer/visitor");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/visitor/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<VisitorDTO>> mng_exhibitor_transfer_visitor_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_transfer_visitor_selectList");
        //System.out.println(searchDTO.toString());

        List<VisitorDTO> responseList = kibsMngService.processSelectListTransferVisitor(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/visitor/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_transfer_visitor_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_transfer_visitor_detail");
        ModelAndView mv = new ModelAndView();

        if(seq != null && !"".equals(seq)){
            VisitorDTO info = kibsMngService.processSelectVisitorSingle(seq);
            mv.addObject("info", info);

            List<PartnerDTO> partnerList = kibsMngService.processSelectPartnerList(info.getSeq());
            mv.addObject("partnerList", partnerList);
        }

        mv.setViewName("/mng/exhibitor/transfer/visitor/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/transfer/visitor/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_transfer_visitor_update(@RequestBody VisitorDTO visitorDTO) {
        System.out.println("KibsController > mng_exhibitor_transfer_visitor_update");
        ResponseDTO response = kibsMngService.processUpdateTransferVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //***************************************************************************
    // exhibitor/application Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/exhibitor/application/booth.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_booth() {
        System.out.println("KibsMngController > mng_exhibitor_application_booth");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/booth");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/booth/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorDTO>> mng_exhibitor_application_booth_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_booth_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorDTO> responseList = kibsMngService.processSelectBoothList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/booth/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_booth_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_booth_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorDTO exhibitorInfo = kibsMngService.processSelectBoothSingle(seq);
            mv.addObject("info", exhibitorInfo);
        }
        mv.setViewName("/mng/exhibitor/application/booth/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/booth/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_booth_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mng_exhibitor_application_booth_update");
        ResponseDTO response = kibsMngService.processUpdateBooth(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/sign.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_sign() {
        System.out.println("KibsMngController > mng_exhibitor_application_sign");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/sign");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/sign/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorDTO>> mng_exhibitor_application_sign_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_sign_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorDTO> responseList = kibsMngService.processSelectSignList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/sign/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_sign_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_sign_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorDTO exhibitorInfo = kibsMngService.processSelectSignSingle(seq);
            mv.addObject("info", exhibitorInfo);
        }
        mv.setViewName("/mng/exhibitor/application/sign/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/sign/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_sign_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mng_exhibitor_application_sign_update");
        ResponseDTO response = kibsMngService.processUpdateSign(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/utility.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_utility() {
        System.out.println("KibsMngController > mng_exhibitor_application_utility");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/utility");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/utility/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorDTO>> mng_exhibitor_application_utility_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_utility_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorDTO> responseList = kibsMngService.processSelectUtilityList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/utility/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_utility_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_utility_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorDTO exhibitorInfo = kibsMngService.processSelectUtilitySingle(seq);
            mv.addObject("info", exhibitorInfo);
        }
        mv.setViewName("/mng/exhibitor/application/utility/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/utility/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_utility_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mng_exhibitor_application_utility_update");
        ResponseDTO response = kibsMngService.processUpdateUtility(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/pass.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_pass() {
        System.out.println("KibsMngController > mng_exhibitor_application_pass");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/pass");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/pass/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngPassDTO>> mng_exhibitor_application_pass_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_pass_selectList");
        //System.out.println(searchDTO.toString());

        List<MngPassDTO> responseList = kibsMngService.processSelectPassList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/pass/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_pass_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_pass_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngPassDTO> passList = kibsMngService.processSelectPassDetailList(seq);
            mv.addObject("passList", passList);
            mv.addObject("exSeq", seq);
        }
        mv.setViewName("/mng/exhibitor/application/pass/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/pass/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_pass_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mng_exhibitor_application_pass_update");
        ResponseDTO response = kibsMngService.processUpdatePass(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/pass/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_pass_delete(@RequestBody PassDTO passDTO) {
        System.out.println("KibsController > mng_exhibitor_application_pass_delete");
        ResponseDTO response = kibsMngService.processDeletePass(passDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/buyer.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_buyer() {
        System.out.println("KibsMngController > mng_exhibitor_application_buyer");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/buyer");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/buyer/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngBuyerDTO>> mng_exhibitor_application_buyer_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_buyer_selectList");
        //System.out.println(searchDTO.toString());

        List<MngBuyerDTO> responseList = kibsMngService.processSelectMngBuyerList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/buyer/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_buyer_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_buyer_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngBuyerDTO> buyerList = kibsMngService.processSelectBuyerDetailList(seq);
            mv.addObject("buyerList", buyerList);
            mv.addObject("exSeq", seq);
        }
        mv.setViewName("/mng/exhibitor/application/buyer/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/buyer/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_buyer_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mng_exhibitor_application_buyer_update");
        ResponseDTO response = kibsMngService.processUpdateBuyer(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/buyer/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_buyer_delete(@RequestBody BuyerDTO buyerDTO) {
        System.out.println("KibsController > mng_exhibitor_application_buyer_delete");
        ResponseDTO response = kibsMngService.processDeleteBuyer(buyerDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/gift.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_gift() {
        System.out.println("KibsMngController > mng_exhibitor_application_gift");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/gift");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/gift/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngGiftDTO>> mng_exhibitor_application_gift_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_gift_selectList");
        //System.out.println(searchDTO.toString());

        List<MngGiftDTO> responseList = kibsMngService.processSelectMngGiftList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/gift/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_gift_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_gift_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngGiftDTO> giftList = kibsMngService.processSelectGiftDetailList(seq);
            mv.addObject("giftList", giftList);
            mv.addObject("exSeq", seq);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            List<FileDTO> giftPhotoFileList = new ArrayList<>();
            List<FileDTO> giftCompanyLogoList = new ArrayList<>();
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                switch (fileNote){
                    case "giftPhoto":
                        giftPhotoFileList.add(fileInfo);
                        break;
                    case "giftCompanyLogo":
                        giftCompanyLogoList.add(fileInfo);
                        break;
                    default:
                        break;
                }
            }
            mv.addObject("giftPhotoFileList", giftPhotoFileList);
            mv.addObject("giftCompanyLogoList", giftCompanyLogoList);
        }
        mv.setViewName("/mng/exhibitor/application/gift/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/gift/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_gift_update(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mng_exhibitor_application_gift_update");
        ResponseDTO response = kibsMngService.processUpdateGift(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/gift/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_gift_delete(@RequestBody GiftDTO giftDTO) {
        System.out.println("KibsController > mng_exhibitor_application_gift_delete");
        ResponseDTO response = kibsMngService.processDeleteGift(giftDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/banner.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_banner() {
        System.out.println("KibsMngController > mng_exhibitor_application_banner");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/banner");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/banner/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_banner_detail() {
        System.out.println("KibsMngController > mng_exhibitor_application_banner_detail");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/banner/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/online.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_online() {
        System.out.println("KibsMngController > mng_exhibitor_application_online");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitor/application/online");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/online/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorDTO>> mng_exhibitor_application_online_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_online_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorDTO> responseList = kibsMngService.processSelectMngOnlineList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/online/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_application_online_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_online_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            /* 전시업체정보 */
            ExhibitorDTO requestDto = new ExhibitorDTO();
            requestDto.setSeq(seq);
            ExhibitorDTO info = kibsMngService.processSelectExhibitorSingle(requestDto);
            mv.addObject("info", info);

            /* 온라인정보 */
            OnlineDTO onlineReq = new OnlineDTO();
            onlineReq.setId(seq);
            List<OnlineDTO> onlineList = kibsMngService.processSelectOnlineList(onlineReq);
            mv.addObject("onlineList", onlineList);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            List<FileDTO> productImageFileList = new ArrayList<>();
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                if("productImage".equals(fileNote)){
                    productImageFileList.add(fileInfo);
                }
            }
            mv.addObject("productImageFileList", productImageFileList);
        }
        mv.setViewName("/mng/exhibitor/application/online/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitor/application/online/updateViewYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_online_updateViewYn(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_online_updateViewYn");
        System.out.println(exhibitorDTO.getSeq());

        ResponseDTO responseDTO = kibsMngService.processUpdateViewYn(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/updateApprovalStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_updateApprovalStatus(@RequestBody List<ExhibitorDTO> exhibitorList) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_updateApprovalStatus");

        ResponseDTO responseDTO = kibsMngService.processUpdateApprovalStatus(exhibitorList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/updatePrcYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_updatePrcYn(@RequestBody List<ExhibitorDTO> exhibitorList) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_updatePrcYn");

        ResponseDTO responseDTO = kibsMngService.processUpdatePrcYn(exhibitorList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //***************************************************************************
    // event/product Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/event/product.do", method = RequestMethod.GET)
    public ModelAndView mng_event_product() {
        System.out.println("KibsMngController > mng_event_product");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/event/product");
        return mv;
    }

    @RequestMapping(value = "/mng/event/product/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<AwardDTO>> mng_event_product_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_event_product_selectList");
        //System.out.println(searchDTO.toString());

        List<AwardDTO> responseList = kibsMngService.processSelectEventProductList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/event/product/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_event_product_detail(String seq) {
        System.out.println("KibsMngController > mng_event_product_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null && !"".equals(seq)){
            AwardDTO info = kibsMngService.processSelectEventProductSingle(seq);
            mv.addObject("info", info);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                if("productPhoto".equals(fileNote)){
                    mv.addObject("productPhotoFile", fileInfo);
                }
            }
        }
        mv.setViewName("/mng/event/product/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/event/product/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_event_product_update(@RequestBody AwardDTO awardDTO) {
        System.out.println("KibsMngController > mng_event_product_update");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateEventProduct(awardDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/event/product/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_event_product_delete(@RequestBody AwardDTO awardDTO) {
        System.out.println("KibsMngController > mng_event_product_delete");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteEventProduct(awardDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //***************************************************************************
    // event/company Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/event/company.do", method = RequestMethod.GET)
    public ModelAndView mng_event_company() {
        System.out.println("KibsMngController > mng_event_company");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/event/company");
        return mv;
    }

    @RequestMapping(value = "/mng/event/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<CompanyDTO>> mng_event_company_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_event_company_selectList");
        //System.out.println(searchDTO.toString());

        List<CompanyDTO> responseList = kibsMngService.processSelectEventCompanyList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/event/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_event_company_detail(String seq) {
        System.out.println("KibsMngController > mng_event_company_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null && !"".equals(seq)){
            CompanyDTO info = kibsMngService.processSelectEventCompanySingle(seq);
            mv.addObject("info", info);
        }
        mv.setViewName("/mng/event/company/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/event/company/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_event_company_update(@RequestBody CompanyDTO companyDTO) {
        System.out.println("KibsMngController > mng_event_company_update");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateEventCompany(companyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/event/company/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_event_company_delete(@RequestBody CompanyDTO companyDTO) {
        System.out.println("KibsMngController > mng_event_company_delete");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteEventCompany(companyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //***************************************************************************
    // center/board Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/center/board/notice.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_notice() {
        System.out.println("KibsMngController > mng_center_board_notice");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/notice");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/notice/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<NoticeDTO>> mng_center_board_notice_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_notice_selectList");
        //System.out.println(searchDTO.toString());

        List<NoticeDTO> responseList = kibsMngService.processSelectNoticeList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/notice/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<NoticeDTO> mng_center_board_notice_selectSingle(@RequestBody NoticeDTO noticeDTO) {
        System.out.println("KibsMngController > mng_center_board_notice_selectSingle");
        //System.out.println(searchDTO.toString());

        NoticeDTO response = kibsMngService.processSelectNoticeSingle(noticeDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/notice/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_notice_modifySave(@RequestBody NoticeDTO noticeDTO) {
        System.out.println("KibsMngController > mng_center_board_notice_modifySave");
        System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateBoardNotice(noticeDTO);

        String fileIdList = noticeDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(noticeDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/notice/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_notice_insertSave(@RequestBody NoticeDTO noticeDTO) {
        System.out.println("KibsMngController > mng_center_board_notice_insertSave");
        System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertBoardNotice(noticeDTO);

        String fileIdList = noticeDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/newsletter_ko.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_newsletter_ko() {
        System.out.println("KibsMngController > mng_center_board_newsletter_ko");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/newsletter_ko");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/newsletter/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<NewsletterDTO>> mng_center_board_newsletter_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_newsletter_selectList");
        //System.out.println(searchDTO.toString());

        List<NewsletterDTO> responseList = kibsMngService.processSelectNewsletterList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/newsletter/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<NewsletterDTO> mng_center_board_newsletter_selectSingle(@RequestBody NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngController > mng_center_board_newsletter_selectSingle");
        //System.out.println(newsletterDTO.toString());

        NewsletterDTO response = kibsMngService.processSelectNewsletterSingle(newsletterDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/newsletter/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_newsletter_delete(@RequestBody NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngController > mng_center_board_newsletter_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteNewsletter(newsletterDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/newsletter_en.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_newsletter_en() {
        System.out.println("KibsMngController > mng_center_board_newsletter_en");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/newsletter_en");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/press.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_press() {
        System.out.println("KibsMngController > mng_center_board_press");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/press");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/press/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<PressDTO>> mng_center_board_press_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_press_selectList");
        //System.out.println(searchDTO.toString());

        List<PressDTO> responseList = kibsMngService.processSelectPressList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/press/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PressDTO> mng_center_board_press_selectSingle(@RequestBody PressDTO pressDTO) {
        System.out.println("KibsMngController > mng_center_board_press_selectSingle");
        //System.out.println(newsletterDTO.toString());

        PressDTO response = kibsMngService.processSelectPressSingle(pressDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/press/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_press_delete(@RequestBody PressDTO pressDTO) {
        System.out.println("KibsMngController > mng_center_board_press_delete");

        ResponseDTO responseDTO = kibsMngService.processDeletePress(pressDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/press/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_press_modifySave(@RequestBody PressDTO pressDTO) {
        System.out.println("KibsMngController > mng_center_board_press_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdatePress(pressDTO);

        String fileIdList = pressDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(pressDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/press/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_press_insertSave(@RequestBody PressDTO pressDTO) {
        System.out.println("KibsMngController > mng_center_board_press_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertPress(pressDTO);

        String fileIdList = pressDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/faq.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_faq() {
        System.out.println("KibsMngController > mng_center_board_faq");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/faq");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/faq/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<FaqDTO>> mng_center_board_faq_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_faq_selectList");
        //System.out.println(searchDTO.toString());

        List<FaqDTO> responseList = kibsMngService.processSelectFaqList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/faq/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FaqDTO> mng_center_board_faq_selectSingle(@RequestBody FaqDTO faqDTO) {
        System.out.println("KibsMngController > mng_center_board_faq_selectSingle");
        //System.out.println(newsletterDTO.toString());

        FaqDTO response = kibsMngService.processSelectFaqSingle(faqDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/faq/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_faq_delete(@RequestBody FaqDTO faqDTO) {
        System.out.println("KibsMngController > mng_center_board_faq_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteFaq(faqDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/faq/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_faq_modifySave(@RequestBody FaqDTO faqDTO) {
        System.out.println("KibsMngController > mng_center_board_faq_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateFaq(faqDTO);

        /*
        String fileIdList = faqDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(faqDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }*/

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/faq/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_faq_insertSave(@RequestBody FaqDTO faqDTO) {
        System.out.println("KibsMngController > mng_center_board_faq_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertFaq(faqDTO);

        /*String fileIdList = faqDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }*/

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/column.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_column() {
        System.out.println("KibsMngController > mng_center_board_column");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/column");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/column/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ColumnDTO>> mng_center_board_column_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_column_selectList");
        //System.out.println(searchDTO.toString());

        List<ColumnDTO> responseList = kibsMngService.processSelectColumnList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/column/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ColumnDTO> mng_center_board_column_selectSingle(@RequestBody ColumnDTO columnDTO) {
        System.out.println("KibsMngController > mng_center_board_column_selectSingle");
        //System.out.println(newsletterDTO.toString());

        ColumnDTO response = kibsMngService.processSelectColumnSingle(columnDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/column/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_column_delete(@RequestBody ColumnDTO columnDTO) {
        System.out.println("KibsMngController > mng_center_board_column_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteColumn(columnDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/column/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_column_modifySave(@RequestBody ColumnDTO columnDTO) {
        System.out.println("KibsMngController > mng_center_board_column_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateColumn(columnDTO);

        String fileIdList = columnDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(columnDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/column/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_column_insertSave(@RequestBody ColumnDTO columnDTO) {
        System.out.println("KibsMngController > mng_center_board_column_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertColumn(columnDTO);

        String fileIdList = columnDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/brochure.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_brochure() {
        System.out.println("KibsMngController > mng_center_board_brochure");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/brochure");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/brochure/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<BrochureDTO>> mng_center_board_brochure_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_brochure_selectList");
        //System.out.println(searchDTO.toString());

        List<BrochureDTO> responseList = kibsMngService.processSelectBrochureList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/brochure/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BrochureDTO> mng_center_board_brochure_selectSingle(@RequestBody BrochureDTO brochureDTO) {
        System.out.println("KibsMngController > mng_center_board_brochure_selectSingle");
        //System.out.println(newsletterDTO.toString());

        BrochureDTO response = kibsMngService.processSelectBrochureSingle(brochureDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/brochure/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_brochure_delete(@RequestBody BrochureDTO brochureDTO) {
        System.out.println("KibsMngController > mng_center_board_brochure_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteBrochure(brochureDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/brochure/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_brochure_modifySave(@RequestBody BrochureDTO brochureDTO) {
        System.out.println("KibsMngController > mng_center_board_brochure_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateBrochure(brochureDTO);

        String fileIdList = brochureDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(brochureDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/brochure/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_brochure_insertSave(@RequestBody BrochureDTO brochureDTO) {
        System.out.println("KibsMngController > mng_center_board_brochure_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertBrochure(brochureDTO);

        String fileIdList = brochureDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/dataroom.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_dataroom() {
        System.out.println("KibsMngController > mng_center_board_dataroom");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/dataroom");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/dataroom/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<DataroomDTO>> mng_center_board_dataroom_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_dataroom_selectList");
        //System.out.println(searchDTO.toString());

        List<DataroomDTO> responseList = kibsMngService.processSelectDataroomList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/dataroom/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DataroomDTO> mng_center_board_dataroom_selectSingle(@RequestBody DataroomDTO dataroomDTO) {
        System.out.println("KibsMngController > mng_center_board_dataroom_selectSingle");
        //System.out.println(newsletterDTO.toString());

        DataroomDTO response = kibsMngService.processSelectDataroomSingle(dataroomDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/dataroom/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_dataroom_delete(@RequestBody DataroomDTO dataroomDTO) {
        System.out.println("KibsMngController > mng_center_board_dataroom_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteDataroom(dataroomDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/dataroom/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_dataroom_modifySave(@RequestBody DataroomDTO dataroomDTO) {
        System.out.println("KibsMngController > mng_center_board_dataroom_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateDataroom(dataroomDTO);

        String fileIdList = dataroomDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(dataroomDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/dataroom/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_dataroom_insertSave(@RequestBody DataroomDTO dataroomDTO) {
        System.out.println("KibsMngController > mng_center_board_dataroom_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertDataroom(dataroomDTO);

        String fileIdList = dataroomDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/gallery.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_gallery() {
        System.out.println("KibsMngController > mng_center_board_gallery");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/gallery");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/gallery/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<GalleryDTO>> mng_center_board_gallery_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_gallery_selectList");
        //System.out.println(searchDTO.toString());

        List<GalleryDTO> responseList = kibsMngService.processSelectGalleryList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/gallery/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GalleryDTO> mng_center_board_gallery_selectSingle(@RequestBody GalleryDTO galleryDTO) {
        System.out.println("KibsMngController > mng_center_board_gallery_selectSingle");
        //System.out.println(newsletterDTO.toString());

        GalleryDTO response = kibsMngService.processSelectGallerySingle(galleryDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/gallery/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_gallery_delete(@RequestBody GalleryDTO galleryDTO) {
        System.out.println("KibsMngController > mng_center_board_gallery_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteGallery(galleryDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/gallery/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_gallery_modifySave(@RequestBody GalleryDTO galleryDTO) {
        System.out.println("KibsMngController > mng_center_board_gallery_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateGallery(galleryDTO);

        String fileIdList = galleryDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(galleryDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/gallery/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_gallery_insertSave(@RequestBody GalleryDTO galleryDTO) {
        System.out.println("KibsMngController > mng_center_board_gallery_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertGallery(galleryDTO);

        String fileIdList = galleryDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/kibstv.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_kibstv() {
        System.out.println("KibsMngController > mng_center_board_kibstv");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/kibstv");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/kibstv/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<KibstvDTO>> mng_center_board_kibstv_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_kibstv_selectList");
        //System.out.println(searchDTO.toString());

        List<KibstvDTO> responseList = kibsMngService.processSelectKibstvList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/kibstv/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<KibstvDTO> mng_center_board_kibstv_selectSingle(@RequestBody KibstvDTO kibstvDTO) {
        System.out.println("KibsMngController > mng_center_board_kibstv_selectSingle");
        //System.out.println(newsletterDTO.toString());

        KibstvDTO response = kibsMngService.processSelectKibstvSingle(kibstvDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/kibstv/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_kibstv_delete(@RequestBody KibstvDTO kibstvDTO) {
        System.out.println("KibsMngController > mng_center_board_kibstv_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteKibstv(kibstvDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/kibstv/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_kibstv_modifySave(@RequestBody KibstvDTO kibstvDTO) {
        System.out.println("KibsMngController > mng_center_board_kibstv_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateKibstv(kibstvDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/kibstv/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_kibstv_insertSave(@RequestBody KibstvDTO kibstvDTO) {
        System.out.println("KibsMngController > mng_center_board_kibstv_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertKibstv(kibstvDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/notice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_notice_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_notice_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            NoticeDTO requestDto = new NoticeDTO();
            requestDto.setId(seq);
            NoticeDTO noticeInfo = kibsMngService.processSelectNoticeSingle(requestDto);
            mv.addObject("info", noticeInfo);

            if(noticeInfo.getFileIdList() != null && !"".equals(noticeInfo.getFileIdList())){
                String[] fileIdList = noticeInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for(int i=0; i<fileIdList.length; i++){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdList[i]);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/notice/detail");
        return mv;
    }

    @RequestMapping(value = "/file/upload/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<FileDTO>> file_upload_selectList(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsMngController > mng_center_board_kibstv_selectSingle");
        //System.out.println(newsletterDTO.toString());

        List<FileDTO> fileList = kibsMngService.processSelectFileList(fileDTO);

        return new ResponseEntity<>(fileList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/notice/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_notice_delete(@RequestBody NoticeDTO noticeDTO) {
        System.out.println("KibsMngController > mng_center_board_notice_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteBoardNotice(noticeDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/newsletter/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_newsletter_detail(String lang, String seq) {
        System.out.println("KibsMngController > mng_center_board_newsletter_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            NewsletterDTO requestDto = new NewsletterDTO();
            requestDto.setId(seq);
            requestDto.setLang(lang);
            NewsletterDTO resInfo = kibsMngService.processSelectNewsletterSingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for(int i=0; i<fileIdList.length; i++){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdList[i]);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        if("KO".equals(lang)){
            mv.setViewName("/mng/center/board/newsletter_ko/detail");
        }else{
            mv.setViewName("/mng/center/board/newsletter_en/detail");
        }
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/newsletter/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_newsletter_modifySave(@RequestBody NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngController > mng_center_board_newsletter_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateNewsletter(newsletterDTO);

        String fileIdList = newsletterDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(newsletterDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/newsletter/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_newsletter_insertSave(@RequestBody NewsletterDTO newsletterDTO) {
        System.out.println("KibsMngController > mng_center_board_newsletter_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertNewsletter(newsletterDTO);

        String fileIdList = newsletterDTO.getFileIdList();
        if(fileIdList != null && !"".equals(fileIdList)){
            String[] fileIdSplit = fileIdList.split(",");
            for(int i=0; i<fileIdSplit.length; i++){
                if(!"".equals(fileIdSplit[i])){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileIdSplit[i]);
                    fileDTO.setUserId(responseDTO.getCustomValue());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/press/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_press_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_press_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            PressDTO requestDto = new PressDTO();
            requestDto.setId(seq);
            PressDTO resInfo = kibsMngService.processSelectPressSingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for (String id : fileIdList) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(id);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/press/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/faq/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_faq_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_faq_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            FaqDTO requestDto = new FaqDTO();
            requestDto.setId(seq);
            FaqDTO resInfo = kibsMngService.processSelectFaqSingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for (String id : fileIdList) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(id);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/faq/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/column/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_column_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_column_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            ColumnDTO requestDto = new ColumnDTO();
            requestDto.setId(seq);
            ColumnDTO resInfo = kibsMngService.processSelectColumnSingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for (String id : fileIdList) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(id);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/column/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/brochure/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_brochure_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_brochure_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            BrochureDTO requestDto = new BrochureDTO();
            requestDto.setId(seq);
            BrochureDTO resInfo = kibsMngService.processSelectBrochureSingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for (String id : fileIdList) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(id);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/brochure/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/dataroom/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_dataroom_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_dataroom_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            DataroomDTO requestDto = new DataroomDTO();
            requestDto.setId(seq);
            DataroomDTO resInfo = kibsMngService.processSelectDataroomSingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for (String id : fileIdList) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(id);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/dataroom/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/gallery/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_gallery_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_gallery_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            GalleryDTO requestDto = new GalleryDTO();
            requestDto.setId(seq);
            GalleryDTO resInfo = kibsMngService.processSelectGallerySingle(requestDto);
            mv.addObject("info",resInfo);

            if(resInfo.getFileIdList() != null && !"".equals(resInfo.getFileIdList())){
                String[] fileIdList = resInfo.getFileIdList().split(",");
                List<FileDTO> fileList = new ArrayList<>();
                for (String id : fileIdList) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(id);
                    FileDTO fileInfo = kibsMngService.processSelectFileInfo(fileDTO);
                    fileList.add(fileInfo);
                }
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/mng/center/board/gallery/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/kibstv/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_kibstv_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_kibstv_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !"".equals(seq)){
            KibstvDTO requestDto = new KibstvDTO();
            requestDto.setId(seq);
            KibstvDTO response = kibsMngService.processSelectKibstvSingle(requestDto);
            mv.addObject("info",response);
        }
        mv.setViewName("/mng/center/board/kibstv/detail");
        return mv;
    }

    //***************************************************************************
    // center/popup Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/center/popup/add.do", method = RequestMethod.GET)
    public ModelAndView mng_center_popup_add() {
        System.out.println("KibsMngController > mng_center_popup_add");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/popup/add");
        return mv;
    }

    @RequestMapping(value = "/mng/center/popup/add/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<PopupDTO>> mng_center_popup_add_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_popup_add_selectList");
        //System.out.println(searchDTO.toString());

        List<PopupDTO> responseList = kibsMngService.processSelectPopupList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/popup/add/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_popup_add_detail(String seq) {
        System.out.println("KibsMngController > mng_center_popup_add_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null && !"".equals(seq)){
            PopupDTO response = kibsMngService.processSelectPopupSingle(seq);
            mv.addObject("info",response);
        }

        mv.setViewName("/mng/center/popup/add/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/popup/add/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_popup_add_modifySave(@RequestBody PopupDTO popupDTO) {
        System.out.println("KibsMngController > mng_center_popup_add_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdatePopup(popupDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/popup/add/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_popup_add_insertSave(@RequestBody PopupDTO popupDTO) {
        System.out.println("KibsMngController > mng_center_popup_add_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertPopup(popupDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/popup/add/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_popup_add_delete(@RequestBody PopupDTO popupDTO) {
        System.out.println("KibsMngController > mng_center_popup_add_delete");

        ResponseDTO responseDTO = kibsMngService.processDeletePopup(popupDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //***************************************************************************
    // center/document Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/center/document/download.do", method = RequestMethod.GET)
    public ModelAndView mng_center_document_download() {
        System.out.println("KibsMngController > mng_center_document_download");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/document/download");
        return mv;
    }

    //***************************************************************************
    // center/edm Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/center/edm/list.do", method = RequestMethod.GET)
    public ModelAndView mng_center_edm_list() {
        System.out.println("KibsMngController > mng_center_edm_list");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/edm/list");
        return mv;
    }

    @RequestMapping(value = "/mng/center/edm/list/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_edm_list_detail() {
        System.out.println("KibsMngController > mng_center_edm_list_detail");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/edm/list/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/edm/list/selectHstList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<EdmHstDTO>> mng_center_edm_list_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_edm_list_selectList");
        //System.out.println(searchDTO.toString());

        List<EdmHstDTO> responseList = kibsMngService.processSelectEdmHstList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/edm/list/insertHst.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_edm_list_insertHst(@RequestBody EdmHstDTO edmHstDTO) {
        System.out.println("KibsMngController > mng_center_edm_list_insertHst");
        //System.out.println(participantCompanyDTO.toString());

        ResponseDTO responseDto = kibsMngService.processInsertEdmHst(edmHstDTO);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/edm/list/deleteHst.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_edm_list_deleteHst(@RequestBody EdmHstDTO edmHstDTO) {
        System.out.println("KibsMngController > mng_center_edm_list_deleteHst");
        //System.out.println(participantCompanyDTO.toString());

        ResponseDTO responseDto = kibsMngService.processDeleteEdmHst(edmHstDTO);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //***************************************************************************
    // request/management Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/request/management/list.do", method = RequestMethod.GET)
    public ModelAndView mng_request_management_list() {
        System.out.println("KibsMngController > mng_request_management_list");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/request/management/list");
        return mv;
    }

    @RequestMapping(value = "/mng/request/management/list/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<RequestDTO>> mng_request_management_list_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("PosterMngController > mng_request_management_list_selectList");
        //System.System.out.println(searchDTO.toString());

        List<RequestDTO> responseList = kibsMngService.processSelectRequestList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_request_management_list_detail(String seq) {
        System.out.println("KibsMngController > mng_request_management_list_detail");
        ModelAndView mv = new ModelAndView();

        if(seq != null && !"".equals(seq)){
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setSeq(seq);
            RequestDTO info = kibsMngService.processSelectRequestSingle(requestDTO);
            if(info != null){
                mv.addObject("info", info);

                List<ReplyDTO> replyList = kibsMngService.processSelectReplyList(seq);
                mv.addObject("replyList", replyList);

                FileDTO fileDTO = new FileDTO();
                fileDTO.setUserId(info.getSeq());
                List<FileDTO> fileList = commService.processSelectFileParentSeqList(fileDTO);
                mv.addObject("fileList", fileList);
            }
        }

        mv.setViewName("/mng/request/management/list/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/request/management/list/insert.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_insert(@RequestBody RequestDTO requestDTO) {
        System.out.println("KibsMngController > mng_request_management_list_insert");
        //System.System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertRequest(requestDTO);

        updateFileParentSeq(requestDTO.getSeq(), requestDTO.getFileIdList());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_update(@RequestBody RequestDTO requestDTO) {
        System.out.println("KibsMngController > mng_request_management_list_update");
        //System.System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateRequest(requestDTO);

        updateFileParentSeq(requestDTO.getSeq(), requestDTO.getFileIdList());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_delete(@RequestBody RequestDTO requestDTO) {
        System.out.println("KibsMngController > mng_request_management_list_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteRequest(requestDTO);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(requestDTO.getSeq());
        commService.processUpdateFileDeleteUseN(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/reply/insert.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_reply_insert(@RequestBody ReplyDTO replyDTO) {
        System.out.println("KibsMngController > mng_request_management_list_reply_insert");
        //System.System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertReply(replyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/reply/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_reply_delete(@RequestBody ReplyDTO replyDTO) {
        System.out.println("KibsMngController > mng_request_management_list_reply_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteReply(replyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/progress/step/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_progress_step_update(@RequestBody List<RequestDTO> requestList) {
        System.out.println("KibsMngController > mng_request_management_list_progress_step_update");
        //System.System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateRequestProgressStep(requestList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/request/management/list/complete/expect/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_request_management_list_complete_expect_update(@RequestBody List<RequestDTO> requestList) {
        System.out.println("KibsMngController > mng_request_management_list_complete_expect_update");
        //System.System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateRequestCompleteExpect(requestList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/mng/participant/company/list.do", method = RequestMethod.GET)
    public ModelAndView mng_participant_company_list() {
        System.out.println("KibsMngController > mng_participant_company_list");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/participant/company/list");
        return mv;
    }

    @RequestMapping(value = "/mng/participant/company/list/save.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_participant_company_list_save(@RequestBody ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_save");
        //System.out.println(participantCompanyDTO.toString());

        ResponseDTO responseDto = kibsMngService.processInsertExhibitorComp(participantCompanyDTO);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/participant/company/list/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ParticipantCompanyDTO>> mng_participant_company_list_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_selectList");
        //System.out.println(searchDTO.toString());

        List<ParticipantCompanyDTO> responseList = kibsMngService.processSelectListExhibitorComp(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/participant/company/list/deleteList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_participant_company_list_deleteList(@RequestBody ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_deleteList");
        //System.out.println(participantCompanyDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteExhibitorComp(participantCompanyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/participant/company/list/detail.do", method = RequestMethod.POST)
    public ModelAndView mng_participant_company_list_detail(ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_detail");
        //System.out.println(participantCompanyDTO.toString());
        ModelAndView mv = new ModelAndView();
        ParticipantCompanyDTO responseDTO = kibsMngService.processSelectExhibitorComp(participantCompanyDTO);
        mv.addObject("info", responseDTO);
        mv.setViewName("/mng/participant/company/list/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/participant/company/list/select.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ParticipantCompanyDTO> mng_participant_company_list_select(@RequestBody ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_select");
        //System.out.println(participantCompanyDTO.toString());

        ParticipantCompanyDTO responseDTO = kibsMngService.processSelectExhibitorComp(participantCompanyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/participant/company/list/buyerSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BuyerDTO> mng_participant_company_list_buyerSingle(@RequestBody BuyerDTO buyerDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_buyerSingle");
        //System.out.println(buyerDTO.toString());

        BuyerDTO responseDTO = kibsMngService.processSelectBuyerSingle(buyerDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/participant/company/list/deleteBuyer.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_participant_company_list_deleteBuyer(@RequestBody BuyerDTO buyerDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_deleteBuyer");
        //System.out.println(buyerDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteBuyer(buyerDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/participant/company/list/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_participant_company_list_modifySave(@RequestBody ParticipantCompanyDTO participantCompanyDTO) {
        System.out.println("KibsMngController > mng_participant_company_list_modifySave");
        //System.out.println(participantCompanyDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorComp(participantCompanyDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private void updateFileParentSeq(String parentSeq, String fileSeqList){

        if(fileSeqList != null && !"".equals(fileSeqList)){
            String[] fileSeqSplit = fileSeqList.split(",");
            for (String fileSeq : fileSeqSplit) {
                if (!"".equals(fileSeq)) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileSeq);
                    fileDTO.setUserId(parentSeq);
                    commService.processUpdateFileParentSeq(fileDTO);
                }
            }
        }

    }

    /*********************** file upload ***********************/

    /**
     * 에디터 이미지 업로드
     * @param image 파일 객체
     * @return 업로드된 파일명
     */
    @PostMapping("/tui-editor/image-upload")
    @ResponseBody
    public String uploadEditorImage(@RequestParam final MultipartFile image) {
        if (image.isEmpty()) {
            return "";
        }

        /*System.out.println("파일이름 : " + image.getOriginalFilename());
        System.out.println("파일타입 : " + image.getContentType());
        System.out.println("파일크기 : " + image.getSize());*/

        String orgFilename = image.getOriginalFilename();                                         // 원본 파일명
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String extension = Objects.requireNonNull(orgFilename).substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
        String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
        String fileFullPath = "/usr/local/tomcat/webapps/upload/editor" + "/" + saveFilename;                      // 디스크에 저장할 파일의 전체 경로

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File("/usr/local/tomcat/webapps/upload/editor");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return saveFilename;

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }

    /**
     * 디스크에 업로드된 파일을 byte[]로 반환
     * @param filename 디스크에 업로드된 파일명
     * @return image byte array
     */
    @GetMapping(value = "/tui-editor/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    @ResponseBody
    public byte[] printEditorImage(@RequestParam final String filename) {

        // 업로드된 파일의 전체 경로
        String fileFullPath = "/usr/local/tomcat/webapps/upload/editor" + "/" + filename;

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (!uploadedFile.exists()) {
            throw new RuntimeException();
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            return Files.readAllBytes(uploadedFile.toPath());

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/file/upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> file_upload(HttpServletRequest uploadFile) throws UnsupportedEncodingException {
        System.out.println("KibsMngController > file_upload");
        String gbn = uploadFile.getParameter("gbn");
        uploadFile.setCharacterEncoding("UTF-8");
        JSONObject response = new JSONObject();

        int size = 1024 * 1024 * 100; // 100M
        String file = "";
        String oriFile = "";

        try {
            String path = "";
            if("mail".equals(gbn)){
                path = ResourceUtils.getFile("/usr/local/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + gbn + "/").toPath().toString();
            }else{
                /* prod */
                path = ResourceUtils.getFile("/usr/local/tomcat/webapps/upload/" + gbn + "/").toPath().toString();

                /* Local */
                //path = ResourceUtils.getFile("C:/Users/slhge/project/Kibs/upload/" + gbn).toPath().toString();
            }

            File uploadPath = new File(path);

            if (!uploadPath.exists()) {
                try {
                    Files.createDirectories(uploadPath.toPath());
                } catch (IOException e) {
                    System.out.println("[mkdir error] : " + e.getMessage());
                }
            }

            /* 파일명 중복 방지 - UUID setting */
            //String uuid = UUID.randomUUID().toString();

            /* 폴더에 파일 업로드 */
            MultipartRequest multi = new MultipartRequest(uploadFile, uploadPath.getPath()+"/", size,
                    "UTF-8", new DefaultFileRenamePolicy());
            Enumeration files = multi.getFileNames();
            String str = (String)files.nextElement();

            file = multi.getFilesystemName(str);
            oriFile = multi.getOriginalFileName(str);

            file = appendSuffixName(uploadPath.getPath() , file, 1);

            // 업로드된 파일 객체 생성
            File oldFile = new File(uploadPath.getPath()  + File.separator + oriFile);

            // 실제 저장될 파일 객체 생성
            File newFile = new File(uploadPath.getPath() + File.separator + file);

            // 파일명 rename
            if(!oldFile.renameTo(newFile)){
                int read = 0;
                byte[] buf = new byte[1024];
                FileInputStream fin = null;
                FileOutputStream fout = null;
                //rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제
                buf = new byte[1024];
                fin = new FileInputStream(oldFile);
                fout = new FileOutputStream(newFile);
                read = 0;
                while((read=fin.read(buf,0,buf.length))!=-1){
                    fout.write(buf, 0, read);
                }

                fin.close();
                fout.close();
                oldFile.delete();
            }

            response.put("uploadPath", uploadPath.getPath());
            response.put("fileName", file);
            //response.put("fileNameOrigin", oriFile);

            System.out.println("[full file path] : " + uploadPath + File.separator + file);

        } catch (Exception e) {
            System.out.println("[upload file save error] : " + e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/file/useYn/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FileResponseDTO> file_useYn_update(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsMngController > file_useYn_update");
        System.out.println(fileDTO.toString());

        FileResponseDTO responseDTO = kibsMngService.processUpdateFileUseN(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public static String appendSuffixName(String path, String orgFileName, int seq) {
        String retFileName = "";
        // 파일이 존재하는지 확인한다.
        if (new File(path + File.separator + orgFileName).exists()) {
            int plusSeq = 1;

            String seqStr = "_" + seq;
            String firstFileName = orgFileName.substring(0,
                    orgFileName.lastIndexOf("."));
            String extName = orgFileName
                    .substring(orgFileName.lastIndexOf("."));

            // 만약 파일명에 _숫자가 들어간경우라면..
            if (orgFileName.lastIndexOf("_") != -1
                    && !firstFileName.endsWith("_")) {
                String numStr = orgFileName.substring(
                        orgFileName.lastIndexOf("_") + 1,
                        orgFileName.lastIndexOf(extName));
                try {
                    plusSeq = Integer.parseInt(numStr);
                    plusSeq = plusSeq + 1;

                    retFileName = firstFileName.substring(0,
                            firstFileName.lastIndexOf("_"))
                            + "_" + plusSeq + extName;
                } catch (NumberFormatException e) {
                    retFileName = firstFileName + seqStr + extName;
                    return appendSuffixName(path , retFileName, ++plusSeq);
                }

            } else {
                retFileName = firstFileName + seqStr + extName;
            }
            // 재귀
            return appendSuffixName(path , retFileName, ++plusSeq);
        } else {
            return orgFileName;
        }
    }

    @ResponseBody
    @GetMapping(value = "/board/uploadFileGet")
    public ResponseEntity<byte[]> board_uploadFileGet(@RequestParam("fileName") String fileName) {
        System.out.println("KibsMngController > board_uploadFileGet");
        //System.out.println("fileName : " + fileName);

        //String replaceFileName = fileName.replace("/",File.separator);

        File file = new File(fileName);

        ResponseEntity<byte[]> result = null;

        try {

            HttpHeaders header = new HttpHeaders();

        /*
        Files.probeContentType() 해당 파일의 Content 타입을 인식(image, text/plain ...)
        없으면 null 반환

        file.toPath() -> file 객체를 Path객체로 변환

        */
            //System.out.println("Files.content-type : " + Files.probeContentType(file.toPath()));
            header.add("Content-type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/file/upload/save.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FileResponseDTO> file_upload_save(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsMngController > file_upload_save");
        System.out.println(fileDTO.toString());

        FileResponseDTO responseDTO = kibsMngService.processInsertFileInfo(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/file/upload/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FileResponseDTO> file_upload_update(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsMngController > file_upload_update");
        System.out.println(fileDTO.toString());

        FileResponseDTO responseDTO = kibsMngService.processUpdateFileInfo(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /*********************** file download ***********************/

    @RequestMapping(value = "/file/download.do", method = RequestMethod.GET)
    public void board_downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("KibsMngController > board_downloadFile");
        String path = request.getParameter("path");
        path = path.replaceAll("\\\\", "/");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String file_repo = "";
        if("mail".equals(path)){
            file_repo = ResourceUtils.getFile("/usr/local/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + path + "/").toPath().toString();
        }else {
            // 파일 업로드된 경로
            file_repo = ResourceUtils.getFile("/usr/local/tomcat/webapps/upload/" + path + "/").toPath().toString();
        }
        // 서버에 실제 저장된 파일명
        //String filename = "20140819151221.zip" ;
        String fileName = request.getParameter("fileName");

        OutputStream out = response.getOutputStream();
        String downFile = file_repo + "/" + fileName;
        File f = new File(downFile);
        response.setHeader("Cache-Control", "no-cache");
        // 한글 파일명 처리
        //fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.addHeader("Content-disposition","attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ";");

        FileInputStream in = new FileInputStream(f);
        byte[] buffer = new byte[1024*8];
        while(true){
            int count = in.read(buffer);
            if(count == -1){
                break;
            }
            out.write(buffer,0,count);
        }
        in.close();
        out.close();
    }

    @RequestMapping(value = "/mng/directory/download.do", method = RequestMethod.GET)
    public void directory_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > directory_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // Workbook 생성
        try(XSSFWorkbook workbook = new XSSFWorkbook()){ // Excel 2007 이상

            /* 엑셀 그리기 */
            final String[] colNames_ex = {
                    "No", "승인여부", "입금여부", "회사명(국문)", "회사명(영문)",
                    "주소", "상세주소", "공장 주소", "공장 상세 주소", "대표자명",
                    "전화", "홈페이지", "FAX", "산업 분류", "산업 분류 기타",
                    "임직원수", "사업자등록번호", "블로그", "페이스북", "인스타그램",
                    "기타", "회사소개(국문)", "회사소개(영문)", "KIBS 참가목적(국문)", "KIBS 참가목적(영문)",
                    "성명", "직위", "부서", "전화번호", "휴대전화",
                    "이메일", "전시품목명", "브랜드명", "실물보트수", "전시품소개(국)",
                    "전시품소개(영)"
            };

            // 헤더 사이즈
            final int[] colWidths_ex = {
                    3000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000
            };

            // *** Style--------------------------------------------------
            //Font
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");	//글씨체
            fontHeader.setFontHeight((short)(9 * 20));	//사이즈
            fontHeader.setBold(true);	//볼드(굵게)
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");	//글씨체
            font9.setFontHeight((short)(9 * 20));	//사이즈
            // 엑셀 헤더 셋팅 (참가업체정보)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);
            // 엑셀 바디 셋팅 (참가업체정보)
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9);
            // 엑셀 헤더 셋팅 (담당자정보)
            CellStyle headerStyle_ch = workbook.createCellStyle();
            headerStyle_ch.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_ch.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_ch.setBorderRight(BorderStyle.THIN);
            headerStyle_ch.setBorderLeft(BorderStyle.THIN);
            headerStyle_ch.setBorderTop(BorderStyle.THIN);
            headerStyle_ch.setBorderBottom(BorderStyle.THIN);
            headerStyle_ch.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            headerStyle_ch.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_ch.setFont(fontHeader);
            // 엑셀 바디 셋팅 (담당자정보)
            CellStyle bodyStyle_ch = workbook.createCellStyle();
            bodyStyle_ch.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle_ch.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle_ch.setBorderRight(BorderStyle.THIN);
            bodyStyle_ch.setBorderLeft(BorderStyle.THIN);
            bodyStyle_ch.setBorderTop(BorderStyle.THIN);
            bodyStyle_ch.setBorderBottom(BorderStyle.THIN);
            bodyStyle_ch.setFont(font9);
            bodyStyle_ch.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (전시정보)
            CellStyle headerStyle_di = workbook.createCellStyle();
            headerStyle_di.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_di.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_di.setBorderRight(BorderStyle.THIN);
            headerStyle_di.setBorderLeft(BorderStyle.THIN);
            headerStyle_di.setBorderTop(BorderStyle.THIN);
            headerStyle_di.setBorderBottom(BorderStyle.THIN);
            headerStyle_di.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
            headerStyle_di.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_di.setFont(fontHeader);
            // 엑셀 바디 셋팅 (전시정보)
            CellStyle bodyStyle_di = workbook.createCellStyle();
            bodyStyle_di.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle_di.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle_di.setBorderRight(BorderStyle.THIN);
            bodyStyle_di.setBorderLeft(BorderStyle.THIN);
            bodyStyle_di.setBorderTop(BorderStyle.THIN);
            bodyStyle_di.setBorderBottom(BorderStyle.THIN);
            bodyStyle_di.setFont(font9);
            bodyStyle_di.setWrapText(true); //개행

            // 데이터 조회
            DirectoryDTO directoryDTO = new DirectoryDTO();
            directoryDTO.setTransferYear(transferYear);
            List<DirectoryDTO> directoryList = kibsMngService.processSelectDirectoryList(directoryDTO);

            //rows
            int rowCnt = 0;
            int cellCnt = 0;
            int listCount = directoryList.size();

            // *** Sheet-------------------------------------------------
            // Sheet 생성
            XSSFSheet sheet = workbook.createSheet("Directory");

            XSSFCell cell = null;
            XSSFRow row = sheet.createRow(rowCnt++);

            // 헤더 정보 구성
            // 참가업체정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,24));
            XSSFCell mergeCell = row.createCell(0);
            mergeCell.setCellStyle(headerStyle);
            mergeCell.setCellValue("참가업체정보");

            // 담당자정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,25,30));
            XSSFCell mergeCell2 = row.createCell(25);
            mergeCell2.setCellStyle(headerStyle_ch);
            mergeCell2.setCellValue("담당자정보");

            // 전시정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,31,35));
            XSSFCell mergeCell3 = row.createCell(31);
            mergeCell3.setCellStyle(headerStyle_di);
            mergeCell3.setCellValue("전시정보");

            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                cell = row.createCell(i);
                if(i < 25) {
                    cell.setCellStyle(headerStyle);
                }else if(i<31){
                    cell.setCellStyle(headerStyle_ch);
                }else{
                    cell.setCellStyle(headerStyle_di);
                }
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(colWidths_ex[i]) + 1024));	//column width 지정
            }

            //데이터 부분 생성
            for(DirectoryDTO info : directoryList) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                int nCount = 0;
                String[] remark = info.getCompanyNameKo().split("\\^");

                //줄 높이 계산
                for (String s : remark) {
                    if (s.length() > 0) {
                        nCount++;
                    }
                }

                //줄 높이 설정
                if (nCount > 1){
                    row.setHeightInPoints((nCount * sheet.getDefaultRowHeightInPoints()));
                }

                // 넘버링
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);
                // 승인여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getApprovalStatus());
                // 입금여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPrcYn());
                // 회사명(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameKo());
                // 회사명(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameEn());
                // 주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddress());
                // 상세주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddressDetail());
                // 공장주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFactoryAddress());
                // 공장상세주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFactoryAddressDetail());
                // 대표자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyCeo());
                // 전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyTel());
                // 홈페이지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyHomepage());
                // FAX
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyFax());
                // 산업분류
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getIndustryPart());
                // 산업분류 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getIndustryPartEtc());
                // 임직원 수
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getEmployeeCnt());
                // 사업자등록번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyLicenseNum());
                // 블로그
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsBlog());
                // 페이스북
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsFacebook());
                // 인스타그램
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsInstagram());
                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsEtc());
                // 회사소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroKo());
                // 회사소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroEn());
                // KIBS 참가목적(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyPurposeKo());
                // KIBS 참가목적(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyPurposeEn());
                // 담당자명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getChargePersonName().replaceAll("\\^","\n"));
                // 담당자직위
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getChargePersonPosition().replaceAll("\\^","\n"));
                // 담당자부서
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getChargePersonDepart().replaceAll("\\^","\n"));
                // 담당자전화번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getChargePersonTel().replaceAll("\\^","\n"));
                // 담당자휴대전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getChargePersonPhone().replaceAll("\\^","\n"));
                // 담당자이메일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getChargePersonEmail().replaceAll("\\^","\n"));
                // 전시품목
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getDisplayItem().replaceAll("\\^","\n"));
                // 브랜드명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getDisplayBrand().replaceAll("\\^","\n"));
                // 전시품목보트수
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getDisplayBoatCnt().replaceAll("\\^","\n"));
                // 전시품소개(국)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getDisplayItemIntroKo().replaceAll("\\^","\n"));
                // 전시품소개(영)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getDisplayItemIntroEn().replaceAll("\\^","\n"));
            }

            //너비를 자동으로 다시 설정
            for (int i = 0; i < colNames_ex.length; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // excel 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 엑셀 파일명 설정
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping(value = "/mng/exhibitor/download.do", method = RequestMethod.GET)
    public void exhibitor_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // Workbook 생성
        try(XSSFWorkbook workbook = new XSSFWorkbook()){ // Excel 2007 이상

            /* 엑셀 그리기 */
            final String[] colNames_ex = {
                    /* 업체정보 */
                    "No",
                    "참가상태", "승인구분", "부스번호", "아이디", "등록일",
                    "최종수정일", "입금현황(참가비 수납상태)", "BP번호",
                    "(컨택내역)작성자", "(컨택내역)날짜", "(컨택내역)내용",
                    "(참고사항)작성자", "(참고사항)날짜", "(참고사항)내용",
                    "회사명(국문)", "회사명(영문)", "주소", "상세주소",
                    "공장 주소", "공장 상세 주소",
                    "대표자", "전화", "홈페이지", "Fax", "산업 분류", "산업 분류 기타", "임직원 수",
                    "사업자등록증", "사업자등록번호", "기참가연도", "회원사 여부",
                    /* SNS(주소기입) */
                    "블로그", "페이스북", "인스타그램", "기타",
                    /* 담당자 정보 1 */
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 담당자 정보 2 */
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 담당자 정보 3 */
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 참가분야 */
                    "요트·보트전 (Yacht & Boat World)", "무동력보트전 (Paddler’s World)", "워크보트전 (Workboat World)", "해양레저관 (Marine Leisure)",
                    "카라반쇼 (Caravan Show)", "아라마리나 교육 및 체험 프로그램 (Aramarina Education and Experience Program)", "해양부품·안전·마리나산업전 (Marine Equipment, Safety & Marina Industry Show)",
                    "친환경 특별전 (Eco Friendly Marine)", "한국해양관광전 (Marine Tourism)", "보트정비관 (Boat Maintenance)",
                    /* 전시품목 1 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 2 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 3 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 4 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 5 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 6 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 7 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 8 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 9 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 10 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 11 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 12 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 13 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 14 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 15 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 16 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 17 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 18 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 19 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 전시품목 20 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)", "전시품소개(국)", "전시품소개(영)",
                    /* 업체정보 */
                    "회사소개(국문)", "회사소개(영문)", "KIBS참가목적(국문)", "KIBS참가목적(영문)",
                    "신제품 출품 사항 소개(국문)", "신제품 출품 사항 소개(영문)",
                    "우리 기업 부스는 꼭 들려야 될 이유가 있다면?", "행사/이벤트 진행계획", "사무국 요청사항", "회사소개영상",
                    "온라인전시회 노출여부",
                    /* 기업뱃지 */
                    "보트쇼 3회 이상 참가", "올해의 제품상 수상", "보트쇼와 제작한 영상", "제품 등록 우수",
                    /* 제품 1 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 2 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 3 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 4 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 5 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 6 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 7 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 8 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 9 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 10 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 11 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 12 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 13 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 14 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 15 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 16 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 17 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 18 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 19 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 20 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 21 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 22 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 23 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 24 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 25 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 26 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 27 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 28 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 29 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 제품 30 */
                    "제품사진", "제품명(국문)", "제품명(영문)", "제품분류(대)", "제품분류(소)", "제품설명(국문)", "제품설명(영문)",
                    "제품 영상", "전장(m)", "마력",
                    /* 업체정보 */
                    "비즈니스 상담 참가희망여부",
                    /* 전시부스 신청 */
                    "등록비", "독립부스", "조립부스", "온라인부스",
                    /* 할인적용 */
                    "1차 조기신청", "2차 조기신청", "재참가할인", "규모할인 1(20부스 이상)", "규모할인 2(40부스 이상)",
                    "첫 참가 할인", "한국해양레저산업협회 할인",
                    /* 상호간판 신청 */
                    "국문", "영문",
                    /* 유틸리티 신청 */
                    "주간 단상 220V", "24시간용 220V", "압축공기 기본형", "급배수 기본형", "인터넷", "파이텍스(신)", "파이텍스(재)", "바코드 리더기",
                    /* 출입증 신청 1 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 2 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 3 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 4 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 5 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 6 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 7 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 8 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 9 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 출입증 신청 10 */
                    "회사명", "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고",
                    /* 바이어 등록 정보 1 */
                    "회사명", "국가", "소재지", "홈페이지", "부서", "직책", "이메일", "전화번호", "휴대전화", "FAX", "취급품목",
                    "초청사유", "계약진행여부", "희망사항",
                    /* 경품 신청 등록 정보 1 */
                    "구분", "수량", "분류", "품목명", "경품설명", "경품사진", "경품브랜드회사로고", "소비자가", "협찬가", "비고"
            };

            // 헤더 사이즈
            final int[] colWidths_ex = {
                    3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000
            };

            // *** Style--------------------------------------------------
            //Font
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");	//글씨체
            fontHeader.setFontHeight((short)(9 * 20));	//사이즈
            fontHeader.setBold(true);	//볼드(굵게)
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");	//글씨체
            font9.setFontHeight((short)(9 * 20));	//사이즈
            // 엑셀 헤더 셋팅 default (참가업체정보)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.GOLD.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);
            headerStyle.setWrapText(true); //개행
            // 엑셀 바디 셋팅 default (참가업체정보)
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9);
            bodyStyle.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIME)
            CellStyle headerStyle_lime = workbook.createCellStyle();
            headerStyle_lime.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lime.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lime.setBorderRight(BorderStyle.THIN);
            headerStyle_lime.setBorderLeft(BorderStyle.THIN);
            headerStyle_lime.setBorderTop(BorderStyle.THIN);
            headerStyle_lime.setBorderBottom(BorderStyle.THIN);
            headerStyle_lime.setFillForegroundColor(IndexedColors.LIME.index);
            headerStyle_lime.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lime.setFont(fontHeader);
            headerStyle_lime.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_TURQUOISE)
            CellStyle headerStyle_light_turquoise = workbook.createCellStyle();
            headerStyle_light_turquoise.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_turquoise.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_turquoise.setBorderRight(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderTop(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_turquoise.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
            headerStyle_light_turquoise.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_turquoise.setFont(fontHeader);
            headerStyle_light_turquoise.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (CORNFLOWER_BLUE)
            CellStyle headerStyle_cornflower_blue = workbook.createCellStyle();
            headerStyle_cornflower_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_cornflower_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_cornflower_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_cornflower_blue.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
            headerStyle_cornflower_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_cornflower_blue.setFont(fontHeader);
            headerStyle_cornflower_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_BLUE)
            CellStyle headerStyle_light_blue = workbook.createCellStyle();
            headerStyle_light_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_light_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_light_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_blue.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
            headerStyle_light_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_blue.setFont(fontHeader);
            headerStyle_light_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (GREEN)
            CellStyle headerStyle_green = workbook.createCellStyle();
            headerStyle_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_green.setBorderRight(BorderStyle.THIN);
            headerStyle_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_green.setBorderTop(BorderStyle.THIN);
            headerStyle_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_green.setFillForegroundColor(IndexedColors.GREEN.index);
            headerStyle_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_green.setFont(fontHeader);
            headerStyle_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_GREEN)
            CellStyle headerStyle_light_green = workbook.createCellStyle();
            headerStyle_light_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_green.setBorderRight(BorderStyle.THIN);
            headerStyle_light_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_green.setBorderTop(BorderStyle.THIN);
            headerStyle_light_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_green.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            headerStyle_light_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_green.setFont(fontHeader);
            headerStyle_light_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_ORANGE)
            CellStyle headerStyle_light_orange = workbook.createCellStyle();
            headerStyle_light_orange.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_orange.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_orange.setBorderRight(BorderStyle.THIN);
            headerStyle_light_orange.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_orange.setBorderTop(BorderStyle.THIN);
            headerStyle_light_orange.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_orange.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
            headerStyle_light_orange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_orange.setFont(fontHeader);
            headerStyle_light_orange.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (ROSE)
            CellStyle headerStyle_rose = workbook.createCellStyle();
            headerStyle_rose.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_rose.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_rose.setBorderRight(BorderStyle.THIN);
            headerStyle_rose.setBorderLeft(BorderStyle.THIN);
            headerStyle_rose.setBorderTop(BorderStyle.THIN);
            headerStyle_rose.setBorderBottom(BorderStyle.THIN);
            headerStyle_rose.setFillForegroundColor(IndexedColors.ROSE.index);
            headerStyle_rose.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_rose.setFont(fontHeader);
            headerStyle_rose.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (TAN)
            CellStyle headerStyle_tan = workbook.createCellStyle();
            headerStyle_tan.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_tan.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_tan.setBorderRight(BorderStyle.THIN);
            headerStyle_tan.setBorderLeft(BorderStyle.THIN);
            headerStyle_tan.setBorderTop(BorderStyle.THIN);
            headerStyle_tan.setBorderBottom(BorderStyle.THIN);
            headerStyle_tan.setFillForegroundColor(IndexedColors.TAN.index);
            headerStyle_tan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_tan.setFont(fontHeader);
            headerStyle_tan.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LAVENDER)
            CellStyle headerStyle_lavender = workbook.createCellStyle();
            headerStyle_lavender.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lavender.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lavender.setBorderRight(BorderStyle.THIN);
            headerStyle_lavender.setBorderLeft(BorderStyle.THIN);
            headerStyle_lavender.setBorderTop(BorderStyle.THIN);
            headerStyle_lavender.setBorderBottom(BorderStyle.THIN);
            headerStyle_lavender.setFillForegroundColor(IndexedColors.LAVENDER.index);
            headerStyle_lavender.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lavender.setFont(fontHeader);
            headerStyle_lavender.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LEMON_CHIFFON)
            CellStyle headerStyle_lemon_chiffon = workbook.createCellStyle();
            headerStyle_lemon_chiffon.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lemon_chiffon.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lemon_chiffon.setBorderRight(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderLeft(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderTop(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderBottom(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
            headerStyle_lemon_chiffon.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lemon_chiffon.setFont(fontHeader);
            headerStyle_lemon_chiffon.setWrapText(true); //개행

            //rows
            int rowCnt = 0;

            // *** Sheet-------------------------------------------------
            // Sheet 생성
            XSSFSheet sheet = workbook.createSheet("Exhibitor");

            XSSFCell cell = null;
            XSSFRow row = sheet.createRow(rowCnt++);

            // 헤더 정보 구성
            // 참가업체정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,31));
            XSSFCell mergeCell = row.createCell(0);
            mergeCell.setCellStyle(headerStyle);
            mergeCell.setCellValue("참가업체정보");

            // SNS
            sheet.addMergedRegion(new CellRangeAddress(0,0,32,35));
            XSSFCell mergeCell2 = row.createCell(32);
            mergeCell2.setCellStyle(headerStyle);
            mergeCell2.setCellValue("SNS");

            // 담당자 정보 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,36,41));
            XSSFCell mergeCell3 = row.createCell(36);
            mergeCell3.setCellStyle(headerStyle);
            mergeCell3.setCellValue("담당자 정보 1");

            // 담당자 정보 2
            sheet.addMergedRegion(new CellRangeAddress(0,0,42,47));
            XSSFCell mergeCell4 = row.createCell(42);
            mergeCell4.setCellStyle(headerStyle);
            mergeCell4.setCellValue("담당자 정보 2");

            // 담당자 정보 3
            sheet.addMergedRegion(new CellRangeAddress(0,0,48,53));
            XSSFCell mergeCell5 = row.createCell(48);
            mergeCell5.setCellStyle(headerStyle);
            mergeCell5.setCellValue("담당자 정보 3");

            // 참가분야
            sheet.addMergedRegion(new CellRangeAddress(0,0,54,63));
            XSSFCell mergeCell6 = row.createCell(54);
            mergeCell6.setCellStyle(headerStyle_lime);
            mergeCell6.setCellValue("참가분야");

            // 전시품목 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,64,68));
            XSSFCell mergeCell7 = row.createCell(64);
            mergeCell7.setCellStyle(headerStyle_light_green);
            mergeCell7.setCellValue("전시품목 1");

            // 전시품목 2
            sheet.addMergedRegion(new CellRangeAddress(0,0,69,73));
            XSSFCell mergeCell8 = row.createCell(69);
            mergeCell8.setCellStyle(headerStyle_light_green);
            mergeCell8.setCellValue("전시품목 2");

            // 전시품목 3
            sheet.addMergedRegion(new CellRangeAddress(0,0,74,78));
            XSSFCell mergeCell9 = row.createCell(74);
            mergeCell9.setCellStyle(headerStyle_light_green);
            mergeCell9.setCellValue("전시품목 3");

            // 전시품목 4
            sheet.addMergedRegion(new CellRangeAddress(0,0,79,83));
            XSSFCell mergeCell10 = row.createCell(79);
            mergeCell10.setCellStyle(headerStyle_light_green);
            mergeCell10.setCellValue("전시품목 4");

            // 전시품목 5
            sheet.addMergedRegion(new CellRangeAddress(0,0,84,88));
            XSSFCell mergeCell11 = row.createCell(84);
            mergeCell11.setCellStyle(headerStyle_light_green);
            mergeCell11.setCellValue("전시품목 5");

            // 전시품목 6
            sheet.addMergedRegion(new CellRangeAddress(0,0,89,93));
            XSSFCell mergeCell12 = row.createCell(89);
            mergeCell12.setCellStyle(headerStyle_light_green);
            mergeCell12.setCellValue("전시품목 6");

            // 전시품목 7
            sheet.addMergedRegion(new CellRangeAddress(0,0,94,98));
            XSSFCell mergeCell13 = row.createCell(94);
            mergeCell13.setCellStyle(headerStyle_light_green);
            mergeCell13.setCellValue("전시품목 7");

            // 전시품목 8
            sheet.addMergedRegion(new CellRangeAddress(0,0,99,103));
            XSSFCell mergeCell14 = row.createCell(99);
            mergeCell14.setCellStyle(headerStyle_light_green);
            mergeCell14.setCellValue("전시품목 8");

            // 전시품목 9
            sheet.addMergedRegion(new CellRangeAddress(0,0,104,108));
            XSSFCell mergeCell15 = row.createCell(104);
            mergeCell15.setCellStyle(headerStyle_light_green);
            mergeCell15.setCellValue("전시품목 9");

            // 전시품목 10
            sheet.addMergedRegion(new CellRangeAddress(0,0,109,113));
            XSSFCell mergeCell16 = row.createCell(109);
            mergeCell16.setCellStyle(headerStyle_light_green);
            mergeCell16.setCellValue("전시품목 10");

            // 전시품목 11
            sheet.addMergedRegion(new CellRangeAddress(0,0,114,118));
            XSSFCell mergeCell17 = row.createCell(114);
            mergeCell17.setCellStyle(headerStyle_light_green);
            mergeCell17.setCellValue("전시품목 11");

            // 전시품목 12
            sheet.addMergedRegion(new CellRangeAddress(0,0,119,123));
            XSSFCell mergeCell18 = row.createCell(119);
            mergeCell18.setCellStyle(headerStyle_light_green);
            mergeCell18.setCellValue("전시품목 12");

            // 전시품목 13
            sheet.addMergedRegion(new CellRangeAddress(0,0,124,128));
            XSSFCell mergeCell19 = row.createCell(124);
            mergeCell19.setCellStyle(headerStyle_light_green);
            mergeCell19.setCellValue("전시품목 13");

            // 전시품목 14
            sheet.addMergedRegion(new CellRangeAddress(0,0,129,133));
            XSSFCell mergeCell20 = row.createCell(129);
            mergeCell20.setCellStyle(headerStyle_light_green);
            mergeCell20.setCellValue("전시품목 14");

            // 전시품목 15
            sheet.addMergedRegion(new CellRangeAddress(0,0,134,138));
            XSSFCell mergeCell21 = row.createCell(134);
            mergeCell21.setCellStyle(headerStyle_light_green);
            mergeCell21.setCellValue("전시품목 15");

            // 전시품목 16
            sheet.addMergedRegion(new CellRangeAddress(0,0,139,143));
            XSSFCell mergeCell22 = row.createCell(139);
            mergeCell22.setCellStyle(headerStyle_light_green);
            mergeCell22.setCellValue("전시품목 16");

            // 전시품목 17
            sheet.addMergedRegion(new CellRangeAddress(0,0,144,148));
            XSSFCell mergeCell23 = row.createCell(144);
            mergeCell23.setCellStyle(headerStyle_light_green);
            mergeCell23.setCellValue("전시품목 17");

            // 전시품목 18
            sheet.addMergedRegion(new CellRangeAddress(0,0,149,153));
            XSSFCell mergeCell24 = row.createCell(149);
            mergeCell24.setCellStyle(headerStyle_light_green);
            mergeCell24.setCellValue("전시품목 18");

            // 전시품목 19
            sheet.addMergedRegion(new CellRangeAddress(0,0,154,158));
            XSSFCell mergeCell25 = row.createCell(154);
            mergeCell25.setCellStyle(headerStyle_light_green);
            mergeCell25.setCellValue("전시품목 19");

            // 전시품목 20
            sheet.addMergedRegion(new CellRangeAddress(0,0,159,163));
            XSSFCell mergeCell26 = row.createCell(159);
            mergeCell26.setCellStyle(headerStyle_light_green);
            mergeCell26.setCellValue("전시품목 20");

            // 참가업체정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,164,174));
            XSSFCell mergeCell27 = row.createCell(164);
            mergeCell27.setCellStyle(headerStyle);
            mergeCell27.setCellValue("참가업체정보");

            // 기업뱃지
            sheet.addMergedRegion(new CellRangeAddress(0,0,175,178));
            XSSFCell mergeCell28 = row.createCell(175);
            mergeCell28.setCellStyle(headerStyle_cornflower_blue);
            mergeCell28.setCellValue("기업 뱃지");

            // 제품 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,179,188));
            XSSFCell mergeCell29 = row.createCell(179);
            mergeCell29.setCellStyle(headerStyle_light_orange);
            mergeCell29.setCellValue("제품 1");

            // 제품 2
            sheet.addMergedRegion(new CellRangeAddress(0,0,189,198));
            XSSFCell mergeCell30 = row.createCell(189);
            mergeCell30.setCellStyle(headerStyle_light_orange);
            mergeCell30.setCellValue("제품 2");

            // 제품 3
            sheet.addMergedRegion(new CellRangeAddress(0,0,199,208));
            XSSFCell mergeCell31 = row.createCell(199);
            mergeCell31.setCellStyle(headerStyle_light_orange);
            mergeCell31.setCellValue("제품 3");

            // 제품 4
            sheet.addMergedRegion(new CellRangeAddress(0,0,209,218));
            XSSFCell mergeCell32 = row.createCell(209);
            mergeCell32.setCellStyle(headerStyle_light_orange);
            mergeCell32.setCellValue("제품 4");

            // 제품 5
            sheet.addMergedRegion(new CellRangeAddress(0,0,219,228));
            XSSFCell mergeCell33 = row.createCell(219);
            mergeCell33.setCellStyle(headerStyle_light_orange);
            mergeCell33.setCellValue("제품 5");

            // 제품 6
            sheet.addMergedRegion(new CellRangeAddress(0,0,229,238));
            XSSFCell mergeCell34 = row.createCell(229);
            mergeCell34.setCellStyle(headerStyle_light_orange);
            mergeCell34.setCellValue("제품 6");

            // 제품 7
            sheet.addMergedRegion(new CellRangeAddress(0,0,239,248));
            XSSFCell mergeCell35 = row.createCell(239);
            mergeCell35.setCellStyle(headerStyle_light_orange);
            mergeCell35.setCellValue("제품 7");

            // 제품 8
            sheet.addMergedRegion(new CellRangeAddress(0,0,249,258));
            XSSFCell mergeCell36 = row.createCell(249);
            mergeCell36.setCellStyle(headerStyle_light_orange);
            mergeCell36.setCellValue("제품 8");

            // 제품 9
            sheet.addMergedRegion(new CellRangeAddress(0,0,259,268));
            XSSFCell mergeCell37 = row.createCell(259);
            mergeCell37.setCellStyle(headerStyle_light_orange);
            mergeCell37.setCellValue("제품 9");

            // 제품 10
            sheet.addMergedRegion(new CellRangeAddress(0,0,269,278));
            XSSFCell mergeCell38 = row.createCell(269);
            mergeCell38.setCellStyle(headerStyle_light_orange);
            mergeCell38.setCellValue("제품 10");

            // 제품 11
            sheet.addMergedRegion(new CellRangeAddress(0,0,279,288));
            XSSFCell mergeCell39 = row.createCell(279);
            mergeCell39.setCellStyle(headerStyle_light_orange);
            mergeCell39.setCellValue("제품 11");

            // 제품 12
            sheet.addMergedRegion(new CellRangeAddress(0,0,289,298));
            XSSFCell mergeCell40 = row.createCell(289);
            mergeCell40.setCellStyle(headerStyle_light_orange);
            mergeCell40.setCellValue("제품 12");

            // 제품 13
            sheet.addMergedRegion(new CellRangeAddress(0,0,299,308));
            XSSFCell mergeCell41 = row.createCell(299);
            mergeCell41.setCellStyle(headerStyle_light_orange);
            mergeCell41.setCellValue("제품 13");

            // 제품 14
            sheet.addMergedRegion(new CellRangeAddress(0,0,309,318));
            XSSFCell mergeCell42 = row.createCell(309);
            mergeCell42.setCellStyle(headerStyle_light_orange);
            mergeCell42.setCellValue("제품 14");

            // 제품 15
            sheet.addMergedRegion(new CellRangeAddress(0,0,319,328));
            XSSFCell mergeCell43 = row.createCell(319);
            mergeCell43.setCellStyle(headerStyle_light_orange);
            mergeCell43.setCellValue("제품 15");

            // 제품 16
            sheet.addMergedRegion(new CellRangeAddress(0,0,329,338));
            XSSFCell mergeCell44 = row.createCell(329);
            mergeCell44.setCellStyle(headerStyle_light_orange);
            mergeCell44.setCellValue("제품 16");

            // 제품 17
            sheet.addMergedRegion(new CellRangeAddress(0,0,339,348));
            XSSFCell mergeCell45 = row.createCell(339);
            mergeCell45.setCellStyle(headerStyle_light_orange);
            mergeCell45.setCellValue("제품 17");

            // 제품 18
            sheet.addMergedRegion(new CellRangeAddress(0,0,349,358));
            XSSFCell mergeCell46 = row.createCell(349);
            mergeCell46.setCellStyle(headerStyle_light_orange);
            mergeCell46.setCellValue("제품 18");

            // 제품 19
            sheet.addMergedRegion(new CellRangeAddress(0,0,359,368));
            XSSFCell mergeCell47 = row.createCell(359);
            mergeCell47.setCellStyle(headerStyle_light_orange);
            mergeCell47.setCellValue("제품 19");

            // 제품 20
            sheet.addMergedRegion(new CellRangeAddress(0,0,369,378));
            XSSFCell mergeCell48 = row.createCell(369);
            mergeCell48.setCellStyle(headerStyle_light_orange);
            mergeCell48.setCellValue("제품 20");

            // 제품 21
            sheet.addMergedRegion(new CellRangeAddress(0,0,379,388));
            XSSFCell mergeCell49 = row.createCell(379);
            mergeCell49.setCellStyle(headerStyle_light_orange);
            mergeCell49.setCellValue("제품 21");

            // 제품 22
            sheet.addMergedRegion(new CellRangeAddress(0,0,389,398));
            XSSFCell mergeCell50 = row.createCell(389);
            mergeCell50.setCellStyle(headerStyle_light_orange);
            mergeCell50.setCellValue("제품 22");

            // 제품 23
            sheet.addMergedRegion(new CellRangeAddress(0,0,399,408));
            XSSFCell mergeCell51 = row.createCell(399);
            mergeCell51.setCellStyle(headerStyle_light_orange);
            mergeCell51.setCellValue("제품 23");

            // 제품 24
            sheet.addMergedRegion(new CellRangeAddress(0,0,409,418));
            XSSFCell mergeCell52 = row.createCell(409);
            mergeCell52.setCellStyle(headerStyle_light_orange);
            mergeCell52.setCellValue("제품 24");

            // 제품 25
            sheet.addMergedRegion(new CellRangeAddress(0,0,419,428));
            XSSFCell mergeCell53 = row.createCell(419);
            mergeCell53.setCellStyle(headerStyle_light_orange);
            mergeCell53.setCellValue("제품 25");

            // 제품 26
            sheet.addMergedRegion(new CellRangeAddress(0,0,429,438));
            XSSFCell mergeCell54 = row.createCell(429);
            mergeCell54.setCellStyle(headerStyle_light_orange);
            mergeCell54.setCellValue("제품 26");

            // 제품 27
            sheet.addMergedRegion(new CellRangeAddress(0,0,439,448));
            XSSFCell mergeCell55 = row.createCell(439);
            mergeCell55.setCellStyle(headerStyle_light_orange);
            mergeCell55.setCellValue("제품 27");

            // 제품 28
            sheet.addMergedRegion(new CellRangeAddress(0,0,449,458));
            XSSFCell mergeCell56 = row.createCell(449);
            mergeCell56.setCellStyle(headerStyle_light_orange);
            mergeCell56.setCellValue("제품 28");

            // 제품 29
            sheet.addMergedRegion(new CellRangeAddress(0,0,459,468));
            XSSFCell mergeCell57 = row.createCell(459);
            mergeCell57.setCellStyle(headerStyle_light_orange);
            mergeCell57.setCellValue("제품 29");

            // 제품 30
            sheet.addMergedRegion(new CellRangeAddress(0,0,469,478));
            XSSFCell mergeCell58 = row.createCell(469);
            mergeCell58.setCellStyle(headerStyle_light_orange);
            mergeCell58.setCellValue("제품 30");

            // 참가업체정보
            //sheet.addMergedRegion(new CellRangeAddress(0,0,436,436));
            XSSFCell mergeCell59 = row.createCell(479);
            mergeCell59.setCellStyle(headerStyle);
            mergeCell59.setCellValue("참가업체정보");

            // 전시부스신청
            sheet.addMergedRegion(new CellRangeAddress(0,0,480,483));
            XSSFCell mergeCell60 = row.createCell(480);
            mergeCell60.setCellStyle(headerStyle_cornflower_blue);
            mergeCell60.setCellValue("전시부스 신청");

            // 할인적용
            sheet.addMergedRegion(new CellRangeAddress(0,0,484,490));
            XSSFCell mergeCell61 = row.createCell(484);
            mergeCell61.setCellStyle(headerStyle_cornflower_blue);
            mergeCell61.setCellValue("할인적용");

            // 상호간판신청
            sheet.addMergedRegion(new CellRangeAddress(0,0,491,492));
            XSSFCell mergeCell62 = row.createCell(491);
            mergeCell62.setCellStyle(headerStyle_light_blue);
            mergeCell62.setCellValue("상호간판 신청");

            // 유틸리티 신청
            sheet.addMergedRegion(new CellRangeAddress(0,0,493,500));
            XSSFCell mergeCell63 = row.createCell(493);
            mergeCell63.setCellStyle(headerStyle_rose);
            mergeCell63.setCellValue("유틸리티 신청");

            // 출입증 신청 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,501,506));
            XSSFCell mergeCell64 = row.createCell(501);
            mergeCell64.setCellStyle(headerStyle_lavender);
            mergeCell64.setCellValue("출입증 신청 1");

            // 출입증 신청 2
            sheet.addMergedRegion(new CellRangeAddress(0,0,507,512));
            XSSFCell mergeCell65 = row.createCell(507);
            mergeCell65.setCellStyle(headerStyle_lavender);
            mergeCell65.setCellValue("출입증 신청 2");

            // 출입증 신청 3
            sheet.addMergedRegion(new CellRangeAddress(0,0,513,518));
            XSSFCell mergeCell66 = row.createCell(513);
            mergeCell66.setCellStyle(headerStyle_lavender);
            mergeCell66.setCellValue("출입증 신청 3");

            // 출입증 신청 4
            sheet.addMergedRegion(new CellRangeAddress(0,0,519,524));
            XSSFCell mergeCell67 = row.createCell(519);
            mergeCell67.setCellStyle(headerStyle_lavender);
            mergeCell67.setCellValue("출입증 신청 4");

            // 출입증 신청 5
            sheet.addMergedRegion(new CellRangeAddress(0,0,525,530));
            XSSFCell mergeCell68 = row.createCell(525);
            mergeCell68.setCellStyle(headerStyle_lavender);
            mergeCell68.setCellValue("출입증 신청 5");

            // 출입증 신청 6
            sheet.addMergedRegion(new CellRangeAddress(0,0,531,536));
            XSSFCell mergeCell69 = row.createCell(531);
            mergeCell69.setCellStyle(headerStyle_lavender);
            mergeCell69.setCellValue("출입증 신청 6");

            // 출입증 신청 7
            sheet.addMergedRegion(new CellRangeAddress(0,0,537,542));
            XSSFCell mergeCell70 = row.createCell(537);
            mergeCell70.setCellStyle(headerStyle_lavender);
            mergeCell70.setCellValue("출입증 신청 7");

            // 출입증 신청 8
            sheet.addMergedRegion(new CellRangeAddress(0,0,543,548));
            XSSFCell mergeCell71 = row.createCell(543);
            mergeCell71.setCellStyle(headerStyle_lavender);
            mergeCell71.setCellValue("출입증 신청 8");

            // 출입증 신청 9
            sheet.addMergedRegion(new CellRangeAddress(0,0,549,554));
            XSSFCell mergeCell72 = row.createCell(549);
            mergeCell72.setCellStyle(headerStyle_lavender);
            mergeCell72.setCellValue("출입증 신청 9");

            // 출입증 신청 10
            sheet.addMergedRegion(new CellRangeAddress(0,0,555,560));
            XSSFCell mergeCell73 = row.createCell(555);
            mergeCell73.setCellStyle(headerStyle_lavender);
            mergeCell73.setCellValue("출입증 신청 10");

            // 바이어 등록 정보 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,561,574));
            XSSFCell mergeCell74 = row.createCell(561);
            mergeCell74.setCellStyle(headerStyle_rose);
            mergeCell74.setCellValue("바이어 등록 정보 1");

            // 경품 신청 등록 정보 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,575,584));
            XSSFCell mergeCell75 = row.createCell(575);
            mergeCell75.setCellStyle(headerStyle_lemon_chiffon);
            mergeCell75.setCellValue("경품 신청 등록 정보 1");

            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                cell = row.createCell(i);
                if(i < 54) {
                    cell.setCellStyle(headerStyle);
                }else if(i<64){
                    cell.setCellStyle(headerStyle_lime);
                }else if(i<164){
                    cell.setCellStyle(headerStyle_light_green);
                }else if(i<175){
                    cell.setCellStyle(headerStyle);
                }else if(i<179){
                    cell.setCellStyle(headerStyle_cornflower_blue);
                }else if(i<479){
                    cell.setCellStyle(headerStyle_light_orange);
                }else if(i<480){
                    cell.setCellStyle(headerStyle);
                }else if(i<491){
                    cell.setCellStyle(headerStyle_cornflower_blue);
                }else if(i<493){
                    cell.setCellStyle(headerStyle_light_blue);
                }else if(i<501){
                    cell.setCellStyle(headerStyle_rose);
                }else if(i<561){
                    cell.setCellStyle(headerStyle_lavender);
                }else if(i<575){
                    cell.setCellStyle(headerStyle_rose);
                }else{
                    cell.setCellStyle(headerStyle_lemon_chiffon);
                }
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(colWidths_ex[i]) + 1024));	//column width 지정
            }

            // 데이터 조회
            ExhibitorDetailDTO exhibitorDetailDTO = new ExhibitorDetailDTO();
            exhibitorDetailDTO.setTransferYear(transferYear);
            List<ExhibitorDetailDTO> exhibitorDetailList = kibsMngService.processSelectExhibitorDetailList(exhibitorDetailDTO);

            int cellCnt = 0;
            int listCount = exhibitorDetailList.size();

            //데이터 부분 생성
            for(ExhibitorDetailDTO info : exhibitorDetailList) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                int nCount = 0;
                String[] remark = info.getCompanyNameKo().split("\\^");

                //줄 높이 계산
                for (String s : remark) {
                    if (s.length() > 0) {
                        nCount++;
                    }
                }

                //줄 높이 설정
                if (nCount > 1){
                    row.setHeightInPoints((nCount * sheet.getDefaultRowHeightInPoints()));
                }

                // 넘버링
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);

                // 참가상태
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getParticipationStatus());

                // 승인구분
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getApprovalStatus());

                // 부스번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getBoothNum());

                // 아이디
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getId());

                // 등록일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getInitRegiDttm());

                // 수정일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFinalRegiDttm());

                // 입금현황
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if("1".equals(info.getPrcYn())){
                    cell.setCellValue("Y");
                }else{
                    cell.setCellValue("N");
                }

                // BP번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getBpNum());

                // 컨택내역-작성자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getContactWriter());

                // 컨택내역-날짜
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getContactDate());

                // 컨택내역-내용
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getContactContent());

                // 참고사항-작성자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getReferenceWriter());

                // 참고사항-날짜
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getReferenceDate());

                // 참고사항-내용
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getReferenceContent());

                // 회사명(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameKo());

                // 회사명(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameEn());

                // 주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddress());

                // 상세주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddressDetail());

                // 공장주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFactoryAddress());

                // 공장상세주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFactoryAddressDetail());

                // 대표자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyCeo());

                // 전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyTel());

                // 홈페이지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyHomepage());

                // Fax
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyFax());

                // 산업분류
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getIndustryPart());

                // 산업분류 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getIndustryPartEtc());

                // 임직원 수
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getEmployeeCnt());

                // 사업자등록증
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyLicense());

                // 사업자등록번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyLicenseNum());

                // 기참가연도
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPrePartYear());

                // 회원사 여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getMemberCompanyYn());

                // 블로그
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsBlog());

                // 페이스북
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsFacebook());

                // 인스타그램
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsInstagram());

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsEtc());

                // 담당자정보
                for(int i=0; i<3; i++){
                    // 성명
                    String[] chargePersonNameSplit = new String[3];
                    if(info.getChargePersonName() != null){
                        if(info.getChargePersonName().contains("^")){
                            chargePersonNameSplit = info.getChargePersonName().split("\\^");
                        }else{
                            chargePersonNameSplit[0] = info.getChargePersonName().replaceAll("\\^","");
                        }
                    }else{
                        chargePersonNameSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonNameSplit, i));

                    // 직위
                    String[] chargePersonPositionSplit = new String[3];
                    if(info.getChargePersonPosition() != null){
                        if(info.getChargePersonPosition().contains("^")){
                            chargePersonPositionSplit = info.getChargePersonPosition().split("\\^");
                        }else{
                            chargePersonPositionSplit[0] = info.getChargePersonPosition();
                        }
                    }else{
                        chargePersonPositionSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonPositionSplit, i));

                    // 부서
                    String[] chargePersonDepartSplit = new String[3];
                    if(info.getChargePersonDepart() != null){
                        if(info.getChargePersonDepart().contains("^")){
                            chargePersonDepartSplit = info.getChargePersonDepart().split("\\^");
                        }else{
                            chargePersonDepartSplit[0] = info.getChargePersonDepart();
                        }
                    }else{
                        chargePersonDepartSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonDepartSplit, i));

                    // 전화번호
                    String[] chargePersonTelSplit = new String[3];
                    if(info.getChargePersonTel() != null){
                        if(info.getChargePersonTel().contains("^")){
                            chargePersonTelSplit = info.getChargePersonTel().split("\\^");
                        }else{
                            chargePersonTelSplit[0] = info.getChargePersonTel();
                        }
                    }else{
                        chargePersonTelSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonTelSplit, i));

                    // 휴대전화
                    String[] chargePersonPhoneSplit = new String[3];
                    if(info.getChargePersonPhone() != null){
                        if(info.getChargePersonPhone().contains("^")){
                            chargePersonPhoneSplit = info.getChargePersonPhone().split("\\^");
                        }else{
                            chargePersonPhoneSplit[0] = info.getChargePersonPhone();
                        }
                    }else{
                        chargePersonPhoneSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonPhoneSplit, i));

                    // 이메일
                    String[] chargePersonEmailSplit = new String[3];
                    if(info.getChargePersonEmail() != null){
                        if(info.getChargePersonEmail().contains("^")){
                            chargePersonEmailSplit = info.getChargePersonEmail().split("\\^");
                        }else{
                            chargePersonEmailSplit[0] = info.getChargePersonEmail();
                        }
                    }else{
                        chargePersonEmailSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonEmailSplit, i));
                }

                // 참가분야
                String fieldPart = "";
                if(info.getFieldPart() != null && !"".equals(info.getFieldPart())) {
                    fieldPart = info.getFieldPart().replaceAll(" ","");
                }

                // 요트·보트전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("요트·보트전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 무동력보트전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("무동력보트전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 워크보트전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("워크보트전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양레저관
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("해양레저관")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 카라반쇼
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("카라반쇼")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 아라마리나 교육 및 체험 프로그램
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("아라마리나")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양부품·안전·마리나산업전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("해양부품")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 친환경 특별전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("친환경")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 한국해양관광전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("한국해양관광전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트정비관
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("보트정비관")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 전시품목
                for(int i=0; i<20; i++){
                    // 전시품목
                    String[] displayItemSplit = new String[3];
                    if(info.getDisplayItem().contains("^")){
                        displayItemSplit = info.getDisplayItem().split("\\^");
                    }else{
                        displayItemSplit[0] = info.getDisplayItem();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayItemSplit, i));

                    // 브랜드명
                    String[] displayBrandSplit = new String[3];
                    if(info.getDisplayBrand().contains("^")){
                        displayBrandSplit = info.getDisplayBrand().split("\\^");
                    }else{
                        displayBrandSplit[0] = info.getDisplayBrand();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayBrandSplit, i));

                    // 실물보트수
                    String[] displayBoatCntSplit = new String[3];
                    if(info.getDisplayBoatCnt().contains("^")){
                        displayBoatCntSplit = info.getDisplayBoatCnt().split("\\^");
                    }else{
                        displayBoatCntSplit[0] = info.getDisplayBoatCnt();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayBoatCntSplit, i));

                    // 전시품소개(국)
                    String[] displayItemIntroKoSplit = new String[3];
                    if(info.getDisplayItemIntroKo().contains("^")){
                        displayItemIntroKoSplit = info.getDisplayItemIntroKo().split("\\^");
                    }else{
                        displayItemIntroKoSplit[0] = info.getDisplayItemIntroKo();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayItemIntroKoSplit, i));

                    // 전시품소개(영)
                    String[] displayItemIntroEnSplit = new String[3];
                    if(info.getDisplayItemIntroEn().contains("^")){
                        displayItemIntroEnSplit = info.getDisplayItemIntroEn().split("\\^");
                    }else{
                        displayItemIntroEnSplit[0] = info.getDisplayItemIntroEn();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayItemIntroEnSplit, i));
                }

                // 회사소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroKo());

                // 회사소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroEn());

                // KIBS 참가목적(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyPurposeKo());

                // KIBS 참가목적(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyPurposeEn());

                // 신제품 출품 사항 소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getNewItemIntroKo());

                // 신제품 출품 사항 소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getNewItemIntroEn());

                // 우리 기업 부스 ~
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getBoothVisitReason());

                // 행사/이벤트 진행계획
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getEventPlan());

                // 사무국 요청사항
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getOfficeRequest());

                // 회사소개영상
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getCompanyIntroVideo() != null && !"".equals(info.getCompanyIntroVideo())){
                    cell.setCellValue("https://www.youtube.com/embed/" + info.getCompanyIntroVideo());
                }else{
                    cell.setCellValue("");
                }

                // 온라인 전시관 노출 여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyOnlineViewYn());

                // 기업 뱃지
                String companyBadge = "";
                if(info.getCompanyBadge() != null && !"".equals(info.getCompanyBadge())) {
                    companyBadge = info.getCompanyBadge().replaceAll(" ","");
                }

                // 보트쇼 3회 이상 참가
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(companyBadge.contains("3회")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 올해의 제품상 수상
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(companyBadge.contains("제품상")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트쇼와 제작한 영상
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(companyBadge.contains("제작한")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 제품 등록 우수
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(companyBadge.contains("우수")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 제품
                for(int i=0; i<30; i++) {
                    // 제품사진
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] productPhotoSplit = new String[30];
                    if(info.getProductNameKo().contains("^")){
                        productPhotoSplit = info.getProductNameKo().split("\\^");
                    }else{
                        productPhotoSplit[0] = "-";
                    }
                    if(!"".equals(convertValue(productPhotoSplit, i))){
                        cell.setCellValue("-");
                    }else{
                        cell.setCellValue("");
                    }

                    // 제품명(국문)
                    String[] productNameKoSplit = new String[30];
                    if(info.getProductNameKo().contains("^")){
                        productNameKoSplit = info.getProductNameKo().split("\\^");
                    }else{
                        productNameKoSplit[0] = info.getProductNameKo();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productNameKoSplit, i));

                    // 제품명(영문)
                    String[] productNameEnSplit = new String[30];
                    if(info.getProductNameEn().contains("^")){
                        productNameEnSplit = info.getProductNameEn().split("\\^");
                    }else{
                        productNameEnSplit[0] = info.getProductNameEn();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productNameEnSplit, i));

                    // 제품분류(대)
                    String[] productOptionBigSplit = new String[30];
                    if(info.getProductOptionBig().contains("^")){
                        productOptionBigSplit = info.getProductOptionBig().split("\\^");
                    }else{
                        productOptionBigSplit[0] = info.getProductOptionBig();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productOptionBigSplit, i));

                    // 제품분류(소)
                    String[] productOptionSmallSplit = new String[30];
                    if(info.getProductOptionSmall().contains("^")){
                        productOptionSmallSplit = info.getProductOptionSmall().split("\\^");
                    }else{
                        productOptionSmallSplit[0] = info.getProductOptionSmall();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productOptionSmallSplit, i));

                    // 제품설명(국문)
                    String[] productIntroKoSplit = new String[30];
                    if(info.getProductIntroKo().contains("^")){
                        productIntroKoSplit = info.getProductIntroKo().split("\\^");
                    }else{
                        productIntroKoSplit[0] = info.getProductIntroKo();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productIntroKoSplit, i));

                    // 제품설명(영문)
                    String[] productIntroEnSplit = new String[30];
                    if(info.getProductIntroEn().contains("^")){
                        productIntroEnSplit = info.getProductIntroEn().split("\\^");
                    }else{
                        productIntroEnSplit[0] = info.getProductIntroEn();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productIntroEnSplit, i));

                    // 제품영상
                    String[] productIntroVideoSplit = new String[30];
                    if(info.getProductIntroVideo().contains("^")){
                        productIntroVideoSplit = info.getProductIntroVideo().split("\\^");
                    }else{
                        productIntroVideoSplit[0] = info.getProductIntroVideo();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    if(convertValue(productIntroVideoSplit, i) != null && !"".equals(convertValue(productIntroVideoSplit, i))){
                        cell.setCellValue("https://www.youtube.com/embed/" + convertValue(productIntroVideoSplit, i));
                    }else{
                        cell.setCellValue("");
                    }

                    // 전장
                    String[] productWidthSplit = new String[30];
                    if(info.getProductWidth().contains("^")){
                        productWidthSplit = info.getProductWidth().split("\\^");
                    }else{
                        productWidthSplit[0] = info.getProductWidth();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productWidthSplit, i));

                    // 마력
                    String[] productHorsePowerSplit = new String[30];
                    if(info.getProductHorsePower().contains("^")){
                        productHorsePowerSplit = info.getProductHorsePower().split("\\^");
                    }else{
                        productHorsePowerSplit[0] = info.getProductHorsePower();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(productHorsePowerSplit, i));
                }

                // 비즈니스 상담 참가희망 여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getExportMeetingYn());

                // 등록비
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getRegistrationCnt());

                // 독립부스
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getStandAloneBoothCnt());

                // 조립부스
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getAssemblyBoothCnt());

                // 온라인부스
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getOnlineBoothCnt());

                // 1차 조기신청
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountEarly1()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2차 조기신청
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountEarly2()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 재참가할인
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountReAll()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 규모할인1
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountScale2()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 규모할인2
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountScale3()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 첫참가할인
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountFirst()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 한국해양레저산업협회할인
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getDiscountLeisure()){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 상호간판(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanySignNameKo());

                // 상호간판(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanySignNameEn());

                // 주간단상 220v
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityJuganCnt());

                // 24시간용 220V
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityDayCnt());

                // 압축공기 기본형
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityCompressedAirCnt());

                // 급배수 기본형
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityWaterBasicCnt());

                // 인터넷
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityInternetCnt());

                // 파이텍스 신
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityPytexNewCnt());

                // 파이텍스 재
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityPytexReCnt());

                // 바코드 리더기
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUtilityBarcodeCnt());

                // 출입증 신청
                for(int i=0; i<10; i++){
                    // 회사명
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] passCompanyNameSplit = new String[10];
                    if(info.getPassName() != null){
                        if(info.getPassName().contains("^")){
                            passCompanyNameSplit = info.getPassName().split("\\^");
                        }else{
                            passCompanyNameSplit[0] = "-";
                        }
                        if(!"".equals(convertValue(passCompanyNameSplit, i))){
                            cell.setCellValue("-");
                        }else{
                            cell.setCellValue("");
                        }
                    }else{
                        cell.setCellValue("");
                    }

                    // 성명(국문)
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] passNameSplit = new String[10];
                    if(info.getPassName() != null){
                        if(info.getPassName().contains("^")){
                            passNameSplit = info.getPassName().split("\\^");
                        }else{
                            passNameSplit[0] = info.getPassName();
                        }
                        cell.setCellValue(convertValue(passNameSplit, i));
                    }else{
                        cell.setCellValue("");
                    }

                    // 성명(영문)
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] passLastNameSplit = new String[10];
                    if(info.getPassLastName() != null){
                        if(info.getPassLastName().contains("^")){
                            passLastNameSplit = info.getPassLastName().split("\\^");
                        }else{
                            passLastNameSplit[0] = info.getPassLastName();
                        }
                    }else{
                        passLastNameSplit[0] = "";
                    }
                    String[] passFirstNameSplit = new String[10];
                    if(info.getPassFirstName() != null){
                        if(info.getPassFirstName().contains("^")){
                            passFirstNameSplit = info.getPassFirstName().split("\\^");
                        }else{
                            passFirstNameSplit[0] = info.getPassFirstName();
                        }
                    }else{
                        passFirstNameSplit[0] = "";
                    }
                    cell.setCellValue(convertValue(passLastNameSplit, i) + " " + convertValue(passFirstNameSplit, i));

                    // 직책(국문)
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] passPositionKoSplit = new String[10];
                    if(info.getPassPositionKo() != null){
                        if(info.getPassPositionKo().contains("^")){
                            passPositionKoSplit = info.getPassPositionKo().split("\\^");
                        }else{
                            passPositionKoSplit[0] = info.getPassPositionKo();
                        }
                    }else{
                        passPositionKoSplit[0] = "";
                    }
                    cell.setCellValue(convertValue(passPositionKoSplit, i));

                    // 직책(영문)
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] passPositionEnSplit = new String[10];
                    if(info.getPassPositionEn() != null){
                        if(info.getPassPositionEn().contains("^")){
                            passPositionEnSplit = info.getPassPositionEn().split("\\^");
                        }else{
                            passPositionEnSplit[0] = info.getPassPositionEn();
                        }
                    }else{
                        passPositionEnSplit[0] = "";
                    }
                    cell.setCellValue(convertValue(passPositionEnSplit, i));

                    // 비고
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    String[] passNoteSplit = new String[10];
                    if(info.getPassNote() != null){
                        if(info.getPassNote().contains("^")){
                            passNoteSplit = info.getPassNote().split("\\^");
                        }else{
                            passNoteSplit[0] = info.getPassNote();
                        }
                    }else{
                        passNoteSplit[0] = "";
                    }
                    cell.setCellValue(convertValue(passNoteSplit, i));
                }

                // 바이어 등록 정보
                // 회사명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyName() != null){
                    cell.setCellValue(info.getBuyerCompanyName().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 국가
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyCountry() != null){
                    cell.setCellValue(info.getBuyerCompanyCountry().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 소재지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyLocation() != null){
                    cell.setCellValue(info.getBuyerCompanyLocation().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 홈페이지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyHomepage() != null){
                    cell.setCellValue(info.getBuyerCompanyHomepage().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 부서
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyDepart() != null){
                    cell.setCellValue(info.getBuyerCompanyDepart().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 직책
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyPosition() != null){
                    cell.setCellValue(info.getBuyerCompanyPosition().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 이메일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyEmail() != null){
                    cell.setCellValue(info.getBuyerCompanyEmail().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 전화번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyTel() != null) {
                    cell.setCellValue(info.getBuyerCompanyTel().replaceAll("\\^", "\n"));
                }else{
                    cell.setCellValue("");
                }

                // 휴대전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyPhone() != null){
                    cell.setCellValue(info.getBuyerCompanyPhone().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // FAX
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyFax() != null) {
                    cell.setCellValue(info.getBuyerCompanyFax().replaceAll("\\^", "\n"));
                }else{
                    cell.setCellValue("");
                }

                // 취급품목
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyItem() != null) {
                    cell.setCellValue(info.getBuyerCompanyItem().replaceAll("\\^", "\n"));
                }else{
                    cell.setCellValue("");
                }

                // 초청사유
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyInviteReason() != null) {
                    cell.setCellValue(info.getBuyerCompanyInviteReason().replaceAll("\\^", "\n"));
                }else{
                    cell.setCellValue("");
                }

                // 계약진행여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyProgressYn() != null) {
                    cell.setCellValue(info.getBuyerCompanyProgressYn().replaceAll("\\^", "\n"));
                }else{
                    cell.setCellValue("");
                }

                // 희망사항
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getBuyerCompanyHope() != null){
                    cell.setCellValue(info.getBuyerCompanyHope().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 경품신청등록정보
                // 구분
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftGbn() != null){
                    cell.setCellValue(info.getGiftGbn().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 수량
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftCnt() != null){
                cell.setCellValue(info.getGiftCnt().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 분류
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftClassify() != null){
                    cell.setCellValue(info.getGiftClassify().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 품목명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftName() != null){
                    cell.setCellValue(info.getGiftName().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 경품설명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftContent() != null){
                    cell.setCellValue(info.getGiftContent().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 경품사진
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftPhoto() != null){
                    cell.setCellValue("-");
                }else{
                    cell.setCellValue("");
                }

                // 경품 브랜드 회사 로고
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftCompanyLogo() != null){
                    cell.setCellValue("-");
                }else{
                    cell.setCellValue("");
                }

                // 소비자가
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftPrice() != null){
                    cell.setCellValue(info.getGiftPrice().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 협찬가
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftSponsorPrice() != null){
                    cell.setCellValue(info.getGiftSponsorPrice().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }

                // 비고
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getGiftNote() != null){
                    cell.setCellValue(info.getGiftNote().replaceAll("\\^","\n"));
                }else{
                    cell.setCellValue("");
                }
            }

            //너비를 자동으로 다시 설정
            for (int i = 0; i < colNames_ex.length; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // excel 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 엑셀 파일명 설정
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping(value = "/mng/exhibitor/transfer/download.do", method = RequestMethod.GET)
    public void exhibitor_transfer_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_transfer_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // Workbook 생성
        try(SXSSFWorkbook workbook = new SXSSFWorkbook()){ // Excel 2007 이상

            /* 엑셀 그리기 */
            final String[] colNames_ex = {
                    /* 업체정보 */
                    "No",
                    "연도",
                    "회사명(국문)", "회사명(영문)", "주소", "상세주소",
                    "대표자", "전화", "홈페이지", "Fax", "사업자등록번호",
                    "기참가연도", "회원사 여부",
                    /* SNS(주소기입) */
                    "블로그", "페이스북", "인스타그램", "기타",
                    /* 담당자 정보 1 */
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 담당자 정보 2 */
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 담당자 정보 3 */
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 참가분야 */
                    "요트·보트전 (Yacht & Boat World)", "무동력보트전 (Paddler’s World)", "워크보트전 (Workboat World)", "해양레저관 (Marine Leisure)",
                    "카라반쇼 (Caravan Show)", "아라마리나 교육 및 체험 프로그램 (Aramarina Education and Experience Program)", "해양부품·안전·마리나산업전 (Marine Equipment, Safety & Marina Industry Show)",
                    "친환경 특별전 (Eco Friendly Marine)", "한국해양관광전 (Marine Tourism)", "보트정비관 (Boat Maintenance)",
                    /* 전시품목 1 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 2 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 3 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 4 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 5 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 6 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 7 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 8 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 9 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 전시품목 10 */
                    "전시품목", "전시품목;브랜드명", "실물 보트수;(단위:척)",
                    /* 업체정보 */
                    "회사소개(국문)", "회사소개(영문)", "KIBS참가목적(국문)", "KIBS참가목적(영문)",
                    "전시품소개(국문)", "전시품소개(영문)", "신제품 출품 사항 소개(국문)", "신제품 출품 사항 소개(영문)",
                    "우리 기업 부스는 꼭 들려야 될 이유가 있다면?", "행사/이벤트 진행계획", "사무국 요청사항", "수출 상담회 참가 희망 여부"
            };

            // 헤더 사이즈
            final int[] colWidths_ex = {
                    3000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000
            };

            workbook.setCompressTempFiles(true);

            // *** Style--------------------------------------------------
            //Font
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");	//글씨체
            fontHeader.setFontHeight((short)(9 * 20));	//사이즈
            fontHeader.setBold(true);	//볼드(굵게)
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");	//글씨체
            font9.setFontHeight((short)(9 * 20));	//사이즈
            // 엑셀 헤더 셋팅 default (참가업체정보)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.GOLD.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);
            headerStyle.setWrapText(true); //개행
            // 엑셀 바디 셋팅 default (참가업체정보)
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9);
            bodyStyle.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIME)
            CellStyle headerStyle_lime = workbook.createCellStyle();
            headerStyle_lime.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lime.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lime.setBorderRight(BorderStyle.THIN);
            headerStyle_lime.setBorderLeft(BorderStyle.THIN);
            headerStyle_lime.setBorderTop(BorderStyle.THIN);
            headerStyle_lime.setBorderBottom(BorderStyle.THIN);
            headerStyle_lime.setFillForegroundColor(IndexedColors.LIME.index);
            headerStyle_lime.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lime.setFont(fontHeader);
            headerStyle_lime.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_TURQUOISE)
            CellStyle headerStyle_light_turquoise = workbook.createCellStyle();
            headerStyle_light_turquoise.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_turquoise.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_turquoise.setBorderRight(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderTop(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_turquoise.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
            headerStyle_light_turquoise.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_turquoise.setFont(fontHeader);
            headerStyle_light_turquoise.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (CORNFLOWER_BLUE)
            CellStyle headerStyle_cornflower_blue = workbook.createCellStyle();
            headerStyle_cornflower_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_cornflower_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_cornflower_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_cornflower_blue.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
            headerStyle_cornflower_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_cornflower_blue.setFont(fontHeader);
            headerStyle_cornflower_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_BLUE)
            CellStyle headerStyle_light_blue = workbook.createCellStyle();
            headerStyle_light_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_light_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_light_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_blue.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
            headerStyle_light_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_blue.setFont(fontHeader);
            headerStyle_light_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (GREEN)
            CellStyle headerStyle_green = workbook.createCellStyle();
            headerStyle_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_green.setBorderRight(BorderStyle.THIN);
            headerStyle_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_green.setBorderTop(BorderStyle.THIN);
            headerStyle_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_green.setFillForegroundColor(IndexedColors.GREEN.index);
            headerStyle_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_green.setFont(fontHeader);
            headerStyle_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_GREEN)
            CellStyle headerStyle_light_green = workbook.createCellStyle();
            headerStyle_light_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_green.setBorderRight(BorderStyle.THIN);
            headerStyle_light_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_green.setBorderTop(BorderStyle.THIN);
            headerStyle_light_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_green.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            headerStyle_light_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_green.setFont(fontHeader);
            headerStyle_light_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_ORANGE)
            CellStyle headerStyle_light_orange = workbook.createCellStyle();
            headerStyle_light_orange.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_orange.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_orange.setBorderRight(BorderStyle.THIN);
            headerStyle_light_orange.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_orange.setBorderTop(BorderStyle.THIN);
            headerStyle_light_orange.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_orange.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
            headerStyle_light_orange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_orange.setFont(fontHeader);
            headerStyle_light_orange.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (ROSE)
            CellStyle headerStyle_rose = workbook.createCellStyle();
            headerStyle_rose.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_rose.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_rose.setBorderRight(BorderStyle.THIN);
            headerStyle_rose.setBorderLeft(BorderStyle.THIN);
            headerStyle_rose.setBorderTop(BorderStyle.THIN);
            headerStyle_rose.setBorderBottom(BorderStyle.THIN);
            headerStyle_rose.setFillForegroundColor(IndexedColors.ROSE.index);
            headerStyle_rose.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_rose.setFont(fontHeader);
            headerStyle_rose.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (TAN)
            CellStyle headerStyle_tan = workbook.createCellStyle();
            headerStyle_tan.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_tan.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_tan.setBorderRight(BorderStyle.THIN);
            headerStyle_tan.setBorderLeft(BorderStyle.THIN);
            headerStyle_tan.setBorderTop(BorderStyle.THIN);
            headerStyle_tan.setBorderBottom(BorderStyle.THIN);
            headerStyle_tan.setFillForegroundColor(IndexedColors.TAN.index);
            headerStyle_tan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_tan.setFont(fontHeader);
            headerStyle_tan.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LAVENDER)
            CellStyle headerStyle_lavender = workbook.createCellStyle();
            headerStyle_lavender.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lavender.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lavender.setBorderRight(BorderStyle.THIN);
            headerStyle_lavender.setBorderLeft(BorderStyle.THIN);
            headerStyle_lavender.setBorderTop(BorderStyle.THIN);
            headerStyle_lavender.setBorderBottom(BorderStyle.THIN);
            headerStyle_lavender.setFillForegroundColor(IndexedColors.LAVENDER.index);
            headerStyle_lavender.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lavender.setFont(fontHeader);
            headerStyle_lavender.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LEMON_CHIFFON)
            CellStyle headerStyle_lemon_chiffon = workbook.createCellStyle();
            headerStyle_lemon_chiffon.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lemon_chiffon.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lemon_chiffon.setBorderRight(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderLeft(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderTop(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderBottom(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
            headerStyle_lemon_chiffon.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lemon_chiffon.setFont(fontHeader);
            headerStyle_lemon_chiffon.setWrapText(true); //개행

            //rows
            int rowCnt = 0;

            // *** Sheet-------------------------------------------------
            // Sheet 생성!
            SXSSFSheet sheet = workbook.createSheet("Exhibitor");

            SXSSFCell cell = null;
            SXSSFRow row = sheet.createRow(rowCnt++);

            sheet.createFreezePane(0,2);

            // 헤더 정보 구성
            // 참가업체정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,12));
            SXSSFCell mergeCell = row.createCell(0);
            mergeCell.setCellStyle(headerStyle);
            mergeCell.setCellValue("참가업체정보");

            // SNS
            sheet.addMergedRegion(new CellRangeAddress(0,0,13,16));
            SXSSFCell mergeCell2 = row.createCell(13);
            mergeCell2.setCellStyle(headerStyle_light_blue);
            mergeCell2.setCellValue("SNS");

            // 담당자 정보 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,17,22));
            SXSSFCell mergeCell3 = row.createCell(17);
            mergeCell3.setCellStyle(headerStyle_light_orange);
            mergeCell3.setCellValue("담당자 정보 1");

            // 담당자 정보 2
            sheet.addMergedRegion(new CellRangeAddress(0,0,23,28));
            SXSSFCell mergeCell4 = row.createCell(23);
            mergeCell4.setCellStyle(headerStyle_light_orange);
            mergeCell4.setCellValue("담당자 정보 2");

            // 담당자 정보 3
            sheet.addMergedRegion(new CellRangeAddress(0,0,29,34));
            SXSSFCell mergeCell5 = row.createCell(29);
            mergeCell5.setCellStyle(headerStyle_light_orange);
            mergeCell5.setCellValue("담당자 정보 3");

            // 참가분야
            sheet.addMergedRegion(new CellRangeAddress(0,0,35,44));
            SXSSFCell mergeCell6 = row.createCell(35);
            mergeCell6.setCellStyle(headerStyle_lime);
            mergeCell6.setCellValue("참가분야");

            // 전시품목 1
            sheet.addMergedRegion(new CellRangeAddress(0,0,45,47));
            SXSSFCell mergeCell7 = row.createCell(45);
            mergeCell7.setCellStyle(headerStyle_light_green);
            mergeCell7.setCellValue("전시품목 1");

            // 전시품목 2
            sheet.addMergedRegion(new CellRangeAddress(0,0,48,50));
            SXSSFCell mergeCell8 = row.createCell(48);
            mergeCell8.setCellStyle(headerStyle_light_green);
            mergeCell8.setCellValue("전시품목 2");

            // 전시품목 3
            sheet.addMergedRegion(new CellRangeAddress(0,0,51,53));
            SXSSFCell mergeCell9 = row.createCell(51);
            mergeCell9.setCellStyle(headerStyle_light_green);
            mergeCell9.setCellValue("전시품목 3");

            // 전시품목 4
            sheet.addMergedRegion(new CellRangeAddress(0,0,54,56));
            SXSSFCell mergeCell10 = row.createCell(54);
            mergeCell10.setCellStyle(headerStyle_light_green);
            mergeCell10.setCellValue("전시품목 4");

            // 전시품목 5
            sheet.addMergedRegion(new CellRangeAddress(0,0,57,59));
            SXSSFCell mergeCell11 = row.createCell(57);
            mergeCell11.setCellStyle(headerStyle_light_green);
            mergeCell11.setCellValue("전시품목 5");

            // 전시품목 6
            sheet.addMergedRegion(new CellRangeAddress(0,0,60,62));
            SXSSFCell mergeCell12 = row.createCell(60);
            mergeCell12.setCellStyle(headerStyle_light_green);
            mergeCell12.setCellValue("전시품목 6");

            // 전시품목 7
            sheet.addMergedRegion(new CellRangeAddress(0,0,63,65));
            SXSSFCell mergeCell13 = row.createCell(63);
            mergeCell13.setCellStyle(headerStyle_light_green);
            mergeCell13.setCellValue("전시품목 7");

            // 전시품목 8
            sheet.addMergedRegion(new CellRangeAddress(0,0,66,68));
            SXSSFCell mergeCell14 = row.createCell(66);
            mergeCell14.setCellStyle(headerStyle_light_green);
            mergeCell14.setCellValue("전시품목 8");

            // 전시품목 9
            sheet.addMergedRegion(new CellRangeAddress(0,0,69,71));
            SXSSFCell mergeCell15 = row.createCell(69);
            mergeCell15.setCellStyle(headerStyle_light_green);
            mergeCell15.setCellValue("전시품목 9");

            // 전시품목 10
            sheet.addMergedRegion(new CellRangeAddress(0,0,72,74));
            SXSSFCell mergeCell16 = row.createCell(72);
            mergeCell16.setCellStyle(headerStyle_light_green);
            mergeCell16.setCellValue("전시품목 10");

            // 참가업체정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,75,86));
            SXSSFCell mergeCell27 = row.createCell(75);
            mergeCell27.setCellStyle(headerStyle);
            mergeCell27.setCellValue("참가업체정보");

            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                cell = row.createCell(i);
                if(i < 13) {
                    cell.setCellStyle(headerStyle);
                }else if(i<17){
                    cell.setCellStyle(headerStyle_light_blue);
                }else if(i<35){
                    cell.setCellStyle(headerStyle_light_orange);
                }else if(i<45){
                    cell.setCellStyle(headerStyle_lime);
                }else if(i<75){
                    cell.setCellStyle(headerStyle_light_green);
                }else{
                    cell.setCellStyle(headerStyle);
                }
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(colWidths_ex[i]) + 1024));	//column width 지정
            }

            // 데이터 조회
            ExhibitorDetailDTO exhibitorDetailDTO = new ExhibitorDetailDTO();
            exhibitorDetailDTO.setTransferYear(transferYear);
            List<ExhibitorDetailDTO> exhibitorDetailList = kibsMngService.processSelectTransferExhibitorDetailList(exhibitorDetailDTO);

            int cellCnt = 0;
            int listCount = exhibitorDetailList.size();

            //데이터 부분 생성
            for(ExhibitorDetailDTO info : exhibitorDetailList) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                int nCount = 0;
                String[] remark = info.getCompanyNameKo().split("\\^");

                //줄 높이 계산
                for (String s : remark) {
                    if (s.length() > 0) {
                        nCount++;
                    }
                }

                //줄 높이 설정
                if (nCount > 1){
                    row.setHeightInPoints((nCount * sheet.getDefaultRowHeightInPoints()));
                }

                // 넘버링
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);

                // 연도
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getTransferYear());

                // 회사명(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameKo());

                // 회사명(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameEn());

                // 주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddress());

                // 상세주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddressDetail());

                // 대표자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyCeo());

                // 전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyTel());

                // 홈페이지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyHomepage());

                // Fax
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyFax());

                // 사업자등록번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyLicenseNum());

                // 기참가연도
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPrePartYear());

                // 회원사 여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getMemberCompanyYn());

                // 블로그
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsBlog());

                // 페이스북
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsFacebook());

                // 인스타그램
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsInstagram());

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSnsEtc());

                // 담당자정보
                for(int i=0; i<3; i++){
                    // 성명
                    String[] chargePersonNameSplit = new String[3];
                    if(info.getChargePersonName() != null){
                        if(info.getChargePersonName().contains("^")){
                            chargePersonNameSplit = info.getChargePersonName().split("\\^");
                        }else{
                            chargePersonNameSplit[0] = info.getChargePersonName().replaceAll("\\^","");
                        }
                    }else{
                        chargePersonNameSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonNameSplit, i));

                    // 직위
                    String[] chargePersonPositionSplit = new String[3];
                    if(info.getChargePersonPosition() != null){
                        if(info.getChargePersonPosition().contains("^")){
                            chargePersonPositionSplit = info.getChargePersonPosition().split("\\^");
                        }else{
                            chargePersonPositionSplit[0] = info.getChargePersonPosition();
                        }
                    }else{
                        chargePersonPositionSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonPositionSplit, i));

                    // 부서
                    String[] chargePersonDepartSplit = new String[3];
                    if(info.getChargePersonDepart() != null){
                        if(info.getChargePersonDepart().contains("^")){
                            chargePersonDepartSplit = info.getChargePersonDepart().split("\\^");
                        }else{
                            chargePersonDepartSplit[0] = info.getChargePersonDepart();
                        }
                    }else{
                        chargePersonDepartSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonDepartSplit, i));

                    // 전화번호
                    String[] chargePersonTelSplit = new String[3];
                    if(info.getChargePersonTel() != null){
                        if(info.getChargePersonTel().contains("^")){
                            chargePersonTelSplit = info.getChargePersonTel().split("\\^");
                        }else{
                            chargePersonTelSplit[0] = info.getChargePersonTel();
                        }
                    }else{
                        chargePersonTelSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonTelSplit, i));

                    // 휴대전화
                    String[] chargePersonPhoneSplit = new String[3];
                    if(info.getChargePersonPhone() != null){
                        if(info.getChargePersonPhone().contains("^")){
                            chargePersonPhoneSplit = info.getChargePersonPhone().split("\\^");
                        }else{
                            chargePersonPhoneSplit[0] = info.getChargePersonPhone();
                        }
                    }else{
                        chargePersonPhoneSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonPhoneSplit, i));

                    // 이메일
                    String[] chargePersonEmailSplit = new String[3];
                    if(info.getChargePersonEmail() != null){
                        if(info.getChargePersonEmail().contains("^")){
                            chargePersonEmailSplit = info.getChargePersonEmail().split("\\^");
                        }else{
                            chargePersonEmailSplit[0] = info.getChargePersonEmail();
                        }
                    }else{
                        chargePersonEmailSplit[0] = "";
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(chargePersonEmailSplit, i));
                }

                // 참가분야
                String fieldPart = "";
                if(info.getFieldPart() != null && !"".equals(info.getFieldPart())) {
                    fieldPart = info.getFieldPart().replaceAll(" ","");
                }

                // 요트·보트전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("요트·보트전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 무동력보트전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("무동력보트전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 워크보트전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("워크보트전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양레저관
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("해양레저관")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 카라반쇼
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("카라반쇼")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 아라마리나 교육 및 체험 프로그램
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("아라마리나")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양부품·안전·마리나산업전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("해양부품")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 친환경 특별전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("친환경")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 한국해양관광전
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("한국해양관광전")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트정비관
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(fieldPart.contains("보트정비관")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 전시품목
                for(int i=0; i<10; i++){
                    // 전시품목
                    String[] displayItemSplit = new String[3];
                    if(info.getDisplayItem().contains("^")){
                        displayItemSplit = info.getDisplayItem().split("\\^");
                    }else{
                        displayItemSplit[0] = info.getDisplayItem();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayItemSplit, i));

                    // 브랜드명
                    String[] displayBrandSplit = new String[3];
                    if(info.getDisplayBrand().contains("^")){
                        displayBrandSplit = info.getDisplayBrand().split("\\^");
                    }else{
                        displayBrandSplit[0] = info.getDisplayBrand();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayBrandSplit, i));

                    // 실물보트수
                    String[] displayBoatCntSplit = new String[3];
                    if(info.getDisplayBoatCnt().contains("^")){
                        displayBoatCntSplit = info.getDisplayBoatCnt().split("\\^");
                    }else{
                        displayBoatCntSplit[0] = info.getDisplayBoatCnt();
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(displayBoatCntSplit, i));
                }

                // 회사소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroKo());

                // 회사소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroEn());

                // KIBS 참가목적(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyPurposeKo());

                // KIBS 참가목적(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyPurposeEn());

                // 전시품소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getDisplayItemIntroKo());

                // 전시품소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getDisplayItemIntroEn());

                // 신제품 출품 사항 소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getNewItemIntroKo());

                // 신제품 출품 사항 소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getNewItemIntroEn());

                // 우리 기업 부스 ~
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getBoothVisitReason());

                // 행사/이벤트 진행계획
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getEventPlan());

                // 사무국 요청사항
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getOfficeRequest());

                // 수출 상담회 참가 희망 여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getExportMeetingYn());

            }

            //너비를 자동으로 다시 설정
            for (int i = 0; i < colNames_ex.length; i++) {
                sheet.trackColumnForAutoSizing(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // excel 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 엑셀 파일명 설정
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping(value = "/mng/visitor/download.do", method = RequestMethod.GET)
    public void visitor_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > visitor_download");
        String fileName = req.getParameter("fileName");
        String joinYear = req.getParameter("joinYear");

        // Workbook 생성
        try(SXSSFWorkbook workbook = new SXSSFWorkbook()){ // Excel 2007 이상

            /* 엑셀 그리기 */
            final String[] colNames_ex = {
                    /* 업체정보 */
                    "No", "연도", "구분", "참석여부", "등록일", "수정일",
                    /* 참관 구분 */
                    "참관구분",
                    /* 참관객 정보 */
                    "성명", "전화번호", "휴대전화", "이메일", "직장명", "동반자 여부",
                    /* 설문항목 */
                    "성별", "지역(시/도)", "지역(구/군)", "연령대",
                    /* 관람 구분 */
                    "조종면허 보유자", "보트 소유자", "보트 구매 예정자", "관련 업종 종사자", "낚시 매니아",
                    "다이빙 매니아", "캠핑카 매니아", "학생", "일반관람", "기타",
                    /* 보트쇼 방문 목적*/
                    "업계동향 파악 및 정보수집", "제품구매 및 기술도입 상담", "기존 거래업체 방문", "차기 전시회 참가여부 파악",
                    "일반관람", "기타",
                    /* 관심품목 */
                    "요트/보트", "워크보트", "낚시", "다이빙", "카누/카약/무동력", "워터스포츠", "해양부품", "캠핑",
                    "친환경", "해양관광", "기타",
                    /* 인지경로 */
                    "뉴스레터", "옥외광고물", "홈페이지", "전문지", "온라인 커뮤니티", "오프라인매장", "소셜 네트워크",
                    "초청장", "방송광고", "KIBS 2024 참석", "낚시박람회", "기타",
                    /* 지난 전시회 참관 여부 */
                    "첫 참관", "2008", "2009", "2010", "2011",
                    "2012", "2013", "2014", "2015", "2016",
                    "2017", "2018", "2019", "2020", "2021",
                    "2022", "2023", "2024",
                    /* 동반자 1 */
                    "성명", "연령",
                    /* 동반자 2 */
                    "성명", "연령",
                    /* 동반자 3 */
                    "성명", "연령",
                    /* 동반자 4 */
                    "성명", "연령",
                    /* 동반자 5 */
                    "성명", "연령"
            };

            // 헤더 사이즈
            final int[] colWidths_ex = {
                    3000, 5000, 5000, 5000, 5000, 5000,
                    5000,
                    5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000,
                    5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000,
                    5000, 5000,
                    5000, 5000,
                    5000, 5000,
                    5000, 5000,
                    5000, 5000
            };

            // *** Style--------------------------------------------------
            //Font
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");	//글씨체
            fontHeader.setFontHeight((short)(9 * 20));	//사이즈
            fontHeader.setBold(true);	//볼드(굵게)
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");	//글씨체
            font9.setFontHeight((short)(9 * 20));	//사이즈
            // 엑셀 헤더 셋팅 default (참가업체정보)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.GOLD.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);
            headerStyle.setWrapText(true); //개행
            // 엑셀 바디 셋팅 default (참가업체정보)
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9);
            bodyStyle.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIME)
            CellStyle headerStyle_lime = workbook.createCellStyle();
            headerStyle_lime.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lime.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lime.setBorderRight(BorderStyle.THIN);
            headerStyle_lime.setBorderLeft(BorderStyle.THIN);
            headerStyle_lime.setBorderTop(BorderStyle.THIN);
            headerStyle_lime.setBorderBottom(BorderStyle.THIN);
            headerStyle_lime.setFillForegroundColor(IndexedColors.LIME.index);
            headerStyle_lime.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lime.setFont(fontHeader);
            headerStyle_lime.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_TURQUOISE)
            CellStyle headerStyle_light_turquoise = workbook.createCellStyle();
            headerStyle_light_turquoise.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_turquoise.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_turquoise.setBorderRight(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderTop(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_turquoise.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
            headerStyle_light_turquoise.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_turquoise.setFont(fontHeader);
            headerStyle_light_turquoise.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (CORNFLOWER_BLUE)
            CellStyle headerStyle_cornflower_blue = workbook.createCellStyle();
            headerStyle_cornflower_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_cornflower_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_cornflower_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_cornflower_blue.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
            headerStyle_cornflower_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_cornflower_blue.setFont(fontHeader);
            headerStyle_cornflower_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_BLUE)
            CellStyle headerStyle_light_blue = workbook.createCellStyle();
            headerStyle_light_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_light_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_light_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_blue.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
            headerStyle_light_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_blue.setFont(fontHeader);
            headerStyle_light_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (GREEN)
            CellStyle headerStyle_green = workbook.createCellStyle();
            headerStyle_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_green.setBorderRight(BorderStyle.THIN);
            headerStyle_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_green.setBorderTop(BorderStyle.THIN);
            headerStyle_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_green.setFillForegroundColor(IndexedColors.GREEN.index);
            headerStyle_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_green.setFont(fontHeader);
            headerStyle_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_GREEN)
            CellStyle headerStyle_light_green = workbook.createCellStyle();
            headerStyle_light_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_green.setBorderRight(BorderStyle.THIN);
            headerStyle_light_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_green.setBorderTop(BorderStyle.THIN);
            headerStyle_light_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_green.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            headerStyle_light_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_green.setFont(fontHeader);
            headerStyle_light_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_ORANGE)
            CellStyle headerStyle_light_orange = workbook.createCellStyle();
            headerStyle_light_orange.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_orange.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_orange.setBorderRight(BorderStyle.THIN);
            headerStyle_light_orange.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_orange.setBorderTop(BorderStyle.THIN);
            headerStyle_light_orange.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_orange.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
            headerStyle_light_orange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_orange.setFont(fontHeader);
            headerStyle_light_orange.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (ROSE)
            CellStyle headerStyle_rose = workbook.createCellStyle();
            headerStyle_rose.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_rose.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_rose.setBorderRight(BorderStyle.THIN);
            headerStyle_rose.setBorderLeft(BorderStyle.THIN);
            headerStyle_rose.setBorderTop(BorderStyle.THIN);
            headerStyle_rose.setBorderBottom(BorderStyle.THIN);
            headerStyle_rose.setFillForegroundColor(IndexedColors.ROSE.index);
            headerStyle_rose.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_rose.setFont(fontHeader);
            headerStyle_rose.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (TAN)
            CellStyle headerStyle_tan = workbook.createCellStyle();
            headerStyle_tan.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_tan.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_tan.setBorderRight(BorderStyle.THIN);
            headerStyle_tan.setBorderLeft(BorderStyle.THIN);
            headerStyle_tan.setBorderTop(BorderStyle.THIN);
            headerStyle_tan.setBorderBottom(BorderStyle.THIN);
            headerStyle_tan.setFillForegroundColor(IndexedColors.TAN.index);
            headerStyle_tan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_tan.setFont(fontHeader);
            headerStyle_tan.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LAVENDER)
            CellStyle headerStyle_lavender = workbook.createCellStyle();
            headerStyle_lavender.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lavender.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lavender.setBorderRight(BorderStyle.THIN);
            headerStyle_lavender.setBorderLeft(BorderStyle.THIN);
            headerStyle_lavender.setBorderTop(BorderStyle.THIN);
            headerStyle_lavender.setBorderBottom(BorderStyle.THIN);
            headerStyle_lavender.setFillForegroundColor(IndexedColors.LAVENDER.index);
            headerStyle_lavender.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lavender.setFont(fontHeader);
            headerStyle_lavender.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LEMON_CHIFFON)
            CellStyle headerStyle_lemon_chiffon = workbook.createCellStyle();
            headerStyle_lemon_chiffon.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lemon_chiffon.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lemon_chiffon.setBorderRight(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderLeft(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderTop(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderBottom(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
            headerStyle_lemon_chiffon.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lemon_chiffon.setFont(fontHeader);
            headerStyle_lemon_chiffon.setWrapText(true); //개행

            //rows
            int rowCnt = 0;

            // *** Sheet-------------------------------------------------
            // Sheet 생성
            SXSSFSheet sheet = workbook.createSheet("visitor");

            SXSSFCell cell = null;
            SXSSFRow row = sheet.createRow(rowCnt++);

            // 헤더 정보 구성
            // 기본
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
            SXSSFCell mergeCell = row.createCell(0);
            mergeCell.setCellStyle(headerStyle);
            mergeCell.setCellValue("기본정보");

            // 참관객 정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,7,12));
            SXSSFCell mergeCell2 = row.createCell(7);
            mergeCell2.setCellStyle(headerStyle);
            mergeCell2.setCellValue("참관객 정보");

            // 설문항목
            sheet.addMergedRegion(new CellRangeAddress(0,0,13,16));
            SXSSFCell mergeCell3 = row.createCell(13);
            mergeCell3.setCellStyle(headerStyle);
            mergeCell3.setCellValue("설문항목");

            // 관람 구분
            sheet.addMergedRegion(new CellRangeAddress(0,0,17,26));
            SXSSFCell mergeCell4 = row.createCell(17);
            mergeCell4.setCellStyle(headerStyle);
            mergeCell4.setCellValue("관람 구분");

            // 보트쇼 방문 목적
            sheet.addMergedRegion(new CellRangeAddress(0,0,27,32));
            SXSSFCell mergeCell5 = row.createCell(27);
            mergeCell5.setCellStyle(headerStyle);
            mergeCell5.setCellValue("보트쇼 방문 목적");

            // 관심품목
            sheet.addMergedRegion(new CellRangeAddress(0,0,33,43));
            SXSSFCell mergeCell6 = row.createCell(33);
            mergeCell6.setCellStyle(headerStyle);
            mergeCell6.setCellValue("관심품목");

            // 인지경로
            sheet.addMergedRegion(new CellRangeAddress(0,0,44,55));
            SXSSFCell mergeCell7 = row.createCell(44);
            mergeCell7.setCellStyle(headerStyle);
            mergeCell7.setCellValue("인지경로");

            // 지난 전시회 참관 여부
            sheet.addMergedRegion(new CellRangeAddress(0,0,56,73));
            SXSSFCell mergeCell8 = row.createCell(56);
            mergeCell8.setCellStyle(headerStyle);
            mergeCell8.setCellValue("지난 전시회 참관 여부");

            // 동반자
            sheet.addMergedRegion(new CellRangeAddress(0,0,74,83));
            SXSSFCell mergeCell9 = row.createCell(74);
            mergeCell9.setCellStyle(headerStyle);
            mergeCell9.setCellValue("동반자");

            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(colWidths_ex[i]) + 1024));	//column width 지정
            }

            // 데이터 조회
            VisitorDetailDTO visitorDetailDTO = new VisitorDetailDTO();
            visitorDetailDTO.setJoinYear(joinYear);
            List<VisitorDetailDTO> visitorDetailList = kibsMngService.processSelectVisitorDetailList(visitorDetailDTO);

            int cellCnt = 0;
            int listCount = visitorDetailList.size();

            //데이터 부분 생성
            for(VisitorDetailDTO info : visitorDetailList) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                int nCount = 0;
                String[] remark = info.getName().split("\\^");

                //줄 높이 계산
                for (String s : remark) {
                    if (s.length() > 0) {
                        nCount++;
                    }
                }

                //줄 높이 설정
                if (nCount > 1){
                    row.setHeightInPoints((nCount * sheet.getDefaultRowHeightInPoints()));
                }

                // 넘버링
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);

                // 연도
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getJoinYear());

                // 구분
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getVisitorGbn());

                // 참석여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                String joinYn = "참석확인";
                if("N".equals(info.getJoinYn())){
                    joinYn = "참석취소";
                }
                cell.setCellValue(joinYn);

                // 등록일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getInitRegiDttm());

                // 수정일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFinalRegiDttm());

                // 참관구분
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPartGbn());

                // 참관객 정보
                // 성명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getName());

                // 전화번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getTel() != null){
                    cell.setCellValue(info.getTel());
                }else{
                    cell.setCellValue("-");
                }

                // 휴대전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPhone());

                // 이메일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getEmail() + "@" + info.getDomain());

                // 직장명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyName());

                // 동반자여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if("Y".equals(info.getPartnerYn())){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 설문항목
                // 성별
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSex());

                // 지역(시/도)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getRegionSi());

                // 지역(구/군)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getRegionGu());

                // 연령대
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getAgeGroup() != null){
                    cell.setCellValue(info.getAgeGroup() + "대");
                }else{
                    cell.setCellValue("");
                }

                // 참관구분
                String observationGbn = info.getObservationGbn();
                if(observationGbn == null){
                    observationGbn = "";
                }

                // 조정면허 보유자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("조정면허 보유자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트 소유자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("보트 소유자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트 구매 예정자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("보트 구매 예정자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 관련 업종 종사자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("관련 업종 종사자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 낚시 매니아
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("낚시 매니아")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 다이빙 매니아
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("다이빙 매니아")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 캠핑카 매니아
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("캠핑카 매니아")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 학생
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("학생")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 일반관람
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("일반관람")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트쇼 방문 목적
                String visitPurpose = info.getVisitPurpose();
                if(visitPurpose == null){
                    visitPurpose = "";
                }

                // 업계동향 파악 및 정보수집
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("업계동향 파악 및 정보수집")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 제품구매 및 기술도입 상담
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("제품구매 및 기술도입 상담")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기존 거래업체 방문
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("기존 거래업체 방문")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 차기 전시회 참가여부 파악
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("차기 전시회 참가여부 파악")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 일반관람
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("일반관람")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 관심품목
                String interestItem = info.getInterestItem();
                if(interestItem == null){
                    interestItem = "";
                }

                // 요트/보트
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("요트/보트")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 워크보트
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("워크보트")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 낚시
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("낚시")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 다이빙
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("다이빙")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 카누/카약/무동력
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("카누/카약/무동력")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 워터스포츠
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("워터스포츠")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양부품
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("해양부품")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 캠핑
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("캠핑")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 친환경
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("친환경")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양관광
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("해양관광")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 인지경로
                String recognizePath = info.getRecognizePath();
                if(recognizePath == null){
                    recognizePath = "";
                }

                // 뉴스레터
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("뉴스레터")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 옥외광고물
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("옥외광고물")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 홈페이지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("홈페이지")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 전문지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("전문지")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 온라인 커뮤니티
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("온라인 커뮤니티")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 오프라인매장
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("오프라인매장")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 소셜 네트워크
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("소셜 네트워크")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 초청장
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("초청장")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 방송광고
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("방송광고")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // KIBS 2024 참석
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("KIBS 2024 참석")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 낚시박람회
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("낚시박람회")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 지난 전시회 참관 여부
                String preObservationGbn = info.getPreObservationGbn();
                if(preObservationGbn == null) {
                    preObservationGbn = "";
                }

                // 첫 참관
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("first")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2008
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2008")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2009
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2009")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2010
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2010")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2011
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2011")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2012
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2012")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2013
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2013")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2014
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2014")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2015
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2015")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2016
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2016")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2017
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2017")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2018
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2018")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2019
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2019")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2020
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2020")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2021
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2021")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2022
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2022")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2023
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2023")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2024
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2024")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }
                
                // 동반자
                for(int i=0; i<5; i++) {
                    // 성명
                    String[] partnerNameSplit = new String[5];
                    if(info.getPartnerName() != null && !"".equals(info.getPartnerName())){
                        if (info.getPartnerName().contains("^")) {
                            partnerNameSplit = info.getPartnerName().split("\\^");
                        } else {
                            partnerNameSplit[0] = info.getPartnerName();
                        }
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(partnerNameSplit, i));

                    // 나이
                    String[] partnerAgeSplit = new String[5];
                    if(info.getPartnerAge() != null && !"".equals(info.getPartnerAge())){
                        if (info.getPartnerAge().contains("^")) {
                            partnerAgeSplit = info.getPartnerAge().split("\\^");
                        } else {
                            partnerAgeSplit[0] = info.getPartnerAge();
                        }
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(partnerAgeSplit, i));
                }

            }

            //너비를 자동으로 다시 설정
            for (int i = 0; i < colNames_ex.length; i++) {
                sheet.trackColumnForAutoSizing(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // excel 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 엑셀 파일명 설정
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping(value = "/mng/visitor/join/download.do", method = RequestMethod.GET)
    public void visitor_join_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > visitor_join_download");
        String fileName = req.getParameter("fileName");
        String joinYear = req.getParameter("joinYear");

        // Workbook 생성
        try(SXSSFWorkbook workbook = new SXSSFWorkbook()){ // Excel 2007 이상

            /* 엑셀 그리기 */
            final String[] colNames_ex = {
                    /* 업체정보 */
                    "No", "연도", "구분", "참석여부", "등록일", "수정일",
                    /* 참관객 정보 */
                    "성명", "전화번호", "휴대전화", "이메일", "직장명", "동반자 여부",
                    /* 설문항목 */
                    "성별", "지역(시/도)", "지역(구/군)", "연령대",
                    /* 참관 구분 */
                    "조종면허 보유자", "보트 소유자", "보트 구매 예정자", "관련 업종 종사자", "낚시 매니아",
                    "다이빙 매니아", "캠핑카 매니아", "학생", "일반관람", "기타",
                    /* 보트쇼 방문 목적*/
                    "업계동향 파악 및 정보수집", "제품구매 및 기술도입 상담", "기존 거래업체 방문", "차기 전시회 참가여부 파악",
                    "일반관람", "기타",
                    /* 관심품목 */
                    "요트/보트", "워크보트", "낚시", "다이빙", "카누/카약/무동력", "워터스포츠", "해양부품", "캠핑",
                    "친환경", "해양관광", "기타",
                    /* 인지경로 */
                    "뉴스레터", "옥외광고물", "홈페이지", "전문지", "온라인 커뮤니티", "오프라인매장", "소셜 네트워크",
                    "초청장", "방송광고", "KIBS 2024 참석", "낚시박람회", "기타",
                    /* 지난 전시회 참관 여부 */
                    "첫 참관", "2008", "2009", "2010", "2011",
                    "2012", "2013", "2014", "2015", "2016",
                    "2017", "2018", "2019", "2020", "2021",
                    "2022", "2023", "2024",
                    /* 동반자 1 */
                    "성명", "연령",
                    /* 동반자 2 */
                    "성명", "연령",
                    /* 동반자 3 */
                    "성명", "연령",
                    /* 동반자 4 */
                    "성명", "연령",
                    /* 동반자 5 */
                    "성명", "연령"
            };

            // 헤더 사이즈
            final int[] colWidths_ex = {
                    3000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000,
                    5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000,
                    5000, 5000,
                    5000, 5000,
                    5000, 5000,
                    5000, 5000,
                    5000, 5000
            };

            workbook.setCompressTempFiles(true);

            // *** Style--------------------------------------------------
            //Font
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");	//글씨체
            fontHeader.setFontHeight((short)(9 * 20));	//사이즈
            fontHeader.setBold(true);	//볼드(굵게)
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");	//글씨체
            font9.setFontHeight((short)(9 * 20));	//사이즈
            // 엑셀 헤더 셋팅 default (참가업체정보)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.GOLD.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);
            headerStyle.setWrapText(true); //개행
            // 엑셀 바디 셋팅 default (참가업체정보)
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9);
            bodyStyle.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIME)
            CellStyle headerStyle_lime = workbook.createCellStyle();
            headerStyle_lime.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lime.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lime.setBorderRight(BorderStyle.THIN);
            headerStyle_lime.setBorderLeft(BorderStyle.THIN);
            headerStyle_lime.setBorderTop(BorderStyle.THIN);
            headerStyle_lime.setBorderBottom(BorderStyle.THIN);
            headerStyle_lime.setFillForegroundColor(IndexedColors.LIME.index);
            headerStyle_lime.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lime.setFont(fontHeader);
            headerStyle_lime.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_TURQUOISE)
            CellStyle headerStyle_light_turquoise = workbook.createCellStyle();
            headerStyle_light_turquoise.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_turquoise.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_turquoise.setBorderRight(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderTop(BorderStyle.THIN);
            headerStyle_light_turquoise.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_turquoise.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
            headerStyle_light_turquoise.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_turquoise.setFont(fontHeader);
            headerStyle_light_turquoise.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (CORNFLOWER_BLUE)
            CellStyle headerStyle_cornflower_blue = workbook.createCellStyle();
            headerStyle_cornflower_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_cornflower_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_cornflower_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_cornflower_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_cornflower_blue.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
            headerStyle_cornflower_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_cornflower_blue.setFont(fontHeader);
            headerStyle_cornflower_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_BLUE)
            CellStyle headerStyle_light_blue = workbook.createCellStyle();
            headerStyle_light_blue.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_blue.setBorderRight(BorderStyle.THIN);
            headerStyle_light_blue.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_blue.setBorderTop(BorderStyle.THIN);
            headerStyle_light_blue.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_blue.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
            headerStyle_light_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_blue.setFont(fontHeader);
            headerStyle_light_blue.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (GREEN)
            CellStyle headerStyle_green = workbook.createCellStyle();
            headerStyle_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_green.setBorderRight(BorderStyle.THIN);
            headerStyle_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_green.setBorderTop(BorderStyle.THIN);
            headerStyle_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_green.setFillForegroundColor(IndexedColors.GREEN.index);
            headerStyle_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_green.setFont(fontHeader);
            headerStyle_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_GREEN)
            CellStyle headerStyle_light_green = workbook.createCellStyle();
            headerStyle_light_green.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_green.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_green.setBorderRight(BorderStyle.THIN);
            headerStyle_light_green.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_green.setBorderTop(BorderStyle.THIN);
            headerStyle_light_green.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_green.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            headerStyle_light_green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_green.setFont(fontHeader);
            headerStyle_light_green.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LIGHT_ORANGE)
            CellStyle headerStyle_light_orange = workbook.createCellStyle();
            headerStyle_light_orange.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_light_orange.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_light_orange.setBorderRight(BorderStyle.THIN);
            headerStyle_light_orange.setBorderLeft(BorderStyle.THIN);
            headerStyle_light_orange.setBorderTop(BorderStyle.THIN);
            headerStyle_light_orange.setBorderBottom(BorderStyle.THIN);
            headerStyle_light_orange.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
            headerStyle_light_orange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_light_orange.setFont(fontHeader);
            headerStyle_light_orange.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (ROSE)
            CellStyle headerStyle_rose = workbook.createCellStyle();
            headerStyle_rose.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_rose.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_rose.setBorderRight(BorderStyle.THIN);
            headerStyle_rose.setBorderLeft(BorderStyle.THIN);
            headerStyle_rose.setBorderTop(BorderStyle.THIN);
            headerStyle_rose.setBorderBottom(BorderStyle.THIN);
            headerStyle_rose.setFillForegroundColor(IndexedColors.ROSE.index);
            headerStyle_rose.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_rose.setFont(fontHeader);
            headerStyle_rose.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (TAN)
            CellStyle headerStyle_tan = workbook.createCellStyle();
            headerStyle_tan.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_tan.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_tan.setBorderRight(BorderStyle.THIN);
            headerStyle_tan.setBorderLeft(BorderStyle.THIN);
            headerStyle_tan.setBorderTop(BorderStyle.THIN);
            headerStyle_tan.setBorderBottom(BorderStyle.THIN);
            headerStyle_tan.setFillForegroundColor(IndexedColors.TAN.index);
            headerStyle_tan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_tan.setFont(fontHeader);
            headerStyle_tan.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LAVENDER)
            CellStyle headerStyle_lavender = workbook.createCellStyle();
            headerStyle_lavender.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lavender.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lavender.setBorderRight(BorderStyle.THIN);
            headerStyle_lavender.setBorderLeft(BorderStyle.THIN);
            headerStyle_lavender.setBorderTop(BorderStyle.THIN);
            headerStyle_lavender.setBorderBottom(BorderStyle.THIN);
            headerStyle_lavender.setFillForegroundColor(IndexedColors.LAVENDER.index);
            headerStyle_lavender.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lavender.setFont(fontHeader);
            headerStyle_lavender.setWrapText(true); //개행
            // 엑셀 헤더 셋팅 (LEMON_CHIFFON)
            CellStyle headerStyle_lemon_chiffon = workbook.createCellStyle();
            headerStyle_lemon_chiffon.setAlignment(HorizontalAlignment.CENTER);
            headerStyle_lemon_chiffon.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle_lemon_chiffon.setBorderRight(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderLeft(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderTop(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setBorderBottom(BorderStyle.THIN);
            headerStyle_lemon_chiffon.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
            headerStyle_lemon_chiffon.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle_lemon_chiffon.setFont(fontHeader);
            headerStyle_lemon_chiffon.setWrapText(true); //개행

            //rows
            int rowCnt = 0;

            // *** Sheet-------------------------------------------------
            // Sheet 생성
            SXSSFSheet sheet = workbook.createSheet("visitor");

            SXSSFCell cell = null;
            SXSSFRow row = sheet.createRow(rowCnt++);

            // 헤더 정보 구성
            // 기본
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
            SXSSFCell mergeCell = row.createCell(0);
            mergeCell.setCellStyle(headerStyle);
            mergeCell.setCellValue("기본정보");

            // 참관객 정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,6,11));
            SXSSFCell mergeCell2 = row.createCell(6);
            mergeCell2.setCellStyle(headerStyle);
            mergeCell2.setCellValue("참관객 정보");

            // 설문항목
            sheet.addMergedRegion(new CellRangeAddress(0,0,12,15));
            SXSSFCell mergeCell3 = row.createCell(12);
            mergeCell3.setCellStyle(headerStyle);
            mergeCell3.setCellValue("설문항목");

            // 참관 구분
            sheet.addMergedRegion(new CellRangeAddress(0,0,16,25));
            SXSSFCell mergeCell4 = row.createCell(16);
            mergeCell4.setCellStyle(headerStyle);
            mergeCell4.setCellValue("참관 구분");

            // 보트쇼 방문 목적
            sheet.addMergedRegion(new CellRangeAddress(0,0,26,31));
            SXSSFCell mergeCell5 = row.createCell(26);
            mergeCell5.setCellStyle(headerStyle);
            mergeCell5.setCellValue("보트쇼 방문 목적");

            // 관심품목
            sheet.addMergedRegion(new CellRangeAddress(0,0,32,42));
            SXSSFCell mergeCell6 = row.createCell(32);
            mergeCell6.setCellStyle(headerStyle);
            mergeCell6.setCellValue("관심품목");

            // 인지경로
            sheet.addMergedRegion(new CellRangeAddress(0,0,43,54));
            SXSSFCell mergeCell7 = row.createCell(43);
            mergeCell7.setCellStyle(headerStyle);
            mergeCell7.setCellValue("인지경로");

            // 지난 전시회 참관 여부
            sheet.addMergedRegion(new CellRangeAddress(0,0,55,72));
            SXSSFCell mergeCell8 = row.createCell(55);
            mergeCell8.setCellStyle(headerStyle);
            mergeCell8.setCellValue("지난 전시회 참관 여부");

            // 동반자
            sheet.addMergedRegion(new CellRangeAddress(0,0,73,82));
            SXSSFCell mergeCell9 = row.createCell(73);
            mergeCell9.setCellStyle(headerStyle);
            mergeCell9.setCellValue("동반자");

            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(colWidths_ex[i]) + 1024));	//column width 지정
            }

            // 데이터 조회
            VisitorDetailDTO visitorDetailDTO = new VisitorDetailDTO();
            visitorDetailDTO.setJoinYear(joinYear);
            List<VisitorDetailDTO> visitorDetailList = kibsMngService.processSelectTransferVisitorDetailList(visitorDetailDTO);

            int cellCnt = 0;
            int listCount = visitorDetailList.size();

            //데이터 부분 생성
            for(VisitorDetailDTO info : visitorDetailList) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                int nCount = 0;
                String[] remark = info.getName().split("\\^");

                //줄 높이 계산
                for (String s : remark) {
                    if (s.length() > 0) {
                        nCount++;
                    }
                }

                //줄 높이 설정
                if (nCount > 1){
                    row.setHeightInPoints((nCount * sheet.getDefaultRowHeightInPoints()));
                }

                // 넘버링
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);

                // 연도
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getJoinYear());

                // 구분
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getVisitorGbn());

                // 참석여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                String joinYn = "참석확인";
                if("N".equals(info.getJoinYn())){
                    joinYn = "참석취소";
                }
                cell.setCellValue(joinYn);

                // 등록일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getInitRegiDttm());

                // 수정일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getFinalRegiDttm());

                // 참관객 정보
                // 성명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getName());

                // 전화번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getTel() != null){
                    cell.setCellValue(info.getTel());
                }else{
                    cell.setCellValue("-");
                }

                // 휴대전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPhone());

                // 이메일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getEmail() + "@" + info.getDomain());

                // 직장명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyName());

                // 동반자여부
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if("Y".equals(info.getPartnerYn())){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 설문항목
                // 성별
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getSex());

                // 지역(시/도)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getRegionSi());

                // 지역(구/군)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getRegionGu());

                // 연령대
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(info.getAgeGroup() != null){
                    cell.setCellValue(info.getAgeGroup() + "대");
                }else{
                    cell.setCellValue("");
                }

                // 참관구분
                String observationGbn = info.getObservationGbn();
                if(observationGbn == null){
                    observationGbn = "";
                }

                // 조정면허 보유자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("조정면허 보유자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트 소유자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("보트 소유자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트 구매 예정자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("보트 구매 예정자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 관련 업종 종사자
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("관련 업종 종사자")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 낚시 매니아
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("낚시 매니아")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 다이빙 매니아
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("다이빙 매니아")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 캠핑카 매니아
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("캠핑카 매니아")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 학생
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("학생")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 일반관람
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("일반관람")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(observationGbn.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 보트쇼 방문 목적
                String visitPurpose = info.getVisitPurpose();
                if(visitPurpose == null){
                    visitPurpose = "";
                }

                // 업계동향 파악 및 정보수집
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("업계동향 파악 및 정보수집")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 제품구매 및 기술도입 상담
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("제품구매 및 기술도입 상담")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기존 거래업체 방문
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("기존 거래업체 방문")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 차기 전시회 참가여부 파악
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("차기 전시회 참가여부 파악")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 일반관람
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("일반관람")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(visitPurpose.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 관심품목
                String interestItem = info.getInterestItem();
                if(interestItem == null){
                    interestItem = "";
                }

                // 요트/보트
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("요트/보트")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 워크보트
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("워크보트")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 낚시
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("낚시")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 다이빙
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("다이빙")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 카누/카약/무동력
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("카누/카약/무동력")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 워터스포츠
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("워터스포츠")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양부품
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("해양부품")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 캠핑
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("캠핑")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 친환경
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("친환경")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 해양관광
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("해양관광")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(interestItem.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 인지경로
                String recognizePath = info.getRecognizePath();
                if(recognizePath == null){
                    recognizePath = "";
                }

                // 뉴스레터
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("뉴스레터")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 옥외광고물
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("옥외광고물")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 홈페이지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("홈페이지")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 전문지
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("전문지")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 온라인 커뮤니티
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("온라인 커뮤니티")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 오프라인매장
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("오프라인매장")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 소셜 네트워크
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("소셜 네트워크")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 초청장
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("초청장")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 방송광고
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("방송광고")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // KIBS 2024 참석
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("KIBS 2024 참석")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 낚시박람회
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("낚시박람회")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 기타
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(recognizePath.contains("기타")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 지난 전시회 참관 여부
                String preObservationGbn = info.getPreObservationGbn();
                if(preObservationGbn == null) {
                    preObservationGbn = "";
                }

                // 첫 참관
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("first")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2008
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2008")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2009
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2009")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2010
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2010")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2011
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2011")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2012
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2012")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2013
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2013")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2014
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2014")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2015
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2015")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2016
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2016")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2017
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2017")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2018
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2018")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2019
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2019")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2020
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2020")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2021
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2021")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2022
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2022")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2023
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2023")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 2024
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                if(preObservationGbn.contains("2024")){
                    cell.setCellValue("O");
                }else{
                    cell.setCellValue("");
                }

                // 동반자
                for(int i=0; i<5; i++) {
                    // 성명
                    String[] partnerNameSplit = new String[5];
                    if(info.getPartnerName() != null && !"".equals(info.getPartnerName())){
                        if (info.getPartnerName().contains("^")) {
                            partnerNameSplit = info.getPartnerName().split("\\^");
                        } else {
                            partnerNameSplit[0] = info.getPartnerName();
                        }
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(partnerNameSplit, i));

                    // 나이
                    String[] partnerAgeSplit = new String[5];
                    if(info.getPartnerAge() != null && !"".equals(info.getPartnerAge())){
                        if (info.getPartnerAge().contains("^")) {
                            partnerAgeSplit = info.getPartnerAge().split("\\^");
                        } else {
                            partnerAgeSplit[0] = info.getPartnerAge();
                        }
                    }
                    cell = row.createCell(cellCnt++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(convertValue(partnerAgeSplit, i));
                }

            }

            //너비를 자동으로 다시 설정
            for (int i = 0; i < colNames_ex.length; i++) {
                sheet.trackColumnForAutoSizing(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // excel 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 엑셀 파일명 설정
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping(value = "/mng/exhibitor/pass/download.do", method = RequestMethod.GET)
    public void exhibitor_pass_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_pass_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // Workbook 생성
        try(SXSSFWorkbook workbook = new SXSSFWorkbook()){ // Excel 2007 이상

            /* 엑셀 그리기 */
            final String[] colNames_ex = {
                    "No", "연도", "아이디", "업체명(국문)", "업체명(영문)",
                    "성명(국문)", "성명(영문)", "직책(국문)", "직책(영문)", "비고"
            };

            // 헤더 사이즈
            final int[] colWidths_ex = {
                    3000, 5000, 5000, 5000, 5000,
                    5000, 5000, 5000, 5000, 5000
            };

            workbook.setCompressTempFiles(true);

            // *** Style--------------------------------------------------
            //Font
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");	//글씨체
            fontHeader.setFontHeight((short)(9 * 20));	//사이즈
            fontHeader.setBold(true);	//볼드(굵게)
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");	//글씨체
            font9.setFontHeight((short)(9 * 20));	//사이즈
            // 엑셀 헤더 셋팅 default (참가업체정보)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);
            headerStyle.setWrapText(true); //개행
            // 엑셀 바디 셋팅 default (참가업체정보)
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9);
            bodyStyle.setWrapText(true); //개행

            //rows
            int rowCnt = 0;

            // *** Sheet-------------------------------------------------
            // Sheet 생성
            SXSSFSheet sheet = workbook.createSheet("Pass");

            SXSSFCell cell = null;
            SXSSFRow row = sheet.createRow(rowCnt++);

            // 헤더 정보 구성
            // 기본
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
            SXSSFCell mergeCell = row.createCell(0);
            mergeCell.setCellStyle(headerStyle);
            mergeCell.setCellValue("전체 출입증 정보 상세");

            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(colWidths_ex[i]) + 1024));	//column width 지정
            }

            // 데이터 조회
            PassDetailDTO passDetailDTO = new PassDetailDTO();
            passDetailDTO.setTransferYear(transferYear);
            List<PassDetailDTO> passDetailList = kibsMngService.processSelectExcelPassDetailList(passDetailDTO);

            int cellCnt = 0;
            int listCount = passDetailList.size();

            //데이터 부분 생성
            for(PassDetailDTO info : passDetailList) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                int nCount = 0;
                String[] remark = info.getCompanyNameKo().split("\\^");

                //줄 높이 계산
                for (String s : remark) {
                    if (s.length() > 0) {
                        nCount++;
                    }
                }

                //줄 높이 설정
                if (nCount > 1){
                    row.setHeightInPoints((nCount * sheet.getDefaultRowHeightInPoints()));
                }

                // 넘버링
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);

                // 연도
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getTransferYear());

                // 아이디
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getUserId());

                // 회사명(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameKo());

                // 회사명(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                String companyNameEn = "-";
                if(info.getCompanyNameEn() != null && !"".equals(info.getCompanyNameEn())){
                    companyNameEn = info.getCompanyNameEn();
                }
                cell.setCellValue(companyNameEn);

                // 성명(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPassName());

                // 성명(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPassLastName() + " " + info.getPassFirstName());

                // 직책(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPassPositionKo());

                // 직책(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPassPositionEn());

                // 비고
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                String passNote = "-";
                if(info.getPassNote() != null && !"".equals(info.getPassNote())){
                    passNote = info.getPassNote();
                }
                cell.setCellValue(passNote);

            }

            //너비를 자동으로 다시 설정
            for (int i = 0; i < colNames_ex.length; i++) {
                sheet.trackColumnForAutoSizing(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // excel 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 엑셀 파일명 설정
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private String convertValue(String[] splitVal, int index){
        int length = splitVal.length;
        String returnVal = "";
        if(index < length){
            returnVal = splitVal[index] == null ? "" : splitVal[index];
        }
        return returnVal;
    }

    /*********************** excel upload ***********************/

    @RequestMapping(value = "/mng/excelUpload.do" , method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_excelUpload(MultipartHttpServletRequest request) {
        System.out.println("KibsMngController > mng_excelUpload");
        ResponseDTO responseDto = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;

        try {
//            JSONObject jsonObject = new JSONObject();

            MultipartFile file = null;
            Iterator<String> iterator = request.getFileNames();
            if(iterator.hasNext()) {
                file = request.getFile(iterator.next());
            }

            System.out.println(Objects.requireNonNull(file).getOriginalFilename());
            System.out.println(file.getName());

            List<?> list = kibsMngService.uploadExcelFile(file);
            if(list !=null) {
//                jsonObject.put("rs", "0000");
            }else {
//                jsonObject.put("rs", "9999");
            }

        } catch (Exception e) {
            System.out.println("[EXCEL UPLOAD ERROR] : " + e.getMessage());
        }

        responseDto.setResultCode(resultCode);
        responseDto.setResultMessage(resultMessage);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*********************** mail send ***********************/

    @RequestMapping(value = "/mail/send.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mail_send(@RequestBody MailRequestDTO mailRequestDTO) {
        System.out.println("KibsMngController > mail_send");
        System.out.println(mailRequestDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processMailSend(mailRequestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}