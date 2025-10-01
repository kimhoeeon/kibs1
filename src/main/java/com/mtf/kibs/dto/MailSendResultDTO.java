package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailSendResultDTO {
    private String resultCode;    // 결과 코드 (예: "0"은 성공, 그 외는 실패)
    private String resultMessage; // 결과 메시지 (예: "발송성공" 또는 에러 메시지)

    /**
     * 기본 생성자
     */
    public MailSendResultDTO() {
    }

    /**
     * 필드를 포함하는 생성자
     * @param resultCode 결과 코드
     * @param resultMessage 결과 메시지
     */
    public MailSendResultDTO(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    // --- Getters and Setters ---

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}