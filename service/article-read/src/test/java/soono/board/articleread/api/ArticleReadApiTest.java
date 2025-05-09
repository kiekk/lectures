package soono.board.articleread.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import soono.board.articleread.service.response.ArticleReadPageResponse;
import soono.board.articleread.service.response.ArticleReadResponse;

import java.util.List;

@Slf4j
class ArticleReadApiTest {
    RestClient articleReadRestClient = RestClient.create("http://localhost:9005");
    RestClient articleRestClient = RestClient.create("http://localhost:9000");

    @Test
    void readTest() {
        ArticleReadResponse response = articleReadRestClient.get()
                .uri("/v1/articles/{articleId}", 178683953058918400L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void readAllTest() {
        ArticleReadPageResponse response1 = articleReadRestClient.get()
                .uri("/v1/articles?boardId={boardId}&page={page}&pageSize={pageSize}", 1L, 3000L, 5)
                .retrieve()
                .body(ArticleReadPageResponse.class);

        log.info("response1.getArticleCount() = {}", response1.getArticleCount());
        for (ArticleReadResponse article : response1.getArticles()) {
            log.info("article.getArticleId() = {}", article.getArticleId());
        }

        ArticleReadPageResponse response2 = articleRestClient.get()
                .uri("/v1/articles?boardId={boardId}&page={page}&pageSize={pageSize}", 1L, 3000L, 5)
                .retrieve()
                .body(ArticleReadPageResponse.class);

        log.info("response2.getArticleCount() = {}", response2.getArticleCount());
        for (ArticleReadResponse article : response2.getArticles()) {
            log.info("article.getArticleId() = {}", article.getArticleId());
        }
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<ArticleReadResponse> response1 = articleReadRestClient.get()
                .uri("/v1/articles/infinite-scroll?boardId={boardId}&pageSize={pageSize}&lastArticleId={lastArticleId}", 1L, 5L, 126170915769933824L)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        for (ArticleReadResponse response : response1) {
            log.info("response = {}", response.getArticleId());
        }

        List<ArticleReadResponse> response2 = articleRestClient.get()
                .uri("/v1/articles/infinite-scroll?boardId={boardId}&pageSize={pageSize}&lastArticleId={lastArticleId}", 1L, 5L, 126170915769933824L)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        for (ArticleReadResponse response : response2) {
            log.info("response = {}", response.getArticleId());
        }
    }

}
