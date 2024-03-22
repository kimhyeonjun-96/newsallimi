package hello.newsallimi.domain.member.service;

import hello.newsallimi.config.mail.EmailProvider;
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

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailProvider emailProvider;

    // 회원 탈퇴를 위한 이메일 요청
    public  boolean requestEmailVerification(String email) {

        String createToken = createCertificationKey();
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

    // 회원 탈퇴
    @Transactional
    public String withdrawal(MemberDto memberDTO) {
        if(sendEmail(memberDTO, createCertificationKey())){
            log.info("이메일 전송 성공");
            Member member = memberRepository.findMemberById(memberDTO.getId());
            memberRepository.delete(member);
            return "redirect:/logout";
        }else {
            log.info("이메일 전송 실패");
            throw new ServiceException("인증이 실패하였습니다.");
        }
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
}
