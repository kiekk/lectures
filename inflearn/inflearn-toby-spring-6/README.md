# [토비의 스프링 6]

> [토비의 스프링 6] 인프런 강의를 수강하며 학습한 내용을 정리한 공간입니다.  
> 강의 링크: [토비의 스프링 6](https://www.inflearn.com/course/%ED%86%A0%EB%B9%84%EC%9D%98-%EC%8A%A4%ED%94%84%EB%A7%816-%EC%9D%B4%ED%95%B4%EC%99%80-%EC%9B%90%EB%A6%AC/dashboard)


<img src="https://cdn.inflearn.com/public/courses/332188/cover/930648e2-f2a1-4361-935e-dfb760db3eba/332188.png" width="400px">

<sub>※ 이미지 출처: 인프런</sub>

---

## 📚 목차

- 1.강의소개
  - 강의 소개
  - 강사 소개
  - 학습 방법
  - 강의 자료
- 2.스프링 개발 시작하기
  - 개발환경 준비
  - HelloSpring 프로젝트 생성
  - PaymentService 요구사항
  - PaymentService 개발 (1)
  - PaymentService 개발 (2)
  - PaymentService 개발 (3)
- 3.오브젝트와 의존관계
  - 오브젝트와 의존관계
  - 관심사의 분리
  - 상속을 통한 확장
  - 클래스의 분리
  - 인터페이스 도입
  - 관계설정 책임의 분리
  - 오브젝트 팩토리
  - 원칙과 패턴
  - 스프링 컨테이너와 의존관계 주입
  - 구성정보를 가져오는 다른 방법
  - 싱글톤 레지스트리
  - DI와 디자인 패턴 (1)
  - DI와 디자인 패턴 (2)
  - 의존성 역전 원칙 (DIP)
- 4.테스트
  - 자동으로 수행되는 테스트
  - JUnit 테스트 작성
  - PaymentService 테스트
  - 테스트의 구성 요소
  - 테스트와 DI (1)
  - 테스트와 DI (2)
  - 학습 테스트
  - Clock을 이용한 시간 테스트
  - 도메인 오브젝트 테스트
- 5.템플릿
  - 스프링과 JDK 업그레이드
  - 다시 보는 개방 폐쇄 원칙
  - WebApiExRateProvider 리팩터링
  - 변하는 코드 분리하기 - 메소드 추출
  - 변하지 않는 코드 분리하기 - 메소드 추출
  - ApiExecutor 분리 - 인터페이스 도입과 클래스 분리
  - ApiExecutor 콜백과 메소드 주입
  - ExRateExecutor 콜백
  - ApiTemplate 분리
  - 디폴트 콜백과 템플릿 빈
  - 스프링이 제공하는 템플릿
- 6.예외
  - 예외를 다루는 방법
  - JPA를 이용한 Order 저장
  - Order 리포지토리와 예외
  - DataAccessException과 예외 추상화
- 7.서비스 추상화
  - 서비스란 무엇인가?
  - 애플리케이션 서비스 도입
  - 기술에 독립적인 애플리케이션 서비스
  - OrderRepository DIP
  - 트랜잭션 서비스 추상화
  - JDBC 데이터 액세스 기술
  - 트랜잭션 테스트
  - 트랜잭션 프록시
  - @Transactional과 AOP
- 8.스프링 학습 방법
  - 스프링을 어떻게 공부할 것인가?

---

## 🛠️ 환경 정보

- Java: `21`
- Spring Boot: `3.4.3`
- Build Tool: `Gradle 8.13`
- IDE: `IntelliJ`

---

## 🗂️ 이슈 정리
[6. 예외 - DataAccessException과 예외 추상화]
 - [Order entity에 @GeneratedValue의 strategy를 GenerationType.IDENTITY로 설정하면서 발생한 문제](md/generation_identity.md)

## *스프링으로 어떻게 개발할 것인가?* ~~스프링을 어떻게 공부할 것인가?~~

```
결국 스프링도 개발을 하기 위한 도구일 뿐 우리가 집중해야할 것은 스프링을 이용해서 어떻게 어떤 것을 개발할 것인가.
이번 강의에서 토비님이 전하고자 하는 핵심 메시지인데 앞으로 개발을 하면서 계속 되새겨야 하는 말인 것 같습니다.
```