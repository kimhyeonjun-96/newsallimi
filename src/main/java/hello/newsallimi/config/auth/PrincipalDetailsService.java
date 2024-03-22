package hello.newsallimi.config.auth;

import hello.newsallimi.domain.member.Member;
import hello.newsallimi.domain.member.repository.MemberRepository;
import hello.newsallimi.web.member.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PrincipalDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByName(username).get();
        if (member == null) {
            return null;
        }else{
            return new PrincipalDetails(member);
        }
    }
}
