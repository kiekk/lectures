package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HelloApiTest {
    @Test
    void helloApi() {
        // http://localhost8080/hello?name=Spring

        TestRestTemplate testRestTemplate = new TestRestTemplate();

        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:9090/app/hello?name={name}", String.class, "Spring");

        // status code 200
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // header(Content-type) text/plain
        Assertions.assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        // body Hello, Spring 😜
        Assertions.assertThat(response.getBody()).isEqualTo("**Hello, Spring \uD83D\uDE1C**");
    }
}
