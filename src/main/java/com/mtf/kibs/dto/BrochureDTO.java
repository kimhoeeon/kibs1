package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class BrochureDTO {

    /**
     * DB 테이블 컬럼과 매핑되는 필드
     */
    private String transferYear; // 대상 연도 (PK)

    // 메인 국문
    private String mainKoTitle;
    private String mainKoPath; // DB에 저장될 파일 경로
    private String mainKoOriginalName;

    // 메인 영문
    private String mainEnTitle;
    private String mainEnPath;
    private String mainEnOriginalName;

    // KISS
    private String kissTitle;
    private String kissPath;
    private String kissOriginalName;

    // KMTS
    private String kmtsTitle;
    private String kmtsPath;
    private String kmtsOriginalName;

    /**
     * JSP 폼에서 파일 업로드를 받기 위한 필드 (name 속성과 일치)
     * (이 필드들은 DB에 저장되지 않음 - @Transient와 동일한 효과)
     */
    private MultipartFile mainKoFile;
    private MultipartFile mainEnFile;
    private MultipartFile kissFile;
    private MultipartFile kmtsFile;

    /**
     * JSP 폼에서 기존 파일 경로를 받기 위한 필드 (name 속성과 일치)
     */
    private String mainKoFile_existing;
    private String mainEnFile_existing;
    private String kissFile_existing;
    private String kmtsFile_existing;

    // 기존 원본 파일명을 JSP에서 받기 위한 hidden input
    private String mainKoOriginalName_existing;
    private String mainEnOriginalName_existing;
    private String kissOriginalName_existing;
    private String kmtsOriginalName_existing;
}