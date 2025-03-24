package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OnlineMngDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String transferYear; //관리년도
    String companyOnlineViewYn; //노출여부
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
    String fieldPart; //참가분야
    String productNameKo; //제품명(국문)
    String initRegiDttm; //최초 등록 일시
    String finalRegiDttm; //최종 변경 일시
}