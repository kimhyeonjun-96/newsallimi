package hello.newsallimi.config.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender javaMailSender;
    private static final String SUBJECT = "[뉴스일라미 회원 탈퇴] 인증 메일입니다.";
    private static final String ADMIN_ADDRESS = "khjlovetest8427@naver.com";

    public boolean  sendCirtificationMail(String mail, String token) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
//            message.addRecipients(MimeMessage.RecipientType.TO, ADMIN_ADDRESS);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            String htmlContent = cirtificationMessage(token);

            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setFrom(ADMIN_ADDRESS);
            mimeMessageHelper.setSubject(SUBJECT);
            mimeMessageHelper.setText(htmlContent, true);

            javaMailSender.send(message);
        }catch (Exception e) {

            log.info("sendCirtificationMail :: FAILE");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String cirtificationMessage(String token){

        String cirtificationMessage = "";
        cirtificationMessage += "<h1 style='text-align: center;'>[뉴스 알리미] 인증메일 </h2>";
        cirtificationMessage += "<h3 style='text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>" + token + "</strong></h3>";
        return cirtificationMessage;
    }

}
