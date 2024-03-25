package hello.newsallimi.domain.article;

import hello.newsallimi.domain.news.News;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;
    private String description;
    private String url;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;


    public Article(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Article(String title, String description, String url, News news) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.news = news;
    }

    public void updateNews(News news) {
        this.news = news;
    }
}
