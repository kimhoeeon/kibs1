package com.mtf.kibs.controller;

import com.mtf.kibs.dto.*;
import com.mtf.kibs.service.CalculationService;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.util.AES256Util;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * The type Kibs page controller.
 */
@Controller
public class KibsMngNewController {

    private final KibsMngService kibsMngService;
    private final CommService commService;
    private final AES256Util aes256Util; // 복호화를 위한 AES256Util 추가

    @Autowired
    public KibsMngNewController(KibsMngService kms, CommService cs, AES256Util aes256Util){
        this.kibsMngService = kms;
        this.commService = cs;
        this.aes256Util = aes256Util;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_new_new_participant_company(String nameKo) {
        ModelAndView mv = new ModelAndView();
        if(nameKo != null){
            mv.addObject("nameKo", nameKo);
        }
        mv.setViewName("/mng/exhibitorNewNew/participant/company");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNewNew_participant_company_selectList(@RequestBody SearchDTO searchDTO) {
        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectListExhibitorNew(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNewNew_company_detail(String seq, String lang) {
        ModelAndView mv = new ModelAndView();
        // lang 파라미터가 없으면 업체의 신청 언어를 사용 (이전에는 lang 미지정 시 NPE 로 500 발생)
        String resolvedLang = lang;
        if(seq != null && !seq.isEmpty()){
            /* 전시업체정보 */
            ExhibitorNewDTO requestDto = new ExhibitorNewDTO();
            requestDto.setSeq(seq);
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewSingle(requestDto);

            if(info != null){
                mv.addObject("info", info);
                if (resolvedLang == null || resolvedLang.isEmpty()) {
                    resolvedLang = info.getLang();
                }

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
        if("EN".equals(resolvedLang)){
            mv.setViewName("/mng/exhibitorNewNew/participant/company/detail_en");
        }else{
            mv.setViewName("/mng/exhibitorNewNew/participant/company/detail");
        }
        return mv;
    }

    //***************************************************************************
    // exhibitor/application Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/booth");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_booth_selectList(@RequestBody SearchDTO searchDTO) {
        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewBoothList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewInvoiceDetail(seq);
            mv.addObject("info", info);

            // 3. 해당 참가업체의 '전시부스 인보이스' 목록 조회
            List<InvoiceBoothDTO> boothInvoiceList  = kibsMngService.getInvoiceBoothList(seq);
            mv.addObject("boothInvoiceList", boothInvoiceList);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/booth/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility_invoice_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            InvoiceUtilityDTO invoiceInfo = kibsMngService.processSelectInvoiceUtilitySingle(seq);
            mv.addObject("invoiceInfo", invoiceInfo);

            ExhibitorNewDTO exhibitorNewDTO = new ExhibitorNewDTO();
            exhibitorNewDTO.setSeq(invoiceInfo.getExhibitorSeq());
            ExhibitorNewDTO exhibitorBaseInfo = kibsMngService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorBaseInfo", exhibitorBaseInfo);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/utility/invoice");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBooth(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth_invoice_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            InvoiceBoothDTO invoiceInfo = kibsMngService.processSelectInvoiceBoothSingle(seq);
            mv.addObject("invoiceInfo", invoiceInfo);

            ExhibitorNewDTO exhibitorNewDTO = new ExhibitorNewDTO();
            exhibitorNewDTO.setSeq(invoiceInfo.getExhibitorSeq());
            ExhibitorNewDTO exhibitorNewInfo = kibsMngService.processSelectExhibitorNewSingle(exhibitorNewDTO);
            mv.addObject("exhibitorNewInfo", exhibitorNewInfo);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/booth/invoice");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_delete(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewBoothInvoice(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_delete(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewUtilityInvoice(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/filePath/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_filePath_update(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBoothInvoiceFilePath(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/filePath/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_filePath_update(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtilityInvoiceFilePath(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/mail/result/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_mail_result_update(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBoothInvoiceSendResult(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/mail/result/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_mail_result_update(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtilityInvoiceSendResult(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_sign() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/sign");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_sign_selectList(@RequestBody SearchDTO searchDTO) {
        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewSignList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_sign_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO exhibitorInfo = kibsMngService.processSelectExhibitorNewSignSingle(seq);
            mv.addObject("info", exhibitorInfo);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/sign/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_sign_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewSign(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/utility");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_utility_selectList(@RequestBody SearchDTO searchDTO) {
        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewUtilityList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewInvoiceDetail(seq);
            mv.addObject("info", info);

            List<InvoiceUtilityDTO> utilityInvoiceList = kibsMngService.getInvoiceUtilityList(seq);
            mv.addObject("utilityInvoiceList", utilityInvoiceList);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/utility/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtility(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/invoice/mail/open/update.do", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void mng_exhibitorNew_application_invoice_mail_open_update(@RequestParam("hseq") int historySeq) {
        kibsMngService.updateInvoiceHistoryStatusToOpen(historySeq);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_pass() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/pass");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngPassDTO>> mng_exhibitorNew_application_pass_selectList(@RequestBody SearchDTO searchDTO) {
        List<MngPassDTO> responseList = kibsMngService.processSelectExhibitorNewPassList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_pass_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngPassDTO> passList = kibsMngService.processSelectExhibitorNewPassDetailList(seq);
            mv.addObject("passList", passList);
            mv.addObject("exSeq", seq);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/pass/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_pass_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewPass(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_pass_delete(@RequestBody PassNewDTO passNewDTO) {
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewPass(passNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_buyer() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/buyer");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngBuyerDTO>> mng_exhibitorNew_application_buyer_selectList(@RequestBody SearchDTO searchDTO) {
        List<MngBuyerDTO> responseList = kibsMngService.processSelectExhibitorNewBuyerList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_buyer_detail(String seq) {
        ModelAndView mv = new ModelAndView();
        if(seq != null){
            List<MngBuyerDTO> buyerList = kibsMngService.processSelectExhibitorNewBuyerDetailList(seq);
            mv.addObject("buyerList", buyerList);
            mv.addObject("exSeq", seq);
        }
        mv.setViewName("/mng/exhibitorNewNew/application/buyer/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_buyer_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBuyer(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_buyer_delete(@RequestBody BuyerNewDTO buyerNewDTO) {
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewBuyer(buyerNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_gift() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/gift");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngGiftDTO>> mng_exhibitorNew_application_gift_selectList(@RequestBody SearchDTO searchDTO) {
        List<MngGiftDTO> responseList = kibsMngService.processSelectExhibitorNewGiftList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_gift_detail(String seq) {
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
        mv.setViewName("/mng/exhibitorNewNew/application/gift/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_gift_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewGift(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_gift_delete(@RequestBody GiftNewDTO giftNewDTO) {
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewGift(giftNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_online() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/online");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_online_selectList(@RequestBody SearchDTO searchDTO) {
        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectMngOnlineNewList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_online_detail(String seq) {
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
        mv.setViewName("/mng/exhibitorNewNew/application/online/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online/updateViewYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_online_updateViewYn(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewOnlineViewYn(exhibitorNewDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/product.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_product() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/product");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/product/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngProductDTO>> mng_exhibitorNew_application_product_selectList(@RequestBody SearchDTO searchDTO) {
        List<MngProductDTO> responseList = kibsMngService.processSelectMngProductNewList(searchDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/product/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_product_detail(String seq) {
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
        mv.setViewName("/mng/exhibitorNewNew/application/product/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/updateExhibitorNewPrcYn.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_participant_company_updateExhibitorNewPrcYn(@RequestBody List<ExhibitorNewDTO> exhibitorList) {
        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewPrcYn(exhibitorList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/invoice/detail.do", method = RequestMethod.POST)
    public ModelAndView mng_exhibitorNewNew_company_invoice_detail(String seq) {
        ModelAndView mv = new ModelAndView();

        if(seq != null && !seq.isEmpty()){
            // 1. 참가업체 통합 정보 조회 (기존 로직)
            ExhibitorNewDTO info = kibsMngService.processSelectExhibitorNewInvoiceDetail(seq);
            mv.addObject("info", info);

            // 3. 해당 참가업체의 '전시부스 인보이스' 목록 조회
            List<InvoiceBoothDTO> boothInvoiceList  = kibsMngService.getInvoiceBoothList(seq);
            mv.addObject("boothInvoiceList", boothInvoiceList);

            // 4. 유틸리티 인보이스 목록 조회 로직 추가
            List<InvoiceUtilityDTO> utilityInvoiceList = kibsMngService.getInvoiceUtilityList(seq);
            mv.addObject("utilityInvoiceList", utilityInvoiceList);
        }

        mv.setViewName("/mng/exhibitorNewNew/participant/company/invoice/detail");
        return mv;
    }

    @PostMapping("/mng/exhibitorNewNew/application/invoice/updateFilePath.do")
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

    @PostMapping("/mng/exhibitorNewNew/application/invoice/booth/delete.do")
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

    @PostMapping("/mng/exhibitorNewNew/application/invoice/utility/delete.do")
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

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/invoice/saveSpecialDiscount.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> mng_exhibitorNew_saveSpecialDiscount(@RequestBody ExhibitorNewDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
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

    @PostMapping("/mng/exhibitorNewNew/application/booth/invoice/insert.do")
    @ResponseBody
    public Map<String, Object> createBoothInvoice(@RequestBody Map<String, String> payload) {
        Map<String, Object> resultMap = new HashMap<>();
        String exhibitorSeq = payload.get("exSeq");

        try {
            InvoiceBoothDTO newInvoice = kibsMngService.createAndInsertInvoiceBooth(exhibitorSeq);
            resultMap.put("resultCode", "0");
            resultMap.put("resultMsg", "인보이스가 생성되었습니다.");
            resultMap.put("customValue", newInvoice.getInvoiceSeq());
        } catch (Exception e) {
            resultMap.put("resultCode", "-1");
            resultMap.put("resultMsg", "인보이스 생성 중 오류 발생: " + e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/mng/exhibitorNewNew/application/utility/invoice/insert.do")
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

    @RequestMapping(value = "/mng/exhibitorNewNew/download.do", method = RequestMethod.GET)
    public void exhibitorNewNew_download(HttpServletRequest req, HttpServletResponse res) {
        String fileName = req.getParameter("fileName");
        String transferYear = req.getParameter("transferYear");

        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            List<String> headerList = new ArrayList<>();

            String[] fixedHeaders1 = {
                    "No", "참가상태", "승인구분", "부스번호", "아이디", "등록일", "최종수정일", "사업자등록번호", "회사명(국문)", "회사명(영문)", "본사 주소", "본사 상세주소", "공장 주소", "공장 상세 주소", "대표자", "전화", "홈페이지", "Fax", "산업 분류", "산업 분류 기타", "기참가연도", "회원사 여부",
                    "금액", "내용", "수납여부", "입금일", "입금예정일", "세금계산서", "입금자", "확인자", "코멘트", "등록일시", "수정일시",
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    "성명", "직위", "부서", "전화번호", "휴대전화", "이메일", "성명", "직위", "부서", "전화번호", "휴대전화", "이메일", "성명", "직위", "부서", "전화번호", "휴대전화", "이메일",
                    "회사소개영상", "회사소개(국문)", "회사소개(영문)", "KIBS참가목적(국문)", "KIBS참가목적(영문)", "신제품출품 사항 소개(국문)", "신제품출품 사항 소개(영문)", "프로모션 정보",
                    "참가행사", "1순위", "2순위", "3순위",
                    "참가여부", "참가구분"
            };
            Collections.addAll(headerList, fixedHeaders1);

            String[] appHeaders = {
                    "수출상담회 참가희망여부", "참가비", "독립부스", "조립부스", "온라인부스", "총 부스 수", "부스가격",
                    "참가비 합계\n(등록비+부스가격)",
                    "1차 조기신청", "2차 조기신청", "첫 참가(10부스 미만)", "첫 참가(10부스 이상)", "재참가",
                    "규모(10+)", "규모(20+)", "규모(30+)", "규모(40+)", "규모(50+)", "규모(100+)",
                    "협회할인",
                    "특할1(제품상)", "특할1 비고", "특할2 금액", "특할2 사유", "특할2 비고", "특할3 금액", "특할3 사유", "특할3 비고",
                    "할인가격", "협회 발전기금",
                    "유틸리티 신청내역", "유틸리티 총액",
                    "소계(공급가액)", "부가세", "총계"
            };
            Collections.addAll(headerList, appHeaders);

            final String[] colNames_ex = headerList.toArray(new String[0]);

            Font fontHeader = workbook.createFont(); fontHeader.setFontName("맑은 고딕"); fontHeader.setFontHeightInPoints((short) 9); fontHeader.setBold(true);
            Font font9 = workbook.createFont(); font9.setFontName("맑은 고딕"); font9.setFontHeightInPoints((short) 9);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER); headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderRight(BorderStyle.THIN); headerStyle.setBorderLeft(BorderStyle.THIN); headerStyle.setBorderTop(BorderStyle.THIN); headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader); headerStyle.setWrapText(true);

            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER); bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderRight(BorderStyle.THIN); bodyStyle.setBorderLeft(BorderStyle.THIN); bodyStyle.setBorderTop(BorderStyle.THIN); bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setFont(font9); bodyStyle.setWrapText(true);

            CellStyle bodyLeftStyle = workbook.createCellStyle();
            bodyLeftStyle.cloneStyleFrom(bodyStyle); bodyLeftStyle.setAlignment(HorizontalAlignment.LEFT);

            CellStyle bodyRightStyle = workbook.createCellStyle();
            bodyRightStyle.cloneStyleFrom(bodyStyle); bodyRightStyle.setAlignment(HorizontalAlignment.RIGHT);

            SXSSFSheet sheet = workbook.createSheet("참가업체");
            sheet.trackAllColumnsForAutoSizing();
            int rowCnt = 0;

            Row headerRow = sheet.createRow(rowCnt++);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 21));   headerRow.createCell(0).setCellValue("참가업체정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 22, 32));  headerRow.createCell(22).setCellValue("입금 현황");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 33, 38));  headerRow.createCell(33).setCellValue("대표 담당자");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 39, 56));  headerRow.createCell(39).setCellValue("기타 담당자 정보");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 57, 64));  headerRow.createCell(57).setCellValue("업체정보(소개)");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 65, 68));  headerRow.createCell(65).setCellValue("참가분야");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 69, 70));  headerRow.createCell(69).setCellValue("해상전시회");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 71, 105)); headerRow.createCell(71).setCellValue("신청내역");

            for(int i=0; i < colNames_ex.length; i++){
                if(headerRow.getCell(i) == null) headerRow.createCell(i);
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            Row subHeaderRow = sheet.createRow(rowCnt++);
            subHeaderRow.setHeightInPoints(35);
            for (int i = 0; i < colNames_ex.length; i++) {
                Cell cell = subHeaderRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(colNames_ex[i]);
                sheet.setColumnWidth(i, 5000);
            }
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(105, 8000);

            ExhibitorNewDetailDTO exhibitorDetailDTO = new ExhibitorNewDetailDTO();
            exhibitorDetailDTO.setTransferYear(transferYear);
            List<ExhibitorNewDetailDTO> exhibitorDetailList = kibsMngService.processSelectExhibitorNewDetailList(exhibitorDetailDTO);
            int listNum = exhibitorDetailList.size();
            DecimalFormat df = new DecimalFormat("#,##0");

            for (ExhibitorNewDetailDTO info : exhibitorDetailList) {
                // ==========================================
                // ★ 엑셀 쓰기 전 복호화 전처리 시작 ★
                // ==========================================
                info.setCompanyAddress(aes256Util.safeDecrypt(info.getCompanyAddress()));
                info.setCompanyAddressDetail(aes256Util.safeDecrypt(info.getCompanyAddressDetail()));
                info.setFactoryAddress(aes256Util.safeDecrypt(info.getFactoryAddress()));
                info.setFactoryAddressDetail(aes256Util.safeDecrypt(info.getFactoryAddressDetail()));
                info.setCompanyTel(aes256Util.safeDecrypt(info.getCompanyTel()));
                info.setCompanyFax(aes256Util.safeDecrypt(info.getCompanyFax()));
                info.setCompanyCeo(aes256Util.safeDecrypt(info.getCompanyCeo()));

                info.setName(aes256Util.safeDecrypt(info.getName()));
                info.setTel(aes256Util.safeDecrypt(info.getTel()));
                info.setPhone(aes256Util.safeDecrypt(info.getPhone()));
                info.setEmail(aes256Util.safeDecrypt(info.getEmail()));

                info.setChargePersonName(aes256Util.decryptMulti(info.getChargePersonName()));
                info.setChargePersonPosition(aes256Util.decryptMulti(info.getChargePersonPosition()));
                info.setChargePersonDepart(aes256Util.decryptMulti(info.getChargePersonDepart()));
                info.setChargePersonTel(aes256Util.decryptMulti(info.getChargePersonTel()));
                info.setChargePersonPhone(aes256Util.decryptMulti(info.getChargePersonPhone()));
                info.setChargePersonEmail(aes256Util.decryptMulti(info.getChargePersonEmail()));
                // ==========================================

                Row dataRow = sheet.createRow(rowCnt++);
                int cellCnt = 0;

                cellCnt = writeCompanyInfo(dataRow, cellCnt, info, listNum--);
                cellCnt = writeDepositHistory(dataRow, cellCnt, info, df);
                cellCnt = writeRepresentativeInfo(dataRow, cellCnt, info);
                cellCnt = writeOtherContactsInfo(dataRow, cellCnt, info);
                cellCnt = writeCompanyIntroInfo(dataRow, cellCnt, info);
                cellCnt = writeParticipationFields(dataRow, cellCnt, info);
                cellCnt = writeMaritimeExhibitionInfo(dataRow, cellCnt, info);

                cellCnt = writeApplicationDetails(dataRow, cellCnt, info, df);

                for(int i=0; i < colNames_ex.length; i++){
                    Cell cell = dataRow.getCell(i);
                    if (cell == null) {
                        cell = dataRow.createCell(i);
                    }

                    if (i >= 22 && i <= 32) {
                        cell.setCellStyle(bodyLeftStyle);
                    } else {
                        cell.setCellStyle(bodyStyle);
                    }
                }
            }

            // =============================================================
            // Pricing 시트 생성 로직 ▼▼▼
            // =============================================================
            SXSSFSheet sheet2 = workbook.createSheet("Pricing");
            sheet2.trackAllColumnsForAutoSizing();

            int rowCnt2 = 0;

            Font titleFont = workbook.createFont(); titleFont.setFontName("맑은 고딕"); titleFont.setBold(true); titleFont.setFontHeightInPoints((short) 12);
            Font headerFont = workbook.createFont(); headerFont.setFontName("맑은 고딕"); headerFont.setBold(true); headerFont.setFontHeightInPoints((short) 10);
            Font bodyFont = workbook.createFont(); bodyFont.setFontName("맑은 고딕"); bodyFont.setFontHeightInPoints((short) 10);
            Font noteFont = workbook.createFont(); noteFont.setFontName("맑은 고딕"); noteFont.setFontHeightInPoints((short) 10); noteFont.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
            CellStyle titleStyle = workbook.createCellStyle(); titleStyle.setFont(titleFont);
            CellStyle mainHeaderStyle = workbook.createCellStyle(); mainHeaderStyle.setAlignment(HorizontalAlignment.CENTER); mainHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER); mainHeaderStyle.setBorderTop(BorderStyle.THIN); mainHeaderStyle.setBorderBottom(BorderStyle.THIN); mainHeaderStyle.setBorderLeft(BorderStyle.THIN); mainHeaderStyle.setBorderRight(BorderStyle.THIN); mainHeaderStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index); mainHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); mainHeaderStyle.setFont(headerFont);
            CellStyle subHeaderStyle = workbook.createCellStyle(); subHeaderStyle.setAlignment(HorizontalAlignment.CENTER); subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER); subHeaderStyle.setBorderTop(BorderStyle.THIN); subHeaderStyle.setBorderBottom(BorderStyle.THIN); subHeaderStyle.setBorderLeft(BorderStyle.THIN); subHeaderStyle.setBorderRight(BorderStyle.THIN); subHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); subHeaderStyle.setFont(headerFont);
            CellStyle bodyCenterStyle = workbook.createCellStyle(); bodyCenterStyle.cloneStyleFrom(bodyLeftStyle); bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyRightStyle = workbook.createCellStyle(); bodyRightStyle.cloneStyleFrom(bodyLeftStyle); bodyRightStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle noteStyle = workbook.createCellStyle(); noteStyle.cloneStyleFrom(bodyLeftStyle); noteStyle.setFont(noteFont);

            SXSSFRow row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("■ 부스(vat 별도)"); row.getCell(0).setCellStyle(titleStyle);
            rowCnt2++;
            String[][] boothData = { {"부스구분", "가격"}, {"독립부스 1차", "1,500,000"}, {"조립부스 1차", "1,800,000"}, {"독립부스 2차", "1,600,000"}, {"조립부스 2차", "1,900,000"}, {"독립부스", "2,100,000"}, {"조립부스", "2,400,000"} };
            for (int i=0; i<boothData.length; i++) {
                row = sheet2.createRow(rowCnt2++);
                row.createCell(0).setCellValue(boothData[i][0]); row.createCell(1).setCellValue(boothData[i][1]);
                if (i==0) { row.getCell(0).setCellStyle(mainHeaderStyle); row.getCell(1).setCellStyle(mainHeaderStyle); }
                else { row.getCell(0).setCellStyle(bodyCenterStyle); row.getCell(1).setCellStyle(bodyRightStyle); }
            }
            rowCnt2+=2;

            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("■ 할인내역(vat 별도)"); row.getCell(0).setCellStyle(titleStyle);
            rowCnt2++;
            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("구분"); row.createCell(1).setCellValue("할인항목"); row.createCell(2).setCellValue("가격"); row.createCell(3).setCellValue("비고");
            for(int i=0; i<4; i++) row.getCell(i).setCellStyle(mainHeaderStyle);
            String[][] discountData = { {"기본할인", "1차 조기신청 할인", "300,000", "부스당"}, {"기본할인", "2차 조기신청 할인", "200,000", "부스당"}, {"규모할인", "10부스 이상", "500,000", "첫참가 업체 30만원/부스 추가 할인"}, {"규모할인", "20부스 이상", "820,000", ""}, {"규모할인", "30부스 이상", "950,000", ""}, {"규모할인", "40부스 이상", "1,030,000", ""}, {"규모할인", "50부스 이상", "1,080,000", ""}, {"규모할인", "100부스 이상", "1,150,000", ""}, {"기본할인", "재참가 기업 할인", "200,000", ""}, {"기본할인", "첫참가 기업 할인", "500,000", "10부스 미만 신청시에만 적용"}, {"기본할인", "협회 회원사(10부스 미만)", "500,000", "업체당"}, {"기본할인", "협회 회원사(10부스 이상)", "200,000", "부스당"}, {"특별할인(비공개)", "올해의 제품상", "최종 참가비용의 50% 할인", "=할인 적용 된 금액의 50%"}, {"특별할인(비공개)", "특별관", "기본할인 적용가격의 최대 80% 할인", ""}, {"기타", "해양레저산업협회 발전기금 납부", "참가비의 10% 납부", ""} };
            int discountStartRow = rowCnt2;
            for(String[] d : discountData){
                row = sheet2.createRow(rowCnt2++);
                row.createCell(0).setCellValue(d[0]); row.createCell(1).setCellValue(d[1]); row.createCell(2).setCellValue(d[2]); row.createCell(3).setCellValue(d[3]);
                row.getCell(0).setCellStyle(subHeaderStyle); row.getCell(1).setCellStyle(bodyLeftStyle); row.getCell(2).setCellStyle(bodyRightStyle); row.getCell(3).setCellStyle(noteStyle);
            }
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow, discountStartRow + 1, 0, 0));
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow + 2, discountStartRow + 7, 0, 0));
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow + 8, discountStartRow + 11, 0, 0));
            sheet2.addMergedRegion(new CellRangeAddress(discountStartRow + 12, discountStartRow + 13, 0, 0));
            rowCnt2+=2;

            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("■ 유틸리티(부대시설)"); row.getCell(0).setCellStyle(titleStyle);
            rowCnt2++;
            row = sheet2.createRow(rowCnt2++);
            row.createCell(0).setCellValue("구분"); row.createCell(1).setCellValue("기술지원 항목"); row.createCell(2).setCellValue("가격(원)"); row.createCell(3).setCellValue("비고");
            for(int i=0; i<4; i++) row.getCell(i).setCellStyle(mainHeaderStyle);
            String[][] utilityData = {
                    {"파이텍스", "신품", "100,000", ""},
                    {"파이텍스", "재사용품", "50,000", ""},
                    {"전기", "주간 단상 220v", "100,000", "원/kw"},
                    {"전기", "24시간용 220v", "150,000", "원/kw"},
                    {"전기", "작업전기", "100,000", "원/kw"},
                    {"인터넷", "인터넷", "250,000", "원/1회선"},
                    {"급배수 및 압축공기", "급배수", "250,000", "원/1구"},
                    {"급배수 및 압축공기", "압축공기", "250,000", "원/1구"},
                    {"바코드 리더기", "바코드 리더기", "250,000", "원/1개"}
            };
            int utilityStartRow = rowCnt2;
            for(String[] u : utilityData){
                row = sheet2.createRow(rowCnt2++);
                row.createCell(0).setCellValue(u[0]); row.createCell(1).setCellValue(u[1]); row.createCell(2).setCellValue(u[2]); row.createCell(3).setCellValue(u[3]);
            }

            sheet2.addMergedRegion(new CellRangeAddress(utilityStartRow, utilityStartRow + 1, 0, 0));
            sheet2.addMergedRegion(new CellRangeAddress(utilityStartRow + 2, utilityStartRow + 4, 0, 0));
            sheet2.addMergedRegion(new CellRangeAddress(utilityStartRow + 6, utilityStartRow + 7, 0, 0));

            for(int i = utilityStartRow; i < rowCnt2; i++){
                row = sheet2.getRow(i);
                row.getCell(0).setCellStyle(subHeaderStyle);
                row.getCell(1).setCellStyle(bodyLeftStyle);
                row.getCell(2).setCellStyle(bodyRightStyle);
                row.getCell(3).setCellStyle(bodyLeftStyle);
            }

            for (int i=0; i<4; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) + 1024));
            }

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

    private int writeDepositHistory(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info, DecimalFormat df) {
        String[] amounts = info.getDepositAmounts() != null ? info.getDepositAmounts().split("\n") : new String[0];
        StringBuilder formattedAmounts = new StringBuilder();
        for (String amount : amounts) {
            try {
                formattedAmounts.append(df.format(Long.parseLong(amount))).append(" 원\n");
            } catch (Exception e) {
                formattedAmounts.append(amount).append("\n");
            }
        }
        dataRow.createCell(cellCnt++).setCellValue(formattedAmounts.toString().trim());

        dataRow.createCell(cellCnt++).setCellValue(info.getDepositContents());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositStatuses());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositDates());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositScheduledDates());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositTaxStatuses());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositDepositorNames());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositConfirmerNames());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositComments());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositInitRegiDttms());
        dataRow.createCell(cellCnt++).setCellValue(info.getDepositFinalRegiDttms());

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

    private String checkField(String fieldName, String f1, String f2, String f3) {
        if (fieldName == null) return "";
        if (fieldName.equals(f1) || fieldName.equals(f2) || fieldName.equals(f3)) {
            return "O";
        }
        return "";
    }

    private int writeParticipationFields(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        String eventType = info.getFieldParticipatory();
        String eventName = "";
        if ("boatShow".equals(eventType)) eventName = "경기국제보트쇼";
        else if ("surfShow".equals(eventType)) eventName = "코리아서프쇼";
        else if ("travelShow".equals(eventType)) eventName = "해양관광전";
        else if (eventType != null) eventName = eventType;

        dataRow.createCell(cellCnt++).setCellValue(eventName);
        dataRow.createCell(cellCnt++).setCellValue(nvl(info.getFieldParticipatory1()));
        dataRow.createCell(cellCnt++).setCellValue(nvl(info.getFieldParticipatory2()));
        dataRow.createCell(cellCnt++).setCellValue(nvl(info.getFieldParticipatory3()));

        return cellCnt;
    }

    private int writeMaritimeExhibitionInfo(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        dataRow.createCell(cellCnt++).setCellValue("Y".equals(info.getMaritimeExhibitionYn()) ? "참가" : "미참가");

        boolean sea = Boolean.TRUE.equals(info.getMaritimeExhibitionSea());
        boolean land = Boolean.TRUE.equals(info.getMaritimeExhibitionLand());
        String participationType = "";
        if (sea) {
            participationType += "해상 전시 (시승체험)";
        }
        if (land) {
            if (sea) participationType += "\n";
            participationType += "육상 전시";
        }
        dataRow.createCell(cellCnt++).setCellValue(participationType);

        return cellCnt;
    }

    private int writeApplicationDetails(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info, DecimalFormat df) {
        long registrationFee = info.getRegistrationFee() != null ? info.getRegistrationFee() : 0;
        long boothPrcSum = (info.getBoothPrcSum() != null) ? info.getBoothPrcSum() : 0;
        long basicDiscount = (info.getDiscountPrcSum() != null) ? info.getDiscountPrcSum() : 0;
        long utilityPrcSum = (info.getUtilityPrcSum() != null) ? info.getUtilityPrcSum() : 0;

        long boothPriceOnly = boothPrcSum - registrationFee;

        long baseAmountForSpecial = (boothPrcSum + utilityPrcSum) - basicDiscount;

        long specialDiscount1Amount = 0;
        if (Boolean.TRUE.equals(info.getDiscountSpecial1Yn())) {
            specialDiscount1Amount = (long)Math.floor(baseAmountForSpecial * 0.5);
        }
        long specialDiscount2Amount = (Boolean.TRUE.equals(info.getDiscountSpecial2Yn()) && info.getDiscountSpecial2Amount() != null) ? info.getDiscountSpecial2Amount() : 0;
        long specialDiscount3Amount = (Boolean.TRUE.equals(info.getDiscountSpecial3Yn()) && info.getDiscountSpecial3Amount() != null) ? info.getDiscountSpecial3Amount() : 0;

        long totalSpecialDiscount = specialDiscount1Amount + specialDiscount2Amount + specialDiscount3Amount;
        long totalDiscount = basicDiscount + totalSpecialDiscount;

        long developmentFund = 0;
        if (("Y".equals(info.getMemberCompanyYn()) || Boolean.TRUE.equals(info.getDiscountLeisure()))) {
            long developmentBase = boothPrcSum - totalDiscount;
            developmentFund = (long)(developmentBase * 0.1);
        }

        long participationFeeTotal = registrationFee + boothPriceOnly + developmentFund;

        long recalculatedPrcSum = participationFeeTotal - totalDiscount;

        long recalculatedPrcVat = (long) Math.floor(recalculatedPrcSum * 0.1);
        long recalculatedPrcTotal = recalculatedPrcSum + recalculatedPrcVat;

        dataRow.createCell(cellCnt++).setCellValue("Y".equals(info.getExportMeetingYn()) ? "참가" : "참가 안 함");
        dataRow.createCell(cellCnt++).setCellValue(df.format(registrationFee));
        int standAlone = info.getStandAloneBoothCnt() != null ? info.getStandAloneBoothCnt() : 0;
        int assembly = info.getAssemblyBoothCnt() != null ? info.getAssemblyBoothCnt() : 0;
        int online = info.getOnlineBoothCnt() != null ? info.getOnlineBoothCnt() : 0;
        dataRow.createCell(cellCnt++).setCellValue(standAlone);
        dataRow.createCell(cellCnt++).setCellValue(assembly);
        dataRow.createCell(cellCnt++).setCellValue(online);
        dataRow.createCell(cellCnt++).setCellValue(standAlone + assembly + online);
        dataRow.createCell(cellCnt++).setCellValue(df.format(boothPriceOnly));

        dataRow.createCell(cellCnt++).setCellValue(df.format(boothPrcSum));

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

        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountSpecial1Yn()) ? df.format(specialDiscount1Amount) : "");
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial1Note());

        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountSpecial2Yn()) ? df.format(specialDiscount2Amount) : "");
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial2Reason());
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial2Note());

        dataRow.createCell(cellCnt++).setCellValue(Boolean.TRUE.equals(info.getDiscountSpecial3Yn()) ? df.format(specialDiscount3Amount) : "");
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial3Reason());
        dataRow.createCell(cellCnt++).setCellValue(info.getDiscountSpecial3Note());

        dataRow.createCell(cellCnt++).setCellValue(df.format(totalDiscount));
        dataRow.createCell(cellCnt++).setCellValue(developmentFund > 0 ? df.format(developmentFund) : "");

        StringBuilder utilityDetails = new StringBuilder();
        if (info.getUtilityJuganCnt() != null && info.getUtilityJuganCnt() > 0) utilityDetails.append("주간 단상 220v (").append(info.getUtilityJuganCnt()).append(")\n");
        if (info.getUtilityDayCnt() != null && info.getUtilityDayCnt() > 0) utilityDetails.append("24시간용 220v (").append(info.getUtilityDayCnt()).append(")\n");
        if (info.getUtilityWorkCnt() != null && info.getUtilityWorkCnt() > 0) utilityDetails.append("작업전기 (").append(info.getUtilityWorkCnt()).append(")\n");
        if (info.getUtilityCompressedAirCnt() != null && info.getUtilityCompressedAirCnt() > 0) utilityDetails.append("압축공기 기본형 (").append(info.getUtilityCompressedAirCnt()).append(")\n");
        if (info.getUtilityWaterBasicCnt() != null && info.getUtilityWaterBasicCnt() > 0) utilityDetails.append("급배수 기본형 (").append(info.getUtilityWaterBasicCnt()).append(")\n");
        if (info.getUtilityInternetCnt() != null && info.getUtilityInternetCnt() > 0) utilityDetails.append("인터넷 (").append(info.getUtilityInternetCnt()).append(")\n");
        if (info.getUtilityPytexNewCnt() != null && info.getUtilityPytexNewCnt() > 0) utilityDetails.append("파이텍스-신품 (").append(info.getUtilityPytexNewCnt()).append(")\n");
        if (info.getUtilityPytexReCnt() != null && info.getUtilityPytexReCnt() > 0) utilityDetails.append("파이텍스-재사용품 (").append(info.getUtilityPytexReCnt()).append(")\n");
        if (info.getUtilityBarcodeCnt() != null && info.getUtilityBarcodeCnt() > 0) utilityDetails.append("바코드 리더기 (").append(info.getUtilityBarcodeCnt()).append(")\n");

        String utilityDetailsStr = utilityDetails.toString();
        if (utilityDetailsStr.endsWith("\n")) {
            utilityDetailsStr = utilityDetailsStr.substring(0, utilityDetailsStr.length() - 1);
        }

        dataRow.createCell(cellCnt++).setCellValue(utilityDetailsStr);
        dataRow.createCell(cellCnt++).setCellValue(utilityPrcSum > 0 ? df.format(utilityPrcSum) : "");

        dataRow.createCell(cellCnt++).setCellValue(df.format(recalculatedPrcSum));
        dataRow.createCell(cellCnt++).setCellValue(df.format(recalculatedPrcVat));
        dataRow.createCell(cellCnt++).setCellValue(df.format(recalculatedPrcTotal));
        return cellCnt;
    }

    private int writeExhibitionItems(Row dataRow, int cellCnt, ExhibitorNewDetailDTO info) {
        for(int i=0; i<20; i++){
            String[] productOptionBigSplit = info.getProductOptionBig() != null ? info.getProductOptionBig().split("\\^", -1) : new String[0];
            String productOptionBig = convertValue(productOptionBigSplit, i);
            if (productOptionBig != null && !productOptionBig.trim().isEmpty()) {
                String[] productOptionSmallSplit = info.getProductOptionSmall() != null ? info.getProductOptionSmall().split("\\^", -1) : new String[0];
                dataRow.createCell(cellCnt++).setCellValue(!productOptionBig.isEmpty() ? productOptionBig + " / " + convertValue(productOptionSmallSplit, i) : "");

                String[] productIsNewSplit = info.getProductIsNew() != null ? info.getProductIsNew().split("\\^", -1) : new String[0];
                String isNew = convertValue(productIsNewSplit, i);
                dataRow.createCell(cellCnt++).setCellValue("Y".equals(isNew) ? "O" : "X");

                String[] productNameKoSplit = info.getProductNameKo() != null ? info.getProductNameKo().split("\\^", -1) : new String[0];
                dataRow.createCell(cellCnt++).setCellValue(convertValue(productNameKoSplit, i));

                String[] productQtySplit = info.getProductQty() != null ? info.getProductQty().split("\\^", -1) : new String[0];
                dataRow.createCell(cellCnt++).setCellValue(convertValue(productQtySplit, i));

                String[] productBrandSplit = info.getProductBrand() != null ? info.getProductBrand().split("\\^", -1) : new String[0];
                dataRow.createCell(cellCnt++).setCellValue(convertValue(productBrandSplit, i));

                String[] productFeatureSplit = info.getProductFeature() != null ? info.getProductFeature().split("\\^", -1) : new String[0];
                dataRow.createCell(cellCnt++).setCellValue(convertValue(productFeatureSplit, i));

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

            }else{
                for (int j = 0; j < 12; j++) {
                    dataRow.createCell(cellCnt++).setCellValue("");
                }
            }
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
            String onlineOptionBig = convertValue(onlineOptionBigSplit, i);
            if (onlineOptionBig != null && !onlineOptionBig.trim().isEmpty()) {
                String[] onlineOptionSmallSplit = info.getOnlineOptionSmall() != null ? info.getOnlineOptionSmall().split("\\^", -1) : new String[0];
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

            }else{
                for (int j = 0; j < 11; j++) {
                    dataRow.createCell(cellCnt++).setCellValue("");
                }
            }
        }
        return cellCnt;
    }

    private String convertValue(String[] split, int index) {
        if (split != null && index < split.length && split[index] != null) {
            return split[index];
        }
        return "";
    }

    // nvl 헬퍼
    private String nvl(String str) {
        return str == null ? "" : str;
    }

}