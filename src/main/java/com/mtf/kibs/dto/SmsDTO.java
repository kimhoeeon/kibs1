package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsDTO {
    String id; //ID
    String receiver; //수신번호
    String sender; //발신번호
    String message; //문자내용
    String resultMsg; //발신결과
    String sendDttm; //전송일시
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}