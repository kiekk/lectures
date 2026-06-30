# 💳 견고한 결제 시스템 구축

> 인프런 [견고한 결제 시스템 구축] 강의로 학습한 내용을 정리한 공간입니다.
>
> 강의 링크: [견고한 결제 시스템 구축](https://biz.inflearn.com/course/%EA%B2%AC%EA%B3%A0%ED%95%9C-%EA%B2%B0%EC%A0%9C-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%B5%AC%EC%B6%95/dashboard?cid=333243)

<img src="https://cdn.inflearn.com/public/courses/333243/cover/274f2018-cf6e-44c2-a3e3-bad8eb6540ec/333243.png?w=736&f=avif" alt="견고한 결제 시스템 구축" width="400px">

<sub>※ 이미지 출처: 인프런</sub>

---

## 📑 목차

- [환경 정보](#환경-정보)
- [실행 방법](#실행-방법)
- [학습 내용](#학습-내용)

---

## 🛠️ 환경 정보

- **Language**: Kotlin `2.3.21`
- **JVM**: Java `17`
- **Framework**: Spring Boot `4.1.0`
- **Build Tool**: Gradle (Kotlin DSL)
- **IDE**: IntelliJ

### 주요 의존성

- Spring WebFlux (Reactive Web)
- Spring Data R2DBC
- MySQL (`r2dbc-mysql`, `mysql-connector-j`)
- Thymeleaf
- Kotlin Coroutines (`kotlinx-coroutines-reactor`)
- Jackson Module Kotlin

---

## 🚀 실행 방법

### 1. MySQL 실행 (Docker)

```bash
docker compose up -d
```

DB 접속 정보:

| 항목       | 값         |
|----------|-----------|
| host     | localhost |
| port     | 3306      |
| database | test      |
| user     | root      |
| password | 1234      |

DB 초기화가 필요하면:

```bash
docker compose down -v
```

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

### 3. 빌드 / 테스트

```bash
./gradlew build      # 빌드 + 테스트
./gradlew test       # 테스트만
```

---

## 📚 학습 내용

### 섹션 1. 강의 소개와 자료 (3개 ∙ 4분)

- [ ] 1\. 강의 소개 (04:35)
- [ ] 2\. 수업 자료
- [ ] 3\. 실습 코드

### 섹션 2. Payment System 설계 (1개 ∙ 32분)

- [ ] 4\. Payment System 설계 (32:59)

### 섹션 3. 결제 승인 기능 구현 (6개 ∙ 2시간 10분)

- [ ] 5\. 실습 준비 (05:09)
- [ ] 6\. Toss Payments 결제 연동 (14:41)
- [ ] 7\. Payment Service 데이터 모델링 (08:56)
- [ ] 8\. 가상의 Checkout 기능 구현 (29:19)
- [ ] 9\. 결제 승인 기능 구현 (47:13)
- [ ] 10\. 결제 승인 에러 핸들링 (feat: Retry, Timeout) (25:07)

### 섹션 4. 결제 복구 기능 구현 (1개 ∙ 24분)

- [ ] 11\. 결제 복구 서비스 (feat: Bulk Head, Parallel Processing) (24:36)

### 섹션 5. 결제 승인 메시지 발행 (3개 ∙ 1시간 6분)

- [ ] 12\. Confluent Kafka 연동 (10:11)
- [ ] 13\. 결제 승인 메시지 발행 (feat: Transactional Outbox Pattern, Apache Kafka) (37:45)
- [ ] 14\. 신뢰성 있게 카프카를 사용하는 방법 (18:58)

### 섹션 6. Wallet Service 구축 (4개 ∙ 1시간 19분)

- [ ] 15\. Wallet Service 구축 (45:15)
- [ ] 16\. Wallet Service 동시성 제어 (feat: Optimistic Locking) (14:00)
- [ ] 17\. Wallet Service 메시지 처리와 전달 보장 (feat: Kafka Transaction) (10:16)
- [ ] 18\. Wallet Service 신뢰성 향상 (feat: Dead Letter Queue) (10:15)

### 섹션 7. Ledger Service 구축 (1개 ∙ 39분)

- [ ] 19\. Ledger Service 구축 (feat: Double-Entry Ledger, Trigger) (39:55)

### 섹션 8. 결제 완료 기능 구현 (1개 ∙ 29분)

- [ ] 20\. 결제 완료 기능 구축 (29:56)

### 섹션 9. 마무리 (1개 ∙ 19분)

- [ ] 21\. 더 견고해지기 위해 남은 작업 (19:19)
