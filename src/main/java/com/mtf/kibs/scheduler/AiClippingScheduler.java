package com.mtf.kibs.scheduler;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.AiClippingKeywordDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.service.NewsletterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AiClippingScheduler {

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private NewsletterService newsletterService;

    @Autowired
    private AiClippingPipelineV2 pipelineV2;

    /**
     * 파이프라인 선택. legacy = 금 08:00 생성 → 월 08:00 노출·발송 (이 클래스의 기존 로직),
     * v2 = 월 08:00 생성 → 월 10:00 승인 글 노출·발송 (AiClippingPipelineV2). 2026-10-28 발주사 요청 전환용.
     */
    @Value("${ai.clipping.pipeline:legacy}")
    private String pipeline;

    /** v2: 월 10:00 자동 노출·발송을 승인(approve_yn='Y')된 글로 제한할지. 발주사 확답 전 기본 true */
    @Value("${ai.clipping.publish-requires-approval:true}")
    private boolean publishRequiresApproval;

    public boolean isV2() {
        return "v2".equalsIgnoreCase(pipeline == null ? "" : pipeline.trim());
    }

    public boolean isPublishRequiresApproval() {
        return publishRequiresApproval;
    }

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${openai.model.summarize}")
    private String openAiModel;

    /** 키워드당 Daum 뉴스 후보 기사 수 (이전에는 3건 하드코딩이라 커버리지가 좁았음) */
    @Value("${daum.search.articles-per-keyword:10}")
    private int articlesPerKeyword;

    /** 목록 페이지 간 딜레이(ms) - 크롤링 부하 완화 */
    @Value("${daum.search.list-delay-min-ms:1000}")
    private int listDelayMinMs;

    @Value("${daum.search.list-delay-max-ms:2000}")
    private int listDelayMaxMs;

    /** 후보 수집 시 최대 탐색 페이지 수 (Daum 뉴스 검색은 페이지당 10건 내외) */
    private static final int MAX_SEARCH_PAGES = 5;

    /** 이 일수 이내 기사를 우선 선정 (주간 클리핑 성격 유지). 이내 기사가 모두 탈락할 때만 더 오래된 기사 허용 */
    @Value("${ai.clipping.recent-days:30}")
    private int recentDays;

    /**
     * 선거일 목록(yyyy-MM-dd, 콤마 구분). 선거일 기준 electionWindowDays 전부터 선거일까지는
     * 후보자·출마예정자 관련 기사 배제를 더 엄격하게 적용한다.
     * 기본값: 2028-04-12 제23대 국회의원 선거, 2030-06-03 제22대 대통령 선거·제10회 지방선거. 일정 변경 시 설정으로 조정.
     */
    @Value("${ai.clipping.election-dates:2028-04-12,2030-06-03}")
    private String electionDates;

    @Value("${ai.clipping.election-window-days:90}")
    private int electionWindowDays;

    /** 이 일수 내에 생성된 클리핑(미노출 포함)에 이미 실린 기사 링크는 다음 생성 시 후보에서 제외 (호 간 중복 방지) */
    @Value("${ai.clipping.dedupe-days:60}")
    private int dedupeDays;

    /** 목표 건수 미달 시 보충 단계에서 같은 키워드에서 뽑을 수 있는 최대 기사 수 (1차는 항상 키워드당 1건) */
    @Value("${ai.clipping.max-picks-per-keyword:2}")
    private int maxPicksPerKeyword;

    /** 등록 키워드로 목표 건수를 못 채울 때만 사용하는 보조 키워드 (콤마 구분). 관리자 키워드 풀에 있는 것은 자동 제외 */
    @Value("${ai.clipping.fallback-keywords:해양레저산업,마리나,요트,보트쇼,해양관광,레저선박,수상레저,선박 전동화,해양레저 지원사업,요트 관광}")
    private String fallbackKeywords;

    // 1. [생성 담당] 매주 금요일 오전 8시 자동 실행 (미노출/미발송 상태로 생성만 수행)
    @Scheduled(cron = "0 0 8 * * FRI", zone = "Asia/Seoul")
    public void generateAiClippingAt10AM() {
        if (isV2()) {
            System.out.println("[금요일 08:00 AM] ai.clipping.pipeline=v2 이므로 금요일 생성은 건너뜁니다 (월 08:00 생성).");
            return;
        }
        System.out.println("========== [금요일 08:00 AM] AI 클리핑 자동 생성 스케줄러 시작 (미노출/미발송) ==========");
        processAiClipping(false); // false를 전달하여 뉴스레터 자동 발송 차단 (임시저장)
    }

    // 2. [발행 담당] 매주 월요일 오전 8시 자동 실행
    //    legacy: 금요일에 생성되어 검수 완료된 기사를 노출로 변경 후 발송
    //    v2    : 지난 주 월 08:00 ~ 지금 기사로 클리핑 생성(미노출·미승인). 발행은 10:00 (publishApprovedAt10AM)
    @Scheduled(cron = "0 0 8 * * MON", zone = "Asia/Seoul")
    public void publishAndSendAiClippingAt8AM() {
        if (isV2()) {
            System.out.println("========== [월요일 08:00 AM] AI 클리핑 V2 생성 스케줄러 시작 (미노출/미승인) ==========");
            pipelineV2.generate(false);
            return;
        }
        System.out.println("========== [월요일 08:00 AM] AI 클리핑 자동 승인 및 발송 스케줄러 시작 ==========");

        try {
            // DB에서 가장 최근에 생성된 미노출(display_yn='N') 기사 1건을 조회
            AiClippingDTO searchDto = new AiClippingDTO();
            searchDto.setDisplayYn("N");
            searchDto.setLimit(1);
            searchDto.setOffset(0);

            List<AiClippingDTO> pendingList = aiClippingMapper.selectAiClippingList(searchDto);

            if (pendingList != null && !pendingList.isEmpty()) {
                AiClippingDTO target = pendingList.get(0);

                // 1) 노출 상태를 'Y'로 업데이트하여 홈페이지에 게시
                Map<String, String> param = new HashMap<>();
                param.put("seq", target.getSeq());
                param.put("displayYn", "Y");
                aiClippingMapper.updateDisplayStatus(param);
                System.out.println("-> 게시물 노출 상태 [게시중(Y)]으로 변경 완료 (SEQ: " + target.getSeq() + ")");

                // 2) 구독자에게 뉴스레터 메일 발송
                newsletterService.sendClippingNewsletter(target.getSeq(), target.getTitle(), target.getContent());
                System.out.println("-> 뉴스레터 자동 발송 완료");

                System.out.println("========== [월요일 08:00 AM] AI 클리핑 승인/발송 프로세스 정상 종료 ==========");
            } else {
                System.out.println("========== [월요일 08:00 AM] 발송 대기 중인(미노출) 기사가 없습니다. ==========");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== [월요일 08:00 AM] AI 클리핑 승인/발송 중 오류 발생 ==========");
        }
    }

    // 2-1. [v2 발행 담당] 매주 월요일 오전 10시: 오늘 08:00 에 생성된 글 중 (승인된) 미노출 글을 노출 전환 후 발송
    @Scheduled(cron = "0 0 10 * * MON", zone = "Asia/Seoul")
    public void publishApprovedAt10AM() {
        if (!isV2()) return;
        System.out.println("========== [월요일 10:00 AM] AI 클리핑 V2 발행 스케줄러 시작 (승인 필수: " + publishRequiresApproval + ") ==========");
        pipelineV2.publishToday(publishRequiresApproval);
    }

    // 3. 실제 클리핑 수집 및 생성을 담당하는 코어 로직 (관리자 수동 생성 포함). v2 설정이면 V2 파이프라인으로 위임
    public boolean processAiClipping(boolean isSend) {
        if (isV2()) {
            return pipelineV2.generate(isSend) != null;
        }
        return processAiClippingLegacy(isSend);
    }

    private boolean processAiClippingLegacy(boolean isSend) {
        System.out.println("========== AI 클리핑 코어 프로세스 시작 (발송여부: " + isSend + ") ==========");

        try {
            int sleepTime = new Random().nextInt(10000);
            Thread.sleep(sleepTime);

            String todayDate = LocalDate.now().toString();
            String generatedTitle = "[경기국제보트쇼 AI 클리핑] " + todayDate + " 해양레저산업 주요 동향";

            List<AiClippingKeywordDTO> keywordList = aiClippingMapper.selectKeywordList();

            if (keywordList == null || keywordList.isEmpty()) {
                System.out.println("========== 등록된 키워드가 없어 AI 클리핑을 종료합니다 ==========");
                return false;
            }

            Collections.shuffle(keywordList);

            CollectContext ctx = new CollectContext();
            ctx.targetCount = 5; // 목표 기사 수

            // 최근 발행/생성된 클리핑에 이미 실린 기사 링크·제목 (호 간 중복 방지)
            loadRecentlyUsed(ctx);
            System.out.println("-> 최근 " + dedupeDays + "일 클리핑에 실린 기사 링크 " + ctx.previouslyUsedLinks.size() + "건 제외 대상, 제목 " + ctx.previouslyUsedTitles.size() + "건을 '이미 다룬 사안'으로 전달");

            List<String> poolKeywords = new ArrayList<>();
            for (AiClippingKeywordDTO kw : keywordList) {
                if (kw.getKeyword() != null && !kw.getKeyword().trim().isEmpty()) poolKeywords.add(kw.getKeyword().trim());
            }

            List<String> fallback = new ArrayList<>();
            if (fallbackKeywords != null) {
                for (String s : fallbackKeywords.split(",")) {
                    String t = s.trim();
                    if (!t.isEmpty() && !poolKeywords.contains(t)) fallback.add(t);
                }
            }
            Collections.shuffle(fallback);

            // [1차] 등록 키워드에서 키워드당 1건씩 선정 (최근 recentDays 이내 기사만)
            collectArticles(poolKeywords, 1, false, "1차 (등록 키워드, 키워드당 1건, 최근 기사)", ctx);

            // [2차] 목표 미달이면 같은 키워드에서 추가 선정 (재크롤링 없이 캐시된 후보 재사용, 키워드당 누적 최대 maxPicksPerKeyword 건, 최근 기사만)
            if (ctx.collected < ctx.targetCount && maxPicksPerKeyword > 1) {
                System.out.println("-> 목표 " + ctx.targetCount + "건 미달(" + ctx.collected + "건). 2차 보충: 등록 키워드에서 추가 선정");
                collectArticles(poolKeywords, maxPicksPerKeyword, false, "2차 (등록 키워드 추가 선정, 최근 기사)", ctx);
            }

            // [3차] 그래도 미달이면 보조 키워드(설정값)로 수집 (최근 기사만)
            if (ctx.collected < ctx.targetCount && !fallback.isEmpty()) {
                System.out.println("-> 목표 " + ctx.targetCount + "건 미달(" + ctx.collected + "건). 3차 보충: 보조 키워드 " + fallback.size() + "개로 수집");
                collectArticles(fallback, 1, false, "3차 (보조 키워드, 최근 기사)", ctx);
                if (ctx.collected < ctx.targetCount && maxPicksPerKeyword > 1) {
                    collectArticles(fallback, maxPicksPerKeyword, false, "3차 (보조 키워드 추가 선정, 최근 기사)", ctx);
                }
            }

            // [4차] 최근 기사만으로 목표를 못 채울 때만 이전 기사(recentDays 초과) 허용 — 등록 키워드 → 보조 키워드 순
            if (ctx.collected < ctx.targetCount) {
                System.out.println("-> 목표 " + ctx.targetCount + "건 미달(" + ctx.collected + "건). 4차 보충: 최근 " + recentDays + "일 이전 기사 허용");
                collectArticles(poolKeywords, Math.max(1, maxPicksPerKeyword), true, "4차 (등록 키워드, 이전 기사 허용)", ctx);
                if (ctx.collected < ctx.targetCount && !fallback.isEmpty()) {
                    collectArticles(fallback, Math.max(1, maxPicksPerKeyword), true, "4차 (보조 키워드, 이전 기사 허용)", ctx);
                }
            }

            System.out.println("-> 최종 확보 기사 " + ctx.collected + "건 / 목표 " + ctx.targetCount + "건, 사용 키워드: " + ctx.usedKeywords);

            StringBuilder rawArticlesBuilder = ctx.rawArticlesBuilder;
            List<String> usedKeywords = ctx.usedKeywords;
            List<String[]> usedSources = ctx.usedSources;

            String rawArticles = rawArticlesBuilder.toString();

            if (rawArticles.trim().isEmpty()) {
                System.out.println("========== 전체 키워드를 탐색했으나 AI 필터링을 통과한 유효 기사가 없어 종료합니다 ==========");
                return false; // 컨트롤러에 실패(false) 반환
            }

            // [3단계] 최종 확보된 유효 기사들로 뉴스레터 본문(HTML) 생성
            String generatedContent = generateFinalNewsletter(rawArticles, usedKeywords, usedSources);

            AiClippingDTO clippingDTO = new AiClippingDTO();
            clippingDTO.setTitle(generatedTitle);
            clippingDTO.setContent(generatedContent);

            int insertCnt = aiClippingMapper.insertAiClipping(clippingDTO);

            if (insertCnt > 0 && clippingDTO.getSeq() != null) {
                System.out.println("========== AI 클리핑 생성 완료 (SEQ: " + clippingDTO.getSeq() + ", 상태: 미노출) ==========");

                if (isSend) {
                    newsletterService.sendClippingNewsletter(
                            clippingDTO.getSeq(),
                            clippingDTO.getTitle(),
                            clippingDTO.getContent()
                    );
                    System.out.println("========== AI 클리핑 뉴스레터 수동 즉시 발송 완료 ==========");
                }
                return true; // 컨트롤러에 성공(true) 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== AI 클리핑 프로세스 실행 중 오류 발생 ==========");
        }
        return false;
    }

    /** 한 번의 클리핑 생성 동안 단계(1차/2차/3차)를 넘나들며 공유하는 수집 상태 */
    private static class CollectContext {
        int targetCount = 5;
        int collected = 0;
        StringBuilder rawArticlesBuilder = new StringBuilder();
        List<String> usedKeywords = new ArrayList<>();          // 표시용 (중복 없이)
        List<String[]> usedSources = new ArrayList<>();         // {언론사, 링크, 제목, 날짜}
        Set<String> previouslyUsedLinks = new HashSet<>();       // 최근 호에 실린 링크
        List<String> previouslyUsedTitles = new ArrayList<>();   // 최근 호에 실린 기사 제목 (같은 사안의 다른 언론사 기사 제외용)
        Map<String, List<String[]>> candidateCache = new HashMap<>(); // 키워드별 크롤링 결과 캐시
        Map<String, Integer> picksPerKeyword = new HashMap<>();  // 키워드별 선정 건수
        Set<String> rejectedRecent = new HashSet<>();            // 최근 기사만으로 심사했을 때 전부 탈락한 키워드
        Set<String> rejectedOlder = new HashSet<>();             // 이전 기사까지 포함해 심사했을 때 전부 탈락한 키워드
    }

    /**
     * 주어진 키워드 목록을 순회하며 목표 건수에 도달할 때까지 기사를 선정한다.
     * - 키워드당 선정 건수가 maxPicks 에 도달한 키워드는 건너뛴다.
     * - 크롤링 결과는 캐시해 2차/3차 보충 단계에서 재크롤링하지 않는다.
     * - 이미 실린 기사(이번 호 + 최근 호)는 후보에서 제외한다.
     */
    private void collectArticles(List<String> keywords, int maxPicks, boolean allowOlder, String stageName, CollectContext ctx) throws InterruptedException {
        for (String targetKeyword : keywords) {
            if (ctx.collected >= ctx.targetCount) break;

            int picked = ctx.picksPerKeyword.getOrDefault(targetKeyword, 0);
            if (picked >= maxPicks) continue;

            // 같은 조건(최근만 / 이전 포함)으로 이미 전부 탈락한 키워드는 다시 심사하지 않음
            if (!allowOlder && ctx.rejectedRecent.contains(targetKeyword)) continue;
            if (allowOlder && ctx.rejectedOlder.contains(targetKeyword)) continue;

            System.out.println("-> [" + stageName + "] 수집 시도 키워드: " + targetKeyword);

            // [1단계] Daum에서 후보 기사 긁어오기 (캐시 우선)
            List<String[]> cached = ctx.candidateCache.get(targetKeyword);
            if (cached == null) {
                cached = fetchCandidateArticlesFromDaum(targetKeyword);
                ctx.candidateCache.put(targetKeyword, cached);
            } else {
                System.out.println("   캐시된 후보 " + cached.size() + "건 재사용");
            }
            List<String[]> candidateArticles = new ArrayList<>(cached);

            // 이미 실린 기사는 후보에서 제외:
            //  - 이번 생성에서 이미 선정된 기사 (같은 호 내 중복 방지)
            //  - 최근 dedupeDays 일 내 클리핑에 실린 기사 (호 간 중복 방지)
            if (!candidateArticles.isEmpty()) {
                Set<String> usedLinks = new HashSet<>(ctx.previouslyUsedLinks);
                for (String[] s : ctx.usedSources) usedLinks.add(normalizeLink(s[1]));
                int before = candidateArticles.size();
                candidateArticles.removeIf(c -> usedLinks.contains(normalizeLink(c[1])));
                int removed = before - candidateArticles.size();
                if (removed > 0) System.out.println("   이미 실린 기사 " + removed + "건 후보 제외");
            }

            // 최근 기사만 허용하는 단계에서는 recentDays 를 넘긴 후보를 미리 제거
            if (!allowOlder) {
                int before = candidateArticles.size();
                candidateArticles.removeIf(c -> !isRecent(c[3]));
                int removed = before - candidateArticles.size();
                if (removed > 0) System.out.println("   최근 " + recentDays + "일 이전 기사 " + removed + "건 후보 제외 (이 단계는 최근 기사만)");
            }

            if (candidateArticles.isEmpty()) {
                System.out.println("   [스킵] 선정 가능한 후보 없음.");
                continue;
            }

            // [2단계] 능동적 필터링: AI에게 기사 적합성 심사 (최근 호 제목을 '이미 다룬 사안'으로 함께 전달)
            String validArticleFromAi = filterAndSummarizeValidArticle(targetKeyword, candidateArticles, ctx.usedSources, ctx.previouslyUsedTitles);

            if (validArticleFromAi != null && !validArticleFromAi.trim().isEmpty()) {
                ctx.rawArticlesBuilder.append(validArticleFromAi).append("\n\n");
                if (!ctx.usedKeywords.contains(targetKeyword)) ctx.usedKeywords.add(targetKeyword);
                ctx.picksPerKeyword.put(targetKeyword, picked + 1);
                ctx.collected++;
                System.out.println("   [성공] 유효 기사 확보 (" + ctx.collected + "/" + ctx.targetCount + ")");
            } else {
                // 같은 조건으로는 더 뽑을 게 없으므로 기록해 두고 다음 단계에서 건너뛰게 함
                if (allowOlder) ctx.rejectedOlder.add(targetKeyword); else ctx.rejectedRecent.add(targetKeyword);
                System.out.println("   [스킵] 후보 기사들이 AI 적합성 심사(산업 무관, 사건사고, 선거, 이미 다룬 사안 등)에서 모두 탈락함.");
            }

            if (ctx.collected >= ctx.targetCount) break;

            Thread.sleep(1500);
        }
    }

    // 1단계: Daum 뉴스 크롤링 메서드
    private List<String[]> fetchCandidateArticlesFromDaum(String keyword) {
        List<String[]> candidates = new ArrayList<>();
        Set<String> seenLinks = new HashSet<>();
        int targetCount = Math.max(1, articlesPerKeyword);

        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");

            // 설정값(daum.search.articles-per-keyword)만큼 채울 때까지 검색 결과 페이지를 넘겨가며 수집
            for (int page = 1; page <= MAX_SEARCH_PAGES && candidates.size() < targetCount; page++) {
                if (page > 1) {
                    Thread.sleep(randomDelay(listDelayMinMs, listDelayMaxMs));
                }

                String url = "https://search.daum.net/search?w=news&q=" + encodedKeyword + "&sort=rec&p=" + page;

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .timeout(5000)
                        .get();

                Elements newsElements = doc.select(".c-item-content");
                if (newsElements.isEmpty()) break; // 더 이상 결과 없음

                int addedOnThisPage = 0;

                for (Element element : newsElements) {
                    if (candidates.size() >= targetCount) break;

                    Element titleElement = element.select(".item-title").first();
                    if (titleElement == null) continue;

                    String title = titleElement.text();
                    String summary = element.select(".conts-desc").text();

                    // 1. 불량 텍스트 차단
                    if (summary.contains("function()") || summary.contains("document.") || summary.contains("{") || summary.contains("}")) {
                        continue;
                    }

                    // 2. 링크 처리
                    String link = "";
                    if (titleElement.tagName().equals("a")) link = titleElement.attr("href");
                    else if (titleElement.select("a").first() != null) link = titleElement.select("a").first().attr("href");
                    else if (element.select("a").first() != null) link = element.select("a").first().attr("href");

                    // 깡통 링크 차단
                    if (link == null || link.trim().isEmpty() || link.startsWith("#") || link.contains("search.daum.net")) {
                        continue;
                    }
                    if (!link.startsWith("http")) link = "https:" + (link.startsWith("/") ? "" : "/") + link;

                    // 페이지 간 중복 기사 제거
                    if (!seenLinks.add(link)) continue;

                    // 3. 날짜 추출
                    String fullText = element.text().replace(title, "").replace(summary, "");
                    fullText = fullText.replace("동영상 첨부된 문서", "").replace("사진 첨부된 문서", "").replace("음성 첨부된 문서", "").replace("다음뉴스", "")
                            .replaceAll("관련기사\\s*\\d+건?", "").replaceAll("관련뉴스\\s*\\d+건?", "").replaceAll("[|·ⓒ]", " ").trim();

                    //    검색 결과의 상대 표기("3시간 전", "어제", "2주 전" 등)는 수집 시점 기준 절대 날짜(yyyy.MM.dd)로 고정한다.
                    //    (클리핑 본문에 저장된 뒤에는 재계산되지 않으므로 상대 표기를 남기지 않는다)
                    String rawDateText = "";
                    Matcher matcher = Pattern.compile("(\\d{4}\\.\\d{2}\\.\\d{2}\\.?|\\d+\\s*(시간|분|일|주|개월)\\s*전|어제)").matcher(fullText);
                    if (matcher.find()) rawDateText = matcher.group(1).trim();

                    LocalDate parsedDate = parseArticleDate(rawDateText);
                    String articleDate = (parsedDate != null ? parsedDate : LocalDate.now()).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

                    // 4. 언론사명(검색 결과 텍스트 기반 임시값)
                    //    기사 본문 접속(og:site_name)은 후보마다 하지 않고, AI 가 선정한 1건에 대해서만 resolvePublisher() 로 수행한다.
                    String leftover = fullText;
                    if (!rawDateText.isEmpty()) leftover = leftover.replace(rawDateText, "");
                    String publisher = leftover.replaceAll("(?<!\\S)\\d+(?!\\S)", "").replaceAll("\\s{2,}", " ").trim();
                    if (publisher.isEmpty() || publisher.matches("^[0-9\\s\\p{P}]+$")) publisher = "언론사";

                    candidates.add(new String[]{publisher, link, title, articleDate, summary});
                    addedOnThisPage++;
                }

                if (addedOnThisPage == 0) break; // 이 페이지에서 새 기사가 없으면 종료
            }

            System.out.println("   후보 기사 " + candidates.size() + "건 수집 (목표 " + targetCount + "건)");
        } catch (Exception e) {
            System.err.println("Daum 크롤링 실패 [" + keyword + "]: " + e.getMessage());
        }
        return candidates;
    }

    /**
     * 검색 결과의 날짜 표기("2026.10.20", "3시간 전", "어제", "2일 전", "1주 전", "2개월 전")를 LocalDate 로 변환.
     * 해석 불가 시 null.
     */
    private LocalDate parseArticleDate(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        String t = text.trim();
        LocalDate today = LocalDate.now();
        try {
            if (t.matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.?")) {
                if (t.endsWith(".")) t = t.substring(0, t.length() - 1);
                return LocalDate.parse(t, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            }
            if (t.contains("어제")) return today.minusDays(1);
            Matcher m = Pattern.compile("(\\d+)\\s*(시간|분|일|주|개월)\\s*전").matcher(t);
            if (m.find()) {
                int n = Integer.parseInt(m.group(1));
                switch (m.group(2)) {
                    case "분":
                    case "시간":
                        return today;
                    case "일":
                        return today.minusDays(n);
                    case "주":
                        return today.minusWeeks(n);
                    case "개월":
                        return today.minusMonths(n);
                    default:
                        return null;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /** 링크 비교용 정규화: 공백 제거, 쿼리스트링·해시 제거, 끝 슬래시 제거, 소문자 */
    private String normalizeLink(String link) {
        if (link == null) return "";
        String l = link.trim();
        int q = l.indexOf('?');
        if (q >= 0) l = l.substring(0, q);
        int h = l.indexOf('#');
        if (h >= 0) l = l.substring(0, h);
        while (l.endsWith("/")) l = l.substring(0, l.length() - 1);
        return l.toLowerCase();
    }

    /** 프롬프트에 넘길 '이미 다룬 기사' 제목 최대 개수 (최신 호부터) */
    private static final int MAX_RECENT_TITLES_FOR_PROMPT = 60;

    /**
     * 최근 dedupeDays 일 내 생성된 클리핑(미노출 포함, 삭제 제외) 본문에서 기사 링크와 제목을 추출해 ctx 에 채운다.
     * 본문은 generateFinalNewsletter 가 만든 HTML 이며, 상단 "출처" 목록의 <a href='링크'>제목</a> 에서 제목을 얻는다.
     * (본문 중간의 키워드 하이퍼링크는 제목이 아니므로, 같은 링크에 대해 처음 나온 앵커 텍스트만 제목으로 사용한다)
     */
    private void loadRecentlyUsed(CollectContext ctx) {
        if (dedupeDays <= 0) return;
        try {
            List<String> contents = aiClippingMapper.selectRecentClippingContents(dedupeDays);
            if (contents == null) return;
            Pattern anchorPattern = Pattern.compile("<a\\s+[^>]*href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>(.*?)</a>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Set<String> titleSeen = new HashSet<>();
            for (String content : contents) {
                if (content == null) continue;
                Set<String> linksInThisContent = new HashSet<>();
                Matcher m = anchorPattern.matcher(content);
                while (m.find()) {
                    String url = m.group(1).trim();
                    // 사이트 내부 링크(수신거부, 로고 등)는 제외하고 외부 기사 링크만 수집
                    if (!url.startsWith("http")) continue;
                    if (url.contains("kibs.com")) continue;
                    String norm = normalizeLink(url);
                    ctx.previouslyUsedLinks.add(norm);

                    // 같은 본문에서 처음 등장한 앵커 텍스트 = 출처 목록의 기사 제목
                    if (linksInThisContent.add(norm) && ctx.previouslyUsedTitles.size() < MAX_RECENT_TITLES_FOR_PROMPT) {
                        String title = m.group(2).replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").trim();
                        if (title.length() >= 8 && titleSeen.add(title)) {
                            ctx.previouslyUsedTitles.add(title);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("최근 클리핑 링크/제목 조회 실패: " + e.getMessage());
        }
    }

    /**
     * 선거 관련 배제 규칙 문구.
     * - 항상: 후보자·예비후보자·출마예정자의 공약·유세·행보·인터뷰 기사는 정책 내용이 있어도 배제.
     * - 선거일 electionWindowDays 전 ~ 선거일: 현직 단체장·의원 기사도 공약성/치적 홍보성이면 배제하고,
     *   기관(도청·시청·부처) 명의의 정책·사업 공고 기사만 합격.
     */
    private String buildElectionRule() {
        StringBuilder sb = new StringBuilder();
        sb.append("6. **선거 관련 배제**: 선거 후보자·예비후보자·출마 예정자의 공약, 유세, 지역 방문 행보, 인터뷰 기사는 해양레저·해양관광 정책 내용이 있어도 무조건 버려.");

        LocalDate today = LocalDate.now();
        LocalDate activeElection = null;
        if (electionDates != null) {
            for (String s : electionDates.split(",")) {
                String t = s.trim();
                if (t.isEmpty()) continue;
                try {
                    LocalDate d = LocalDate.parse(t);
                    LocalDate windowStart = d.minusDays(Math.max(0, electionWindowDays));
                    if (!today.isBefore(windowStart) && !today.isAfter(d)) {
                        activeElection = d;
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
        }

        if (activeElection != null) {
            sb.append(" 특히 지금은 ").append(activeElection).append(" 선거일 기준 ").append(electionWindowDays)
              .append("일 전 선거 기간이야. 이 기간에는 현직 도지사·시장·군수·국회의원·지방의원이 등장하는 기사도 공약성·치적 홍보성이면 버리고, ")
              .append("기관(도청·시청·군청·부처·공사) 명의로 발표된 정책·사업 공고·인프라 조성 기사만 합격시켜.");
            System.out.println("   [선거 기간] " + activeElection + " 선거일 기준 " + electionWindowDays + "일 전 규칙 적용 중");
        }
        return sb.toString();
    }

    /** 기사 날짜가 recentDays 이내인지 (날짜를 모르면 최근으로 간주하지 않음) */
    private boolean isRecent(String dateText) {
        LocalDate d = parseArticleDate(dateText);
        return d != null && !d.isBefore(LocalDate.now().minusDays(Math.max(0, recentDays)));
    }

    private long randomDelay(int minMs, int maxMs) {
        if (maxMs <= minMs) return Math.max(0, minMs);
        return minMs + new Random().nextInt(maxMs - minMs);
    }

    /** 선정된 기사 1건에 대해서만 본문의 og:site_name 으로 언론사명을 확정한다 (실패 시 검색 결과 기반 임시값 유지) */
    private String resolvePublisher(String link, String fallback) {
        try {
            Document articleDoc = Jsoup.connect(link)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(3000)
                    .get();
            Element ogSiteName = articleDoc.selectFirst("meta[property=og:site_name]");
            if (ogSiteName != null) {
                String publisher = ogSiteName.attr("content").replace("Daum", "").replace("|", "").trim();
                if (!publisher.isEmpty() && !publisher.matches("^[0-9\\s\\p{P}]+$")) return publisher;
            }
        } catch (Exception ignored) {
        }
        return (fallback == null || fallback.isEmpty()) ? "언론사" : fallback;
    }

    // 2단계: AI 필터링 (번호를 기반으로 매칭하는 견고한 로직)
    private String filterAndSummarizeValidArticle(String keyword, List<String[]> candidates, List<String[]> usedSources, List<String> recentTitles) {
        // 최근 recentDays 이내 기사를 앞쪽에 배치 (상대 순서 유지). 프롬프트 번호와 선택 매칭은 이 정렬된 목록 기준.
        List<String[]> recent = new ArrayList<>();
        List<String[]> older = new ArrayList<>();
        for (String[] c : candidates) {
            if (isRecent(c[3])) recent.add(c); else older.add(c);
        }
        candidates.clear();
        candidates.addAll(recent);
        candidates.addAll(older);
        System.out.println("   후보 중 최근 " + recentDays + "일 이내 기사 " + recent.size() + "건 / 이전 기사 " + older.size() + "건");

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("너는 해양레저산업 비즈니스 전문가야. 다음은 '").append(keyword).append("' 키워드로 검색된 최신 뉴스 후보들이야.\n\n");
        promptBuilder.append("[판단 기준 - 매우 엄격함]\n");
        promptBuilder.append("1. **부정적/사건사고 기사 절대 배제**: 바다에서 일어난 조난, 구조, 갯벌 고립, 실종, 사망, 침몰, 해경 적발 등 단순 사회면/사건사고 기사는 무조건 버려.\n");
        promptBuilder.append("2. **타 산업/가십/연예 절대 배제**: 정치인 개인 가십·선거·정쟁, 연예인 사생활(연예인의 요트·보트 소유나 여행 기사 포함), 골프 클럽, 부동산, 아파트 분양 등 해양레저(보트, 요트, 마리나, 해양관광) 산업/비즈니스 동향과 관련 없는 기사는 무조건 버려.\n");
        promptBuilder.append("3. 위 두 기준을 통과한, 업계 관계자에게 유의미한 '산업 동향, 기술 발전, 전시회, 비즈니스, 관광 정책' 관련 기사만 합격이야.\n");
        promptBuilder.append("4. **정책 기사는 합격 대상**: 정부·지자체(도지사, 시장 등)의 해양레저·해양관광 정책 발표, 마리나·항만 등 인프라 조성 계획, 관련 예산·지원사업, 공식 행사(박람회, 축제, 간담회) 기사는 정치인이 등장하더라도 '정치 가십'이 아니라 '관광 정책' 기사로 보고 합격시켜. (단, 해양레저·해양관광 내용이 핵심이 아닌 단순 정치 행보 기사는 제외)\n");
        promptBuilder.append("5. **최신성 우선**: 이 클리핑은 주간 동향이야. 각 후보에 표시된 [최근] 기사 중 합격 기사가 하나라도 있으면 반드시 [최근] 기사 중에서 골라. [이전] 기사는 [최근] 기사가 모두 불합격일 때만 선택해.\n");
        promptBuilder.append(buildElectionRule()).append("\n");
        promptBuilder.append("7. **이미 다룬 사안 배제**: 아래 [최근 호에 이미 다룬 기사] 목록과 같은 사안(동일 정책 발표, 동일 행사, 동일 협약·사업, 동일 제품 출시)을 다룬 기사는 언론사나 제목이 달라도 무조건 버려. 새로운 사안이거나 뚜렷한 후속 진전(결과 발표, 착공, 수치 공개 등)이 있는 기사만 합격이야.\n\n");
        if (recentTitles != null && !recentTitles.isEmpty()) {
            promptBuilder.append("[최근 호에 이미 다룬 기사]\n");
            for (String t : recentTitles) {
                promptBuilder.append("- ").append(t).append("\n");
            }
            promptBuilder.append("\n");
        }
        promptBuilder.append("[요구사항]\n");
        promptBuilder.append("- 합격한 기사가 있다면, 그 중 가장 중요한 1개의 기사만 골라내. (5번 최신성 우선 규칙 적용)\n");
        promptBuilder.append("- 출력 형식은 반드시 아래와 같이 딱 4줄 텍스트로만 해. (마크다운 ** 등 절대 금지)\n");
        promptBuilder.append("선택번호: (1~").append(candidates.size()).append(" 중 하나 숫자만 입력)\n");
        promptBuilder.append("제목: (선택한 기사 제목)\n");
        promptBuilder.append("링크: (선택한 기사 링크)\n");
        promptBuilder.append("내용: (해당 기사의 핵심 내용을 3~4문장으로 요약)\n\n");
        promptBuilder.append("- 만약 모든 기사가 판단 기준에 미달하여 합격한 기사가 0개라면, 딱 한 단어 'REJECT' 라고만 출력해.\n\n");
        promptBuilder.append("[기사 후보 목록]\n");

        for (int i = 0; i < candidates.size(); i++) {
            String[] c = candidates.get(i);
            promptBuilder.append((i + 1)).append(". ")
                    .append(isRecent(c[3]) ? "[최근] " : "[이전] ")
                    .append("날짜: ").append(c[3]).append(" | 제목: ").append(c[2]).append("\n");
            promptBuilder.append("   내용: ").append(c[4]).append("\n\n");
        }

        String aiResponse = callOpenAI(promptBuilder.toString(), 1500);

        if (aiResponse == null || aiResponse.contains("REJECT")) {
            return null; // 모든 기사가 불합격
        }

        // [중요 수정] 출처 매칭 로직: URL 문자열 매칭 대신 '선택번호' 파싱 우선
        try {
            int selectedIndex = -1;
            String selectedTitle = "";
            String selectedLink = "";
            StringBuilder selectedSummaryBuilder = new StringBuilder();
            boolean isContentMode = false;

            String[] lines = aiResponse.split("\n");
            for (String line : lines) {
                // 마크다운 흔적 방어
                String cleanLine = line.trim().replaceAll("\\*\\*", "");

                if (cleanLine.startsWith("선택번호:")) {
                    isContentMode = false;
                    String numStr = cleanLine.replace("선택번호:", "").replaceAll("[^0-9]", "");
                    if (!numStr.isEmpty()) selectedIndex = Integer.parseInt(numStr) - 1;
                } else if (cleanLine.startsWith("제목:")) {
                    isContentMode = false;
                    selectedTitle = cleanLine.substring(cleanLine.indexOf("제목:") + 3).trim();
                } else if (cleanLine.startsWith("링크:")) {
                    isContentMode = false;
                    selectedLink = cleanLine.substring(cleanLine.indexOf("링크:") + 3).trim();
                } else if (cleanLine.startsWith("내용:")) {
                    isContentMode = true;
                    selectedSummaryBuilder.append(cleanLine.substring(cleanLine.indexOf("내용:") + 3).trim()).append(" ");
                } else if (isContentMode && !cleanLine.isEmpty()) {
                    selectedSummaryBuilder.append(cleanLine).append(" ");
                }
            }

            // 번호 파싱 실패 시, 예외적으로 URL 포함 여부로 매칭 시도 (Fallback)
            if (selectedIndex < 0 || selectedIndex >= candidates.size()) {
                for (int i = 0; i < candidates.size(); i++) {
                    if (!selectedLink.isEmpty() && (candidates.get(i)[1].contains(selectedLink) || selectedLink.contains(candidates.get(i)[1]))) {
                        selectedIndex = i;
                        break;
                    }
                }
            }

            // 매칭된 기사가 있다면 출처에 추가 후 작성기로 넘김
            if (selectedIndex >= 0 && selectedIndex < candidates.size()) {
                String[] c = candidates.get(selectedIndex);

                String finalTitle = selectedTitle.isEmpty() ? c[2] : selectedTitle;
                String finalSummary = selectedSummaryBuilder.toString().trim();

                // 선정된 기사 1건에 대해서만 본문 접속으로 언론사명 확정 (후보 전체 접속 대비 크롤링 부하 절감)
                String publisher = resolvePublisher(c[1], c[0]);

                // {언론사, 링크, 제목, 날짜}
                usedSources.add(new String[]{publisher, c[1], finalTitle, c[3]});
                return "제목: " + finalTitle + "\n원본링크: " + c[1] + "\n내용: " + finalSummary;
            }

        } catch (Exception e) {
            System.err.println("AI 필터 응답 파싱 오류: " + e.getMessage());
        }

        return null;
    }

    // 3단계: 최종 엄선된 기사들로 뉴스레터 HTML 코드 생성
    private String generateFinalNewsletter(String rawArticles, List<String> keywords, List<String[]> sources) {
        String prompt = "다음은 AI가 1차적으로 엄선한 해양레저 산업 관련 핵심 기사 요약본들이야. 이를 바탕으로 독자(업계 관계자)를 위한 전문적인 B2B 뉴스레터 본문을 작성해줘.\n\n" +
                "[작성 요구사항]\n" +
                "1. 전달받은 기사 내용을 바탕으로 4~5개의 소주제(섹션)로 나눌 것. 분량이 너무 길지 않도록 **각 섹션당 본문은 반드시 정확히 2개의 문단**으로만 핵심을 요약해서 작성할 것.\n" +
                "2. (여백 확보 및 순번 표기) 각 섹션의 제목은 반드시 '1.', '2.', '3.' 처럼 순번을 매겨서 아래 형식의 <h3> 태그를 사용할 것. (단락 구분 여백 포함)\n" +
                "   -> <h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>1. 섹션 제목</h3>\n" +
                "3. (단락 띄어쓰기) 본문 내용은 가독성을 위해 단락마다 반드시 아래 형식의 <p> 태그를 사용할 것. (각 섹션당 <p>태그는 딱 2개씩만 생성할 것)\n" +
                "   -> <p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>본문 내용...</p>\n" +
                "4. 선별하여 요약에 반영한 기사의 '원본링크'를 반드시 활용하여, 본문 문맥 중 핵심 텍스트에 <a> 태그로 하이퍼링크를 1개 이상 걸어줄 것.\n" +
                "   -> 하이퍼링크 스타일 양식: <a href='원본링크' target='_blank' style='color:#222222; text-decoration:underline; text-underline-offset:4px; font-weight:bold;'>키워드</a>\n" +
                "5. 단순 요약이 아닌, 독자(업계 관계자)에게 인사이트를 제공하는 전문적인 기사 톤으로 작성할 것.\n" +
                "6. 인사말이나 맺음말 없이 바로 <h3> 태그로 시작하는 본문 HTML만 출력하고, 문장이 중간에 잘리지 않도록 반드시 끝맺음을 완벽하게 할 것.\n" +
                "7. 매일 비슷하거나 정형화된 서론, 결론, 문장 구조가 반복되지 않도록 주의할 것. 매번 새로운 시각, 다채로운 어휘, 그리고 트렌디한 표현 방식을 도입하여 독자가 지루하지 않게 작성할 것.\n\n" +
                "[엄선된 기사 데이터]\n" + rawArticles;

        String aiContent = callOpenAI(prompt, 6000);
        if (aiContent == null) return "<p>생성 중 오류 발생</p>";

        // 하단 출처 표기 및 조립 로직
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);

        List<Map<String, Object>> parsedSources = new ArrayList<>();
        for (String[] src : sources) {
            Map<String, Object> map = new HashMap<>();
            String pub = src[0];
            String url = src[1];
            String title = src[2];
            String rawDate = src.length > 3 ? src[3] : "";

            // 수집 단계에서 이미 yyyy.MM.dd 로 고정되지만, 안전하게 공용 파서로 한 번 더 해석 (상대 표기도 처리)
            LocalDate parsedSrcDate = parseArticleDate(rawDate);
            LocalDate calculatedDate = parsedSrcDate != null ? parsedSrcDate : LocalDate.now();

            String displayDate = calculatedDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "(" + calculatedDate.format(DateTimeFormatter.ofPattern("E", java.util.Locale.KOREAN)) + ")";
            map.put("publisher", pub);
            map.put("url", url);
            map.put("title", title);
            map.put("date", calculatedDate);
            map.put("displayDate", displayDate);
            parsedSources.add(map);
        }

        parsedSources.sort((m1, m2) -> ((LocalDate) m2.get("date")).compareTo((LocalDate) m1.get("date")));

        String dateRange = "";
        if (!parsedSources.isEmpty()) {
            DateTimeFormatter mdFormatter = DateTimeFormatter.ofPattern("M. d.");
            String startDate = ((LocalDate) parsedSources.get(parsedSources.size() - 1).get("date")).format(mdFormatter);
            String endDate = ((LocalDate) parsedSources.get(0).get("date")).format(mdFormatter);
            dateRange = startDate.equals(endDate) ? startDate : startDate + " ~ " + endDate;
        }

        StringBuilder headerHtml = new StringBuilder();
        headerHtml.append("<div style='margin-bottom: 40px; padding: 20px; background-color: #f8f9fa; border-top: 4px solid #1d5cad; border-bottom: 1px solid #ddd;'>");
        headerHtml.append("   <strong style='color: #1d5cad; display:block; margin-bottom:15px; font-size: 16px;'>■ 기사</strong>");
        headerHtml.append("   <ul style='list-style: none; padding: 0; margin: 0;'>");
        for (int i = 0; i < parsedSources.size(); i++) {
            Map<String, Object> ps = parsedSources.get(i);
            headerHtml.append("<li style='font-size: 14px; color: #333; margin-bottom: 8px;'>");
            headerHtml.append("출처 (").append(i + 1).append(") ").append(ps.get("displayDate")).append(" | ");
            headerHtml.append("<a href='").append(ps.get("url")).append("' target='_blank' style='color:#1d5cad; font-weight:bold; text-decoration:underline; text-underline-offset:2px;'>").append(ps.get("title")).append("</a>");
            headerHtml.append(" | ").append(ps.get("publisher")).append("</li>");
        }
        headerHtml.append("   </ul></div>");

        if (!dateRange.isEmpty()) {
            headerHtml.append("<div style='background-color: #1d5cad; color: #ffffff; text-align: center; padding: 25px 15px; font-size: 24px; font-weight: bold; margin-bottom: 40px; border-radius: 5px;'>");
            headerHtml.append("해양레저산업 기사 요약 (기간: ").append(dateRange).append(")</div>");
        }

        StringBuilder footerHtml = new StringBuilder("<div style='margin-top: 50px; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #1d5cad; border-radius: 5px; text-align: left; font-size: 14px; color: #444; line-height: 1.6;'>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 수집 키워드 :</strong> ");
        for (String kw : keywords) footerHtml.append("#").append(kw).append(" ");
        footerHtml.append("<br><br>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 작성자 :</strong> 경기국제보트쇼 AI 브리핑 봇<br>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 생성 모델 :</strong> OpenAI ").append(openAiModel).append("<br>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 생성 일시 :</strong> ").append(currentTime).append("<br>");
        footerHtml.append("   <span style='font-size: 12px; color: #888; display: block; margin-top: 8px; margin-bottom: 12px;'>* 본 기사는 인공지능 모델이 자동 수집 및 요약한 내용으로, 원본 기사의 논조와 일부 다를 수 있습니다.</span></div>");

        return headerHtml.toString() + aiContent + footerHtml.toString();
    }

    // 공통 OpenAI API 호출 헬퍼
    private String callOpenAI(String prompt, int maxTokens) {
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", Collections.singletonList(message));
        body.put("max_completion_tokens", maxTokens);
        body.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> messageResp = (Map<String, Object>) choices.get(0).get("message");
                return (String) messageResp.get("content");
            }
        } catch (Exception e) {
            System.err.println("OpenAI API 호출 에러: " + e.getMessage());
        }
        return null;
    }
}