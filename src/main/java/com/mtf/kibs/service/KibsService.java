package com.mtf.kibs.service;

import com.mtf.kibs.dto.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface KibsService {

    Integer checkDuplicateId(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processLoginExhibit(ExhibitorDTO exhibitorDTO);

    void logoutCheck(HttpSession session);

    ResponseDTO processInsertExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processUpdateExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    void processUpdateProductNewFileNote(String exhibitorSeq, String productNote);

    void processUpdateOnlineNewFileNote(String exhibitorSeq, String onlineNote);

    ExhibitorNewDTO processSelectExhibitorNewSingle(ExhibitorNewDTO exhibitorNewDTO);

    List<ChargeNewDTO> processSelectChargeNewList(ChargeNewDTO chargeNewDTO);

    List<ProductNewDTO> processSelectProductNewList(ProductNewDTO productNewDTO);

    List<OnlineNewDTO> processSelectOnlineNewList(OnlineNewDTO onlineNewDTO);

    List<BuyerNewDTO> processSelectBuyerNewList(BuyerNewDTO buyerNewReq);

    ResponseDTO processUpdateExhibitorNewBooth(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processUpdateExhibitorNewCompanySign(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processUpdateExhibitorNewUtility(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processInsertPassNew(ExhibitorNewDTO exhibitorNewDTO);

    List<PassNewDTO> processSelectPassNewList(PassNewDTO passNewDTO);

    String processSearchExhibitorNewSeq(ExhibitorNewDTO exhibitorNewDTO);

    List<GiftNewDTO> processSelectGiftNewList(GiftNewDTO giftNewDTO);

    ResponseDTO processInsertDirectory(DirectoryDTO directoryDTO);

    ResponseDTO processUpdateExhibitorNewApprovalStatus(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processInsertGiftNew(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processLoginExhibitorNew(ExhibitorNewDTO exhibitorNewDTO);

    ExhibitorNewDTO processSelectExhibitorNewPrc(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processUpdateExhibitorNewInfo(ExhibitorNewDTO exhibitorNewDTO);

    String getExhibitorNewEmail(ExhibitorNewDTO exhibitorNewDTO);

    List<String> preGetExhibitorEmail(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitorNewPasswordInit(ExhibitorNewDTO exhibitorNewDTO);

    ResponseDTO processDeletePassNew(PassNewDTO passNewDTO);

    ResponseDTO processDeleteChargeNew(ChargeNewDTO chargeNewDTO);

    ResponseDTO processDeleteProductNew(ProductNewDTO productNewDTO);

    ResponseDTO processDeleteOnlineNew(OnlineNewDTO onlineNewDTO);

    ResponseDTO processDeleteFile(FileDTO fileDTO);

    ExhibitorDTO processSelectPreExhibitorSingle(ExhibitorDTO exhibitorDTO);


    ResponseDTO processUpdateExhibitBooth(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateCompanySign(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitUtility(ExhibitorDTO exhibitorDTO);

    ResponseDTO processInsertExhibitPass(ExhibitorDTO exhibitorDTO);

    ResponseDTO processInsertExhibitGift(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateWebbanner(ExhibitorDTO exhibitorDTO);

    ResponseDTO processUpdateExhibitApprovalStatus(ExhibitorDTO exhibitorDTO);

    List<MainOnlineDTO> processSelectOnlineList(String transferYear);

    List<MainOnlineDTO> processSelectEnOnlineListPaging(MainOnlineDTO mainOnlineDTO);

    List<MainOnlineDTO> processSelectOnlineListPaging(MainOnlineDTO mainOnlineDTO);

    ExhibitorDTO processSelectOnlineExhibitorInfo(String seq);

    ExhibitorNewDTO processSelectOnlineExhibitorNewInfo(String seq);

    List<OnlineDTO> processSelectOnlineInfoList(String seq);

    List<ProductNewDTO> processSelectProductNewInfoList(String seq);

    List<OnlineNewDTO> processSelectOnlineNewInfoList(String seq);

    List<FileDTO> processSelectFileList(String seq);

    OnlineDTO processSelectOnlineInfo(String seq);

    ProductNewDTO processSelectProductNewInfo(String seq);

    OnlineNewDTO processSelectOnlineNewInfo(String seq);

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

    List<DataroomDTO> processSelectGalleryList(SearchDTO searchDTO);

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

    BuyerNewDTO processSelectBuyerNewSingle(BuyerNewDTO buyerNewDTO);

    ResponseDTO processDeleteBuyerNew(BuyerNewDTO buyerNewDTO);

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

    List<PopupDTO> processSelectPopupList(PopupDTO popupDTO);

    PopupDTO processSelectPopupSingle(PopupDTO popupDTO);

    void processStatisticsAccessor();

    List<ChargePersonDTO> processSelectChargeEmail(ChargePersonDTO chargePersonDTO);

    List<KibstvDTO> processSelectKibstvYearList(SearchDTO searchDTO);

    InstaTokenDTO processSelectInstaRefresh();

    ResponseDTO processUpdateInstaRefresh(InstaTokenDTO instaTokenDTO);

    VisitorDTO processSelectPreVisitorCheck(VisitorDTO visitorDTO);

    ResponseDTO processSaveVisitor(VisitorDTO visitorDTO);

    SearchCompanyResponseDTO processSearchCompany(SearchCompanyRequestDTO searchCompanyRequestDTO);

    VisitorDTO processSelectVisitorSingle(String seq);

    List<PartnerDTO> processSelectPartnerList(String seq);

    ResponseDTO processDeletePartner(PartnerDTO partnerDTO);

    void insertBusiness(BusinessDTO businessDTO);

    List<ContestDTO> processSelectContestList(SearchDTO searchDTO);

    ResponseDTO processSaveProductNew(ExhibitorNewDTO exhibitorNewDTO);

    String getExhibitorNewRepEmail(ExhibitorNewDTO exhibitorNewDTO);
}