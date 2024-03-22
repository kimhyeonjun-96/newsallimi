package hello.newsallimi.web.home.controller;

import hello.newsallimi.config.auth.PrincipalDetails;
import hello.newsallimi.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
//    public String home(Model model, @PageableDefault(size = 10) Pageable pageable) {
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        Page<ArticleDTO> allArticles = articleService.getAllArticles(pageable);
//        model.addAttribute("allArticles", allArticles);
        return "home";
    }

}
