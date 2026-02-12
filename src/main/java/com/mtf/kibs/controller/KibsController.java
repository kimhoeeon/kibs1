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
import javax.swing.*;
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
import java.time.LocalDate;
import java.util.*;

@Controller
public class KibsController {

    @Autowired
    private CalculationService calculationService; // 1. 공통 계산 서비스 주입

    private final KibsService kibsService;

    private final CommService commService;

    private final String transferYear = "2026";

    public KibsController(KibsService ks, CommService cs){
        this.kibsService = ks;
        this.commService = cs;
    }

    //***************************************************************************
    // home
    //***************************************************************************

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView home() {
        System.out.println("KibsController > home : ======");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public ModelAndView login() {
        System.out.println("KibsController > login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, ModelAndView mv) {
        System.out.println("KibsController > logout");
        kibsService.logoutCheck(session);
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "/main.do", method = RequestMethod.GET)
    public ModelAndView main() {
        System.out.println("KibsController > main");
        ModelAndView mv = new ModelAndView();
        String lang = "KO";

        // 공지사항&칼럼/기사
        List<NoticeDTO> mainNoticeList = kibsService.processSelectMainNoticeList(lang);
        mv.addObject("mainNoticeList", mainNoticeList);

        List<ColumnDTO> mainColumnList = kibsService.processSelectMainColumnList();
        mv.addObject("mainColumnList", mainColumnList);

        // KIBS Tube
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

        // 참가업체 보기
        String transferYear = "2026";
        List<MainOnlineDTO> mainOnlineList = kibsService.processSelectOnlineList(transferYear);
        mv.addObject("mainOnlineList", mainOnlineList);

        // 팝업파일정보
        PopupDTO popupDTO = new PopupDTO();
        popupDTO.setLang(lang);
        popupDTO.setUseYn("Y");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = dateFormat.format(new Date());
        popupDTO.setToday(today);
        List<PopupDTO> popupList = kibsService.processSelectPopupList(popupDTO);
        mv.addObject("popupList", popupList);

        // 브로슈어
        String currentYear = "2026"; // 현재 대상 연도
        BrochureDTO brochureData = kibsService.getBrochureInfo(currentYear);

        if (brochureData == null) {
            brochureData = new BrochureDTO();
        }

        mv.addObject("brochureData", brochureData);

        // 방문자 수 카운트
        kibsService.processStatisticsAccessor();

        mv.setViewName("main");
        return mv;
    }

    @RequestMapping(value = "/popup.do", method = RequestMethod.POST)
    public ModelAndView popup(PopupDTO popupDTO) {
        System.out.println("KibsController > popup");
        ModelAndView mv = new ModelAndView();
        //System.out.println(popupDTO);

        PopupDTO info = kibsService.processSelectPopupSingle(popupDTO);
        if(info != null){
            mv.addObject("info", info);
        }

        mv.setViewName("/popup");
        return mv;
    }

    @RequestMapping(value = "/privacy.do", method = RequestMethod.GET)
    public ModelAndView privacy() {
        System.out.println("KibsController > privacy");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/privacy");
        return mv;
    }

    @RequestMapping(value = "/insta/refresh/select.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<InstaTokenDTO> insta_refresh_select() {
        System.out.println("KibsController > insta_refresh_select");

        InstaTokenDTO response = kibsService.processSelectInstaRefresh();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/insta/refresh/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insta_refresh_update(@RequestBody InstaTokenDTO instaTokenDTO) {
        System.out.println("KibsController > insta_refresh_update");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsService.processUpdateInstaRefresh(instaTokenDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //***************************************************************************
    // guide Folder
    //***************************************************************************

    @RequestMapping(value = "/guide/notice.do", method = RequestMethod.GET)
    public ModelAndView guide_notice() {
        System.out.println("KibsController > guide_notice");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/guide/notice");
        return mv;
    }

    @RequestMapping(value = "/guide/notice/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<NoticeDTO>> guide_notice_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > guide_notice_selectList");
        //System.out.println(searchDTO.getPageNum());
        //System.out.println(searchDTO.getRows());

        List<NoticeDTO> responseList = kibsService.processSelectNoticeList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/guide/noticeView.do", method = RequestMethod.GET)
    public ModelAndView guide_noticeView(String id) {
        System.out.println("KibsController > guide_noticeView");
        //System.out.println(id);
        ModelAndView mv = new ModelAndView();

        /* 조회 카운트 Update */
        kibsService.processUpdateNoticeViewCnt(id);

        /* 데이터 조회 후 Set */
        NoticeDTO noticeInfo = kibsService.processSelectNoticeSingle(id);

        if(noticeInfo != null){

            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setLang("KO");
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
        mv.setViewName("/guide/noticeView");
        return mv;
    }

    @RequestMapping(value = "/guide/summary.do", method = RequestMethod.GET)
    public ModelAndView guide_summary() {
        System.out.println("KibsController > guide_summary");
        ModelAndView mv = new ModelAndView();

        // 브로슈어
        String currentYear = "2026"; // 현재 대상 연도
        BrochureDTO brochureData = kibsService.getBrochureInfo(currentYear);

        if (brochureData == null) {
            brochureData = new BrochureDTO();
        }

        mv.addObject("brochureData", brochureData);

        mv.setViewName("/guide/summary");
        return mv;
    }

    @RequestMapping(value = "/guide/maritime_expo.do", method = RequestMethod.GET)
    public ModelAndView guide_maritime_expo() {
        System.out.println("KibsController > guide_maritime_expo");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/guide/maritime_expo");
        return mv;
    }

    @RequestMapping(value = "/guide/summary_kiss.do", method = RequestMethod.GET)
    public ModelAndView guide_summary_kiss() {
        System.out.println("KibsController > guide_summary_kiss");
        ModelAndView mv = new ModelAndView();

        // 브로슈어
        String currentYear = "2026"; // 현재 대상 연도
        BrochureDTO brochureData = kibsService.getBrochureInfo(currentYear);

        if (brochureData == null) {
            brochureData = new BrochureDTO();
        }

        mv.addObject("brochureData", brochureData);

        mv.setViewName("/guide/summary_kiss");
        return mv;
    }

    @RequestMapping(value = "/guide/summary_kmts.do", method = RequestMethod.GET)
    public ModelAndView guide_summary_kmts() {
        System.out.println("KibsController > guide_summary_kmts");
        ModelAndView mv = new ModelAndView();

        // 브로슈어
        String currentYear = "2026"; // 현재 대상 연도
        BrochureDTO brochureData = kibsService.getBrochureInfo(currentYear);

        if (brochureData == null) {
            brochureData = new BrochureDTO();
        }

        mv.addObject("brochureData", brochureData);

        mv.setViewName("/guide/summary_kmts");
        return mv;
    }

    @RequestMapping(value = "/guide/program.do", method = RequestMethod.GET)
    public ModelAndView guide_program() {
        System.out.println("KibsController > guide_program");
        ModelAndView mv = new ModelAndView();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String today = dateFormat.format(new Date());
        if (Long.parseLong(today) > Long.parseLong("202602130859")) {
            mv.addObject("mode", "open");
        }

        mv.setViewName("/guide/program");
        return mv;
    }

    // 최미나가 추가함
    @RequestMapping(value = "/guide/program2025.do", method = RequestMethod.GET)
    public ModelAndView guide_program2025() {
        System.out.println("KibsController > guide_program2025");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/guide/program2025");
        return mv;
    }

    @RequestMapping(value = "/guide/conference.do", method = RequestMethod.GET)
    public ModelAndView guide_conference() {
        System.out.println("KibsController > guide_conference");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/guide/conference");
        return mv;
    }

    @RequestMapping(value = "/guide/conference2025.do", method = RequestMethod.GET)
    public ModelAndView guide_conference2025() {
        System.out.println("KibsController > guide_conference2025");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/guide/conference2025");
        return mv;
    }

    //***************************************************************************
    // apply Folder
    //***************************************************************************

    @RequestMapping(value = "/apply/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step01(String mode, ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step01");
        ModelAndView mv = new ModelAndView();

        if(mode != null && !mode.isEmpty()){
            mv.addObject("mode", mode);
        }else {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String today = dateFormat.format(new Date());
            if (Long.parseLong(today) > Long.parseLong("202510200859")) {
                mv.addObject("mode", "open");
            }
        }

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            if(id != null && !id.equals("null") && !id.isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/apply/step01");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_9.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_9(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_9");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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
        mv.setViewName("/apply/step2_9");
        return mv;
    }
    
    @RequestMapping(value = "/apply/step2_1.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_1(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_1");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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
        mv.setViewName("/apply/step2_1");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_2(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_2");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/apply/step2_2");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_10.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_10(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_10");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/apply/step2_10");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_3.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_3(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_3");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/apply/step2_3");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_4.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_4(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_4");
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
            exDTO.setTransferYear(transferYear);
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            PassNewDTO reqDTO = new PassNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            passList = kibsService.processSelectPassNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("passList", passList);
            exhibitorNewDTO.setTransferYear(transferYear);
            ExhibitorNewDTO exhibitorInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }

        mv.setViewName("/apply/step2_4");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_5.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_5(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_5");

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
            exDTO.setTransferYear(transferYear);
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            GiftNewDTO reqDTO = new GiftNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            giftList = kibsService.processSelectGiftNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("giftList", giftList);
        }

        mv.setViewName("/apply/step2_5");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_6.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_6() {
        System.out.println("KibsController > apply_step2_6");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/apply/step2_6");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_7.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_7() {
        System.out.println("KibsController > apply_step2_7");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/apply/step2_7");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_8.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_8(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_8");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            if(id != null && !id.equals("null") && !id.isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/apply/step2_8");
        return mv;
    }

    @RequestMapping(value = "/apply/step03.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step03(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > apply_step03");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/apply/step03");
        return mv;
    }

    //***************************************************************************
    // exhibitor Folder
    //***************************************************************************

    @RequestMapping(value = "/exhibitor/guide.do", method = RequestMethod.GET)
    public ModelAndView exhibitor_guide() {
        System.out.println("KibsController > exhibitor_guide");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/exhibitor/guide");
        return mv;
    }

    @RequestMapping(value = "/exhibitor/login.do", method = RequestMethod.GET)
    public ModelAndView exhibitor_login() {
        System.out.println("KibsController > exhibitor_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/exhibitor/login");
        return mv;
    }

    @RequestMapping(value = "/exhibitor/faq.do", method = RequestMethod.GET)
    public ModelAndView exhibitor_faq() {
        System.out.println("KibsController > exhibitor_faq");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/exhibitor/faq");
        return mv;
    }

    @RequestMapping(value = "/exhibitor/faq/selectList.do", method = RequestMethod.POST)
    public ResponseEntity<List<FaqDTO>> exhibitor_faq_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > exhibitor_faq_selectList");
        //System.out.println(searchDTO.toString());

        List<FaqDTO> responseList = kibsService.processSelectFaqList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/exhibitor/privacy.do", method = RequestMethod.GET)
    public ModelAndView exhibitor_privacy() {
        System.out.println("KibsController > exhibitor_privacy");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/exhibitor/privacy");
        return mv;
    }

    @RequestMapping(value = "/exhibitor/findpw.do", method = RequestMethod.GET)
    public ModelAndView exhibitor_findpw() {
        System.out.println("KibsController > exhibitor_findpw");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/exhibitor/findpw");
        return mv;
    }

    //***************************************************************************
    // visitor Folder
    //***************************************************************************

    @RequestMapping(value = "/visitor/guide_off.do", method = RequestMethod.GET)
    public ModelAndView visitor_guide_off() {
        System.out.println("KibsController > visitor_guide_off");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visitor/guide_off");
        return mv;
    }

    @RequestMapping(value = "/visitor/apply.do", method = RequestMethod.GET)
    public ModelAndView visitor_apply(String mode) {
        System.out.println("KibsController > visitor_apply");
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
        mv.setViewName("/visitor/apply");
        return mv;
    }

    @RequestMapping(value = "/visitor/completed.do", method = RequestMethod.GET)
    public ModelAndView visitor_completed() {
        System.out.println("KibsController > visitor_completed");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visitor/completed");
        return mv;
    }

    @RequestMapping(value = "/visitor/login.do", method = RequestMethod.GET)
    public ModelAndView visitor_login() {
        System.out.println("KibsController > visitor_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visitor/login");
        return mv;
    }

    @RequestMapping(value = "/visitor/preApplyCheck.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<VisitorDTO> visitor_preApplyCheck(@RequestBody VisitorDTO visitorDTO) {
        System.out.println("KibsController > visitor_preApplyCheck");
        VisitorDTO result = kibsService.processSelectPreVisitorCheck(visitorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/visitor/save.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> visitor_save(@RequestBody VisitorDTO visitorDTO) {
        System.out.println("KibsController > visitor_save");
        ResponseDTO response = kibsService.processSaveVisitor(visitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/visitor/mypage.do", method = RequestMethod.GET)
    public ModelAndView visitor_mypage(String seq) {
        System.out.println("KibsController > visitor_mypage");
        ModelAndView mv = new ModelAndView();
        VisitorDTO info = kibsService.processSelectVisitorSingle(seq);

        if(info != null){
            mv.addObject("info", info);
            List<PartnerDTO> partnerList = kibsService.processSelectPartnerList(info.getSeq());
            mv.addObject("partnerList", partnerList);
        }

        mv.setViewName("/visitor/mypage");
        return mv;
    }

    @RequestMapping(value = "/visitor/mypage/deletePartner.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> visitor_mypage_deletePartner(@RequestBody PartnerDTO partnerDTO) {
        System.out.println("KibsController > visitor_mypage_deletePartner");
        //System.out.println(displayDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeletePartner(partnerDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/visitor/faq.do", method = RequestMethod.GET)
    public ModelAndView visitor_faq() {
        System.out.println("KibsController > visitor_faq");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visitor/faq");
        return mv;
    }

    @RequestMapping(value = "/visitor/companySearch.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SearchCompanyResponseDTO> visitor_companySearch(@RequestBody SearchCompanyRequestDTO searchCompanyRequestDTO) {
        System.out.println("KibsController > visitor_companySearch");
        SearchCompanyResponseDTO result = kibsService.processSearchCompany(searchCompanyRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/apis/data/NpsBplcInfoInqireService/getBassInfoSearch/companySearch.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseDTO> data_companySearch(Integer code, Integer pageNo, Integer totalCount) {
        System.out.println("KibsController > data_companySearch");
        ResponseDTO response = new ResponseDTO();
        try{
            //int pageNo = 1;
            int curPageNo = 1000;
            //int totalCount = 1614300;
            int total = 0;
            while (total <= totalCount){
                total = pageNo * curPageNo;

                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "nngY%2FlASnTg%2FKJlWdupohRX699RJx6xxaPIsfw3WMoP74fL3ElwqhwmIWWYrlYYABP%2B7SUiOfhPGiVY%2BRDSxdg%3D%3D"); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_dg_cd","UTF-8") + "=" + URLEncoder.encode(Integer.toString(code), "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(Integer.toString(curPageNo), "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
                urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(Integer.toString(pageNo), "UTF-8")); /*시도(행정자치부 법정동 주소코드 참조)*/
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

                String xmlData = sb.toString();

                StringReader sr = new StringReader(xmlData);
                if(!xmlData.contains("OpenAPI_")){
                    JAXBContext jaxbContext = JAXBContext.newInstance(SearchCompanyResponseDTO.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    SearchCompanyResponseDTO result = (SearchCompanyResponseDTO) unmarshaller.unmarshal(sr);

                    int idx = 0;
                    for(SearchCompanyResponseDTO.Body.Items.Item item: result.getBody().getItems().getItem()){
                        BusinessDTO businessDTO = new BusinessDTO();
                        businessDTO.setLdongAddrMgplDgCd(item.getLdongAddrMgplDgCd());
                        businessDTO.setLdongAddrMgplSgguCd(item.getLdongAddrMgplSgguCd());
                        businessDTO.setLdongAddrMgplSgguEmdCd(item.getLdongAddrMgplSgguEmdCd());
                        businessDTO.setWkplNm(item.getWkplNm());
                        businessDTO.setBzowrRgstNo(item.getBzowrRgstNo());
                        businessDTO.setWkplRoadNmDtlAddr(item.getWkplRoadNmDtlAddr());
                        businessDTO.setWkplJnngStcd(item.getWkplJnngStcd());

                        kibsService.insertBusiness(businessDTO);
                        System.out.println("total : " + total + " , pageNo : " + pageNo + " idx : " + idx);
                        idx++;
                    }
                }

                pageNo++;
            }

        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //***************************************************************************
    // board Folder
    //***************************************************************************

    @RequestMapping(value = "/board/contest.do", method = RequestMethod.GET)
    public ModelAndView board_contest() {
        System.out.println("KibsController > board_contest");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/contest");
        return mv;
    }

    @RequestMapping(value = "/board/contest/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ContestDTO>> board_contest_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > board_contest_selectList");
        //System.out.println(searchDTO.toString());

        List<ContestDTO> responseList = kibsService.processSelectContestList(searchDTO);

        for(ContestDTO response : responseList){
            List<String> fullFilePathList = new ArrayList<>();
            List<FileDTO> fileList = kibsService.processSelectFileList(response.getId());
            for(FileDTO file : fileList){
                fullFilePathList.add(file.getFullFilePath());
            }
            response.setFullFilePathList(fullFilePathList);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/gallery.do", method = RequestMethod.GET)
    public ModelAndView board_gallery() {
        System.out.println("KibsController > board_gallery");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/gallery");
        return mv;
    }

    @RequestMapping(value = "/board/gallery/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<DataroomDTO>> board_gallery_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > board_gallery_selectList");
        //System.out.println(searchDTO.toString());

        List<DataroomDTO> responseList = kibsService.processSelectGalleryList(searchDTO);

        for(DataroomDTO response : responseList){

            List<String> fullFilePathList = new ArrayList<>();
            List<FileDTO> fileList = kibsService.processSelectFileList(response.getId());
            response.setFileList(fileList);

            for(FileDTO file : fileList){
                fullFilePathList.add(file.getFullFilePath());
            }
            response.setFullFilePathList(fullFilePathList);

        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/press.do", method = RequestMethod.GET)
    public ModelAndView board_press() {
        System.out.println("KibsController > board_press");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/press");
        return mv;
    }

    @RequestMapping(value = "/board/press/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<PressDTO>> board_press_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > board_press_selectList");
        //System.out.println(searchDTO.toString());

        List<PressDTO> responseList = kibsService.processSelectPressList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/pressView.do", method = RequestMethod.GET)
    public ModelAndView board_pressView(String id) {
        System.out.println("KibsController > board_pressView");
        ModelAndView mv = new ModelAndView();

        /* 조회 카운트 Update */
        kibsService.processUpdatePressViewCnt(id);

        /* 데이터 조회 후 Set */
        PressDTO pressInfo = kibsService.processSelectPressSingle(id);

        if(pressInfo != null){

            List<PressDTO> responseList = kibsService.processSelectPressList(new SearchDTO());
            for(PressDTO responseInfo: responseList){
                if(responseInfo.getId().equals(id)){
                    pressInfo.setPrevId(responseInfo.getPrevId());
                    pressInfo.setNextId(responseInfo.getNextId());
                }
            }
            mv.addObject("pressInfo", pressInfo);

            /* 첨부파일 정보 Set */
            List<FileDTO> fileList = kibsService.processSelectFileList(pressInfo.getId());
            if(fileList != null && !fileList.isEmpty()){
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/board/pressView");
        return mv;
    }

    @RequestMapping(value = "/board/column.do", method = RequestMethod.GET)
    public ModelAndView board_column() {
        System.out.println("KibsController > board_column");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/column");
        return mv;
    }

    @RequestMapping(value = "/board/column/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ColumnDTO>> board_column_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > board_column_selectList");
        //System.out.println(searchDTO.toString());

        List<ColumnDTO> responseList = kibsService.processSelectColumnList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/columnView.do", method = RequestMethod.GET)
    public ModelAndView board_columnView(String id) {
        System.out.println("KibsController > board_columnView");
        ModelAndView mv = new ModelAndView();

        /* 조회 카운트 Update */
        kibsService.processUpdateColumnViewCnt(id);

        /* 데이터 조회 후 Set */
        ColumnDTO columnInfo = kibsService.processSelectColumnSingle(id);

        if(columnInfo != null){

            List<ColumnDTO> responseList = kibsService.processSelectColumnList(new SearchDTO());
            for(ColumnDTO responseInfo: responseList){
                if(responseInfo.getId().equals(id)){
                    columnInfo.setPrevId(responseInfo.getPrevId());
                    columnInfo.setNextId(responseInfo.getNextId());
                }
            }
            mv.addObject("columnInfo", columnInfo);

            /* 첨부파일 정보 Set */
            List<FileDTO> fileList = kibsService.processSelectFileList(columnInfo.getId());
            if(fileList != null && !fileList.isEmpty()){
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/board/columnView");
        return mv;
    }

    @RequestMapping(value = "/board/newsletter.do", method = RequestMethod.GET)
    public ModelAndView board_newsletter() {
        System.out.println("KibsController > board_newsletter");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/newsletter");
        return mv;
    }

    @RequestMapping(value = "/board/newsletter/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<NewsletterDTO>> board_newsletter_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > board_newsletter_selectList");
        //System.out.println(searchDTO.toString());

        List<NewsletterDTO> responseList = kibsService.processSelectNewsletterList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/newsletterView.do", method = RequestMethod.GET)
    public ModelAndView board_newsletterView(String id) {
        System.out.println("KibsController > board_newsletterView");
        ModelAndView mv = new ModelAndView();

        /* 조회 카운트 Update */
        kibsService.processUpdateNewsletterViewCnt(id);

        /* 데이터 조회 후 Set */
        NewsletterDTO newsletterInfo = kibsService.processSelectNewsletterSingle(id);

        if(newsletterInfo != null){
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setLang("KO");
            List<NewsletterDTO> responseList = kibsService.processSelectNewsletterList(searchDTO);
            for(NewsletterDTO responseInfo: responseList){
                if(responseInfo.getId().equals(id)){
                    newsletterInfo.setPrevId(responseInfo.getPrevId());
                    newsletterInfo.setNextId(responseInfo.getNextId());
                }
            }
            mv.addObject("newsletterInfo", newsletterInfo);

            /* 첨부파일 정보 Set */
            List<FileDTO> fileList = kibsService.processSelectFileList(newsletterInfo.getId());
            if(fileList != null && !fileList.isEmpty()){
                mv.addObject("fileList", fileList);
            }
        }
        mv.setViewName("/board/newsletterView");
        return mv;
    }

    @RequestMapping(value = "/board/history.do", method = RequestMethod.GET)
    public ModelAndView board_history() {
        System.out.println("KibsController > board_history");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history");
        return mv;
    }

    @RequestMapping(value = "/board/history08.do", method = RequestMethod.GET)
    public ModelAndView board_history08() {
        System.out.println("KibsController > board_history08");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history08");
        return mv;
    }

    @RequestMapping(value = "/board/history09.do", method = RequestMethod.GET)
    public ModelAndView board_history09() {
        System.out.println("KibsController > board_history09");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history09");
        return mv;
    }

    @RequestMapping(value = "/board/history10.do", method = RequestMethod.GET)
    public ModelAndView board_history10() {
        System.out.println("KibsController > board_history10");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history10");
        return mv;
    }

    @RequestMapping(value = "/board/history11.do", method = RequestMethod.GET)
    public ModelAndView board_history11() {
        System.out.println("KibsController > board_history11");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history11");
        return mv;
    }

    @RequestMapping(value = "/board/history12.do", method = RequestMethod.GET)
    public ModelAndView board_history12() {
        System.out.println("KibsController > board_history12");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history12");
        return mv;
    }

    @RequestMapping(value = "/board/history13.do", method = RequestMethod.GET)
    public ModelAndView board_history13() {
        System.out.println("KibsController > board_history13");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history13");
        return mv;
    }

    @RequestMapping(value = "/board/history14.do", method = RequestMethod.GET)
    public ModelAndView board_history14() {
        System.out.println("KibsController > board_history14");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history14");
        return mv;
    }

    @RequestMapping(value = "/board/history15.do", method = RequestMethod.GET)
    public ModelAndView board_history15() {
        System.out.println("KibsController > board_history15");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history15");
        return mv;
    }

    @RequestMapping(value = "/board/history16.do", method = RequestMethod.GET)
    public ModelAndView board_history16() {
        System.out.println("KibsController > board_history16");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history16");
        return mv;
    }

    @RequestMapping(value = "/board/history17.do", method = RequestMethod.GET)
    public ModelAndView board_history17() {
        System.out.println("KibsController > board_history17");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history17");
        return mv;
    }

    @RequestMapping(value = "/board/history18.do", method = RequestMethod.GET)
    public ModelAndView board_history18() {
        System.out.println("KibsController > board_history18");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history18");
        return mv;
    }

    @RequestMapping(value = "/board/history19.do", method = RequestMethod.GET)
    public ModelAndView board_history19() {
        System.out.println("KibsController > board_history19");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history19");
        return mv;
    }

    @RequestMapping(value = "/board/history21.do", method = RequestMethod.GET)
    public ModelAndView board_history21() {
        System.out.println("KibsController > board_history21");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history21");
        return mv;
    }

    @RequestMapping(value = "/board/history22.do", method = RequestMethod.GET)
    public ModelAndView board_history22() {
        System.out.println("KibsController > board_history22");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history22");
        return mv;
    }

    @RequestMapping(value = "/board/history23.do", method = RequestMethod.GET)
    public ModelAndView board_history23() {
        System.out.println("KibsController > board_history23");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history23");
        return mv;
    }

    @RequestMapping(value = "/board/history24.do", method = RequestMethod.GET)
    public ModelAndView board_history24() {
        System.out.println("KibsController > board_history24");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history24");
        return mv;
    }

    // 최미나가 추가함
    @RequestMapping(value = "/board/history25.do", method = RequestMethod.GET)
    public ModelAndView board_history25() {
        System.out.println("KibsController > board_history25");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/history25");
        return mv;
    }

    @RequestMapping(value = "/board/bi.do", method = RequestMethod.GET)
    public ModelAndView board_bi() {
        System.out.println("KibsController > board_bi");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/bi");
        return mv;
    }

    @RequestMapping(value = "/board/cerify.do", method = RequestMethod.GET)
    public ModelAndView board_cerify() {
        System.out.println("KibsController > board_cerify");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/cerify");
        return mv;
    }

    @RequestMapping(value = "/board/award.do", method = RequestMethod.GET)
    public ModelAndView board_award() {
        System.out.println("KibsController > board_award");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/award");
        return mv;
    }

    //***************************************************************************
    // online Folder
    //***************************************************************************

    @RequestMapping(value = "/online/kibstv.do", method = RequestMethod.GET)
    public ModelAndView online_kibstv() {
        System.out.println("KibsController > online_kibstv");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/online/kibstv");
        return mv;
    }

    @RequestMapping(value = "/online/kibstv_year/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<KibstvDTO>> online_kibstv_year_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > online_kibstv_year_selectList");
        //System.out.println(searchDTO.toString());

        List<KibstvDTO> responseList = kibsService.processSelectKibstvYearList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/online/kibstv_cate.do", method = RequestMethod.GET)
    public ModelAndView online_kibstv_year() {
        System.out.println("KibsController > online_kibstv_year");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/online/kibstv_cate");
        return mv;
    }

    @RequestMapping(value = "/online/kibstv/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<KibstvDTO>> online_kibstv_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > online_kibstv_selectList");
        //System.out.println(searchDTO.toString());

        List<KibstvDTO> responseList = kibsService.processSelectKibstvList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/online/company.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView online_company(HttpServletRequest request, String companyNameKo) {
        System.out.println("KibsController > online_company");
        ModelAndView mv = new ModelAndView();
        if("POST".equals(request.getMethod())){
            mv.addObject("companyNameKo", companyNameKo);
        }
        mv.setViewName("/online/company");
        return mv;
    }

    @RequestMapping(value = "/online/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MainOnlineDTO>> online_company_selectList(@RequestBody MainOnlineDTO mainOnlineDTO) {
        System.out.println("KibsController > online_company_selectList");
        //System.out.println(mainOnlineDTO.toString());

        List<MainOnlineDTO> responseDTO = kibsService.processSelectOnlineListPaging(mainOnlineDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/online/company_view.do", method = RequestMethod.GET)
    public ModelAndView online_company_view(String seq) {
        System.out.println("KibsController > online_company_view");
        ModelAndView mv = new ModelAndView();

        if(seq != null && !seq.isEmpty()) {
            if (seq.contains("EN")) {
                mv.addObject("gbn", "EN");

                ExhibitorNewDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorNewInfo(seq);
                mv.addObject("exhibitorInfo", exhibitorInfo);

                /*List<ProductNewDTO> productList = kibsService.processSelectProductNewInfoList(seq);
                mv.addObject("productList", productList);*/

                List<OnlineNewDTO> onlineList = kibsService.processSelectOnlineNewInfoList(seq);
                mv.addObject("onlineList", onlineList);
            } else {
                mv.addObject("gbn", "E");

                ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(seq);
                mv.addObject("exhibitorInfo", exhibitorInfo);

                List<OnlineDTO> onlineList = kibsService.processSelectOnlineInfoList(seq);
                mv.addObject("onlineList", onlineList);
            }

            List<FileDTO> fileList = kibsService.processSelectFileList(seq);
            mv.addObject("fileList", fileList);
        }

        mv.setViewName("/online/company_view");
        return mv;
    }

    @RequestMapping(value = "/exhibitor/company/selectChargeEmail.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ChargePersonDTO>> exhibitor_company_select_charge_email(@RequestBody ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsController > exhibitor_company_select_charge_email");
        //System.out.println(fileDTO.toString());

        List<ChargePersonDTO> response = kibsService.processSelectChargeEmail(chargePersonDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/online/product.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView online_product(HttpServletRequest request, OnlineRelatedDTO onlineRelatedDTO) {
        System.out.println("KibsController > online_product");
        //System.out.println(onlineRelatedDTO.toString());

        ModelAndView mv = new ModelAndView();
        if("POST".equals(request.getMethod())){
            mv.addObject("productOptionInfo", onlineRelatedDTO);
        }
        mv.setViewName("/online/product");
        return mv;
    }

    @RequestMapping(value = "/online/product/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> online_product_selectList(@RequestBody OnlineRelatedDTO onlineRelatedDTO) {
        System.out.println("KibsController > online_product_selectList");
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

    @RequestMapping(value = "/online/product_view.do", method = RequestMethod.GET)
    public ModelAndView online_product_view(String seq) {
        System.out.println("KibsController > online_product_view");
        //System.out.println(seq);
        ModelAndView mv = new ModelAndView();
        if (seq != null && !seq.isEmpty()) {
            if(seq.contains("PN")){
                mv.addObject("gbn", "PN");

                ProductNewDTO productInfo = kibsService.processSelectProductNewInfo(seq);
                mv.addObject("productInfo", productInfo);

                if (productInfo != null) {
                    String exSeq = productInfo.getExSeq();
                    List<FileDTO> fileList = kibsService.processSelectFileList(exSeq);
                    mv.addObject("fileList", fileList);

                    ExhibitorNewDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorNewInfo(exSeq);
                    mv.addObject("exhibitorInfo", exhibitorInfo);

                    /* 같은 회사 제품 */
                    List<ProductNewDTO> productItemList = kibsService.processSelectProductNewInfoList(exSeq);
                    mv.addObject("productItemList", productItemList);

                    List<OnlineNewDTO> onlineItemList = kibsService.processSelectOnlineNewInfoList(exSeq);
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
                mv.addObject("onlineInfo", onlineInfo);

                if (onlineInfo != null) {
                    String id = onlineInfo.getExSeq();
                    List<FileDTO> fileList = kibsService.processSelectFileList(id);
                    mv.addObject("fileList", fileList);

                    ExhibitorNewDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorNewInfo(id);
                    mv.addObject("exhibitorInfo", exhibitorInfo);

                    /* 같은 회사 제품 */
                    /*List<ProductNewDTO> productItemList = kibsService.processSelectProductNewInfoList(id);
                    mv.addObject("productItemList", productItemList);*/

                    List<OnlineNewDTO> onlineItemList = kibsService.processSelectOnlineNewInfoList(id);
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
                mv.addObject("onlineInfo", onlineInfo);

                if (onlineInfo != null) {
                    String id = onlineInfo.getId();
                    List<FileDTO> fileList = kibsService.processSelectFileList(id);
                    mv.addObject("fileList", fileList);

                    ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(id);
                    mv.addObject("exhibitorInfo", exhibitorInfo);

                    /* 같은 회사 제품 */
                    List<OnlineDTO> onlineItemList = kibsService.processSelectOnlineInfoList(id);
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

        mv.setViewName("/online/product_view");
        return mv;
    }

    //***************************************************************************
    // mypage Folder
    //***************************************************************************

    @RequestMapping(value = "/mypage/index.do", method = RequestMethod.POST)
    public ModelAndView mypage_index(ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > mypage_index");
        //System.out.println(exhibitorDTO.getSeq() + " / " + exhibitorDTO.getId() + " / " + exhibitorDTO.getTransferYear() );
        ModelAndView mv = new ModelAndView();
        ExhibitorNewDTO info = kibsService.processSelectExhibitorNewPrc(exhibitorNewDTO);
        mv.addObject("info", info);
        mv.setViewName("/mypage/index");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/selectBuyerSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BuyerNewDTO> mypage_step_selectBuyerNewSingle(@RequestBody BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsController > mypage_step_selectBuyerNewSingle");
        //System.out.println(buyerDTO.toString());

        BuyerNewDTO responseDTO = kibsService.processSelectBuyerNewSingle(buyerNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteBuyer.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteBuyerNew(@RequestBody BuyerNewDTO buyerNewDTO) {
        System.out.println("KibsController > mypage_step_deleteBuyerNew");
        //System.out.println(buyerDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteBuyerNew(buyerNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_modify(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_modify");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/mypage/modify");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/exhibitorNew/updateInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_exhibitor_updateInfo(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > mypage_step_exhibitor_updateInfo");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsService.processUpdateExhibitorNewInfo(exhibitorNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step01(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step01");
        ModelAndView mv = new ModelAndView();

        // 세션 ID로만 사용자 정보를 가져옵니다.
        String id = (String) session.getAttribute("id");
        if (id == null) {
            mv.setViewName("redirect:/main.do");
            return mv;
        }else{
            /* 기본정보 - 참가업체 정보 */
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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

            mv.setViewName("/mypage/step01");
        }

        return mv;
    }

    @RequestMapping(value = "/mypage/step/updateExhibitorNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateExhibitorNew(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > mypage_step_updateExhibitorNew");

        ResponseDTO responseDTO = kibsService.processUpdateExhibitorNew(exhibitorNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/updateProductNewFileNote.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateProductFileNote(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > mypage_step_updateProductFileNote");

        for(ProductNewDTO productNew: exhibitorNewDTO.getProductList()){
            kibsService.processUpdateProductNewFileNote(exhibitorNewDTO.getSeq(), productNew.getNote());
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResultCode(CommConstants.RESULT_CODE_SUCCESS);
        responseDTO.setResultMessage(CommConstants.RESULT_MSG_SUCCESS);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/updateOnlineNewFileNote.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateOnlineFileNote(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > mypage_step_updateOnlineFileNote");

        for(OnlineNewDTO onlineNew: exhibitorNewDTO.getOnlineList()){
            kibsService.processUpdateOnlineNewFileNote(exhibitorNewDTO.getSeq(), onlineNew.getNote());
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResultCode(CommConstants.RESULT_CODE_SUCCESS);
        responseDTO.setResultMessage(CommConstants.RESULT_MSG_SUCCESS);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteChargeNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteChargeNew(@RequestBody ChargeNewDTO ChargeNewDTO) {
        System.out.println("KibsController > mypage_step_deleteChargeNew");
        //System.out.println(onlineDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteChargeNew(ChargeNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteProductNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteProductNew(@RequestBody ProductNewDTO productNewDTO) {
        System.out.println("KibsController > mypage_step_deleteProductNew");
        //System.out.println(displayDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteProductNew(productNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteOnlineNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteOnlineNew(@RequestBody OnlineNewDTO onlineNewDTO) {
        System.out.println("KibsController > mypage_step_deleteOnlineNew");
        //System.out.println(onlineDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteOnlineNew(onlineNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteFile.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteFile(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsController > mypage_step_deleteFile");
        //System.out.println(fileDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteFile(fileDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_9.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_9(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_9");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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
        mv.setViewName("/mypage/step2_9");
        return mv;
    }

    @RequestMapping(value = "/mypage/step2_1.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_1(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_1");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/mypage/step2_1");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/updateExhibitBooth.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateExhibitBooth(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step_updateExhibitBooth");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processUpdateExhibitBoothSeq(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_10.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_10(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_10");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/mypage/step2_10");
        return mv;
    }
    
    @RequestMapping(value = "/mypage/step2_2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_2(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_2");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/mypage/step2_2");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/updateCompanySign.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateCompanySign(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step_updateCompanySign");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processUpdateCompanySignSeq(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_3.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_3(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_3");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/mypage/step2_3");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/updateExhibitUtility.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateExhibitUtility(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step_updateExhibitUtility");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processUpdateExhibitUtilitySeq(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_4.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_4(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_4");
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
            exDTO.setTransferYear(transferYear);
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            PassNewDTO reqDTO = new PassNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            passList = kibsService.processSelectPassNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("passList", passList);
            exhibitorNewDTO.setTransferYear(transferYear);
            ExhibitorNewDTO exhibitorInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/mypage/step2_4");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/deletePassNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deletePassNew(@RequestBody PassNewDTO passNewDTO) {
        System.out.println("KibsController > mypage_step_deletePassNew");
        ResponseDTO response = kibsService.processDeletePassNew(passNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_5.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_5(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_5");
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
            exDTO.setTransferYear(transferYear);
            exhibitor_new_seq = kibsService.processSearchExhibitorNewSeq(exDTO);

            GiftNewDTO reqDTO = new GiftNewDTO();
            reqDTO.setExSeq(exhibitor_new_seq);
            giftList = kibsService.processSelectGiftNewList(reqDTO);
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exhibitorNewDTO);
            mv.addObject("giftList", giftList);
            exhibitorNewDTO.setTransferYear(transferYear);
            ExhibitorNewDTO exhibitorInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/mypage/step2_5");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/updateExhibitGift.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateExhibitGift(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step_updateExhibitGift");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processUpdateExhibitGiftSeq(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_6.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_6() {
        System.out.println("KibsController > mypage_step2_6");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mypage/step2_6");
        return mv;
    }

    @RequestMapping(value = "/mypage/step2_7.do", method = RequestMethod.GET)
    public ModelAndView mypage_step2_7() {
        System.out.println("KibsController > mypage_step2_7");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mypage/step2_7");
        return mv;
    }

    @RequestMapping(value = "/mypage/step2_8.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_8(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_8");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            if(id != null && !id.equals("null") && !id.isEmpty()) {
                ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/mypage/step2_8");
        return mv;
    }

    @RequestMapping(value = "/mypage/step03.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step03(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step03");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
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

        mv.setViewName("/mypage/step03");
        return mv;
    }

    @RequestMapping(value = "/mypage/total.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_total(ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > mypage_total");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorNewDTO exInfo = null;
        String exhibitor_new_seq = exhibitorNewDTO.getSeq();
        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            exhibitorNewDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(exhibitorNewDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            ExhibitorNewDTO reqDTO = new ExhibitorNewDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorNewSingle(reqDTO);
            if(exInfo != null){
                exhibitor_new_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_new_seq != null && !exhibitor_new_seq.equals("null") && !exhibitor_new_seq.isEmpty()){
            mv.addObject("info", exInfo);

            /*BuyerDTO buyerDTO = new BuyerDTO();
            buyerDTO.setId(exhibitor_seq);
            List<BuyerDTO> buyerList = kibsService.processSelectBuyerListSeq(buyerDTO);
            mv.addObject("buyerList", buyerList);*/
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/mypage/total");
        return mv;
    }

    /**
     * [AJAX 용] 실시간 금액 계산 미리보기 (사용자용)
     * @param input (JS에서 보낸 모든 입력값)
     * @return CalculationResultDTO (JSON)
     */
    @PostMapping("/calculate-preview.do")
    @ResponseBody
    public CalculationResultDTO getPublicCalculationPreview(@RequestBody CalculationInputDTO input) {
        // JS에서 보낸 입력값(input)을 기반으로 즉시 계산하여 결과만 반환
        // (DB 저장은 없음)
        return calculationService.calculateTotals(input);
    }

    //***************************************************************************
    // Eng page
    //***************************************************************************

    @RequestMapping(value = "/eng/index.do", method = RequestMethod.GET)
    public ModelAndView eng_index() {
        System.out.println("KibsController > eng_index");
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
        String currentYear = "2026"; // 현재 대상 연도
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
        System.out.println("KibsController > eng_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/login");
        return mv;
    }

    //***************************************************************************
    // Eng guide page
    //***************************************************************************

    @RequestMapping(value = "/eng/guide/summary.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_summary() {
        System.out.println("KibsController > eng_guide_summary");
        ModelAndView mv = new ModelAndView();

        // 브로슈어
        String currentYear = "2026"; // 현재 대상 연도
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
        System.out.println("KibsController > eng_guide_venue");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/venue");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/to_kintex.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_to_kintex() {
        System.out.println("KibsController > eng_guide_to_kintex");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/to_kintex");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/to_ara.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_to_ara() {
        System.out.println("KibsController > eng_guide_to_ara");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/to_ara");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/bi.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_bi() {
        System.out.println("KibsController > eng_guide_bi");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/bi");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/brochure.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_brochure() {
        System.out.println("KibsController > eng_guide_brochure");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/brochure");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/certify.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_certify() {
        System.out.println("KibsController > eng_guide_certify");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/certify");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/contact.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_contact() {
        System.out.println("KibsController > eng_guide_contact");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/contact");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/program.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_program() {
        System.out.println("KibsController > eng_guide_program");
        ModelAndView mv = new ModelAndView();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String today = dateFormat.format(new Date());
        if (Long.parseLong(today) > Long.parseLong("202602130859")) {
            mv.addObject("mode", "open");
        }

        mv.setViewName("/eng/guide/program");
        return mv;
    }
    
    // 최미나가 추가함
    @RequestMapping(value = "/eng/guide/program2025.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_program2025() {
        System.out.println("KibsController > eng_guide_program2025");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/program2025");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/conference.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_conference() {
        System.out.println("KibsController > eng_guide_conference");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/conference");
        return mv;
    }

    @RequestMapping(value = "/eng/guide/conference2025.do", method = RequestMethod.GET)
    public ModelAndView eng_guide_conference2025() {
        System.out.println("KibsController > eng_guide_conference2025");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/guide/conference2025");
        return mv;
    }

    //***************************************************************************
    // Eng exhibitor page
    //***************************************************************************

    @RequestMapping(value = "/eng/exhibitor/categories.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_categories() {
        System.out.println("KibsController > eng_exhibitor_categories");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/categories");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/glance.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_glance() {
        System.out.println("KibsController > eng_exhibitor_glance");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/glance");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/marina.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_marina() {
        System.out.println("KibsController > eng_exhibitor_marina");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/marina");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/match.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_match() {
        System.out.println("KibsController > eng_exhibitor_match");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/match");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list() {
        System.out.println("KibsController > eng_exhibitor_list");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/qna.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_qna() {
        System.out.println("KibsController > eng_exhibitor_qna");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/qna");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<EnExhibitorListDTO>> eng_exhibitor_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > eng_exhibitor_selectList");
        //System.out.println(searchDTO.getPageNum());
        //System.out.println(searchDTO.getRows());

        List<EnExhibitorListDTO> responseList = kibsService.processSelectEnExhibitorList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/eng/exhibitor/privacy.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_privacy() {
        System.out.println("KibsController > eng_exhibitor_privacy");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/privacy");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/login.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_login() {
        System.out.println("KibsController > eng_exhibitor_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/login");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/findpw.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_findpw() {
        System.out.println("KibsController > eng_exhibitor_findpw");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/findpw");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list22.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list22() {
        System.out.println("KibsController > eng_exhibitor_list22");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list22");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list19.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list19() {
        System.out.println("KibsController > eng_exhibitor_list19");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list19");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list18.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list18() {
        System.out.println("KibsController > eng_exhibitor_list18");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list18");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list17.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list17() {
        System.out.println("KibsController > eng_exhibitor_list17");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list17");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list16.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list16() {
        System.out.println("KibsController > eng_exhibitor_list16");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list16");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list15.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list15() {
        System.out.println("KibsController > eng_exhibitor_list15");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list15");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list14.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list14() {
        System.out.println("KibsController > eng_exhibitor_list14");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list14");
        return mv;
    }

    @RequestMapping(value = "/eng/exhibitor/list13.do", method = RequestMethod.GET)
    public ModelAndView eng_exhibitor_list13() {
        System.out.println("KibsController > eng_exhibitor_list13");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/exhibitor/list13");
        return mv;
    }

    //***************************************************************************
    // Eng apply page
    //***************************************************************************

    @RequestMapping(value = "/eng/apply/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_apply_step01() {
        System.out.println("KibsController > eng_apply_step01");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step01");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_1.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_1() {
        System.out.println("KibsController > eng_apply_step2_1");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_1");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_2.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_2() {
        System.out.println("KibsController > eng_apply_step2_2");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_2");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_3.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_3() {
        System.out.println("KibsController > eng_apply_step2_3");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_3");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_4.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_4() {
        System.out.println("KibsController > eng_apply_step2_4");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_4");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_5.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_5() {
        System.out.println("KibsController > eng_apply_step2_5");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_5");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_6.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_6() {
        System.out.println("KibsController > eng_apply_step2_6");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_6");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_7.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_7() {
        System.out.println("KibsController > eng_apply_step2_7");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_7");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step2_8.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step2_8() {
        System.out.println("KibsController > eng_apply_step2_8");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step2_8");
        return mv;
    }

    @RequestMapping(value = "/eng/apply/step03.do", method = RequestMethod.GET)
    public ModelAndView eng_apply_step03() {
        System.out.println("KibsController > eng_apply_step03");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/apply/step03");
        return mv;
    }

    //***************************************************************************
    // Eng buyer page
    //***************************************************************************

    @RequestMapping(value = "/eng/buyer/apply.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_apply() {
        System.out.println("KibsController > eng_buyer_apply");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/apply");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/completed.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_completed() {
        System.out.println("KibsController > eng_buyer_completed");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/completed");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/glance.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_glance() {
        System.out.println("KibsController > eng_buyer_glance");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/glance");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/marina.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_marina() {
        System.out.println("KibsController > eng_buyer_marina");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/marina");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/match.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_match() {
        System.out.println("KibsController > eng_buyer_match");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/match");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/login.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_login() {
        System.out.println("KibsController > eng_buyer_login");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/login");
        return mv;
    }

    @RequestMapping(value = "/eng/buyer/mypage.do", method = RequestMethod.GET)
    public ModelAndView eng_buyer_mypage(String seq) {
        System.out.println("KibsController > eng_buyer_mypage");
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
        System.out.println("KibsController > eng_buyer_qna");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/buyer/qna");
        return mv;
    }

    //***************************************************************************
    // Eng board page
    //***************************************************************************

    @RequestMapping(value = "/eng/board/notice.do", method = RequestMethod.GET)
    public ModelAndView eng_board_notice() {
        System.out.println("KibsController > eng_board_notice");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/notice");
        return mv;
    }

    @RequestMapping(value = "/eng/board/kibstv.do", method = RequestMethod.GET)
    public ModelAndView eng_board_kibstv() {
        System.out.println("KibsController > eng_board_kibstv");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/kibstv");
        return mv;
    }

    @RequestMapping(value = "/eng/board/kibstv_cate.do", method = RequestMethod.GET)
    public ModelAndView eng_board_kibstv_cate() {
        System.out.println("KibsController > eng_board_kibstv_cate");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/kibstv_cate");
        return mv;
    }

    @RequestMapping(value = "/eng/board/media.do", method = RequestMethod.GET)
    public ModelAndView eng_board_media() {
        System.out.println("KibsController > eng_board_media");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/media");
        return mv;
    }

    @RequestMapping(value = "/eng/board/gallery.do", method = RequestMethod.GET)
    public ModelAndView eng_board_gallery() {
        System.out.println("KibsController > eng_board_gallery");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/gallery");
        return mv;
    }

    @RequestMapping(value = "/eng/board/newsletter.do", method = RequestMethod.GET)
    public ModelAndView eng_board_newsletter() {
        System.out.println("KibsController > eng_board_newsletter");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/newsletter");
        return mv;
    }

    @RequestMapping(value = "/eng/board/mediaView.do", method = RequestMethod.GET)
    public ModelAndView eng_board_mediaView() {
        System.out.println("KibsController > eng_board_mediaView");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/mediaView");
        return mv;
    }

    @RequestMapping(value = "/eng/board/newsletterView.do", method = RequestMethod.GET)
    public ModelAndView eng_board_newsletterView() {
        System.out.println("KibsController > eng_board_newsletterView");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/board/newsletterView");
        return mv;
    }

    @RequestMapping(value = "/eng/board/noticeView.do", method = RequestMethod.GET)
    public ModelAndView eng_board_noticeView(String id) {
        System.out.println("KibsController > eng_board_noticeView");
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

    @RequestMapping(value = "/eng/mypage/index.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_index() {
        System.out.println("KibsController > eng_mypage_index");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/index");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/modify.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_modify() {
        System.out.println("KibsController > eng_mypage_modify");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/modify");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step01.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step01() {
        System.out.println("KibsController > eng_mypage_step01");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step01");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_1.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_1() {
        System.out.println("KibsController > eng_mypage_step2_1");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_1");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_2.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_2() {
        System.out.println("KibsController > eng_mypage_step2_2");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_2");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_3.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_3() {
        System.out.println("KibsController > eng_mypage_step2_3");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_3");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_4.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_4() {
        System.out.println("KibsController > eng_mypage_step2_4");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_4");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_5.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_5() {
        System.out.println("KibsController > eng_mypage_step2_5");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_5");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_6.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_6() {
        System.out.println("KibsController > eng_mypage_step2_6");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_6");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_7.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_7() {
        System.out.println("KibsController > eng_mypage_step2_7");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_7");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step2_8.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step2_8() {
        System.out.println("KibsController > eng_mypage_step2_8");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step2_8");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/step03.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_step03() {
        System.out.println("KibsController > eng_mypage_step03");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/step03");
        return mv;
    }

    @RequestMapping(value = "/eng/mypage/total.do", method = RequestMethod.GET)
    public ModelAndView eng_mypage_total() {
        System.out.println("KibsController > eng_mypage_total");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/eng/mypage/total");
        return mv;
    }

    //***************************************************************************
    // Online
    //***************************************************************************

    @RequestMapping(value = "/eng/online/company.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView eng_online_company(HttpServletRequest request, String companyNameEn) {
        System.out.println("KibsController > eng_online_company");
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
        System.out.println("KibsController > eng_online_company_selectList");
        //System.out.println(mainOnlineDTO.toString());

        List<MainOnlineDTO> responseDTO = kibsService.processSelectEnOnlineListPaging(mainOnlineDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/eng/online/company_view.do", method = RequestMethod.GET)
    public ModelAndView eng_online_company_view(String seq) {
        System.out.println("KibsController > eng_online_company_view");
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
        System.out.println("KibsController > eng_online_product");
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
        System.out.println("KibsController > eng_online_product_selectList");
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
        System.out.println("KibsController > eng_online_product_view");
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

    //***************************************************************************
    // Custom
    //***************************************************************************

    @RequestMapping(value = "/checkDuplicateId.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Integer> checkDuplicateId(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > checkDuplicateId");
        Integer result = kibsService.checkDuplicateId(exhibitorNewDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchExhibitorNewSeq.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> searchExhibitorNewSeq(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > searchExhibitorNewSeq");
        String seq = kibsService.processSearchExhibitorNewSeq(exhibitorNewDTO);
        return new ResponseEntity<>(seq, HttpStatus.OK);
    }

    @RequestMapping(value = "/getExhibitorNewEmail.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getExhibitorNewEmail(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > getExhibitorNewEmail");
        String result = kibsService.getExhibitorNewEmail(exhibitorNewDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getExhibitorNewRepEmail.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getExhibitorNewRepEmail(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > getExhibitorNewRepEmail");
        String result = kibsService.getExhibitorNewRepEmail(exhibitorNewDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/preGetExhibitorEmail.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<String>> preGetExhibitorEmail(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > preGetExhibitorEmail");
        List<String> result = kibsService.preGetExhibitorEmail(exhibitorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateExhibitorNewPasswordInit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorNewPasswordInit(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        System.out.println("KibsController > updateExhibitorNewPasswordInit");
        ResponseDTO response = kibsService.processUpdateExhibitorNewPasswordInit(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/exhibit/loginForm.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> exhibit_loginForm(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > exhibit_loginForm");
        ResponseDTO response = kibsService.processLoginExhibitorNew(exhibitorNewDTO);
        if(response.getResultCode().equals("0")){
            session.setAttribute("status", "logon");
            session.setAttribute("lang", "KO");
            session.setAttribute("seq", exhibitorNewDTO.getSeq());
            session.setAttribute("id", exhibitorNewDTO.getId());
            session.setAttribute("transferYear", exhibitorNewDTO.getTransferYear());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertExhibitorNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertExhibitorNew(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > insertExhibitorNew");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processInsertExhibitorNew(exhibitorNewDTO);
        if(response.getResultCode().equals("0")){
            session.setAttribute("id", exhibitorNewDTO.getId());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/saveProductNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> saveProductNew(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > saveProductNew");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processSaveProductNew(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitorNewBooth.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorNewBooth(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitorNewBooth");
        ResponseDTO response = kibsService.processUpdateExhibitorNewBooth(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitorNewMaritime.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorNewMaritime(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitorNewMaritime");
        ResponseDTO response = kibsService.processUpdateExhibitorNewMaritime(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitorNewCompanySign.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorNewCompanySign(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitorNewCompanySign");
        ResponseDTO response = kibsService.processUpdateExhibitorNewCompanySign(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitorNewUtility.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorNewUtility(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitorNewUtility");
        ResponseDTO response = kibsService.processUpdateExhibitorNewUtility(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertPassNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertPassNew(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > insertPassNew");
        ResponseDTO response = kibsService.processInsertPassNew(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertGiftNew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertGiftNew(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > insertGiftNew");
        ResponseDTO response = kibsService.processInsertGiftNew(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/selectExhibitGift.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GiftDTO> selectExhibitGift(@RequestBody GiftDTO giftDTO) {
        System.out.println("KibsController > selectExhibitGift");
        //System.out.println(searchDTO.toString());

        GiftDTO response = kibsService.processSelectExhibitorGift(giftDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/deleteExhibitGift.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> apply_step_deleteExhibitGift(@RequestBody GiftDTO giftDTO) {
        System.out.println("KibsController > apply_step_deleteExhibitGift");
        //System.out.println(buyerDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteExhibitGift(giftDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getExhibitorSeq.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> getExhibitorSeq(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > getExhibitorSeq");
        //System.out.println(exhibitorDTO.toString());
        if(session.getAttribute("id") != null) {
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
        }else{
            exhibitorDTO.setId(exhibitorDTO.getId());
        }
        String exhibitorSeq = kibsService.processSearchExhibitorSeq(exhibitorDTO);
        ResponseDTO response = new ResponseDTO();
        response.setCustomValue(exhibitorSeq);
        response.setResultCode(CommConstants.RESULT_CODE_SUCCESS);
        response.setResultMessage(CommConstants.RESULT_MSG_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateWebbanner.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateWebbanner(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > updateWebbanner");
        //System.out.println(exhibitorDTO.toString());
        if(session.getAttribute("id") != null) {
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
        }else{
            exhibitorDTO.setId(exhibitorDTO.getId());
        }
        ResponseDTO response = kibsService.processUpdateWebbanner(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/directory/selectExhibitorInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ExhibitorDTO> selectExhibitorInfo(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > selectExhibitorInfo");
        //System.out.println(searchDTO.toString());

        ExhibitorDTO response = kibsService.processSelectExhibitorInfo(exhibitorDTO);

        if(response == null){
            response = new ExhibitorDTO();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/directory/selectChargeNewInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ChargeNewDTO>> selectChargeNewInfo(@RequestBody ChargeNewDTO chargeNewDTO) {
        System.out.println("KibsController > selectChargeNewInfo");
        //System.out.println(searchDTO.toString());

        List<ChargeNewDTO> responseList = kibsService.processSelectChargeNewList(chargeNewDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/directory/selectChargeInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ChargePersonDTO>> selectChargeInfo(@RequestBody ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsController > selectChargeInfo");
        //System.out.println(searchDTO.toString());

        List<ChargePersonDTO> responseList = kibsService.processSelectChargeInfo(chargePersonDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/directory/selectDisplayInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<DisplayDTO>> selectDisplayInfo(@RequestBody DisplayDTO displayDTO) {
        System.out.println("KibsController > selectDisplayInfo");
        //System.out.println(searchDTO.toString());

        List<DisplayDTO> responseList = kibsService.processSelectDisplayInfo(displayDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/directory/selectFileInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FileDTO> selectFileInfo(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsController > selectFileInfo");
        //System.out.println(searchDTO.toString());

        FileDTO response = kibsService.processSelectFileInfo(fileDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertDirectory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertDirectory(@RequestBody DirectoryDTO directoryDTO, HttpSession session) {
        System.out.println("KibsController > insertDirectory");
        ResponseDTO response = kibsService.processInsertDirectory(directoryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitorNewStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorNewStatus(@RequestBody ExhibitorNewDTO exhibitorNewDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitorNewStatus");
        ResponseDTO response = kibsService.processUpdateExhibitorNewApprovalStatus(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/file/selectFileList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<FileDTO>> processSelectFileList(@RequestBody FileDTO fileDTO) {
        System.out.println("KibsController > processSelectFileList");
        //System.out.println(fileDTO.toString());

        List<FileDTO> response = kibsService.processSelectFileList(fileDTO.getUserId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/exhibitor/pre/selectSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ExhibitorDTO> apply_exhibitor_pre_selectSingle(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > apply_exhibitor_pre_selectSingle");
        //System.out.println(searchDTO.toString());

        ExhibitorDTO info = kibsService.processSelectPreExhibitorSingle(exhibitorDTO);

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    //***************************************************************************
    // Common
    //***************************************************************************

    /**
     * Upload file response entity.
     *
     * @return the response entity
     */
    @RequestMapping(value = "/uploadFile.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> uploadFile(MultipartFile[] uploadFiles, String uploadFilePath) {

        //System.out.println(uploadFilePath); // exhibitor/{id}

        JSONObject obj = new JSONObject();
        ResponseDTO response = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        if(uploadFiles != null) {
            for (MultipartFile multipartFile : uploadFiles) {
                try {
                    /* Prod */
                    String path = ResourceUtils.getFile("/usr/local/tomcat/webapps/upload/" + uploadFilePath).toPath().toString();

                    /* Local */
                    //String path = ResourceUtils.getFile("C:/Users/slhge/project/Kibs/upload/" + uploadFilePath).toPath().toString();

                    File folder = new File(path);

                    // 해당 디렉토리가 없을경우 디렉토리를 생성
                    if (!folder.exists()) {
                        Files.createDirectories(folder.toPath());
                    }

                    String fileName = generateFileName(multipartFile);
                    File tmp = new File(path + "/" + fileName);
                    multipartFile.transferTo(tmp);
                } catch (Exception e) {
                    String eMessage = "[UPLOAD] Error : ";
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = String.format("%s - %s", eMessage, e.getMessage() == null ? "" : e.getMessage());
                }
            }
        }

        response.setResultCode(resultCode);
        response.setResultMessage(resultMessage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String generateFileName(MultipartFile multipartFile) {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return new SimpleDateFormat("yyyyMMdd").format(date)+"_"+multipartFile.getOriginalFilename();
    }

    @RequestMapping(value = "/sms/send.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SmsResponseDTO> processSmsSend(@RequestBody SmsDTO smsDTO) {
        System.out.println("KibsController > processSelectFileList");
        //System.out.println(fileDTO.toString());

        SmsResponseDTO response = commService.smsSend(smsDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @RequestMapping(value="/sitemap.xml", produces= {"application/xml"})
    @ResponseBody
    public ResponseEntity<String> sitemap (HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(commService.getSystemicSiteMap());
    }
    /**
     * Robots string.
     *
     * @return the string
     */
    /* robots.txt */
    @RequestMapping(value = "/robots.txt")
    @ResponseBody
    public String robots() {
        return "User-agent: *\nAllow: /\n";
    }

}