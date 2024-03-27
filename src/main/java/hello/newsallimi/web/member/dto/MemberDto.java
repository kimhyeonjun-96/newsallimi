package hello.newsallimi.web.member.dto;

import hello.newsallimi.domain.subscription.Subscription;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class MemberDto {

    private Long id;
    private String name;
    private String password;
    private String email;
    private Timestamp joinDate;
    private String provider;
    private String accessToken;

    private List<Subscription> subscriptionList = new ArrayList<>();

    public MemberDto(Long id, String name, String password, String email, Timestamp joinDate, String provider, String accessToken, List<Subscription> subscriptionList) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.provider = provider;
        this.accessToken = accessToken;
        this.subscriptionList = subscriptionList;
    }
}