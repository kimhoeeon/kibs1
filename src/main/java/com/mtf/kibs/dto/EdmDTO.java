package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EdmDTO {
    Integer rownum; //연번
    String id; //ID
    String hstId; //이력ID
    String email; //이메일
    String name; //이름
    String phone; //연락처
    String type; //타입
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}