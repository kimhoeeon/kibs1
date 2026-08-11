package com.mtf.kibs.controller;

import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.mapper.NewsletterMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mng/center/board")
public class AdminNewsletterController {

    @Autowired
    private NewsletterMapper newsletterMapper;

    // 뉴스레터 구독자 관리 페이지
    @GetMapping("/subscriber.do")
    public String subscriberList(Model model) {
        Map<String, Object> stats = newsletterMapper.selectSubscriberStatistics();
        model.addAttribute("stats", stats);

        return "mng/center/board/subscriber";
    }

    // AI 뉴스 클리핑 관리 페이지
    @GetMapping("/clipping.do")
    public String clippingList() {
        return "mng/center/board/clipping";
    }

    // 구독자 엑셀 다운로드
    @GetMapping("/subscriber/excelDownload.do")
    public void downloadExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        // 페이징 없이 전체 조회를 위해 limit 제거 처리
        params.remove("limit");
        params.remove("offset");

        List<NewsletterSubscriberDTO> list = newsletterMapper.selectSubscriberList(params);

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("구독자 목록");

        // 엑셀 헤더 세팅
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("이름");
        headerRow.createCell(1).setCellValue("이메일");
        headerRow.createCell(2).setCellValue("개인정보 수집 동의");
        headerRow.createCell(3).setCellValue("광고성 정보 동의");
        headerRow.createCell(4).setCellValue("등록일시");
        headerRow.createCell(5).setCellValue("최종 발송일");
        headerRow.createCell(6).setCellValue("수신상태");

        // 엑셀 본문 데이터 세팅
        int rowNum = 1;
        for (NewsletterSubscriberDTO dto : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(dto.getName());
            row.createCell(1).setCellValue(dto.getEmail());
            row.createCell(2).setCellValue("Y".equals(dto.getPrivacyAgreeYn()) ? "동의" : "미동의");
            row.createCell(3).setCellValue("Y".equals(dto.getAdAgreeYn()) ? "동의" : "미동의");
            row.createCell(4).setCellValue(dto.getRegDate());
            row.createCell(5).setCellValue(dto.getLastSendDate() != null ? dto.getLastSendDate() : "-");
            row.createCell(6).setCellValue(dto.getReceiveStatus());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"newsletter_subscribers.xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.dispose();
    }
}