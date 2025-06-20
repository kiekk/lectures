package soono.board.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import soono.board.like.entity.ArticleLike;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Optional<ArticleLike> findByArticleIdAndUserId(Long articleId, Long userId);

    // delete 쿼리 실행 후 실행 카운트 반환 쿼리
    @Modifying
    @Query(
            value = """
                    DELETE FROM article_like
                    WHERE article_id = :articleId
                      AND user_id = :userId
                    """,
            nativeQuery = true
    )
    int delete(Long articleId, Long userId);
}
