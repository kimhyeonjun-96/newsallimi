package hello.newsallimi.domain.news.service;

import hello.newsallimi.domain.article.Article;
import hello.newsallimi.domain.article.repository.ArticleRepository;
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
public class CrawlingArticles implements SchedulingConfigurer {

    private final NewsRepository newsRepository;
    private final ArticleRepository articleRepository;
    private final WebDriverProvider webDriverProvider;


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                // 트리거가 될 수 있는 로직을 작성
                crawlingArticles(), triggerContext -> {
//                    CronTrigger cronTrigger = new CronTrigger("0 * * * * ?"); // 매분마다 실행 ( 테스트 )
                    CronTrigger cronTrigger = new CronTrigger("0 0 * * * ?"); // 매 시간마다 실행 ( 실제 )
                    return cronTrigger.nextExecutionTime(triggerContext).toInstant();
                }
        );

    }

    @Transactional
    protected Runnable crawlingArticles() {

        return () -> {
            WebDriver driver = webDriverProvider.getWebDriver();
            try {
                List<News> newsList = newsRepository.findAll();
                for (News news : newsList) {
                    driver.get(news.responseUrl());
                    List<WebElement> newspaperArea = driver.findElements(By.tagName("dl"));

                    log.info("========== 언론사 이름 : " + news.responseNewsName() + " ==========");
                    String title = "";
                    String url = "";
                    String description = "";
                    String articleNewsName = "";
                    String imgUrl = "";
                    String alt = "";

                    for (WebElement webElement : newspaperArea) {

                        List<WebElement> dt = webElement.findElements(By.tagName("dt"));
                        for (WebElement element : dt) {
                            if (element.getText().equals("")) continue;

                            title = element.getText();
                            url = element.findElement(By.tagName("a")).getAttribute("href");
                            System.out.println("title = " + title);
                            System.out.println("url = " + url);
                        }


                        List<WebElement> lede = webElement.findElements(By.className("lede"));
                        for (WebElement element : lede) {
                            description = element.getText();
                            System.out.println("description = " + description);
                        }

                        List<WebElement> writing = webElement.findElements(By.className("writing"));
                        for (WebElement element : writing) {
                            articleNewsName = element.getText();
                            System.out.println("articleNewsName = " + articleNewsName);
                        }

                        if(news.responseNewsName().equals("언론사 최신기사")){
                            List<WebElement> photo = webElement.findElements(By.className("photo"));
                            for (WebElement element : photo) {
                                List<WebElement> img = element.findElements(By.tagName("img"));
                                for (WebElement webElement1 : img) {
                                    imgUrl = webElement1.getAttribute("src");
                                    alt = webElement1.getAttribute("alt");
                                }
                            }
                            System.out.println("언론사 최신기사 이미지 Url = " + imgUrl);
                            System.out.println("언론사 최신기사 alt = " + alt);
                        }


                        Article article = new Article(title, description, url, articleNewsName, imgUrl, alt);
                        news.addArticle(article);
                        articleRepository.save(article);
                    }
                }
            } catch (Exception e) {
                log.info("error : " + e.getMessage());
            } finally {
                driver.quit();
            }
        };
    }
}
