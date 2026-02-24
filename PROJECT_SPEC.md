# 프로젝트 명세서: vibeapp

이 문서는 기능을 갖춘 스프링부트 애플리케이션인 **vibeapp**의 기술 사양과 구조를 정의합니다.

## 1. 프로젝트 개요
- **목표**: 게시판 기능을 포함한 표준 스프링부트 웹 애플리케이션 구현
- **프로젝트 구조**: 기능형(Feature-based) 패키지 구조
- **설정 형식**: YAML (`application.yml`)

## 2. 환경 및 도구
| 구성 요소 | 요구 사양 |
| :--- | :--- |
| **JDK** | JDK 25 이상 |
| **언어** | Java |
| **스프링 부트** | 4.0.1 이상 |
| **빌드 도구** | Gradle 9.3.0 이상 |
| **빌드 스크립트** | Groovy DSL |

## 3. 프로젝트 구조 (기능형)

### Java 패키지 구조
`src/main/java`
- `com.example.vibeapp`
    - `VibeApp.java`: 애플리케이션 실행 메인 클래스
    - `home/`: 홈/인덱스 관련 도메인
        - `HomeController.java`
    - `post/`: 게시글 관련 도메인
        - `Post.java` (Entity)
        - `PostController.java`
        - `PostService.java`
        - `PostRepository.java` (Collection 기반 메모리 저장소)

### 뷰 템플릿 구조
`src/main/resources/templates`
- `home/`: 홈 관련 템플릿 (`home.html`)
- `post/`: 게시글 관련 템플릿 (`posts.html`, `post_detail.html`, `post_new_form.html`, `post_edit_form.html`)

## 4. 주요 기능
- **게시글 목록 조회**: 최신순 정렬 및 페이지당 5개씩 페이징 처리
- **게시글 상세 조회**: 게시글 내용 확인 및 조회수 자동 증가 기능
- **게시글 등록**: 새 게시글 작성 및 저장
- **게시글 수정**: 기존 게시글 제목/내용 수정 및 수정일 자동 반영
- **게시글 삭제**: 게시글 삭제 처리 및 목록 리다이렉트
- **기타**: 디자인 시스템 적용 및 반응형 UI (Thymeleaf + CSS)

## 5. 의존성 및 플러그인
### 플러그인
- `org.springframework.boot`
- `io.spring.dependency-management`
- `java`

### 의존성
- `spring-boot-starter-web`: 웹 기능 활성화 및 내장 톰캣 포함
- `spring-boot-starter-thymeleaf`: Thymeleaf 뷰 엔진

## 6. 설정 방식
애플리케이션 설정은 YAML 파일을 사용합니다.
- 파일 위치: `src/main/resources/application.yml`
