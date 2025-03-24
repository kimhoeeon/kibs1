package com.mtf.kibs.service;

import com.mtf.kibs.dto.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface KibsService {

    Integer checkDuplicateId(ExhibitorDTO exhibitorDTO);

    ResponseDTO processLoginExhibit(ExhibitorDTO exhibitorDTO);

    void logoutCheck(HttpSession session);

    ResponseDTO processInsertExhibitor(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitBooth(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateCompanySign(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitUtility(ExhibitorDTO exhibitorDTO);

    ResponseDTO processInsertExhibitPass(ExhibitorDTO exhibitorDTO);

    ResponseDTO processInsertExhibitGift(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateWebbanner(ExhibitorDTO exhibitorDTO);

    ResponseDTO processInsertDirectory(DirectoryDTO directoryDTO);

    ResponseDTO processUpdateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO);

    List<MainOnlineDTO> processSelectOnlineList(String transferYear);

    List<MainOnlineDTO> processSelectOnlineListPaging(MainOnlineDTO mainOnlineDTO);

    ExhibitorDTO processSelectOnlineExhibitorInfo(String seq);

    List<OnlineDTO> processSelectOnlineInfoList(String seq);

    List<FileDTO> processSelectFileList(String seq);

    OnlineDTO processSelectOnlineInfo(String seq);

    List<OnlineRelatedDTO> processSelectOnlineRelatedList(OnlineDTO onlineDTO);

    List<FileDTO> processSelectProductFileInfo(FileDTO relatedItemFileRequestDto);

    List<OnlineRelatedDTO> processSelectOnlineProductListPaging(OnlineRelatedDTO onlineRelatedDTO);

    FileDTO processSelectLogoFileInfo(FileDTO relatedItemFileRequestDto);

    List<NoticeDTO> processSelectNoticeList(SearchDTO searchDTO);

    ResponseDTO processUpdateNoticeViewCnt(String id);

    NoticeDTO processSelectNoticeSingle(String id);

    List<FaqDTO> processSelectFaqList(SearchDTO searchDTO);

    List<PressDTO> processSelectPressList(SearchDTO searchDTO);

    ResponseDTO processUpdatePressViewCnt(String id);

    PressDTO processSelectPressSingle(String id);

    List<ColumnDTO> processSelectColumnList(SearchDTO searchDTO);

    ResponseDTO processUpdateColumnViewCnt(String id);

    ColumnDTO processSelectColumnSingle(String id);

    List<NewsletterDTO> processSelectNewsletterList(SearchDTO searchDTO);

    ResponseDTO processUpdateNewsletterViewCnt(String id);

    NewsletterDTO processSelectNewsletterSingle(String id);

    List<KibstvDTO> processSelectKibstvList(SearchDTO searchDTO);

    List<GalleryDTO> processSelectGalleryList(SearchDTO searchDTO);

    List<NoticeDTO> processSelectMainNoticeList(String lang);

    List<ColumnDTO> processSelectMainColumnList();

    List<KibstvDTO> processSelectMainKibstvList(String lang);

    String processSearchExhibitorSeq(ExhibitorDTO exhibitorDTO);

    ExhibitorDTO processSelectExhibitorInfo(ExhibitorDTO exhibitorDTO);

    List<ChargePersonDTO> processSelectChargeInfo(ChargePersonDTO chargePersonDTO);

    List<DisplayDTO> processSelectDisplayInfo(DisplayDTO displayDTO);

    FileDTO processSelectFileInfo(FileDTO fileDTO);

    List<EnExhibitorListDTO> processSelectEnExhibitorList(SearchDTO searchDTO);

    ExhibitorDTO processSelectExhibitorPrc(ExhibitorDTO exhibitorDTO);

    ExhibitorDTO processSelectExhibitorSingle(ExhibitorDTO exhibitorDTO);

    List<ChargePersonDTO> processSelectChargeListSeq(ChargePersonDTO chargeReq);

    List<DisplayDTO> processSelectDisplayListSeq(DisplayDTO displayReq);

    List<OnlineDTO> processSelectOnlineListSeq(OnlineDTO onlineReq);

    List<BuyerDTO> processSelectBuyerListSeq(BuyerDTO buyerReq);

    BuyerDTO processSelectBuyerSingle(BuyerDTO buyerDTO);

    ResponseDTO processDeleteBuyer(BuyerDTO buyerDTO);

    ResponseDTO processUpdateExhibitor(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitBoothSeq(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateCompanySignSeq(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitUtilitySeq(ExhibitorDTO exhibitorDTO);

    List<PassDTO> processSelectPassList(PassDTO passDTO);

    ResponseDTO processUpdateExhibitPassSeq(ExhibitorDTO exhibitorDTO);

    GiftDTO processSelectExhibitorGift(GiftDTO giftDTO);

    ResponseDTO processUpdateExhibitGiftSeq(ExhibitorDTO exhibitorDTO);

    List<GiftDTO> processSelectGiftList(GiftDTO giftDTO);

    ResponseDTO processDeleteExhibitGift(GiftDTO giftDTO);

    DirectoryDTO processSelectDirectoryInfo(DirectoryDTO info);

    ResponseDTO processDeleteCharge(ChargePersonDTO chargePersonDTO);

    ResponseDTO processDeleteDisplay(DisplayDTO displayDTO);

    ResponseDTO processDeleteOnline(OnlineDTO onlineDTO);

    ResponseDTO processDeleteFile(FileDTO fileDTO);

    ResponseDTO processUpdateExhibitPersonalInfo(ExhibitorDTO exhibitorDTO);

    List<PopupDTO> processSelectPopupList(PopupDTO popupDTO);

    ResponseDTO processDeleteExhibitPass(PassDTO passDTO);

    String getExhibitorEmail(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorPasswordInit(ExhibitorDTO exhibitorDTO);

    void processStatisticsAccessor();

    List<ChargePersonDTO> processSelectChargeEmail(ChargePersonDTO chargePersonDTO);

    List<KibstvDTO> processSelectKibstvYearList(SearchDTO searchDTO);

    void processUpdateOnlineFileNote(String exhibitorSeq, String onlineNote);

    InstaTokenDTO processSelectInstaRefresh();

    ResponseDTO processUpdateInstaRefresh(InstaTokenDTO instaTokenDTO);

    VisitorDTO processSelectPreVisitorCheck(VisitorDTO visitorDTO);

    ResponseDTO processSaveVisitor(VisitorDTO visitorDTO);

    SearchCompanyResponseDTO processSearchCompany(SearchCompanyRequestDTO searchCompanyRequestDTO) ;

    VisitorDTO processSelectVisitorSingle(String seq);

    List<PartnerDTO> processSelectPartnerList(String seq);

    ResponseDTO processDeletePartner(PartnerDTO partnerDTO);

    void insertBusiness(BusinessDTO businessDTO);
}