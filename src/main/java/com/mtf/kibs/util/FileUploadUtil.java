package com.mtf.kibs.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileUploadUtil {

    /**
     * 파일을 서버의 지정된 물리적 경로에 저장합니다.
     *
     * @param file         업로드된 MultipartFile 객체
     * @param physicalPath 파일을 저장할 서버의 절대 경로 (예: /usr/local/tomcat/webapps/upload/center/board/brochure/)
     * @return 저장된 실제 파일명 (UUID 적용)
     * @throws IOException 파일 저장 실패 시
     */
    public static String saveFile(MultipartFile file, String physicalPath) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        File uploadDir = new File(physicalPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리가 없으면 생성합니다.
        }

        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // 중복을 피하기 위해 UUID로 고유한 파일명 생성
        String savedFileName = UUID.randomUUID().toString() + extension;
        File targetFile = new File(physicalPath + File.separator + savedFileName);

        file.transferTo(targetFile); // 파일을 실제 경로에 저장

        return savedFileName;
    }

    /**
     * 서버에서 기존 파일을 삭제합니다.
     *
     * @param fullPhysicalPath 삭제할 파일의 전체 절대 경로
     * (예: /usr/local/tomcat/webapps/upload/center/board/brochure/abc.pdf)
     */
    public static void deleteFile(String fullPhysicalPath) {
        if (fullPhysicalPath == null || fullPhysicalPath.isEmpty()) {
            return;
        }

        try {
            File file = new File(fullPhysicalPath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            // 파일 삭제 실패 시 로그 (또는 예외 처리)
            e.printStackTrace();
        }
    }
}