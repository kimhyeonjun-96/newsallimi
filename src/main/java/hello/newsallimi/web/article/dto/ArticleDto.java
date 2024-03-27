package hello.newsallimi.web.article.dto;


import lombok.Getter;

@Getter
public class ArticleDto {

    private Long id;
    private String title;
    private String description;
    private String url;
    private String subNewsName;
    private String imgUrl;
    private String alt;

    private Long newsId;
    private String newsName;
    private String newsUrl;

    public ArticleDto(Long id, String title, String description, String url, String subNewsName, String imgUrl, String alt, Long newsId, String newsName, String newsUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.subNewsName = subNewsName;
        this.imgUrl = imgUrl;
        this.alt = alt;
        this.newsId = newsId;
        this.newsName = newsName;
        this.newsUrl = newsUrl;
    }
}
