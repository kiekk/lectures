package soono.board.article.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import soono.board.article.service.request.ArticleReadAllRequest;

@Getter
@Setter
public class ApiArticleReadAllRequest {
    @NotNull(message = "게시판 ID는 필수입니다.")
    private Long boardId;

    @NotNull(message = "페이지 번호는 필수입니다.")
    private Long page;

    @NotNull(message = "페이지 크기는 필수입니다.")
    private Long pageSize;

    // 비지니스 로직과 관련된 메서드가 Web Request 객체와 혼재되는 것은 별로인 것 같다.
//    public Long getOffset() {
//        return (page - 1) * pageSize;
//    }
//
//    public Long getLimit() {
//        return PageLimitCalculator.calculatePageLimit(page, pageSize, 10L);
//    }

    public ArticleReadAllRequest toServiceRequest() {
        return ArticleReadAllRequest.of(boardId, page, pageSize);
    }
}
