# ☘️ [스프링 시큐리티 완전 정복 [6.x 개정판]]

> 정수원님의 [스프링 시큐리티 완전 정복 [6.x 개정판]] 인프런 강의로 학습한 내용을 정리한 공간입니다.  
> 강의 링크: [스프링 시큐리티 완전 정복 [6.x 개정판]](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EC%99%84%EC%A0%84%EC%A0%95%EB%B3%B5/dashboard)


<img src="https://cdn.inflearn.com/public/courses/333154/cover/7d446f00-12af-4924-a9cd-7e8c886bde59/333154.png" width="400px">

<sub>※ 이미지 출처: 인프런</sub>

---
## 📚 목차 및 소스코드 링크
> 각 챕터의 디렉토리에는 해당 챕터에서 다루는 강의 내용이 정리되어 있습니다.

<details>
<summary>섹션 2. 초기화 과정 이해</summary>
<div markdown="1">

| 강의                                       | 디렉토리 경로                                                                                                |
|------------------------------------------|--------------------------------------------------------------------------------------------------------|
| 프로젝트 생성 / 의존성 추가                         | [section02/프로젝트생성_의존성추가](section02/프로젝트생성_의존성추가/README.md)                                             |
| SecurityBuilder / SecurityConfigurer     | [section02/SecurityBuilder_SecurityConfigurer](section02/SecurityBuilder_SecurityConfigurer/README.md) |
| WebSecurity / HttpSecurity               | [section02/WebSecurity_HttpSecurity](section02/WebSecurity_HttpSecurity/README.md)           |
| DelegatingFilterProxy / FilterChainProxy | [section02/DelegatingFilterProxy_FilterChainProxy](section02/DelegatingFilterProxy_FilterChainProxy/README.md)           |
| 사용자 정의 보안 설정하기                           | [section02/사용자_정의_보안_설정하기](section02/사용자_정의_보안_설정하기/README.md)           |
</div>
</details>

<details>
<summary>섹션 3. 인증 프로세스</summary>
<div markdown="1">

| 강의                                            | 디렉토리 경로                                                                                                            |
|-----------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| 폼 인증 - formLogin()                            | [section03/폼인증_formLogin()](section03/폼인증_formLogin()/README.md)                                                   |
| 폼 인증 필터 - UsernamePasswordAuthenticationFilter | [section03/폼인증필터_UsernamePasswordAuthenticationFilter](section03/폼인증필터_UsernamePasswordAuthenticationFilter/README.md) |
| 기본 인증 - httpBasic()                           | [section03/기본인증_httpBasic()](section03/기본인증_httpBasic()/README.md)                                                 |
| 기본 인증 필터 - BasicAuthenticationFilter          | [section03/기본인증필터_BasicAuthenticationFilter](section03/기본인증필터_BasicAuthenticationFilter/README.md)                                               |
| 기억하기 인증 – rememberMe()                        | [section03/기억하기인증_rememberMe()](section03/기억하기인증_rememberMe()/README.md)                                                 |
| 기억하기 인증 필터 - RememberMeAuthenticationFilter   | [section03/기억하기인증필터_RememberMeAuthenticationFilter](section03/기억하기인증필터_RememberMeAuthenticationFilter/README.md)                                               |
| 익명 인증 사용자 - anonymous()                       | [section03/익명인증사용자_anonymous()](section03/익명인증사용자_anonymous()/README.md)                                                 |
| 로그 아웃 - logout() -1~2                         | [section03/로그_아웃_logout()](section03/로그_아웃_logout()/README.md)                                                     |
| 요청 캐시 RequestCache / SavedRequest| [section03/요청캐시_RequestCache_SavedRequest](section03/요청캐시_RequestCache_SavedRequest/README.md)                                                     |
</div>
</details>

<details>
<summary>섹션 4. 인증 아키텍처</summary>
<div markdown="1">

| 강의                                                       | 디렉토리 경로                                                                                                                        |
|----------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| 인증 - Authentication                                      | [section04/인증_Authentication](section04/인증_Authentication/README.md)                                                           |
| 인증 컨텍스트 - SecurityContext / SecurityContextHolder -1 ~ 2 | [section04/인증_컨텍스트_SecurityContext_SecurityContextHolder](section04/인증_컨텍스트_SecurityContext_SecurityContextHolder/README.md) |
| 인증 관리자 - AuthenticationManager - 1 ~ 2                   | [section04/인증_관리자_AuthenticationManager](section04/인증_관리자_AuthenticationManager/README.md) |
| 인증 제공자 - AuthenticationProvider - 1 ~ 2                  | [section04/인증_관리자_AuthenticationManager](section04/인증_관리자_AuthenticationManager/README.md) |
| 사용자 상세 서비스 - UserDetailsService                          | [section04/사용자_상세_서비스_UserDetailsService](section04/사용자_상세_서비스_UserDetailsService/README.md) |
</div>
</details>

<details>
<summary>섹션 5. 인증 상태 영속성</summary>
<div markdown="1">

| 강의                                                           | 디렉토리 경로                                                                                                  |
|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| SecurityContextRepository / SecurityContextHolderFilter - 1  | [section05/SecurityContextRepository_SecurityContextHolderFilter](section05/SecurityContextRepository_SecurityContextHolderFilter/README.md) |
</div>
</details>

<details>
<summary>섹션 6. 세션 관리</summary>
<div markdown="1">

| 강의                                                | 디렉토리 경로                                                                                                                    |
|---------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| 동시 세션 제어 - sessionManagement().maximumSessions()  | [section06/동시세션제어_sessionManagement().maximumSessions()](section06/동시세션제어_sessionManagement().maximumSessions()/README.md) |
| 세션 고정 보호 - sessionManagement().sessionFixation()  | [section06/세션고정보호_sessionManagement().sessionFixation()](section06/세션고정보호_sessionManagement().sessionFixation()/README.md) |
| 세션 생성 정책 - sessionManagement().sessionCreationPolicy()| [section06/세션생성정책_sessionManagement().sessionCreationPolicy()](section06/세션생성정책_sessionManagement().sessionCreationPolicy()/README.md) |
| SessionManagementFilter / ConcurrentSessionFilter - 1 ~ 2| [section06/세션생성정책_sessionManagement().sessionCreationPolicy()](section06/세션생성정책_sessionManagement().sessionCreationPolicy()/README.md) |
</div>
</details>

---

## 🛠️ 환경 정보

- Java: `21`
- Spring Boot: `3.4.4`
- Build Tool: `Gradle 8.13`
- IDE: `IntelliJ`

---

## 🗂️ 정리 방식

- 각 챕터마다 별도 디렉토리
- 이론 설명 강의는 README.md에 강의 내용을 정리
- 실습 강의는 Project 생성하여 실습 코드 작성

---

