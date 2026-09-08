package com.mtf.kibs.controller;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.AiClippingKeywordDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clipping")
public class AiClippingApiController {

    @Autowired
    private AiClippingMapper aiClippingMapper;

    // 메인 페이지 노출용 최신 기사 1건 조회
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestClipping() {
        Map<String, Object> result = new HashMap<>();
        AiClippingDTO latestClipping = aiClippingMapper.selectAiClippingLatest();

        result.put("resultCode", "0");
        result.put("data", latestClipping);
        return ResponseEntity.ok(result);
    }

    // 클리핑 목록 조회 (페이징 포함)
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getClippingList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) String filterKeyword,
            @RequestParam(required = false) String displayYn) {

        Map<String, Object> result = new HashMap<>();

        AiClippingDTO searchDto = new AiClippingDTO();
        searchDto.setSearchType(searchType);
        searchDto.setSearchKeyword(searchKeyword);
        searchDto.setFilterKeyword(filterKeyword);
        searchDto.setDisplayYn(displayYn);
        searchDto.setLimit(size);
        searchDto.setOffset((page - 1) * size);

        List<AiClippingDTO> list = aiClippingMapper.selectAiClippingList(searchDto);
        int totalCount = aiClippingMapper.selectAiClippingCount(searchDto);

        result.put("resultCode", "0");
        result.put("list", list);
        result.put("totalCount", totalCount);
        result.put("currentPage", page);

        return ResponseEntity.ok(result);
    }

    // 2. 컨트롤러 맨 아래에 프론트엔드용 키워드 목록 조회 API 신규 추가
    @GetMapping("/keyword/list")
    public ResponseEntity<Map<String, Object>> getFrontKeywordList() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<AiClippingKeywordDTO> list = aiClippingMapper.selectKeywordList();
            result.put("resultCode", "0");
            result.put("list", list);
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "키워드 조회 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    // 조회수 증가
    @PostMapping("/view/{seq}")
    public ResponseEntity<Map<String, String>> increaseViewCount(@PathVariable String seq) {
        Map<String, String> result = new HashMap<>();
        aiClippingMapper.updateViewCnt(seq);
        result.put("resultCode", "0");
        return ResponseEntity.ok(result);
    }

    // 공유 횟수 증가
    @PostMapping("/share/{seq}")
    public ResponseEntity<Map<String, String>> increaseShareCount(@PathVariable String seq) {
        Map<String, String> result = new HashMap<>();
        aiClippingMapper.updateShareCnt(seq);
        result.put("resultCode", "0");
        return ResponseEntity.ok(result);
    }
}