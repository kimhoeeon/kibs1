package com.mtf.kibs.controller;

import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.service.CalculationService;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.util.FileUploadUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The type Kibs page controller.
 */
@Controller
public class KibsMngController {

    @Autowired
    private CalculationService calculationService; // 1. 공통 계산 서비스 주입

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
        String fullYear = "2026";

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

        /* 전시품 수 */
        StatisticsDTO productReq = new StatisticsDTO();
        productReq.setTransferYear(fullYear);
        StatisticsDTO productStat = kibsMngService.processSelectProductCount(productReq);
        mv.addObject("productStat", productStat);

        /* 전시품 수 */
        StatisticsDTO productQtyReq = new StatisticsDTO();
        productQtyReq.setTransferYear(fullYear);
        StatisticsDTO productQtyStat = kibsMngService.processSelectProductQtyCount(productQtyReq);
        mv.addObject("productQtyStat", productQtyStat);

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
    public ModelAndView mng_exhibitor_participant_company(String nameKo) {
        System.out.println("KibsMngController > mng_exhibitor_company");
        ModelAndView mv = new ModelAndView();
        if(nameKo != null){
            mv.addObject("nameKo", nameKo);
        }
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

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_participant_company_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_participant_company_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectListExhibitorNew(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /*@RequestMapping(value = "/mng/exhibitor/participant/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_company_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_company");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !seq.isEmpty()){
            *//* 전시업체정보 *//*
            ExhibitorDTO requestDto = new ExhibitorDTO();
            requestDto.setSeq(seq);
            ExhibitorDTO info = kibsMngService.processSelectExhibitorSingle(requestDto);
            mv.addObject("info", info);

            *//* 담당자정보*//*
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(seq);
            List<ChargePersonDTO> chargeList = kibsMngService.processSelectChargeList(chargeReq);
            mv.addObject("chargeList", chargeList);

            *//* 전시정보 *//*
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(seq);
            List<DisplayDTO> displayList = kibsMngService.processSelectDisplayList(displayReq);
            mv.addObject("displayList", displayList);

            *//* 온라인정보 *//*
            OnlineDTO onlineReq = new OnlineDTO();
            onlineReq.setId(seq);
            List<OnlineDTO> onlineList = kibsMngService.processSelectOnlineList(onlineReq);
            mv.addObject("onlineList", onlineList);

            *//* 바이어정보 *//*
            BuyerDTO buyerReq = new BuyerDTO();
            buyerReq.setId(seq);
            List<BuyerDTO> buyerList = kibsMngService.processSelectBuyerList(buyerReq);
            mv.addObject("buyerList", buyerList);

            *//* 파일정보 *//*
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
    }*/

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_company_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_company_detail");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !seq.isEmpty()){
            /* 전시업체정보 */
            ExhibitorNewDTO requestDto = new ExhibitorNewDTO();
            requestDto.setSeq(seq);
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewSingle(requestDto);

            if(info != null){
                mv.addObject("info", info);

                String exhibitor_new_seq = info.getSeq();

                /* 부담당자 정보*/
                ChargeNewDTO chargeNewReq = new ChargeNewDTO();
                chargeNewReq.setExSeq(exhibitor_new_seq);
                List<ChargeNewDTO> chargeList = kibsMngService.processSelectChargeNewList(chargeNewReq);
                mv.addObject("chargeList", chargeList);

                /* 온라인정보 */
                OnlineNewDTO onlineNewReq = new OnlineNewDTO();
                onlineNewReq.setExSeq(exhibitor_new_seq);
                List<OnlineNewDTO> onlineList = kibsMngService.processSelectOnlineNewList(onlineNewReq);
                mv.addObject("onlineList", onlineList);

                /* 바이어정보 */
                BuyerNewDTO buyerNewReq = new BuyerNewDTO();
                buyerNewReq.setExSeq(exhibitor_new_seq);
                List<BuyerNewDTO> buyerList = kibsMngService.processSelectBuyerNewList(buyerNewReq);
                mv.addObject("buyerList", buyerList);

                /* 파일정보 */
                FileDTO fileReq = new FileDTO();
                fileReq.setUserId(exhibitor_new_seq);
                List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
                List<FileDTO> onlineImageFileList = new ArrayList<>();
                for (FileDTO fileInfo : fileList) {
                    String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                    switch (fileNote) {
                        case "companyLicense":
                            mv.addObject("companyLicenseFile", fileInfo);
                            break;
                        case "logo":
                            mv.addObject("logoFile", fileInfo);
                            break;
                        case "onlineImage":
                            onlineImageFileList.add(fileInfo);
                            break;
                        default:
                            break;
                    }
                }
                mv.addObject("onlineImageFileList", onlineImageFileList);
            }

        }
        mv.setViewName("/mng/exhibitor/participant/company/detail");
        return mv;
    }

    // 특정 참가업체의 입금 내역 전체를 불러오는 API
    @GetMapping("/mng/deposits/{exhibitorSeq}")
    @ResponseBody
    public List<DepositHistoryDTO> selectDepositHistoryList(@PathVariable String exhibitorSeq) {
        return kibsMngService.selectDepositHistoryList(exhibitorSeq);
    }

    // 새로운 입금 내역을 저장하는 API
    @PostMapping("/mng/deposits")
    @ResponseBody
    public Map<String, Object> addDepositHistory(@RequestBody DepositHistoryDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            DepositHistoryDTO savedDto = kibsMngService.insertDepositHistory(dto);

            if (savedDto != null) {
                resultMap.put("resultCode", "0");
                resultMap.put("resultMsg", "추가되었습니다.");
                resultMap.put("data", savedDto); // 저장된 데이터를 'data' 키에 담아 반환
            } else {
                resultMap.put("resultCode", "-1");
                resultMap.put("resultMsg", "데이터 추가에 실패했습니다.");
            }
        } catch (Exception e) {
        }
        return resultMap;
    }

    // 기존 입금 내역을 수정하는 API
    @PostMapping("/mng/deposits/{depositSeq}")
    @ResponseBody
    public Map<String, Object> updateDepositHistory(@RequestBody DepositHistoryDTO dto, @PathVariable("depositSeq") int depositSeq) {
        dto.setDepositSeq(depositSeq);
        return kibsMngService.updateDepositHistory(dto);
    }

    // 입금 내역을 삭제하는 API
    @PostMapping("/mng/deposits/{depositSeq}/delete")
    @ResponseBody
    public Map<String, Object> deleteDepositHistory(@PathVariable int depositSeq) {
        return kibsMngService.deleteDepositHistory(depositSeq);
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

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_participant_company_delete(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_participant_company_delete");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processDeleteExhibitorNew(exhibitorNewDTO);

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
    public ModelAndView mng_exhibitor_visitor(String nameKo) {
        System.out.println("KibsMngController > mng_exhibitor_visitor");
        ModelAndView mv = new ModelAndView();
        if(nameKo != null){
            mv.addObject("nameKo", nameKo);
        }
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
        System.out.println("KibsMngController > mng_exhibitor_visitor_update");
        ResponseDTO response = kibsMngService.processUpdateVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_visitor_delete(@RequestBody VisitorDTO visitorDTO) {
        System.out.println("KibsMngController > mng_exhibitor_visitor_delete");
        ResponseDTO response = kibsMngService.processDeleteVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/visitor/deletePartner.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_visitor_deletePartner(@RequestBody PartnerDTO partnerDTO) {
        System.out.println("KibsMngController > mng_exhibitor_visitor_deletePartner");
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

    @PostMapping("/mng/exhibitor/loginAs")
    @ResponseBody
    public Map<String, Object> loginAsExhibitor(@RequestBody Map<String, String> payload, HttpSession session) {
        String exhibitorSeq = payload.get("seq");
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. seq로 업체의 ID를 조회합니다.
            ExhibitorNewDTO exhibitorInfo = kibsMngService.getExhibitorInfoForLogin(exhibitorSeq);

            if (exhibitorInfo != null) {
                // 2. 현재 관리자 세션 정보를 잠시 백업합니다.
                session.setAttribute("admin_origin_id", session.getAttribute("id"));
                session.setAttribute("admin_origin_status", session.getAttribute("status"));

                // 3. 업체 정보로 세션을 '스위칭'합니다.
                session.setAttribute("id", exhibitorInfo.getId());
                session.setAttribute("status", "logon"); // 업체로 로그인한 상태임을 명시

                resultMap.put("resultCode", "0");
                resultMap.put("resultMsg", "업체로 로그인되었습니다.");
            } else {
                throw new Exception("업체 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/mng/exhibitor/logoutAs")
    @ResponseBody
    public Map<String, Object> logoutAsExhibitor(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1. 백업해 둔 관리자 ID가 세션에 있는지 확인합니다.
            String adminOriginId = (String) session.getAttribute("admin_origin_id");

            if (adminOriginId != null) {
                // 2. 원래 관리자 ID와 상태로 세션을 복구합니다.
                session.setAttribute("id", adminOriginId);
                session.setAttribute("status", "logon");

                // 3. 사용했던 백업 정보는 세션에서 삭제합니다.
                session.removeAttribute("admin_origin_id");
                session.removeAttribute("admin_origin_status");

                resultMap.put("resultCode", "0");
                resultMap.put("resultMsg", "관리자 세션으로 복구되었습니다.");
            } else {
                throw new Exception("복구할 관리자 세션 정보가 없습니다.");
            }
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", e.getMessage());
        }
        return resultMap;
    }

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

        if(seq != null && !seq.isEmpty()){
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
        System.out.println("KibsMngController > mng_exhibitor_transfer_visitor_update");
        ResponseDTO response = kibsMngService.processUpdateTransferVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //***************************************************************************
    // exhibitor/application Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/exhibitorNew/application/booth.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/booth");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_booth_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewBoothList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewInvoiceDetail(seq);
            mv.addObject("info", info);

            // 3. 해당 참가업체의 '전시부스 인보이스' 목록 조회
            List<InvoiceBoothDTO> boothInvoiceList  = kibsMngService.getInvoiceBoothList(seq);
            mv.addObject("boothInvoiceList", boothInvoiceList);

            // 4. 유틸리티 인보이스 목록 조회 로직 추가
            //List<InvoiceUtilityDTO> utilityInvoiceList = kibsMngService.getInvoiceUtilityList(seq); // 이 Service 메소드를 새로 만들어야 합니다.
            //mv.addObject("utilityInvoiceList", utilityInvoiceList);
        }
        mv.setViewName("/mng/exhibitorNew/application/booth/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/invoice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility_invoice_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            InvoiceUtilityDTO invoiceInfo = kibsMngService.processSelectInvoiceUtilitySingle(seq);
            mv.addObject("invoiceInfo", invoiceInfo);

            ExhibitorNewDTO exhibitorNewDTO = new ExhibitorNewDTO();
            exhibitorNewDTO.setSeq(invoiceInfo.getExhibitorSeq());
            ExhibitorNewDTO exhibitorBaseInfo = kibsMngService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorBaseInfo", exhibitorBaseInfo);
        }
        mv.setViewName("/mng/exhibitorNew/application/utility/invoice");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBooth(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/invoice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth_invoice_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            InvoiceBoothDTO invoiceInfo = kibsMngService.processSelectInvoiceBoothSingle(seq);
            mv.addObject("invoiceInfo", invoiceInfo);

            ExhibitorNewDTO exhibitorNewDTO = new ExhibitorNewDTO();
            exhibitorNewDTO.setSeq(invoiceInfo.getExhibitorSeq());
            ExhibitorNewDTO exhibitorNewInfo = kibsMngService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorNewInfo", exhibitorNewInfo);
        }
        mv.setViewName("/mng/exhibitorNew/application/booth/invoice");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/invoice/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_delete(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewBoothInvoice(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/invoice/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_delete(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewUtilityInvoice(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/invoice/filePath/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_filePath_update(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_filePath_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBoothInvoiceFilePath(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/invoice/filePath/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_filePath_update(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_filePath_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtilityInvoiceFilePath(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/booth/invoice/mail/result/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_mail_result_update(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_mail_result_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBoothInvoiceSendResult(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/mng/invoices/history")
    @ResponseBody
    public List<InvoiceSendHistoryDTO> getInvoiceHistory(@RequestParam("invoiceSeq") int invoiceSeq, @RequestParam("invoiceType") String invoiceType) {
        return kibsMngService.getInvoiceSendHistory(invoiceSeq, invoiceType);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/invoice/mail/result/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_mail_result_update(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_mail_result_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtilityInvoiceSendResult(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/getExhibitorNewInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ExhibitorNewDTO> mng_getExhibitorNewInfo(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_getExhibitorNewInfo");
        ExhibitorNewDTO result = kibsMngService.getExhibitorNewInfo(exhibitorNewDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/maritime.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_maritime() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_maritime");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/maritime");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/maritime/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_maritime_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_maritime_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewMaritimeList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/maritime/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_maritime_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_maritime_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO exhibitorInfo = kibsMngService.processSelectExhibitorNewMaritimeSingle(seq);
            mv.addObject("info", exhibitorInfo);
        }
        mv.setViewName("/mng/exhibitorNew/application/maritime/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/maritime/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_maritime_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_maritime_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewMaritime(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/sign.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_sign() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_sign");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/sign");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/sign/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_sign_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_sign_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewSignList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/sign/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_sign_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_sign_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO exhibitorInfo = kibsMngService.processSelectExhibitorNewSignSingle(seq);
            mv.addObject("info", exhibitorInfo);
        }
        mv.setViewName("/mng/exhibitorNew/application/sign/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/sign/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_sign_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_sign_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewSign(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/utility");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_utility_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewUtilityList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewInvoiceDetail(seq);
            mv.addObject("info", info);

            // 3. 해당 참가업체의 '전시부스 인보이스' 목록 조회
            //List<InvoiceBoothDTO> boothInvoiceList  = kibsMngService.getInvoiceBoothList(seq);
            //mv.addObject("boothInvoiceList", boothInvoiceList);

            // 4. 유틸리티 인보이스 목록 조회 로직 추가
            List<InvoiceUtilityDTO> utilityInvoiceList = kibsMngService.getInvoiceUtilityList(seq); // 이 Service 메소드를 새로 만들어야 합니다.
            mv.addObject("utilityInvoiceList", utilityInvoiceList);
        }
        mv.setViewName("/mng/exhibitorNew/application/utility/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/utility/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_utility_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtility(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/invoice/mail/open/update.do", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void mng_exhibitorNew_application_invoice_mail_open_update(@RequestParam("hseq") int historySeq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_invoice_mail_open_update");
        kibsMngService.updateInvoiceHistoryStatusToOpen(historySeq);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/pass.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_pass() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_pass");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/pass");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/pass/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngPassDTO>> mng_exhibitorNew_application_pass_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_pass_selectList");
        //System.out.println(searchDTO.toString());

        List<MngPassDTO> responseList = kibsMngService.processSelectExhibitorNewPassList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/pass/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_pass_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_pass_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngPassDTO> passList = kibsMngService.processSelectExhibitorNewPassDetailList(seq);
            mv.addObject("passList", passList);
            mv.addObject("exSeq", seq);
        }
        mv.setViewName("/mng/exhibitorNew/application/pass/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/pass/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_pass_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_pass_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewPass(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/pass/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_pass_delete(@RequestBody PassNewDTO passNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_pass_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewPass(passNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/buyer.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_buyer() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_buyer");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/buyer");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/buyer/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngBuyerDTO>> mng_exhibitorNew_application_buyer_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_selectList");
        //System.out.println(searchDTO.toString());

        List<MngBuyerDTO> responseList = kibsMngService.processSelectExhibitorNewBuyerList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/buyer/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_buyer_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngBuyerDTO> buyerList = kibsMngService.processSelectExhibitorNewBuyerDetailList(seq);
            mv.addObject("buyerList", buyerList);
            mv.addObject("exSeq", seq);
        }
        mv.setViewName("/mng/exhibitorNew/application/buyer/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/buyer/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_buyer_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBuyer(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/buyer/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_buyer_delete(@RequestBody BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewBuyer(buyerNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/gift.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_gift() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_gift");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/gift");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/gift/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngGiftDTO>> mng_exhibitorNew_application_gift_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_gift_selectList");
        //System.out.println(searchDTO.toString());

        List<MngGiftDTO> responseList = kibsMngService.processSelectExhibitorNewGiftList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/gift/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_gift_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_gift_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngGiftDTO> giftList = kibsMngService.processSelectExhibitorNewGiftDetailList(seq);
            mv.addObject("giftList", giftList);
            mv.addObject("exSeq", seq);

            // 파일정보
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            List<FileDTO> giftPhotoFileList = new ArrayList<>();
            List<FileDTO> giftCompanyLogoList = new ArrayList<>();
            for (FileDTO fileInfo : fileList) {
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                switch (fileNote) {
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
        mv.setViewName("/mng/exhibitorNew/application/gift/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/gift/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_gift_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_gift_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewGift(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/gift/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_gift_delete(@RequestBody GiftNewDTO giftNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_gift_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewGift(giftNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/online.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_online() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_online");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/online");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/online/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_online_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_online_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectMngOnlineNewList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/online/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_online_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitor_application_online_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            /* 전시업체정보 */
            ExhibitorNewDTO requestDto = new ExhibitorNewDTO();
            requestDto.setSeq(seq);
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewSingle(requestDto);
            mv.addObject("info", info);

            /* 온라인정보 */
            OnlineNewDTO onlineReq = new OnlineNewDTO();
            onlineReq.setExSeq(seq);
            List<OnlineNewDTO> onlineList = kibsMngService.processSelectOnlineNewList(onlineReq);
            mv.addObject("onlineList", onlineList);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(seq);
            List<FileDTO> fileList = kibsMngService.processSelectFileList(fileReq);
            List<FileDTO> onlineImageFileList = new ArrayList<>();
            for (FileDTO fileInfo : fileList) {
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                if ("onlineImage".equals(fileNote)) {
                    onlineImageFileList.add(fileInfo);
                }
            }
            mv.addObject("onlineImageFileList", onlineImageFileList);
        }
        mv.setViewName("/mng/exhibitorNew/application/online/detail");
        return mv;
    }

    /**
     * [AJAX 용] 관리자 - 기업 뱃지 정보 업데이트
     * @param payload (seq, companyBadge)
     * @return ResponseDTO
     */
    @PostMapping("/mng/exhibitorNew/updateCompanyBadge.do")
    @ResponseBody
    public ResponseDTO updateCompanyBadge(@RequestBody Map<String, String> payload) {
        System.out.println("KibsMngController > updateCompanyBadge");
        ResponseDTO res = new ResponseDTO();
        try {
            String seq = payload.get("seq");
            String companyBadge = payload.get("companyBadge");

            ExhibitorNewDTO dto = new ExhibitorNewDTO();
            dto.setSeq(seq);
            dto.setCompanyBadge(companyBadge); // DTO에 companyBadge 필드 필요

            int result = kibsMngService.updateCompanyBadge(dto); // 서비스 호출

            if (result > 0) {
                res.setResultCode("0");
                res.setResultMessage("저장되었습니다.");
            } else {
                res.setResultCode("1");
                res.setResultMessage("저장 실패 (업데이트된 행 없음)");
            }
        } catch (Exception e) {
            res.setResultCode("99");
            res.setResultMessage(e.getMessage());
        }
        return res;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/online/updateViewYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_online_updateViewYn(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_online_updateViewYn");
        //System.out.println(exhibitorNewDTO.getSeq());

        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewOnlineViewYn(exhibitorNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/product.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_product() {
        System.out.println("KibsMngController > mng_exhibitorNew_application_product");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNew/application/product");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/product/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngProductDTO>> mng_exhibitorNew_application_product_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_product_selectList");
        //System.out.println(searchDTO.toString());

        List<MngProductDTO> responseList = kibsMngService.processSelectMngProductNewList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/application/product/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_product_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_application_product_detail");
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            /* 전시업체정보 */
            ExhibitorNewDTO requestDto = new ExhibitorNewDTO();
            requestDto.setSeq(seq);
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewSingle(requestDto);
            mv.addObject("info", info);

            /* 전시품정보 */
            ProductNewDTO productReq = new ProductNewDTO();
            productReq.setExSeq(seq);
            List<ProductNewDTO> productList = kibsMngService.processSelectProductNewList(productReq);
            mv.addObject("productList", productList);

        }
        mv.setViewName("/mng/exhibitorNew/application/product/detail");
        return mv;
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
        System.out.println("KibsMngController > mng_exhibitor_application_booth_update");
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
        System.out.println("KibsMngController > mng_exhibitor_application_sign_update");
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
        System.out.println("KibsMngController > mng_exhibitor_application_utility_update");
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
        System.out.println("KibsMngController > mng_exhibitor_application_pass_update");
        ResponseDTO response = kibsMngService.processUpdatePass(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/pass/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_pass_delete(@RequestBody PassDTO passDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_pass_delete");
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
        System.out.println("KibsMngController > mng_exhibitor_application_buyer_update");
        ResponseDTO response = kibsMngService.processUpdateBuyer(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/buyer/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_buyer_delete(@RequestBody BuyerDTO buyerDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_buyer_delete");
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
        System.out.println("KibsMngController > mng_exhibitor_application_gift_update");
        ResponseDTO response = kibsMngService.processUpdateGift(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/application/gift/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_application_gift_delete(@RequestBody GiftDTO giftDTO) {
        System.out.println("KibsMngController > mng_exhibitor_application_gift_delete");
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

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/updateExhibitorNewApprovalStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_participant_company_updateExhibitorNewApprovalStatus(@RequestBody List<ExhibitorNewDTO> exhibitorList) {
        System.out.println("KibsMngController > mng_exhibitorNew_participant_company_updateExhibitorNewApprovalStatus");

        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewApprovalStatus(exhibitorList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitor/participant/company/updatePrcYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitor_participant_company_updatePrcYn(@RequestBody List<ExhibitorDTO> exhibitorList) {
        System.out.println("KibsMngController > mng_exhibitor_participant_company_updatePrcYn");

        ResponseDTO responseDTO = kibsMngService.processUpdatePrcYn(exhibitorList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/updateExhibitorNewPrcYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_participant_company_updateExhibitorNewPrcYn(@RequestBody List<ExhibitorNewDTO> exhibitorList) {
        System.out.println("KibsMngController > mng_exhibitorNew_participant_company_updateExhibitorNewPrcYn");

        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewPrcYn(exhibitorList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getExhibitorNewEmailList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<String>> getExhibitorNewEmailList(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsMngController > getExhibitorNewEmailList");
        List<String> result = kibsMngService.getExhibitorNewEmailList(exhibitorNewDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
        if(fileIdList != null && !fileIdList.isEmpty()){
            String[] fileIdSplit = fileIdList.split(",");
            for (String s : fileIdSplit) {
                if (!"".equals(s)) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(s);
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
    public String mng_center_board_brochure() {
        System.out.println("KibsMngController > mng_center_board_brochure");
        return "redirect:/mng/center/board/brochure/detail.do";
    }

    /**
     * [신규] 브로슈어 관리 페이지 로드
     */
    @GetMapping("/mng/center/board/brochure/detail.do")
    public String brochureManagementPage(Model model) {
        String currentYear = "2026"; // 예시 연도 (세션 등에서 가져오도록 수정 가능)

        // DB에서 현재 연도의 브로슈어 정보를 조회
        BrochureDTO brochureInfo = kibsMngService.getBrochureInfo(currentYear);

        if (brochureInfo == null) {
            brochureInfo = new BrochureDTO(); // 정보가 없으면 빈 객체 생성
        }

        model.addAttribute("brochureInfo", brochureInfo);
        return "/mng/center/board/brochure/detail"; // JSP 파일 경로
    }

    /**
     * [수정] 브로슈어 정보 저장 (경로 수정 및 FileUploadUtil 적용)
     */
    @PostMapping("/mng/center/brochure/update.do")
    public String updateBrochure(
            @ModelAttribute BrochureDTO formData,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        final String PHYSICAL_PATH_BASE = "/usr/local/tomcat/webapps";
        final String WEB_PATH_BASE = "/upload/center/board/brochure/";
        final String PHYSICAL_SAVE_PATH = PHYSICAL_PATH_BASE + WEB_PATH_BASE;

        try {
            // 1. 메인 국문 파일 처리
            String mainKoPath = formData.getMainKoFile_existing();
            String mainKoOrigName = formData.getMainKoOriginalName_existing(); // [추가]
            if (formData.getMainKoFile() != null && !formData.getMainKoFile().isEmpty()) {
                FileUploadUtil.deleteFile(PHYSICAL_PATH_BASE + mainKoPath);
                String newFileName = FileUploadUtil.saveFile(formData.getMainKoFile(), PHYSICAL_SAVE_PATH);
                mainKoPath = WEB_PATH_BASE + newFileName;
                mainKoOrigName = formData.getMainKoFile().getOriginalFilename(); // [추가] 원본 파일명 저장
            }
            formData.setMainKoPath(mainKoPath);
            formData.setMainKoOriginalName(mainKoOrigName); // [추가]


            // 2. 메인 영문 파일 처리
            String mainEnPath = formData.getMainEnFile_existing();
            String mainEnOrigName = formData.getMainEnOriginalName_existing(); // [추가]
            if (formData.getMainEnFile() != null && !formData.getMainEnFile().isEmpty()) {
                FileUploadUtil.deleteFile(PHYSICAL_PATH_BASE + mainEnPath);
                String newFileName = FileUploadUtil.saveFile(formData.getMainEnFile(), PHYSICAL_SAVE_PATH);
                mainEnPath = WEB_PATH_BASE + newFileName;
                mainEnOrigName = formData.getMainEnFile().getOriginalFilename(); // [추가]
            }
            formData.setMainEnPath(mainEnPath);
            formData.setMainEnOriginalName(mainEnOrigName); // [추가]


            // 3. KISS 파일 처리
            String kissPath = formData.getKissFile_existing();
            String kissOrigName = formData.getKissOriginalName_existing(); // [추가]
            if (formData.getKissFile() != null && !formData.getKissFile().isEmpty()) {
                FileUploadUtil.deleteFile(PHYSICAL_PATH_BASE + kissPath);
                String newFileName = FileUploadUtil.saveFile(formData.getKissFile(), PHYSICAL_SAVE_PATH);
                kissPath = WEB_PATH_BASE + newFileName;
                kissOrigName = formData.getKissFile().getOriginalFilename(); // [추가]
            }
            formData.setKissPath(kissPath);
            formData.setKissOriginalName(kissOrigName); // [추가]


            // 4. KMTS 파일 처리
            String kmtsPath = formData.getKmtsFile_existing();
            String kmtsOrigName = formData.getKmtsOriginalName_existing(); // [추가]
            if (formData.getKmtsFile() != null && !formData.getKmtsFile().isEmpty()) {
                FileUploadUtil.deleteFile(PHYSICAL_PATH_BASE + kmtsPath);
                String newFileName = FileUploadUtil.saveFile(formData.getKmtsFile(), PHYSICAL_SAVE_PATH);
                kmtsPath = WEB_PATH_BASE + newFileName;
                kmtsOrigName = formData.getKmtsFile().getOriginalFilename(); // [추가]
            }
            formData.setKmtsPath(kmtsPath);
            formData.setKmtsOriginalName(kmtsOrigName); // [추가]


            // 5. DB에 최종 정보 업데이트
            formData.setTransferYear("2026");
            kibsMngService.saveOrUpdateBrochureInfo(formData);

            redirectAttributes.addFlashAttribute("message", "브로슈어 정보가 성공적으로 저장되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "저장 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/mng/center/board/brochure/detail.do";
    }

    @RequestMapping(value = "/mng/center/board/contest.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_contest() {
        System.out.println("KibsMngController > mng_center_board_contest");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/center/board/contest");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/contest/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ContestDTO>> mng_center_board_contest_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsMngController > mng_center_board_contest_selectList");
        //System.out.println(searchDTO.toString());

        List<ContestDTO> responseList = kibsMngService.processSelectContestList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/contest/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_center_board_contest_detail(String seq) {
        System.out.println("KibsMngController > mng_center_board_contest_detail");
        ModelAndView mv = new ModelAndView();
        //seq == notice table id
        if(seq != null && !seq.isEmpty()){
            ContestDTO requestDto = new ContestDTO();
            requestDto.setId(seq);
            ContestDTO info = kibsMngService.processSelectContestSingle(requestDto);
            if(info != null){
                mv.addObject("info",info);

                FileDTO fileDTO = new FileDTO();
                fileDTO.setUserId(seq);
                List<FileDTO> fileList = kibsMngService.processSelectFileList(fileDTO);
                if(fileList != null && !fileList.isEmpty()){
                    mv.addObject("fileList", fileList);
                }
            }
        }
        mv.setViewName("/mng/center/board/contest/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/center/board/contest/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ContestDTO> mng_center_board_contest_selectSingle(@RequestBody ContestDTO contestDTO) {
        System.out.println("KibsMngController > mng_center_board_contest_selectSingle");
        //System.out.println(newsletterDTO.toString());

        ContestDTO response = kibsMngService.processSelectContestSingle(contestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/contest/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_contest_delete(@RequestBody ContestDTO contestDTO) {
        System.out.println("KibsMngController > mng_center_board_contest_delete");

        ResponseDTO responseDTO = kibsMngService.processDeleteContest(contestDTO);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setUserId(contestDTO.getId());
        commService.processUpdateFileDeleteUseN(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/contest/modifySave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_contest_modifySave(@RequestBody ContestDTO contestDTO) {
        System.out.println("KibsMngController > mng_center_board_contest_modifySave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processUpdateContest(contestDTO);

        String fileIdList = contestDTO.getFileIdList();
        if(fileIdList != null && !fileIdList.isEmpty()){
            String[] fileIdSplit = fileIdList.split(",");
            for (String s : fileIdSplit) {
                if (!"".equals(s)) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(s);
                    fileDTO.setUserId(contestDTO.getId());
                    ResponseDTO fileResponse = kibsMngService.processUpdateFileUserId(fileDTO);
                }
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/center/board/contest/insertSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_center_board_contest_insertSave(@RequestBody ContestDTO contestDTO) {
        System.out.println("KibsMngController > mng_center_board_contest_insertSave");
        //System.out.println(noticeDTO.toString());

        ResponseDTO responseDTO = kibsMngService.processInsertContest(contestDTO);

        String fileIdList = contestDTO.getFileIdList();
        if(fileIdList != null && !fileIdList.isEmpty()){
            String[] fileIdSplit = fileIdList.split(",");
            for (String s : fileIdSplit) {
                if (!"".equals(s)) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(s);
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
        if(fileIdList != null && !fileIdList.isEmpty()){
            String[] fileIdSplit = fileIdList.split(",");
            for (String s : fileIdSplit) {
                if (!"".equals(s)) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(s);
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
        if(fileIdList != null && !fileIdList.isEmpty()){
            String[] fileIdSplit = fileIdList.split(",");
            for (String s : fileIdSplit) {
                if (!"".equals(s)) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(s);
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

            if(resInfo.getFileIdList() != null && !resInfo.getFileIdList().isEmpty()){
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
    public ResponseEntity<JSONObject> file_upload(HttpServletRequest uploadFile) throws UnsupportedEncodingException {
        System.out.println("KibsMngController > file_upload");
        String gbn = uploadFile.getParameter("gbn");
        uploadFile.setCharacterEncoding("UTF-8");
        JSONObject response = new JSONObject();

        int size = 1024 * 1024 * 10; // 10M
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
            Collection<Part> parts = uploadFile.getParts();
            for (Part part : parts) {

                //편의 메서드
                //System.out.println("submittedFiledName : " + part.getSubmittedFileName());
                //System.out.println("size : " + part.getSize());

                //파일 저장하기
                if (StringUtils.hasText(part.getSubmittedFileName())) {
                    file = part.getSubmittedFileName();
                    oriFile = part.getSubmittedFileName();

                    file = appendSuffixName(uploadPath.getPath() , file, 1);
                    part.write(uploadPath.getPath() + File.separator + file);
                }
            }

            response.put("uploadPath", uploadPath.getPath());
            response.put("fileName", file);
            //response.put("fileNameOrigin", oriFile);

            System.out.println("[file name] " + file + " / " + "[ori file name] " + oriFile);
            System.out.println("[full file path] : " + uploadPath.getPath() + File.separator + file);

        } catch (Exception e) {
            System.out.println("[upload file save error] : " + e.getMessage());
        }

        return new ResponseEntity(response, HttpStatus.OK);
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

    @RequestMapping(value = "/file/download.do", method = RequestMethod.POST)
    public void board_downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("KibsMngController > board_downloadFile");
        // 1. 요청 파라미터 가져오기
        String path = request.getParameter("path");
        String fileName = request.getParameter("fileName");

        if (path == null || fileName == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required parameters are missing.");
            return;
        }
        path = path.replaceAll("\\\\", "/");

        // 2. 파일 저장소 경로 결정 (기존 로직 유지)
        String fileRepoPath;
        if ("mail".equals(path)) {
            fileRepoPath = "/usr/local/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + path;
        } else {
            fileRepoPath = "/usr/local/tomcat/webapps/upload/" + path;
        }

        // 3. 다운로드할 파일 객체 생성 및 유효성 검사
        File file = new File(fileRepoPath, fileName);
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }

        // 4. HTTP 응답 헤더 설정 (가장 중요한 부분)
        // 4-1. Content-Type (MIME 타입) 동적 설정
        String mimeType = request.getServletContext().getMimeType(file.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // 타입을 알 수 없는 경우를 위한 기본값
        }
        response.setContentType(mimeType);

        // 4-2. Content-Length 설정 (모바일 깨짐 방지 핵심)
        response.setContentLengthLong(file.length());

        // 4-3. Content-Disposition 설정 (다운로드 및 파일명 인코딩)
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
        response.setHeader("Cache-Control", "no-cache");

        // 5. try-with-resources를 이용한 안전한 파일 스트리밍
        try (InputStream inputStream = new FileInputStream(file)) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
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
                    "No", "승인여부", "입금여부", "사업자등록번호", "회사명(국문)",
                    "회사명(영문)", "본사 주소", "본사 상세주소", "공장 주소", "공장 상세 주소",
                    "대표자", "전화", "홈페이지", "FAX", "산업 분류",
                    "산업 분류 기타", "임직원수", "회사소개영상", "회사소개(국문)", "회사소개(영문)",
                    "KIBS 참가목적(국문)", "KIBS 참가목적(영문)", "신제품출품 사항 소개(국문)", "신제품출품 사항 소개(영문)", "프로모션 정보",
                    "성명", "직위", "부서", "전화번호", "휴대전화",
                    "이메일", "제품 분류(품목)", "제품명", "수량", "제조사(브랜드)",
                    "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재",
                    "연식"
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
            mergeCell2.setCellValue("대표담당자정보");

            // 전시정보
            sheet.addMergedRegion(new CellRangeAddress(0,0,31,40));
            XSSFCell mergeCell3 = row.createCell(31);
            mergeCell3.setCellStyle(headerStyle_di);
            mergeCell3.setCellValue("전시품정보");

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
                // 사업자등록번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyLicenseNum());
                // 회사명(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameKo());
                // 회사명(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyNameEn());
                // 본사 주소
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyAddress());
                // 본사 상세주소
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
                // 회사소개영상
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getCompanyIntroVideo());
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
                // 신제품출품 사항 소개(국문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getNewItemIntroKo());
                // 신제품출품 사항 소개(영문)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getNewItemIntroEn());
                // 프로모션 정보
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(info.getPromotionPlan());
                // 담당자명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getName());
                // 담당자직위
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getPosition());
                // 담당자부서
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getDepart());
                // 담당자전화번호
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getTel());
                // 담당자휴대전화
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getPhone());
                // 담당자이메일
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_ch);
                cell.setCellValue(info.getEmail());
                // 제품분류(품목)
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductOptionBig().replaceAll("\\^", "\n") + "/" + info.getProductOptionSmall().replaceAll("\\^", "\n"));
                // 제품명
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductNameKo().replaceAll("\\^","\n"));
                // 수량
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductQty().replaceAll("\\^","\n"));
                // 브랜드
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductBrand().replaceAll("\\^","\n"));
                // 길이
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductLength().replaceAll("\\^","\n"));
                // 너비
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductWidth().replaceAll("\\^","\n"));
                // 높이
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductHeight().replaceAll("\\^","\n"));
                // 중량
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductWeight().replaceAll("\\^","\n"));
                // 소재
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductMaterial().replaceAll("\\^","\n"));
                // 연식
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle_di);
                cell.setCellValue(info.getProductYear().replaceAll("\\^","\n"));
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

        // XSSFWorkbook 대신 SXSSFWorkbook 사용
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            // 모든 컬럼명이 포함된 헤더 배열
            final String[] colNames_ex = {
                    /* 업체정보 (30개) */ "No", "참가상태", "승인구분", "부스번호", "아이디", "등록일", "최종수정일", "참가비수납여부", "BP번호", "컨택내역-작성자", "컨택내역-날짜", "컨택내역-내용", "참고사항-작성자", "참고사항-날짜", "참고사항-내용", "사업자등록번호", "회사명(국문)", "회사명(영문)", "본사 주소", "본사 상세주소", "공장 주소", "공장 상세 주소", "대표자", "전화", "홈페이지", "Fax", "산업 분류", "산업 분류 기타", "기참가연도", "회원사 여부",
                    /* 대표담당자 (6개) */ "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 담당자 1~3 (18개) */ "성명", "직위", "부서", "전화번호", "휴대전화", "이메일", "성명", "직위", "부서", "전화번호", "휴대전화", "이메일", "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    /* 업체소개 (8개) */ "회사소개영상", "회사소개(국문)", "회사소개(영문)", "KIBS참가목적(국문)", "KIBS참가목적(영문)", "신제품출품 사항 소개(국문)", "신제품출품 사항 소개(영문)", "프로모션 정보",
                    /* 참가분야 (10개) */ "참가행사", "보트&요트", "무동력보트", "워크보트", "해양부품&장비", "안전&마리나", "해양관광", "해양레저", "수중레저", "서핑",
                    /* 해상전시회 (2개) */ "참가여부", "참가구분",
                    /* 전시품 1~20 (200개) */ "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식",
                    /* 기업뱃지 (4) */ "보트쇼 3회 이상 참가", "올해의 제품상 수상", "보트쇼와 제작한 영상", "제품 등록 우수",
                    /* 온라인 제품 1~30 (330) */ "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식", "제품분류(품목)", "제품명", "제품설명(국문)", "제품설명(영문)", "제품영상", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식",
                    /* 신청내역 (31) */ "수출상담회 참가희망여부", "등록비", "독립부스", "조립부스", "온라인부스", "총 부스 수", "1차 조기신청", "2차 조기신청", "첫 참가(10부스 미만)", "첫 참가(10부스 이상)", "재참가", "규모(10+)", "규모(20+)", "규모(30+)", "규모(40+)", "규모(50+)", "규모(100+)", "협회할인", "협회 발전기금", "특할1(제품상)", "특할1 비고", "특할2 사유", "특할2 금액", "특할2 비고", "특할3 사유", "특할3 금액", "특할3 비고", "소계(공급가액)", "부가세", "총계"
            };

            final int[] colWidths_ex = new int[colNames_ex.length];
            Arrays.fill(colWidths_ex, 5000);
            colWidths_ex[0] = 3000;

            Font fontHeader = workbook.createFont(); fontHeader.setFontName("맑은 고딕"); fontHeader.setFontHeightInPoints((short) 9); fontHeader.setBold(true);
            Font font9 = workbook.createFont(); font9.setFontName("맑은 고딕"); font9.setFontHeightInPoints((short) 9);
            CellStyle headerStyle = workbook.createCellStyle(); headerStyle.setAlignment(HorizontalAlignment.CENTER); headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); headerStyle.setBorderRight(BorderStyle.THIN); headerStyle.setBorderLeft(BorderStyle.THIN); headerStyle.setBorderTop(BorderStyle.THIN); headerStyle.setBorderBottom(BorderStyle.THIN); headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); headerStyle.setFont(fontHeader); headerStyle.setWrapText(true);
            CellStyle bodyStyle = workbook.createCellStyle(); bodyStyle.setAlignment(HorizontalAlignment.CENTER); bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER); bodyStyle.setBorderRight(BorderStyle.THIN); bodyStyle.setBorderLeft(BorderStyle.THIN); bodyStyle.setBorderTop(BorderStyle.THIN); bodyStyle.setBorderBottom(BorderStyle.THIN); bodyStyle.setFont(font9); bodyStyle.setWrapText(true);

            SXSSFSheet sheet = workbook.createSheet("참가업체");
            sheet.trackAllColumnsForAutoSizing();
            int rowCnt = 0;

            Row headerRow = sheet.createRow(rowCnt++);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 29));   headerRow.createCell(0).setCellValue("참가업체정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 30, 35));  headerRow.createCell(30).setCellValue("대표 담당자");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 36, 53));  headerRow.createCell(36).setCellValue("기타 담당자 정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 54, 61));  headerRow.createCell(54).setCellValue("업체정보(소개)");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 62, 71));  headerRow.createCell(62).setCellValue("참가분야");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 72, 73));  headerRow.createCell(72).setCellValue("해상전시회");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 74, 273)); headerRow.createCell(74).setCellValue("전시품 정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 274, 277)); headerRow.createCell(274).setCellValue("기업 뱃지");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 278, 607)); headerRow.createCell(278).setCellValue("온라인 제품 정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 608, 637)); headerRow.createCell(608).setCellValue("신청내역");

            for(int i=0; i < colNames_ex.length; i++){
                if(headerRow.getCell(i) == null) headerRow.createCell(i);
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            Row subHeaderRow = sheet.createRow(rowCnt++);
            for (int i = 0; i < colNames_ex.length; i++) {
                Cell cell = subHeaderRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, colWidths_ex[i]);
            }

            ExhibitorNewDetailDTO exhibitorDetailDTO = new ExhibitorNewDetailDTO();
            exhibitorDetailDTO.setTransferYear(transferYear);
            List<ExhibitorNewDetailDTO> exhibitorDetailList = kibsMngService.processSelectExhibitorNewDetailList(exhibitorDetailDTO);
            int listNum = exhibitorDetailList.size();
            DecimalFormat df = new DecimalFormat("#,##0");

            for (ExhibitorNewDetailDTO info : exhibitorDetailList) {
                Row dataRow = sheet.createRow(rowCnt++);
                int cellCnt = 0;

                cellCnt = writeCompanyInfo(dataRow, cellCnt, info, listNum--);
                cellCnt = writeRepresentativeInfo(dataRow, cellCnt, info);
                cellCnt = writeOtherContactsInfo(dataRow, cellCnt, info);
                cellCnt = writeCompanyIntroInfo(dataRow, cellCnt, info);
                cellCnt = writeParticipationFields(dataRow, cellCnt, info);
                cellCnt = writeMaritimeExhibitionInfo(dataRow, cellCnt, info);
                cellCnt = writeExhibitionItems(dataRow, cellCnt, info);
                cellCnt = writeCompanyBadges(dataRow, cellCnt, info);
                cellCnt = writeOnlineProducts(dataRow, cellCnt, info);
                cellCnt = writeApplicationDetails(dataRow, cellCnt, info, df);

                for(int i=0; i < colNames_ex.length; i++){
                    Cell cell = dataRow.getCell(i);
                    if (cell == null) {
                        cell = dataRow.createCell(i);
                    }
                    cell.setCellStyle(bodyStyle);
                }
            }

            // =============================================================
            // Pricing 시트 생성 로직 ▼▼▼
            // =============================================================
            SXSSFSheet sheet2 = workbook.createSheet("Pricing");
            sheet2.trackAllColumnsForAutoSizing();

            int rowCnt2 = 0;

            // --- 폰트 및 스타일 정의 ---
            Font titleFont = workbook.createFont(); titleFont.setFontName("맑은 고딕"); titleFont.setBold(true); titleFont.setFontHeightInPoints((short) 12);
            Font headerFont = workbook.createFont(); headerFont.setFontName("맑은 고딕"); headerFont.setBold(true); headerFont.setFontHeightInPoints((short) 10);
            Font bodyFont = workbook.createFont(); bodyFont.setFontName("맑은 고딕"); bodyFont.setFontHeightInPoints((short) 10);
            Font noteFont = workbook.createFont(); noteFont.setFontName("맑은 고딕"); noteFont.setFontHeightInPoints((short) 10); noteFont.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
            CellStyle titleStyle = workbook.createCellStyle(); titleStyle.setFont(titleFont);
            CellStyle mainHeaderStyle = workbook.createCellStyle(); mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER); mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER); mainHeaderStyle.setBorderTop(BorderStyle.THIN); mainHeaderStyle.setBorderBottom(BorderStyle.THIN); mainHeaderStyle.setBorderLeft(BorderStyle.THIN); mainHeaderStyle.setBorderRight(BorderStyle.THIN); mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index); mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); mainHeaderStyle.setFont(headerFont);
            CellStyle subHeaderStyle = workbook.createCellStyle(); subHeaderStyle.setAlignment(HorizontalAlignment.CENTER); subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER); subHeaderStyle.setBorderTop(BorderStyle.THIN); subHeaderStyle.setBorderBottom(BorderStyle.THIN); subHeaderStyle.setBorderLeft(BorderStyle.THIN); subHeaderStyle.setBorderRight(BorderStyle.THIN); subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); subHeaderStyle.setFont(headerFont);
            CellStyle bodyLeftStyle = workbook.createCellStyle(); bodyLeftStyle.setAlignment(HorizontalAlignment.LEFT); bodyLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER); bodyLeftStyle.setBorderTop(BorderStyle.THIN); bodyLeftStyle.setBorderBottom(BorderStyle.THIN); bodyLeftStyle.setBorderLeft(BorderStyle.THIN); bodyLeftStyle.setBorderRight(BorderStyle.THIN); bodyLeftStyle.setFont(bodyFont); bodyLeftStyle.setWrapText(true);
            CellStyle bodyCenterStyle = workbook.createCellStyle(); bodyCenterStyle.cloneStyleFrom(bodyLeftStyle); bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
            CellStyle bodyRightStyle = workbook.createCellStyle(); bodyRightStyle.cloneStyleFrom(bodyLeftStyle); bodyRightStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle noteStyle = workbook.createCellStyle(); noteStyle.cloneStyleFrom(bodyLeftStyle); noteStyle.setFont(noteFont);

            // --- ■ 부스 (VAT 별도) ---
            SXSSFRow row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("■ 부스(vat 별도)"); row.getCell(0).setCellStyle(titleStyle);
            rowCnt2++;
            String[][] boothData = { {"부스구분", "가격"}, {"독립부스 1차", "1,500,000"}, {"조립부스 1차", "1,800,000"}, {"독립부스 2차", "1,600,000"}, {"조립부스 2차", "1,900,000"}, {"독립부스", "1,800,000"}, {"조립부스", "2,100,000"} };
            for (int i=0; i<boothData.length; i++) {
                row = sheet2.createRow(rowCnt2++);
                row.createCell(0).setCellValue(boothData[i][0]); row.createCell(1).setCellValue(boothData[i][1]);
                if (i==0) { row.getCell(0).setCellStyle(mainHeaderStyle); row.getCell(1).setCellStyle(mainHeaderStyle); }
                else { row.getCell(0).setCellStyle(bodyCenterStyle); row.getCell(1).setCellStyle(bodyRightStyle); }
            }
            rowCnt2+=2;

            // --- ■ 할인내역 (VAT 별도) ---
            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("■ 할인내역(vat 별도)"); row.getCell(0).setCellStyle(titleStyle);
            rowCnt2++;
            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("구분"); row.createCell(1).setCellValue("할인항목"); row.createCell(2).setCellValue("가격"); row.createCell(3).setCellValue("비고");
            for(int i=0; i<4; i++) row.getCell(i).setCellStyle(mainHeaderStyle);
            String[][] discountData = { {"기본할인", "1차 조기신청 할인", "300,000", "부스당"}, {"기본할인", "2차 조기신청 할인", "200,000", "부스당"}, {"규모할인", "10부스 이상", "400,000", "첫참가 업체 30만원/부스 추가 할인"}, {"규모할인", "20부스 이상", "650,000", ""}, {"규모할인", "30부스 이상", "750,000", ""}, {"규모할인", "40부스 이상", "800,000", ""}, {"규모할인", "50부스 이상", "850,000", ""}, {"규모할인", "100부스 이상", "900,000", ""}, {"기본할인", "재참가 기업 할인", "200,000", ""}, {"기본할인", "첫참가 기업 할인", "500,000", "10부스 미만 신청시에만 적용"}, {"기본할인", "협회 회원사", "200,000", ""}, {"특별할인(비공개)", "올해의 제품상", "최종 참가비용의 50% 할인", "=할인 적용 된 금액의 50%"}, {"특별할인(비공개)", "특별관", "기본할인 적용가격의 최대 80% 할인", ""}, {"기타", "해양레저산업협회 발전기금 납부", "참가비의 10% 납부", ""} };
            int discountStartRow = rowCnt2;
            for(String[] d : discountData){
                row = sheet2.createRow(rowCnt2++);
                row.createCell(0).setCellValue(d[0]); row.createCell(1).setCellValue(d[1]); row.createCell(2).setCellValue(d[2]); row.createCell(3).setCellValue(d[3]);
                row.getCell(0).setCellStyle(subHeaderStyle); row.getCell(1).setCellStyle(bodyLeftStyle); row.getCell(2).setCellStyle(bodyRightStyle); row.getCell(3).setCellStyle(noteStyle);
            }
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow, discountStartRow + 1, 0, 0)); // 기본할인
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow + 2, discountStartRow + 7, 0, 0)); // 규모할인
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow + 8, discountStartRow + 10, 0, 0)); // 기본할인
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow + 11, discountStartRow + 12, 0, 0)); // 특별할인
            rowCnt2+=2;

            // --- ■ 유틸리티(부대시설) ---
            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("■ 유틸리티(부대시설)"); row.getCell(0).setCellStyle(titleStyle);
            rowCnt2++;
            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("구분"); row.createCell(1).setCellValue("기술지원 항목"); row.createCell(2).setCellValue("가격(원)"); row.createCell(3).setCellValue("비고");
            for(int i=0; i<4; i++) row.getCell(i).setCellStyle(mainHeaderStyle);
            String[][] utilityData = { {"파이텍스", "신품", "80,000", ""}, {"파이텍스", "재사용품", "50,000", ""}, {"전기", "주간 단상 220v", "80,000", "원/kw"}, {"전기", "24시간용 220v", "100,000", "원/kw"}, {"인터넷", "인터넷", "160,000", "원/1회선"}, {"급배수 및 압축공기", "급배수", "180,000", "원/1구"}, {"급배수 및 압축공기", "압축공기", "180,000", "원/1구"}, {"바코드 리더기", "바코드 리더기", "200,000", "원/1개"} };
            int utilityStartRow = rowCnt2;
            for(String[] u : utilityData){
                row = sheet2.createRow(rowCnt2++);
                row.createCell(0).setCellValue(u[0]); row.createCell(1).setCellValue(u[1]); row.createCell(2).setCellValue(u[2]); row.createCell(3).setCellValue(u[3]);
            }

            // 유틸리티 셀 병합 로직 수정
            sheet2.addMergedRegion(new CellRangeAddress(utilityStartRow, utilityStartRow + 1, 0, 0)); // 파이텍스
            sheet2.addMergedRegion(new CellRangeAddress(utilityStartRow + 2, utilityStartRow + 3, 0, 0)); // 전기
            sheet2.addMergedRegion(new CellRangeAddress(utilityStartRow + 5, utilityStartRow + 6, 0, 0)); // 급배수 및 압축공기
            // 인터넷과 바코드 리더기는 1줄이므로 병합하지 않음

            for(int i = utilityStartRow; i < rowCnt2; i++){
                row = sheet2.getRow(i);
                row.getCell(0).setCellStyle(subHeaderStyle);
                row.getCell(1).setCellStyle(bodyLeftStyle);
                row.getCell(2).setCellStyle(bodyRightStyle);
                row.getCell(3).setCellStyle(bodyLeftStyle);
            }

            // 컬럼 너비 자동 조정
            for (int i=0; i<4; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

            // 파일 저장
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(res.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int writeCompanyInfo(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info, int listNum) {
        dataRow.createCell(cellCnt++).setCellValue(listNum);
        dataRow.createCell(cellCnt++).setCellValue(info.getApprovalStatus() != null && info.getApprovalStatus().contains("승인") ? "참가" : "취소");
        dataRow.createCell(cellCnt++).setCellValue(info.getApprovalStatus());
        dataRow.createCell(cellCnt++).setCellValue(info.getBoothNum());
        dataRow.createCell(cellCnt++).setCellValue(info.getId());
        dataRow.createCell(cellCnt++).setCellValue(info.getInitRegiDttm());
        dataRow.createCell(cellCnt++).setCellValue(info.getFinalRegiDttm());
        String prcYn = info.getPrcYn(); String prcYn_val = "";
        if (prcYn != null) { switch (prcYn) { case "0": prcYn_val = "미납"; break; case "1": prcYn_val = "참가비 납부"; break; case "2": prcYn_val = "50% 납부"; break; case "3": prcYn_val = "전액 납부"; break; case "4": prcYn_val = "완납(부대시설비)"; break; } }
        dataRow.createCell(cellCnt++).setCellValue(prcYn_val);
        dataRow.createCell(cellCnt++).setCellValue(info.getBpNum());
        dataRow.createCell(cellCnt++).setCellValue(info.getContactWriter());
        dataRow.createCell(cellCnt++).setCellValue(info.getContactDate());
        dataRow.createCell(cellCnt++).setCellValue(info.getContactContent());
        dataRow.createCell(cellCnt++).setCellValue(info.getReferenceWriter());
        dataRow.createCell(cellCnt++).setCellValue(info.getReferenceDate());
        dataRow.createCell(cellCnt++).setCellValue(info.getReferenceContent());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyLicenseNum());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyNameKo());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyNameEn());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyAddress());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyAddressDetail());
        dataRow.createCell(cellCnt++).setCellValue(info.getFactoryAddress());
        dataRow.createCell(cellCnt++).setCellValue(info.getFactoryAddressDetail());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyCeo());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyTel());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyHomepage());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyFax());
        dataRow.createCell(cellCnt++).setCellValue(info.getIndustryPart());
        dataRow.createCell(cellCnt++).setCellValue(info.getIndustryPartEtc());
        dataRow.createCell(cellCnt++).setCellValue(info.getPrePartYear());
        dataRow.createCell(cellCnt++).setCellValue(info.getMemberCompanyYn());
        return cellCnt;
    }

    private int writeRepresentativeInfo(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        dataRow.createCell(cellCnt++).setCellValue(info.getName());
        dataRow.createCell(cellCnt++).setCellValue(info.getPosition());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepart());
        dataRow.createCell(cellCnt++).setCellValue(info.getTel());
        dataRow.createCell(cellCnt++).setCellValue(info.getPhone());
        dataRow.createCell(cellCnt++).setCellValue(info.getEmail());
        return cellCnt;
    }

    private int writeOtherContactsInfo(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        for(int i=0; i<3; i++){
            String[] chargePersonNameSplit = info.getChargePersonName() != null ? info.getChargePersonName().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(chargePersonNameSplit, i));
            String[] chargePersonPositionSplit = info.getChargePersonPosition() != null ? info.getChargePersonPosition().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(chargePersonPositionSplit, i));
            String[] chargePersonDepartSplit = info.getChargePersonDepart() != null ? info.getChargePersonDepart().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(chargePersonDepartSplit, i));
            String[] chargePersonTelSplit = info.getChargePersonTel() != null ? info.getChargePersonTel().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(chargePersonTelSplit, i));
            String[] chargePersonPhoneSplit = info.getChargePersonPhone() != null ? info.getChargePersonPhone().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(chargePersonPhoneSplit, i));
            String[] chargePersonEmailSplit = info.getChargePersonEmail() != null ? info.getChargePersonEmail().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(chargePersonEmailSplit, i));
        }
        return cellCnt;
    }

    private int writeCompanyIntroInfo(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyIntroVideo());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyIntroKo());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyIntroEn());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyPurposeKo());
        dataRow.createCell(cellCnt++).setCellValue(info.getCompanyPurposeEn());
        dataRow.createCell(cellCnt++).setCellValue(info.getNewItemIntroKo());
        dataRow.createCell(cellCnt++).setCellValue(info.getNewItemIntroEn());
        dataRow.createCell(cellCnt++).setCellValue(info.getPromotionPlan());
        return cellCnt;
    }

    // 헬퍼 메소드 (참가 분야)
    private String checkField(String fieldName, String f1, String f2, String f3) {
        if (fieldName.equals(f1) || fieldName.equals(f2) || fieldName.equals(f3)) {
            return "O";
        }
        return "";
    }

    private int writeParticipationFields(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        String eventType = info.getFieldParticipatory();
        String eventName = ""; // 기본값

        if ("boatShow".equals(eventType)) {
            eventName = "경기국제보트쇼";
        } else if ("surfShow".equals(eventType)) {
            eventName = "코리아서프쇼";
        } else if ("travelShow".equals(eventType)) {
            eventName = "해양관광전";
        } else if (eventType != null) {
            eventName = eventType; // 혹시 모를 다른 값은 그대로 출력
        }

        dataRow.createCell(cellCnt++).setCellValue(eventName); // 참가행사
        String f1 = info.getFieldParticipatory1();
        String f2 = info.getFieldParticipatory2();
        String f3 = info.getFieldParticipatory3();
        dataRow.createCell(cellCnt++).setCellValue(checkField("보트&요트", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("무동력보트", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("워크보트", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("해양부품&장비", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("안전&마리나", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("해양관광", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("해양레저", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("수중레저", f1, f2, f3));
        dataRow.createCell(cellCnt++).setCellValue(checkField("서핑", f1, f2, f3));
        return cellCnt;
    }

    private int writeMaritimeExhibitionInfo(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        // 1. 참가여부
        dataRow.createCell(cellCnt++).setCellValue("Y".equals(info.getMaritimeExhibitionYn()) ? "참가" : "미참가");

        // 2. 참가구분
        boolean sea = Boolean.TRUE.equals(info.getMaritimeExhibitionSea());
        boolean land = Boolean.TRUE.equals(info.getMaritimeExhibitionLand());
        String participationType = "";
        if (sea) {
            participationType += "해상 전시 (시승체험)";
        }
        if (land) {
            if (sea) participationType += "\n"; // 엑셀에서 줄바꿈은 \n
            participationType += "육상 전시";
        }
        dataRow.createCell(cellCnt++).setCellValue(participationType);

        return cellCnt;
    }

    private int writeExhibitionItems(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        for(int i=0; i<20; i++){
            String[] productOptionBigSplit = info.getProductOptionBig() != null ? info.getProductOptionBig().split("\\^", -1) : new String[0];
            String[] productOptionSmallSplit = info.getProductOptionSmall() != null ? info.getProductOptionSmall().split("\\^", -1) : new String[0];
            String productOptionBig = convertValue(productOptionBigSplit, i);
            dataRow.createCell(cellCnt++).setCellValue((productOptionBig != null && !productOptionBig.isEmpty()) ? productOptionBig + " / " + convertValue(productOptionSmallSplit, i) : "");
            String[] productNameKoSplit = info.getProductNameKo() != null ? info.getProductNameKo().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productNameKoSplit, i));
            String[] productQtySplit = info.getProductQty() != null ? info.getProductQty().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productQtySplit, i));
            String[] productBrandSplit = info.getProductBrand() != null ? info.getProductBrand().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productBrandSplit, i));
            String[] productLengthSplit = info.getProductLength() != null ? info.getProductLength().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productLengthSplit, i));
            String[] productWidthSplit = info.getProductWidth() != null ? info.getProductWidth().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productWidthSplit, i));
            String[] productHeightSplit = info.getProductHeight() != null ? info.getProductHeight().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productHeightSplit, i));
            String[] productWeightSplit = info.getProductWeight() != null ? info.getProductWeight().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productWeightSplit, i));
            String[] productMaterialSplit = info.getProductMaterial() != null ? info.getProductMaterial().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productMaterialSplit, i));
            String[] productYearSplit = info.getProductYear() != null ? info.getProductYear().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(productYearSplit, i));
        }
        return cellCnt;
    }

    private int writeCompanyBadges(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        String companyBadge = info.getCompanyBadge() != null ? info.getCompanyBadge().replaceAll(" ","") : "";
        dataRow.createCell(cellCnt++).setCellValue(companyBadge.contains("3회") ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(companyBadge.contains("제품상") ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(companyBadge.contains("제작한") ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(companyBadge.contains("우수") ? "O" : "");
        return cellCnt;
    }

    private int writeOnlineProducts(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        for(int i=0; i<30; i++) {
            String[] onlineOptionBigSplit = info.getOnlineOptionBig() != null ? info.getOnlineOptionBig().split("\\^", -1) : new String[0];
            String[] onlineOptionSmallSplit = info.getOnlineOptionSmall() != null ? info.getOnlineOptionSmall().split("\\^", -1) : new String[0];
            String onlineOptionBig = convertValue(onlineOptionBigSplit, i);
            dataRow.createCell(cellCnt++).setCellValue((onlineOptionBig != null && !onlineOptionBig.isEmpty()) ? onlineOptionBig + " / " + convertValue(onlineOptionSmallSplit, i) : "");
            String[] onlineNameKoSplit = info.getOnlineNameKo() != null ? info.getOnlineNameKo().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineNameKoSplit, i));
            String[] onlineIntroKoSplit = info.getOnlineIntroKo() != null ? info.getOnlineIntroKo().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineIntroKoSplit, i));
            String[] onlineIntroEnSplit = info.getOnlineIntroEn() != null ? info.getOnlineIntroEn().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineIntroEnSplit, i));
            String[] onlineLinkSplit = info.getOnlineLink() != null ? info.getOnlineLink().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineLinkSplit, i));
            String[] onlineLengthSplit = info.getOnlineLength() != null ? info.getOnlineLength().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineLengthSplit, i));
            String[] onlineWidthSplit = info.getOnlineWidth() != null ? info.getOnlineWidth().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineWidthSplit, i));
            String[] onlineHeightSplit = info.getOnlineHeight() != null ? info.getOnlineHeight().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineHeightSplit, i));
            String[] onlineWeightSplit = info.getOnlineWeight() != null ? info.getOnlineWeight().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineWeightSplit, i));
            String[] onlineMaterialSplit = info.getOnlineMaterial() != null ? info.getOnlineMaterial().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineMaterialSplit, i));
            String[] onlineYearSplit = info.getOnlineYear() != null ? info.getOnlineYear().split("\\^", -1) : new String[0];
            dataRow.createCell(cellCnt++).setCellValue(convertValue(onlineYearSplit, i));
        }
        return cellCnt;
    }

    private int writeApplicationDetails(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info, DecimalFormat df) {
        dataRow.createCell(cellCnt++).setCellValue("Y".equals(info.getExportMeetingYn()) ? "참가" : "참가 안 함");
        dataRow.createCell(cellCnt++).setCellValue("100000 원");
        int standAlone = info.getStandAloneBoothCnt() != null ? info.getStandAloneBoothCnt() : 0;
        int assembly = info.getAssemblyBoothCnt() != null ? info.getAssemblyBoothCnt() : 0;
        int online = info.getOnlineBoothCnt() != null ? info.getOnlineBoothCnt() : 0;
        dataRow.createCell(cellCnt++).setCellValue(standAlone);
        dataRow.createCell(cellCnt++).setCellValue(assembly);
        dataRow.createCell(cellCnt++).setCellValue(online);
        dataRow.createCell(cellCnt++).setCellValue(standAlone + assembly + online); // 총 부스 수

        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountEarly1()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountEarly2()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountFirstUnder10()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountFirstOver10()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountRe()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountScale1()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountScale2()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountScale3()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountScale4()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountScale5()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountScale6()) ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountLeisure()) ? "O" : "");

        if ("Y".equals(info.getMemberCompanyYn()) && info.getBoothPrcSum() != null) {
            dataRow.createCell(cellCnt++).setCellValue(df.format(info.getBoothPrcSum() * 0.1) + " 원");
        } else {
            dataRow.createCell(cellCnt++).setCellValue("");
        }

        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial1Yn() ? "O" : "");
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial1Note());
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial2Reason());
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial2Yn() ? df.format(info.getDiscountSpecial2Amount()) + " 원" : "");
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial2Note());
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial3Reason());
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial3Yn() ? df.format(info.getDiscountSpecial3Amount()) + " 원" : "");
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial3Note());

        dataRow.createCell(cellCnt++).setCellValue(info.getPrcSum() != null ? df.format(info.getPrcSum()) + " 원" : "0 원");
        dataRow.createCell(cellCnt++).setCellValue(info.getPrcVat() != null ? df.format(info.getPrcVat()) + " 원" : "0 원");
        dataRow.createCell(cellCnt++).setCellValue(info.getPrcTotal() != null ? df.format(info.getPrcTotal()) + " 원" : "0 원");
        return cellCnt;
    }

    private String convertValue(String[] split, int index) {
        if (split != null && index < split.length && split[index] != null) {
            return split[index];
        }
        return "";
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

    @RequestMapping(value = "/mng/exhibitor/booth/download.do", method = RequestMethod.GET)
    public void exhibitor_booth_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_booth_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // SXSSFWorkbook: 대용량 데이터 처리 시 메모리 부족 방지를 위한 스트리밍 방식
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            SXSSFSheet sheet = workbook.createSheet("전시부스 신청 현황");

            sheet.trackAllColumnsForAutoSizing();

            // --- 폰트 및 스타일 정의 ---
            Font titleFont = workbook.createFont();
            titleFont.setFontName("맑은 고딕");
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 18);

            Font headerFont = workbook.createFont();
            headerFont.setFontName("맑은 고딕");
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);

            Font bodyFont = workbook.createFont();
            bodyFont.setFontName("맑은 고딕");
            bodyFont.setFontHeightInPoints((short) 10);

            // 타이틀 스타일
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // 메인 헤더 스타일 (진한 회색 배경)
            CellStyle mainHeaderStyle = workbook.createCellStyle();
            mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
            mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            mainHeaderStyle.setBorderTop(BorderStyle.THIN);
            mainHeaderStyle.setBorderBottom(BorderStyle.THIN);
            mainHeaderStyle.setBorderLeft(BorderStyle.THIN);
            mainHeaderStyle.setBorderRight(BorderStyle.THIN);
            mainHeaderStyle.setFont(headerFont);

            // 서브 헤더 스타일 (옅은 회색 배경)
            CellStyle subHeaderStyle = workbook.createCellStyle();
            subHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            subHeaderStyle.setBorderTop(BorderStyle.THIN);
            subHeaderStyle.setBorderBottom(BorderStyle.THIN);
            subHeaderStyle.setBorderLeft(BorderStyle.THIN);
            subHeaderStyle.setBorderRight(BorderStyle.THIN);
            subHeaderStyle.setFont(headerFont);

            // 본문 스타일 (가운데 정렬)
            CellStyle bodyCenterStyle = workbook.createCellStyle();
            bodyCenterStyle.setFont(bodyFont);
            bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyCenterStyle.setBorderTop(BorderStyle.THIN);
            bodyCenterStyle.setBorderBottom(BorderStyle.THIN);
            bodyCenterStyle.setBorderLeft(BorderStyle.THIN);
            bodyCenterStyle.setBorderRight(BorderStyle.THIN);
            bodyCenterStyle.setWrapText(true);

            // 본문 스타일 (왼쪽 정렬)
            CellStyle bodyLeftStyle = workbook.createCellStyle();
            bodyLeftStyle.setFont(bodyFont);
            bodyLeftStyle.setAlignment(HorizontalAlignment.LEFT);
            bodyLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyLeftStyle.setBorderTop(BorderStyle.THIN);
            bodyLeftStyle.setBorderBottom(BorderStyle.THIN);
            bodyLeftStyle.setBorderLeft(BorderStyle.THIN);
            bodyLeftStyle.setBorderRight(BorderStyle.THIN);
            bodyLeftStyle.setWrapText(true);

            // --- 1행: 메인 헤더 생성 ---
            Row mainHeaderRow = sheet.createRow(0);
            mainHeaderRow.createCell(0).setCellValue("기본정보");
            mainHeaderRow.createCell(7).setCellValue("할인구분");
            mainHeaderRow.createCell(19).setCellValue("기본정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 18));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 22));
            for(int i=0; i<22; i++){
                if(mainHeaderRow.getCell(i) == null) mainHeaderRow.createCell(i);
                mainHeaderRow.getCell(i).setCellStyle(mainHeaderStyle);
            }

            // --- 2행: 서브 헤더 생성 ---
            String[] headers = {"번호", "회사명(국문)", "회사명(영문)", "인보이스", "부스구분", "부스수량", "부스가격", "1차 조기신청", "2차 조기신청", "첫 참가(10부스 미만)", "첫 참가(10부스 이상)", "재참가", "규모(10+)", "규모(20+)", "규모(30+)", "규모(40+)", "규모(50+)", "규모(100+)", "협회할인", "할인가격", "총액(VAT미포함)", "등록일시", "수정일시"};
            Row subHeaderRow = sheet.createRow(1);
            for(int i=0; i<headers.length; i++) {
                Cell cell = subHeaderRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(subHeaderStyle);
            }

            // --- 데이터 행 생성 ---
            // 데이터 조회
            List<BoothDetailDTO> boothList = kibsMngService.processSelectExcelBoothDetailList(transferYear);
            int rowNum = 2; // 데이터는 3행부터 시작

            if (boothList != null && !boothList.isEmpty()) {
                for (int i = 0; i < boothList.size(); i++) {
                    BoothDetailDTO booth = boothList.get(i);
                    Row row = sheet.createRow(rowNum++);
                    int cellCnt = 0;

                    row.createCell(cellCnt++).setCellValue(i + 1); // 0. 번호
                    row.createCell(cellCnt++).setCellValue(booth.getCompanyNameKo()); // 회사명(국문)
                    row.createCell(cellCnt++).setCellValue(booth.getCompanyNameEn()); // 회사명(영문)
                    row.createCell(cellCnt++).setCellValue(booth.getInvoiceYn().equals("Y") ? "O" : ""); // 인보이스
                    row.createCell(cellCnt++).setCellValue(booth.getBoothType()); // 부스구분
                    row.createCell(cellCnt++).setCellValue(booth.getRegistrationCnt() + booth.getStandAloneBoothCnt() + booth.getAssemblyBoothCnt() + booth.getOnlineBoothCnt()); // 부스수량
                    row.createCell(cellCnt++).setCellValue(String.format("%,d", booth.getBoothPrcSum())); // 부스가격
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountEarly1() ? "O" : ""); // 1차 조기신청
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountEarly2() ? "O" : ""); // 2차 조기신청
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountFirstUnder10() ? "O" : ""); // 첫 참가(10-)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountFirstOver10() ? "O" : ""); // 첫 참가(10+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountRe() ? "O" : ""); // 재 참가
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountScale1() ? "O" : ""); // 규모(10+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountScale2() ? "O" : ""); // 규모(20+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountScale3() ? "O" : ""); // 규모(30+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountScale4() ? "O" : ""); // 규모(40+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountScale5() ? "O" : ""); // 규모(50+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountScale6() ? "O" : ""); // 규모(100+)
                    row.createCell(cellCnt++).setCellValue(booth.getDiscountLeisure() ? "O" : ""); // 협회할인
                    row.createCell(cellCnt++).setCellValue(String.format("%,d", booth.getDiscountPrcSum())); // 할인가격
                    row.createCell(cellCnt++).setCellValue(String.format("%,d", booth.getPrcSum())); // 총액(VAT미포함)
                    row.createCell(cellCnt++).setCellValue(booth.getInitRegiDttm()); // 등록일시
                    row.createCell(cellCnt++).setCellValue(booth.getFinalRegiDttm()); // 수정일시

                    // 모든 셀에 스타일 적용
                    for (int j = 0; j < headers.length; j++) {
                        Cell cell = row.getCell(j);
                        cell.setCellStyle(bodyCenterStyle);
                    }
                }
            }

            // --- 컬럼 너비 설정 (11개 컬럼 기준으로 수정) ---
            sheet.setColumnWidth(0, 1500);  // 번호
            sheet.setColumnWidth(1, 8000);  // 회사명(국문)
            sheet.setColumnWidth(2, 8000);  // 회사명(영문)
            sheet.setColumnWidth(3, 4000);  // 인보이스
            sheet.setColumnWidth(4, 6000);  // 부스구분
            sheet.setColumnWidth(5, 4000);  // 부스수량
            sheet.setColumnWidth(6, 4000);  // 부스가격
            sheet.setColumnWidth(7, 5000);  // 1차조기신청
            sheet.setColumnWidth(8, 5000);  // 2차조기신청
            sheet.setColumnWidth(9, 4000);  // 첫참가(10-)
            sheet.setColumnWidth(10, 4000); // 첫참가(10+)
            sheet.setColumnWidth(11, 4000); // 재참가
            sheet.setColumnWidth(12, 4000); // 규모(10+)
            sheet.setColumnWidth(13, 4000); // 규모(20+)
            sheet.setColumnWidth(14, 4000); // 규모(30+)
            sheet.setColumnWidth(15, 4000); // 규모(40+)
            sheet.setColumnWidth(16, 4000); // 규모(50+)
            sheet.setColumnWidth(17, 4000); // 규모(100+)
            sheet.setColumnWidth(18, 4000); // 협회할인
            sheet.setColumnWidth(19, 4000); // 할인가격
            sheet.setColumnWidth(20, 4000); // 총액
            sheet.setColumnWidth(21, 4000); // 등록일시
            sheet.setColumnWidth(22, 4000); // 수정일시

            // --- 엑셀 파일 다운로드 ---
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(res.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mng/exhibitor/pass/download.do", method = RequestMethod.GET)
    public void exhibitor_pass_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_pass_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // SXSSFWorkbook: 대용량 데이터 처리 시 메모리 부족 방지를 위한 스트리밍 방식
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            SXSSFSheet sheet = workbook.createSheet("출입증 신청 현황");

            sheet.trackAllColumnsForAutoSizing();

            // --- 폰트 및 스타일 정의 ---
            Font titleFont = workbook.createFont();
            titleFont.setFontName("맑은 고딕");
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 18);

            Font headerFont = workbook.createFont();
            headerFont.setFontName("맑은 고딕");
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);

            Font bodyFont = workbook.createFont();
            bodyFont.setFontName("맑은 고딕");
            bodyFont.setFontHeightInPoints((short) 10);

            // 타이틀 스타일
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // 메인 헤더 스타일 (진한 회색 배경)
            CellStyle mainHeaderStyle = workbook.createCellStyle();
            mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
            mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            mainHeaderStyle.setBorderTop(BorderStyle.THIN);
            mainHeaderStyle.setBorderBottom(BorderStyle.THIN);
            mainHeaderStyle.setBorderLeft(BorderStyle.THIN);
            mainHeaderStyle.setBorderRight(BorderStyle.THIN);
            mainHeaderStyle.setFont(headerFont);

            // 서브 헤더 스타일 (옅은 회색 배경)
            CellStyle subHeaderStyle = workbook.createCellStyle();
            subHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            subHeaderStyle.setBorderTop(BorderStyle.THIN);
            subHeaderStyle.setBorderBottom(BorderStyle.THIN);
            subHeaderStyle.setBorderLeft(BorderStyle.THIN);
            subHeaderStyle.setBorderRight(BorderStyle.THIN);
            subHeaderStyle.setFont(headerFont);

            // 본문 스타일 (가운데 정렬)
            CellStyle bodyCenterStyle = workbook.createCellStyle();
            bodyCenterStyle.setFont(bodyFont);
            bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyCenterStyle.setBorderTop(BorderStyle.THIN);
            bodyCenterStyle.setBorderBottom(BorderStyle.THIN);
            bodyCenterStyle.setBorderLeft(BorderStyle.THIN);
            bodyCenterStyle.setBorderRight(BorderStyle.THIN);
            bodyCenterStyle.setWrapText(true);

            // 본문 스타일 (왼쪽 정렬)
            CellStyle bodyLeftStyle = workbook.createCellStyle();
            bodyLeftStyle.setFont(bodyFont);
            bodyLeftStyle.setAlignment(HorizontalAlignment.LEFT);
            bodyLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyLeftStyle.setBorderTop(BorderStyle.THIN);
            bodyLeftStyle.setBorderBottom(BorderStyle.THIN);
            bodyLeftStyle.setBorderLeft(BorderStyle.THIN);
            bodyLeftStyle.setBorderRight(BorderStyle.THIN);
            bodyLeftStyle.setWrapText(true);

            // --- 1행: 메인 헤더 생성 ---
            Row mainHeaderRow = sheet.createRow(0);
            mainHeaderRow.createCell(0).setCellValue("기본정보");
            mainHeaderRow.createCell(5).setCellValue("출입증신청");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7)); // A1~H1
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10)); // I1~K1
            for(int i=0; i<11; i++){
                if(mainHeaderRow.getCell(i) == null) mainHeaderRow.createCell(i);
                mainHeaderRow.getCell(i).setCellStyle(mainHeaderStyle);
            }

            // --- 2행: 서브 헤더 생성 ---
            String[] headers = {"번호", "회사명(국문)", "회사명(영문)", "담당자명(국문)", "담당자명(영문)", "직책(국문)", "직책(영문)", "연락처", "인원", "신청여부", "등록일시"};
            Row subHeaderRow = sheet.createRow(1);
            for(int i=0; i<headers.length; i++) {
                Cell cell = subHeaderRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(subHeaderStyle);
            }

            // --- 데이터 행 생성 ---
            // 데이터 조회
            List<PassDetailDTO> passList = kibsMngService.processSelectExcelPassDetailList(transferYear);
            int rowNum = 2; // 데이터는 3행부터 시작

            if (passList != null && !passList.isEmpty()) {
                for (int i = 0; i < passList.size(); i++) {
                    PassDetailDTO pass = passList.get(i);
                    Row row = sheet.createRow(rowNum++);
                    int cellCnt = 0;

                    row.createCell(cellCnt++).setCellValue(i + 1); // 0. 번호
                    row.createCell(cellCnt++).setCellValue(pass.getCompanyNameKo()); // 1. 회사명(국문)
                    row.createCell(cellCnt++).setCellValue(pass.getCompanyNameEn()); // 2. 회사명(영문)
                    row.createCell(cellCnt++).setCellValue(pass.getPassName()); // 3. 담당자명(국문)
                    row.createCell(cellCnt++).setCellValue(pass.getPassLastName() + " " + pass.getPassFirstName()); // 4. 담당자명(영문)
                    row.createCell(cellCnt++).setCellValue(pass.getPassPositionKo()); // 5. 직책(국문)
                    row.createCell(cellCnt++).setCellValue(pass.getPassPositionEn()); // 6. 직책(영문)
                    row.createCell(cellCnt++).setCellValue(pass.getRepresentativePhone()); // 7. 연락처
                    row.createCell(cellCnt++).setCellValue(pass.getPassTotalCount()); // 8. 인원

                    String passName = pass.getPassName();
                    boolean isApplied = (passName != null && !passName.trim().isEmpty());
                    row.createCell(cellCnt++).setCellValue(isApplied ? "신청" : "미신청"); // 9. 신청여부

                    row.createCell(cellCnt++).setCellValue(pass.getInitRegiDttm() != null ? pass.getInitRegiDttm().substring(0, 10) : ""); // 10. 등록일시

                    // 모든 셀에 스타일 적용
                    for (int j = 0; j < headers.length; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) cell = row.createCell(j); // [추가] 빈 셀 생성

                        if (j == 1 || j == 2) { // 회사명(국문), 회사명(영문)만 왼쪽 정렬
                            cell.setCellStyle(bodyLeftStyle);
                        } else {
                            cell.setCellStyle(bodyCenterStyle);
                        }
                    }
                }
            }

            // --- 컬럼 너비 설정 (11개 컬럼 기준으로 수정) ---
            sheet.setColumnWidth(0, 1500);  // 번호
            sheet.setColumnWidth(1, 8000);  // 회사명(국문)
            sheet.setColumnWidth(2, 8000);  // 회사명(영문)
            sheet.setColumnWidth(3, 4000);  // 담당자명(국문)
            sheet.setColumnWidth(4, 5000);  // 담당자명(영문)
            sheet.setColumnWidth(5, 4000);  // 직책(국문)
            sheet.setColumnWidth(6, 4000);  // 직책(영문)
            sheet.setColumnWidth(7, 5000);  // 연락처
            sheet.setColumnWidth(8, 2000);  // 인원
            sheet.setColumnWidth(9, 4000);  // 신청여부
            sheet.setColumnWidth(10, 4000); // 등록일시

            // --- 엑셀 파일 다운로드 ---
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(res.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mng/exhibitor/sign/download.do", method = RequestMethod.GET)
    public void exhibitor_sign_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_sign_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // SXSSFWorkbook: 대용량 데이터 처리 시 메모리 부족 방지를 위한 스트리밍 방식
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            SXSSFSheet sheet = workbook.createSheet("상호 간판 신청 현황");

            sheet.trackAllColumnsForAutoSizing();

            // --- 폰트 및 스타일 정의 ---
            Font headerFont = workbook.createFont();
            headerFont.setFontName("맑은 고딕");
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);

            Font bodyFont = workbook.createFont();
            bodyFont.setFontName("맑은 고딕");
            bodyFont.setFontHeightInPoints((short) 10);

            CellStyle mainHeaderStyle = workbook.createCellStyle();
            mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
            mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            mainHeaderStyle.setBorderTop(BorderStyle.THIN); mainHeaderStyle.setBorderBottom(BorderStyle.THIN);
            mainHeaderStyle.setBorderLeft(BorderStyle.THIN); mainHeaderStyle.setBorderRight(BorderStyle.THIN);
            mainHeaderStyle.setFont(headerFont);

            CellStyle subHeaderStyle = workbook.createCellStyle();
            subHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            subHeaderStyle.setBorderTop(BorderStyle.THIN); subHeaderStyle.setBorderBottom(BorderStyle.THIN);
            subHeaderStyle.setBorderLeft(BorderStyle.THIN); subHeaderStyle.setBorderRight(BorderStyle.THIN);
            subHeaderStyle.setFont(headerFont);

            CellStyle bodyCenterStyle = workbook.createCellStyle();
            bodyCenterStyle.setFont(bodyFont);
            bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyCenterStyle.setBorderTop(BorderStyle.THIN); bodyCenterStyle.setBorderBottom(BorderStyle.THIN);
            bodyCenterStyle.setBorderLeft(BorderStyle.THIN); bodyCenterStyle.setBorderRight(BorderStyle.THIN);
            bodyCenterStyle.setWrapText(true);

            // --- 1행: 메인 헤더 생성 및 셀 병합 ---
            Row mainHeaderRow = sheet.createRow(0);
            mainHeaderRow.createCell(0).setCellValue("기본정보");
            mainHeaderRow.createCell(5).setCellValue("상호간판 신청");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4)); // A1~E1
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 9)); // F1~J1
            for(int i=0; i<10; i++){
                if(mainHeaderRow.getCell(i) == null) mainHeaderRow.createCell(i);
                mainHeaderRow.getCell(i).setCellStyle(mainHeaderStyle);
            }

            // --- 2행: 서브 헤더 생성 ---
            String[] headers = {"번호", "회사명", "담당자명", "직책", "연락처", "독립/조립", "간판명(국문)", "간판명(영문)", "등록일시", "신청여부"};
            Row subHeaderRow = sheet.createRow(1);
            for(int i=0; i<headers.length; i++) {
                Cell cell = subHeaderRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(subHeaderStyle);
            }

            // --- 데이터 행 생성 ---
            // 데이터 조회
            List<SignDetailDTO> signList = kibsMngService.processSelectExcelSignDetailList(transferYear);
            int rowNum = 2; // 데이터는 2행부터 시작

            if (signList != null && !signList.isEmpty()) {
                for (int i = 0; i < signList.size(); i++) {
                    SignDetailDTO sign = signList.get(i);
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(i + 1);
                    row.createCell(1).setCellValue(sign.getCompanyNameKo());
                    row.createCell(2).setCellValue(sign.getRepresentativeName());
                    row.createCell(3).setCellValue(sign.getRepresentativePosition());
                    row.createCell(4).setCellValue(sign.getRepresentativePhone());
                    String originalBoothType = sign.getBoothType();
                    String displayBoothType = "";
                    if (originalBoothType != null) {
                        // 1. "등록비"와 공백을 제거합니다.
                        displayBoothType = originalBoothType.replace("등록비", "").replace(" ", "");

                        // 2. 맨 앞이나 맨 뒤에 남은 콤마(,)를 제거합니다.
                        if (displayBoothType.startsWith(",")) {
                            displayBoothType = displayBoothType.substring(1);
                        }
                        if (displayBoothType.endsWith(",")) {
                            displayBoothType = displayBoothType.substring(0, displayBoothType.length() - 1);
                        }
                    }
                    row.createCell(5).setCellValue(displayBoothType);
                    row.createCell(6).setCellValue(sign.getCompanySignNameKo());
                    row.createCell(7).setCellValue(sign.getCompanySignNameEn());
                    row.createCell(8).setCellValue(sign.getInitRegiDttm() != null ? sign.getInitRegiDttm().substring(0, 10) : "");

                    String signNameKo = sign.getCompanySignNameKo();
                    String signNameEn = sign.getCompanySignNameEn();
                    boolean isApplied = (signNameKo != null && !signNameKo.isEmpty()) || (signNameEn != null && !signNameEn.isEmpty());
                    row.createCell(9).setCellValue(isApplied ? "신청" : "미신청");

                    for (int j = 0; j < headers.length; j++) {
                        row.getCell(j).setCellStyle(bodyCenterStyle);
                    }
                }
            }

            // --- 컬럼 너비 설정 ---
            for(int i=0; i<headers.length; i++){
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1024);
            }

            // --- 엑셀 파일 다운로드 ---
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(res.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mng/exhibitor/utility/download.do", method = RequestMethod.GET)
    public void exhibitor_utility_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_utility_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // SXSSFWorkbook: 대용량 데이터 처리 시 메모리 부족 방지를 위한 스트리밍 방식
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            SXSSFSheet sheet = workbook.createSheet("유틸리티 신청 현황");

            sheet.trackAllColumnsForAutoSizing();

            // --- 폰트 및 스타일 정의 ---
            Font headerFont = workbook.createFont();
            headerFont.setFontName("맑은 고딕");
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);

            Font bodyFont = workbook.createFont();
            bodyFont.setFontName("맑은 고딕");
            bodyFont.setFontHeightInPoints((short) 10);

            CellStyle mainHeaderStyle = workbook.createCellStyle();
            mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
            mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            mainHeaderStyle.setBorderTop(BorderStyle.THIN); mainHeaderStyle.setBorderBottom(BorderStyle.THIN);
            mainHeaderStyle.setBorderLeft(BorderStyle.THIN); mainHeaderStyle.setBorderRight(BorderStyle.THIN);
            mainHeaderStyle.setFont(headerFont);

            CellStyle subHeaderStyle = workbook.createCellStyle();
            subHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            subHeaderStyle.setBorderTop(BorderStyle.THIN); subHeaderStyle.setBorderBottom(BorderStyle.THIN);
            subHeaderStyle.setBorderLeft(BorderStyle.THIN); subHeaderStyle.setBorderRight(BorderStyle.THIN);
            subHeaderStyle.setFont(headerFont);

            CellStyle bodyCenterStyle = workbook.createCellStyle();
            bodyCenterStyle.setFont(bodyFont);
            bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyCenterStyle.setBorderTop(BorderStyle.THIN); bodyCenterStyle.setBorderBottom(BorderStyle.THIN);
            bodyCenterStyle.setBorderLeft(BorderStyle.THIN); bodyCenterStyle.setBorderRight(BorderStyle.THIN);
            bodyCenterStyle.setWrapText(true);

            CellStyle bodyRightStyle = workbook.createCellStyle();
            bodyRightStyle.cloneStyleFrom(bodyCenterStyle);
            bodyRightStyle.setAlignment(HorizontalAlignment.RIGHT);

            // --- 1행: 메인 헤더 생성 및 셀 병합 ---
            Row mainHeaderRow = sheet.createRow(0);
            mainHeaderRow.createCell(0).setCellValue("기본정보");
            mainHeaderRow.createCell(5).setCellValue("유틸리티 신청");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4)); // A1~E1
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 8)); // F1~I1
            for(int i=0; i<9; i++){
                if(mainHeaderRow.getCell(i) == null) mainHeaderRow.createCell(i);
                mainHeaderRow.getCell(i).setCellStyle(mainHeaderStyle);
            }

            // --- 2행: 서브 헤더 생성 ---
            String[] headers = {"번호", "회사명", "담당자명", "직책", "연락처", "독립/조립", "총액(VAT별도)", "등록일시", "신청여부"};
            Row subHeaderRow = sheet.createRow(1);
            for(int i=0; i<headers.length; i++) {
                Cell cell = subHeaderRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(subHeaderStyle);
            }

            // --- 데이터 행 생성 ---
            // 데이터 조회
            List<UtilityDetailDTO> utilityList = kibsMngService.processSelectExcelUtilityDetailList(transferYear);
            int rowNum = 2; // 데이터는 3행부터 시작
            DecimalFormat df = new DecimalFormat("#,##0");

            if (utilityList != null && !utilityList.isEmpty()) {
                for (int i = 0; i < utilityList.size(); i++) {
                    UtilityDetailDTO util = utilityList.get(i);
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(i + 1);
                    row.createCell(1).setCellValue(util.getCompanyNameKo());
                    row.createCell(2).setCellValue(util.getRepresentativeName());
                    row.createCell(3).setCellValue(util.getRepresentativePosition());
                    row.createCell(4).setCellValue(util.getRepresentativePhone());

                    String originalBoothType = util.getBoothType();
                    String displayBoothType = "";
                    if (originalBoothType != null) {
                        displayBoothType = originalBoothType.replace("등록비", "").replace(" ", "");
                        if (displayBoothType.startsWith(",")) displayBoothType = displayBoothType.substring(1);
                        if (displayBoothType.endsWith(","))
                            displayBoothType = displayBoothType.substring(0, displayBoothType.length() - 1);
                    }
                    row.createCell(5).setCellValue(displayBoothType);

                    Integer utilityPrcSum = util.getUtilityPrcSum();
                    int sum = (utilityPrcSum != null) ? utilityPrcSum : 0;

                    row.createCell(6).setCellValue(df.format(sum) + " 원");
                    row.createCell(7).setCellValue(util.getInitRegiDttm() != null ? util.getInitRegiDttm().substring(0, 10) : "");
                    row.createCell(8).setCellValue(sum > 0 ? "신청" : "미신청");

                    for (int j = 0; j < headers.length; j++) {
                        Cell cell = row.getCell(j);
                        if (j == 6) { // 금액만 오른쪽 정렬
                            cell.setCellStyle(bodyRightStyle);
                        } else {
                            cell.setCellStyle(bodyCenterStyle);
                        }
                    }
                }
            }

            // --- 컬럼 너비 설정 ---
            for(int i=0; i<headers.length; i++){
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1024);
            }

            // --- 엑셀 파일 다운로드 ---
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(res.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mng/exhibitor/product/download.do", method = RequestMethod.GET)
    public void exhibitor_product_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > exhibitor_product_download");
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        // SXSSFWorkbook: 대용량 데이터 처리 시 메모리 부족 방지를 위한 스트리밍 방식
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            SXSSFSheet sheet = workbook.createSheet("전시품 신청");

            sheet.trackAllColumnsForAutoSizing();

            // --- 폰트 및 스타일 정의 ---
            Font headerFont = workbook.createFont();
            headerFont.setFontName("맑은 고딕");
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);

            Font bodyFont = workbook.createFont();
            bodyFont.setFontName("맑은 고딕");
            bodyFont.setFontHeightInPoints((short) 10);

            CellStyle mainHeaderStyle = workbook.createCellStyle();
            mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
            mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            mainHeaderStyle.setBorderTop(BorderStyle.THIN);
            mainHeaderStyle.setBorderBottom(BorderStyle.THIN);
            mainHeaderStyle.setBorderLeft(BorderStyle.THIN);
            mainHeaderStyle.setBorderRight(BorderStyle.THIN);
            mainHeaderStyle.setFont(headerFont);

            CellStyle subHeaderStyle = workbook.createCellStyle();
            subHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
            subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            subHeaderStyle.setBorderTop(BorderStyle.THIN);
            subHeaderStyle.setBorderBottom(BorderStyle.THIN);
            subHeaderStyle.setBorderLeft(BorderStyle.THIN);
            subHeaderStyle.setBorderRight(BorderStyle.THIN);
            subHeaderStyle.setFont(headerFont);
            subHeaderStyle.setWrapText(true);

            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setFont(bodyFont);
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setWrapText(true);

            // --- 헤더 생성 ---
            Row mainHeaderRow = sheet.createRow(0);
            Row subHeaderRow = sheet.createRow(1);

            // 1-1. 업체정보 헤더
            Cell companyMainCell = mainHeaderRow.createCell(0);
            companyMainCell.setCellValue("업체정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

            Cell companySubCell1 = subHeaderRow.createCell(0);
            companySubCell1.setCellValue("순번");
            Cell companySubCell2 = subHeaderRow.createCell(1);
            companySubCell2.setCellValue("업체명");

            // 1-2. 전시품 신청 헤더 (20개 반복)
            String[] productHeaders = {"제품분류(대분류)", "제품분류(소분류)", "제품명", "수량", "제조사(브랜드)", "길이(cm)", "너비(cm)", "높이(cm)", "중량(kg)", "소재", "연식"};
            for (int i = 0; i < 20; i++) {
                int startCol = 2 + (i * productHeaders.length);
                Cell productMainCell = mainHeaderRow.createCell(startCol);
                productMainCell.setCellValue("전시품 신청 " + (i + 1));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, startCol, startCol + productHeaders.length - 1));

                for (int j = 0; j < productHeaders.length; j++) {
                    Cell productSubCell = subHeaderRow.createCell(startCol + j);
                    productSubCell.setCellValue(productHeaders[j]);
                }
            }

            // 1-3. 생성된 모든 헤더 셀에 스타일 적용
            for (int i=0; i < subHeaderRow.getLastCellNum(); i++) {
                if (mainHeaderRow.getCell(i) != null) mainHeaderRow.getCell(i).setCellStyle(mainHeaderStyle);
                if (subHeaderRow.getCell(i) != null) subHeaderRow.getCell(i).setCellStyle(subHeaderStyle);
            }

            // --- 데이터 행 생성 ---
            // 데이터 조회
            List<ProductDetailDTO> rawData = kibsMngService.processSelectExcelProductDetailList(transferYear);

            // 업체별로 데이터를 그룹화 (Map<업체SEQ, 제품리스트>)
            Map<String, List<ProductDetailDTO>> groupedData = new LinkedHashMap<>();
            for (ProductDetailDTO dto : rawData) {
                groupedData.computeIfAbsent(dto.getExhibitorSeq(), k -> new ArrayList<>()).add(dto);
            }

            int rowNum = 2; // 데이터는 3행부터 시작
            int companyNum = 1;
            for (Map.Entry<String, List<ProductDetailDTO>> entry : groupedData.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                List<ProductDetailDTO> products = entry.getValue();

                // 업체정보 채우기
                row.createCell(0).setCellValue(companyNum++);
                row.createCell(1).setCellValue(products.get(0).getCompanyNameKo());

                // 전시품 신청 채우기 (최대 20개)
                for (int i = 0; i < products.size() && i < 20; i++) {
                    ProductDetailDTO product = products.get(i);
                    int startCol = 2 + (i * productHeaders.length);

                    // 제품 정보가 null이 아닌 경우에만 셀 생성 (제품이 없는 업체의 경우 빈 칸으로 남음)
                    if (product.getProductOptionBig() != null) {
                        row.createCell(startCol).setCellValue(product.getProductOptionBig());
                        row.createCell(startCol + 1).setCellValue(product.getProductOptionSmall());
                        row.createCell(startCol + 2).setCellValue(product.getProductNameKo());
                        row.createCell(startCol + 3).setCellValue(product.getProductQty() != null ? String.valueOf(product.getProductQty()) : "");
                        row.createCell(startCol + 4).setCellValue(product.getProductBrand());
                        row.createCell(startCol + 5).setCellValue(product.getProductLength() != null ? String.valueOf(product.getProductLength()) : "");
                        row.createCell(startCol + 6).setCellValue(product.getProductWidth() != null ? String.valueOf(product.getProductWidth()) : "");
                        row.createCell(startCol + 7).setCellValue(product.getProductHeight() != null ? String.valueOf(product.getProductHeight()) : "");
                        row.createCell(startCol + 8).setCellValue(product.getProductWeight() != null ? String.valueOf(product.getProductWeight()) : "");
                        row.createCell(startCol + 9).setCellValue(product.getProductMaterial());
                        row.createCell(startCol + 10).setCellValue(product.getProductYear() != null ? String.valueOf(product.getProductYear()) : "");
                    }
                }

                // 생성된 모든 데이터 셀에 스타일 적용
                for(int j=0; j < subHeaderRow.getLastCellNum(); j++){
                    if(row.getCell(j) == null) row.createCell(j);
                    row.getCell(j).setCellStyle(bodyStyle);
                }
            }

            // --- 컬럼 너비 설정 ---
            sheet.setColumnWidth(0, 2000); // 순번
            sheet.setColumnWidth(1, 8000); // 업체명
            for (int i = 0; i < 20; i++) {
                int startCol = 2 + (i * productHeaders.length);
                for (int j = 0; j < productHeaders.length; j++) {
                    sheet.setColumnWidth(startCol + j, 4000);
                }
            }

            // --- 엑셀 파일 다운로드 ---
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(res.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mng/admin/stat/download.do", method = RequestMethod.GET)
    public void mng_admin_stat_download(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("KibsMngController > mng_admin_stat_download");
        String fileName = req.getParameter("fileName");

        try {
            // 1. 다운로드 될 파일 이름 설정
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            // 2. HTTP Response Header 설정
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            // 3. Service 호출 (Response 객체를 직접 전달)
            kibsMngService.createAndDownloadProductExcel(res);

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            // 에러 페이지로 리다이렉트 하거나, 적절한 에러 응답을 보낼 수 있습니다.
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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

    public String nvl(String str, String repStr){
        String result = "";
        if(str == null || str.isEmpty()){
            result = repStr;
        }else{
            result = str;
        }
        return result;
    }

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/invoice/detail.do", method = RequestMethod.POST)
    public ModelAndView mng_exhibitorNew_company_invoice_detail(String seq) {
        System.out.println("KibsMngController > mng_exhibitorNew_company_invoice_detail");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();

        if(seq != null && !seq.isEmpty()){
            // 1. 참가업체 통합 정보 조회 (기존 로직)
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewInvoiceDetail(seq);
            mv.addObject("info", info);

            // 3. 해당 참가업체의 '전시부스 인보이스' 목록 조회
            List<InvoiceBoothDTO> boothInvoiceList  = kibsMngService.getInvoiceBoothList(seq);
            mv.addObject("boothInvoiceList", boothInvoiceList);

            // 4. 유틸리티 인보이스 목록 조회 로직 추가
            List<InvoiceUtilityDTO> utilityInvoiceList = kibsMngService.getInvoiceUtilityList(seq); // 이 Service 메소드를 새로 만들어야 합니다.
            mv.addObject("utilityInvoiceList", utilityInvoiceList);
        }

        mv.setViewName("/mng/exhibitor/participant/company/invoice/detail");
        return mv;
    }

    /**
     * 생성된 PDF 파일 경로를 DB에 업데이트하는 API
     * - JavaScript의 createAndUploadPdfFromIframe 함수 성공 후 호출될 것으로 예상
     */
    @PostMapping("/mng/exhibitorNew/application/invoice/updateFilePath.do")
    @ResponseBody
    public Map<String, Object> updateInvoiceFilePath(@RequestBody InvoiceBoothDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean isSuccess = kibsMngService.updateInvoiceBoothFilePath(dto);
            if (isSuccess) {
                resultMap.put("resultCode", "0");
                resultMap.put("resultMsg", "파일 경로가 업데이트되었습니다.");
            } else {
                resultMap.put("resultCode", "-1");
                resultMap.put("resultMsg", "파일 경로 업데이트에 실패했습니다.");
            }
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "오류가 발생했습니다.");
        }
        return resultMap;
    }

    /**
     * 선택된 인보이스들을 삭제하는 API
     */
    @PostMapping("/mng/exhibitorNew/application/invoice/booth/delete.do")
    @ResponseBody
    public Map<String, Object> deleteInvoicesBooth(@RequestBody List<Integer> invoiceSeqList) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            for (int invoiceSeq : invoiceSeqList) {
                kibsMngService.deleteInvoiceBooth(invoiceSeq);
            }
            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "선택한 인보이스가 삭제되었습니다.");
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "삭제 중 오류가 발생했습니다.");
        }
        return resultMap;
    }

    @PostMapping("/mng/exhibitorNew/application/invoice/utility/delete.do")
    @ResponseBody
    public Map<String, Object> deleteInvoicesUtility(@RequestBody List<Integer> invoiceSeqList) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            for (int invoiceSeq : invoiceSeqList) {
                kibsMngService.deleteInvoiceUtility(invoiceSeq);
            }
            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "선택한 인보이스가 삭제되었습니다.");
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "삭제 중 오류가 발생했습니다.");
        }
        return resultMap;
    }

    @RequestMapping(value = "/mng/exhibitorNew/participant/company/invoice/saveSpecialDiscount.do", method = RequestMethod.POST)
    @ResponseBody // AJAX 통신을 위해 ResponseBody 추가
    public Map<String, Object> mng_exhibitorNew_saveSpecialDiscount(@RequestBody ExhibitorNewDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 특별 할인 정보 업데이트 서비스 호출
            int result = kibsMngService.processUpdateExhibitorNewSpecialDiscount(dto);
            if (result > 0) {
                resultMap.put("resultCode", "0");
                resultMap.put("resultMsg", "저장되었습니다.");
            } else {
                resultMap.put("resultCode", "-1");
                resultMap.put("resultMsg", "저장에 실패했습니다.");
            }
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "오류가 발생했습니다: " + e.getMessage());
        }
        return resultMap;
    }

    /**
     * 전시부스 인보이스 생성 API
     * - Service를 호출하여 인보이스 데이터를 생성하고, 생성된 인보이스의 PK(invoiceSeq)를 반환합니다.
     */
    @PostMapping("/mng/exhibitorNew/application/booth/invoice/insert.do")
    @ResponseBody
    public Map<String, Object> createBoothInvoice(@RequestBody Map<String, String> payload) {
        Map<String, Object> resultMap = new HashMap<>();
        String exhibitorSeq = payload.get("exSeq");

        try {
            InvoiceBoothDTO newInvoice = kibsMngService.createAndInsertInvoiceBooth(exhibitorSeq);
            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "인보이스가 생성되었습니다.");
            resultMap.put("customValue", newInvoice.getInvoiceSeq()); // JavaScript에서 사용할 수 있도록 PK 반환
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "인보이스 생성 중 오류 발생: " + e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/mng/exhibitorNew/application/utility/invoice/insert.do")
    @ResponseBody
    public Map<String, Object> createUtilityInvoice(@RequestBody Map<String, String> payload) {
        Map<String, Object> resultMap = new HashMap<>();
        String exhibitorSeq = payload.get("exSeq");

        try {
            InvoiceUtilityDTO newInvoice = kibsMngService.createAndInsertInvoiceUtility(exhibitorSeq);
            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "유틸리티 인보이스가 생성되었습니다.");
            resultMap.put("customValue", newInvoice.getInvoiceSeq());
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "유틸리티 인보이스 생성 중 오류 발생: " + e.getMessage());
        }
        return resultMap;
    }

    /**
     * 선택된 인보이스들을 이메일로 발송하는 API
     */
    @PostMapping("/mng/exhibitorNew/application/invoice/send.do")
    @ResponseBody
    public Map<String, Object> sendInvoices(@RequestBody List<Integer> invoiceSeqList) {
        // String adminId = (String) session.getAttribute("adminId");
        String adminId = "admin"; // 임시 관리자 ID
        try {
            return kibsMngService.sendInvoices(invoiceSeqList, adminId);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("resultCode", "-1");
            errorMap.put("resultMsg", "발송 중 오류가 발생했습니다: " + e.getMessage());
            return errorMap;
        }
    }

    /**
     * [AJAX 용] 실시간 금액 계산 미리보기 (관리자용)
     * @param input (JS에서 보낸 모든 입력값)
     * @return CalculationResultDTO (JSON)
     */
    @PostMapping("/mng/calculate-preview.do")
    @ResponseBody
    public CalculationResultDTO getMngCalculationPreview(@RequestBody CalculationInputDTO input) {
        // JS에서 보낸 입력값(input)을 기반으로 즉시 계산하여 결과만 반환
        // (DB 저장은 없음)
        return calculationService.calculateTotals(input);
    }
}