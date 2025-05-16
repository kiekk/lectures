package soono.board.article.service.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;
import soono.board.article.service.PageLimitCalculator;

@Getter
@ToString
public class ArticleReadAllRequest {
    private final Long boardId;
    private final Long page;
    private final Long pageSize;

    private ArticleReadAllRequest(Long boardId, Long page, Long pageSize) {
        Assert.notNull(boardId, "Board ID must not be null");
        Assert.notNull(page, "Page number must not be null");
        Assert.notNull(pageSize, "Page size must not be null");
        this.boardId = boardId;
        this.page = page;
        this.pageSize = pageSize;
    }

    public static ArticleReadAllRequest of(Long boardId, Long page, Long pageSize) {
        return new ArticleReadAllRequest(boardId, page, pageSize);
    }

    public Long getOffset() {
        return (page - 1) * pageSize;
    }

    public Long getLimit() {
        return PageLimitCalculator.calculatePageLimit(page, pageSize, 10L);
    }
}
