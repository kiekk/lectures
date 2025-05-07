package soono.board.hotarticle.service.eventhandler;

import soono.board.common.event.Event;
import soono.board.common.event.EventType;
import soono.board.common.event.payload.ArticleDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soono.board.hotarticle.repository.ArticleCreatedTimeRepository;
import soono.board.hotarticle.repository.HotArticleListRepository;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {

    private final HotArticleListRepository hotArticleListRepository;
    private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        articleCreatedTimeRepository.delete(payload.getArticleId());
        hotArticleListRepository.remove(payload.getArticleId(), payload.getCreatedAt());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleDeletedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
