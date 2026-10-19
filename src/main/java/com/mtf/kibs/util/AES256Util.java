package com.mtf.kibs.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AES256Util {

    // application.properties 에서 읽어올 32바이트 암호화 키
    @Value("${encryption.secret-key}")
    private String secretKey;

    private final String alg = "AES/CBC/PKCS5Padding";

    // 암호화
    public String encrypt(String text) {
        if (text == null || text.isEmpty()) return text;
        try {
            String iv = secretKey.substring(0, 16);
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return text; // 오류 시 원래 문자열 반환
        }
    }

    // 복호화
    public String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) return cipherText;
        try {
            String iv = secretKey.substring(0, 16);
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 복호화 실패 시 원본 문자열 반환 (마이그레이션 전 평문 데이터 처리 용도)
            return cipherText;
        }
    }

    // ==========================================
    // 공통 안전 복호화 헬퍼 메서드 (강화 버전 - Util 통합)
    // ==========================================
    public String safeDecrypt(String text) {
        if (text == null || text.trim().isEmpty()) return text;
        String original = text.trim();
        String decrypted = original;

        try {
            // 1차 복호화 시도 (공백을 +로 치환)
            String cipher = original.replace(" ", "+");
            String dec = this.decrypt(cipher); // 내부 decrypt 호출

            if (dec != null && !dec.equals(cipher)) {
                decrypted = dec;

                // 2차 복호화 시도 (이중 암호화 방어)
                String secondCipher = dec.replace(" ", "+");
                try {
                    String secondDec = this.decrypt(secondCipher);
                    if (secondDec != null && !secondDec.equals(secondCipher)) {
                        decrypted = secondDec;
                    }
                } catch (Exception ignored) {} // 2차 복호화 실패 시 1차 결과 유지

            } else {
                // 전체 복호화 실패 시, 이메일처럼 '@' 기준으로 분리된 형태인지 확인
                if (original.contains("@")) {
                    String[] parts = original.split("@", 2);
                    String cipherId = parts[0].replace(" ", "+");
                    try {
                        String decId = this.decrypt(cipherId);
                        if (decId != null && !decId.equals(cipherId)) {
                            // ID 부분만 이중 암호화된 경우 대비
                            String secondCipherId = decId.replace(" ", "+");
                            try {
                                String secondDecId = this.decrypt(secondCipherId);
                                if (secondDecId != null && !secondDecId.equals(secondCipherId)) {
                                    decId = secondDecId;
                                }
                            } catch (Exception ignored) {}

                            decrypted = decId + "@" + parts[1];
                        }
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            // 복호화 과정 중 예외 발생 시 원본 반환
        }
        return decrypted;
    }

    // ==========================================
    // 다중 암호문(GROUP_CONCAT 등) 복호화 헬퍼 메서드 (Util 통합)
    // ==========================================
    public String decryptMulti(String text) {
        if (text == null || text.trim().isEmpty()) return text;
        String[] parts = text.split("\\^", -1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            sb.append(this.safeDecrypt(parts[i])); // 내부 safeDecrypt 호출
            if (i < parts.length - 1) sb.append("^");
        }
        return sb.toString();
    }
}