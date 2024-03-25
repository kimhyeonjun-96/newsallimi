package hello.newsallimi.domain.auth;

import hello.newsallimi.domain.auth.dto.AuthDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;

    private String accessToken;
    private String refreshToken;

    public Auth(String accessToken) {
        this.accessToken = accessToken;
    }

    public Auth(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


    public Long responseId() {
        return this.id;
    }

    public String responseAccessToken() {
        return this.accessToken;
    }

    public String responseRefreshToken() {
        return this.refreshToken;
    }

    public AuthDto convertToAuthDto(){

        return new AuthDto(this.id, this.accessToken, this.refreshToken);
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
