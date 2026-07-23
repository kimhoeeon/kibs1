package com.mtf.kibs.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.service.CalculationService;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.service.KibsService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class KibsEnController {

    @Autowired
    private CalculationService calculationService; // 1. 공통 계산 서비스 주입

    private final KibsService kibsService;

    private final CommService commService;

    private final String transferYear = "2027";

    public KibsEnController(KibsService ks, CommService cs){
        this.kibsService = ks;
        this.commService = cs;
    }

    private String getSessionTransferYear(HttpSession session) {
        String sessionYear = (String) session.getAttribute("transferYear");
        return (sessionYear != null && !sessionYear.trim().isEmpty() && !"null".equals(sessionYear)) ? sessionYear : this.transferYear;
    }

    //***************************************************************************
    // Eng page
    //***************************************************************************

    @RequestMapping(value = "/eng/index.do", method = RequestMethod.GET)
    public ModelAndView eng_index() {
        //System.out.println("KibsController > eng_index");
        ModelAndView mv = new ModelAndView();
        String lang = "EN";

        /* 팝업파일정보 */
        PopupDTO popupDTO = new PopupDTO();
        popupDTO.setLang(lang);
        popupDTO.setUseYn("Y");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = dateFormat.format(new Date());
        popupDTO.setToday(today);
        List<PopupDTO> popupList = kibsService.processSelectPopupList(popupDTO);
        mv.addObject("popupList", popupList);

        /* 공지사항 */
        List<NoticeDTO> mainNoticeList = kibsService.processSelectMainNoticeList(lang);
        mv.addObject("mainNoticeList", mainNoticeList);

        /* KIBS Tube */
        List<KibstvDTO> mainKibstvList = kibsService.processSelectMainKibstvList(lang);
        if(!mainKibstvList.isEmpty()){
            List<KibstvDTO> mainKibstvKeyList = new ArrayList<>();
            for(KibstvDTO kibstv : mainKibstvList){
                String kibsUrl = kibstv.getYoutubeUrl();
                if(kibsUrl.contains("/shorts/")){
                    String shortsKey = "";
                    if(kibsUrl.contains("?")){
                        shortsKey = kibsUrl.substring(kibsUrl.indexOf("/shorts/") + 8, kibsUrl.indexOf("?"));
                    }else{
                        shortsKey = kibsUrl.substring(kibsUrl.indexOf("/shorts/") + 8);
                    }
                    KibstvDTO kibsShortsDto = new KibstvDTO();
                    kibsShortsDto.setId(kibstv.getId());
                    kibsShortsDto.setTitle(kibstv.getTitle());
                    kibsShortsDto.setYoutubeUrl(shortsKey);
                    mainKibstvKeyList.add(kibsShortsDto);
                }else{
                    mainKibstvKeyList.add(kibstv);
                }
            }
            mv.addObject("mainKibstvList", mainKibstvKeyList);
        }

        // 브로슈어
        String currentYear = "2027"; // 현재 대상 연도
        BrochureDTO brochureData = kibsService.getBrochureInfo(currentYear);

        if (brochureData == null) {
            brochureData = new BrochureDTO();
        }

        mv.addObject("brochureData", brochureData);

        mv.setViewName("/eng/index");
        return mv;
    }

    @RequestMapping(value = "/eng/login.do", method = RequestMethod.GET)
    public ModelAndView eng_login() {
        //System.out.println("KibsController > eng_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/login");
        return mv;
    }

    //***************************************************************************
    // Eng guide page
    //***************************************************************************

    @RequestMapping(value = "/eng/guide/summary.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_summary() {
        //System.out.println("KibsController > eng_guide_summary");
        ModelAndView mv = new ModelAndView();

        // 브로슈어
        String currentYear = "2027"; // 현재 대상 연도
        BrochureDTO brochureData = kibsService.getBrochureInfo(currentYear);

        if (brochureData == null) {
            brochureData = new BrochureDTO();
        }

        mv.addObject("brochureData", brochureData);

        mv.setViewName("/eng/guide/summary");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/venue.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_venue() {
        //System.out.println("KibsController > eng_guide_venue");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/venue");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/to_kintex.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_to_kintex() {
        //System.out.println("KibsController > eng_guide_to_kintex");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/to_kintex");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/to_ara.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_to_ara() {
        //System.out.println("KibsController > eng_guide_to_ara");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/to_ara");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/bi.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_bi() {
        //System.out.println("KibsController > eng_guide_bi");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/bi");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/brochure.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_brochure() {
        //System.out.println("KibsController > eng_guide_brochure");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/brochure");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/certify.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_certify() {
        //System.out.println("KibsController > eng_guide_certify");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/certify");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/contact.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_contact() {
        //System.out.println("KibsController > eng_guide_contact");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/contact");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/program.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_program() {
        //System.out.println("KibsController > eng_guide_program");
        ModelAndView mv = new ModelAndView();

        /*DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String today = dateFormat.format(new Date());
        if (Long.parseLong(today) > Long.parseLong("202602130859")) {
            mv.addObject("mode", "open");
        }*/

        mv.setViewName("/eng/guide/program");
        return mv;
    }
    
    // 최미나가 추가함
    @RequestMapping(value = "/eng/guide/program2025.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_program2025() {
        //System.out.println("KibsController > eng_guide_program2025");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/program2025");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/conference.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_conference() {
        //System.out.println("KibsController > eng_guide_conference");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/conference");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/conference2025.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_conference2025() {
        //System.out.println("KibsController > eng_guide_conference2025");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/conference2025");
        return mv;
    }

    //***************************************************************************
    // Eng exhibitor page
    //***************************************************************************

    @RequestMapping(value = "/eng/exhibitor/categories.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_categories() {
        //System.out.println("KibsController > eng_exhibitor_categories");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/categories");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/glance.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_glance() {
        //System.out.println("KibsController > eng_exhibitor_glance");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/glance");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/marina.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_marina() {
        //System.out.println("KibsController > eng_exhibitor_marina");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/marina");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/match.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_match() {
        //System.out.println("KibsController > eng_exhibitor_match");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/match");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list() {
        //System.out.println("KibsController > eng_exhibitor_list");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/qna.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_qna() {
        //System.out.println("KibsController > eng_exhibitor_qna");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/qna");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<EnExhibitorListDTO>> eng_exhibitor_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsController > eng_exhibitor_selectList");
        //System.out.println(searchDTO.getPageNum());
        //System.out.println(searchDTO.getRows());

        List<EnExhibitorListDTO> responseList = kibsService.processSelectEnExhibitorList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/eng/exhibitor/privacy.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_privacy() {
        //System.out.println("KibsController > eng_exhibitor_privacy");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/privacy");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/login.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_login() {
        //System.out.println("KibsController > eng_exhibitor_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/login");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/findpw.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_findpw() {
        //System.out.println("KibsController > eng_exhibitor_findpw");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/findpw");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list22.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list22() {
        //System.out.println("KibsController > eng_exhibitor_list22");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list22");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list19.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list19() {
        //System.out.println("KibsController > eng_exhibitor_list19");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list19");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list18.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list18() {
        //System.out.println("KibsController > eng_exhibitor_list18");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list18");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list17.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list17() {
        //System.out.println("KibsController > eng_exhibitor_list17");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list17");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list16.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list16() {
        //System.out.println("KibsController > eng_exhibitor_list16");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list16");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list15.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list15() {
        //System.out.println("KibsController > eng_exhibitor_list15");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list15");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list14.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list14() {
        //System.out.println("KibsController > eng_exhibitor_list14");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list14");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list13.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list13() {
        //System.out.println("KibsController > eng_exhibitor_list13");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list13");
        return mv;
    }

    //***************************************************************************
    // Eng apply page
    //***************************************************************************

    @RequestMapping(value = "/eng/apply/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step01(String mode, ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step01");
        ModelAndView mv = new ModelAndView();

        if(mode != null && !mode.isEmpty()){
            mv.addObject("mode", mode);
        }else {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String today = dateFormat.format(new Date());
            if (Long.parseLong(today) > Long.parseLong("202510200859")) {
                if (Long.parseLong(today) > Long.parseLong("202602272359")) {
                    mv.addObject("mode", "close");
                } else {
                    mv.addObject("mode", "open");
                }
            }else{
                mv.addObject("mode", "soon");
            }
        }

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            if(id != null && !id.equals("null") && !id.isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(getSessionTransferYear(session));
                exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
                if (exInfo != null) {
                    exhibitor_new_seq = exInfo.getSeq();
                }
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);

            /* 부담당자 정보*/
            ChargeNewDTO chargeNewReq = new ChargeNewDTO();
            chargeNewReq.setExSeq(exhibitor_new_seq);
            List<ChargeNewDTO> chargeList = kibsService.processSelectChargeNewList(chargeNewReq);
            mv.addObject("chargeList", chargeList);

            /* 온라인정보 */
            OnlineNewDTO onlineNewReq = new OnlineNewDTO();
            onlineNewReq.setExSeq(exhibitor_new_seq);
            List<OnlineNewDTO> onlineList = kibsService.processSelectOnlineNewList(onlineNewReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerNewDTO buyerNewReq = new BuyerNewDTO();
            buyerNewReq.setExSeq(exhibitor_new_seq);
            List<BuyerNewDTO> buyerList = kibsService.processSelectBuyerNewList(buyerNewReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_new_seq);
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

        mv.setViewName("/eng/apply/step01");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_1.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_1(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_1");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }else{
            session.invalidate(); //세션 초기화
        }
        mv.setViewName("/eng/apply/step2_1");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_10.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_10(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_10");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/eng/apply/step2_10");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_9.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_9(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_9");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);

            /* 요트/보트 출품 정보 */
            ProductNewDTO productNewReq = new ProductNewDTO();
            productNewReq.setExSeq(exhibitor_new_seq);
            List<ProductNewDTO> productList = kibsService.processSelectProductNewList(productNewReq);
            mv.addObject("productList", productList);

        }else{
            session.invalidate(); //세션 초기화
        }
        mv.setViewName("/eng/apply/step2_9");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_2(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_2");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/eng/apply/step2_2");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_3.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_3(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_3");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/eng/apply/step2_3");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_4.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_4(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_4");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        List<PassNewDTO> passList = new ArrayList<>();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            PassNewDTO passNewDTO = new PassNewDTO();
            passNewDTO.setExSeq(exhibitor_new_seq);
            passList = kibsService.processSelectPassNewList(passNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));

            ExhibitorNewDTO exDTO = new ExhibitorNewDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(getSessionTransferYear(session));
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            PassNewDTO reqDTO = new PassNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            passList = kibsService.processSelectPassNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("passList", passList);
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            ExhibitorNewDTO exhibitorInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }

        mv.setViewName("/eng/apply/step2_4");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_5.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_5(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_5");

        ModelAndView mv = new ModelAndView();
        /* 기본정보 - 참가업체 정보 */
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        List<GiftNewDTO> giftList = new ArrayList<>();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            GiftNewDTO giftNewDTO = new GiftNewDTO();
            giftNewDTO.setExSeq(exhibitor_new_seq);
            giftList = kibsService.processSelectGiftNewList(giftNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));

            ExhibitorNewDTO exDTO = new ExhibitorNewDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(getSessionTransferYear(session));
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            GiftNewDTO reqDTO = new GiftNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            giftList = kibsService.processSelectGiftNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("giftList", giftList);
        }

        mv.setViewName("/eng/apply/step2_5");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_6.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_6() {
        //System.out.println("KibsController > eng_apply_step2_6");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_6");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_7.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_7() {
        //System.out.println("KibsController > eng_apply_step2_7");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_7");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_8.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step2_8(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step2_8");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            if(id != null && !id.equals("null") && !id.isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(getSessionTransferYear(session));
                exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
                if (exInfo != null) {
                    exhibitor_new_seq = exInfo.getSeq();
                }
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);

            /* 부담당자 정보*/
            ChargeNewDTO chargeNewReq = new ChargeNewDTO();
            chargeNewReq.setExSeq(exhibitor_new_seq);
            List<ChargeNewDTO> chargeList = kibsService.processSelectChargeNewList(chargeNewReq);
            mv.addObject("chargeList", chargeList);

            /* 요트/보트 출품 정보 */
            ProductNewDTO productNewReq = new ProductNewDTO();
            productNewReq.setExSeq(exhibitor_new_seq);
            List<ProductNewDTO> productList = kibsService.processSelectProductNewList(productNewReq);
            mv.addObject("productList", productList);

            /* 온라인정보 */
            OnlineNewDTO onlineNewReq = new OnlineNewDTO();
            onlineNewReq.setExSeq(exhibitor_new_seq);
            List<OnlineNewDTO> onlineList = kibsService.processSelectOnlineNewList(onlineNewReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerNewDTO buyerNewReq = new BuyerNewDTO();
            buyerNewReq.setExSeq(exhibitor_new_seq);
            List<BuyerNewDTO> buyerList = kibsService.processSelectBuyerNewList(buyerNewReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_new_seq);
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

        mv.setViewName("/eng/apply/step2_8");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step03.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step03(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > apply_step03");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/eng/apply/step03");
        return mv;
    }

    //***************************************************************************
    // Eng buyer page
    //***************************************************************************

    @RequestMapping(value = "/eng/buyer/apply.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_apply(String mode) {
        //System.out.println("KibsController > visitor_apply");
        ModelAndView mv = new ModelAndView();
        if(mode != null && !mode.isEmpty()){
            mv.addObject("mode", mode);
        }else {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String today = dateFormat.format(new Date());
            if (Long.parseLong(today) > Long.parseLong("202601010859")) {
                if (Long.parseLong(today) > Long.parseLong("202603032359")) {
                    mv.addObject("mode", "close");
                }else{
                    mv.addObject("mode", "open");
                }
            }else{
                mv.addObject("mode", "soon");
            }
        }
        mv.setViewName("/eng/buyer/apply");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/completed.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_completed() {
        //System.out.println("KibsController > eng_buyer_completed");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/completed");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/glance.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_glance() {
        //System.out.println("KibsController > eng_buyer_glance");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/glance");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/marina.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_marina() {
        //System.out.println("KibsController > eng_buyer_marina");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/marina");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/match.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_match() {
        //System.out.println("KibsController > eng_buyer_match");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/match");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/login.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_login() {
        //System.out.println("KibsController > eng_buyer_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/login");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/mypage.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_mypage(String seq) {
        //System.out.println("KibsController > eng_buyer_mypage");
        ModelAndView mv = new ModelAndView();
        VisitorDTO info = kibsService.processSelectVisitorSingle(seq);

        if(info != null){
            mv.addObject("info", info);
            List<PartnerDTO> partnerList = kibsService.processSelectPartnerList(info.getSeq());
            mv.addObject("partnerList", partnerList);
        }
        mv.setViewName("/eng/buyer/mypage");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/qna.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_qna() {
        //System.out.println("KibsController > eng_buyer_qna");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/qna");
        return mv;
    }

    //***************************************************************************
    // Eng board page
    //***************************************************************************

    @RequestMapping(value = "/eng/board/notice.do", method = RequestMethod.GET)
    public ModelAndView eng_board_notice() {
        //System.out.println("KibsController > eng_board_notice");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/notice");
        return mv;
    }

    @RequestMapping(value = "/eng/board/kibstv.do", method = RequestMethod.GET)
    public ModelAndView eng_board_kibstv() {
        //System.out.println("KibsController > eng_board_kibstv");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/kibstv");
        return mv;
    }

    @RequestMapping(value = "/eng/board/kibstv_cate.do", method = RequestMethod.GET)
    public ModelAndView eng_board_kibstv_cate() {
        //System.out.println("KibsController > eng_board_kibstv_cate");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/kibstv_cate");
        return mv;
    }

    @RequestMapping(value = "/eng/board/media.do", method = RequestMethod.GET)
    public ModelAndView eng_board_media() {
        //System.out.println("KibsController > eng_board_media");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/media");
        return mv;
    }

    @RequestMapping(value = "/eng/board/gallery.do", method = RequestMethod.GET)
    public ModelAndView eng_board_gallery() {
        //System.out.println("KibsController > eng_board_gallery");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/gallery");
        return mv;
    }

    @RequestMapping(value = "/eng/board/newsletter.do", method = RequestMethod.GET)
    public ModelAndView eng_board_newsletter() {
        //System.out.println("KibsController > eng_board_newsletter");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/newsletter");
        return mv;
    }

    @RequestMapping(value = "/eng/board/mediaView.do", method = RequestMethod.GET)
    public ModelAndView eng_board_mediaView() {
        //System.out.println("KibsController > eng_board_mediaView");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/mediaView");
        return mv;
    }

    @RequestMapping(value = "/eng/board/newsletterView.do", method = RequestMethod.GET)
    public ModelAndView eng_board_newsletterView() {
        //System.out.println("KibsController > eng_board_newsletterView");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/newsletterView");
        return mv;
    }

    @RequestMapping(value = "/eng/board/noticeView.do", method = RequestMethod.GET)
    public ModelAndView eng_board_noticeView(String id) {
        //System.out.println("KibsController > eng_board_noticeView");
        //System.out.println(id);
        ModelAndView mv = new ModelAndView();

        /* 조회 카운트 Update */
        kibsService.processUpdateNoticeViewCnt(id);

        /* 데이터 조회 후 Set */
        NoticeDTO noticeInfo = kibsService.processSelectNoticeSingle(id);

        if(noticeInfo != null){

            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setLang("EN");
            List<NoticeDTO> responseList = kibsService.processSelectNoticeList(searchDTO);
            for(NoticeDTO responseInfo: responseList){
                if(responseInfo.getId().equals(id)){
                    noticeInfo.setPrevId(responseInfo.getPrevId());
                    noticeInfo.setNextId(responseInfo.getNextId());
                }
            }
            mv.addObject("noticeInfo", noticeInfo);

            /* 첨부파일 정보 Set */
            List<FileDTO> fileList = kibsService.processSelectFileList(noticeInfo.getId());
            if(fileList != null && !fileList.isEmpty()){
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/eng/board/noticeView");
        return mv;
    }

    //***************************************************************************
    // Eng mypage page
    //***************************************************************************

    @RequestMapping(value = "/eng/mypage/index.do", method = RequestMethod.POST)
    public ModelAndView eng_mypage_index(ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsController > mypage_index");
        //System.out.println(exhibitorDTO.getSeq() + " / " + exhibitorDTO.getId() + " / " + exhibitorDTO.getTransferYear() );
        ModelAndView mv = new ModelAndView();
        ExhibitorNewDTO info = kibsService.processSelectExhibitorNewPrc(exhibitorNewDTO);
        mv.addObject("info", info);
        mv.setViewName("/eng/mypage/index");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_modify(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_modify");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/eng/mypage/modify");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step01(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step01");
        ModelAndView mv = new ModelAndView();

        // 세션 ID로만 사용자 정보를 가져옵니다.
        String id = (String) session.getAttribute("id");
        if (id == null) {
            mv.setViewName("redirect:/eng/index.do");
            return mv;
        }else{
            /* 기본정보 - 참가업체 정보 */
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            ExhibitorNewDTO exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                String exhibitor_new_seq = exInfo.getSeq();

                mv.addObject("info", exInfo);

                /* 부담당자 정보*/
                ChargeNewDTO chargeNewReq = new ChargeNewDTO();
                chargeNewReq.setExSeq(exhibitor_new_seq);
                List<ChargeNewDTO> chargeList = kibsService.processSelectChargeNewList(chargeNewReq);
                mv.addObject("chargeList", chargeList);

                /* 온라인정보 */
                OnlineNewDTO onlineNewReq = new OnlineNewDTO();
                onlineNewReq.setExSeq(exhibitor_new_seq);
                List<OnlineNewDTO> onlineList = kibsService.processSelectOnlineNewList(onlineNewReq);
                mv.addObject("onlineList", onlineList);

                /* 바이어정보 */
                BuyerNewDTO buyerNewReq = new BuyerNewDTO();
                buyerNewReq.setExSeq(exhibitor_new_seq);
                List<BuyerNewDTO> buyerList = kibsService.processSelectBuyerNewList(buyerNewReq);
                mv.addObject("buyerList", buyerList);

                /* 파일정보 */
                List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_new_seq);
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
            }else{
                session.invalidate(); //세션 초기화
            }

            mv.setViewName("/eng/mypage/step01");
        }

        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_1.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step2_1(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_1");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/eng/mypage/step2_1");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_9.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_9(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_9");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);

            /* 요트/보트 출품 정보 */
            ProductNewDTO productNewReq = new ProductNewDTO();
            productNewReq.setExSeq(exhibitor_new_seq);
            List<ProductNewDTO> productList = kibsService.processSelectProductNewList(productNewReq);
            mv.addObject("productList", productList);

        }else{
            session.invalidate(); //세션 초기화
        }
        mv.setViewName("/eng/mypage/step2_9");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step2_2(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_2");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/eng/mypage/step2_2");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_3.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step2_3(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_3");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/eng/mypage/step2_3");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_4.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step2_4(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_4");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        List<PassNewDTO> passList = new ArrayList<>();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            PassNewDTO passNewDTO = new PassNewDTO();
            passNewDTO.setExSeq(exhibitor_new_seq);
            passList = kibsService.processSelectPassNewList(passNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));

            ExhibitorNewDTO exDTO = new ExhibitorNewDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(getSessionTransferYear(session));
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            PassNewDTO reqDTO = new PassNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            passList = kibsService.processSelectPassNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("passList", passList);
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            ExhibitorNewDTO exhibitorInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/eng/mypage/step2_4");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_5.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step2_5(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_5");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        List<GiftNewDTO> giftList = new ArrayList<>();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            GiftNewDTO giftDTO = new GiftNewDTO();
            giftDTO.setExSeq(exhibitor_new_seq);
            giftList = kibsService.processSelectGiftNewList(giftDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));

            ExhibitorNewDTO exDTO = new ExhibitorNewDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(getSessionTransferYear(session));
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            GiftNewDTO reqDTO = new GiftNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            giftList = kibsService.processSelectGiftNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("giftList", giftList);
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            ExhibitorNewDTO exhibitorInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/eng/mypage/step2_5");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_6.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_6() {
        //System.out.println("KibsController > eng_mypage_step2_6");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_6");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_7.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_7() {
        //System.out.println("KibsController > eng_mypage_step2_7");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_7");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_8.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_mypage_step2_8(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        //System.out.println("KibsController > mypage_step2_8");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(getSessionTransferYear(session));
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            if(id != null && !id.equals("null") && !id.isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(getSessionTransferYear(session));
                exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
                if (exInfo != null) {
                    exhibitor_new_seq = exInfo.getSeq();
                }
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);

            /* 부담당자 정보*/
            ChargeNewDTO chargeNewReq = new ChargeNewDTO();
            chargeNewReq.setExSeq(exhibitor_new_seq);
            List<ChargeNewDTO> chargeList = kibsService.processSelectChargeNewList(chargeNewReq);
            mv.addObject("chargeList", chargeList);

            /* 요트/보트 출품 정보 */
            ProductNewDTO productNewReq = new ProductNewDTO();
            productNewReq.setExSeq(exhibitor_new_seq);
            List<ProductNewDTO> productList = kibsService.processSelectProductNewList(productNewReq);
            mv.addObject("productList", productList);

            /* 온라인정보 */
            OnlineNewDTO onlineNewReq = new OnlineNewDTO();
            onlineNewReq.setExSeq(exhibitor_new_seq);
            List<OnlineNewDTO> onlineList = kibsService.processSelectOnlineNewList(onlineNewReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerNewDTO buyerNewReq = new BuyerNewDTO();
            buyerNewReq.setExSeq(exhibitor_new_seq);
            List<BuyerNewDTO> buyerList = kibsService.processSelectBuyerNewList(buyerNewReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_new_seq);
            List<FileDTO> productImageFileList = new ArrayList<>();
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
                    case "productImage":
                        productImageFileList.add(fileInfo);
                        break;
                    case "onlineImage":
                        onlineImageFileList.add(fileInfo);
                        break;
                    default:
                        break;
                }
            }
            mv.addObject("productImageFileList", productImageFileList);
            mv.addObject("onlineImageFileList", onlineImageFileList);
        }

        mv.setViewName("/eng/mypage/step2_8");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step03.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step03() {
        //System.out.println("KibsController > eng_mypage_step03");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step03");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/total.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_total() {
        //System.out.println("KibsController > eng_mypage_total");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/total");
        return mv;
    }

    //***************************************************************************
    // Online
    //***************************************************************************

    @RequestMapping(value = "/eng/online/company.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_online_company(HttpServletRequest request, String companyNameEn) {
        //System.out.println("KibsController > eng_online_company");
        ModelAndView mv = new ModelAndView();
        if("POST".equals(request.getMethod())){
            mv.addObject("companyNameEn", companyNameEn);
        }
        mv.setViewName("/eng/online/company");
        return mv;
    }

    @RequestMapping(value = "/eng/online/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MainOnlineDTO>> eng_online_company_selectList(@RequestBody MainOnlineDTO mainOnlineDTO) {
        //System.out.println("KibsController > eng_online_company_selectList");
        //System.out.println(mainOnlineDTO.toString());

        List<MainOnlineDTO> responseDTO = kibsService.processSelectEnOnlineListPaging(mainOnlineDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/eng/online/company_view.do", method = RequestMethod.GET)
    public ModelAndView eng_online_company_view(String seq) {
        //System.out.println("KibsController > eng_online_company_view");
        ModelAndView mv = new ModelAndView();

        if(seq != null && !seq.isEmpty()) {
            if (seq.contains("EN")) {
                mv.addObject("gbn", "EN");

                ExhibitorNewDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorNewInfo(seq);
                mv.addObject("exhibitorInfo", exhibitorInfo);

                /*List<ProductNewDTO> productList = kibsService.processSelectProductNewInfoList(seq);
                for(ProductNewDTO productInfo : productList){
                    productInfo.setProductOptionBig(convertOptionBig(productInfo.getProductOptionBig()));
                    productInfo.setProductOptionSmall(convertOptionSmall(productInfo.getProductOptionSmall()));
                }
                mv.addObject("productList", productList);*/

                List<OnlineNewDTO> onlineList = kibsService.processSelectOnlineNewInfoList(seq);
                for(OnlineNewDTO onlineInfo : onlineList){
                    onlineInfo.setOnlineOptionBig(convertOptionBig(onlineInfo.getOnlineOptionBig()));
                    onlineInfo.setOnlineOptionSmall(convertOptionSmall(onlineInfo.getOnlineOptionSmall()));
                }
                mv.addObject("onlineList", onlineList);
            } else {
                mv.addObject("gbn", "E");

                ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(seq);
                mv.addObject("exhibitorInfo", exhibitorInfo);

                List<OnlineDTO> onlineList = kibsService.processSelectOnlineInfoList(seq);
                for(OnlineDTO onlineInfo : onlineList){
                    onlineInfo.setProductOptionBig(convertOptionBig(onlineInfo.getProductOptionBig()));
                    onlineInfo.setProductOptionSmall(convertOptionSmall(onlineInfo.getProductOptionSmall()));
                }
                mv.addObject("onlineList", onlineList);
            }

            List<FileDTO> fileList = kibsService.processSelectFileList(seq);
            mv.addObject("fileList", fileList);
        }

        mv.setViewName("/eng/online/company_view");
        return mv;
    }

    @RequestMapping(value = "/eng/online/product.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_online_product(HttpServletRequest request, OnlineRelatedDTO onlineRelatedDTO) {
        //System.out.println("KibsController > eng_online_product");
        //System.out.println(onlineRelatedDTO.toString());

        ModelAndView mv = new ModelAndView();
        if("POST".equals(request.getMethod())){
            mv.addObject("productOptionInfo", onlineRelatedDTO);
        }
        mv.setViewName("/eng/online/product");
        return mv;
    }

    @RequestMapping(value = "/eng/online/product/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> eng_online_product_selectList(@RequestBody OnlineRelatedDTO onlineRelatedDTO) {
        //System.out.println("KibsController > eng_online_product_selectList");
        //System.out.println(onlineRelatedDTO.toString());

        //List<FileDTO> productFileList = new ArrayList<>();
        List<OnlineRelatedDTO> relatedItemList = kibsService.processSelectOnlineProductListPaging(onlineRelatedDTO);
        for (OnlineRelatedDTO relatedDTO : relatedItemList) {
            FileDTO relatedItemFileRequestDto = new FileDTO();
            relatedItemFileRequestDto.setUserId(relatedDTO.getId());
            relatedItemFileRequestDto.setNote(relatedDTO.getNote());

            List<FileDTO> productFileResponseDto = kibsService.processSelectProductFileInfo(relatedItemFileRequestDto);
            if (productFileResponseDto != null && !productFileResponseDto.isEmpty()) {
                relatedDTO.setProductFullFilePath(productFileResponseDto.get(0).getFullFilePath());
            }

            FileDTO logoFileResponseDto = kibsService.processSelectLogoFileInfo(relatedItemFileRequestDto);
            if (logoFileResponseDto != null) {
                relatedDTO.setLogoFullFilePath(logoFileResponseDto.getFullFilePath());
            }

            //productFileList.add(relatedItemFileResponseDto);
        }

        Gson gson = new Gson();
        JsonElement relatedItemListElement = gson.toJsonTree(relatedItemList, new TypeToken<List<OnlineRelatedDTO>>() {}.getType());
        JsonArray relatedItemListJsonArray = relatedItemListElement.getAsJsonArray();
        //JsonElement productFileListElement = gson.toJsonTree(productFileList, new TypeToken<List<FileDTO>>() {}.getType());
        //JsonArray productFileListJsonArray = productFileListElement.getAsJsonArray();

        JsonObject responseJson = new JsonObject();
        responseJson.add("relatedItemList", relatedItemListJsonArray);
        //responseJson.add("productFileList", productFileListJsonArray);

        return new ResponseEntity<>(responseJson.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/eng/online/product_view.do", method = RequestMethod.GET)
    public ModelAndView eng_online_product_view(String seq) {
        //System.out.println("KibsController > eng_online_product_view");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();
        if (seq != null && !seq.isEmpty()) {
            if(seq.contains("PN")){
                mv.addObject("gbn", "PN");

                ProductNewDTO productInfo = kibsService.processSelectProductNewInfo(seq);
                productInfo.setProductOptionBig(convertOptionBig(productInfo.getProductOptionBig()));
                productInfo.setProductOptionSmall(convertOptionSmall(productInfo.getProductOptionSmall()));
                mv.addObject("productInfo", productInfo);

                if (productInfo != null) {
                    String exSeq = productInfo.getExSeq();
                    List<FileDTO> fileList = kibsService.processSelectFileList(exSeq);
                    mv.addObject("fileList", fileList);

                    ExhibitorNewDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorNewInfo(exSeq);
                    mv.addObject("exhibitorInfo", exhibitorInfo);

                    /* 같은 회사 제품 */
                    List<ProductNewDTO> productItemList = kibsService.processSelectProductNewInfoList(exSeq);
                    for(ProductNewDTO productSameInfo : productItemList){
                        productSameInfo.setProductOptionBig(convertOptionBig(productSameInfo.getProductOptionBig()));
                        productSameInfo.setProductOptionSmall(convertOptionSmall(productSameInfo.getProductOptionSmall()));
                    }
                    mv.addObject("productItemList", productItemList);

                    List<OnlineNewDTO> onlineItemList = kibsService.processSelectOnlineNewInfoList(exSeq);
                    for(OnlineNewDTO onlineSameInfo : onlineItemList){
                        onlineSameInfo.setOnlineOptionBig(convertOptionBig(onlineSameInfo.getOnlineOptionBig()));
                        onlineSameInfo.setOnlineOptionSmall(convertOptionSmall(onlineSameInfo.getOnlineOptionSmall()));
                    }
                    mv.addObject("onlineItemList", onlineItemList);

                    /* 관련 제품 */
                    OnlineDTO relatedRequestDto = new OnlineDTO();
                    relatedRequestDto.setProductOptionBig(productInfo.getProductOptionBig());
                    relatedRequestDto.setProductOptionSmall(productInfo.getProductOptionSmall());
                    relatedRequestDto.setId(productInfo.getExSeq());
                    List<List<FileDTO>> productFileList = new ArrayList<>();
                    List<OnlineRelatedDTO> relatedItemList = kibsService.processSelectOnlineRelatedList(relatedRequestDto);
                    for (OnlineRelatedDTO onlineRelatedDTO : relatedItemList) {
                        FileDTO relatedItemFileRequestDto = new FileDTO();
                        relatedItemFileRequestDto.setUserId(onlineRelatedDTO.getId());
                        relatedItemFileRequestDto.setNote(onlineRelatedDTO.getNote());
                        List<FileDTO> relatedItemFileResponseList = kibsService.processSelectProductFileInfo(relatedItemFileRequestDto);
                        productFileList.add(relatedItemFileResponseList);
                    }
                    mv.addObject("relatedItemList", relatedItemList);
                    mv.addObject("relatedItemFileList", productFileList);
                }
            } else if (seq.contains("ON")) {

                mv.addObject("gbn", "ON");

                OnlineNewDTO onlineInfo = kibsService.processSelectOnlineNewInfo(seq);
                onlineInfo.setOnlineOptionBig(convertOptionBig(onlineInfo.getOnlineOptionBig()));
                onlineInfo.setOnlineOptionSmall(convertOptionSmall(onlineInfo.getOnlineOptionSmall()));
                mv.addObject("onlineInfo", onlineInfo);

                if (onlineInfo != null) {
                    String id = onlineInfo.getExSeq();
                    List<FileDTO> fileList = kibsService.processSelectFileList(id);
                    mv.addObject("fileList", fileList);

                    ExhibitorNewDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorNewInfo(id);
                    mv.addObject("exhibitorInfo", exhibitorInfo);

                    /* 같은 회사 제품 */
                    List<ProductNewDTO> productItemList = kibsService.processSelectProductNewInfoList(id);
                    for(ProductNewDTO productSameInfo : productItemList){
                        productSameInfo.setProductOptionBig(convertOptionBig(productSameInfo.getProductOptionBig()));
                        productSameInfo.setProductOptionSmall(convertOptionSmall(productSameInfo.getProductOptionSmall()));
                    }
                    mv.addObject("productItemList", productItemList);

                    List<OnlineNewDTO> onlineItemList = kibsService.processSelectOnlineNewInfoList(id);
                    for(OnlineNewDTO onlineSameInfo : onlineItemList){
                        onlineSameInfo.setOnlineOptionBig(convertOptionBig(onlineSameInfo.getOnlineOptionBig()));
                        onlineSameInfo.setOnlineOptionSmall(convertOptionSmall(onlineSameInfo.getOnlineOptionSmall()));
                    }
                    mv.addObject("onlineItemList", onlineItemList);

                    /* 관련 제품 */
                    OnlineDTO relatedRequestDto = new OnlineDTO();
                    relatedRequestDto.setProductOptionBig(onlineInfo.getOnlineOptionBig());
                    relatedRequestDto.setProductOptionSmall(onlineInfo.getOnlineOptionSmall());
                    relatedRequestDto.setId(onlineInfo.getExSeq());
                    List<List<FileDTO>> productFileList = new ArrayList<>();
                    List<OnlineRelatedDTO> relatedItemList = kibsService.processSelectOnlineRelatedList(relatedRequestDto);
                    for (OnlineRelatedDTO onlineRelatedDTO : relatedItemList) {
                        FileDTO relatedItemFileRequestDto = new FileDTO();
                        relatedItemFileRequestDto.setUserId(onlineRelatedDTO.getId());
                        relatedItemFileRequestDto.setNote(onlineRelatedDTO.getNote());
                        List<FileDTO> relatedItemFileResponseList = kibsService.processSelectProductFileInfo(relatedItemFileRequestDto);
                        productFileList.add(relatedItemFileResponseList);
                    }
                    mv.addObject("relatedItemList", relatedItemList);
                    mv.addObject("relatedItemFileList", productFileList);
                }
            } else {

                mv.addObject("gbn", "O");

                OnlineDTO onlineInfo = kibsService.processSelectOnlineInfo(seq);
                onlineInfo.setProductOptionBig(convertOptionBig(onlineInfo.getProductOptionBig()));
                onlineInfo.setProductOptionSmall(convertOptionSmall(onlineInfo.getProductOptionSmall()));
                mv.addObject("onlineInfo", onlineInfo);

                if (onlineInfo != null) {
                    String id = onlineInfo.getId();
                    List<FileDTO> fileList = kibsService.processSelectFileList(id);
                    mv.addObject("fileList", fileList);

                    ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(id);
                    mv.addObject("exhibitorInfo", exhibitorInfo);

                    /* 같은 회사 제품 */
                    List<OnlineDTO> onlineItemList = kibsService.processSelectOnlineInfoList(id);
                    for(OnlineDTO onlineSameInfo : onlineItemList){
                        onlineSameInfo.setProductOptionBig(convertOptionBig(onlineSameInfo.getProductOptionBig()));
                        onlineSameInfo.setProductOptionSmall(convertOptionSmall(onlineSameInfo.getProductOptionSmall()));
                    }
                    mv.addObject("onlineItemList", onlineItemList);

                    /* 관련 제품 */
                    OnlineDTO relatedRequestDto = new OnlineDTO();
                    relatedRequestDto.setProductOptionBig(onlineInfo.getProductOptionBig());
                    relatedRequestDto.setProductOptionSmall(onlineInfo.getProductOptionSmall());
                    relatedRequestDto.setId(onlineInfo.getId());
                    List<List<FileDTO>> productFileList = new ArrayList<>();
                    List<OnlineRelatedDTO> relatedItemList = kibsService.processSelectOnlineRelatedList(relatedRequestDto);
                    for (OnlineRelatedDTO onlineRelatedDTO : relatedItemList) {
                        FileDTO relatedItemFileRequestDto = new FileDTO();
                        relatedItemFileRequestDto.setUserId(onlineRelatedDTO.getId());
                        relatedItemFileRequestDto.setNote(onlineRelatedDTO.getNote());
                        List<FileDTO> relatedItemFileResponseList = kibsService.processSelectProductFileInfo(relatedItemFileRequestDto);
                        productFileList.add(relatedItemFileResponseList);
                    }
                    mv.addObject("relatedItemList", relatedItemList);
                    mv.addObject("relatedItemFileList", productFileList);
                }
            }
        }

        mv.setViewName("/eng/online/product_view");
        return mv;
    }

    public String convertOptionBig(String optionBig){
        String result = optionBig;
        //"보트&요트", "무동력보트", "워크보트", "해양부품&장비", "안전&마리나", "해양레저", "수중레저", "서핑", "해양관광",
        //"boat&yacht", "paddler's world", "work boat", "parts&equipment", "safety&marina", "marine leisure", "underwater leisure", "surfing", "marine tourism", "Others"
        if(optionBig != null && !optionBig.isEmpty()){
            switch (optionBig){
                // 기존 Start -----------------
                case "해양부품":
                    result = "Marine parts";
                    break;
                case "요트·보트":
                    result = "Yacht&Boat";
                    break;
                case "아웃도어":
                    result = "Outdoor";
                    break;
                case "워터스포츠":
                    result = "Water sports";
                    break;
                case "마리나산업":
                    result = "Marina industry";
                    break;
                case "다이빙":
                    result = "Diving";
                    break;
                // 기존 End -----------------
                case "보트&요트":
                    result = "Boat&Yacht";
                    break;
                case "무동력보트":
                    result = "Paddler's World";
                    break;
                case "워크보트":
                    result = "Work Boat";
                    break;
                case "해양부품&장비":
                    result = "Parts&Equipment";
                    break;
                case "안전&마리나":
                    result = "Safety&Marina";
                    break;
                case "해양레저":
                    result = "Marine Leisure";
                    break;
                case "수중레저":
                    result = "Underwater Leisure";
                    break;
                case "서핑":
                    result = "Surfing";
                    break;
                case "해양관광":
                    result = "Marine Tourism";
                    break;
                case "기타":
                    result = "Others";
                    break;
            }
        }

        return result;
    }

    public String convertOptionSmall(String optionSmall){
        // 보트&요트
        // "파워보트", "세일요트", "고무보트", "콤비보트", "FRP보트", "알루미늄보트", "카본보트", "복합소재보트"
        // "Power Boat", "Sailing Yacht", "Inflatable Boat", "Rigid-hulled Inflatable Boat(RHIB)", "Fiberglass Boat", "Aluminum Boat", "Carbon Fiber Boat", "Composite Boat"
        // 무동력보트
        // "카누", "카약", "조정", "노보트", "SUP", "딩기요트", "무동력보트"
        // "Canoe", "Kayak", "Rowing Shell", "Rowboat", "Stand-Up Paddleboard", "Dinghy", "Sail-Only Yacht"
        // 워크보트
        // "관공선", "소방선", "구조선", "감시선", "행정선", "고속단정", "특수선박"
        // "Government Vessel", "Firefighting Vessel", "Rescue Vessel", "Patrol Vessel", "Public Service Vessel", "High-Speed RIB", "Special Purpose Vessel"
        // 해양부품&장비
        // "선외기", "선내기", "스턴드라이브 엔진", "가이드모터", "프로펠러", "마린스피커", "케이블류", "앵커", "무어링", "어군탐지기", "네비게이션", "레이더", "무선통신장비", "기타"
        // "Outboard Engine", "Inboard Engine", "Stern Drive Engine", "Trolling Motor", "Propeller", "Marine Speaker", "Marine Cables", "Anchor", "Mooring", "Fish Finder", "Marine Navigation System", "Marine Radar", "Marine Radio Equipment", "ETC"
        // 안전&마리나
        // "선박 보관 임대", "선박 유지보수", "방제장비", "워터프론트 개발", "보트용 전자장비", "도시/광택", "보트 소재 및 원료"
        // "Boat Storage and Rental", "Boat Maintenance and Repair", "Spill Response Equipment", "Waterfront Development", "Marine Electronics for Boats", "Boat Polishing/Detailing", "Boat Materials and Raw Components"
        // 해양레저
        // "수상오토바이", "수상스키", "웨이크보드", "해양레저 서비스"
        // "Personal Watercraft", "Water Skis", "Wakeboard", "Marine Leisure Services"
        // 수중레저
        // "다이빙 장비", "다이빙 서비스", "다이빙 교육", "스킨스쿠버", "스노우쿨링"
        // "Diving Equipment", "Diving Services", "Diving Education", "Scuba Diving", "Snorkeling"
        // 서핑
        // "서핑장비", "의류&패션", "체험&교육프로그램", "라이프 스타일"
        // "Surfing Gear", "Apparel & Fashion", "Experience & Education Programs", "Lifestyle"
        // 해양관광
        // "해양레저 관광상품", "지역관광콘텐츠", "치유&생태관광", "체험 콘텐츠"
        // "Marine Leisure Tour Products", "Local Tourism Content", "Healing & Ecotourism", "Experience Content"
        // 기타
        // "트레일러", "견인장치", "캠핑카", "아웃도어용품", "기타"
        // "Trailer", "Towing Equipment", "Recreational Vehicle", "Outdoor Equipment", "Others"

        String result = optionSmall;

        if(optionSmall != null && !optionSmall.isEmpty()){
            switch (optionSmall){
                // 보트&요트
                case "파워보트":
                    result = "Power Boat";
                    break;
                case "세일요트":
                    result = "Sailing Yacht";
                    break;
                case "고무보트":
                    result = "Inflatable Boat";
                    break;
                case "콤비보트":
                    result = "Rigid-hulled Inflatable Boat(RHIB)";
                    break;
                case "FRP보트":
                    result = "Fiberglass Boat";
                    break;
                case "알루미늄보트":
                    result = "Aluminum Boat";
                    break;
                case "카본보트":
                    result = "Carbon Fiber Boat";
                    break;
                case "복합소재보트":
                    result = "Composite Boat";
                    break;

                // 무동력보트
                case "카누":
                    result = "Canoe";
                    break;
                case "카약":
                    result = "Kayak";
                    break;
                case "조정":
                    result = "Rowing Shell";
                    break;
                case "노보트":
                    result = "Rowboat";
                    break;
                case "SUP":
                    result = "Stand-Up Paddleboard";
                    break;
                case "딩기요트":
                    result = "Dinghy";
                    break;
                case "무동력보트":
                    result = "Sail-Only Yacht";
                    break;

                // 워크보트
                case "관공선":
                    result = "Government Vessel";
                    break;
                case "소방선":
                    result = "Firefighting Vessel";
                    break;
                case "구조선":
                    result = "Rescue Vessel";
                    break;
                case "감시선":
                    result = "Patrol Vessel";
                    break;
                case "행정선":
                    result = "Public Service Vessel";
                    break;
                case "고속단정":
                    result = "High-Speed RIB";
                    break;
                case "특수선박":
                    result = "Special Purpose Vessel";
                    break;

                // 해양부품&장비
                case "선외기":
                    result = "Outboard Engine";
                    break;
                case "선내기":
                    result = "Inboard Engine";
                    break;
                case "스턴드라이브 엔진":
                    result = "Stern Drive Engine";
                    break;
                case "가이드모터":
                    result = "Trolling Motor";
                    break;
                case "프로펠러":
                    result = "Propeller";
                    break;
                case "마린스피커":
                    result = "Marine Speaker";
                    break;
                case "케이블류":
                    result = "Marine Cables";
                    break;
                case "앵커":
                    result = "Anchor";
                    break;
                case "무어링":
                    result = "Mooring";
                    break;
                case "어군탐지기":
                    result = "Fish Finder";
                    break;
                case "네비게이션":
                    result = "Marine Navigation System";
                    break;
                case "레이더":
                    result = "Marine Radar";
                    break;
                case "무선통신장비":
                    result = "Marine Radio Equipment";
                    break;
                case "기타":
                    result = "ETC";
                    break;

                // 안전&마리나
                case "선박 보관 임대":
                    result = "Boat Storage and Rental";
                    break;
                case "선박 유지보수":
                    result = "Boat Maintenance and Repair";
                    break;
                case "방제장비":
                    result = "Spill Response Equipment";
                    break;
                case "워터프론트 개발":
                    result = "Waterfront Development";
                    break;
                case "보트용 전자장비":
                    result = "Marine Electronics for Boats";
                    break;
                case "도시/광택":
                    result = "Boat Polishing/Detailing";
                    break;
                case "보트 소재 및 원료":
                    result = "Boat Materials and Raw Components";
                    break;

                // 해양레저
                case "수상오토바이":
                    result = "Personal Watercraft";
                    break;
                case "수상스키":
                    result = "Water Skis";
                    break;
                case "웨이크보드":
                    result = "Wakeboard";
                    break;
                case "해양레저 서비스":
                    result = "Marine Leisure Services";
                    break;

                // 수중레저
                case "다이빙 장비":
                    result = "Diving Equipment";
                    break;
                case "다이빙 서비스":
                    result = "Diving Services";
                    break;
                case "다이빙 교육":
                    result = "Diving Education";
                    break;
                case "스킨스쿠버":
                    result = "Scuba Diving";
                    break;
                case "스노우쿨링":
                    result = "Snorkeling";
                    break;

                // 서핑
                case "서핑장비":
                    result = "Surfing Gear";
                    break;
                case "의류&패션":
                    result = "Apparel & Fashion";
                    break;
                case "체험&교육프로그램":
                    result = "Experience & Education Programs";
                    break;
                case "라이프스타일":
                    result = "Lifestyle";
                    break;

                // 해양관광
                case "해양레저 관광상품":
                    result = "Marine Leisure Tour Products";
                    break;
                case "지역관광콘텐츠":
                    result = "Local Tourism Content";
                    break;
                case "치유&생태관광":
                    result = "Healing & Ecotourism";
                    break;
                case "체험 콘텐츠":
                    result = "Experience Content";
                    break;

                // 기타 (기존 "기타"와 중복될 수 있으니 주의)
                case "트레일러":
                    result = "Trailer";
                    break;
                case "견인장치":
                    result = "Towing Equipment";
                    break;
                case "캠핑카":
                    result = "Recreational Vehicle";
                    break;
                case "아웃도어용품":
                    result = "Outdoor Equipment";
                    break;
                /*case "기타":
                    result = "ETC";
                    break;*/
            }
        }

        return result;
    }

    public HashMap<String, Object> convertMap(HttpServletRequest request) {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        String key;
        Enumeration<?> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (request.getParameterValues(key).length > 1) {
                hm.put(key, request.getParameterValues(key));
            } else {
                hm.put(key, request.getParameter(key));
            }
        }
        return hm;
    }

}