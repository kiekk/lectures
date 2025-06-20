package soono.board.article.service.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
public class ArticleReadAllInfiniteScrollRequest {
    private final Long boardId;
    private final Long pageSize;
    private final Long lastArticleId;

    private ArticleReadAllInfiniteScrollRequest(Long boardId, Long pageSize, Long lastArticleId) {
        Assert.notNull(boardId, "Board ID must not be null");
        Assert.notNull(pageSize, "Page size must not be null");
        this.boardId = boardId;
        this.pageSize = pageSize;
        this.lastArticleId = lastArticleId;
    }

    public static ArticleReadAllInfiniteScrollRequest of(Long boardId, Long pageSize, Long lastArticleId) {
        return new ArticleReadAllInfiniteScrollRequest(boardId, pageSize, lastArticleId);
    }

    public boolean isFirstPage() {
        return lastArticleId == null;
    }
}
