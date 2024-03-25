package hello.newsallimi.domain.news;

import hello.newsallimi.domain.article.Article;
import hello.newsallimi.domain.member.Member;
import hello.newsallimi.web.news.dto.NewsDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    private String newsName;
    private String url;

    @OneToMany(mappedBy = "news", fetch = FetchType.EAGER)
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "news")
    private List<Member> memberList = new ArrayList<>();


    public News(String newsName, String url) {
        this.newsName = newsName;
        this.url = url;
    }

    public void addArticle(Article article) {
        articleList.add(article);
        article.updateNews(this);
    }

    public String responseNewsName() {
        return this.newsName;
    }

    public String responseUrl() {
        return this.url;
    }


    public NewsDto convertToNewsDto() {
        return new NewsDto(this.id, this.newsName, this.url);
    }
}
