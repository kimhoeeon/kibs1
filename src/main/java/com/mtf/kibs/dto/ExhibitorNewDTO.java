package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExhibitorNewDTO {
    Integer rownum; //순번
    String seq; //SEQ

    /* begin: 관리자 전시업체 목록 검색조건 */
    String boothType; //부스타입
    String companyName; //회사명(국문+영문)
    String chargePersonContact; //담당자연락처 (핸드폰+이메일)
    String deposit; //선금
    String balance; //잔액
    String prcYn; //입금여부
    String taxYn; //세금계산서 발행여부
    String boothNum; //부스번호
    String discountYn; //할인여부
    String cancelYn; //취소여부
    String refundYn; //환불여부
    /* end: 관리자 전시업체 목록 검색조건 */

    /* begin: 관리자 전시업체 목록 상세 */
    //승인정보
    String participationStatus; //참가상태
    String applyComplt; //신청폼작성여부
    String approvalStatus; //승인구분
    String approvalStatusCancelReason; //취소사유
    String id; //id
    String salt; //암호화난수
    String password; //비밀번호
    String passwordYn; //비밀번호 변경여부
    String transferYear; //이관년도

    //관리정보
    String bpNum; //BP번호
    String contactWriter; //컨택내역-작성자
    String contactDate; //컨택내역-날짜
    String contactContent; //컨택내역-내용
    String referenceWriter; //참고사항-작성자
    String referenceDate; //참고사항-날짜
    String referenceContent; //참고사항-내용

    //참가업체정보
    String companyLicenseNum; //사업자등록번호
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
    String companyCountry;
    String companyState;
    String companyCity;
    String companyZipcode;
    String companyAddress; //주소
    String companyAddressDetail; //상세주소
    String factoryAddress; //공장주소
    String factoryAddressDetail; //공장상세주소
    String companyCeo; //대표자
    String companyTel; //전화
    String companyHomepage; //홈페이지
    String companyFax; //Fax
    String industryPart; //산업분류
    String industryPartEtc; //산업분류기타
    int employeeCnt; //임직원수
    String prePartYear; //기참가년도
    String memberCompanyYn; //회원사여부

    //대표 담당자 정보
    String name; //이름
    String position; //직위
    String depart; //부서
    String tel; //전화번호
    String phone; //휴대전화
    String email; //이메일
    String emailMarketingYn; //이메일 마케팅 수신여부

    //부담당자정보
    List<ChargeNewDTO> chargePersonList; //부담당자정보

    String companyIntroVideo; //회사소개영상
    String companyIntroKo; //회사소개(국문)
    String companyIntroEn; //회사소개(영문)
    String companyPurposeKo; //참가목적(국문)
    String companyPurposeEn; //참가목적(영문)
    String newItemIntroKo; //신제품소개(국문)
    String newItemIntroEn; //신제품소개(영문)
    String promotionPlan; //이벤트진행계획
    String fieldPart; //참가분야
    String fieldParticipatory;
    String fieldParticipatory1;
    String fieldParticipatory2;
    String fieldParticipatory3;

    // 요트/보트 출품 정보
    String boatEntryYn; // 요트/보트 출품 여부
    List<ProductNewDTO> productList; //전시품정보

    //온라인 전시관 정보
    List<OnlineNewDTO> onlineList; //온라인 전시관 정보

    //바이어정보
    String exportMeetingYn; //수출상담회참가희망여부

    /* end: 관리자 전시업체 목록 상세 */

    /* begin:step01 */
    String lang; //사이트 언어
    List<OnlineNewDTO> onlineExhibitList; //온라인전시회
    List<BuyerNewDTO> buyerList; //바이어정보
    /* end:step01 */

    /* begin:step2_1 */
    String discountType; //할인구분
    Integer registrationCnt; //등록수량
    Integer registrationFee; //등록비
    Integer standAloneBoothCnt; //독립부스 신청수량
    Integer standAloneBoothFee; //독립부스 신청비용
    Integer assemblyBoothCnt; //조립부스 신청수량
    Integer assemblyBoothFee; //조립부스 신청비용
    Integer onlineBoothCnt; //온라인부스 신청수량
    Integer onlineBoothFee; //온라인부스 신청비용
    Boolean discountEarly1; //1차조기신청
    Boolean discountEarly2; //2차조기신청
    Boolean discountFirstUnder10; //첫참가할인(10부스미만)
    Boolean discountFirstOver10; //첫참가할인(10부스이상)
    Boolean discountFirst; //첫참가할인
    Boolean discountRe; //재참가할인
    Boolean discountScale1; //규모할인1(10부스이상)
    Boolean discountScale2; //규모할인2(20부스이상)
    Boolean discountScale3; //규모할인3(30부스이상)
    Boolean discountScale4; //규모할인4(40부스이상)
    Boolean discountScale5; //규모할인5(50부스이상)
    Boolean discountScale6; //규모할인6(100부스이상)
    Boolean discountLeisure; //한국해양레저산업협회할인
    boolean discountSpecial1Yn;
    boolean discountSpecial2Yn;
    String discountSpecial2Reason;
    int discountSpecial2Amount;
    boolean discountSpecial3Yn;
    String discountSpecial3Reason;
    int discountSpecial3Amount;
    String discountSpecial1Note;
    String discountSpecial2Note;
    String discountSpecial3Note;
    Integer boothPrcSum; //부스신청총액
    Integer discountPrcSum; //할인총액
    Integer prcSum; //소개
    Integer prcVat; //부가세
    Integer prcTotal; //총계
    /* end:step2_1 */

    /* begin:step2_10 */
    String maritimeExhibitionYn;
    boolean maritimeExhibitionSea;
    boolean maritimeExhibitionLand;
    /* end:step2_10 */

    /* begin:step2_2 */
    String companySignNameKo; //상호간판명(국문)
    String companySignNameEn; //상호간판명(영문)
    /* end:step2_2 */

    /* begin:step2_3 */
    Integer utilityJuganCnt; //주간단상 220v 수량
    Integer utilityJuganFee; //주간단상 220v 비용
    Integer utilityDayCnt; //24시간용 220v 수량
    Integer utilityDayFee; //24시간용 220v 비용
    Integer utilityWorkCnt; //작업전기 수량
    Integer utilityWorkFee; //작업전기 비용
    Integer utilityCompressedAirCnt; //압축공기 기본형 수량
    Integer utilityCompressedAirFee; //압축공기 기본형 금액
    Integer utilityWaterBasicCnt; //급배수 기본형 수량
    Integer utilityWaterBasicFee; //급배수 기본형 금액
    Integer utilityTelephoneCnt; //전화(국내용) 수량
    Integer utilityTelephoneFee; //전화(국내용) 금액
    Integer utilityInternetCnt; //인터넷 수량
    Integer utilityInternetFee; //인터넷 금액
    Integer utilityPytexNewCnt; //파이텍스 신품 수량
    Integer utilityPytexNewFee; //파이텍스 신품 금액
    Integer utilityPytexReCnt; //파이텍스 재사용품 수량
    Integer utilityPytexReFee; //파이텍스 재사용품 금액
    Integer utilityBarcodeCnt; //바코드 리더기 수량
    Integer utilityBarcodeFee; //바코드 리더기 금액
    Integer utilityPrcSum; //유틸리티신청 총액
    /* end:step2_3 */

    /* begin:step2_4 */
    List<PassNewDTO> passList; //출입증신청
    /* end:step2_4 */

    /* begin:step2_5 */
    List<GiftNewDTO> giftList; //경품제공신청
    String giftApplyYn; // 경품 신청 여부
    /* end:step2_5 */

    /* begin:step2_6 */
    String webbannerContent; //웹배너내용
    /* end:step2_6 */

    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

    String partWantYn; //참가의향
    String companyBadge; //기업 뱃지
    String companyOnlineViewYn; //온라인전시회 노출여부
    String productOptionSmall;
 
    String invoiceYn; //인보이스존재여부
}