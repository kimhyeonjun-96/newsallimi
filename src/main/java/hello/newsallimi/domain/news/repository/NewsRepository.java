package hello.newsallimi.domain.news.repository;

import hello.newsallimi.domain.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    News findByNewsName(String newsName);
    List<News> findAll();


}
