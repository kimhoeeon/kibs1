package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO extends AbstractPagingRequestVo {
    String condition; //검색조건
    String searchText; //검색단어
    String lang; //언어

    String emergencyYn;
    String progressStep;

    /* 참가자 관리 - 전시업체 목록*/
    String boothType; //부스타입
    String discountYn; //할인여부
    String taxYn; //세금계산서 발행여부
    String prcYn; //참가비 수납여부
    String applyComplt; //신청서 완료여부
    String approvalStatus; //승인여부
    String cancelYn; //참가취소포함여부
    String refundYn; //환불내역존재여부
    String transferYear; //이관년도
    String nowYear; //현재년도

    /* 참가자 관리 - 참관객 목록 */
    String joinYear; //참관년도
    String joinYn; //참석여부
    String visitorGbn; //구분

    /* 참가자 관리 - 회원 목록 */
    String gbn; //구분
    String stat; //상태

    /* 부대행사 - 올해의제품 */
    String awardYear;

    /* 부대행사 - KIBS 참가업체 */
    String kibsYear;

    /* 정보센터 - 게시판 관리 - KIBS TV */
    String category; //카테고리

    /* 정보센터 - 게시판 관리 - FAQ */
    String noticeGbn;

    /* 자료실 - 갤러리 */
    String mngYear;

    /* 참가신청서 관리 - 부스 */
    String dateFrom; // from date
    String dateTo; // to date

    /* 참가신청서 관리 - 유틸리티 */
    String utilityGbn; //유틸리티 항목

    /* 참가신청서 관리 - 온라인전시관 */
    String companyOnlineViewYn;

    /* 정보센터 - 팝업관리 - 팝업등록 */
    String useYn;
}