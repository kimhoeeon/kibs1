package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AwardDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String awardYear; //수상연도
    String gbn; //분류
    String productPhoto; //제품사진
    String companyNameKo; //업체명(국문)
    String companyNameEn; //업체명(영문)
    String productNameKo; //제품명(국문)
    String productNameEn; //제품명(영문)
    String productIntroKo; //제품설명(국문)
    String productIntroEn; //제품설명(영문)
    String homepage; //홈페이지주소
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}