package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MainOnlineDTO extends AbstractPagingRequestVo {
    String seq; //seq
    String id; //ID
    String transferYear; //연도
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
    List<String> companyBadgeList; //기업뱃지
    String companyBadge;
    String logoFilePath; //로고파일경로
    String promotionImageFilePath; //홍보이미지파일경로

    /*검색조건*/
    String productOptionBig; //제품분류(대)
    Integer totalRecords;
    String searchText; //검색어

}