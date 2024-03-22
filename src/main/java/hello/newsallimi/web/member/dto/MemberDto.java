package hello.newsallimi.web.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class MemberDto {

    private Long id;
    private String name;
    private String password;
    private String email;
    private Timestamp joinDate;
    private String provider;

    public MemberDto(Long id, String name, String password, String email, Timestamp joinDate, String provider) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.provider = provider;
    }
}