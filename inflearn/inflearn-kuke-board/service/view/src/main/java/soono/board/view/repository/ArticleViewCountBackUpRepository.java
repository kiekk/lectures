package soono.board.view.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soono.board.view.entity.ArticleViewCount;

public interface ArticleViewCountBackUpRepository extends JpaRepository<ArticleViewCount, Long> {
    @Query(
            value = """
                    UPDATE article_view_count
                    SET view_count = :viewCount
                    WHERE article_id = :articleId
                    AND view_count < :viewCount
                    """,
            nativeQuery = true
    )
    @Modifying
    int updateViewCount(@Param("articleId") Long articleId,
                        @Param("viewCount") Long viewCount);
}
