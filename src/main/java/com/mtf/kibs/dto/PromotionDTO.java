package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PromotionDTO {
    String seq; //SEQ
    String id; //ID
    String promotionImage; //홍보이미지
    Integer promotionImageSeq; //홍보이미지순번
    String useYn; //사용여부
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}