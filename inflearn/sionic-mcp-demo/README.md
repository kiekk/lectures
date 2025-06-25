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

## 실행 방법

1. 프로젝트 빌드
```bash
./gradlew build
```

2. 애플리케이션 실행
```bash
./gradlew bootRun
```

3. 브라우저에서 http://localhost:8080 접속

## 테스트

IntelliJ IDEA의 HTTP Client를 사용하여 테스트할 수 있습니다.
`http-client/url-shortener.http` 파일을 열고 각 요청을 실행해보세요.

## 기술 스택

- **언어**: Kotlin
- **프레임워크**: Spring Boot 3.2.0
- **로깅**: kotlin-logging-jvm
- **빌드 도구**: Gradle
- **저장소**: In-Memory (ConcurrentHashMap)

## 프로젝트 구조

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/urlshortener/
│   │       ├── controller/     # REST API 컨트롤러
│   │       ├── service/        # 비즈니스 로직
│   │       ├── repository/     # 데이터 접근 레이어
│   │       └── dto/           # 데이터 전송 객체
│   └── resources/
│       ├── application.yml    # 애플리케이션 설정
│       └── logback-spring.xml # 로깅 설정
└── http-client/
    └── url-shortener.http     # HTTP 클라이언트 테스트 파일
```

## 특징

- **문자로 시작하는 6자리 키**: 첫 번째 문자는 반드시 영문자, 나머지는 영문자와 숫자 조합
- **중복 키 방지**: 동일한 키가 생성되면 자동으로 재생성
- **상세한 로깅**: 주요 흐름에 INFO 레벨 로그 추가
- **검증**: URL 형식 검증 및 필수 값 체크
- **리다이렉트**: 브라우저에서 직접 접근 가능한 리다이렉트 엔드포인트
