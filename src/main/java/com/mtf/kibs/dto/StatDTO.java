package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class StatDTO {
    String eventDate;
    int exhibitorCount;
    int visitorCount;

    String weekPeriod;
    String monthPeriod;

    int boothCount1;
    int boothCount2;
    int boothCount3;

    String statisticDate;
    int totalCount;

    String weekStartDate;

    int fieldCount1;
    int fieldCount2;
    int fieldCount3;
    int fieldCount4;
    int fieldCount5;
    int fieldCount6;
    int fieldCount7;
    int fieldCount8;
    int fieldCount9;
    int fieldCount10;
}