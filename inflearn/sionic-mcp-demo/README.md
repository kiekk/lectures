# ☘️ [[Sionic MCP 시리즈 1] Model Context Protocol 을 이용하여 IntelliJ 와 코딩해보자!]

> Sionic AI님의 [[Sionic MCP 시리즈 1] Model Context Protocol 을 이용하여 IntelliJ 와 코딩해보자!] 인프런 강의로 학습한 내용을 정리한 공간입니다.  
> 강의 링크: [[Sionic MCP 시리즈 1] Model Context Protocol 을 이용하여 IntelliJ 와 코딩해보자!](https://www.inflearn.com/course/%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%EA%B8%B0%EC%B4%88/dashboard)


<img src="https://cdn.inflearn.com/public/files/courses/336732/cover/01jqn0fg6k2x8bn0qfarc42pjw?f=avif" width="400px">

<sub>※ 이미지 출처: 인프런</sub>

---

## 📚 목차

- [섹션 1. 반가워, MCP와 친해지기 👋](md/section01/README.md)
- [섹션 2. IntelliJ ⫘⫘⫘ Claude App MCP 연동하기](md/section02/README.md)
- 섹션 3. 이제 MCP와 친구 𐦂𖨆𐀪𖠋 가 되었겠지?

---

# URL Shortener Service

간단한 URL 단축 서비스입니다. Spring Boot와 Kotlin으로 구현되었습니다.

## 기능

- URL 단축: 긴 URL을 6자리 랜덤 키로 단축
- URL 조회: 단축된 키로 원본 URL 조회
- 리다이렉트: 단축 URL로 접근 시 원본 URL로 자동 리다이렉트
- 인메모리 저장: ConcurrentHashMap을 사용한 빠른 저장/조회

## API 명세

### POST /api/shorten
URL을 단축합니다.

**Request:**
```json
{
  "originalUrl": "https://www.example.com"
}
```

**Response:**
```json
{
  "shortKey": "a1b2c3",
  "shortUrl": "http://localhost:8080/api/redirect/a1b2c3",
  "originalUrl": "https://www.example.com"
}
```

### GET /api/shorten/{shortKey}
단축 키로 원본 URL을 조회합니다.

**Response:**
```
https://www.example.com
```

### GET /api/redirect/{shortKey}
단축 URL로 접근 시 원본 URL로 리다이렉트합니다.

## ✅ 실행 방법 및 테스트 결과

### Local 환경 (In-Memory) 🚀 **테스트 완료**
```bash
# 기본 환경 (in-memory storage)
./gradlew bootRun

# 또는 명시적으로 local 프로필 지정
./gradlew bootRun --args='--spring.profiles.active=local'
```

**✅ 테스트 성공:**
```bash
# URL 단축 요청
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl": "https://github.com"}'

# 응답: {"shortKey":"xm7MN8","shortUrl":"http://localhost:8080/api/redirect/xm7MN8","originalUrl":"https://github.com"}

# URL 조회 테스트
curl http://localhost:8080/api/shorten/xm7MN8
# 응답: https://github.com
```

### Local PostgreSQL 환경 🐘 **Docker 실행 필요**
```bash
# 1. PostgreSQL 컨테이너 실행 (Docker 필요)
cd docker
docker-compose up -d

# 2. PostgreSQL 연결 확인
docker-compose logs postgres

# 3. 애플리케이션 실행 (PostgreSQL 환경)
./gradlew bootRun --args='--spring.profiles.active=local_postgres'
```

### PostgreSQL 관리
```bash
# 컨테이너 중지
docker-compose down

# 데이터까지 삭제하고 중지
docker-compose down -v

# 컨테이너 로그 확인
docker-compose logs -f postgres
```

## 테스트

IntelliJ IDEA의 HTTP Client를 사용하여 테스트할 수 있습니다.
`http-client/url-shortener.http` 파일을 열고 각 요청을 실행해보세요.

## 기술 스택

- **언어**: Kotlin
- **프레임워크**: Spring Boot 3.2.0
- **로깅**: kotlin-logging-jvm
- **빌드 도구**: Gradle
- **저장소**: 
  - Local: In-Memory (ConcurrentHashMap)
  - Local PostgreSQL: PostgreSQL with Exposed ORM
- **데이터베이스**: PostgreSQL 16 (Docker)
- **ORM**: Jetbrains Exposed

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/urlshortener/
│   │       ├── UrlShortenerApplication.kt  # 메인 애플리케이션
│   │       ├── controller/                 # REST API 컨트롤러
│   │       │   └── UrlShortenerController.kt
│   │       ├── service/                    # 비즈니스 로직
│   │       │   └── UrlShortenerService.kt
│   │       ├── repository/                 # 데이터 접근 레이어
│   │       │   ├── UrlRepository.kt        # 인터페이스
│   │       │   ├── InMemoryUrlRepository.kt # In-Memory 구현
│   │       │   └── PostgresUrlRepository.kt # PostgreSQL 구현
│   │       ├── entity/                     # Exposed 엔티티
│   │       │   └── UrlMappings.kt
│   │       ├── config/                     # 설정 클래스
│   │       │   ├── DatabaseConfig.kt       # PostgreSQL 설정
│   │       │   └── LocalDataSourceConfig.kt # Local DataSource
│   │       └── dto/                        # 데이터 전송 객체
│   │           ├── ShortenRequest.kt
│   │           └── ShortenResponse.kt
│   └── resources/
│       ├── application.yml                 # 환경별 설정
│       └── logback-spring.xml             # 로깅 설정
├── docker/                                 # Docker 설정
│   ├── docker-compose.yml                 # PostgreSQL 컨테이너
│   ├── init.sql                           # DB 초기화 스크립트
│   └── README.md                          # Docker 사용법
└── http-client/
    └── url-shortener.http                 # HTTP 테스트 파일
```

## 환경별 설정

### Local 환경 (기본)
- **Profile**: `local` (기본값)
- **저장소**: In-Memory ConcurrentHashMap
- **특징**: 애플리케이션 재시작 시 데이터 초기화
- **용도**: 빠른 개발 및 테스트

### Local PostgreSQL 환경
- **Profile**: `local_postgres`
- **저장소**: PostgreSQL 데이터베이스
- **특징**: 데이터 영속성 보장
- **용도**: 실제 데이터베이스 연동 테스트

### 환경 전환
```bash
# In-Memory 환경
./gradlew bootRun --args='--spring.profiles.active=local'

# PostgreSQL 환경
./gradlew bootRun --args='--spring.profiles.active=local_postgres'
```
