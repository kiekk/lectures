# inflearn-toby-spring-boot

```
토비님의 스프링 부트 인프런 강의를 토대로 학습한 Repository입니다.
```
![img.png](img.png)

### 인프런 강의
[인프런 강의 바로가기](https://www.inflearn.com/course/%ED%86%A0%EB%B9%84-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%9D%B4%ED%95%B4%EC%99%80%EC%9B%90%EB%A6%AC/dashboard)

### Notion
[notion 바로가기](https://flat-asp-5ca.notion.site/11303e8aa74246a5b9f3d5a41300350e)

### Blog 주소
1. 스프링 부트 살펴보기
2. 스프링 부트 시작하기
3. 독립 실행형 서블릿 애플리케이션
4. 독립 실행형 스프링 애플리케이션
5. DI와 테스트, 디자인 패턴
6. 자동 구성 기반 애플리케이션
7. 조건부 자동 구성
8. 외부 설정을 이용한 자동 구성
9. Spring JDBC 자동 구성 개발
10. 스프링 부트 자세히 살펴보기


### 이슈
```
독립 실행형 스프링 애플리케이션 - 애노테이션 매핑 정보 사용

HelloController에 @RequestMapping, @GetMapping, @ResponseBody 애노테이션을 추가하여도,
/hello에 대한 경로를 인식하지 못함.

원인: Spring 6 부터는 Type Level, 즉 Class에 @RequestMapping 애노테이션을 인식하지 않는다고 합니다.

해결: 
1. Type Level 에 @Controller, @RestController 애노테이션을 추가
2. 클래스 기반 프록시 (CGLib) 활성화
```

![img_1.png](img_1.png)
<img src="img_2.png" alt="drawing" width="500"/>

### Spring 6 이전
![img_3.png](img_3.png)

### Spring 6 이후
![img_4.png](img_4.png)