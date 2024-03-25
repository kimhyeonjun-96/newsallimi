package hello.newsallimi.global.common.alertmessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlertMessage {

    String message = "";
    String href = "";

    public AlertMessage(String message, String href) {
        this.message = message;
        this.href = href;
    }
}
