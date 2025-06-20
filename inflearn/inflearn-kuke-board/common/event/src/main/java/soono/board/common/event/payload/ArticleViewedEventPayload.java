package soono.board.common.event.payload;

import soono.board.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleViewedEventPayload implements EventPayload {
    private Long articleId;
    private Long articleViewCount;

    @Builder
    public ArticleViewedEventPayload(Long articleId, Long articleViewCount) {
        this.articleId = articleId;
        this.articleViewCount = articleViewCount;
    }
}
