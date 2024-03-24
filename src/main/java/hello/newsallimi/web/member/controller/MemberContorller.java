package hello.newsallimi.web.member.controller;

import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.global.common.message.Message;
import hello.newsallimi.web.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
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
        model.addAttribute("emailVerified", false);
        return "members/withdrawalForm";
    }

    // 이메일 인증 검증
    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("name") String name, @RequestParam("token") String token, Model model) {

        boolean verifyEmailToken = memberService.verifyEmailToken(name, token);
        if (verifyEmailToken) {
            log.info("verifyEmail :: 이메일 인증 성공");
            model.addAttribute("emailVerified", true);
            return ResponseEntity.ok().body("{\"status\": \"success\", \"emailVerified\": true}");
        } else {
            log.info("verifyEmail :: 이메일 인증 실패");
            model.addAttribute("emailVerified", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"status\": \"fail\", \"emailVerified\": false}");
        }
    }

    // 회원 탈퇴
    @PostMapping("/members/{id}/withdrawal")
    public ModelAndView withdrawal(@ModelAttribute("memberDto")MemberDto memberDto, ModelAndView mav){

        String redirectUrl = memberService.withdrawal(memberDto);
        mav.addObject("data", new Message("회원 탈퇴가 완료되었습니다.", redirectUrl));
        mav.setViewName("common/message");
        return mav;
    }

}
