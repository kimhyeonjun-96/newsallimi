package hello.newsallimi.web.home.controller;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.domain.member.Member;
import hello.newsallimi.global.common.message.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, ModelAndView mav) {
        String message = (String) request.getAttribute("message");
        if (message != null) {
            mav.addObject("data", new Message(message, "/"));
            mav.setViewName("common/message");
        }else {
            mav.setViewName("home");
        }
        return mav;
    }

//    @GetMapping("/")
//    public String home(Model model, @PageableDefault(size = 10) Pageable pageable) {
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        Page<ArticleDTO> allArticles = articleService.getAllArticles(pageable);
//        model.addAttribute("allArticles", allArticles);

        return "home";
    }
}
