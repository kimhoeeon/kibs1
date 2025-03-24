package com.mtf.kibs.mapper;

import com.mtf.kibs.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface KibsMapper {

    Integer checkDuplicateId(ExhibitorDTO exhibitorDTO);

    Integer insertExhibitor(ExhibitorDTO exhibitorDTO);

    Integer insertDisplayInfo(DisplayDTO displayDTO);

    Integer insertPromotionInfo(PromotionDTO promotionDTO);

    Integer insertOnlineInfo(OnlineDTO onlineDTO);

    Integer insertBuyerInfo(BuyerDTO buyerDTO);

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

    List<MainOnlineDTO> selectOnlineListPaging(MainOnlineDTO mainOnlineDTO);

    ExhibitorDTO selectOnlineExhibitorInfo(String seq);

    List<OnlineDTO> selectOnlineInfoList(String seq);

    List<FileDTO> selectFileList(String seq);

    OnlineDTO selectOnlineInfo(String seq);

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

    List<GalleryDTO> selectGalleryList(SearchDTO searchDTO);

    List<NoticeDTO> selectMainNoticeList(String lang);

    List<ColumnDTO> selectMainColumnList();

    List<KibstvDTO> selectMainKibstvList(String lang);

    String getExhibitorSeq();

    Integer insertChargePersonInfo(ChargePersonDTO chargePersonDTO);

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

    BuyerDTO selectBuyerSingle(BuyerDTO buyerDTO);

    Integer deleteBuyer(BuyerDTO buyerDTO);

    Integer UpdateExhibitor(ExhibitorDTO exhibitorDTO);

    Integer updateExhibitorCharge(ChargePersonDTO request);

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

    Integer deleteCharge(ChargePersonDTO chargePersonDTO);

    Integer deleteDisplay(DisplayDTO displayDTO);

    Integer deleteOnline(OnlineDTO onlineDTO);

    Integer deleteFile(FileDTO fileDTO);

    Integer updateExhibitPersonalInfo(ExhibitorDTO exhibitorDTO);

    List<PopupDTO> selectPopupList(PopupDTO popupDTO);

    Integer deleteExhibitPass(PassDTO passDTO);

    String getExhibitorEmail(ExhibitorDTO exhibitorDTO);

    Integer updateExhibitPasswordInit(ExhibitorDTO exhibitorDTO);

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

    List<String> selectFileSeq(FileDTO fileDTO);

    Integer updateProductImageFileNote(FileDTO updFileDTO);

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
}