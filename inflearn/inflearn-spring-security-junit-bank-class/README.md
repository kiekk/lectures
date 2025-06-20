# inflearn-spring-security-junit-bank-class

```
메타 코딩(최주호)님의 인프런 강의인 '스프링부트 JUnit 테스트 - 시큐리티를 활용한 Bank 애플리케이션'을 수강하며 학습한 내용을 정리한 Repository입니다.

```

## 인프런
[인프런 강의 바로가기](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-junit-%ED%85%8C%EC%8A%A4%ED%8A%B8/dashboard)

## 개발 환경

* Intellij IDEA Ultimate 2023.2.2
* Java 17
* Gradle 8.2.1
* Spring Boot 3.1.3

## 기술 세부 스펙

* Spring Web
* Spring Data JPA
* Spring Validation
* Spring Security
* H2 Database
* MySQL Driver
* Lombok
* Spring Boot DevTools

그 외

* Jwt
* springdoc-openapi

## Jacoco로 테스트 커버리지 확인하기
* 참고 링크: https://techblog.woowahan.com/2661/

## 적용 순서
```
1. Jacoco 플러그인 추가
2. 전체 테스트 실행하여 build/jacoco/test.exec 파일 생성하기
(exec 파일은 현재 우리가 눈으로 확인할 수 없는 파일)
3. jacocoTestReport 생성 gradle 문법 추가
4. 터미널에서 ./gradlew jacocoTestReport 실행하여 눈으로 확인할 수 있는 파일(html, xml, csv)로 변환
5. 일정 조건이 충족될 경우에만 테스트 커버리지 리포트 파일을 생성할 수 있도록 jacocoTestCoverageVerification 문법 추가
```
![img_1.png](img_1.png)

## 테스트 커버리지 확인 결과
![img.png](img.png)