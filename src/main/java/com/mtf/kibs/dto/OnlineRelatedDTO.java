package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OnlineRelatedDTO extends AbstractPagingRequestVo {
    /* Online table */
    String seq; //SEQ
    String id; //ID
    String productNameKo; //제품명(국문)
    String productNameEn; //제품명(영문)
    String productOptionBig; //제품분류(대)
    String productOptionSmall; //제품분류(소)
    String note; //비고

    /* Exhibitor table */
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)

    String productWidthMin;
    String productWidthMax;
    String productHorsePowerMin;
    String productHorsePowerMax;
    String transferYear;

    String productFullFilePath;
    String logoFullFilePath;

    String condition;
    String searchText;

    Integer totalRecords;
}
