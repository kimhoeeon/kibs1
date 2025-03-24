package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class VisitorDetailDTO {
    Integer rownum; //순번
    String seq; //순번
    String joinYear; //참관년도
    String joinYn; //참석여부
    String visitorGbn; //구분
    String partGbn; //참관구분
    String name; //이름
    String tel; //전화번호
    String phone; //휴대전화
    String email; //이메일
    String domain; //도메인
    String companyName; //회사명
    String companyAddress; //회사주소
    String companyAddressDetail; //회사상세주소
    String partnerYn; //동반자 여부
    String sex; //성별
    String regionSi; //지역(시)
    String regionGu; //지역(구)
    String ageGroup; //연령대
    String observationGbn; //참관여부
    String visitPurpose; //방문목적
    String interestItem; //관심품목
    String recognizePath; //인지경로
    String preObservationGbn; //지난전시회참관여부
    String preObservationGbnEn; //지난전시회참관여부(영)
    String partnerName;
    String partnerAge;
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}