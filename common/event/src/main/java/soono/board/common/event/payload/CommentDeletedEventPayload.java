package soono.board.common.event.payload;

import soono.board.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDeletedEventPayload implements EventPayload {
    private Long commentId;
    private String content;
    private String path;
    private Long articleId;
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private Long articleCommentCount;

    @Builder
    public CommentDeletedEventPayload(Long commentId, String content, String path, Long articleId, Long writerId,
                                      Boolean deleted, LocalDateTime createdAt, Long articleCommentCount) {
        this.commentId = commentId;
        this.content = content;
        this.path = path;
        this.articleId = articleId;
        this.writerId = writerId;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.articleCommentCount = articleCommentCount;
    }
}
