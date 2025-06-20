package soono.board.article.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import soono.board.article.service.request.ArticleCreateRequest;

@Getter
public class ApiArticleCreateRequest {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "작성자는 필수입니다.")
    private Long writerId;

    @NotNull(message = "게시판은 필수입니다.")
    private Long boardId;

    public ArticleCreateRequest toServiceRequest() {
        return ArticleCreateRequest.of(title, content, writerId, boardId);
    }
}
