package hello.newsallimi.domain.news.config;

import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class WebDriverProvider {

    private final MessageSource messageSource;

    public WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver", messageSource.getMessage("path", null, Locale.getDefault()));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);
        return driver;
    }
}
