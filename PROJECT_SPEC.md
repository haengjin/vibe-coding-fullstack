# 프로젝트 명세서: vibeapp

이 문서는 최소 기능을 갖춘 스프링부트 애플리케이션인 **vibeapp**의 기술 사양을 개설합니다.

## 1. 프로젝트 개요
- **목표**: 최소 기능의 스프링부트 애플리케이션 생성
- **프로젝트 구조**: Java 기반의 표준 Gradle 프로젝트
- **설정 형식**: YAML (`application.yml`)

## 2. 환경 및 도구
| 구성 요소 | 요구 사양 |
| :--- | :--- |
| **JDK** | JDK 25 이상 |
| **언어** | Java |
| **스프링 부트** | 4.0.1 이상 |
| **빌드 도구** | Gradle 9.3.0 이상 |
| **빌드 스크립트** | Groovy DSL |

## 3. 프로젝트 메타데이터
- **Group ID**: `com.example`
- **Artifact ID**: `vibeapp`
- **버전**: `0.0.1-SNAPSHOT`
- **메인 클래스명**: `VibeApp`
- **설명**: 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트다.

## 4. 의존성 및 플러그인
### 플러그인
- `org.springframework.boot` (Spring Boot 4.0.1 이상 버전에 대응)
- `io.spring.dependency-management` (Spring Boot 버전에 맞춰 추가)
- `java`

### 의존성
- `spring-boot-starter-web`: 웹 기능 활성화 및 내장 톰캣 서버 포함
- `spring-boot-starter-thymeleaf`: Thymeleaf 뷰 템플릿 엔진 추가

## 5. 설정 방식
애플리케이션 설정은 YAML 파일을 사용합니다.
- 파일 위치: `src/main/resources/application.yml`
