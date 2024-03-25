package hello.newsallimi.web.news.dto;


import lombok.Getter;

@Getter
public class NewsDto {

    private Long id;
    private String newsName;
    private String url;

    public NewsDto(Long id, String newsName, String url) {
        this.id = id;
        this.newsName = newsName;
        this.url = url;
    }
}
