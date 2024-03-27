package hello.newsallimi.web.home.controller;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.domain.article.service.ArticleService;
import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.domain.news.service.NewsService;
import hello.newsallimi.global.common.alertmessage.AlertMessage;

import hello.newsallimi.web.article.dto.ArticleDto;
import hello.newsallimi.web.member.dto.MemberDto;
import hello.newsallimi.web.news.dto.NewsDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final NewsService newsService;
    private final ArticleService articleService;


    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, ModelAndView mav, @PageableDefault(size = 9) Pageable pageable) {

        // 로그인 완료 알림 설정
        String message = (String) request.getAttribute("message");
        if (message != null){
            mav.addObject("data", new AlertMessage(message, "/"));
            mav.setViewName("common/message");
        }else {
            mav.setViewName("home");
        }

        // 모든 언론사 가져오기
        Page<NewsDto> allNews = newsService.findAllNews(pageable);
        mav.addObject("allNews", allNews);

        // 최신 기사 가져오기
        Page<ArticleDto> latestNews = articleService.findLatestNews(pageable);
        mav.addObject("latestNews", latestNews);

        // 로그인 시 회원의 정보 가져오기
        if(principalDetails != null){
            MemberDto findMember = memberService.findMemberById(Long.parseLong(principalDetails.getName()));
            mav.addObject("memberDto", findMember);
        }else{
            mav.addObject("memberDto", null);
        }
        return mav;
    }
}
