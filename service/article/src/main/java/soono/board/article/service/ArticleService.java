package soono.board.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soono.board.article.entity.Article;
import soono.board.article.entity.BoardArticleCount;
import soono.board.article.repository.ArticleRepository;
import soono.board.article.repository.BoardArticleCountRepository;
import soono.board.article.service.request.ArticleCreateRequest;
import soono.board.article.service.request.ArticleReadAllInfiniteScrollRequest;
import soono.board.article.service.request.ArticleReadAllRequest;
import soono.board.article.service.request.ArticleUpdateRequest;
import soono.board.article.service.response.ArticlePageResponse;
import soono.board.article.service.response.ArticleResponse;
import soono.board.common.event.EventType;
import soono.board.common.event.payload.ArticleCreatedEventPayload;
import soono.board.common.event.payload.ArticleDeletedEventPayload;
import soono.board.common.event.payload.ArticleUpdatedEventPayload;
import soono.board.common.outboxmessagerelay.OutboxEventPublisher;
import soono.board.common.snowflake.Snowflake;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    public ArticlePageResponse readAll(ArticleReadAllRequest request) {
        return ArticlePageResponse.of(
                articleRepository.findAll(request.getBoardId(), request.getOffset(), request.getPageSize()).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(
                        request.getBoardId(),
                        request.getLimit()
                )
        );
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    public List<ArticleResponse> readAllInfiniteScroll(ArticleReadAllInfiniteScrollRequest request) {
        List<Article> articles = request.isFirstPage() ? articleRepository.findAllInfiniteScroll(request.getBoardId(), request.getPageSize()) :
                articleRepository.findAllInfiniteScroll(request.getBoardId(), request.getPageSize(), request.getLastArticleId());
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

        // 이벤트 발행
        outboxEventPublisher.publish(
                EventType.ARTICLE_CREATED,
                ArticleCreatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .modifiedAt(article.getModifiedAt())
                        .boardArticleCount(count(article.getBoardId()))
                        .build(),
                article.getBoardId()
        );
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());

        // 이벤트 발행
        outboxEventPublisher.publish(
                EventType.ARTICLE_UPDATED,
                ArticleUpdatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .modifiedAt(article.getModifiedAt())
                        .build(),
                article.getBoardId()
        );
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

        // 이벤트 발행
        outboxEventPublisher.publish(
                EventType.ARTICLE_DELETED,
                ArticleDeletedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .modifiedAt(article.getModifiedAt())
                        .boardArticleCount(count(article.getBoardId()))
                        .build(),
                article.getBoardId()
        );
        boardArticleCountRepository.decrease(article.getBoardId());
    }

    public Long count(Long boardId) {
        return boardArticleCountRepository.findById(boardId)
                .map(BoardArticleCount::getArticleCount)
                .orElse(0L);
    }

}