package soono.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import soono.board.comment.service.response.CommentPageResponse;
import soono.board.comment.service.response.CommentResponse;

import java.util.List;

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

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v1/comments?articleId={articleId}&page={page}&pageSize={pageSize}", 1L, 50000L, 10L)
                .retrieve()
                .body(CommentPageResponse.class);
        log.info("response.getCommentCount()={}", response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.printf("\t");
            }
            log.info("comment.getCommentId()={}", comment.getCommentId());
        }
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> responses1 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId={articleId}&pageSize={pageSize}", 1L, 5L)
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");
        for (CommentResponse comment : responses1) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        Long lastParentCommentId = responses1.getLast().getParentCommentId();
        Long lastCommentId = responses1.getLast().getCommentId();

        List<CommentResponse> responses2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId={articleId}&pageSize={pageSize}&lastParentCommentId={lastParentCommentId}&lastCommentId={lastCommentId}"
                        , 1L, 5L, lastParentCommentId, lastCommentId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        System.out.println("secondPage");
        for (CommentResponse comment : responses2) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
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
