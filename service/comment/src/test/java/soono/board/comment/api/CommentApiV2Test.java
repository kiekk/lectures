package soono.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import soono.board.comment.service.response.CommentPageResponseV2;
import soono.board.comment.service.response.CommentResponseV2;

import java.util.List;

@Slf4j
class CommentApiV2Test {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void readAll() {
        CommentPageResponseV2 response = restClient.get()
                .uri("/v2/comments?articleId={articleId}&page={page}&pageSize={pageSize}", 1L, 1L, 10L)
                .retrieve()
                .body(CommentPageResponseV2.class);
        log.info("response.getCommentCount() = {}", response.getCommentCount());
        for (CommentResponseV2 comment : response.getComments()) {
            log.info("comment.getCommentId() = {}", comment.getCommentId());
        }
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponseV2> response1 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId={articleId}&lastPath={lastPath}&pageSize={pageSize}", 1L, null, 10L)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        log.info("firstPage");
        for (CommentResponseV2 response : response1) {
            log.info("response.getCommentId() = {}", response.getCommentId());
        }

        String lastPath = response1.getLast().getPath();
        List<CommentResponseV2> response2 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId={articleId}&lastPath={lastPath}&pageSize={pageSize}", 1L, lastPath, 10L)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        log.info("secondPage");
        for (CommentResponseV2 response : response2) {
            log.info("response.getCommentId() = {}", response.getCommentId());
        }
    }

    @Test
    void read() {
        CommentResponseV2 response = restClient.get()
                .uri("/v2/comments/{commentId}", 177701145819594752L)
                .retrieve()
                .body(CommentResponseV2.class);

        log.info("response = {}", response);
    }

    @Test
    void create() {
        CommentResponseV2 response1 = create(new CommentCreateRequestV2(1L, "my comment1", null, 1L));
        CommentResponseV2 response2 = create(new CommentCreateRequestV2(1L, "my comment2", response1.getPath(), 1L));
        CommentResponseV2 response3 = create(new CommentCreateRequestV2(1L, "my comment3", response2.getPath(), 1L));

        log.info("response1.getCommentId() = {}", response1.getCommentId());
        log.info("\tresponse2.getCommentId() = {}", response2.getCommentId());
        log.info("\t\tresponse3.getCommentId() = {}", response3.getCommentId());

        log.info("response1.getPath() = {}", response1.getPath());
        log.info("\tresponse2.getPath() = {}", response2.getPath());
        log.info("\t\tresponse3.getPath() = {}", response3.getPath());

        /*
        -- response1.getCommentId() = 177701145819594752
        -- 	response2.getCommentId() = 177701146356465664
        -- 		response3.getCommentId() = 177701146402603008
        -- response1.getPath() = 00001
        -- 	response2.getPath() = 0000100000
        -- 		response3.getPath() = 000010000000000
         */
    }

    @Test
    void delete() {
        restClient.delete()
                .uri("/v2/comments/{commentId}", 177701145819594752L)
                .retrieve()
                .body(Void.class);
    }

    @Test
    void countTest() {
        Long articleId = 2L;
        CommentResponseV2 response = create(new CommentCreateRequestV2(articleId, "my comment1", null, 1L));

        Long count1 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", articleId)
                .retrieve()
                .body(Long.class);
        log.info("count1 = {}", count1);

        // delete
        restClient.delete()
                .uri("/v2/comments/{commentId}", response.getCommentId())
                .retrieve()
                .toBodilessEntity();

        Long count2 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", articleId)
                .retrieve()
                .body(Long.class);
        log.info("count2 = {}", count2);
    }

    CommentResponseV2 create(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponseV2.class);
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequestV2 {
        private Long articleId;
        private String content;
        private String parentPath;
        private Long writerId;
    }
}
