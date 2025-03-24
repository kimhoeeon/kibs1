package com.mtf.kibs.service;

import com.mtf.kibs.dto.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The interface GoingSool service.
 */
public interface KibsMngService {

    /**
     * Login check customer dto.
     *
     * @param adminDTO the admin dto
     * @param session     the session
     * @return the customer dto
     */
    AdminDTO login(AdminDTO adminDTO, HttpSession session);

    /**
     * Logout check.
     *
     * @param session the session
     */
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

    List<BrochureDTO> processSelectBrochureList(SearchDTO searchDTO);

    BrochureDTO processSelectBrochureSingle(BrochureDTO brochureDTO);

    ResponseDTO processDeleteBrochure(BrochureDTO brochureDTO);

    ResponseDTO processUpdateBrochure(BrochureDTO brochureDTO);

    ResponseDTO processInsertBrochure(BrochureDTO brochureDTO);

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

    ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateFileUserId(FileDTO fileDTO);

    ResponseDTO processInsertParticipantCompany(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateParticipantCompany(ExhibitorDTO exhibitorDTO);

    ResponseDTO processDeleteParticipantCompany(ExhibitorDTO exhibitorDTO);

    List<DisplayDTO> processSelectDisplayList(DisplayDTO displayDTO);

    List<OnlineDTO> processSelectOnlineList(OnlineDTO onlineDTO);

    List<BuyerDTO> processSelectBuyerList(BuyerDTO buyerDTO);

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

    ResponseDTO processDeleteCharge(ChargePersonDTO chargePersonDTO);

    List<ExhibitorDTO> processSelectBoothList(SearchDTO searchDTO);

    ExhibitorDTO processSelectBoothSingle(String seq);

    ResponseDTO processUpdateBooth(ExhibitorDTO exhibitorDTO);

    List<ExhibitorDTO> processSelectMngOnlineList(SearchDTO searchDTO);

    ResponseDTO processUpdateViewYn(ExhibitorDTO exhibitorDTO);

    List<ExhibitorDTO> processSelectSignList(SearchDTO searchDTO);

    ExhibitorDTO processSelectSignSingle(String seq);

    ResponseDTO processUpdateSign(ExhibitorDTO exhibitorDTO);

    List<ExhibitorDTO> processSelectUtilityList(SearchDTO searchDTO);

    ExhibitorDTO processSelectUtilitySingle(String seq);

    ResponseDTO processUpdateUtility(ExhibitorDTO exhibitorDTO);

    List<MngPassDTO> processSelectPassList(SearchDTO searchDTO);

    List<MngPassDTO> processSelectPassDetailList(String seq);

    ResponseDTO processUpdatePass(ExhibitorDTO exhibitorDTO);

    ResponseDTO processDeletePass(PassDTO passDTO);

    List<MngBuyerDTO> processSelectMngBuyerList(SearchDTO searchDTO);

    List<MngBuyerDTO> processSelectBuyerDetailList(String seq);

    ResponseDTO processUpdateBuyer(ExhibitorDTO exhibitorDTO);

    List<MngGiftDTO> processSelectMngGiftList(SearchDTO searchDTO);

    List<MngGiftDTO> processSelectGiftDetailList(String seq);

    ResponseDTO processUpdateGift(ExhibitorDTO exhibitorDTO);

    ResponseDTO processDeleteGift(GiftDTO giftDTO);

    List<PopupDTO> processSelectPopupList(SearchDTO searchDTO);

    PopupDTO processSelectPopupSingle(String seq);

    ResponseDTO processUpdatePopup(PopupDTO popupDTO);

    ResponseDTO processInsertPopup(PopupDTO popupDTO);

    ResponseDTO processDeletePopup(PopupDTO popupDTO);

    List<DirectoryDTO> processSelectDirectoryList(DirectoryDTO directoryDTO);

    StatisticsDTO processSelectCompanyCount(StatisticsDTO statisticsDTO);

    StatisticsDTO processSelectVisitorCount(StatisticsDTO statisticsDTO);

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

    List<ExhibitorDetailDTO> processSelectExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO);

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

    List<PassDetailDTO> processSelectExcelPassDetailList(PassDetailDTO passDetailDTO);

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

}