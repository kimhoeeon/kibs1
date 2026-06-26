package com.mtf.kibs.service;

import com.mtf.kibs.dto.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface KibsMngService {

    AdminDTO login(AdminDTO adminDTO, HttpSession session);

    void logoutCheck(HttpSession session);

    List<?> uploadExcelFile(MultipartFile file);

    ResponseDTO memberExcelUp(MultipartFile file);

    ResponseDTO processInsertExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    List<ParticipantCompanyDTO> processSelectListExhibitorComp(SearchDTO searchDTO);

    ResponseDTO processDeleteExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    ParticipantCompanyDTO processSelectExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    ResponseDTO processUpdateExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    List<NoticeDTO> processSelectNoticeList(SearchDTO searchDTO);

    NoticeDTO processSelectNoticeSingle(NoticeDTO noticeDTO);

    ResponseDTO processDeleteBoardNotice(NoticeDTO noticeDTO);

    ResponseDTO processUpdateBoardNotice(NoticeDTO noticeDTO);

    ResponseDTO processInsertBoardNotice(NoticeDTO noticeDTO);

    List<NewsletterDTO> processSelectNewsletterList(SearchDTO searchDTO);

    NewsletterDTO processSelectNewsletterSingle(NewsletterDTO newsletterDTO);

    ResponseDTO processDeleteNewsletter(NewsletterDTO newsletterDTO);

    ResponseDTO processUpdateNewsletter(NewsletterDTO newsletterDTO);

    ResponseDTO processInsertNewsletter(NewsletterDTO newsletterDTO);

    List<PressDTO> processSelectPressList(SearchDTO searchDTO);

    PressDTO processSelectPressSingle(PressDTO pressDTO);

    ResponseDTO processDeletePress(PressDTO pressDTO);

    ResponseDTO processUpdatePress(PressDTO pressDTO);

    ResponseDTO processInsertPress(PressDTO pressDTO);

    List<FaqDTO> processSelectFaqList(SearchDTO searchDTO);

    FaqDTO processSelectFaqSingle(FaqDTO faqDTO);

    ResponseDTO processDeleteFaq(FaqDTO faqDTO);

    ResponseDTO processUpdateFaq(FaqDTO faqDTO);

    ResponseDTO processInsertFaq(FaqDTO faqDTO);

    List<ColumnDTO> processSelectColumnList(SearchDTO searchDTO);

    ColumnDTO processSelectColumnSingle(ColumnDTO columnDTO);

    ResponseDTO processDeleteColumn(ColumnDTO columnDTO);

    ResponseDTO processUpdateColumn(ColumnDTO columnDTO);

    ResponseDTO processInsertColumn(ColumnDTO columnDTO);

    List<DataroomDTO> processSelectDataroomList(SearchDTO searchDTO);

    DataroomDTO processSelectDataroomSingle(DataroomDTO dataroomDTO);

    ResponseDTO processDeleteDataroom(DataroomDTO dataroomDTO);

    ResponseDTO processUpdateDataroom(DataroomDTO dataroomDTO);

    ResponseDTO processInsertDataroom(DataroomDTO dataroomDTO);

    List<GalleryDTO> processSelectGalleryList(SearchDTO searchDTO);

    GalleryDTO processSelectGallerySingle(GalleryDTO galleryDTO);

    ResponseDTO processDeleteGallery(GalleryDTO galleryDTO);

    ResponseDTO processUpdateGallery(GalleryDTO galleryDTO);

    ResponseDTO processInsertGallery(GalleryDTO galleryDTO);

    List<KibstvDTO> processSelectKibstvList(SearchDTO searchDTO);

    KibstvDTO processSelectKibstvSingle(KibstvDTO kibstvDTO);

    ResponseDTO processDeleteKibstv(KibstvDTO kibstvDTO);

    ResponseDTO processUpdateKibstv(KibstvDTO kibstvDTO);

    ResponseDTO processInsertKibstv(KibstvDTO kibstvDTO);

    FileResponseDTO processInsertFileInfo(FileDTO fileDTO);

    List<FileDTO> processSelectFileList(FileDTO fileDTO);

    FileDTO processSelectFileInfo(FileDTO fileDTO);

    FileResponseDTO processUpdateFileInfo(FileDTO fileDTO);

    List<ExhibitorDTO> processSelectListExhibitor(SearchDTO searchDTO);

    List<ExhibitorNewDTO> processSelectListExhibitorNew(SearchDTO searchDTO);

    ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO);

    ExhibitorNewDTO processSelectExhibitorNewSingle(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processUpdateFileUserId(FileDTO fileDTO);

    ResponseDTO processInsertParticipantCompany(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateParticipantCompany(ExhibitorDTO exhibitorDTO);

    ResponseDTO processDeleteExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processDeleteParticipantCompany(ExhibitorDTO exhibitorDTO);

    List<DisplayDTO> processSelectDisplayList(DisplayDTO displayDTO);

    List<ProductNewDTO> processSelectProductNewList(ProductNewDTO productNewDTO);

    List<OnlineDTO> processSelectOnlineList(OnlineDTO onlineDTO);

    List<OnlineNewDTO> processSelectOnlineNewList(OnlineNewDTO onlineNewDTO);

    List<BuyerDTO> processSelectBuyerList(BuyerDTO buyerDTO);

    List<BuyerNewDTO> processSelectBuyerNewList(BuyerNewDTO buyerNewDTO);

    BuyerDTO processSelectBuyerSingle(BuyerDTO buyerDTO);

    ResponseDTO processDeleteBuyer(BuyerDTO buyerDTO);

    List<EdmHstDTO> processSelectEdmHstList(SearchDTO searchDTO);

    ResponseDTO processMailSend(MailRequestDTO mailRequestDTO);

    ResponseDTO processInsertEdmHst(EdmHstDTO edmHstDTO);

    ResponseDTO processDeleteEdmHst(EdmHstDTO edmHstDTO);

    ResponseDTO processDeleteOnline(OnlineDTO onlineDTO);

    ResponseDTO processDeleteDisplay(DisplayDTO displayDTO);

    ResponseDTO processDeleteFile(FileDTO fileDTO);

    List<ExhibitorDTO> processSelectListTransferCompany(SearchDTO searchDTO);

    List<VisitorDTO> processSelectVisitorList(SearchDTO searchDTO);

    List<ChargePersonDTO> processSelectChargeList(ChargePersonDTO chargePersonDTO);

    List<ChargeNewDTO> processSelectChargeNewList(ChargeNewDTO chargeNewDTO);

    ResponseDTO processDeleteCharge(ChargePersonDTO chargePersonDTO);

    List<ExhibitorDTO> processSelectBoothList(SearchDTO searchDTO);

    List<ExhibitorNewDTO> processSelectExhibitorNewBoothList(SearchDTO searchDTO);

    ExhibitorDTO processSelectBoothSingle(String seq);

    ExhibitorNewDTO processSelectExhibitorNewBoothSingle(String seq);

    List<InvoiceBoothDTO> processSelectInvoiceBoothExSeqList(InvoiceBoothDTO invoiceBoothDTO);

    ResponseDTO processUpdateBooth(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO);

    InvoiceBoothDTO processSelectInvoiceBoothSingle(String seq);

    InvoiceUtilityDTO processSelectInvoiceUtilitySingle(String seq);

    ResponseDTO processDeleteExhibitorNewBoothInvoice(InvoiceBoothDTO invoiceBoothDTO);

    ResponseDTO processDeleteExhibitorNewUtilityInvoice(InvoiceUtilityDTO invoiceUtilityDTO);

    ResponseDTO processUpdateExhibitorNewBoothInvoiceFilePath(InvoiceBoothDTO invoiceBoothDTO);

    ResponseDTO processUpdateExhibitorNewUtilityInvoiceFilePath(InvoiceUtilityDTO invoiceUtilityDTO);

    ResponseDTO processUpdateExhibitorNewBoothInvoiceSendResult(InvoiceBoothDTO invoiceBoothDTO);

    ResponseDTO processUpdateExhibitorNewUtilityInvoiceSendResult(InvoiceUtilityDTO invoiceUtilityDTO);

    ExhibitorNewDTO getExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO);

    List<ExhibitorDTO> processSelectMngOnlineList(SearchDTO searchDTO);

    List<ExhibitorNewDTO> processSelectMngOnlineNewList(SearchDTO searchDTO);

    ResponseDTO processUpdateViewYn(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewOnlineViewYn(ExhibitorNewDTO exhibitorNewDTO);

    List<MngProductDTO> processSelectMngProductNewList(SearchDTO searchDTO);

    List<ExhibitorDTO> processSelectSignList(SearchDTO searchDTO);

    List<ExhibitorNewDTO> processSelectExhibitorNewSignList(SearchDTO searchDTO);

    ExhibitorDTO processSelectSignSingle(String seq);

    ExhibitorNewDTO processSelectExhibitorNewSignSingle(String seq);

    ResponseDTO processUpdateSign(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewSign(ExhibitorNewDTO exhibitorNewDTO);

    List<ExhibitorDTO> processSelectUtilityList(SearchDTO searchDTO);

    List<ExhibitorNewDTO> processSelectExhibitorNewUtilityList(SearchDTO searchDTO);

    ExhibitorDTO processSelectUtilitySingle(String seq);

    ExhibitorNewDTO processSelectExhibitorNewUtilitySingle(String seq);

    List<InvoiceUtilityDTO> processSelectInvoiceUtilityExSeqList(InvoiceUtilityDTO invoiceUtilityDTO);

    ResponseDTO processUpdateUtility(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewUtility(ExhibitorNewDTO exhibitorNewDTO);

    void updateInvoiceHistoryStatusToOpen(int historySeq);

    List<MngPassDTO> processSelectPassList(SearchDTO searchDTO);

    List<MngPassDTO> processSelectExhibitorNewPassList(SearchDTO searchDTO);

    List<MngPassDTO> processSelectPassDetailList(String seq);

    List<MngPassDTO> processSelectExhibitorNewPassDetailList(String seq);

    ResponseDTO processUpdatePass(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewPass(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processDeletePass(PassDTO passDTO);

    ResponseDTO processDeleteExhibitorNewPass(PassNewDTO passNewDTO);

    List<MngBuyerDTO> processSelectMngBuyerList(SearchDTO searchDTO);

    List<MngBuyerDTO> processSelectExhibitorNewBuyerList(SearchDTO searchDTO);

    List<MngBuyerDTO> processSelectBuyerDetailList(String seq);

    List<MngBuyerDTO> processSelectExhibitorNewBuyerDetailList(String seq);

    ResponseDTO processUpdateBuyer(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewBuyer(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processDeleteExhibitorNewBuyer(BuyerNewDTO buyerNewDTO);

    List<MngGiftDTO> processSelectMngGiftList(SearchDTO searchDTO);

    List<MngGiftDTO> processSelectExhibitorNewGiftList(SearchDTO searchDTO);

    List<MngGiftDTO> processSelectGiftDetailList(String seq);

    List<MngGiftDTO> processSelectExhibitorNewGiftDetailList(String seq);

    ResponseDTO processUpdateGift(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewGift(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processDeleteGift(GiftDTO giftDTO);

    ResponseDTO processDeleteExhibitorNewGift(GiftNewDTO giftNewDTO);

    List<PopupDTO> processSelectPopupList(SearchDTO searchDTO);

    PopupDTO processSelectPopupSingle(String seq);

    ResponseDTO processUpdatePopup(PopupDTO popupDTO);

    ResponseDTO processInsertPopup(PopupDTO popupDTO);

    ResponseDTO processDeletePopup(PopupDTO popupDTO);

    List<DirectoryDTO> processSelectDirectoryList(DirectoryDTO directoryDTO);

    StatisticsDTO processSelectCompanyCount(StatisticsDTO statisticsDTO);

    StatisticsDTO processSelectVisitorCount(StatisticsDTO statisticsDTO);

    StatisticsDTO processSelectProductCount(StatisticsDTO statisticsDTO);

    StatisticsDTO processSelectProductQtyCount(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> processSelectStatisticsAccessorDay(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> processSelectStatisticsAccessorMonth(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsAccessorWeek(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsParticipantCompanyDay(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsParticipantVisitorDay(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsParticipantCompanyMonth(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsParticipantVisitorMonth(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsParticipantCompanyWeek(StatisticsDTO reqDto);

    List<StatisticsDTO> processSelectStatisticsParticipantVisitorWeek(StatisticsDTO reqDto);

    StatisticsDTO processSelectStatisticsCompanyBooth(StatisticsDTO reqDto);

    StatisticsDTO processSelectStatisticsCompanyFieldPart(StatisticsDTO reqDto);

    ResponseDTO processUpdateApprovalStatus(List<ExhibitorDTO> exhibitorList);

    ResponseDTO processUpdateExhibitorNewApprovalStatus(List<ExhibitorNewDTO> exhibitorList);

    List<ExhibitorDetailDTO> processSelectExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO);

    List<ExhibitorNewDetailDTO> processSelectExhibitorNewDetailList(ExhibitorNewDetailDTO exhibitorNewDetailDTO);

    List<MemberDTO> processSelectMemberList(SearchDTO searchDTO);

    List<VisitorDTO> processSelectListTransferVisitor(SearchDTO searchDTO);

    List<AwardDTO> processSelectEventProductList(SearchDTO searchDTO);

    AwardDTO processSelectEventProductSingle(String seq);

    ResponseDTO processUpdateEventProduct(AwardDTO awardDTO);

    ResponseDTO processDeleteEventProduct(AwardDTO awardDTO);

    List<CompanyDTO> processSelectEventCompanyList(SearchDTO searchDTO);

    CompanyDTO processSelectEventCompanySingle(String seq);

    ResponseDTO processUpdateEventCompany(CompanyDTO companyDTO);

    ResponseDTO processDeleteEventCompany(CompanyDTO companyDTO);

    ResponseDTO processUpdatePrcYn(List<ExhibitorDTO> exhibitorList);

    ResponseDTO processUpdateExhibitorNewPrcYn(List<ExhibitorNewDTO> exhibitorList);

    ResponseDTO processUpdateVisitor(VisitorDTO visitorDTO);

    ResponseDTO processDeleteVisitor(VisitorDTO visitorDTO);

    ResponseDTO processDeletePartner(PartnerDTO partnerDTO);

    VisitorDTO processSelectVisitorSingle(String seq);

    List<PartnerDTO> processSelectPartnerList(String seq);

    List<ExhibitorDetailDTO> processSelectTransferExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO);

    List<VisitorDetailDTO> processSelectVisitorDetailList(VisitorDetailDTO visitorDetailDTO);

    List<VisitorDetailDTO> processSelectTransferVisitorDetailList(VisitorDetailDTO visitorDetailDTO);

    ResponseDTO processUpdateTransferCompany(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateTransferVisitor(VisitorDTO visitorDTO);

    List<PassDetailDTO> processSelectExcelPassDetailList(String transferYear);

    ResponseDTO processSaveAccessIp(List<AccessDTO> accessDTOList);

    ResponseDTO processSelectAccessIp(AccessDTO accessDTO);

    String processSelectAccessIpList();

    FileResponseDTO processUpdateFileUseN(FileDTO fileDTO);

    List<RequestDTO> processSelectRequestList(SearchDTO searchDTO);

    RequestDTO processSelectRequestSingle(RequestDTO requestDTO);

    ResponseDTO processInsertRequest(RequestDTO requestDTO);

    ResponseDTO processUpdateRequest(RequestDTO requestDTO);

    ResponseDTO processDeleteRequest(RequestDTO requestDTO);

    List<ReplyDTO> processSelectReplyList(String requestSeq);

    ResponseDTO processInsertReply(ReplyDTO replyDTO);

    ResponseDTO processDeleteReply(ReplyDTO replyDTO);

    ResponseDTO processUpdateRequestProgressStep(List<RequestDTO> requestList);

    ResponseDTO processUpdateRequestCompleteExpect(List<RequestDTO> requestList);

    List<ContestDTO> processSelectContestList(SearchDTO searchDTO);

    ContestDTO processSelectContestSingle(ContestDTO requestDto);

    ResponseDTO processDeleteContest(ContestDTO contestDTO);

    ResponseDTO processUpdateContest(ContestDTO contestDTO);

    ResponseDTO processInsertContest(ContestDTO contestDTO);

    void createAndDownloadProductExcel(HttpServletResponse response) throws Exception;

    ExhibitorNewDTO processSelectExhibitorNewInvoiceDetail(String seq);

    int processUpdateExhibitorNewSpecialDiscount(ExhibitorNewDTO dto);

    List<DepositHistoryDTO> selectDepositHistoryList(String exhibitorSeq);

    DepositHistoryDTO insertDepositHistory(DepositHistoryDTO dto);

    Map<String, Object> updateDepositHistory(DepositHistoryDTO dto);

    Map<String, Object> deleteDepositHistory(int depositSeq);

    InvoiceBoothDTO createAndInsertInvoiceBooth(String exhibitorSeq) throws Exception;

    List<InvoiceBoothDTO> getInvoiceBoothList(String exhibitorSeq);

    boolean updateInvoiceBoothFilePath(InvoiceBoothDTO invoiceDto);

    boolean deleteInvoiceBooth(int invoiceSeq);

    InvoiceUtilityDTO createAndInsertInvoiceUtility(String exhibitorSeq) throws Exception;

    List<InvoiceUtilityDTO> getInvoiceUtilityList(String seq);

    boolean deleteInvoiceUtility(int invoiceSeq);

    List<SignDetailDTO> processSelectExcelSignDetailList(String transferYear);

    List<UtilityDetailDTO> processSelectExcelUtilityDetailList(String transferYear);

    List<ProductDetailDTO> processSelectExcelProductDetailList(String transferYear);

    List<InvoiceSendHistoryDTO> getInvoiceSendHistory(int invoiceSeq, String invoiceType);

    ExhibitorNewDTO getExhibitorInfoForLogin(String exhibitorSeq);

    List<ExhibitorNewDTO> processSelectExhibitorNewMaritimeList(SearchDTO searchDTO);

    ExhibitorNewDTO processSelectExhibitorNewMaritimeSingle(String seq);

    ResponseDTO processUpdateExhibitorNewMaritime(ExhibitorNewDTO exhibitorNewDTO);

    List<String> getExhibitorNewEmailList(ExhibitorNewDTO exhibitorNewDTO);

    List<BoothDetailDTO> processSelectExcelBoothDetailList(String transferYear);

    int updateCompanyBadge(ExhibitorNewDTO dto);

    BrochureDTO getBrochureInfo(String currentYear);

    void saveOrUpdateBrochureInfo(BrochureDTO formData);

    void updateInvoiceHistoryStatusByMailId(String reserveId, String status);

    List<BuyerDetailDTO> processSelectExcelBuyerDetailList(String transferYear);
}