package hello.newsallimi.domain.auth.dto;


import lombok.Getter;

@Getter
public class AuthDto {

    private Long id;
    private String accessToken;
    private String refreshToken;

    public AuthDto(Long id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

    public AuthDto(Long id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
