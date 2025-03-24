package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDTO {
    String id; //고객ID
    String name; //고객명
    String phone; //연락처
    String refundBank; //보증금 환불 은행
    String refundBankNum; //보증금 환불 계좌번호
    String refundBankName; //보증금 환불 계좌번호 입금자명
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}