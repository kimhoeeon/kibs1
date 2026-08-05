package com.mtf.kibs.controller.api;

import com.mtf.kibs.dto.AiClippingDTO;
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
            @RequestParam(required = false) String title) {

        Map<String, Object> result = new HashMap<>();

        AiClippingDTO searchDto = new AiClippingDTO();
        searchDto.setTitle(title);
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