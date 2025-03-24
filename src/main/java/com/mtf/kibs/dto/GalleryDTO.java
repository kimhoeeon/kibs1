package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GalleryDTO extends AbstractPagingRequestVo {
    Integer totalRecords;
    Integer rownum; //연번
    String id; //ID
    String siteGbn; //사이트 분류
    Integer mngYear; //관리년도
    String title; //제목
    String content; //내용
    String writer; //작성자
    String writeDate; //작성일
    Integer viewCnt; //조회수
    String fileIdList; //첨부파일ID목록
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

    List<String> fullFilePathList;
}