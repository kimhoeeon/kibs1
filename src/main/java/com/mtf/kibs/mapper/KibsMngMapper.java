package com.mtf.kibs.mapper;

import com.mtf.kibs.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * The interface Kibs Mng mapper.
 */
@Repository
public interface KibsMngMapper {

    AdminDTO login(AdminDTO customerDTO);

    Integer insertExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    List<ParticipantCompanyDTO> selectListExhibitorComp(SearchDTO searchDTO);

    Integer deleteExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    ParticipantCompanyDTO selectExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    Integer updateExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    Integer checkExhibitorComp(ParticipantCompanyDTO participantCompanyDTO);

    List<NoticeDTO> selectNoticeList(SearchDTO searchDTO);

    NoticeDTO selectNoticeSingle(NoticeDTO noticeDTO);

    Integer deleteBoardNotice(NoticeDTO noticeDTO);

    Integer updateBoardNotice(NoticeDTO noticeDTO);

    Integer insertBoardNotice(NoticeDTO noticeDTO);

    List<NewsletterDTO> selectNewsletterList(SearchDTO searchDTO);

    NewsletterDTO selectNewsletterSingle(NewsletterDTO newsletterDTO);

    Integer deleteNewsletter(NewsletterDTO newsletterDTO);

    Integer updateNewsletter(NewsletterDTO newsletterDTO);

    Integer insertNewsletter(NewsletterDTO newsletterDTO);

    List<PressDTO> selectPressList(SearchDTO searchDTO);

    PressDTO selectPressSingle(PressDTO pressDTO);

    Integer deletePress(PressDTO pressDTO);

    Integer updatePress(PressDTO pressDTO);

    Integer insertPress(PressDTO pressDTO);

    List<FaqDTO> selectFaqList(SearchDTO searchDTO);

    FaqDTO selectFaqSingle(FaqDTO faqDTO);

    Integer deleteFaq(FaqDTO faqDTO);

    Integer updateFaq(FaqDTO faqDTO);

    Integer insertFaq(FaqDTO faqDTO);

    List<ColumnDTO> selectColumnList(SearchDTO searchDTO);

    ColumnDTO selectColumnSingle(ColumnDTO columnDTO);

    Integer deleteColumn(ColumnDTO columnDTO);

    Integer updateColumn(ColumnDTO columnDTO);

    Integer insertColumn(ColumnDTO columnDTO);

    List<BrochureDTO> selectBrochureList(SearchDTO searchDTO);

    BrochureDTO selectBrochureSingle(BrochureDTO brochureDTO);

    Integer deleteBrochure(BrochureDTO brochureDTO);

    Integer updateBrochure(BrochureDTO brochureDTO);

    Integer insertBrochure(BrochureDTO brochureDTO);

    List<DataroomDTO> selectDataroomList(SearchDTO searchDTO);

    DataroomDTO selectDataroomSingle(DataroomDTO dataroomDTO);

    Integer deleteDataroom(DataroomDTO dataroomDTO);

    Integer updateDataroom(DataroomDTO dataroomDTO);

    Integer insertDataroom(DataroomDTO dataroomDTO);

    List<GalleryDTO> selectGalleryList(SearchDTO searchDTO);

    GalleryDTO selectGallerySingle(GalleryDTO galleryDTO);

    Integer deleteGallery(GalleryDTO galleryDTO);

    Integer updateGallery(GalleryDTO galleryDTO);

    Integer insertGallery(GalleryDTO galleryDTO);

    List<KibstvDTO> selectKibstvList(SearchDTO searchDTO);

    KibstvDTO selectKibstvSingle(KibstvDTO kibstvDTO);

    Integer deleteKibstv(KibstvDTO kibstvDTO);

    Integer updateKibstv(KibstvDTO kibstvDTO);

    Integer insertKibstv(KibstvDTO kibstvDTO);

    Integer insertFileInfo(FileDTO fileDTO);

    String getFileId();

    List<FileDTO> selectFileList(FileDTO fileDTO);

    FileDTO selectFileInfo(FileDTO fileDTO);

    Integer updateFileInfo(FileDTO fileDTO);

    Integer updateFileUserId(FileDTO fileDTO);

    List<ExhibitorDTO> selectListExhibitor(SearchDTO searchDTO);

    ExhibitorDTO selectExhibitorSingle(ExhibitorDTO exhibitorDTO);

    String getExhibitorSeq();

    Integer insertParticipantCompany(ExhibitorDTO exhibitorDTO);

    Integer insertParticipantCompanyDisplay(DisplayDTO displayDTO);

    Integer insertParticipantCompanyOnline(OnlineDTO onlineDTO);

    Integer insertParticipantCompanyBuyer(BuyerDTO buyerDTO);

    List<DisplayDTO> selectDisplayList(DisplayDTO displayDTO);

    List<OnlineDTO> selectOnlineList(OnlineDTO onlineDTO);

    List<BuyerDTO> selectBuyerList(BuyerDTO buyerDTO);

    Integer updateParticipantCompany(ExhibitorDTO exhibitorDTO);

    Integer updateParticipantCompanyDisplay(DisplayDTO displayDTO);

    Integer updateParticipantCompanyOnline(OnlineDTO onlineDTO);

    Integer updateParticipantCompanyBuyer(BuyerDTO buyerDTO);

    Integer checkParticipantCompanyDisplay(DisplayDTO displayDTO);

    Integer checkParticipantCompanyOnline(OnlineDTO onlineDTO);

    Integer checkParticipantCompanyBuyer(BuyerDTO buyerDTO);

    BuyerDTO selectBuyerSingle(BuyerDTO buyerDTO);

    Integer deleteBuyer(BuyerDTO buyerDTO);

    Integer deleteParticipantCompany(ExhibitorDTO exhibitorDTO);

    String getNoticeId();

    String getNewsletterId();

    String getPressId();

    String getFaqId();

    String getColumnId();

    String getBrochureId();

    String getDataroomId();

    String getGalleryId();

    Integer deleteParticipantCompanyDisplay(DisplayDTO displayDTO);

    Integer deleteParticipantCompanyOnline(OnlineDTO onlineDTO);

    Integer deleteParticipantCompanyBuyer(BuyerDTO buyerDTO);

    List<EdmHstDTO> selectEdmHstList(SearchDTO searchDTO);

    Integer insertEdmHst(EdmHstDTO edmHstDTO);

    Integer deleteEdmHst(EdmHstDTO edmHstDTO);

    Integer deleteOnline(OnlineDTO onlineDTO);

    Integer deleteDisplay(DisplayDTO displayDTO);

    Integer deleteFile(FileDTO fileDTO);

    List<ExhibitorDTO> selectListTransferCompany(SearchDTO searchDTO);

    Integer checkExhibitor(ExhibitorDTO exhibitorDTO);

    List<VisitorDTO> selectVisitorList(SearchDTO searchDTO);

    List<ChargePersonDTO> selectChargeList(ChargePersonDTO chargePersonDTO);

    Integer deleteCharge(ChargePersonDTO chargePersonDTO);

    Integer checkParticipantCompanyCharge(ChargePersonDTO request);

    Integer insertParticipantCompanyCharge(ChargePersonDTO request);

    Integer updateParticipantCompanyCharge(ChargePersonDTO request);

    List<ExhibitorDTO> selectBoothList(SearchDTO searchDTO);

    ExhibitorDTO selectBoothSingle(String seq);

    Integer UpdateBooth(ExhibitorDTO exhibitorDTO);

    List<ExhibitorDTO> selectMngOnlineList(SearchDTO searchDTO);

    Integer updateViewYn(ExhibitorDTO exhibitorDTO);

    List<ExhibitorDTO> selectSignList(SearchDTO searchDTO);

    ExhibitorDTO selectSignSingle(String seq);

    Integer updateSign(ExhibitorDTO exhibitorDTO);

    List<ExhibitorDTO> selectUtilityList(SearchDTO searchDTO);

    ExhibitorDTO selectUtilitySingle(String seq);

    Integer updateUtility(ExhibitorDTO exhibitorDTO);

    List<MngPassDTO> selectPassList(SearchDTO searchDTO);

    List<MngPassDTO> selectPassDetailList(String seq);

    ExhibitorDTO getExhibitorId(String exhibitorSeq);

    Integer updatePass(PassDTO passDTO);

    Integer insertPass(PassDTO passDTO);

    Integer deletePass(PassDTO passDTO);

    List<MngBuyerDTO> selectMngBuyerList(SearchDTO searchDTO);

    List<MngBuyerDTO> selectBuyerDetailList(String seq);

    List<MngGiftDTO> selectMngGiftList(SearchDTO searchDTO);

    List<MngGiftDTO> selectGiftDetailList(String seq);

    Integer updateExhibitGift(GiftDTO giftDTO);

    Integer insertExhibitGift(GiftDTO giftDTO);

    Integer deleteGift(GiftDTO giftDTO);

    Integer updateFileYn(FileDTO photoReq);

    List<PopupDTO> selectPopupList(SearchDTO searchDTO);

    PopupDTO selectPopupSingle(String seq);

    Integer updatePopup(PopupDTO popupDTO);

    Integer insertPopup(PopupDTO popupDTO);

    String getPopupId();

    Integer deletePopup(PopupDTO popupDTO);

    Integer getActivePopupCount(PopupDTO popupDTO);

    List<DirectoryDTO> selectDirectoryList(DirectoryDTO directoryDTO);

    StatisticsDTO selectCompanyCount(StatisticsDTO statisticsDTO);

    StatisticsDTO selectVisitorCount(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsAccessorDay(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsAccessorMonth(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsAccessorWeek(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsParticipantCompanyDay(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsParticipantVisitorDay(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsParticipantCompanyMonth(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsParticipantVisitorMonth(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsParticipantCompanyWeek(StatisticsDTO statisticsDTO);

    List<StatisticsDTO> selectStatisticsParticipantVisitorWeek(StatisticsDTO statisticsDTO);

    StatisticsDTO selectStatisticsCompanyBooth(StatisticsDTO statisticsDTO);

    StatisticsDTO selectStatisticsCompanyFieldPart(StatisticsDTO statisticsDTO);

    Integer updateApprovalStatus(ExhibitorDTO exhibitorInfo);

    ExhibitorDTO getExhibitorInfo(Map<String,String> paramMap);

    ParticipantCompanyDTO getExhibitorCompInfo(Map<String, String> paramMap);

    String getExhibitorCompSeq();

    List<ExhibitorDetailDTO> selectExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO);

    List<MemberDTO> selectMemberList(SearchDTO searchDTO);

    List<VisitorDTO> selectListTransferVisitor(SearchDTO searchDTO);

    List<AwardDTO> selectEventProductList(SearchDTO searchDTO);

    AwardDTO selectEventProductSingle(String seq);

    Integer updateEventProduct(AwardDTO awardDTO);

    Integer insertEventProduct(AwardDTO awardDTO);

    String getEventProductSeq();

    Integer deleteEventProduct(AwardDTO awardDTO);

    List<CompanyDTO> selectEventCompanyList(SearchDTO searchDTO);

    CompanyDTO selectEventCompanySingle(String seq);

    Integer updateEventCompany(CompanyDTO companyDTO);

    Integer insertEventCompany(CompanyDTO companyDTO);

    String getEventCompanySeq();

    Integer deleteEventCompany(CompanyDTO companyDTO);

    Integer updatePrcYn(ExhibitorDTO exhibitorInfo);

    Integer checkVisitor(VisitorDTO visitorDTO);

    Integer updateVisitor(VisitorDTO visitorDTO);

    Integer insertVisitor(VisitorDTO visitorDTO);

    String getVisitorSeq();

    Integer updatePartner(PartnerDTO partner);

    Integer insertPartner(PartnerDTO partner);

    String getPartnerSeq();

    Integer deleteVisitor(VisitorDTO visitorDTO);

    Integer deletePartner(PartnerDTO partnerDTO);

    VisitorDTO selectVisitorSingle(String seq);

    List<PartnerDTO> selectPartnerList(String seq);

    List<String> selectFileSeq(FileDTO fileDTO);

    Integer updateProductImageFileNote(FileDTO updFileDTO);

    List<ExhibitorDetailDTO> selectTransferExhibitorDetailList(ExhibitorDetailDTO exhibitorDetailDTO);

    List<VisitorDetailDTO> selectVisitorDetailList(VisitorDetailDTO visitorDetailDTO);

    List<VisitorDetailDTO> selectTransferVisitorDetailList(VisitorDetailDTO visitorDetailDTO);

    List<PassDetailDTO> selectExcelPassDetailList(PassDetailDTO passDetailDTO);

    Integer saveAccessIp(AccessDTO info);

    Integer deleteAccessIp();

    Integer selectAccessIp(AccessDTO accessDTO);

    String selectAccessIpList();

    Integer updateFileUseN(FileDTO fileDTO);

    List<RequestDTO> selectRequestList(SearchDTO searchDTO);

    RequestDTO selectRequestSingle(RequestDTO requestDTO);

    String getRequestSeq();

    Integer insertRequest(RequestDTO requestDTO);

    Integer updateRequest(RequestDTO requestDTO);

    Integer deleteRequest(RequestDTO requestDTO);

    List<ReplyDTO> selectReplyList(String requestSeq);

    String getReplySeq();

    Integer insertReply(ReplyDTO replyDTO);

    Integer deleteReply(ReplyDTO replyDTO);

    Integer updateRequestProgressStep(RequestDTO info);

    Integer updateRequestCompleteExpect(RequestDTO info);
}