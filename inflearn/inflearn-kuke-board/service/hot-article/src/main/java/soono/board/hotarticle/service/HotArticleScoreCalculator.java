package soono.board.hotarticle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soono.board.hotarticle.repository.ArticleCommentCountRepository;
import soono.board.hotarticle.repository.ArticleLikeCountRepository;
import soono.board.hotarticle.repository.ArticleViewCountRepository;

@Component
@RequiredArgsConstructor
public class HotArticleScoreCalculator {

    private final ArticleLikeCountRepository articleLikeCountRepository;
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleCommentCountRepository articleCommentCountRepository;

    private static final long ARTICLE_LIKE_COUNT_WEIGHT = 3; // 좋아요 수 가중치
    private static final long ARTICLE_COMMENT_COUNT_WEIGHT = 2; // 댓글 수 가중치
    private static final long ARTICLE_VIEW_COUNT_WEIGHT = 1; // 조회수 가중치

    public long calculate(Long articleId) {
        Long articleLikeCount = articleLikeCountRepository.read(articleId);
        Long articleViewCount = articleViewCountRepository.read(articleId);
        Long articleCommentCount = articleCommentCountRepository.read(articleId);

        return (articleLikeCount * ARTICLE_LIKE_COUNT_WEIGHT) +
                (articleCommentCount * ARTICLE_COMMENT_COUNT_WEIGHT) +
                (articleViewCount * ARTICLE_VIEW_COUNT_WEIGHT);
    }
}
