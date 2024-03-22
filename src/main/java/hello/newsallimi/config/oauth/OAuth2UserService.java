package hello.newsallimi.config.oauth;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.config.oauth.provider.KakaoUserInfo;
import hello.newsallimi.config.oauth.provider.OAuth2UserInfo;
import hello.newsallimi.domain.member.Member;
import hello.newsallimi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // 카카오 로그인 유저를 인가코드를 통해서 가져옴
        log.info("userRequest clientRegistration = {}", userRequest.getClientRegistration()); // code를 통해 구성한 정보
        log.info("userRequest oAuth2User = {}", oAuth2User); // token을 통해 응답받은 회원정보
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }else {
            log.info("지원하는 로그인이 없어요!");
        }

        // 패스워드 암호화
        Optional<Member> userOptional = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String dummyPassword = encoder.encode(UUID.randomUUID().toString());

        Member member;
        if (userOptional.isPresent()) {
            // 기존 회원이라면 패스워드, 이메일 업데이트
            member = userOptional.get();
            member.updatePassword(dummyPassword);
            member.updateEmail(oAuth2UserInfo.getEmail());
            log.info("기존 회원 로그인 = {}", member.responseMemberName());
        }else{
            // 신규 회원이라면 자동으로 회원등록
            member = new Member(oAuth2UserInfo.getName(), dummyPassword, oAuth2UserInfo.getEmail(), new Timestamp(System.currentTimeMillis()) , oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
            memberRepository.save(member);
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
