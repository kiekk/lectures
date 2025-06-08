# ☘️ RestClient - 오류 처리

---

## 📖 내용

- RestClient 에서 오류 처리는 기본적으로 retrieve() 또는 exchange() 메서드에서 수행할 수 있으며 HTTP 4xx 및 5xx 오류 발생 시 자동으로 예외(RestClientException)를 던진다
- 기본 동작을 커스터마이징 하려면 onStatus() 또는 exchange()를 활용하여 오류 상태 코드를 직접 처리해야 한다


#### 자동 예외 처리
- retrieve()를 사용할 경우 자동으로 4xx, 5xx 응답 시 예외를 던짐
- try-catch 블록에서 RestClientException을 잡아 예외를 처리
```java
try {
    String response = restClient.get()
    .uri("https://example.com/api/not-found") // 존재하지 않는 URL
    .retrieve()
    .body(String.class);
} catch (RestClientException e) {
    System.err.println("RestClientException 발생: " + e.getMessage());
}
```

#### onStatus()를 이용한 사용자 정의 오류 처리
- onStatus()를 이용하여 특정 상태 코드에 대한 맞춤형 예외 처리 가능
- 4xx(클라이언트 오류) 또는 5xx(서버 오류) 상태 코드에 따라 커스텀 예외 발생
```java
.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
    throw new RuntimeException("클라이언트 오류 발생: " + response.getStatusCode());
})
.onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
    throw new RuntimeException("서버 오류 발생: " + response.getStatusCode());
})
```

#### onStatus() 를 사용한 특정 오류 매핑
- 일부 상태 코드에 대해 명확한 예외 클래스를 매핑할 수 있다
- onStatus() 내부에서 특정 HTTP 상태 코드(예: 404)에 대해 사용자 정의 예외를 던질 수 있음
- 다른 상태 코드는 기본적으로 RestClientException 이 발생하도록 유지
```java
.onStatus(status -> status.value() == 404, (request, response) -> {
    throw new UserNotFoundException(response.getStatusCode());
})
```

#### exchange()를 이용한 세부 오류 처리
- exchange()를 사용하면 response.getStatusCode()를 통해 모든 HTTP 응답 상태 코드를 직접 확인할 수 있음
- response.getBody()로 직접 응답 바디를 읽어올 수 있음
- 원하는 방식으로 오류를 처리할 수 있으며, 커스텀 예외를 던질 수도 있음
```java
.exchange((request, response) -> {
    HttpStatusCode status = response.getStatusCode();
    // 4xx 오류 발생 시
    if (status.is4xxClientError()) {
        throw new RuntimeException("잘못된 요청 (4xx) : " + status.value());
    }
    // 5xx 오류 발생 시
    if (status.is5xxServerError()) {
        throw new RuntimeException("서버 오류 (5xx) : " + status.value());
    }
    // 정상 응답 바디 변환
    try (InputStream bodyStream = response.getBody()) {
        return new String(bodyStream.readAllBytes()); // 바디 내용을 문자열로 변환
    } catch (Exception e) {
        throw new RuntimeException("응답 바디 읽기 오류", e);
    }
});
```

---

## 🔍 중심 로직

```java
```

📌

---

## 💬 코멘트

---
