package hello.newsallimi.domain.article.service;

import hello.newsallimi.domain.article.Article;
import hello.newsallimi.domain.article.repository.ArticleRepository;
import hello.newsallimi.domain.news.News;
import hello.newsallimi.domain.news.repository.NewsRepository;
import hello.newsallimi.domain.news.service.NewsService;
import hello.newsallimi.web.article.dto.ArticleDto;
import hello.newsallimi.web.news.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final MessageSource messageSource;
    private final NewsRepository newsRepository;
    private final ArticleRepository articleRepository;

    // 언론사 최신기사 반환
    public Page<ArticleDto> findLatestNews(Pageable pageable) {

        String newsName = messageSource.getMessage("latest.articles", null, Locale.getDefault());
        News findNews = newsRepository.findByNewsName(newsName).get();
        Page<Article> latestArticlesByNewsId = articleRepository.findLatestArticlesByNewsId(findNews.responseId(), pageable);
        List<ArticleDto> latestArticleDtoList = new ArrayList<>();
        for (Article article : latestArticlesByNewsId) {
            latestArticleDtoList.add(article.convertToArticleDto());
        }
        return new PageImpl<>(latestArticleDtoList, pageable, latestArticlesByNewsId.getTotalElements());
    }

}
