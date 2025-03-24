package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnExhibitorListDTO extends AbstractPagingRequestVo {
    Integer totalRecords;
    Integer rownum; //연번
    String seq; //seq
    String lang; //언어
    String transferYear; //연도
    String company; //회사명
    String telephone; //연락처
    String homepage; //홈페이지
    String category; //카테고리
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}