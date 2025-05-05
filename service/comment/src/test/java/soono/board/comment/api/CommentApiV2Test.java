package soono.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import soono.board.comment.service.response.CommentResponseV2;

@Slf4j
class CommentApiV2Test {
    RestClient restClient = RestClient.create("http://localhost:9001");

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
