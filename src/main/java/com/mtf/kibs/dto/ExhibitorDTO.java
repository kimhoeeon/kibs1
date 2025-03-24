package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExhibitorDTO {
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
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
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
    String employeeCnt; //임직원수
    String companyLicenseNum; //사업자등록번호
    String prePartYear; //기참가년도
    String partWantYn; //참가의향
    String memberCompanyYn; //회원사여부
    String snsBlog; //블로그
    String snsFacebook; //페이스북
    String snsInstagram; //인스타그램
    String snsEtc; //기타

    //담당자정보
    List<ChargePersonDTO> chargePersonList; //담당자정보

    //참가분야
    String fieldPart; //참가분야

    //전시정보
    List<DisplayDTO> displayList; //전시정보

    //상세정보
    String companyIntroKo; //회사소개(국문)
    String companyIntroEn; //회사소개(영문)
    String companyPurposeKo; //참가목적(국문)
    String companyPurposeEn; //참가목적(영문)
    String preDisplayItemIntroKo; //전시품소개(국문)
    String preDisplayItemIntroEn; //전시품소개(영문)
    String newItemIntroKo; //신제품소개(국문)
    String newItemIntroEn; //신제품소개(영문)
    String boothVisitReason; //부스방문이유
    String eventPlan; //이벤트진행계획
    String officeRequest; //사무국요청사항

    //온라인전시회정보
    String companyIntroVideo; //회사소개영상
    String companyOnlineViewYn; //온라인전시회 노출여부
    String companyBadge; //기업 뱃지

    //바이어정보
    String exportMeetingYn; //수출상담회참가희망여부

    /* end: 관리자 전시업체 목록 상세 */

    /* begin:step01 */
    String lang; //사이트 언어
    String password; //비밀번호
    String name; //이름
    String position; //직위
    String depart; //부서
    String email; //이메일
    String emailMarketingYn; //이메일 마케팅 수신여부
    String tel; //전화번호
    String phone; //휴대전화
    List<OnlineDTO> onlineExhibitList; //온라인전시회
    List<BuyerDTO> buyerList; //바이어정보
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
    Boolean discountRe1; //재참가할인1(19~22)
    Boolean discountRe2; //재참가할인2(16~18)
    Boolean discountRe3; //재참가할인3(13~15)
    Boolean discountReAll; //재참가할인(All)
    Boolean discountScale1; //규모할인1(40부스이상)
    Boolean discountScale2; //규모할인2(20부스이상)
    Boolean discountScale3; //규모할인3(10부스이상)
    Boolean discountFirst; //첫참가할인
    Boolean discountLeisure; //한국해양레저산업협회할인
    Integer boothPrcSum; //부스신청총액
    Integer discountPrcSum; //할인총액
    Integer prcSum; //소개
    Integer prcVat; //부가세
    Integer prcTotal; //총계
    /* end:step2_1 */

    /* begin:step2_2 */
    String companySignNameKo; //상호간판명(국문)
    String companySignNameEn; //상호간판명(영문)
    /* end:step2_2 */

    /* begin:step2_3 */
    Integer utilityJuganCnt; //주간단상 220v 수량
    Integer utilityJuganFee; //주간단상 220v 비용
    Integer utilityDayCnt; //24시간용 220v 수량
    Integer utilityDayFee; //24시간용 220v 비용
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
    List<PassDTO> passList; //출입증신청
    /* end:step2_4 */

    /* begin:step2_5 */
    List<GiftDTO> giftList; //경품제공신청
    /* end:step2_5 */

    /* begin:step2_6 */
    String webbannerContent; //웹배너내용
    /* end:step2_6 */

    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

    String productOptionSmall;
}