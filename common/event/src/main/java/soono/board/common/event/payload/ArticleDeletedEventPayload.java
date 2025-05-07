package soono.board.common.event.payload;

import soono.board.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleDeletedEventPayload implements EventPayload {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long boardArticleCount;

    @Builder
    public ArticleDeletedEventPayload(Long articleId, String title, String content, Long boardId, Long writerId,
                                       LocalDateTime createdAt, LocalDateTime modifiedAt, Long boardArticleCount) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.boardId = boardId;
        this.writerId = writerId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.boardArticleCount = boardArticleCount;
    }
}
