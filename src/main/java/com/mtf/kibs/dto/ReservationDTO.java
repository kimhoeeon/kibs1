package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationDTO {
    String id; //예약ID
    String customerId; //고객ID
    String breweryId; //양조장ID
    String breweryName; //양조장명
    String rsvDay; //예약일
    String rsvStrTime; //예약시작시간
    String rsvEndTime; //예약종료시간
    String rsvStatus; //예약상태
    Integer rsvPersonnel; //예약인원
    String hopeRsvTime; //방문희망시간
    String reqTime; //신청일시
    String depositStatus; //보증금 입금 상태
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
    
    String customerName; //고객명
    String customerPhone; //고객핸드폰

    String programName; //프로그램명
}