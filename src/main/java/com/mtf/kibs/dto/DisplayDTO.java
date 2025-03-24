package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DisplayDTO {
    String seq; //SEQ
    String id; //exhibitor seq
    String userId; //User ID
    String transferYear; //년도
    String displayItem; //전시품목
    String displayBrand; //전시품목브랜드
    Integer displayBoatCnt; //전시보트수
    String displayItemIntroKo;
    String displayItemIntroEn;
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}