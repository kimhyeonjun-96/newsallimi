package hello.newsallimi.domain.member.service;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.config.mail.EmailProvider;
import hello.newsallimi.domain.auth.Auth;
import hello.newsallimi.domain.auth.service.AuthService;
import hello.newsallimi.domain.member.Member;
import hello.newsallimi.domain.member.repository.MemberRepository;
import hello.newsallimi.web.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailProvider emailProvider;
    private final AuthService authService;

    // 회원 탈퇴를 위한 이메일 요청
    public  boolean requestEmailVerification(String name, String email) {

        Member findMember = memberRepository.findByName(name).get();
        String createToken = createCertificationKey();
        findMember.updateToken(createToken);
        return emailProvider.sendCirtificationMail(email, createToken);
    }

    // 회원 탈퇴 요청시 토큰 생성
    private String createCertificationKey() {
        return UUID.randomUUID().toString();
    }

    // 회원 탈퇴를 위한 이메일 인증
    private boolean sendEmail(MemberDto memberDTO, String certificationKey){
        return emailProvider.sendCirtificationMail(memberDTO.getEmail(),certificationKey);
    }

    // 회원 탈퇴를 위한 토큰 검증
    public boolean verifyEmailToken(String name, String receivedToken) {

        Member findMember = memberRepository.findByName(name).get();
        String storedToken = findMember.responseToken();
        if(storedToken.equals(receivedToken)){
            return true;
        }else{
            return false;
        }
    }

    // 회원 탈퇴
    @Transactional
    public String withdrawal(MemberDto memberDTO) {

        Member member = memberRepository.findMemberById(memberDTO.getId());
        memberRepository.delete(member);
        return "/logout";
    }

    // 회원 이름을 통한 회원 조회
    public MemberDto findMember(Authentication authentication) {

        String name = authentication.getName();
        Member member = memberRepository.findByName(name).get();
        return member.convertToMemberDto();
    }

    // 회원 id를 통한 회원 조회
    public MemberDto findMemberById(Long id) {

        Member findMember = memberRepository.findMemberById(id);
        return findMember.convertToMemberDto();
    }

    @Transactional
    public MemberDto addToken(String code, PrincipalDetails principalDetails) {

        String memberName = principalDetails.getMember().responseMemberName();
        Member findMember = memberRepository.findByName(memberName).get();
        Auth authToken = authService.saveAuthToken(code, findMember);
        findMember.updateAuthToken(authToken);
        return findMember.convertToMemberDto();
    }

}
