package soono.board.comment.service.response;

import lombok.Getter;
import lombok.ToString;
import soono.board.comment.entity.CommentV2;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentResponseV2 {
    private Long commentId;
    private String content;
    private String path;
    private Long articleId;
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;

    public static CommentResponseV2 from(CommentV2 commentV2) {
        CommentResponseV2 response = new CommentResponseV2();
        response.commentId = commentV2.getCommentId();
        response.content = commentV2.getContent();
        response.path = commentV2.getCommentPath().getPath();
        response.articleId = commentV2.getArticleId();
        response.writerId = commentV2.getWriterId();
        response.deleted = commentV2.getDeleted();
        response.createdAt = commentV2.getCreatedAt();
        return response;
    }
}
