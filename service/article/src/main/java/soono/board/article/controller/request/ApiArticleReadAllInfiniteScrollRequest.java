package soono.board.article.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import soono.board.article.service.request.ArticleReadAllInfiniteScrollRequest;

@Getter
@Setter
public class ApiArticleReadAllInfiniteScrollRequest {
    @NotNull(message = "게시판 ID는 필수입니다.")
    private Long boardId;

    @NotNull(message = "페이지 번호는 필수입니다.")
    private Long pageSize;
    private Long lastArticleId;

    public ArticleReadAllInfiniteScrollRequest toServiceRequest() {
        return ArticleReadAllInfiniteScrollRequest.of(boardId, pageSize, lastArticleId);
    }
}
