# ☘️ [스프링부트로 직접 만들면서 배우는 대규모 시스템 설계 - 게시판]

> 정수원님의 [스프링부트로 직접 만들면서 배우는 대규모 시스템 설계 - 게시판] 인프런 강의로 학습한 내용을 정리한 공간입니다.  
> 강의 링크: [스프링부트로 직접 만들면서 배우는 대규모 시스템 설계 - 게시판](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8%EB%A1%9C-%EB%8C%80%EA%B7%9C%EB%AA%A8-%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%84%A4%EA%B3%84-%EA%B2%8C%EC%8B%9C%ED%8C%90/dashboard)


<img src="https://cdn.inflearn.com/public/courses/334365/cover/ef04bc2b-b006-4152-bfa2-704dbed57aa0/334365.png" width="400px">

<sub>※ 이미지 출처: 인프런</sub>

---

## 📚 목차

- [섹션 2. 게시글](md/article/README.md)
- [섹션 3. 댓글](md/comment/README.md)
- [섹션 4. 좋아요](md/like/README.md)
- [섹션 5. 조회수](md/view/README.md)
- [섹션 6. 인기글](md/hotarticle/README.md)
- [섹션 7. 게시글 조회 최적화](md/articleread/README.md)
- [번외. jpa show_sql 성능 최적화](md/jpa/README.md)

---

## 🛠️ 환경 정보

- Java: `21`
- Spring Boot: `3.4.5`
- Build Tool: `Gradle 8.13`
- IDE: `IntelliJ`

---

## 📝 더 나아가기
- [ ] `request` 객체 유효성 검증 로직 추가해보기
- [ ] 서비스 수준에서 `CQRS` 패턴 적용해보기
  - `article`, `comment`, `like`, `view` 서비스에서 `command` 작업과 `query` 작업을 분리해보기
  - ex: `article-query-service`, `article-command-service`
- [ ]`article-read`, `hot-article` 서비스에서 `consumer`와 `controller`를 분리해보기
  - `consumer(쓰기)`와 `controller(읽기)`가 같은 서비스 안에 존재한다.
- [ ] 인증 기능 추가해보기
  - 서비스 자체 로그인/회원가입
  - OAuth Google, Kakao, Naver...
- [ ] 여러 개의 DB를 구축하여 샤딩 구현해보기
- [ ] Dockerfile로 이미지를 생성 후 이미지로 배포해보기
- [ ] Spring Cloud 적용해보기
  - config
  - eureka
  - zuul
  - ribbon
  - turbine
  - hystrix
  - feign
- [ ] k6로 부하 테스트 해보기 
- [ ] 모니터링 환경 구축해보기