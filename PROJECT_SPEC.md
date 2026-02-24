# 프로젝트 명세서: vibeapp

이 문서는 현재 `vibeapp` 프로젝트의 구현 상태를 기준으로 기술 스펙과 기능 범위를 정리합니다.

## 1. 프로젝트 개요
- 목표: 게시글 CRUD 및 태그 관리 기능을 제공하는 Spring Boot 웹 애플리케이션 구현
- 아키텍처: 기능(Feature) 중심 패키지 구조
- 뷰 렌더링: Thymeleaf 기반 서버사이드 렌더링
- 설정 파일: `application.yml` (YAML)

## 2. 개발 환경 및 도구
| 항목 | 사양 |
| :--- | :--- |
| JDK | Java 25 |
| Framework | Spring Boot 4.0.1 |
| Build Tool | Gradle |
| Build Script | Groovy DSL |
| DB | H2 Database (File Mode) |
| Persistence | MyBatis |

## 3. 프로젝트 구조

### 3.1 Java 패키지
`src/main/java/com/example/vibeapp`
- `VibeApp.java`
  - Spring Boot 애플리케이션 진입점
  - 트랜잭션 관리 활성화(`@EnableTransactionManagement`)
- `home/`
  - `HomeController.java`
- `post/`
  - `Post.java` (게시글 엔티티)
  - `PostTag.java` (게시글 태그 엔티티)
  - `PostController.java`
  - `PostService.java`
  - `PostRepository.java` (MyBatis Mapper 인터페이스)
  - `PostTagRepository.java` (MyBatis Mapper 인터페이스)
  - `dto/`
    - `PostCreateDto.java`
    - `PostUpdateDto.java`
    - `PostListDto.java`
    - `PostResponseDto.java`

### 3.2 리소스 구조
`src/main/resources`
- `application.yml`
- `schema.sql`
- `data.sql` (파일은 유지, 자동 초기화는 비활성화)
- `mapper/post/`
  - `PostMapper.xml`
  - `PostTagMapper.xml`
- `templates/`
  - `home/home.html`
  - `post/posts.html`
  - `post/post_detail.html`
  - `post/post_new_form.html`
  - `post/post_edit_form.html`

## 4. 데이터베이스 설계

### 4.1 POSTS
- `NO` (PK, 자동 증가)
- `TITLE` (VARCHAR(200), NOT NULL)
- `CONTENT` (CLOB, NOT NULL, 최대 10MB 체크 제약)
- `CREATED_AT` (TIMESTAMP, 기본값 CURRENT_TIMESTAMP)
- `UPDATED_AT` (TIMESTAMP, 기본값 NULL)
- `VIEWS` (INT, 기본값 0)

### 4.2 POST_TAGS
- `ID` (PK, 자동 증가)
- `POST_NO` (FK, `POSTS.NO` 참조, NOT NULL)
- `TAG_NAME` (VARCHAR(50), NOT NULL)

## 5. 설정 현황

### 5.1 DataSource / H2
- URL: `jdbc:h2:file:./data/testdb`
- H2 Console 활성화: `/h2-console`
- H2 콘솔 서블릿 직접 등록(`H2ConsoleConfig`)

### 5.2 MyBatis
- Mapper XML 위치: `classpath:mapper/**/*.xml`
- Type Alias 패키지: `com.example.vibeapp.post`
- Camel Case 매핑: 활성화

### 5.3 SQL 초기화
- `spring.sql.init.mode=always`
- `schema.sql` 실행: 활성화
- `data.sql` 자동 실행: 비활성화(파일은 유지)

### 5.4 로깅/디버그
- `debug: true`
- `org.springframework.boot.autoconfigure: DEBUG`
- 로그 파일: `./logs/app.log`

## 6. 주요 기능
- 게시글 목록 조회(페이징)
- 게시글 상세 조회(조회수 증가)
- 게시글 등록/수정/삭제
- 태그 입력(쉼표 구분) 및 저장
- 게시글 수정 시 태그 재설정(기존 태그 삭제 후 재등록)
- 게시글 상세 페이지에서 제목 아래 태그 표시

## 7. 트랜잭션 정책
- 트랜잭션 관리 활성화: `@EnableTransactionManagement`
- `PostService#create(...)`: 게시글 등록 + 태그 등록을 단일 트랜잭션으로 처리
- `PostService#update(...)`: 게시글 수정 + 태그 수정(삭제/재등록)을 단일 트랜잭션으로 처리

## 8. UI/템플릿 정책
- `posts.html`의 상단 고정 메뉴/푸터 레이아웃을 기준으로
  - `post_new_form.html`
  - `post_edit_form.html`
  - `post_detail.html`
  에 동일한 네비게이션/푸터를 유지
- 페이지별 변경 영역은 `main` 콘텐츠 영역으로 한정
