package com.mtf.kibs.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.service.KibsService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import java.time.LocalDate;
import java.util.*;

@Controller
public class KibsController {

    // 필드 주입이 아닌 생성자 주입형태로 사용합니다. '생성자 주입 형태'로 사용합니다.
    private final KibsService kibsService;

    private final CommService commService;

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
        LocalDate now = LocalDate.now();
        String transferYear = String.valueOf(now.getYear());
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

        // 방문자 수 카운트
        kibsService.processStatisticsAccessor();

        mv.setViewName("main");
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
        mv.setViewName("/guide/summary");
        return mv;
    }

    @RequestMapping(value = "/guide/program.do", method = RequestMethod.GET)
    public ModelAndView guide_program() {
        System.out.println("KibsController > guide_program");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/guide/program");
        return mv;
    }

    //***************************************************************************
    // apply Folder
    //***************************************************************************

    @RequestMapping(value = "/apply/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step01(String mode, ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > apply_step01");
        ModelAndView mv = new ModelAndView();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String today = dateFormat.format(new Date());
        if(Long.parseLong(today) > Long.parseLong("202502261559")){
            mv.addObject("mode", "end");
        }

        if(mode != null && !"".equals(mode)){
            mv.addObject("mode", mode);
        }

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            if(id != null && !id.equals("null") && !id.equals("")) {
                ExhibitorDTO reqDTO = new ExhibitorDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(transferYear);
                exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
                if (exInfo != null) {
                    exhibitor_seq = exInfo.getSeq();
                }
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);

            /* 담당자정보*/
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(exhibitor_seq);
            List<ChargePersonDTO> chargeList = kibsService.processSelectChargeListSeq(chargeReq);
            mv.addObject("chargeList", chargeList);

            /* 전시정보 */
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(exhibitor_seq);
            List<DisplayDTO> displayList = kibsService.processSelectDisplayListSeq(displayReq);
            mv.addObject("displayList", displayList);

            /* 온라인정보 */
            OnlineDTO onlineReq = new OnlineDTO();
            onlineReq.setId(exhibitor_seq);
            List<OnlineDTO> onlineList = kibsService.processSelectOnlineListSeq(onlineReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerDTO buyerReq = new BuyerDTO();
            buyerReq.setId(exhibitor_seq);
            List<BuyerDTO> buyerList = kibsService.processSelectBuyerListSeq(buyerReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(exhibitor_seq);
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_seq);
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

        mv.setViewName("/apply/step01");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_1.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_1(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_1");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);
        }else{
            session.invalidate(); //세션 초기화
        }
        mv.setViewName("/apply/step2_1");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_2(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_2");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/apply/step2_2");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_3.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_3(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_3");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/apply/step2_3");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_4.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_4(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_4");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        String exhibitor_seq = exhibitorDTO.getSeq();
        List<PassDTO> passList = new ArrayList<>();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            PassDTO passDTO = new PassDTO();
            passDTO.setId(exhibitor_seq);
            passList = kibsService.processSelectPassList(passDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));

            ExhibitorDTO exDTO = new ExhibitorDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(transferYear);
            exhibitor_seq = kibsService.processSearchExhibitorSeq(exDTO);

            PassDTO reqDTO = new PassDTO();
            reqDTO.setUserId(id);
            reqDTO.setTransferYear(transferYear);
            passList = kibsService.processSelectPassList(reqDTO);
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exhibitorDTO);
            mv.addObject("passList", passList);

            ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(exhibitor_seq);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }

        mv.setViewName("/apply/step2_4");
        return mv;
    }

    @RequestMapping(value = "/apply/step2_5.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step2_5(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > apply_step2_5");

        ModelAndView mv = new ModelAndView();
        /* 기본정보 - 참가업체 정보 */
        String exhibitor_seq = exhibitorDTO.getSeq();
        List<GiftDTO> giftList = new ArrayList<>();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            GiftDTO giftDTO = new GiftDTO();
            giftDTO.setId(exhibitor_seq);
            giftList = kibsService.processSelectGiftList(giftDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));

            ExhibitorDTO exDTO = new ExhibitorDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(transferYear);
            exhibitor_seq = kibsService.processSearchExhibitorSeq(exDTO);

            GiftDTO reqDTO = new GiftDTO();
            reqDTO.setUserId(id);
            reqDTO.setTransferYear(transferYear);
            giftList = kibsService.processSelectGiftList(reqDTO);
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exhibitorDTO);
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
    public ModelAndView apply_step2_8(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_8");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            if(id != null && !id.equals("null") && !id.equals("")) {
                ExhibitorDTO reqDTO = new ExhibitorDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(transferYear);
                exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
                if (exInfo != null) {
                    exhibitor_seq = exInfo.getSeq();
                }
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);

            /* 담당자정보*/
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(exhibitor_seq);
            List<ChargePersonDTO> chargeList = kibsService.processSelectChargeListSeq(chargeReq);
            mv.addObject("chargeList", chargeList);

            /* 전시정보 */
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(exhibitor_seq);
            List<DisplayDTO> displayList = kibsService.processSelectDisplayListSeq(displayReq);
            mv.addObject("displayList", displayList);

            /* 파일정보 */
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_seq);
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                if(fileNote.equals("companyLicense")){
                    mv.addObject("companyLicenseFile", fileInfo);
                }
            }
        }

        mv.setViewName("/apply/step2_8");
        return mv;
    }

    @RequestMapping(value = "/apply/step03.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apply_step03(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > apply_step03");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
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
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String today = dateFormat.format(new Date());
        if(Long.parseLong(today) > Long.parseLong("202502260000")){
            mv.addObject("mode", "end");
        }

        if(mode != null && !"".equals(mode)){
            mv.addObject("mode", mode);
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
        System.out.println("KibsController > checkDuplicateId");
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

    @RequestMapping(value = "/board/gallery.do", method = RequestMethod.GET)
    public ModelAndView board_gallery() {
        System.out.println("KibsController > board_gallery");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/board/gallery");
        return mv;
    }

    @RequestMapping(value = "/board/gallery/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<GalleryDTO>> board_gallery_selectList(@RequestBody SearchDTO searchDTO) {
        System.out.println("KibsController > board_gallery_selectList");
        //System.out.println(searchDTO.toString());

        List<GalleryDTO> responseList = kibsService.processSelectGalleryList(searchDTO);

        for(GalleryDTO response : responseList){
            List<String> fullFilePathList = new ArrayList<>();
            List<FileDTO> fileList = kibsService.processSelectFileList(response.getId());
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

        ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(seq);
        mv.addObject("exhibitorInfo", exhibitorInfo);

        List<OnlineDTO> onlineList = kibsService.processSelectOnlineInfoList(seq);
        mv.addObject("onlineList", onlineList);

        List<FileDTO> fileList = kibsService.processSelectFileList(seq);
        mv.addObject("fileList", fileList);

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
        for(int i=0; i<relatedItemList.size(); i++){
            FileDTO relatedItemFileRequestDto = new FileDTO();
            relatedItemFileRequestDto.setUserId(relatedItemList.get(i).getId());
            relatedItemFileRequestDto.setNote(relatedItemList.get(i).getNote());

            List<FileDTO> productFileResponseDto = kibsService.processSelectProductFileInfo(relatedItemFileRequestDto);
            if(productFileResponseDto != null && !productFileResponseDto.isEmpty()){
                relatedItemList.get(i).setProductFullFilePath(productFileResponseDto.get(0).getFullFilePath());
            }

            FileDTO logoFileResponseDto = kibsService.processSelectLogoFileInfo(relatedItemFileRequestDto);
            if(logoFileResponseDto != null){
                relatedItemList.get(i).setLogoFullFilePath(logoFileResponseDto.getFullFilePath());
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

        OnlineDTO onlineInfo = kibsService.processSelectOnlineInfo(seq);
        mv.addObject("onlineInfo", onlineInfo);

        if(onlineInfo != null){
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
            for(int i=0; i<relatedItemList.size(); i++){
                FileDTO relatedItemFileRequestDto = new FileDTO();
                relatedItemFileRequestDto.setUserId(relatedItemList.get(i).getId());
                relatedItemFileRequestDto.setNote(relatedItemList.get(i).getNote());
                List<FileDTO> relatedItemFileResponseList = kibsService.processSelectProductFileInfo(relatedItemFileRequestDto);
                productFileList.add(relatedItemFileResponseList);
            }
            mv.addObject("relatedItemList", relatedItemList);
            mv.addObject("relatedItemFileList", productFileList);

        }
        
        mv.setViewName("/online/product_view");
        return mv;
    }

    //***************************************************************************
    // mypage Folder
    //***************************************************************************

    @RequestMapping(value = "/mypage/index.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_index(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_index");
        //System.out.println(exhibitorDTO.toString());
        ModelAndView mv = new ModelAndView();
        ExhibitorDTO exInfo = null;
        if(exhibitorDTO.getId() != null && !"null".equals(exhibitorDTO.getId())){
            exInfo = kibsService.processSelectExhibitorPrc(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorPrc(reqDTO);
        }

        mv.addObject("info", exInfo);

        mv.setViewName("/mypage/index");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/selectBuyerSingle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BuyerDTO> mypage_step_selectBuyerSingle(@RequestBody BuyerDTO buyerDTO) {
        System.out.println("KibsController > mypage_step_selectBuyerSingle");
        //System.out.println(buyerDTO.toString());

        BuyerDTO responseDTO = kibsService.processSelectBuyerSingle(buyerDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteBuyer.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteBuyer(@RequestBody BuyerDTO buyerDTO) {
        System.out.println("KibsController > mypage_step_deleteBuyer");
        //System.out.println(buyerDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteBuyer(buyerDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_modify(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_modify");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);
        }

        mv.setViewName("/mypage/modify");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/exhibitor/updatePersonalInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_exhibitor_updatePersonalInfo(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mypage_step_exhibitor_updatePersonalInfo");
        //System.out.println(exhibitorDTO.toString());

        ResponseDTO responseDTO = kibsService.processUpdateExhibitPersonalInfo(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step01(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step01");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);

            /* 담당자정보*/
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(exhibitor_seq);
            List<ChargePersonDTO> chargeList = kibsService.processSelectChargeListSeq(chargeReq);
            mv.addObject("chargeList", chargeList);

            /* 전시정보 */
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(exhibitor_seq);
            List<DisplayDTO> displayList = kibsService.processSelectDisplayListSeq(displayReq);
            mv.addObject("displayList", displayList);

            /* 온라인정보 */
            OnlineDTO onlineReq = new OnlineDTO();
            onlineReq.setId(exhibitor_seq);
            List<OnlineDTO> onlineList = kibsService.processSelectOnlineListSeq(onlineReq);
            mv.addObject("onlineList", onlineList);

            /* 바이어정보 */
            BuyerDTO buyerReq = new BuyerDTO();
            buyerReq.setId(exhibitor_seq);
            List<BuyerDTO> buyerList = kibsService.processSelectBuyerListSeq(buyerReq);
            mv.addObject("buyerList", buyerList);

            /* 파일정보 */
            FileDTO fileReq = new FileDTO();
            fileReq.setUserId(exhibitor_seq);
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_seq);
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
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/mypage/step01");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/updateExhibitor.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateExhibitor(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mypage_step_updateExhibitor");

        ResponseDTO responseDTO = kibsService.processUpdateExhibitor(exhibitorDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/updateOnlineFileNote.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateOnlineFileNote(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > mypage_step_updateOnlineFileNote");

        for(OnlineDTO online: exhibitorDTO.getOnlineExhibitList()){
            kibsService.processUpdateOnlineFileNote(exhibitorDTO.getSeq(), online.getNote());
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResultCode(CommConstants.RESULT_CODE_SUCCESS);
        responseDTO.setResultMessage(CommConstants.RESULT_MSG_SUCCESS);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteCharge.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteCharge(@RequestBody ChargePersonDTO chargePersonDTO) {
        System.out.println("KibsController > mypage_step_deleteCharge");
        //System.out.println(onlineDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteCharge(chargePersonDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteDisplay.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteDisplay(@RequestBody DisplayDTO displayDTO) {
        System.out.println("KibsController > mypage_step_deleteDisplay");
        //System.out.println(displayDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteDisplay(displayDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/deleteOnline.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteOnline(@RequestBody OnlineDTO onlineDTO) {
        System.out.println("KibsController > mypage_step_deleteOnline");
        //System.out.println(onlineDTO.toString());

        ResponseDTO responseDTO = kibsService.processDeleteOnline(onlineDTO);

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

    @RequestMapping(value = "/mypage/step2_1.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_1(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_1");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
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

    @RequestMapping(value = "/mypage/step2_2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_2(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_2");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
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
    public ModelAndView mypage_step2_3(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_3");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
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
    public ModelAndView mypage_step2_4(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_4");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        String exhibitor_seq = exhibitorDTO.getSeq();
        List<PassDTO> passList = new ArrayList<>();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            PassDTO passDTO = new PassDTO();
            passDTO.setId(exhibitor_seq);
            passList = kibsService.processSelectPassList(passDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));

            ExhibitorDTO exDTO = new ExhibitorDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(transferYear);
            exhibitor_seq = kibsService.processSearchExhibitorSeq(exDTO);

            PassDTO reqDTO = new PassDTO();
            reqDTO.setUserId(id);
            reqDTO.setTransferYear(transferYear);
            passList = kibsService.processSelectPassList(reqDTO);
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exhibitorDTO);
            mv.addObject("passList", passList);

            ExhibitorDTO exhibitorInfo = kibsService.processSelectOnlineExhibitorInfo(exhibitor_seq);
            mv.addObject("exhibitorInfo", exhibitorInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/mypage/step2_4");
        return mv;
    }

    @RequestMapping(value = "/mypage/step/deleteExhibitPass.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_deleteExhibitPass(@RequestBody PassDTO passDTO) {
        System.out.println("KibsController > mypage_step_deleteExhibitPass");
        ResponseDTO response = kibsService.processDeleteExhibitPass(passDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step/updateExhibitPass.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mypage_step_updateExhibitPass(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step_updateExhibitPass");
        //System.out.println(exhibitorDTO.toString());
        if(exhibitorDTO.getId() == null){
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
            List<PassDTO> passList = new ArrayList<>();
            for(PassDTO pass : exhibitorDTO.getPassList()){
                pass.setUserId(String.valueOf(session.getAttribute("id")));
                passList.add(pass);
            }
            exhibitorDTO.setPassList(passList);
        }
        ResponseDTO response = kibsService.processUpdateExhibitPassSeq(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mypage/step2_5.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step2_5(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_5");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        String exhibitor_seq = exhibitorDTO.getSeq();
        List<GiftDTO> giftList = new ArrayList<>();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            GiftDTO giftDTO = new GiftDTO();
            giftDTO.setId(exhibitor_seq);
            giftList = kibsService.processSelectGiftList(giftDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));

            ExhibitorDTO exDTO = new ExhibitorDTO();
            exDTO.setId(id);
            exDTO.setTransferYear(transferYear);
            exhibitor_seq = kibsService.processSearchExhibitorSeq(exDTO);

            GiftDTO reqDTO = new GiftDTO();
            reqDTO.setUserId(id);
            reqDTO.setTransferYear(transferYear);
            giftList = kibsService.processSelectGiftList(reqDTO);
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exhibitorDTO);
            mv.addObject("giftList", giftList);
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
    public ModelAndView mypage_step2_8(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step2_8");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            if(id != null && !id.equals("null") && !id.equals("")) {
                ExhibitorDTO reqDTO = new ExhibitorDTO();
                reqDTO.setId(id);
                reqDTO.setTransferYear(transferYear);
                exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
                if (exInfo != null) {
                    exhibitor_seq = exInfo.getSeq();
                }
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);

            /* 담당자정보*/
            ChargePersonDTO chargeReq = new ChargePersonDTO();
            chargeReq.setId(exhibitor_seq);
            List<ChargePersonDTO> chargeList = kibsService.processSelectChargeListSeq(chargeReq);
            mv.addObject("chargeList", chargeList);

            /* 전시정보 */
            DisplayDTO displayReq = new DisplayDTO();
            displayReq.setId(exhibitor_seq);
            List<DisplayDTO> displayList = kibsService.processSelectDisplayListSeq(displayReq);
            mv.addObject("displayList", displayList);

            /* 파일정보 */
            List<FileDTO> fileList = kibsService.processSelectFileList(exhibitor_seq);
            for(int i=0; i<fileList.size(); i++){
                FileDTO fileInfo = fileList.get(i);
                String fileNote = fileInfo.getNote().replaceAll("[0-9]", "").replaceAll("[_]", "");
                if(fileNote.equals("companyLicense")){
                    mv.addObject("companyLicenseFile", fileInfo);
                }
            }
        }

        mv.setViewName("/mypage/step2_8");
        return mv;
    }

    @RequestMapping(value = "/mypage/step03.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_step03(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_step03");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            mv.addObject("info", exInfo);
        }else{
            session.invalidate(); //세션 초기화
        }

        mv.setViewName("/mypage/step03");
        return mv;
    }

    @RequestMapping(value = "/mypage/total.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage_total(ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > mypage_total");
        ModelAndView mv = new ModelAndView();

        /* 기본정보 - 참가업체 정보 */
        ExhibitorDTO exInfo = null;
        String exhibitor_seq = exhibitorDTO.getSeq();
        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
            exInfo = kibsService.processSelectExhibitorSingle(exhibitorDTO);
        }else{
            String id = String.valueOf(session.getAttribute("id"));
            String transferYear = String.valueOf(session.getAttribute("transferYear"));
            ExhibitorDTO reqDTO = new ExhibitorDTO();
            reqDTO.setId(id);
            reqDTO.setTransferYear(transferYear);
            exInfo = kibsService.processSelectExhibitorSingle(reqDTO);
            if(exInfo != null){
                exhibitor_seq = exInfo.getSeq();
            }
        }

        if(exhibitor_seq != null && !exhibitor_seq.equals("null") && !exhibitor_seq.equals("")){
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
        mv.setViewName("/eng/guide/program");
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
    // Custom
    //***************************************************************************

    @RequestMapping(value = "/checkDuplicateId.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Integer> checkDuplicateId(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > checkDuplicateId");
        Integer result = kibsService.checkDuplicateId(exhibitorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getExhibitorEmail.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getExhibitorEmail(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > getExhibitorEmail");
        String result = kibsService.getExhibitorEmail(exhibitorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateExhibitorPasswordInit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitorPasswordInit(@RequestBody ExhibitorDTO exhibitorDTO) {
        System.out.println("KibsController > updateExhibitorPasswordInit");
        ResponseDTO response = kibsService.processUpdateExhibitorPasswordInit(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/exhibit/loginForm.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> exhibit_loginForm(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > exhibit_loginForm");
        ResponseDTO response = kibsService.processLoginExhibit(exhibitorDTO);
        if(response.getResultCode().equals("0")){
            session.setAttribute("status", "logon");
            session.setAttribute("lang", "KO");
            session.setAttribute("id", exhibitorDTO.getId());
            session.setAttribute("transferYear", exhibitorDTO.getTransferYear());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertExhibitor.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertExhibitor(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > insertExhibitor");
        //System.out.println(exhibitorDTO.toString());
        ResponseDTO response = kibsService.processInsertExhibitor(exhibitorDTO);
        if(response.getResultCode().equals("0")){
            session.setAttribute("id", exhibitorDTO.getId());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitBooth.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitBooth(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitBooth");
        ResponseDTO response = kibsService.processUpdateExhibitBooth(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateCompanySign.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateCompanySign(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > updateCompanySign");
        if(exhibitorDTO.getId() == null){
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
        }
        ResponseDTO response = kibsService.processUpdateCompanySign(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitUtility.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitUtility(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitUtility");
        if(exhibitorDTO.getId() == null){
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
        }
        ResponseDTO response = kibsService.processUpdateExhibitUtility(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertExhibitPass.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertExhibitPass(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > insertExhibitPass");
        if(exhibitorDTO.getId() == null){
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
            List<PassDTO> passList = new ArrayList<>();
            for(PassDTO pass : exhibitorDTO.getPassList()){
                pass.setUserId(String.valueOf(session.getAttribute("id")));
                passList.add(pass);
            }
            exhibitorDTO.setPassList(passList);
        }
        ResponseDTO response = kibsService.processInsertExhibitPass(exhibitorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/insertExhibitGift.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> insertExhibitGift(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > insertExhibitGift");
        if(exhibitorDTO.getId() == null){
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
        }
        ResponseDTO response = kibsService.processInsertExhibitGift(exhibitorDTO);
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
        if(session.getAttribute("id") != null) {
            directoryDTO.setUserId(String.valueOf(session.getAttribute("id")));
        }

        ExhibitorDTO exhibitorDTO = new ExhibitorDTO();
        exhibitorDTO.setId(directoryDTO.getUserId());
        exhibitorDTO.setTransferYear(directoryDTO.getTransferYear());
        String exhibitorSeq = kibsService.processSearchExhibitorSeq(exhibitorDTO);
        directoryDTO.setId(exhibitorSeq); // 업체 ID

        ResponseDTO response = kibsService.processInsertDirectory(directoryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply/step/updateExhibitStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateExhibitApprovalStatus(@RequestBody ExhibitorDTO exhibitorDTO, HttpSession session) {
        System.out.println("KibsController > updateExhibitApprovalStatus");
        if(session.getAttribute("id") != null) {
            exhibitorDTO.setId(String.valueOf(session.getAttribute("id")));
        }else{
            exhibitorDTO.setId(exhibitorDTO.getId());
        }
        ResponseDTO response = kibsService.processUpdateExhibitApprovalStatus(exhibitorDTO);
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