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

    // --- 국문 (KRW) 단가 ---
    private static final int REGISTRATION_FEE = 100000;
    private static final int STAND_ALONE_BOOTH_FEE = 1800000;
    private static final int ASSEMBLY_BOOTH_FEE = 2100000;
    private static final int ONLINE_BOOTH_FEE = 1000000; // 온라인 부스비 (필요시 수정)

    // --- 영문 (USD) 단가 ---
    private static final int EN_REGISTRATION_FEE = 0;
    private static final int EN_STAND_ALONE_BOOTH_FEE = 3000;
    private static final int EN_ASSEMBLY_BOOTH_FEE = 3300;
    private static final int EN_ONLINE_BOOTH_FEE = 0;

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

        // 0. 언어 체크 (EN 여부)
        boolean isEnglish = "EN".equalsIgnoreCase(input.getLang());

        int registrationFee = isEnglish ? EN_REGISTRATION_FEE : REGISTRATION_FEE;
        int standAloneFeeUnit = isEnglish ? EN_STAND_ALONE_BOOTH_FEE : STAND_ALONE_BOOTH_FEE;
        int assemblyFeeUnit = isEnglish ? EN_ASSEMBLY_BOOTH_FEE : ASSEMBLY_BOOTH_FEE;
        int onlineFeeUnit = isEnglish ? EN_ONLINE_BOOTH_FEE : ONLINE_BOOTH_FEE;

        // --- 1. 부스 총액 계산 (등록비 포함) ---
        int boothPrcSum = registrationFee
                + (input.getStandAloneBoothCnt() * standAloneFeeUnit)
                + (input.getAssemblyBoothCnt() * assemblyFeeUnit)
                /*+ (input.getOnlineBoothCnt() * onlineFeeUnit)*/;
        result.setBoothPrcSum(boothPrcSum);

        // --- 2. 기본 할인 총액 계산 ---
        int basicDiscountSum = 0;
        int physicalBooths = input.getStandAloneBoothCnt() + input.getAssemblyBoothCnt();

        if (!isEnglish) {
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
            basicDiscountSum += participationDiscount;

            // --- 규모 할인 (택 1, 위와 중복 가능) ---
            int scaleDiscount = 0;
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
            basicDiscountSum += scaleDiscount;

            // --- 협회 할인 (중복 가능) ---
            if (input.isDiscountLeisure()) basicDiscountSum += physicalBooths * DISCOUNT_LEISURE_AMOUNT;
        }
        result.setBasicDiscountSum(basicDiscountSum);

        // --- 3. 특별 할인 총액 계산 ---
        int specialDiscountTotal = 0;
        int baseAmountForSpecial = boothPrcSum - basicDiscountSum;
        if (baseAmountForSpecial < 0) baseAmountForSpecial = 0;

        if (!isEnglish) {
            if (input.isDiscountSpecial1Yn()) {
                specialDiscountTotal += Math.floor(baseAmountForSpecial * 0.5);
            }
            if (input.isDiscountSpecial2Yn()) {
                specialDiscountTotal += input.getDiscountSpecial2Amount();
            }
            if (input.isDiscountSpecial3Yn()) {
                specialDiscountTotal += input.getDiscountSpecial3Amount();
            }
        }
        result.setSpecialDiscountTotal(specialDiscountTotal);

        // --- 4. 발전 기금 계산 ---
        int developmentFund = 0;
        if (!isEnglish) {
            if ("Y".equals(input.getMemberCompanyYn()) || input.isDiscountLeisure()) {
                int baseAmountForFund = boothPrcSum - (basicDiscountSum + specialDiscountTotal);
                if (baseAmountForFund < 0) baseAmountForFund = 0;
                developmentFund = (int) Math.floor(baseAmountForFund * 0.1);
            }
        }
        result.setDevelopmentFund(developmentFund);

        // --- 5. 최종 금액 계산 (분리 및 합산) ---

        // 5-1. 유틸리티 계산서
        int utilSubtotal = input.getUtilityPrcSum();
        int utilVat = isEnglish ? 0 : (int) Math.floor(utilSubtotal * 0.1);
        int utilTotal = utilSubtotal + utilVat;

        result.setUtilityPrcSum(utilSubtotal);
        result.setUtilityVat(utilVat);
        result.setUtilityTotal(utilTotal);

        // 5-2. 부스 계산서
        int boothSubtotal = (boothPrcSum + developmentFund) - (basicDiscountSum + specialDiscountTotal);
        int boothVat = isEnglish ? 0 : (int) Math.floor(boothSubtotal * 0.1);
        int boothTotal = boothSubtotal + boothVat;

        result.setBoothSubtotal(boothSubtotal);
        result.setBoothVat(boothVat);
        result.setBoothTotal(boothTotal);

        // 5-3. 최종 총계 (DB 저장용)
        int prcSum = boothSubtotal + utilSubtotal;
        int prcVat = isEnglish ? 0 : (int) Math.floor(prcSum * 0.1);
        int prcTotal = prcSum + prcVat;
        int balance = prcTotal - input.getDeposit();

        // (JSP 표기 오차 보정 - 영문은 부가세가 0이므로 제외)
        if (!isEnglish) {
            int vatGap = prcVat - (boothVat + utilVat);
            if (vatGap != 0) {
                boothVat += vatGap;
                boothTotal += vatGap;
                result.setBoothVat(boothVat);
                result.setBoothTotal(boothTotal);
            }
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

        boolean isEnglish = "EN".equalsIgnoreCase(input.getLang());

        int registrationFee = isEnglish ? EN_REGISTRATION_FEE : REGISTRATION_FEE;
        int standAloneFeeUnit = isEnglish ? EN_STAND_ALONE_BOOTH_FEE : STAND_ALONE_BOOTH_FEE;
        int assemblyFeeUnit = isEnglish ? EN_ASSEMBLY_BOOTH_FEE : ASSEMBLY_BOOTH_FEE;
        int onlineFeeUnit = isEnglish ? EN_ONLINE_BOOTH_FEE : ONLINE_BOOTH_FEE;

        // --- 1. 부스 총액 계산 (등록비 포함) ---
        int boothPrcSum = registrationFee
                + (input.getStandAloneBoothCnt() * standAloneFeeUnit)
                + (input.getAssemblyBoothCnt() * assemblyFeeUnit)
                + (input.getOnlineBoothCnt() * onlineFeeUnit);
        result.setBoothPrcSum(boothPrcSum);

        // --- 2. 기본 할인 총액 계산 ---
        int basicDiscountSum = 0;
        int physicalBooths = input.getStandAloneBoothCnt() + input.getAssemblyBoothCnt();

        if (!isEnglish) {
            if (input.isDiscountEarly1()) basicDiscountSum += physicalBooths * DISCOUNT_EARLY_1_AMOUNT;
            if (input.isDiscountEarly2()) basicDiscountSum += physicalBooths * DISCOUNT_EARLY_2_AMOUNT;

            int participationDiscount = 0;
            if (input.isDiscountFirstUnder10() && physicalBooths < 10) {
                participationDiscount = physicalBooths * DISCOUNT_FIRST_UNDER_10_AMOUNT;
            } else if (input.isDiscountFirstOver10() && physicalBooths >= 10) {
                participationDiscount = physicalBooths * DISCOUNT_FIRST_OVER_10_AMOUNT;
            } else if (input.isDiscountRe()) {
                participationDiscount = physicalBooths * DISCOUNT_RE_AMOUNT;
            }
            basicDiscountSum += participationDiscount;

            int scaleDiscount = 0;
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
            basicDiscountSum += scaleDiscount;

            if (input.isDiscountLeisure()) basicDiscountSum += physicalBooths * DISCOUNT_LEISURE_AMOUNT;
        }
        result.setBasicDiscountSum(basicDiscountSum);

        // --- 3. 특별 할인 총액 계산 ---
        int specialDiscountTotal = 0;
        int baseAmountForSpecial = boothPrcSum - basicDiscountSum;
        if (baseAmountForSpecial < 0) baseAmountForSpecial = 0;

        if (!isEnglish) {
            if (input.isDiscountSpecial1Yn()) {
                specialDiscountTotal += Math.floor(baseAmountForSpecial * 0.5);
            }
            if (input.isDiscountSpecial2Yn()) {
                specialDiscountTotal += input.getDiscountSpecial2Amount();
            }
            if (input.isDiscountSpecial3Yn()) {
                specialDiscountTotal += input.getDiscountSpecial3Amount();
            }
        }
        result.setSpecialDiscountTotal(specialDiscountTotal);

        // --- 4. 발전 기금 계산 ---
        int developmentFund = 0;
        if (!isEnglish) {
            if ("Y".equals(input.getMemberCompanyYn()) || input.isDiscountLeisure()) {
                int baseAmountForFund = boothPrcSum - (basicDiscountSum + specialDiscountTotal);
                if (baseAmountForFund < 0) baseAmountForFund = 0;
                developmentFund = (int) Math.floor(baseAmountForFund * 0.1);
            }
        }
        result.setDevelopmentFund(developmentFund);

        // --- 5. 최종 금액 계산 (분리 및 합산) ---
        int utilSubtotal = input.getUtilityPrcSum();
        int utilVat = isEnglish ? 0 : (int) Math.floor(utilSubtotal * 0.1);
        int utilTotal = utilSubtotal + utilVat;

        result.setUtilityPrcSum(utilSubtotal);
        result.setUtilityVat(utilVat);
        result.setUtilityTotal(utilTotal);

        int boothSubtotal = (boothPrcSum + developmentFund) - (basicDiscountSum + specialDiscountTotal);
        int boothVat = isEnglish ? 0 : (int) Math.floor(boothSubtotal * 0.1);
        int boothTotal = boothSubtotal + boothVat;

        result.setBoothSubtotal(boothSubtotal);
        result.setBoothVat(boothVat);
        result.setBoothTotal(boothTotal);

        int prcSum = boothSubtotal + utilSubtotal;
        int prcVat = isEnglish ? 0 : (int) Math.floor(prcSum * 0.1);
        int prcTotal = prcSum + prcVat;
        int balance = prcTotal - input.getDeposit();

        if (!isEnglish) {
            int vatGap = prcVat - (boothVat + utilVat);
            if (vatGap != 0) {
                boothVat += vatGap;
                boothTotal += vatGap;
                result.setBoothVat(boothVat);
                result.setBoothTotal(boothTotal);
            }
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