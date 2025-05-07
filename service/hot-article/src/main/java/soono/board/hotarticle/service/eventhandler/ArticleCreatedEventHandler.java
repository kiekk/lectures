package soono.board.hotarticle.service.eventhandler;

import soono.board.common.event.Event;
import soono.board.common.event.EventType;
import soono.board.common.event.payload.ArticleCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soono.board.hotarticle.repository.ArticleCreatedTimeRepository;
import soono.board.hotarticle.utils.TimeCalculatorUtil;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {

    private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

    @Override
    public void handle(Event<ArticleCreatedEventPayload> event) {
        ArticleCreatedEventPayload payload = event.getPayload();

        articleCreatedTimeRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getCreatedAt(),
                TimeCalculatorUtil.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleCreatedEventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleCreatedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
