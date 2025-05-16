package soono.board.article.service.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
public class ArticleUpdateRequest {
    private final String title;
    private final String content;

    private ArticleUpdateRequest(String title, String content) {
        Assert.hasText(title, "Title must not be empty");
        Assert.hasText(content, "Content must not be empty");
        this.title = title;
        this.content = content;
    }

    public static ArticleUpdateRequest of(String title, String content) {
        return new ArticleUpdateRequest(title, content);
    }
}
