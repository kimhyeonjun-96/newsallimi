package hello.newsallimi.domain.subscription.service;

import hello.newsallimi.domain.member.Member;
import hello.newsallimi.domain.member.repository.MemberRepository;
import hello.newsallimi.domain.news.News;
import hello.newsallimi.domain.news.repository.NewsRepository;
import hello.newsallimi.domain.subscription.Subscription;
import hello.newsallimi.domain.subscription.repository.SubscriptionRepository;
import hello.newsallimi.web.subscription.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;

    public Page<Subscription> findAllSubscription(Pageable pageable) {
        return subscriptionRepository.findAll(pageable);
    }

    public Page<SubscriptionDto> findAllSubscriptionByMemmber(Long id, Pageable pageable) {

        Page<Subscription> subscriptions = subscriptionRepository.findAllByMemberId(id, pageable);
        return subscriptions.map(Subscription::convertToSubscriptionDto);
    }

    public void subscribe(String memberName, String newsName) {

        Member findMember = memberRepository.findByName(memberName).get();
        News findNews = newsRepository.findByNewsName(newsName).get();

        Subscription saveSubscription = new Subscription(findMember, findNews);
        findMember.addSubscription(saveSubscription);
        findNews.addSubscription(saveSubscription);
        subscriptionRepository.save(saveSubscription);
    }

    public void duplicateCheck(String memberName, String newsName) {

        Member findMember = memberRepository.findByName(memberName).get();
        News findNews = newsRepository.findByNewsName(newsName).get();

        List<Subscription> memberSubscripNewsoList = subscriptionRepository.findByMemberId(findMember.responseMemberId());
        for (Subscription subscription : memberSubscripNewsoList) {
            if (subscription.findNewsNameByNewsName().equals(findNews.responseNewsName())) {
                throw new IllegalStateException("이미 구독중인 언론사입니다.");
            }
        }
    }

    public void unsubscribe(Long subscriptionId, String memberName, String newsName) {

        Member findMember = memberRepository.findByName(memberName).get();
        News findNews = newsRepository.findByNewsName(newsName).get();
        Subscription findSubscription = subscriptionRepository.findById(subscriptionId).get();

        findMember.removeSubscription(findSubscription);
        findNews.removeSubscription(findSubscription);
        subscriptionRepository.delete(findSubscription);
    }
}
