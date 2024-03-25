package hello.newsallimi.domain.auth.service;

import hello.newsallimi.domain.auth.Auth;
import hello.newsallimi.domain.auth.repository.AuthRepositoryImpl;
import hello.newsallimi.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService extends HttpCallService implements AuthServiceInterface{

    private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";
    public static String authCode;

    private final MessageSource messageSource;
    private final AuthRepositoryImpl authRepositoryImpl;

    @Transactional
    public boolean saveAuthToken(String code) {

        Auth authToken = getKakaoAuthToken(code);
        return authRepositoryImpl.saveAuthToken(authToken.responseAccessToken()) && authRepositoryImpl.saveRefreshAuthToken(authToken.responseRefreshToken());

    }

    @Transactional
    public Auth saveAuthToken(String code, Member member) {

        log.info("메시지 전달을 위한 토큰 발급 회원 이름 :: " + member.responseMemberName());
        Auth authToken = getKakaoAuthToken(code);
        authRepositoryImpl.saveAuthDto(authToken);
        return authToken;
    }

    @Override
    public boolean saveAuthRefresh() {

        Auth auth = getRefreshToken();
        return authRepositoryImpl.saveAuthToken(auth.responseAccessToken());
    }

    @Override
    public Auth getKakaoAuthToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";

        String tokenFailMessage = messageSource.getMessage("token.issued.fail", null, Locale.getDefault());
        String tokenSuccessMessage = messageSource.getMessage("token.issued.success", null, Locale.getDefault());

        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        headers.set("Content-Type", APP_TYPE_URL_ENCODED);
        parameters.add("code", code);
        parameters.add("grant_type", messageSource.getMessage("kakao.auth.grant_type", null, Locale.getDefault()));
        parameters.add("client_id", messageSource.getMessage("kakao.auth.client_id", null, Locale.getDefault()));
        parameters.add("redirect_url", messageSource.getMessage("kakao.auth.redirect_url", null, Locale.getDefault()));
        parameters.add("client_secret", messageSource.getMessage("kakao.auth.client_secret", null, Locale.getDefault()));

        HttpEntity<?> requestEntity = httpClientEntity(headers, parameters);

        log.info("======================== Auth Request Start ========================");
        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());

        accessToken = jsonData.get("access_token").toString();
        refreshToken = jsonData.get("refresh_token").toString();
        log.info("accessToken = {}", accessToken);
        log.info("refreshToken = {}", refreshToken);

        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)) {
            throw new ServiceException(tokenFailMessage);
        }

        Auth auth = new Auth(accessToken, refreshToken);

        authCode = accessToken;

        log.info("success = {}", tokenSuccessMessage);
        log.info("======================== Auth Request End ========================");

        return auth;
    }

    @Override
    public Auth getRefreshToken() {

        HttpHeaders headers = new HttpHeaders();
        String accessToken = "";
        String tokenFailMessage = messageSource.getMessage("token.issued.fail", null, Locale.getDefault());
        String tokenSuccessMessage = messageSource.getMessage("token.issued.success", null, Locale.getDefault());
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        headers.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("grant_type", messageSource.getMessage("kakao.auth.refresh_grant_type",null, Locale.getDefault()));
        parameters.add("client_id", messageSource.getMessage("kakao.auth.client_id",null, Locale.getDefault()));
        parameters.add("refresh_token", authRepositoryImpl.getRefreshAuthToken());
        parameters.add("client_secret", messageSource.getMessage("kakao.auth.client_secret",null, Locale.getDefault()));

        HttpEntity<?> requestEntiry = httpClientEntity(headers, parameters);

        log.info("======================== Authrefresh Request Start ========================");
        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntiry);
        JSONObject jsonData = new JSONObject(Integer.parseInt(response.getBody()));
        accessToken = jsonData.get("access_token").toString();
        if (StringUtils.isEmpty(accessToken)) {
            throw new ServiceException(tokenFailMessage);
        }

        Auth auth = new Auth(accessToken);

        log.info("success = {}", tokenSuccessMessage);
        log.info("======================== Authrefresh Request End ========================");

        return auth;
    }
}
