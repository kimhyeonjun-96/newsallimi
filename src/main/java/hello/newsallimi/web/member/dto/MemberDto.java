package hello.newsallimi.web.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
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

    public MemberDto(Long id, String name, String password, String email, Timestamp joinDate, String provider, String accessToken) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.provider = provider;
        this.accessToken = accessToken;
    }
}