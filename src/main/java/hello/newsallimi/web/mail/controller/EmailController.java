package hello.newsallimi.web.mail.controller;

import hello.newsallimi.config.mail.EmailProvider;
import hello.newsallimi.domain.member.service.MemberService;
import hello.newsallimi.web.mail.dto.request.EmailCirtificationRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final MemberService memberService;

    @PostMapping("/request-email-verification")
    public String requestEmailVerification(@Validated @ModelAttribute EmailCirtificationRequestDto emailCirtificationRequestDto, Model model) {

//        boolean verificationRequested = memberService.requestEmailVerification(emailCirtificationRequestDto.getEmail());
//        if(verificationRequested){
//            model.addAttribute("verificationRequested", verificationRequested);
//            return new ResponseEntity<>(HttpStatus.OK);
            return "redirect:/members/ " + emailCirtificationRequestDto.getId() + "/withdrawal";
//        }else{
//            model.addAttribute("verificationRequested", verificationRequested);
//            return ResponseEntity.ok("fail");
            return "redirect:/members/ " + emailCirtificationRequestDto.getId() + "/withdrawal";
        }
    }

}
