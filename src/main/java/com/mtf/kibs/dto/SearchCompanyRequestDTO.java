package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchCompanyRequestDTO {
    String ldongAddrMgplDgCd; //법정동주소광역시도코드
    String ldongAddrMgplSgguCd; //법정동주소시군구코드
    String ldongAddrMgplSgguEmdCd; //법정동주소읍면동코드
    String wkplNm; //사업장명
    String bzowrRgstNo; //사업자등록번호
}