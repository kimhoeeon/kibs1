package com.mtf.kibs.mapper;

import com.mtf.kibs.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface KibsMapper {

    Integer checkDuplicateId(ExhibitorNewDTO exhibitorNewDTO);

    Integer insertExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    Integer insertDisplayInfo(DisplayDTO displayDTO);

    Integer insertOnlineInfo(OnlineDTO onlineDTO);

    Integer insertBuyerInfo(BuyerDTO buyerDTO);

    Integer updateExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    ExhibitorNewDTO selectExhibitorNewSingle(ExhibitorNewDTO exhibitorNewDTO);

    Integer updateExhibitorNewPassword(ExhibitorNewDTO updateExhibitorNewInfo);

    Integer updateChargeNew(ChargeNewDTO chargeNewDTO);

    Integer updateImageFileNote(FileDTO updFileDTO);

    List<ChargeNewDTO> selectChargeNewList(ChargeNewDTO chargeNewDTO);

    List<ProductNewDTO> selectProductNewList(ProductNewDTO productNewDTO);

    List<OnlineNewDTO> selectOnlineNewList(OnlineNewDTO onlineNewDTO);

    List<BuyerNewDTO> selectBuyerNewList(BuyerNewDTO buyerNewDTO);

    Integer updateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO);

    Integer updateExhibitorNewCompanySign(ExhibitorNewDTO exhibitorNewDTO);

    Integer updateExhibitorNewUtility(ExhibitorNewDTO exhibitorNewDTO);

    Integer updateExhibitorNewNote(ExhibitorNewDTO exhibitorNewDTO);

    Integer updatePassNew(PassNewDTO passNewDTO);

    Integer insertPassNew(PassNewDTO passNewDTO);

    List<PassNewDTO> selectPassNewList(PassNewDTO passNewDTO);

    String searchExhibitorNewSeq(ExhibitorNewDTO exhibitorNewDTO);

    List<GiftNewDTO> selectGiftNewList(GiftNewDTO giftNewDTO);

    Integer updateExhibitorNewApprovalStatus(ExhibitorNewDTO exhibitorNewDTO);

    void updateGiftFileUnused(List<String> fileIds);

    void deleteAllGiftNew(String exSeq);

    Integer updateGiftNew(GiftNewDTO giftNewDTO);

    Integer insertGiftNew(GiftNewDTO giftNewDTO);

    String getExhibitorNewSalt(ExhibitorNewDTO exhibitorNewDTO);

    String checkLoginExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    ExhibitorNewDTO selectExhibitorNewPrc(ExhibitorNewDTO exhibitorNewDTO);

    Integer updateExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO);

    String getExhibitorNewEmail(ExhibitorNewDTO exhibitorNewDTO);

    List<String> preGetExhibitorEmail(ExhibitorDTO exhibitorDTO);

    Integer deletePassNew(PassNewDTO passNewDTO);

    Integer deleteChargeNew(ChargeNewDTO chargeNewDTO);

    Integer deleteProductNew(ProductNewDTO productNewDTO);

    Integer deleteOnlineNew(OnlineNewDTO onlineNewDTO);

    Integer deleteFile(FileDTO fileDTO);


    Integer updateExhibitBooth(ExhibitorDTO exhibitorDTO);

    Integer updateCompanySign(ExhibitorDTO exhibitorDTO);

    Integer updateExhibitUtility(ExhibitorDTO exhibitorDTO);

    Integer insertExhibitPass(PassDTO passDTO);

    Integer insertExhibitGift(GiftDTO giftDTO);

    Integer updateWebbanner(ExhibitorDTO exhibitorDTO);

    Integer insertDirectory(DirectoryDTO directoryDTO);

    Integer updateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO);

    Integer checkLoginExhibit(ExhibitorDTO exhibitorDTO);

    List<MainOnlineDTO> selectOnlineList(String transferYear);

    List<MainOnlineDTO> selectEnOnlineListPaging(MainOnlineDTO mainOnlineDTO);

    List<MainOnlineDTO> selectOnlineListPaging(MainOnlineDTO mainOnlineDTO);

    ExhibitorDTO selectOnlineExhibitorInfo(String seq);

    ExhibitorNewDTO selectOnlineExhibitorNewInfo(String seq);

    List<OnlineDTO> selectOnlineInfoList(String seq);

    List<ProductNewDTO> selectProductNewInfoList(String seq);

    List<OnlineNewDTO> selectOnlineNewInfoList(String seq);

    List<FileDTO> selectFileList(String seq);

    OnlineDTO selectOnlineInfo(String seq);

    ProductNewDTO selectProductNewInfo(String seq);

    OnlineNewDTO selectOnlineNewInfo(String seq);

    List<OnlineRelatedDTO> selectOnlineRelatedList(OnlineDTO onlineDTO);

    List<FileDTO> selectProductFileInfo(FileDTO fileDTO);

    FileDTO selectLogoFileInfo(FileDTO fileDTO);

    List<OnlineRelatedDTO> selectOnlineProductListPaging(OnlineRelatedDTO onlineRelatedDTO);

    List<NoticeDTO> selectNoticeList(SearchDTO searchDTO);

    Integer updateNoticeViewCnt(String id);

    NoticeDTO selectNoticeSingle(String id);

    List<FaqDTO> selectFaqList(SearchDTO searchDTO);

    List<PressDTO> selectPressList(SearchDTO searchDTO);

    Integer updatePressViewCnt(String id);

    PressDTO selectPressSingle(String id);

    List<ColumnDTO> selectColumnList(SearchDTO searchDTO);

    Integer updateColumnViewCnt(String id);

    ColumnDTO selectColumnSingle(String id);

    List<NewsletterDTO> selectNewsletterList(SearchDTO searchDTO);

    Integer updateNewsletterViewCnt(String id);

    NewsletterDTO selectNewsletterSingle(String id);

    List<KibstvDTO> selectKibstvList(SearchDTO searchDTO);

    List<DataroomDTO> selectGalleryList(SearchDTO searchDTO);

    List<NoticeDTO> selectMainNoticeList(String lang);

    List<ColumnDTO> selectMainColumnList();

    List<KibstvDTO> selectMainKibstvList(String lang);

    String getExhibitorNewSeq();

    Integer updateExhibitNote(ExhibitorDTO exhibitorDTO);

    String searchExhibitorSeq(ExhibitorDTO seqReqDTO);

    ExhibitorDTO selectExhibitorInfo(ExhibitorDTO exhibitorDTO);

    List<ChargePersonDTO> selectChargeInfo(ChargePersonDTO chargePersonDTO);

    List<DisplayDTO> selectDisplayInfo(DisplayDTO displayDTO);

    FileDTO selectFileInfo(FileDTO fileDTO);

    List<EnExhibitorListDTO> selectEnExhibitorList(SearchDTO searchDTO);

    Integer checkDirectory(DirectoryDTO directoryDTO);

    Integer updateDirectory(DirectoryDTO directoryDTO);

    Integer updateChargePersonInfo(ChargePersonDTO chargePersonDTO);

    Integer updateDisplayInfo(DisplayDTO displayDTO);

    ExhibitorDTO selectExhibitorPrc(ExhibitorDTO exhibitorDTO);

    ExhibitorDTO selectExhibitorSingle(ExhibitorDTO exhibitorDTO);

    List<ChargePersonDTO> selectChargeListSeq(ChargePersonDTO chargePersonDTO);

    List<DisplayDTO> selectDisplayListSeq(DisplayDTO displayDTO);

    List<OnlineDTO> selectOnlineListSeq(OnlineDTO onlineDTO);

    List<BuyerDTO> selectBuyerListSeq(BuyerDTO buyerDTO);

    BuyerNewDTO selectBuyerNewSingle(BuyerNewDTO buyerNewDTO);

    Integer deleteBuyerNew(BuyerNewDTO buyerNewDTO);

    Integer updateExhibitorDisplay(DisplayDTO request);

    Integer updateExhibitorOnline(OnlineDTO request);

    Integer updateExhibitorBuyer(BuyerDTO request);

    Integer updateExhibitBoothSeq(ExhibitorDTO exhibitorDTO);

    Integer updateCompanySignSeq(ExhibitorDTO exhibitorDTO);

    Integer updateExhibitUtilitySeq(ExhibitorDTO exhibitorDTO);

    List<PassDTO> selectPassList(PassDTO passDTO);

    Integer updateExhibitPass(PassDTO passDTO);

    GiftDTO selectExhibitorGift(GiftDTO giftDTO);

    List<GiftDTO> selectGiftList(GiftDTO giftDTO);

    Integer updateExhibitGift(GiftDTO giftDTO);

    Integer deleteExhibitGift(GiftDTO giftDTO);

    DirectoryDTO selectDirectoryInfo(DirectoryDTO directoryDTO);

    Integer updateExhibitPersonalInfo(ExhibitorDTO exhibitorDTO);

    List<PopupDTO> selectPopupList(PopupDTO popupDTO);

    PopupDTO selectPopupSingle(PopupDTO popupDTO);

    String checkStatisticsAccessor(StatisticsDTO statisticsDTO);

    Integer updateStatisticsAccessor(StatisticsDTO reqDto);

    Integer insertStatisticsAccessor(StatisticsDTO reqDto);

    ParticipantCompanyDTO getExhibitorCompInfo(Map<String, String> paramMap);

    Integer updateExhibitorComp(ParticipantCompanyDTO updExhibitorCompInfo);

    String getExhibitorCompSeq();

    Integer insertExhibitorComp(ParticipantCompanyDTO istExhibitorCompInfo);

    List<ChargePersonDTO> selectChargeEmail(ChargePersonDTO chargePersonDTO);

    Integer updateExhibitorOnlineViewYn(ExhibitorDTO exhibitorDTO);

    List<KibstvDTO> selectKibstvYearList(SearchDTO searchDTO);

    InstaTokenDTO selectInstaRefresh();

    Integer updateInstaRefresh(InstaTokenDTO instaTokenDTO);

    VisitorDTO selectPreVisitorCheck(VisitorDTO visitorDTO);

    Integer updateVisitor(VisitorDTO visitorDTO);

    String getVisitorSeq();

    Integer insertVisitor(VisitorDTO visitorDTO);

    Integer updatePartner(PartnerDTO partnerDTO);

    String getPartnerSeq();

    Integer insertPartner(PartnerDTO partnerDTO);

    VisitorDTO selectVisitorSingle(String seq);

    List<PartnerDTO> selectPartnerList(String seq);

    Integer deletePartner(PartnerDTO partnerDTO);

    Integer deletePartnerAll(String visitorSeq);

    void insertBusiness(BusinessDTO businessDTO);

    Integer checkBusiness(BusinessDTO businessDTO);

    ExhibitorDTO selectPreExhibitorSingle(ExhibitorDTO exhibitorDTO);

    Integer insertChargeNew(ChargeNewDTO chargeNewDTO);

    Integer insertProductNew(ProductNewDTO productNewDTO);

    Integer insertOnlineNew(OnlineNewDTO onlineNewDTO);

    Integer insertBuyerNew(BuyerNewDTO buyerNewDTO);

    Integer updateProductNew(ProductNewDTO productNew);

    Integer updateOnlineNew(OnlineNewDTO onlineNew);

    Integer updateBuyerNew(BuyerNewDTO buyerNew);

    List<String> selectProductNewFileSeq(FileDTO fileDTO);

    List<String> selectOnlineFileSeq(FileDTO fileDTO);

    List<ContestDTO> selectContestList(SearchDTO searchDTO);

    Integer updateExhibitorNewBoatEntryYn(ExhibitorNewDTO exhibitorNewDTO);

    void deleteProductNewExSeq(String exSeq);

    String getExhibitorNewRepEmail(ExhibitorNewDTO exhibitorNewDTO);

    Integer updateExhibitorNewMaritime(ExhibitorNewDTO exhibitorNewDTO);

    ExhibitorNewDTO selectExhibitorNewInvoiceDetail(String seq);

    BrochureDTO selectBrochureInfo(String year);
}