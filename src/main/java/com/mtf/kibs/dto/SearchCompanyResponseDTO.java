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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class SearchCompanyResponseDTO {
    @XmlElement(name = "header")
    public Header header;

    @XmlElement(name = "body")
    public Body body;

    @Getter
    @XmlRootElement(name = "header")
    public static class Header {

        public String resultCode;
        public String resultMsg;
    }

    @Getter
    @XmlRootElement(name = "body")
    public static class Body {

        public Items items;
        public String numOfRows;
        public String pageNo;
        public String totalCount;

        @Getter
        @XmlRootElement(name = "items")
        public static class Items {

            public List<Item> item;

            @Getter
            @XmlRootElement(name = "item")
            public static class Item {

                public String bzowrRgstNo; //사업자등록번호
                public String dataCrtYm; //자료생성년월
                public String ldongAddrMgplDgCd; //법정동주소광역시도코드
                public String ldongAddrMgplSgguCd; //법정동주소시군구코드
                public String ldongAddrMgplSgguEmdCd; //법정동주소읍면동코드
                public String seq; //식별번호
                public String wkplJnngStcd; //사업장가입상태코드
                public String wkplNm; //사업장명
                public String wkplRoadNmDtlAddr; //사업장도로명상세주소
                public String wkplStylDvcd; //사업장형태구분코드
            }
        }
    }

}