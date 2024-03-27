package hello.newsallimi.domain.subscription;

import hello.newsallimi.domain.member.Member;
import hello.newsallimi.domain.news.News;
import hello.newsallimi.web.subscription.dto.SubscriptionDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    public Subscription(Member member, News news) {
        this.member = member;
        this.news = news;
    }

    public SubscriptionDto convertToSubscriptionDto() {
        return new SubscriptionDto(
                this.id,
                this.member.responseMemberId(),
                this.member.responseMemberName(),
                this.news.responseId(),
                this.news.responseNewsName()
        );
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void addNews(News news) {
        this.news = news;
    }

    public String findNewsNameByNewsName(){
        return this.news.responseNewsName();
    }

    public void removeMember(Member member) {
        this.member = null;
    }

    public void removeNews(News news) {
        this.news = null;
    }
}
