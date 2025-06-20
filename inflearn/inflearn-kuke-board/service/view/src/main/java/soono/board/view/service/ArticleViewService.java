package soono.board.view.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soono.board.view.repository.ArticleViewCountRepository;
import soono.board.view.repository.ArticleViewDistributedLockRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private final ArticleViewDistributedLockRepository articleViewDistributedLockRepository;
    private static final Long BACK_UP_BATCH_SIZE = 100L; // 개수 단위 백업
    private static final Duration TTL = Duration.ofMinutes(10); // 10분

    public Long increase(Long articleId, Long userId) {
        // lock을 획득하지 못하면 현재 count 반환
        if (!articleViewDistributedLockRepository.lock(articleId, userId, TTL)) {
            return articleViewCountRepository.read(articleId);
        }

        // lock을 획득하면 count 증가
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
