package soono.board.article.service;

import soono.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soono.board.article.entity.Article;
import soono.board.article.entity.BoardArticleCount;
import soono.board.article.repository.ArticleRepository;
import soono.board.article.repository.BoardArticleCountRepository;
import soono.board.article.service.request.ArticleCreateRequest;
import soono.board.article.service.request.ArticleUpdateRequest;
import soono.board.article.service.response.ArticlePageResponse;
import soono.board.article.service.response.ArticleResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(
                        boardId,
                        PageLimitCalculator.calculatePageLimit(page, pageSize, 10L)
                )
        );
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ? articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);
        return articles.stream()
                .map(ArticleResponse::from)
                .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId())
        );

        int result = boardArticleCountRepository.increase(request.getBoardId());
        if (result == 0) {
            boardArticleCountRepository.save(
                    BoardArticleCount.init(request.getBoardId(), 1L)
            );
        }

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.from(article);
    }

    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow();
        int deleteCount = articleRepository.deleteByArticleId(article.getArticleId());
        if (deleteCount == 0) {
            throw new IllegalStateException("삭제된 게시글입니다.");
        }
        boardArticleCountRepository.decrease(article.getBoardId());
    }

    public Long count(Long boardId) {
        return boardArticleCountRepository.findById(boardId)
                .map(BoardArticleCount::getArticleCount)
                .orElse(0L);
    }

}