package com.mtf.kibs.mapper;

import com.mtf.kibs.dto.NewsletterSendHistoryDTO;
import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NewsletterMapper {

    // ==== 구독자 관리 ====

    // 구독 신청
    int insertSubscriber(NewsletterSubscriberDTO dto);

    String checkSubscriberStatus(String email);

    int updateReSubscribe(NewsletterSubscriberDTO dto);

    // 이메일 중복 체크 (결과가 0보다 크면 중복)
    int checkEmailDuplicate(String email);

    // 구독자 목록 조회 (관리자 검색 필터 포함)
    List<NewsletterSubscriberDTO> selectSubscriberList(Map<String, Object> params);

    // 구독자 총 개수 (페이징용)
    int selectSubscriberCount(Map<String, Object> params);

    // 관리자 대시보드 통계 (총 구독자, 수신가능, 수신거부, 오늘 신규)
    Map<String, Object> selectSubscriberStatistics();

    // 수신 상태 변경 (사용자 직접 해지 또는 관리자 처리)
    int updateReceiveStatus(NewsletterSubscriberDTO dto);

    // 구독자 삭제 (데이터 완전 삭제)
    int deleteSubscriber(String seq);

    // 발송 대상자 전체 목록 추출 (수신중인 사람만)
    List<NewsletterSubscriberDTO> selectActiveSubscribers();

    // 뉴스레터 발송 후 최근 발송일 업데이트
    int updateLastSendDate(String email);


    // ==== 발송 이력 관리 ====

    // 발송 결과 이력 등록
    int insertSendHistory(NewsletterSendHistoryDTO dto);

    // 발송 이력 페이징 목록 조회
    List<NewsletterSendHistoryDTO> selectSendHistoryList(Map<String, Object> params);

    // 발송 이력 총 개수 조회
    int selectSendHistoryCount(Map<String, Object> params);

}