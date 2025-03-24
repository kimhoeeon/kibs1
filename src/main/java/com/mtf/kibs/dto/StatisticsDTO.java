package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class StatisticsDTO {
    String id; //ID
    String transferYear; //연도
    String gbn; //구분
    String inDttm; //일시
    String inCount; //수
    String customValue; //값
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

    String x;
    String y;

    @Override
    public boolean equals(Object object) {
        StatisticsDTO statisticsDTO = (StatisticsDTO) object;
        return Objects.equals(statisticsDTO.x, this.x);
    }
}