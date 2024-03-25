package hello.newsallimi.domain.auth.service;

import hello.newsallimi.domain.auth.Auth;

public interface AuthServiceInterface {

    public boolean saveAuthToken(String code);

    public boolean saveAuthRefresh();

    public Auth getKakaoAuthToken(String code);

    public Auth getRefreshToken();
}
