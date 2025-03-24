package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDTO {
    String id; //ID
    String title; //제목
    String content; //내용
    String writer; //작성자
    String writeDate; //작성일
    String uploadFile1; //첨부파일1
    String uploadFile2; //첨부파일2
    String uploadFile3; //첨부파일3
    String uploadFile4; //첨부파일4
    String uploadFile5; //첨부파일5
    String uploadImage1; //첨부이미지1
    String uploadImage2; //첨부이미지2
    String uploadImage3; //첨부이미지3
    String uploadImage4; //첨부이미지4
    String uploadImage5; //첨부이미지5
    String sourceUrl; //출처
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}