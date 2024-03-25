package hello.newsallimi.web.home.controller;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.domain.news.service.NewsService;
import hello.newsallimi.global.common.alertmessage.AlertMessage;

import hello.newsallimi.web.member.dto.MemberDto;
import hello.newsallimi.web.news.dto.NewsDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final NewsService newsService;


    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, ModelAndView mav, @PageableDefault(size = 10) Pageable pageable) {

        // 로그인 완료 알림 설정
        String message = (String) request.getAttribute("message");
        if (message != null) {
            mav.addObject("data", new AlertMessage(message, "/"));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MemberDto memberDto = memberService.findMember(authentication);
            mav.addObject("membeDto", memberDto);
            mav.setViewName("common/message");
        }else {
            mav.addObject("membeDto", null);
            mav.setViewName("home");
        }

        // 모든 언론사 가져오기
        Page<NewsDto> allNews = newsService.findAllNews(pageable);
        mav.addObject("allNews", allNews);

        return mav;
    }
}
