package hello.newsallimi.web.mail.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;


@Getter @Setter
@NoArgsConstructor
public class EmailCirtificationRequestDto {

    private Long id;
    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요")
    private String email;

}
