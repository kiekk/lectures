# Docker Setup for URL Shortener

이 디렉토리는 URL Shortener 서비스를 위한 PostgreSQL 데이터베이스 설정을 포함합니다.

## 구성 요소

### docker-compose.yml
- PostgreSQL 16 Alpine 이미지 사용
- 포트: 5432 (호스트) -> 5432 (컨테이너)
- 데이터베이스: `urlshortener`
- 사용자: `urlshortener_user`
- 패스워드: `urlshortener_password`

### init.sql
- 데이터베이스 초기화 스크립트
- `url_mappings` 테이블 생성
- 인덱스 생성 (성능 최적화)
- 샘플 데이터 삽입

## 사용법

### 1. PostgreSQL 시작
```bash
cd docker
docker-compose up -d
```

### 2. 로그 확인
```bash
docker-compose logs -f postgres
```

### 3. 데이터베이스 접속 (선택사항)
```bash
# psql 클라이언트로 접속
docker-compose exec postgres psql -U urlshortener_user -d urlshortener

# 또는 외부에서 접속
psql -h localhost -p 5432 -U urlshortener_user -d urlshortener
```

### 4. PostgreSQL 중지
```bash
# 컨테이너만 중지 (데이터 보존)
docker-compose stop

# 컨테이너 제거 (데이터 보존)
docker-compose down

# 컨테이너와 볼륨 모두 제거 (데이터 삭제)
docker-compose down -v
```

## 데이터베이스 스키마

### url_mappings 테이블
```sql
CREATE TABLE url_mappings (
    id SERIAL PRIMARY KEY,
    short_key VARCHAR(10) UNIQUE NOT NULL,
    original_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 인덱스
- `idx_url_mappings_short_key`: short_key 컬럼 (빠른 조회)
- `idx_url_mappings_created_at`: created_at 컬럼 (분석용)

## 환경 변수

| 변수명 | 값 | 설명 |
|--------|-----|------|
| POSTGRES_DB | urlshortener | 데이터베이스 이름 |
| POSTGRES_USER | urlshortener_user | 데이터베이스 사용자 |
| POSTGRES_PASSWORD | urlshortener_password | 데이터베이스 패스워드 |

## 트러블슈팅

### 포트 충돌
만약 5432 포트가 이미 사용 중이라면:
```yaml
ports:
  - "5433:5432"  # 호스트 포트를 5433으로 변경
```

그리고 application.yml의 URL도 수정:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/urlshortener
```

### 데이터 초기화
데이터를 완전히 초기화하려면:
```bash
docker-compose down -v
docker-compose up -d
```
