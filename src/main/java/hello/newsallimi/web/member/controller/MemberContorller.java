package hello.newsallimi.web.member.controller;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.config.oauth.provider.KakaoApi;
import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.domain.subscription.Subscription;
import hello.newsallimi.domain.subscription.service.SubscriptionService;
import hello.newsallimi.global.common.alertmessage.AlertMessage;
import hello.newsallimi.web.member.dto.MemberDto;
import hello.newsallimi.web.subscription.dto.SubscriptionDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberContorller {

    private final MemberService memberService;
    private final KakaoApi kakaoApi;
    private final SubscriptionService subscriptionService;

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginForm(){
        return "members/loginForm";
    }

    // 마이페이지 이동
    @GetMapping("/members/mypage")
    public String mypageForm(Model model, @PageableDefault(size = 10) Pageable pageable) {

        MemberDto memberDto = memberService.findMember(SecurityContextHolder.getContext().getAuthentication());
        Page<SubscriptionDto> allSubscriptionByMemmber = subscriptionService.findAllSubscriptionByMemmber(memberDto.getId(), pageable);

        model.addAttribute("memberDto", memberDto);
        model.addAttribute("allSubscriptionByMemmber", allSubscriptionByMemmber);
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
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
        mav.addObject("data", new AlertMessage("회원 탈퇴가 완료되었습니다.", redirectUrl));
        mav.setViewName("common/message");
        return mav;
    }

    // 회원 메시지 전달을 위한 액세스, 리프레시 토큰 발급
    @GetMapping("/setCode")
    public ModelAndView sendMessage(@RequestParam String code, @AuthenticationPrincipal PrincipalDetails principalDetails, ModelAndView mav, HttpSession session){

        log.info("code = {}", code);
        MemberDto memberDto = memberService.addToken(code, principalDetails);

        session.setAttribute("memberDto", memberDto); // 세션에 memberDto 저장
        mav.addObject("data", new AlertMessage("알림 허용이 완료되었습니다.", "/members/mypage"));
        mav.setViewName("common/message");
        return mav;
    }

    // 언론사 구독
    @PostMapping("/members/news/subscribe")
    public ResponseEntity<String> subscribe(@Validated @RequestParam("memberName") String memberName, @Validated @RequestParam("newsName") String newsName, Model model) {

        subscriptionService.duplicateCheck(memberName, newsName);
        subscriptionService.subscribe(memberName, newsName);
        log.info("subscribe :: 구독 완료");
        return ResponseEntity.ok().body("구독 하였습니다.");
    }

    // 언론사 구독 취소
    @PostMapping("/members/news/unsubscribe")
    public ResponseEntity<String> unsubscribe(@Validated @RequestParam("subscriptionId") Long subscriptionId, @Validated @RequestParam("memberName") String memberName, @Validated @RequestParam("newsName") String newsName, Model model) {

        subscriptionService.unsubscribe(subscriptionId, memberName, newsName);
        log.info("unsubscribe :: 구독 취소 완료");
        return ResponseEntity.ok().body("구독을 취소하였습니다.");
    }


}
