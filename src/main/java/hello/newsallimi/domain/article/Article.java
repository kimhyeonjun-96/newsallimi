package hello.newsallimi.domain.article;

import hello.newsallimi.domain.news.News;
import hello.newsallimi.web.article.dto.ArticleDto;
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
    private String subNewsName;
    private String imgUrl;
    private String alt;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    public Article(String title, String description, String url, String subNewsName, String imgUrl, String alt) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.subNewsName = subNewsName;
        this.imgUrl = imgUrl;
        this.alt = alt;
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

    public ArticleDto convertToArticleDto() {

        return new ArticleDto(this.id
                , this.title
                , this.description
                , this.url
                , this.subNewsName
                , this.imgUrl
                , this.alt
                , this.news.responseId()
                , this.news.responseNewsName()
                , this.news.responseUrl()
        );
    }

}
