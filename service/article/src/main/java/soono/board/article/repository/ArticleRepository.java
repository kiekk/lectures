package soono.board.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soono.board.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
