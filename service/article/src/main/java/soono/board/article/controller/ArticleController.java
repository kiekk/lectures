package soono.board.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soono.board.article.controller.request.ApiArticleCreateRequest;
import soono.board.article.controller.request.ApiArticleReadAllInfiniteScrollRequest;
import soono.board.article.controller.request.ApiArticleReadAllRequest;
import soono.board.article.controller.request.ApiArticleUpdateRequest;
import soono.board.article.service.ArticleService;
import soono.board.article.service.request.ArticleCreateRequest;
import soono.board.article.service.request.ArticleUpdateRequest;
import soono.board.article.service.response.ArticlePageResponse;
import soono.board.article.service.response.ArticleResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/v1/articles/boards/{boardId}/count")
    public Long count(@PathVariable("boardId") Long boardId) {
        return articleService.count(boardId);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponse readAll(@Valid ApiArticleReadAllRequest request) {
        return articleService.readAll(request.toServiceRequest());
    }

    @GetMapping("/v1/articles/infinite-scroll")
    public List<ArticleResponse> readAllInfiniteScroll(@Valid ApiArticleReadAllInfiniteScrollRequest request) {
        return articleService.readAllInfiniteScroll(request.toServiceRequest());
    }

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody @Valid ApiArticleCreateRequest request) {
        return articleService.create(request.toServiceRequest());
    }

    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(@PathVariable("articleId") Long articleId, @RequestBody @Valid ApiArticleUpdateRequest request) {
        return articleService.update(articleId, request.toServiceRequest());
    }

    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable("articleId") Long articleId) {
        articleService.delete(articleId);
    }
}
