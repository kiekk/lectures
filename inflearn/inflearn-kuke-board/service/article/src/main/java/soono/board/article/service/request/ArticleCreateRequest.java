package soono.board.article.service.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
public class ArticleCreateRequest {
    private final String title;
    private final String content;
    private final Long writerId;
    private final Long boardId;

    private ArticleCreateRequest(String title, String content, Long writerId, Long boardId) {
        Assert.hasText(title, "Title must not be empty");
        Assert.hasText(content, "Content must not be empty");
        Assert.notNull(writerId, "Writer ID must not be null");
        Assert.notNull(boardId, "Board ID must not be null");
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.boardId = boardId;
    }

    public static ArticleCreateRequest of(String title, String content, Long writerId, Long boardId) {
        return new ArticleCreateRequest(title, content, writerId, boardId);
    }
}
