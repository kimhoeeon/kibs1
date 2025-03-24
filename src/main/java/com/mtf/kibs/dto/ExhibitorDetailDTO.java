package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExhibitorDetailDTO {
    private String transferYear; //연도
    
    /* 참가업체정보 */
    private String participationStatus; //참가상태
    private String approvalStatus; //승인여부
    private String boothNum; //부스번호
    private String id; //아이디
    private String initRegiDttm; //최초 등록 일시
    private String finalRegiDttm; //최종 변경 일시
    private String prcYn; //입금현황
    private String bpNum; //BP번호
    private String contactWriter; //컨택내역-작성자
    private String contactDate; //컨택내역-날짜
    private String contactContent; //컨택내역-내용
    private String referenceWriter; //참고사항-작성자
    private String referenceDate; //참고사항-날짜
    private String referenceContent; //참고사항-내용
    private String companyNameKo; //회사명(국문)
    private String companyNameEn; //회사명(영문)
    private String companyAddress; //회사주소
    private String companyAddressDetail; //회사상세주소
    private String factoryAddress; //공장주소
    private String factoryAddressDetail; //공장상세주소
    private String companyCeo; //대표자명
    private String companyTel; //전화
    private String companyHomepage; //홈페이지
    private String companyFax; //fax
    private String industryPart; //산업분류
    private String industryPartEtc; //산업분류기타
    private String employeeCnt; //임직원수
    private String companyLicense; //사업자등록증
    private String companyLicenseNum; //사업자등록번호
    private String prePartYear; //기참가년도
    private String memberCompanyYn; //회원사여부

    /* SNS */
    private String snsBlog; //블로그
    private String snsFacebook; //페이스북
    private String snsInstagram; //인스타그램
    private String snsEtc; //기타

    /* 담당자정보 */
    //private List<ChargePersonDTO> chargePersonList;
    private String chargePersonName; //담당자명
    private String chargePersonPosition; //직위
    private String chargePersonDepart; //부서
    private String chargePersonTel; //전화번호
    private String chargePersonPhone; //휴대전화
    private String chargePersonEmail; //이메일

    /* 참가분야 */
    private String fieldPart; //참가분야

    /* 전시품목 */
    //private List<DisplayDTO> displayList;
    private String displayItem; //전시품목
    private String displayBrand; //전시브랜드
    private String displayBoatCnt; //전시보트수
    private String displayItemIntroKo; //전시품소개(국)
    private String displayItemIntroEn; //전시품소개(영)

    /* 참가업체정보 */
    private String companyIntroKo; //회사소개(국문)
    private String companyIntroEn; //회사소개(영문)
    private String companyPurposeKo; //참가목적(국문)
    private String companyPurposeEn; //참가목적(영문)
    private String newItemIntroKo; //신제품(국문)
    private String newItemIntroEn; //신제품(영문)
    private String boothVisitReason; //부스방문이유
    private String eventPlan; //행사이벤트진행계획
    private String officeRequest; //사무국요청사항
    private String companyIntroVideo; //회사소개영상
    private String companyOnlineViewYn; //온라인전시회노출여부

    /* 기업뱃지 */
    private String companyBadge; //기업뱃지

    /* 제품 */
    //private List<OnlineDTO> onlineList;
    private String productNameKo; //제품명(국문)
    private String productNameEn; //제품명(영문)
    private String productOptionBig; //제품분류(대)
    private String productOptionSmall; //제품분류(소)
    private String productIntroKo; //제품설명(국문)
    private String productIntroEn; //제품설명(영문)
    private String productIntroVideo; //제품영상
    private String productWidth; //전장
    private String productHorsePower; //마력

    /* 참가업체정보 */
    private String exportMeetingYn; //수출상담회 참가희망여부

    /* 전시부스 신청 */
    private Integer registrationCnt; //등록수량
    private Integer standAloneBoothCnt; //독립부스 신청수량
    private Integer assemblyBoothCnt; //조립부스 신청수량
    private Integer onlineBoothCnt; //온라인부스 신청수량
    private Boolean discountEarly1; //1차조기신청
    private Boolean discountEarly2; //2차조기신청
    private Boolean discountReAll; //재참가할인(All)
    private Boolean discountScale1; //규모할인1(40부스이상)
    private Boolean discountScale2; //규모할인2(20부스이상)
    private Boolean discountScale3; //규모할인3(10부스이상)
    private Boolean discountFirst; //첫참가할인
    private Boolean discountLeisure; //한국해양레저산업협회할인

    /* 상호간판 */
    private String companySignNameKo; //상호간판명(국문)
    private String companySignNameEn; //상호간판명(영문)

    /* 유틸리티 신청 */
    private Integer utilityJuganCnt; //주간단상 220v 수량
    private Integer utilityDayCnt; //24시간용 220v 수량
    private Integer utilityCompressedAirCnt; //압축공기 기본형 수량
    private Integer utilityWaterBasicCnt; //급배수 기본형 수량
    private Integer utilityTelephoneCnt; //전화(국내용) 수량
    private Integer utilityInternetCnt; //인터넷 수량
    private Integer utilityPytexNewCnt; //파이텍스 신품 수량
    private Integer utilityPytexReCnt; //파이텍스 재사용품 수량
    private Integer utilityBarcodeCnt; //바코드 리더기 수량

    /* 출입증 신청 */
    //private List<PassDTO> passList;
    private String passName; //출입증 성명(국문)
    private String passLastName; //출입증 성(영문)
    private String passFirstName; //출입증 이름(영문)
    private String passPositionKo; //직책(국문)
    private String passPositionEn; //직책(영문)
    private String passNote; //출입증 비고

    /* 바이어 등록 정보 */
    //private List<BuyerDTO> buyerList;
    private String buyerCompanyName; //회사명
    private String buyerCompanyCountry; //국가
    private String buyerCompanyLocation; //소재지
    private String buyerCompanyHomepage; //홈페이지
    private String buyerCompanyDepart; //부서
    private String buyerCompanyPosition; //직책
    private String buyerCompanyEmail; //이메일
    private String buyerCompanyTel; //전화번호
    private String buyerCompanyPhone; //휴대전화
    private String buyerCompanyFax; //팩스
    private String buyerCompanyItem; //취급품목
    private String buyerCompanyInviteReason; //초청사유
    private String buyerCompanyProgressYn; //계약진행여부
    private String buyerCompanyHope; //희망사항

    /* 경품제공 신청 */
    //private List<GiftDTO> giftList;
    private String giftGbn; //경품구분
    private String giftCnt; //경품수량
    private String giftClassify; //분류
    private String giftName; //품목명
    private String giftContent; //경품설명
    private String giftPhoto; //경품사진
    private String giftCompanyLogo; //회사로고
    private String giftPrice; //소비자가
    private String giftSponsorPrice; //협찬가
    private String giftNote; //경품비고

}