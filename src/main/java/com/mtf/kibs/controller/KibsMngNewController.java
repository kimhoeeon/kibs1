package com.mtf.kibs.controller;

import com.mtf.kibs.dto.*;
import com.mtf.kibs.service.CalculationService;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.service.KibsMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Kibs page controller.
 */
@Controller
public class KibsMngNewController {

    // 필드 주입이 아닌 생성자 주입형태로 사용합니다. '생성자 주입 형태'로 사용합니다.
    private final KibsMngService kibsMngService;

    private final CommService commService;

    /**
     * Instantiates a new Kibs controller.
     *
     * @param kms         the kms
     * @param cs
     */
    public KibsMngNewController(KibsMngService kms, CommService cs){
        this.kibsMngService = kms;
        this.commService = cs;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitor_new_new_participant_company(String nameKo) {
        //System.out.println("KibsMngController > mng_exhibitor_company");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_participant_company_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectListExhibitorNew(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNewNew_company_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_company_detail");
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
        mv.setViewName("/mng/exhibitorNewNew/participant/company/detail");
        return mv;
    }

    //***************************************************************************
    // exhibitor/application Folder
    //***************************************************************************

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/booth");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_booth_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewBoothList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_detail");
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
        mv.setViewName("/mng/exhibitorNewNew/application/booth/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility_invoice_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBooth(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_booth_invoice_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewBoothInvoice(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_delete(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewUtilityInvoice(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/filePath/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_filePath_update(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_filePath_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBoothInvoiceFilePath(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/filePath/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_filePath_update(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_filePath_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtilityInvoiceFilePath(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/booth/invoice/mail/result/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_booth_invoice_mail_result_update(@RequestBody InvoiceBoothDTO invoiceBoothDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_booth_invoice_mail_result_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBoothInvoiceSendResult(invoiceBoothDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/invoice/mail/result/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_invoice_mail_result_update(@RequestBody InvoiceUtilityDTO invoiceUtilityDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_invoice_mail_result_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtilityInvoiceSendResult(invoiceUtilityDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_sign() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_sign");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/sign");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_sign_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_sign_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewSignList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/sign/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_sign_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_sign_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_sign_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewSign(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/utility");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_utility_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectExhibitorNewUtilityList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_utility_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_detail");
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
        mv.setViewName("/mng/exhibitorNewNew/application/utility/detail");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/utility/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_utility_update(@RequestBody ExhibitorNewDTO exhibitorNewDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_utility_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewUtility(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/invoice/mail/open/update.do", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void mng_exhibitorNew_application_invoice_mail_open_update(@RequestParam("hseq") int historySeq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_invoice_mail_open_update");
        kibsMngService.updateInvoiceHistoryStatusToOpen(historySeq);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_pass() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_pass");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/pass");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngPassDTO>> mng_exhibitorNew_application_pass_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_pass_selectList");
        //System.out.println(searchDTO.toString());

        List<MngPassDTO> responseList = kibsMngService.processSelectExhibitorNewPassList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_pass_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_pass_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_pass_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewPass(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/pass/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_pass_delete(@RequestBody PassNewDTO passNewDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_pass_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewPass(passNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_buyer() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_buyer");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/buyer");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngBuyerDTO>> mng_exhibitorNew_application_buyer_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_selectList");
        //System.out.println(searchDTO.toString());

        List<MngBuyerDTO> responseList = kibsMngService.processSelectExhibitorNewBuyerList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_buyer_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewBuyer(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/buyer/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_buyer_delete(@RequestBody BuyerNewDTO buyerNewDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_buyer_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewBuyer(buyerNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_gift() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_gift");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/gift");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngGiftDTO>> mng_exhibitorNew_application_gift_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_gift_selectList");
        //System.out.println(searchDTO.toString());

        List<MngGiftDTO> responseList = kibsMngService.processSelectExhibitorNewGiftList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_gift_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_gift_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_gift_update");
        ResponseDTO response = kibsMngService.processUpdateExhibitorNewGift(exhibitorNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/gift/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> mng_exhibitorNew_application_gift_delete(@RequestBody GiftNewDTO giftNewDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_gift_delete");
        ResponseDTO response = kibsMngService.processDeleteExhibitorNewGift(giftNewDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_online() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_online");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/online");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ExhibitorNewDTO>> mng_exhibitorNew_application_online_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_online_selectList");
        //System.out.println(searchDTO.toString());

        List<ExhibitorNewDTO> responseList = kibsMngService.processSelectMngOnlineNewList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/online/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_online_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitor_application_online_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_application_online_updateViewYn");
        //System.out.println(exhibitorNewDTO.getSeq());

        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewOnlineViewYn(exhibitorNewDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/product.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_product() {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_product");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mng/exhibitorNewNew/application/product");
        return mv;
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/product/selectList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<MngProductDTO>> mng_exhibitorNew_application_product_selectList(@RequestBody SearchDTO searchDTO) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_product_selectList");
        //System.out.println(searchDTO.toString());

        List<MngProductDTO> responseList = kibsMngService.processSelectMngProductNewList(searchDTO);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/application/product/detail.do", method = RequestMethod.GET)
    public ModelAndView mng_exhibitorNew_application_product_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_application_product_detail");
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
        //System.out.println("KibsMngController > mng_exhibitorNew_participant_company_updateExhibitorNewPrcYn");

        ResponseDTO responseDTO = kibsMngService.processUpdateExhibitorNewPrcYn(exhibitorList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/mng/exhibitorNewNew/participant/company/invoice/detail.do", method = RequestMethod.POST)
    public ModelAndView mng_exhibitorNewNew_company_invoice_detail(String seq) {
        //System.out.println("KibsMngController > mng_exhibitorNew_company_invoice_detail");
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

        mv.setViewName("/mng/exhibitorNewNew/participant/company/invoice/detail");
        return mv;
    }

    /**
     * 생성된 PDF 파일 경로를 DB에 업데이트하는 API
     * - JavaScript의 createAndUploadPdfFromIframe 함수 성공 후 호출될 것으로 예상
     */
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

    /**
     * 선택된 인보이스들을 삭제하는 API
     */
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
    @PostMapping("/mng/exhibitorNewNew/application/booth/invoice/insert.do")
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

}