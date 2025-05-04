package soono.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import soono.board.comment.service.response.CommentResponse;

@Slf4j
class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        log.info("commentId1: {}", response1.getCommentId());
        log.info("\tcommentId2: {}", response2.getCommentId());
        log.info("\tcommentId3: {}", response3.getCommentId());
        /*
        -- commentId1: 177369728196861952
        -- 	commentId2: 177369736614830080
        -- 	commentId3: 177369737931841536
         */
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 177369728196861952L)
                .retrieve()
                .body(CommentResponse.class);
        log.info("response={}", response);
    }

    @Test
    void delete() {
        /*
        -- commentId1: 177369728196861952
        -- 	commentId2: 177369736614830080
        -- 	commentId3: 177369737931841536
         */

        Void body = restClient.delete()
                .uri("/v1/comments/{commentId}", 177369737931841536L)
                .retrieve()
                .body(Void.class);
        // retrieve()만 호출하면 무시, body() 까지 호출해야 실제 API 호출됨
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
