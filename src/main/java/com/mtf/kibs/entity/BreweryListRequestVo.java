package com.mtf.kibs.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("serial")
public class BreweryListRequestVo extends AbstractPagingRequestVo {
    private String id;
    private String addrRegionCode;
}
