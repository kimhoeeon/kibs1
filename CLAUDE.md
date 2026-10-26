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
- AI 뉴스 클리핑(`scheduler/AiClippingScheduler`): 금 08:00 생성(미노출) → 월 08:00 노출+구독자 발송. 선정 로직 = 관리자 등록 키워드(ai_clipping_keyword)를 섞어 순회 → 키워드당 Daum 뉴스 최신순 후보 `daum.search.articles-per-keyword`건(기본 10, 최대 5페이지 페이지네이션, 페이지 간 `daum.search.list-delay-*` 딜레이, 다른 키워드에서 이미 선정된 기사와 최근 `ai.clipping.dedupe-days`(기본 60)일 내 생성된 클리핑(미노출 포함) 본문의 href 링크에 있는 기사 제외 — 링크는 쿼리스트링·끝 슬래시 제거 후 비교) → GPT 필터(사건사고·구조·조난·해경 적발, 정치인 가십·선거, 연예인 사생활, 부동산 등 **무조건 배제**; 산업·기술·전시회·비즈니스·정책 기사 합격; 정부·지자체 해양레저/해양관광 정책·인프라·공식행사 기사는 정치인이 등장해도 합격; 키워드당 1건) → 기사 날짜는 수집 시점에 절대 날짜(yyyy.MM.dd)로 고정(상대 표기 "N시간 전/어제/N주 전/N개월 전"을 `parseArticleDate`로 변환, 본문 저장 후 재계산 없음) → 후보에 날짜와 [최근]/[이전] 표시를 붙여 `ai.clipping.recent-days`(기본 30) 이내 기사를 우선 선택(이내 기사가 모두 탈락할 때만 이전 기사 허용) → 선거 규칙: 후보자·예비후보자·출마예정자 기사는 항상 배제, `ai.clipping.election-dates`(기본 2028-04-12 총선, 2030-06-03 대선·지방선거)의 `ai.clipping.election-window-days`(기본 90)일 전부터는 현직 단체장·의원 공약성/치적 기사도 배제하고 기관 명의 정책 공고만 합격 → 선정 기사 1건만 본문 접속해 언론사명 확정 → 목표 5건 도달 시 중단. **목표 미달 시 자동 보충**: 2차로 같은 키워드에서 추가 선정(크롤링 캐시 재사용, 키워드당 누적 `ai.clipping.max-picks-per-keyword`(기본 2)건), 3차로 보조 키워드(`ai.clipping.fallback-keywords`, 기본 해양레저산업·마리나·요트·보트쇼·해양관광 등, 관리자 풀과 중복 제외) 수집 → 뉴스레터 HTML 생성. 관리자 `/mng/center/board/clipping.do`에서 수동 생성·키워드 관리 가능. **배제 조건은 발주사·사용자 합의로 유지(2026-10-26).** 공개 상세 `/board/clippingView.do`는 display_yn=Y 글만 열리고, 미노출·삭제 글은 관리자 세션이 아니면 목록으로 리다이렉트.

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
- AI 클리핑 키워드 보강은 관리자 화면에서 수동 등록 필요(예: "해양관광 정책", "마리나 개발", "해양레저 지원사업"). 관리자 수동 기사 추가 기능은 없음(수동 생성은 전체 재수집).

## 변경 이력
- 2026-10-23 (커밋 9b0c24b): 발주사 요청 3건 — 관리자 참관객 상세 복호화, 영문 승인 메일 템플릿(201) 분기, 인보이스 정적 첨부(통장사본/사업자등록증) 제거. 참관객·참가업체 저장 시 수동 암호화 제거(이중 암호화 원인) + 사전 조회용 키 DTO 분리.
- 2026-10-26 (미커밋): seq 67 검수 후 후속 — 최근 60일 내 클리핑에 실린 기사 링크를 다음 생성 후보에서 제외(호 간 중복 방지, `selectRecentClippingContents` 매퍼 추가, `ai.clipping.dedupe-days`). 목표 5건 미달 시 자동 보충(2차 같은 키워드 추가 선정, 3차 보조 키워드) 구조로 수집 루프를 `collectArticles`/`CollectContext`로 재구성.
- 2026-10-26 (커밋 68ce648): seq 66 검수 후 후속 — 미노출/삭제 클리핑은 관리자 세션이 아니면 상세 URL 차단(목록으로 리다이렉트), 후보 기사 날짜 파싱 후 최근 30일(`ai.clipping.recent-days`) 이내 기사 우선 선정 규칙 추가, 기사 날짜를 수집 시점 절대 날짜로 고정(본문 출처 날짜 변환도 공용 파서 사용), 선거 90일 전 후보자 기사 배제 규칙(`ai.clipping.election-dates`, `election-window-days`) 추가. 정책 기사 포함은 유지 결정.
- 2026-10-26 (커밋 93699e8): 경기도청 "해양레저 기사가 클리핑에 안 잡힌다" 문의 대응. 원인 = 키워드당 최신 3건만 후보 + 사건사고/연예 배제 규칙(규칙은 유지 결정). 개선: 후보 수를 설정값 기반 페이지네이션으로 확대, 선정 기사만 언론사명 조회, 키워드 간 중복 기사 제거, 필터 프롬프트에 정부·지자체 해양관광 정책 기사 합격 명시 및 연예인 요트 기사 배제 명시.
- 2026-10-23 (커밋 92c8e4f): 테스트에서 발견한 5건 — `mode=open` 우회 차단(ApplyPeriod/TesterAccessChecker, 서버 기간 검사), 참관객 마이페이지 seq 직접 접근 차단(세션 visitorSeq), X-Forwarded-For 신뢰 설정화(ClientIpResolver), 2027 업체 상세 lang NPE, 업로드 실패 응답/안내. 기간 외 테스트는 접근 IP 화이트리스트 기준으로 허용.
