package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@ToString
public class BusinessDTO {

    String seq;
    String ldongAddrMgplDgCd; //법정동주소광역시도코드
    String ldongAddrMgplSgguCd; //법정동주소시군구코드
    String ldongAddrMgplSgguEmdCd; //법정동주소읍면동코드
    String wkplNm; //사업장명
    String bzowrRgstNo; //사업자등록번호
    String wkplRoadNmDtlAddr; //사업장도로명상세주소
    String wkplJnngStcd; //사업장가입상태코드
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

}