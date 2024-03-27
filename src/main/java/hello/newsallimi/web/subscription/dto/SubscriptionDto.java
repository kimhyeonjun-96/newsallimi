package hello.newsallimi.web.subscription.dto;

import hello.newsallimi.domain.member.Member;
import hello.newsallimi.domain.news.News;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SubscriptionDto {

    private Long id;

    private Long memberId;
    private String memberName;

    private Long newsId;
    private String newsName;

    public SubscriptionDto(Long id, Long memberId, String memberName, Long newsId, String newsName) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.newsId = newsId;
        this.newsName = newsName;
    }
}
