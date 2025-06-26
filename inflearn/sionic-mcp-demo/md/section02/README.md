## 섹션 2. IntelliJ ⫘⫘⫘ Claude App MCP 연동하기


### Step 1: 기본 URL 단축 서비스 구현
- urlShortener 서비스를 만들기
- POST /api/shorten 구현
  - 문자와 숫자로만 된, 문자로 시작하는 랜덤 6자리 문자열 키를 생성해서
  - in-memory hashmap 에 저장하고 200 OK와 함께 단축 URL 반환
- GET /api/shorten/{shortKey} 구현
  - 엔드포인트로 단축 키를 받으면 해당하는 원본 URL 반환
- Controller, Service, Repository 레이어로 구조화하기
- 편의성 기능 추가:
  - 수동으로 테스트할 수 있는 http-client 파일 생성
  - 주요 흐름에 info 로그 추가
  - 로깅은 io.github.oshai:kotlin-logging-jvm dependency 사용

### Step 2: env 별로 데이터 저장소 분리
- local env 일때는 지금의 로직을 유지하고
- local_postgres env 일 때는 exposed를 쓰고 싶어, dependency 추가해줘
- 접속 정보는 없어서 local 용으로 접속 정보 생성하고 이를 기반으로 docker/docker-compose.yml 작성해줘
- src/main/resources/application.yml에 env 추가
- local env: 현재 in-memory hashmap 사용 유지
- local_postgres env: exposed를 사용하여 postgresSQL db 연결
  - docker/docker-compose.yml의 postgresSQL 접속 정보 활용 

### Step 3: Kotlin Multiplatform 관리자 화면 구현
- 기능 구현:
  - URL 입력 폼: 긴 URL 입력 필드와 '단축하기' 버튼
  - URL 목록 표시: 단축 키, 원본 URL, 생성 시간 표시
  - 삭제 기능: 각 URL 항목에 삭제 버튼 추가
- 추가 API 엔드포인트:
  - GET /api/shorten/urls: 모든 단축 URL 목록 조회
  - DELETE /api/shorten/{shortKey}: 단축 키로 URL 삭제
- 동작 흐름:
  - POST, DELETE 작업 완료 후 자동으로 URL 목록 갱신
  - 페이지 로드 시 초기 URL 목록 코드