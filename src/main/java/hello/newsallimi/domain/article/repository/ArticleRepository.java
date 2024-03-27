package hello.newsallimi.domain.article.repository;

import hello.newsallimi.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByTitle(String title);
    List<Article> findAll();

    List<Article> findByNewsId(Long id);

    // 언론사의 id 값을 사용하여 해당 언론사의 최신 기사를 가져오는 메서드
    @Query("SELECT a FROM Article a WHERE a.news.id = :newsId ORDER BY a.id DESC")
    Page<Article> findLatestArticlesByNewsId(@Param("newsId") Long newsId, Pageable pageable);
}
