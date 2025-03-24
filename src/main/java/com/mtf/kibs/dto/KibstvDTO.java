package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KibstvDTO extends AbstractPagingRequestVo {
    Integer totalRecords;
    Integer rownum; //연번
    String id; //ID
    String lang; //언어
    String siteGbn; //사이트분류
    String category; //카테고리
    String title; //제목
    String content; //내용
    String writer; //작성자
    String writeDate; //작성일
    Integer viewCnt; //조회수
    String youtubeUrl; //유튜브url
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}