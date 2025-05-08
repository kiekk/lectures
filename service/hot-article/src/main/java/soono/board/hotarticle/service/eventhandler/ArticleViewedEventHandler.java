package soono.board.hotarticle.service.eventhandler;

import soono.board.common.event.Event;
import soono.board.common.event.EventType;
import soono.board.common.event.payload.ArticleViewedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soono.board.hotarticle.repository.ArticleViewCountRepository;
import soono.board.hotarticle.utils.TimeCalculatorUtils;

@Component
@RequiredArgsConstructor
public class ArticleViewedEventHandler implements EventHandler<ArticleViewedEventPayload> {

    private final ArticleViewCountRepository articleViewCountRepository;

    @Override
    public void handle(Event<ArticleViewedEventPayload> event) {
        ArticleViewedEventPayload payload = event.getPayload();
        articleViewCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleViewCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleViewedEventPayload> event) {
        return EventType.ARTICLE_VIEWED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleViewedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
