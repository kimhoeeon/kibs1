package com.mtf.kibs.mapper;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.AiClippingKeywordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AiClippingMapper {

    // AI 클리핑 등록 (스케줄러 자동 생성)
    int insertAiClipping(AiClippingDTO dto);

    // AI 클리핑 목록 조회 (관리자 및 프론트)
    List<AiClippingDTO> selectAiClippingList(AiClippingDTO dto);

    // AI 클리핑 전체 개수 (페이징용)
    int selectAiClippingCount(AiClippingDTO dto);

    // 최신 AI 클리핑 1건 조회 (메인 노출용)
    AiClippingDTO selectAiClippingLatest();

    // AI 클리핑 상세 조회
    AiClippingDTO selectAiClippingDetail(String seq);

    // AI 클리핑 수정 (관리자 수동 수정)
    int updateAiClipping(AiClippingDTO dto);

    // AI 클리핑 삭제 (del_yn = 'Y' 처리)
    int deleteAiClipping(String seq);

    // 프론트 조회수 증가
    int updateViewCnt(String seq);

    // 프론트 공유 횟수 증가
    int updateShareCnt(String seq);

    // 메일 발송 성공/실패 카운트 업데이트
    int updateSendCnt(AiClippingDTO dto);

    // ==== 키워드 관리 ====
    List<AiClippingKeywordDTO> selectKeywordList();

    int checkKeywordDuplicate(String keyword);

    int insertKeyword(AiClippingKeywordDTO dto);

    int deleteKeyword(int seq);
}