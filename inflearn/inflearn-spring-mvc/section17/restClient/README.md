# ☘️ RestClient (1) ~ (2)

---

## 📖 내용

- RestClient 는 Spring 6 에서 새롭게 도입된 동기식 HTTP 클라이언트로서, 기존의 RestTemplate 을 대체하거나 보완할 수 있는보다 모던한 API를 제공한다
- 내부적으로 다양한 HTTP 클라이언트 라이브러리위에 추상화를 제공하며개발자가 손쉽게 HTTP 요청과 응답을 다룰 수 있도록 지원한다

---

### 특징
- 메서드 체이닝 방식의API
  - get(), post(), put() 등 HTTP 메서드를 코드상에서 직관적으로 체이닝할 수 있으며 retrieve(), exchange() 등을 통해 설정 → 요청 → 응답 흐름을 명확히 표현할 수 있다
- 동기식 처리
  - 내부 동작이 동기식이므로 블로킹 모델을 따른다
- Builder 기반 설정
  - RestClient.builder()에서 baseUrl, 기본 헤더, 인터셉터, requestFactory 등을 간편하게 설정할 수 있으며 한 번 만들어진 RestClient 는 여러 스레드에서 안전하게 사용할 수 있다
- 다양한 HTTP 라이브러리 연동
  - 자동으로 Apache HttpClient, Jetty HttpClient, java.net.http 등을 감지하여 활용하며 직접 requestFactory(…)를 지정하여 원하는 라이브러리를 명시적으로 선택할 수도 있다
- 편리한 메시지 변환
  - body(Object), body(ParameterizedTypeReference) 등을 제공해 제네릭 구조를 포함한 다양한 타입 변환을 지원하며 accept(), contentType() 등으로 헤더를 설정할 수 있다
- 유연한 오류 처리
  - 4xx, 5xx 상태 코드를 받으면 기본적으로 RestClientException 계열 예외를 던지지만, onStatus()를 사용해 특정 상태 코드에 대한 맞춤 예외 처리가 가능합니다.
  - exchange() 메서드를 사용하면 요청과 응답을 더 세밀하게 다루면서 상태 코드별로 직접 처리를 구성할 수 있습니다.

---

### 구현체

- HttpComponentsClientHttpRequestFactory를 사용하기 위해서는 아래 의존성 추가 필요
`implementation 'org.apache.httpcomponents.client5:httpclient5:5.4.2'`

| 구현체                                    | 특징                   | 추천 사용 환경                       |
|----------------------------------------|----------------------|--------------------------------|
| HttpComponentsClientHttpRequestFactory | Apache HttpClient 기반 | Spring Boot 설정, 동기 요청          |
| SimpleClientHttpRequestFactory         | JDK 기본 HTTP 요청       | 간단한 HTTP 요청                    |
| OkHttp3ClientHttpRequestFactory        | OkHttp 기반            | API 클라이언트, 대규모 트래픽 처리          |
| Netty4ClientHttpRequestFactory         | WebFlux(Netty) 기반    | 비동기 API 클라이언트 (WebFlux)        |
| JettyClientHttpRequestFactory          | Jetty 기반             | Jetty 기반                애플리케이션 |

---

## 🔍 중심 로직

```java
package org.springframework.web.client;

// imports

public interface RestClient {

	RequestHeadersUriSpec<?> get();

	RequestHeadersUriSpec<?> head();

	RequestBodyUriSpec post();

	RequestBodyUriSpec put();

	RequestBodyUriSpec patch();

	RequestHeadersUriSpec<?> delete();

	RequestHeadersUriSpec<?> options();

	RequestBodyUriSpec method(HttpMethod method);

	Builder mutate();
    
    // static factory methods
    
	static RestClient create() {
		return new DefaultRestClientBuilder().build();
	}

	static RestClient create(String baseUrl) {
		return new DefaultRestClientBuilder().baseUrl(baseUrl).build();
	}

	static RestClient create(URI baseUrl) {
		return new DefaultRestClientBuilder().baseUrl(baseUrl).build();
	}

	static RestClient create(RestTemplate restTemplate) {
		return new DefaultRestClientBuilder(restTemplate).build();
	}
    
	static RestClient.Builder builder() {
		return new DefaultRestClientBuilder();
	}

	static RestClient.Builder builder(RestTemplate restTemplate) {
		return new DefaultRestClientBuilder(restTemplate);
	}
    
	interface Builder {

        // builder methods
	}

	interface UriSpec<S extends RequestHeadersSpec<?>> {

        // urispec methods
	}

	interface RequestHeadersSpec<S extends RequestHeadersSpec<S>> {

        // request headers spec methods
	}

	interface RequestBodySpec extends RequestHeadersSpec<RequestBodySpec> {
        // request body spec methods
	}

	interface ResponseSpec {
        // response spec methods
	}

	interface RequestHeadersUriSpec<S extends RequestHeadersSpec<S>> extends UriSpec<S>, RequestHeadersSpec<S> {
	}

	interface RequestBodyUriSpec extends RequestBodySpec, RequestHeadersUriSpec<RequestBodySpec> {
	}

}
```

📌

---

## 💬 코멘트

---
