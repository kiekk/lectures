package soono.board.common.event;

import soono.board.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.SOONO_BOARD_ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.SOONO_BOARD_ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload .class, Topic.SOONO_BOARD_ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload .class, Topic.SOONO_BOARD_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload .class, Topic.SOONO_BOARD_COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.SOONO_BOARD_LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.SOONO_BOARD_LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.SOONO_BOARD_VIEW),
    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String SOONO_BOARD_ARTICLE = "soono-board-article";
        public static final String SOONO_BOARD_COMMENT = "soono-board-comment";
        public static final String SOONO_BOARD_LIKE = "soono-board-like";
        public static final String SOONO_BOARD_VIEW = "soono-board-view";
    }
}
