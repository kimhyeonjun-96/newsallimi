package hello.newsallimi.domain.auth.repository;

import hello.newsallimi.domain.auth.Auth;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl {

    private final EntityManager em;
    private static String authToken;
    private static String refreshAuthToken;


    public boolean saveAuthToken(String authToken) {
        AuthRepositoryImpl.authToken = authToken;
        return AuthRepositoryImpl.authToken.equals(authToken);
    }

    public void saveAuthDto(Auth auth) {
        em.persist(auth);
    }


    public boolean saveRefreshAuthToken(String refrashToken) {
        AuthRepositoryImpl.refreshAuthToken = refrashToken;
        return AuthRepositoryImpl.refreshAuthToken.equals(refrashToken);
    }

    public String getAuthToken(Long authDtoId) {
        Auth authDto = em.find(Auth.class, authDtoId);
//        return authToken;
        return authDto.responseAccessToken();
    }

    public String getRefreshAuthToken() {
        return refreshAuthToken;
    }
}
