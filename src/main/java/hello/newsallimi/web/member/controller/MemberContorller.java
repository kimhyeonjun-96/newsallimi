package hello.newsallimi.web.member.controller;

import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.web.member.dto.MemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberContorller {

    private final MemberService memberService;

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginForm(){
        return "members/loginForm";
    }

    // 마이페이지 이동
    @GetMapping("/members/mypage")
    public String mypageForm(Model model){

        MemberDto memberDto = memberService.findMember(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("memberDto", memberDto);
        return "members/mypageForm";
    }

    // 회원탈퇴 페이지 이동
    @GetMapping("/members/{id}/withdrawal")
    public String withdrawalForm(@PathVariable Long id, Model model) {

        MemberDto memberDto = memberService.findMemberById(id);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("verificationRequested", false); // 초기값으로 false 설정
        model.addAttribute("emailVerified", false);
        return "members/withdrawalForm";
    }

    // 회원 탈퇴
    @PostMapping("/members/{id}/withdrawal")
    public String withdrawal(@ModelAttribute("memberDto")MemberDto memberDto){

        return memberService.withdrawal(memberDto);
    }

}
