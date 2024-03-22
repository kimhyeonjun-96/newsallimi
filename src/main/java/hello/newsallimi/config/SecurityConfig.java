package hello.newsallimi.config;

import hello.newsallimi.config.oauth.OAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    /**
     * password 암호화를 위해 BCryptPasswordEncoder클래스르 생성하여 빈에 등록
     */
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/signup", "/login", "/send", "/css/**", "/js/**", "/images/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("memberId")
                        .passwordParameter("memberPwd")
                        .failureHandler(customAuthenticationFailureHandler()))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll()
                        )
                .oauth2Login(oatuh2 -> oatuh2
                        .loginPage("/login")
                        .successHandler(successHandler())
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                ).build();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
                // 실패 이유 로깅
                logger.info("Authentication failed: " + exception.getMessage());

                // 실패 시 리다이렉트 할 페이지 등을 설정
                response.sendRedirect("/login?error=true");
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                String name = authentication.getName();

                // /addOption으로 리다렉트, 이후 메시지 추가 동의 요청
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//                response.sendRedirect("/addOption");
                response.sendRedirect("/");
                response.getWriter().flush();
            }
        };
    }
}
