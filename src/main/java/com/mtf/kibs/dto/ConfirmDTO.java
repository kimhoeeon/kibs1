package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConfirmDTO {
    Integer rownum; //순번
    String reservationId; //예약ID
    String customerId; //고객명
    String breweryId; //양조장ID
    String breweryName; //양조장명
    String rsvDay; //예약일
    String name; //고객명
    String phone; //연락처
    String rsvStatus; //예약상태
    String depositStatus; //보증금입금상태
    Integer rsvPersonnel; //방문인원
    String hopeRsvTime; //방문희망시간
    String reqTime; //예약일시
    String refundBank; //보증금 환불 은행
    String refundBankNum; //보증금 환불 계좌번호
    String refundBankName; //보증금 환불 계좌번호 입금자명
    String rsvBank; //예약금입금은행
    String rsvBankNum; //예약금입금계좌번호
    String rsvBankName; //예약금입금계좌주
    String breweryNonRefundDay; //환불불가일
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}