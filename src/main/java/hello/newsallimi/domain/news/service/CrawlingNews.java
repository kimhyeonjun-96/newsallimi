package hello.newsallimi.domain.news.service;

import hello.newsallimi.domain.news.News;
import hello.newsallimi.domain.news.config.WebDriverProvider;
import hello.newsallimi.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@EnableScheduling
public class CrawlingNews implements SchedulingConfigurer {

    private final NewsRepository newsRepository;
    private final WebDriverProvider webDriverProvider;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                // 트리거가 될 수 있는 로직을 작성
                crawlingNews(), triggerContext -> {
//                    CronTrigger cronTrigger = new CronTrigger("0 0 12 1 * ?"); // 매월 1일 00시에 실행 (실제)
                    CronTrigger cronTrigger = new CronTrigger("0 0 * * * ?"); // 1시간마다 실행 (테스트)
                    return cronTrigger.nextExecutionTime(triggerContext).toInstant();
                }
        );

    }

    @Transactional
    protected Runnable crawlingNews(){

        return () -> {
            WebDriver driver = webDriverProvider.getWebDriver();
            try {

                // 네이버 전체 언론사 페이지
                driver.get("https://news.naver.com/main/officeList.naver"); //크롤링할 사이트의 url, 즉 해당 url로 이동한다.
                List<WebElement> group_list = driver.findElements(By.className("group_list"));

                for (WebElement element : group_list) {
                    log.info("***** type06_headline start *****");

                    // 각 언론사 페이지 url
                    // 언론사들 저장
                    List<WebElement> url_list = element.findElements(By.tagName("a"));
                    for (WebElement webElement : url_list) {

                        News findNews = newsRepository.findByNewsName(webElement.getText()).get();
                        if(findNews == null){
                            if(webElement.getText().equals("연합뉴스")){ // 연합뉴스만 오류가 나서 제외
                                continue;
                            }
                            News newNews = new News(webElement.getText(), webElement.getAttribute("href"));
                            newsRepository.save(newNews);
                        }
                    }
                    log.info("===========================================");
                }
            } catch (Exception e) {
                log.info("error : " + e.getMessage());
            } finally {
                driver.quit();
            }
        };
    }
}
