package soono.board.article.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import soono.board.article.service.response.ArticleResponse;

/*
TODO: 이 테스트는 실행할 때마다 articleId를 변경해줘야 테스트가 정상적으로 동작한다.
TODO: articleId를 직접 변경하지 않고도 매번 테스트가 정상적으로 동작하게끔 변경해보자.
TODO: 그리고 현재 테스트 데이터가 실제 DB에 반영되고 있는데 이를 테스트 DB 환경을 구축하여 분리해보자.
TODO: RestClient 대신 RestAssured를 사용해보자, 그리고 Scenario를 사용해보자.
 */
@Slf4j
public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest(
                "hello", "my content", 1L, 1L
        ));
        log.info("response={}", response);
    }

    @Test
    void readTest() {
        ArticleResponse response = read(176487927844192256L);
        log.info("response={}", response);
    }

    @Test
    void updateTest() {
        ArticleResponse response = update(176483745123614720L);
        log.info("response={}", response);
    }

    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/v1/articles/{articleId}", 176483745123614720L)
                .retrieve()
                .toBodilessEntity();
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse update(Long articleId) {
        return restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi", "my content 2"))
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }
}
