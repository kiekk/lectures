package soono.board.view.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soono.board.view.repository.ArticleViewCountRepository;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private static final Long BACK_UP_BATCH_SIZE = 100L; // 개수 단위 백업

    public Long increase(Long articleId, Long userId) {
        Long count = articleViewCountRepository.increase(articleId);
        if (isBackUpRequired(count)) {
            articleViewCountBackUpProcessor.backUp(articleId, count);
        }
        return count;
    }

    private static boolean isBackUpRequired(Long count) {
        return count % BACK_UP_BATCH_SIZE == 0;
    }

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }

}
