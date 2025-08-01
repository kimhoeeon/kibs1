package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ContestDTO {
    Integer totalRecords;
    Integer rownum; //연번
    String id; //ID
    Integer gbn; //회차
    String title; //제목
    String writer; //작성자
    String writeDate; //작성일
    String fileIdList;
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

    List<String> fullFilePathList;
}