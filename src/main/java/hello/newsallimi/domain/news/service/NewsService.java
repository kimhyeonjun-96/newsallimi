package hello.newsallimi.domain.news.service;

import hello.newsallimi.domain.news.News;
import hello.newsallimi.domain.news.repository.NewsRepository;
import hello.newsallimi.web.news.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    // 모든 언론사 반환
    public Page<NewsDto> findAllNews(Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<News> newsPage = newsRepository.findAll(pageable);

        List<NewsDto> newsDtoList = new ArrayList<>();
        for (News news : newsPage) {
            if(news.responseNewsName().equals("언론사 최신기사")) continue;
            newsDtoList.add(news.convertToNewsDto());
        }
        return new PageImpl<>(newsDtoList, pageable, newsPage.getTotalElements());
    }

}
