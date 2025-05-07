package soono.board.hotarticle.service.eventhandler;

import soono.board.common.event.Event;
import soono.board.common.event.EventType;
import soono.board.common.event.payload.ArticleLikedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soono.board.hotarticle.repository.ArticleLikeCountRepository;
import soono.board.hotarticle.utils.TimeCalculatorUtil;

@Component
@RequiredArgsConstructor
public class ArticleLikedEventHandler implements EventHandler<ArticleLikedEventPayload> {

    private final ArticleLikeCountRepository articleLikeCountRepository;

    @Override
    public void handle(Event<ArticleLikedEventPayload> event) {
        ArticleLikedEventPayload payload = event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculatorUtil.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleLikedEventPayload> event) {
        return EventType.ARTICLE_LIKED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleLikedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
