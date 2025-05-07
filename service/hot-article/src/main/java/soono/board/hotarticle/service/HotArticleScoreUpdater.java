package soono.board.hotarticle.service;

import soono.board.common.event.Event;
import soono.board.common.event.EventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soono.board.hotarticle.repository.ArticleCreatedTimeRepository;
import soono.board.hotarticle.repository.HotArticleListRepository;
import soono.board.hotarticle.service.eventhandler.EventHandler;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HotArticleScoreUpdater {

    private final HotArticleListRepository hotArticleListRepository;
    private final HotArticleScoreCalculator hotArticleScoreCalculator;
    private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

    private static final long HOT_ARTICLE_COUNT = 10;
    private static final Duration HOT_ARTICLE_TTL = Duration.ofDays(10);

    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {
        Long articleId = eventHandler.findArticleId(event);
        LocalDateTime createdTime = articleCreatedTimeRepository.read(articleId);

        if (!isArticleCreatedToday(createdTime)) {
            return;
        }

        // 각 repository에 데이터 등록
        eventHandler.handle(event);

        long score = hotArticleScoreCalculator.calculate(articleId);
        hotArticleListRepository.add(
                articleId,
                createdTime,
                score,
                HOT_ARTICLE_COUNT,
                HOT_ARTICLE_TTL);
    }

    private boolean isArticleCreatedToday(LocalDateTime createdTime) {
        return createdTime != null && createdTime.toLocalDate().isEqual(LocalDateTime.now().toLocalDate());
    }
}
