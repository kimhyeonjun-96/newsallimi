package hello.newsallimi.web.mail.controller;

import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.web.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final MemberService memberService;

    @PostMapping("/request-email-verification")
    public ResponseEntity<String> requestEmailVerification(@RequestParam("name") String name, @RequestParam("email") String email, Model model) {

        boolean verificationRequested = memberService.requestEmailVerification(name, email);
        if(verificationRequested){
            model.addAttribute("verificationRequested", verificationRequested);
            return ResponseEntity.ok("success");
        }else{
            model.addAttribute("verificationRequested", verificationRequested);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

}
