package com.mtf.kibs.service;

import com.mtf.kibs.dto.CalculationInputDTO;
import com.mtf.kibs.dto.CalculationResultDTO;
import org.springframework.stereotype.Service;

/**
 * 모든 금액 계산 로직을 중앙에서 처리하는 서비스
 * (Single Source of Truth)
 */
@Service
public class CalculationService {

    // (참고) 실제로는 이 단가들을 CommConstants 또는 DB에서 관리해야 합니다.
    private static final int REGISTRATION_FEE = 100000;
    private static final int STAND_ALONE_BOOTH_FEE = 1800000;
    private static final int ASSEMBLY_BOOTH_FEE = 2100000;
    private static final int ONLINE_BOOTH_FEE = 1000000; // 온라인 부스비 (필요시 수정)

    // --- 기본 할인액 (CommConstants 또는 DB 값 사용 권장) ---
    private static final int DISCOUNT_EARLY_1_AMOUNT = 300000;
    private static final int DISCOUNT_EARLY_2_AMOUNT = 200000;
    private static final int DISCOUNT_SCALE_1_AMOUNT = 400000;
    private static final int DISCOUNT_SCALE_2_AMOUNT = 650000;
    private static final int DISCOUNT_SCALE_3_AMOUNT = 750000;
    private static final int DISCOUNT_SCALE_4_AMOUNT = 800000;
    private static final int DISCOUNT_SCALE_5_AMOUNT = 850000;
    private static final int DISCOUNT_SCALE_6_AMOUNT = 900000;
    private static final int DISCOUNT_RE_AMOUNT = 200000;
    private static final int DISCOUNT_FIRST_UNDER_10_AMOUNT = 500000;
    private static final int DISCOUNT_FIRST_OVER_10_AMOUNT = 300000;
    private static final int DISCOUNT_LEISURE_AMOUNT = 200000;

    /**
     * 기본 계산 진입점 (연도가 없을 경우 무조건 최신 2027년으로 계산)
     */
    public CalculationResultDTO calculateTotals(CalculationInputDTO input) {
        return calculate2027Invoice(input);
    }

    /**
     * 연도별 계산 진입점 (관리자 과거 데이터 조회용)
     */
    public CalculationResultDTO calculateTotals(CalculationInputDTO input, String year) {
        if ("2026".equals(year)) {
            return calculate2026Invoice(input);
        } else {
            return calculate2027Invoice(input);
        }
    }

    /**
     * 2027 모든 금액을 계산하는 중앙 메서드
     * @param input CalculationInputDTO (계산에 필요한 모든 입력값)
     * @return CalculationResultDTO (계산된 모든 결과값)
     */
    public CalculationResultDTO calculate2027Invoice(CalculationInputDTO input) {
        CalculationResultDTO result = new CalculationResultDTO();

        // --- 1. 부스 총액 계산 (등록비 포함) ---
        int boothPrcSum = REGISTRATION_FEE
                + (input.getStandAloneBoothCnt() * STAND_ALONE_BOOTH_FEE)
                + (input.getAssemblyBoothCnt() * ASSEMBLY_BOOTH_FEE)
                /*+ (input.getOnlineBoothCnt() * ONLINE_BOOTH_FEE)*/;
        result.setBoothPrcSum(boothPrcSum);

        // --- 2. 기본 할인 총액 계산 ---
        int basicDiscountSum = 0;
        int physicalBooths = input.getStandAloneBoothCnt() + input.getAssemblyBoothCnt();

        // --- 조기 신청 할인 (중복 가능) ---
        if (input.isDiscountEarly1()) basicDiscountSum += physicalBooths * DISCOUNT_EARLY_1_AMOUNT;
        if (input.isDiscountEarly2()) basicDiscountSum += physicalBooths * DISCOUNT_EARLY_2_AMOUNT;

        // --- 첫 참가 / 재참가 할인 (택 1) ---
        int participationDiscount = 0;
        if (input.isDiscountFirstUnder10() && physicalBooths < 10) {
            participationDiscount = physicalBooths * DISCOUNT_FIRST_UNDER_10_AMOUNT;
        } else if (input.isDiscountFirstOver10() && physicalBooths >= 10) {
            participationDiscount = physicalBooths * DISCOUNT_FIRST_OVER_10_AMOUNT;
        } else if (input.isDiscountRe()) { // 첫 참가가 아닐 때만 재참가 적용
            participationDiscount = physicalBooths * DISCOUNT_RE_AMOUNT;
        }
        basicDiscountSum += participationDiscount; // 첫 참가 또는 재참가 중 하나만 더함

        // --- 규모 할인 (택 1, 위와 중복 가능) ---
        int scaleDiscount = 0;
        // 가장 큰 구간부터 확인하여 하나만 적용
        if (physicalBooths >= 100 && input.isDiscountScale6()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_6_AMOUNT;
        } else if (physicalBooths >= 50 && input.isDiscountScale5()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_5_AMOUNT;
        } else if (physicalBooths >= 40 && input.isDiscountScale4()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_4_AMOUNT;
        } else if (physicalBooths >= 30 && input.isDiscountScale3()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_3_AMOUNT;
        } else if (physicalBooths >= 20 && input.isDiscountScale2()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_2_AMOUNT;
        } else if (physicalBooths >= 10 && input.isDiscountScale1()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_1_AMOUNT;
        }
        basicDiscountSum += scaleDiscount; // 규모 할인 더함 (첫/재참가와 중복됨)

        // --- 협회 할인 (중복 가능) ---
        if (input.isDiscountLeisure()) basicDiscountSum += physicalBooths * DISCOUNT_LEISURE_AMOUNT;
        result.setBasicDiscountSum(basicDiscountSum);

        // --- 3. 특별 할인 총액 계산 ---
        int specialDiscountTotal = 0;
        // 특별 할인 기준액 = 부스비 - 기본할인액
        int baseAmountForSpecial = boothPrcSum - basicDiscountSum;
        if (baseAmountForSpecial < 0) baseAmountForSpecial = 0; // 음수 방지

        if (input.isDiscountSpecial1Yn()) {
            specialDiscountTotal += Math.floor(baseAmountForSpecial * 0.5); // 50% 할인
        }
        if (input.isDiscountSpecial2Yn()) {
            specialDiscountTotal += input.getDiscountSpecial2Amount();
        }
        if (input.isDiscountSpecial3Yn()) {
            specialDiscountTotal += input.getDiscountSpecial3Amount();
        }
        result.setSpecialDiscountTotal(specialDiscountTotal);

        // --- 4. 발전 기금 계산 (요청하신 최종 로직) ---
        int developmentFund = 0;
        if ("Y".equals(input.getMemberCompanyYn()) || input.isDiscountLeisure()) {
            // 기준액 = (부스비 총액 - (기본 할인 + 특별 할인))
            int baseAmountForFund = boothPrcSum - (basicDiscountSum + specialDiscountTotal);
            if (baseAmountForFund < 0) baseAmountForFund = 0; // 음수 방지
            developmentFund = (int) Math.floor(baseAmountForFund * 0.1); // 10%
        }
        result.setDevelopmentFund(developmentFund);

        // --- 5. 최종 금액 계산 (분리 및 합산) ---

        // 5-1. 유틸리티 계산서
        int utilSubtotal = input.getUtilityPrcSum();
        int utilVat = (int) Math.floor(utilSubtotal * 0.1);
        int utilTotal = utilSubtotal + utilVat;

        result.setUtilityPrcSum(utilSubtotal);
        result.setUtilityVat(utilVat);
        result.setUtilityTotal(utilTotal);

        // 5-2. 부스 계산서
        // (부스비 총액) - (기본 할인 + 특별 할인)
        int boothSubtotal = (boothPrcSum) - (basicDiscountSum + specialDiscountTotal);
        int boothVat = (int) Math.floor(boothSubtotal * 0.1);
        int boothTotal = boothSubtotal + boothVat;

        result.setBoothSubtotal(boothSubtotal);
        result.setBoothVat(boothVat);
        result.setBoothTotal(boothTotal);

        // 5-3. 최종 총계 (DB 저장용)
        // (부가세 오차 방지를 위해, 합산 후 다시 계산)
        int prcSum = boothSubtotal + utilSubtotal;
        int prcVat = (int) Math.floor(prcSum * 0.1); // ★★★ 부가세는 합계(prcSum) 기준으로 계산
        int prcTotal = prcSum + prcVat;
        int balance = prcTotal - input.getDeposit();

        // (JSP 표기 오차 보정)
        // 만약 개별 부가세의 합이 전체 부가세와 다르면, 차액을 부스 부가세에 더한다.
        int vatGap = prcVat - (boothVat + utilVat);
        if (vatGap != 0) {
            boothVat += vatGap; // 부스 부가세에 차액 보정
            boothTotal += vatGap; // 부스 합계에도 차액 보정
            result.setBoothVat(boothVat);
            result.setBoothTotal(boothTotal);
        }

        result.setPrcSum(prcSum);
        result.setPrcVat(prcVat);
        result.setPrcTotal(prcTotal);
        result.setBalance(balance);

        // 디버깅용 로그 출력 (실제 운영 시 제거 권장)
        /*System.out.println("--- Calculation Result ---");
        System.out.println("Booth Prc Sum: " + boothPrcSum);
        System.out.println("Utility Prc Sum Input: " + input.getUtilityPrcSum());
        System.out.println("Basic Discount Sum: " + basicDiscountSum);
        System.out.println("Special Discount Total: " + specialDiscountTotal);
        System.out.println("Development Fund: " + developmentFund);
        System.out.println("Booth Subtotal: " + boothSubtotal);
        System.out.println("Booth VAT (Corrected): " + boothVat);
        System.out.println("Booth Total (Corrected): " + boothTotal);
        System.out.println("Utility Subtotal: " + utilSubtotal);
        System.out.println("Utility VAT: " + utilVat);
        System.out.println("Utility Total: " + utilTotal);
        System.out.println("Final PrcSum: " + prcSum);
        System.out.println("Final PrcVat: " + prcVat);
        System.out.println("Final PrcTotal: " + prcTotal);
        System.out.println("Deposit: " + input.getDeposit());
        System.out.println("Final Balance: " + balance);
        System.out.println("--------------------------");*/

        return result;
    }

    /**
     * 2026 모든 금액을 계산하는 중앙 메서드
     * @param input CalculationInputDTO (계산에 필요한 모든 입력값)
     * @return CalculationResultDTO (계산된 모든 결과값)
     */
    public CalculationResultDTO calculate2026Invoice(CalculationInputDTO input) {
        CalculationResultDTO result = new CalculationResultDTO();

        // --- 1. 부스 총액 계산 (등록비 포함) ---
        int boothPrcSum = REGISTRATION_FEE
                + (input.getStandAloneBoothCnt() * STAND_ALONE_BOOTH_FEE)
                + (input.getAssemblyBoothCnt() * ASSEMBLY_BOOTH_FEE)
                + (input.getOnlineBoothCnt() * ONLINE_BOOTH_FEE);
        result.setBoothPrcSum(boothPrcSum);

        // --- 2. 기본 할인 총액 계산 ---
        int basicDiscountSum = 0;
        int physicalBooths = input.getStandAloneBoothCnt() + input.getAssemblyBoothCnt();

        // --- 조기 신청 할인 (중복 가능) ---
        if (input.isDiscountEarly1()) basicDiscountSum += physicalBooths * DISCOUNT_EARLY_1_AMOUNT;
        if (input.isDiscountEarly2()) basicDiscountSum += physicalBooths * DISCOUNT_EARLY_2_AMOUNT;

        // --- 첫 참가 / 재참가 할인 (택 1) ---
        int participationDiscount = 0;
        if (input.isDiscountFirstUnder10() && physicalBooths < 10) {
            participationDiscount = physicalBooths * DISCOUNT_FIRST_UNDER_10_AMOUNT;
        } else if (input.isDiscountFirstOver10() && physicalBooths >= 10) {
            participationDiscount = physicalBooths * DISCOUNT_FIRST_OVER_10_AMOUNT;
        } else if (input.isDiscountRe()) { // 첫 참가가 아닐 때만 재참가 적용
            participationDiscount = physicalBooths * DISCOUNT_RE_AMOUNT;
        }
        basicDiscountSum += participationDiscount; // 첫 참가 또는 재참가 중 하나만 더함

        // --- 규모 할인 (택 1, 위와 중복 가능) ---
        int scaleDiscount = 0;
        // 가장 큰 구간부터 확인하여 하나만 적용
        if (physicalBooths >= 100 && input.isDiscountScale6()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_6_AMOUNT;
        } else if (physicalBooths >= 50 && input.isDiscountScale5()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_5_AMOUNT;
        } else if (physicalBooths >= 40 && input.isDiscountScale4()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_4_AMOUNT;
        } else if (physicalBooths >= 30 && input.isDiscountScale3()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_3_AMOUNT;
        } else if (physicalBooths >= 20 && input.isDiscountScale2()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_2_AMOUNT;
        } else if (physicalBooths >= 10 && input.isDiscountScale1()) {
            scaleDiscount = physicalBooths * DISCOUNT_SCALE_1_AMOUNT;
        }
        basicDiscountSum += scaleDiscount; // 규모 할인 더함 (첫/재참가와 중복됨)

        // --- 협회 할인 (중복 가능) ---
        if (input.isDiscountLeisure()) basicDiscountSum += physicalBooths * DISCOUNT_LEISURE_AMOUNT;
        result.setBasicDiscountSum(basicDiscountSum);

        // --- 3. 특별 할인 총액 계산 ---
        int specialDiscountTotal = 0;
        // 특별 할인 기준액 = 부스비 - 기본할인액
        int baseAmountForSpecial = boothPrcSum - basicDiscountSum;
        if (baseAmountForSpecial < 0) baseAmountForSpecial = 0; // 음수 방지

        if (input.isDiscountSpecial1Yn()) {
            specialDiscountTotal += Math.floor(baseAmountForSpecial * 0.5); // 50% 할인
        }
        if (input.isDiscountSpecial2Yn()) {
            specialDiscountTotal += input.getDiscountSpecial2Amount();
        }
        if (input.isDiscountSpecial3Yn()) {
            specialDiscountTotal += input.getDiscountSpecial3Amount();
        }
        result.setSpecialDiscountTotal(specialDiscountTotal);

        // --- 4. 발전 기금 계산 (요청하신 최종 로직) ---
        int developmentFund = 0;
        if ("Y".equals(input.getMemberCompanyYn()) || input.isDiscountLeisure()) {
            // 기준액 = (부스비 총액 - (기본 할인 + 특별 할인))
            int baseAmountForFund = boothPrcSum - (basicDiscountSum + specialDiscountTotal);
            if (baseAmountForFund < 0) baseAmountForFund = 0; // 음수 방지
            developmentFund = (int) Math.floor(baseAmountForFund * 0.1); // 10%
        }
        result.setDevelopmentFund(developmentFund);

        // --- 5. 최종 금액 계산 (분리 및 합산) ---

        // 5-1. 유틸리티 계산서
        int utilSubtotal = input.getUtilityPrcSum();
        int utilVat = (int) Math.floor(utilSubtotal * 0.1);
        int utilTotal = utilSubtotal + utilVat;

        result.setUtilityPrcSum(utilSubtotal);
        result.setUtilityVat(utilVat);
        result.setUtilityTotal(utilTotal);

        // 5-2. 부스 계산서
        // (부스비 총액 + 발전기금) - (기본 할인 + 특별 할인)
        int boothSubtotal = (boothPrcSum + developmentFund) - (basicDiscountSum + specialDiscountTotal);
        int boothVat = (int) Math.floor(boothSubtotal * 0.1);
        int boothTotal = boothSubtotal + boothVat;

        result.setBoothSubtotal(boothSubtotal);
        result.setBoothVat(boothVat);
        result.setBoothTotal(boothTotal);

        // 5-3. 최종 총계 (DB 저장용)
        // (부가세 오차 방지를 위해, 합산 후 다시 계산)
        int prcSum = boothSubtotal + utilSubtotal;
        int prcVat = (int) Math.floor(prcSum * 0.1); // ★★★ 부가세는 합계(prcSum) 기준으로 계산
        int prcTotal = prcSum + prcVat;
        int balance = prcTotal - input.getDeposit();

        // (JSP 표기 오차 보정)
        // 만약 개별 부가세의 합이 전체 부가세와 다르면, 차액을 부스 부가세에 더한다.
        int vatGap = prcVat - (boothVat + utilVat);
        if (vatGap != 0) {
            boothVat += vatGap; // 부스 부가세에 차액 보정
            boothTotal += vatGap; // 부스 합계에도 차액 보정
            result.setBoothVat(boothVat);
            result.setBoothTotal(boothTotal);
        }

        result.setPrcSum(prcSum);
        result.setPrcVat(prcVat);
        result.setPrcTotal(prcTotal);
        result.setBalance(balance);

        // 디버깅용 로그 출력 (실제 운영 시 제거 권장)
        /*System.out.println("--- Calculation Result ---");
        System.out.println("Booth Prc Sum: " + boothPrcSum);
        System.out.println("Utility Prc Sum Input: " + input.getUtilityPrcSum());
        System.out.println("Basic Discount Sum: " + basicDiscountSum);
        System.out.println("Special Discount Total: " + specialDiscountTotal);
        System.out.println("Development Fund: " + developmentFund);
        System.out.println("Booth Subtotal: " + boothSubtotal);
        System.out.println("Booth VAT (Corrected): " + boothVat);
        System.out.println("Booth Total (Corrected): " + boothTotal);
        System.out.println("Utility Subtotal: " + utilSubtotal);
        System.out.println("Utility VAT: " + utilVat);
        System.out.println("Utility Total: " + utilTotal);
        System.out.println("Final PrcSum: " + prcSum);
        System.out.println("Final PrcVat: " + prcVat);
        System.out.println("Final PrcTotal: " + prcTotal);
        System.out.println("Deposit: " + input.getDeposit());
        System.out.println("Final Balance: " + balance);
        System.out.println("--------------------------");*/

        return result;
    }
}