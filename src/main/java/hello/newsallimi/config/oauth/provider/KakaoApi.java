package hello.newsallimi.config.oauth.provider;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoApi {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoApiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

}
