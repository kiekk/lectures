package soono.board.articleread.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soono.board.articleread.client.ArticleClient;
import soono.board.articleread.client.CommentClient;
import soono.board.articleread.client.LikeClient;
import soono.board.articleread.client.ViewClient;
import soono.board.articleread.repository.ArticleIdListRepository;
import soono.board.articleread.repository.ArticleQueryModel;
import soono.board.articleread.repository.ArticleQueryModelRepository;
import soono.board.articleread.repository.BoardArticleCountRepository;
import soono.board.articleread.service.event.handler.EventHandler;
import soono.board.articleread.service.response.ArticleReadPageResponse;
import soono.board.articleread.service.response.ArticleReadResponse;
import soono.board.common.event.Event;
import soono.board.common.event.EventPayload;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleReadService {
    private final ArticleClient articleClient;
    private final CommentClient commentClient;
    private final LikeClient likeClient;
    private final ViewClient viewClient;
    private final ArticleQueryModelRepository articleQueryModelRepository;
    private final List<EventHandler> eventHandlers;
    private final ArticleIdListRepository articleIdListRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    public void handleEvent(Event<EventPayload> event) {
        eventHandlers.stream()
                .filter(handler -> handler.supports(event))
                .findFirst()
                .ifPresent(handler -> handler.handle(event));
    }

    public ArticleReadResponse read(Long articleId) {
        ArticleQueryModel articleQueryModel = articleQueryModelRepository.read(articleId)
                .or(() -> fetch(articleId))
                .orElseThrow();

        return ArticleReadResponse.from(
                articleQueryModel,
                viewClient.count(articleId)
        );
    }

    private Optional<ArticleQueryModel> fetch(Long articleId) {
        Optional<ArticleQueryModel> articleQueryModelOptional = articleClient.read(articleId)
                .map(article -> ArticleQueryModel.create(
                        article,
                        commentClient.count(articleId),
                        likeClient.count(articleId)
                ));

        articleQueryModelOptional
                .ifPresent(articleQueryModel -> articleQueryModelRepository.create(articleQueryModel, Duration.ofDays(1)));
        log.info("[ArticleReadService.fetch] fetch data. articleId={}, isPresent={}", articleId, articleQueryModelOptional.isPresent());
        return articleQueryModelOptional;
    }

    public ArticleReadPageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticleReadPageResponse.of(
                readAll(readAllArticleIds(boardId, page, pageSize)),
                count(boardId)
        );
    }

    private List<ArticleReadResponse> readAll(List<Long> articleIds) {
        Map<Long, ArticleQueryModel> articleQueryModelMap = articleQueryModelRepository.readAll(articleIds);
        return articleIds.stream()
                .map(articleId -> articleQueryModelMap.containsKey(articleId) ?
                        articleQueryModelMap.get(articleId) :
                        fetch(articleId).orElse(null))
                .filter(Objects::nonNull)
                .map(articleQueryModel -> ArticleReadResponse.from(
                        articleQueryModel,
                        viewClient.count(articleQueryModel.getArticleId())
                ))
                .toList();
    }

    private List<Long> readAllArticleIds(Long boardId, Long page, Long pageSize) {
        List<Long> articleIds = articleIdListRepository.readAll(boardId, (page - 1) * pageSize, pageSize);
        if (pageSize == articleIds.size()) {
            log.info("[ArticleReadService.readAllArticleIds] return redis data.");
            return articleIds;
        }

        log.info("[ArticleReadService.readAllArticleIds] return origin data.");
        return articleClient.readAll(boardId, page, pageSize).getArticles().stream()
                .map(ArticleClient.ArticleResponse::getArticleId)
                .toList();
    }

    private Long count(Long boardId) {
        Long articleCount = boardArticleCountRepository.read(boardId);
        if (articleCount != null) {
            log.info("[ArticleReadService.count] return redis data.");
            return articleCount;
        }

        log.info("[ArticleReadService.count] return origin data.");
        Long originArticleCount = articleClient.count(boardId);
        boardArticleCountRepository.createOrUpdate(boardId, originArticleCount);
        return originArticleCount;
    }

    public List<ArticleReadResponse> readAllInfiniteScroll(Long boardId, Long lastArticleId, Long pageSize) {
        return readAll(
                readAllInfiniteScrollArticleIds(boardId, lastArticleId, pageSize)
        );
    }

    private List<Long> readAllInfiniteScrollArticleIds(Long boardId, Long lastArticleId, Long pageSize) {
        List<Long> articleIds = articleIdListRepository.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
        if (pageSize == articleIds.size()) {
            log.info("[ArticleReadService.readAllInfiniteScrollArticleIds] return redis data.");
            return articleIds;
        }
        log.info("[ArticleReadService.readAllInfiniteScrollArticleIds] return origin data.");
        return articleClient.readAllInfiniteScroll(boardId, pageSize, lastArticleId).stream()
                .map(ArticleClient.ArticleResponse::getArticleId)
                .toList();
    }

}
