package soono.board.article.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import soono.board.article.service.request.ArticleUpdateRequest;

@Getter
public class ApiArticleUpdateRequest {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public ArticleUpdateRequest toServiceRequest() {
        return ArticleUpdateRequest.of(title, content);
    }
}
