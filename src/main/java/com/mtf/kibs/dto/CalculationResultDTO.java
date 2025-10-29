package com.mtf.kibs.dto;

import lombok.Data;

/**
 * CalculationService에서 계산된 모든 결과값을 담는 DTO
 * 이 DTO를 JSON으로 프론트엔드에 반환하거나, Service 레이어에서 DB DTO에 매핑합니다.
 */
@Data
public class CalculationResultDTO {

    // --- 공통 계산 항목 ---
    private int boothPrcSum;          // 부스 총액 (등록비 포함)
    private int basicDiscountSum;     // 기본 할인 총액
    private int specialDiscountTotal; // 특별 할인 총액
    private int developmentFund;      // 발전 기금

    // --- 1. 전시부스 계산서 (Booth Invoice) ---
    // (부스비 + 발전기금) - (기본할인 + 특별할인)
    private int boothSubtotal;        // 부스 소계
    private int boothVat;             // 부스 부가세
    private int boothTotal;           // 부스 합계

    // --- 2. 유틸리티 계산서 (Utility Invoice) ---
    private int utilityPrcSum;        // 유틸리티 소계 (총액)
    private int utilityVat;           // 유틸리티 부가세
    private int utilityTotal;         // 유틸리티 합계

    // --- 3. 최종 총계 (Total) ---
    // (prcSum = boothSubtotal + utilitySubtotal)
    private int prcSum;               // 최종 소계 (DB 저장용)
    private int prcVat;               // 최종 부가세 (DB 저장용)
    private int prcTotal;             // 최종 합계 (DB 저장용)
    private int balance;              // 최종 잔액 (DB 저장용)
}