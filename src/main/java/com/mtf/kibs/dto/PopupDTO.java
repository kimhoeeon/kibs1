package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PopupDTO {
    Integer rownum; //연번
    String id; //ID
    String lang; //언어
    String useYn; //사용여부
    String title; //제목
    String publishedDate; //게시일시
    String expirationDate; //만료일시
    String today;
    String widthPixel; //이미지너비
    String leftPixel; //가로여백
    String topPixel; //세로여백
    String align; //정렬
    String writer; //등록자
    String content; //내용
    String linkUrl; //링크URL
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}