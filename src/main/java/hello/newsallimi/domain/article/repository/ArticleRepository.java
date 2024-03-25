package hello.newsallimi.domain.article.repository;

import hello.newsallimi.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByTitle(String title);
    List<Article> findAll();
}
