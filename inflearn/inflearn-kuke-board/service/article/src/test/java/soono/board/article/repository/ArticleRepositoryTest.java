package soono.board.article.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soono.board.article.entity.Article;

import java.util.List;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findAllTest() {
        long boardId = 1L;
        long offset = 1499970L;
        long limit = 30L;
        List<Article> articles = articleRepository.findAll(boardId, offset, limit);
        log.info("article.size = {}", articles.size());
        for (Article article : articles) {
            log.info("article = {}", article);
        }
    }

    @Test
    void countTest() {
        long boardId = 1L;
        long limit = 10000L;
        Long count = articleRepository.count(boardId, limit);
        log.info("count = {}", count);
    }

    @Test
    void findAllInfiniteScrollTest() {
        long boardId = 1L;
        long limit = 30L;
        List<Article> articles = articleRepository.findAllInfiniteScroll(boardId, limit);
        log.info("article.size = {}", articles.size());
        for (Article article : articles) {
            log.info("article = {}", article);
        }

        Long lastArticleId = articles.getLast().getArticleId();

        List<Article> articles2 = articleRepository.findAllInfiniteScroll(boardId, lastArticleId, limit);
        log.info("article2.size = {}", articles2.size());
        for (Article article : articles2) {
            log.info("article2 = {}", article);
        }
    }

}