package soono.board.common.event.payload;

import soono.board.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleLikedEventPayload implements EventPayload {
    private Long articleLikeId;
    private Long articleId;
    private Long userId;
    private LocalDateTime createdAt;
    private Long articleLikeCount;

    @Builder
    public ArticleLikedEventPayload(Long articleLikeId, Long articleId, Long userId,
                                    LocalDateTime createdAt, Long articleLikeCount) {
        this.articleLikeId = articleLikeId;
        this.articleId = articleId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.articleLikeCount = articleLikeCount;
    }
}
