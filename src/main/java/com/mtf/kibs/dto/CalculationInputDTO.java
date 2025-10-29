package com.mtf.kibs.dto;

import lombok.Data;

/**
 * 모든 금액 계산을 위한 입력 DTO
 * 프론트엔드 또는 서비스 레이어에서 이 객체를 채워서 CalculationService로 전달합니다.
 */
@Data
public class CalculationInputDTO {

    // 1. 부스 정보 (JSP의 input[name=...] 또는 hidden 값)
    private int registrationCnt;        // 등록비 카운트 (1 또는 0)
    private int standAloneBoothCnt;     // 독립부스 수
    private int assemblyBoothCnt;       // 조립부스 수
    private int onlineBoothCnt;         // 온라인부스 수

    // 2. 유틸리티 정보 (DB 또는 hidden 값)
    private int utilityPrcSum;          // 유틸리티 총액

    // 3. 기본 할인 정보 (JSP의 checkbox 체크 여부)
    private boolean discountEarly1;
    private boolean discountEarly2;
    private boolean discountFirstUnder10;
    private boolean discountFirstOver10;
    private boolean discountRe;
    private boolean discountScale1;     // 10~19
    private boolean discountScale2;     // 20~29
    private boolean discountScale3;     // 30~39
    private boolean discountScale4;     // 40~49
    private boolean discountScale5;     // 50~99
    private boolean discountScale6;     // 100~
    private boolean discountLeisure;    // 해양레저산업협회

    // 4. 특별 할인 정보 (관리자 입력값 또는 DB 값)
    private boolean discountSpecial1Yn;
    private boolean discountSpecial2Yn;
    private int discountSpecial2Amount;
    private boolean discountSpecial3Yn;
    private int discountSpecial3Amount;

    // 5. 발전 기금 계산용 정보 (DB 값)
    private String memberCompanyYn;     // 회원사 여부 ("Y" or "N")

    // 6. 기타 정보
    private int deposit;                // 선납금 (잔액 계산용)
}