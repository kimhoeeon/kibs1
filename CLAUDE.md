# KIBS (경기국제보트쇼) 홈페이지 + 관리자 시스템

이 파일은 이 프로젝트에서 작업하는 Claude 세션에 자동 로드된다. 이슈·수정이 생길 때마다 "변경 이력"과 "알려진 이슈"를 최신화할 것.

## 프로젝트 개요
- 도메인: kibs.com (국문 `/`, 영문 `/eng/**`, 관리자 `/mng/**`). 발주사 킨텍스, 개발사 MTF.
- 스택: Spring Boot 2.7.13, Java 8, WAR(외장 Tomcat `/usr/local/tomcat/webapps/ROOT`), JSP+JSTL, MyBatis XML, MariaDB, Lombok, POI, jsoup, DirectSend(메일/SMS), OpenAI(AI 클리핑).
- 규모: Java 130여 개(34k줄), JSP 300개, 매퍼 XML 11k줄, 커스텀 JS 32k줄 + `main.js`/`main_en.js` 각 7~8k줄.
- 설정 `src/main/resources/application.properties`는 git 제외. 서버 사본은 별도 관리(배포 시 덮어쓰지 않음).

## 실행 / 빌드
- JDK 8: `~/.jdks/corretto-1.8.0_504` (PATH에 java 없음). 컴파일: `JAVA_HOME=... ./gradlew compileJava --offline`.
- **이 PC에 로컬 DB 없음.** 설정의 "local" 주석 라인 `1.234.5.22:3306/kibs1`은 kibs.com 운영 서버 = **운영 DB**. 로컬 실행은 운영 데이터에 쓴다.
- 로컬 실행: `SPRING_DATASOURCE_JDBC_URL='jdbc:log4jdbc:mariadb://1.234.5.22:3306/kibs1?serverTimezone=UTC&characterEncoding=utf8mb4&userUnicode=true' ./gradlew bootRun --offline` (설정 파일은 수정하지 않음). `.claude/launch.json`(untracked, 커밋 금지)에 동일 설정.
- 관리자 `/mng/**`는 ACCESS 테이블 IP 화이트리스트. 로컬 요청 IP는 `0:0:0:0:0:0:0:1` / `127.0.0.1` → 운영 관리자 > 접근 IP 관리에 등록해야 로컬에서 관리자 페이지 진입 가능. 관리자 로그인은 사용자가 직접 한다.
- 업로드 물리 경로 `/usr/local/tomcat/webapps/upload` 하드코딩(KibsController `/uploadFile.do`, KibsMngController `/file/upload.do`) → 로컬에서는 저장 실패. 로컬에서 업로드 기능 검증 불가.
- 메일은 DirectSend로 **실제 발송**된다. 테스트 수신처는 `*@example.com`.
- 테스트 데이터는 이름에 `TEST`를 붙이고, 끝나면 관리자 삭제 기능으로 제거한다.

## 구조 핵심
- `controller/`: KibsController(국문 공개), KibsEnController(영문 공개, 국문 복제본), KibsMngController(관리자, 8.9k줄), KibsMngNewController(2027 관리자), AdminBoard/Newsletter/AiClipping.
- `service/impl/`: KibsServiceImpl(공개), KibsMngServiceImpl(관리자, 7k줄), CommServiceImpl(메일/SMS/파일), NewsletterServiceImpl. 금액 계산은 `service/CalculationService` 단일 소스(2026/2027 메서드).
- `util/`: AES256Util(개인정보 암복호화, `safeDecrypt`는 이중 암호화도 복호), ApplyPeriod(신청 기간 판정), ClientIpResolver(IP 추출), TesterAccessChecker(기간 외 테스트 허용 판정).
- `interceptor/`: AdminAccessInterceptor(IP), AdminSessionInterceptor(세션), MyBatisEncryptInterceptor(`@EncryptField` 자동 암복호화).
- 참가신청서 3세대: `~2025` EXHIBITOR 계열(`/mng/exhibitor/**`, exhibitor.js) / `2026` EXHIBITOR_NEW transfer_year=2026(`/mng/exhibitorNew/**`, exhibitorNew.js) / `2027~` 같은 테이블 transfer_year=2027(`/mng/exhibitorNewNew/**`, exhibitorNewNew.js, booth_new_new.js 등). 2026·2027은 JS 상수 `transferYear`와 경로만 다른 복제본.
- 공개 신청 플로우: `/apply/step01` → `step2_1`(부스) → `2_9`(출품) → `2_2`(간판) → `2_3`(유틸리티) → `2_4`(출입증) → `2_5`(경품) → `2_8`(디렉토리) → `step03`(승인요청 제출, 접수 메일). `/mypage/*`는 동일 단계 수정. 영문은 `/eng/apply`, `/eng/mypage`.
- 참관객: `/visitor/apply.do` 등록, `/visitor/login.do` 이름+휴대전화 확인 → `/visitor/mypage.do?seq=`. 영문은 `/eng/buyer/*`이지만 저장/확인 API는 국문 `/visitor/*`를 공용.
- AI 뉴스 클리핑(`scheduler/AiClippingScheduler`): 금 08:00 생성(미노출) → 월 08:00 노출+구독자 발송. 선정 로직 = 관리자 등록 키워드(ai_clipping_keyword)를 섞어 순회 → 키워드당 Daum 뉴스 최신순 후보 `daum.search.articles-per-keyword`건(기본 10, 최대 5페이지 페이지네이션, 페이지 간 `daum.search.list-delay-*` 딜레이, 다른 키워드에서 이미 선정된 기사와 최근 `ai.clipping.dedupe-days`(기본 60)일 내 생성된 클리핑(미노출 포함) 본문의 href 링크에 있는 기사 제외 — 링크는 쿼리스트링·끝 슬래시 제거 후 비교) → GPT 필터(사건사고·구조·조난·해경 적발, 정치인 가십·선거, 연예인 사생활, 부동산 등 **무조건 배제**; 산업·기술·전시회·비즈니스·정책 기사 합격; 정부·지자체 해양레저/해양관광 정책·인프라·공식행사 기사는 정치인이 등장해도 합격; 키워드당 1건) → 기사 날짜는 수집 시점에 절대 날짜(yyyy.MM.dd)로 고정(상대 표기 "N시간 전/어제/N주 전/N개월 전"을 `parseArticleDate`로 변환, 본문 저장 후 재계산 없음) → 후보에 날짜와 [최근]/[이전] 표시를 붙여 `ai.clipping.recent-days`(기본 30) 이내 기사를 우선 선택(이내 기사가 모두 탈락할 때만 이전 기사 허용) → 선거 규칙: 후보자·예비후보자·출마예정자 기사는 항상 배제, `ai.clipping.election-dates`(기본 2028-04-12 총선, 2030-06-03 대선·지방선거)의 `ai.clipping.election-window-days`(기본 90)일 전부터는 현직 단체장·의원 공약성/치적 기사도 배제하고 기관 명의 정책 공고만 합격 → 선정 기사 1건만 본문 접속해 언론사명 확정 → 목표 5건 도달 시 중단. **목표 미달 시 자동 보충**: 1~3차는 최근 `recent-days` 이내 기사만 심사(초과 기사는 후보에서 제거). 2차로 같은 키워드에서 추가 선정(크롤링 캐시 재사용, 키워드당 누적 `ai.clipping.max-picks-per-keyword`(기본 2)건), 3차로 보조 키워드(`ai.clipping.fallback-keywords`, 기본 해양레저산업·마리나·요트·보트쇼·해양관광 등, 관리자 풀과 중복 제외) 수집, **4차에서만 이전 기사 허용**(등록 → 보조 키워드 순). 프롬프트에는 최근 호 출처 제목(최대 60개)을 "[최근 호에 이미 다룬 기사]"로 넘겨 같은 사안의 다른 언론사 기사도 배제(규칙 7) → 뉴스레터 HTML 생성. 관리자 `/mng/center/board/clipping.do`에서 수동 생성·키워드 관리 가능. **배제 조건은 발주사·사용자 합의로 유지(2026-10-26).** 공개 상세 `/board/clippingView.do`는 display_yn=Y 글만 열리고, 미노출·삭제 글은 관리자 세션이 아니면 목록으로 리다이렉트.
- **AI 클리핑 V2**(`scheduler/AiClippingPipelineV2`, 공용 헬퍼 `scheduler/AiClippingSupport`, 2026-10-28 발주사 요청, **미배포·미전환**): `ai.clipping.pipeline=v2`(기본 `legacy`)일 때 AiClippingScheduler 가 위임. 월 08:00 생성(미노출·미승인) → 관리자 검수·**발행 승인**(`approve_yn`, `/mng/center/board/clipping/approve.do`) → 월 10:00 `publishToday`: 당일 생성·미노출·미삭제 글 중 승인 글(`ai.clipping.publish-requires-approval`, 기본 true) 최신 1건 노출+발송. 금요일 생성 스케줄은 v2 에서 건너뜀. 흐름: 등록 키워드(등록 순) × Daum 최신순 `ai.clipping.v2.pages-per-keyword`(3) 페이지, 수집 창 = 지난 월 08:00(`window-anchor-day/hour`, 기준 시각이 `window-min-hours`(24h) 이내면 한 주 전) ~ 지금, 날짜 단위 필터 → 전처리(이전 호 링크 제외 → 링크 중복 → 말머리 제거 후 제목 유사도 `similarity-threshold`(0.8) 중복 → 제목 블랙리스트 `ai.clipping.title-blacklist` → EXCLUDED_BLACKLIST) → GPT 연관도 0~100(`scoring-chunk-size` 50건씩, 3대 축: 레저선박·장비 / 마리나·계류 인프라 / 해양관광·레저문화; 사건사고·해외 관광지/카지노·연예가십·정당/정치인 개인·군함 0점, 기관 명의 정책 기사 정상 채점, 선거 규칙 유지) → `min-score`(10) 미만 EXCLUDED_SCORE(프롬프트가 1~9점 사용 금지·무관 기사 0점이므로 사실상 0점 제외 + 안전망), 나머지 LISTED → 점수순 상위 `body-fetch-count`(10)건을 고르되 같은 사안(정규화 제목의 최장 공통 부분문자열 ≥ `same-issue-min-common`(8))은 `max-per-issue`(2)건까지만 → 본문 접속(`body-max-chars` 3000, 저장 안 함, og:site_name 으로 언론사 확정) → GPT 토픽 최대 `target-topics`(5)개(`###TOPIC` 블록: 태그/제목/출처/문단 2개, 태그는 `ai.clipping.topic-tags` 고정 목록에 매핑, 제목 30자 명사형) → 출처 기사 SELECTED(topic_no) → 기존 레이아웃 HTML(■ 기사 출처 / 타이틀 밴드 / `n. [태그] 제목` 섹션 / 푸터) → `ai_clipping` + `ai_clipping_article`(기사별 keyword/title/link/publisher/summary/article_date/relevance_score/status/topic_no). 공개 상세는 `relatedArticles`(SELECTED+LISTED, 날짜 내림차순)로 "■ 최신 관련 기사" 영역을 **웹에만** 렌더(메일 본문 미포함, 요약문 비공개). 관리자 목록은 임시저장 글에 발행 승인/미승인 배지·토글, 기존 "게시" 버튼은 "즉시 게시"로 명칭 변경. **배포 전 운영 DB에 `src/main/resources/sql/20261028_ai_clipping_v2.sql` 적용 필수**(approve_yn 컬럼이 없으면 legacy 모드에서도 ai_clipping 조회가 실패한다).

## 수정 규칙 (반드시 지킬 것)
1. **국문/영문 동시 반영**: KibsController ↔ KibsEnController, main.js ↔ main_en.js, 국문/영문 JSP. 한쪽만 고치면 어긋난다.
2. **연도별 복제본 동시 반영**: 2027 수정이면 exhibitorNewNew.* (JS/JSP/컨트롤러). 2026(exhibitorNew)은 지난 연도라 보통 제외.
3. **연도/기간 상수 위치**: 신청 기간은 `util/ApplyPeriod.java` 한 곳. 현재 참가기업 2026-10-21 08:59 ~ 2026-12-31 23:59, 참관객 2027-01-01 08:59 ~ 2027-03-09 23:59. JS의 `var transferYear = '2027'`, 컨트롤러의 `"2027"` 상수 20여 곳은 연도 전환 시 일괄 변경 대상.
4. **개인정보 암호화**: DTO의 `@EncryptField` 필드는 MyBatis 인터셉터가 **매퍼 호출마다** 제자리에서 암호화한다. 서비스에서 수동 `aes256Util.encrypt` 금지. 쓰기 전에 같은 DTO로 SELECT를 호출하면 이중 암호화되므로, 사전 조회는 키 필드만 담은 별도 DTO로 한다(KibsServiceImpl `processInsertExhibitorNew` 참고). 읽기는 `safeDecrypt`(1·2중 모두 복호). `@EncryptField`가 없는 필드(예: companyFax)만 `cleanAndEncrypt`로 수동 처리.
5. **`?mode=open|soon|close`**: 접근 IP 화이트리스트 IP 또는 관리자 세션(`adminYn=Y` / `admin_origin_id`)에서만 동작. 기간 외 저장(`/visitor/save.do` 신규, `/apply/step/insertExhibitorNew.do`)도 같은 조건. 개발사무실 IP가 등록되어 있어야 오픈 전 테스트 가능.
6. **클라이언트 IP**: `ClientIpResolver` 사용. `security.trust-proxy-headers`(기본 false) 가 true일 때만 X-Forwarded-For 신뢰. 컨트롤러에서 헤더 직접 읽지 말 것.
7. **참관객 마이페이지 접근**: `/visitor/preApplyCheck.do` 또는 `/visitor/save.do` 성공 시 세션 `visitorSeq` 저장 → 마이페이지/수정/동반자 삭제는 세션 seq와 일치해야 허용.
8. **업로드 실패**: `/file/upload.do`는 실패 시 500 + `resultCode -1`. JS `f_company_uploadFile*`는 reject. 조용히 통과시키지 말 것.
9. 관리자 2027 업체 상세는 `lang` 없으면 업체 lang 사용(`"EN".equals(...)` 널 안전 비교 유지).
10. **AI 클리핑 legacy/V2 공존**: `AiClippingScheduler` 의 legacy 로직은 전환 완료 전까지 수정하지 않고, V2 변경은 `AiClippingPipelineV2`/`AiClippingSupport` 에서 한다(헬퍼 중복은 의도된 것, legacy 제거 시 Support 로 일원화). 스케줄 분기는 `isV2()` 한 곳. 새 설정은 `@Value` 기본값을 반드시 두어 서버 `application.properties` 에 항목이 없어도 뜨게 한다.
11. **AI 클리핑 V2 배제 기준**: 두 층으로 운영한다. (a) 확실한 배제 = 사건사고·연예·정당/정치인 개인·군함·해외 관광지 가십 + **해양레저와 아예 상관없는 기사(증시·IT·모터스포츠·문화공연·공시 등, 키워드·지역명만 겹치는 기사)** → 프롬프트 0점 규칙으로 여지없이 제외(사용자 확정 2026-10-28). (b) 해양 분야의 주변 기사(수산·조선·항만·해양환경, 10~29점)는 발주사 방침("누락보다 잡음이 낫다")에 따라 관련 기사 목록에 남긴다. 기준을 조이자는 요청은 점수 컷을 올리기보다 (a)의 0점 규칙·제목 블랙리스트에 구체 유형을 추가하는 방식으로.

## DirectSend 메일 템플릿
| 번호 | 용도 |
|---|---|
| 6 | 참가업체 참가신청 접수완료 (KO) |
| 184 | Exhibitor Application Received (EN 접수완료) |
| 171 | 참가기업 승인 안내 (KO, 관리자 참가 상태 변경) |
| 201 | Exhibitor Approval Notice (EN 승인, 2026-10-23 신규 발급) |
| 178 / 181 | 참관객 1차 / 2차 사전등록 완료 (KO) |
| 161 / 164 | 부스 / 유틸리티 관련 |
발송 API `POST /mail/send.do` → `KibsMngServiceImpl.processMailSend`, 응답 `{"status":"0"}`이 성공.

## 커밋 규칙
- 메시지: `YYYY.MM.DD : 요청사항 수정 적용` 한 줄. 커밋·푸시는 사용자가 요청할 때만.
- `.claude/`는 커밋하지 않는다.

## 알려진 이슈 / 백로그 (미수정)
- 2026-10-15 ~ 2026-10-23 사이 등록된 참관객·참가업체 개인정보는 이중(일부 삼중) 암호화 상태일 수 있음. 조회는 safeDecrypt로 되지만 관리자 이메일/연락처 **검색은 매칭되지 않음**. 데이터 마이그레이션 필요.
- 업로드/다운로드 물리 경로 하드코딩(`/usr/local/tomcat/webapps/...`). 설정값으로 빼는 것이 바람직.
- 로깅: `System.out.println` 78곳, `printStackTrace` 134곳. 로거는 서비스 2개만 사용.
- `src/test/java/dbTest/MySQLConnectionTest.java`에 운영 DB 계정/비밀번호 평문 커밋됨.
- 2026 관리자(exhibitorNew.js) 승인 메일은 국문 템플릿(171)만 사용.
- 공개 사이트 LOGOUT은 세션 전체를 초기화하므로 관리자 세션 기반 테스트 중 로그아웃하면 다시 관리자 로그인 필요.
- 서버 `application.properties`에 `security.trust-proxy-headers=false` 항목 추가 권장(없어도 기본 false).
- AI 클리핑 호 간 중복 제외는 삭제(del_yn=Y)된 클리핑을 세지 않는다. 테스트 글을 지우면 그 기사가 다시 선정 가능해야 자연스럽다는 사용자 확정 설계(2026-10-26). 바꾸지 말 것.
- AI 클리핑 V2 미확정: 월 10:00 까지 미승인 글 처리(현재는 발송 안 함, 다음 월요일에도 "당일 생성" 조건 때문에 자동 발송되지 않음 → 관리자가 "즉시 게시"+"발송"으로 수동 처리), 월요일 공휴일 처리(현재 스케줄은 공휴일 무시), 관련 기사 영역 메일 포함 여부(현재 웹만), GPT 0점 기사 노출 여부(현재 비노출), 태그 목록(현재 설정값 고정). 발주사 회의 결과에 따라 조정.
- AI 클리핑 V2 Daum 검색의 `period=u&sd=&ed=` 기간 파라미터는 실제 필터 효과 미검증(무시되더라도 날짜 필터로 안전). 7일 이상 지난 기사는 날짜만 표기되어 08:00 경계는 날짜 단위로만 적용.
- AI 클리핑 V2 운영 전환 절차: 서버 `application.properties`에 `ai.clipping.pipeline=v2` 추가(없으면 legacy) → 재기동 → 관리자 수동 생성(발송 N)으로 1회 검수. 관리자 화면 상단 "자동화 운영 가이드" 문구는 V2 기준으로 미리 변경됨(2026-10-28, legacy 운영 중에는 안내가 실제 흐름과 다름 → 전환을 미루지 말 것). 월 10:00 자동 발행(`publishToday`)은 실제 발송이 따라와 로컬 미검증 — 전환 첫 주 월요일 10:00 결과 확인할 것.
- AI 클리핑 키워드 보강은 관리자 화면에서 수동 등록 필요(예: "해양관광 정책", "마리나 개발", "해양레저 지원사업"). 관리자 수동 기사 추가 기능은 없음(수동 생성은 전체 재수집).

## 진행 중 요청 (미확정)
- **2026-10-28 V2 기반 작업 완료(커밋 완료·미배포, 운영 DB DDL 적용 완료)**: 사용자 지시 "기반 작업 시작"에 따라 아래 기본값으로 구현. 관련 기사 영역 웹만 / GPT 0점 기사 비노출 / 태그 3대 축 기반 고정 목록 / 승인 필수는 설정값(기본 true) / 금요일 legacy 흐름은 `ai.clipping.pipeline` 전환 전까지 유지. 발주사 ⑥(승인·공휴일) 답변 후 `publishToday` 조건과 공휴일 처리만 조정하면 됨.
- 2026-10-27 발주사 "뉴스클리핑 수정요청_261027.pptx": AI 클리핑을 유료 정기구독 상품으로 고도화. 요청 4항 — (1) 키워드 수만큼 1:1 발행 + **연예/정치/사건사고 포함**, (2) 유사 기사 이슈 그룹화 후 보도 건수 많은 이슈 우선·주요 언론사 우선, (3) 키워드 기사 부재 시 미발행, 전체 0건이면 유사 키워드 자동 생성, (4) 기사별 메타(원본 키워드·검색 키워드·제목·언론사·발행일·URL) 저장 + 관리자 기사 교체. (1)의 배제 해제는 2026-10-26 합의(배제 유지)·선거 규칙과 충돌, (3)의 미발행은 현행 자동 보충과 충돌, (4)는 기사 단위 테이블 신설 필요. 발주사 확답 전까지 코드 수정 금지.
- 2026-10-28 발주사 2차 요청(텍스트): 월 08:00 다음뉴스 검색(기간 1주·정확도순) 키워드별 1페이지 수집 → 월 10:00 자동 노출·발송 / 링크 완전중복 제거 + 말머리 제거 후 제목 유사도 80% 이상 제거 / 전처리 목록 전체를 기사 리스트로 노출 / 제목+요약문으로 '레저보트산업' 연관도 점수 → 상위 10건 본문 수집(해난구조·해외관광지/브랜드·연예가십정치·군함 0점) / 10건 중복 판단 후 최대 5토픽 브리핑(2~3개도 허용). **10/27 PPT(키워드 1:1 발행)와 정면 충돌**, 10/26 자동 보충과 충돌, 검수 창(금 생성→월 발행) 소멸, 연관도 기준 '레저보트산업'이 해양관광 정책 기사 포함 요구(10/23~26)와 충돌. 어느 요청이 유효한지 확답 전 코드 수정 금지.
- 2026-10-28 사용자 확정 답변: 10/27 PPT(키워드 1:1) 철회, 10/28 요청으로 대체. 월 08:00 생성 → 관리자 검수 → 월 10:00 노출+발송. **발송 조건 미확정**: 사용자는 내부적으로 "승인된 글만"을 원했으나 발주사에는 "승인 여부와 무관하게 10시 자동 발송, 검수는 발주사가 그 사이에"로 제안하고 검수 가능 여부 확답을 요청함(2026-10-28 오후). 확답에 따라 승인 플래그 추가 여부 결정(현행 "게시(승인)" 버튼은 즉시 노출이라 구분 필요).
- 2026-10-28 발주사 2차 답변(확정): ① 프롬프트 3대 축 = 레저선박·장비(모터보트·요트·선외기·제트스키·카약·전기/수소 레저보트·부품소재) / 마리나·계류 인프라(마리나 항만 개발·선착장·레저선박 보관·해양레저 안전 법제화) / 해양관광·레저문화(서핑·스쿠버·해양레저 축제·지자체 해양레저관광 정책). ② GPT 전 제목 키워드 블랙리스트: 여야·탄핵·선거·출마·총선·군함·잠수함·당대표·원내대표·카지노·마리나베이샌즈. ③ 토픽 타이틀 = `[분야 태그]` + 30자 이내 명사형(예: [마리나 인프라] 경기도, 해양레저 거점 육성 위해 계류시설 500선석 확충). ④ 브리핑+출처 하단에 "최신 관련 기사" 영역 신설: 전처리 통과 기사 전부(수십~백여 건) 제목/링크/날짜/언론사만(요약문 미공개). ⑤ 정부·지자체 정책 발표 포함, 정당·후보·정치인 개인 기사만 배제. ⑥ 검수·승인, 월요일 공휴일은 발주사 회의 후 전달 예정. ⑦ 레이아웃은 기존(출처/타이틀/기사) 유지. **남은 확인**: 관련 기사 영역을 메일에도 넣을지(웹만 권장), 영역에 GPT 0점(사고·연예) 기사도 보일지, 태그 목록 고정 여부, 출처에 상위 10건 전부 표시 여부, 해외 제조사 기사 포함(3대 축상 포함으로 해석). 연관도 기준 = 레저보트산업 + 해양레저/해양관광 산업 + 해양관광 정책 기사 포함. 선거 90일 규칙 유지. 배제 = 해양 사고·구조, 해외 관광지 브랜드, 연예/가십/정치, 군함. 수집 창 = 지난 월 08:00 ~ 이번 월 08:00. **미확정**: "정부/지자체 정책 발표 제외"가 정치 배제의 예외(=포함)인지 실제 제외인지(3번 답변과 상충), 해외 보트 브랜드 기사 포함 여부, 전체 기사 리스트 노출 위치·요약문 공개 여부, 10:00까지 미승인/월요일 공휴일 처리.

## 변경 이력
- 2026-10-28 (커밋 2026.10.28, V2 2차): V2 로컬 발행 테스트(운영 DB에 DDL 적용 후, `AI_CLIPPING_PIPELINE=v2` 환경변수로 기동, 관리자 수동 생성 발송 N). seq 70: 수집 373 → 전처리 264(블랙리스트 16 = 이스라엘 총선·미 중간선거) → 통과 163 → 토픽 4. 발견 3건 수정 — ① 관련 기사 언론사명이 "언론사"로 나옴(Daum 검색 결과의 언론사는 `.c-item-content` 바깥 `li > .c-tit-doc .tit_item .txt_info`에 있음 → li 로 올라가 읽도록), ② 5~10점 잡음(증시·전국체전) 목록 노출(채점 프롬프트에 "해양레저와 무관한 기사 0점" 명시 + `min-score` 기본 10), ③ 한 사안(부산 해양모빌리티 엑스포)이 상위 10건 중 6건 차지(본문 수집 대상 선정 시 같은 사안 최대 `max-per-issue` 건, `pickDiverseTop`). seq 71 재생성: 통과 141 → 토픽 5(마리나 인프라/전시·행사/해양관광×2/해양레저 정책), 언론사명 미확인 0건, 승인 토글 Y↔N 정상, 공개 상세 관련 기사 141건 표시. 두 글 모두 삭제. 월 10:00 `publishToday`는 실제 구독자 발송이 따라와 로컬에서 미실행(쿼리만 검토). 점수 분포 참고: LISTED 131건 중 10~19점 40건(해상풍력·조선 인력·아트센터 인터뷰 등 주변 기사). **발주사 방침(2026-10-28, 김충환 위원): "엉뚱한 기사가 섞이더라도 필요한 기사를 놓치면 안 된다. 처음부터 보수적으로 잡지 말고 운영하며 프롬프트에 금지 유형을 추가하는 방향"** → `min-score` 기본값을 1로 되돌렸다가, 사용자 확정("사고·연예 제외는 확실하고, 아예 상관없는 기사는 여지없이 제외")으로 채점 프롬프트에 무관 기사 0점 규칙(키워드·지역명 우연 일치 포함, 1~9점 사용 금지, 1점 이상은 연결 근거 필수)을 강화하고 `min-score` 기본 10(안전망)으로 재조정. 배제 기준 조정은 `AiClippingPipelineV2.requestScores` 의 0점 규칙과 `ai.clipping.title-blacklist` 에 유형을 추가하는 방식으로, 점수 컷 상향은 최후 수단.
- 2026-10-28 (커밋 2026.10.28, V2 1차): AI 클리핑 V2 기반 — DDL `sql/20261028_ai_clipping_v2.sql`(ai_clipping.approve_yn, ai_clipping_article), `AiClippingArticleDTO`, 매퍼 5구문(updateApproveStatus/selectTodayPendingClippings/insertClippingArticle/selectClippingArticlesForList/selectClippingArticlesAll) + 목록·상세·최신 조회에 approveYn, `AiClippingSupport`(callOpenAI/parseArticleDate/normalizeLink/stripTitlePrefix/similarity/fetchArticle/buildElectionRule/loadRecentlyUsed), `AiClippingPipelineV2`(수집→전처리→연관도→본문→토픽→렌더→저장, publishToday), `AiClippingScheduler` 플래그 분기(`ai.clipping.pipeline`, 월 10:00 `publishApprovedAt10AM`, `processAiClipping` 위임), 관리자 `approve.do`/`articles.ajax` + clipping.js 승인 토글(ver=20261028), 공개 상세 `relatedArticles` "최신 관련 기사" 영역. 로컬 application.properties 에 설정 블록 추가(legacy 유지).
- 2026-10-23 (커밋 9b0c24b): 발주사 요청 3건 — 관리자 참관객 상세 복호화, 영문 승인 메일 템플릿(201) 분기, 인보이스 정적 첨부(통장사본/사업자등록증) 제거. 참관객·참가업체 저장 시 수동 암호화 제거(이중 암호화 원인) + 사전 조회용 키 DTO 분리.
- 2026-10-26 (커밋 07ddd26): seq 68 검수 후 후속 — 1~3차는 최근 30일 기사만 선정하고 4차에서만 이전 기사 허용(`collectArticles` allowOlder 스위치, 조건별 탈락 키워드 기록), 최근 호 출처 제목을 프롬프트에 "이미 다룬 사안"으로 전달해 같은 사안의 다른 언론사 기사 배제(규칙 7, `loadRecentlyUsed`가 링크+제목 추출, 조회는 최신순).
- 2026-10-26 (커밋 00fdd4e): seq 67 검수 후 후속 — 최근 60일 내 클리핑에 실린 기사 링크를 다음 생성 후보에서 제외(호 간 중복 방지, `selectRecentClippingContents` 매퍼 추가, `ai.clipping.dedupe-days`). 목표 5건 미달 시 자동 보충(2차 같은 키워드 추가 선정, 3차 보조 키워드) 구조로 수집 루프를 `collectArticles`/`CollectContext`로 재구성.
- 2026-10-26 (커밋 68ce648): seq 66 검수 후 후속 — 미노출/삭제 클리핑은 관리자 세션이 아니면 상세 URL 차단(목록으로 리다이렉트), 후보 기사 날짜 파싱 후 최근 30일(`ai.clipping.recent-days`) 이내 기사 우선 선정 규칙 추가, 기사 날짜를 수집 시점 절대 날짜로 고정(본문 출처 날짜 변환도 공용 파서 사용), 선거 90일 전 후보자 기사 배제 규칙(`ai.clipping.election-dates`, `election-window-days`) 추가. 정책 기사 포함은 유지 결정.
- 2026-10-26 (커밋 93699e8): 경기도청 "해양레저 기사가 클리핑에 안 잡힌다" 문의 대응. 원인 = 키워드당 최신 3건만 후보 + 사건사고/연예 배제 규칙(규칙은 유지 결정). 개선: 후보 수를 설정값 기반 페이지네이션으로 확대, 선정 기사만 언론사명 조회, 키워드 간 중복 기사 제거, 필터 프롬프트에 정부·지자체 해양관광 정책 기사 합격 명시 및 연예인 요트 기사 배제 명시.
- 2026-10-23 (커밋 92c8e4f): 테스트에서 발견한 5건 — `mode=open` 우회 차단(ApplyPeriod/TesterAccessChecker, 서버 기간 검사), 참관객 마이페이지 seq 직접 접근 차단(세션 visitorSeq), X-Forwarded-For 신뢰 설정화(ClientIpResolver), 2027 업체 상세 lang NPE, 업로드 실패 응답/안내. 기간 외 테스트는 접근 IP 화이트리스트 기준으로 허용.
