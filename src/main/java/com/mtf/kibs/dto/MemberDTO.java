package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String gbn; //구분
    String stat; //상태
    String id; //아이디
    String nameKo; //이름/회사명(국)
    String nameEn; //이름/회사명(영)
    String email; //이메일
    String initRegiDttm; //최초 등록 일시
    String finalRegiDttm; //최종 변경 일시
}