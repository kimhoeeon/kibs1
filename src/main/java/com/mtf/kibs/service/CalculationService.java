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
    private static final int STAND_ALONE_BOOTH_FEE = 2000000;
    private static final int ASSEMBLY_BOOTH_FEE = 2100000;
    private static final int ONLINE_BOOTH_FEE = 1000000; // 온라인 부스비 (필요시 수정)

    /**
     * 모든 금액을 계산하는 중앙 메서드
     * @param input CalculationInputDTO (계산에 필요한 모든 입력값)
     * @return CalculationResultDTO (계산된 모든 결과값)
     */
    public CalculationResultDTO calculateTotals(CalculationInputDTO input) {
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

        if (input.isDiscountEarly1()) basicDiscountSum += physicalBooths * 300000;
        if (input.isDiscountEarly2()) basicDiscountSum += physicalBooths * 200000;
        if (input.isDiscountFirstUnder10()) basicDiscountSum += physicalBooths * 500000;
        if (input.isDiscountFirstOver10()) basicDiscountSum += physicalBooths * 300000;
        if (input.isDiscountRe()) basicDiscountSum += physicalBooths * 200000;
        if (input.isDiscountScale1()) basicDiscountSum += physicalBooths * 400000;
        if (input.isDiscountScale2()) basicDiscountSum += physicalBooths * 650000;
        if (input.isDiscountScale3()) basicDiscountSum += physicalBooths * 750000;
        if (input.isDiscountScale4()) basicDiscountSum += physicalBooths * 800000;
        if (input.isDiscountScale5()) basicDiscountSum += physicalBooths * 850000;
        if (input.isDiscountScale6()) basicDiscountSum += physicalBooths * 900000;
        if (input.isDiscountLeisure()) basicDiscountSum += physicalBooths * 200000;
        result.setBasicDiscountSum(basicDiscountSum);

        // --- 3. 특별 할인 총액 계산 ---
        int specialDiscountTotal = 0;
        // 특별 할인 기준액 = (부스비 + 유틸리티비) - 기본할인액
        int baseAmountForSpecial = boothPrcSum + input.getUtilityPrcSum() - basicDiscountSum;
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

        // 디버깅용 로그 출력 (실제 운영 시 제거)
        /*System.out.println("Booth Prc Sum: " + boothPrcSum);
        System.out.println("Development Fund: " + developmentFund);
        System.out.println("Basic Discount Sum: " + basicDiscountSum);
        System.out.println("Special Discount Total: " + specialDiscountTotal);
        System.out.println("Booth Subtotal: " + boothSubtotal);
        System.out.println("Booth VAT: " + boothVat);
        System.out.println("Booth Total: " + boothTotal);*/

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

        return result;
    }
}