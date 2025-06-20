package soono.board.hotarticle.service.event.handler;

import soono.board.common.event.Event;
import soono.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);

    boolean supports(Event<T> event);

    Long findArticleId(Event<T> event);
}
