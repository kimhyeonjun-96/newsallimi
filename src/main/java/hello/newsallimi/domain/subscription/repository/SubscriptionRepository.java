package hello.newsallimi.domain.subscription.repository;

import hello.newsallimi.domain.subscription.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByMemberId(Long memberId);
    List<Subscription> findByNewsId(Long newsId);
    Subscription findByMemberIdAndNewsId(Long memberId, Long newsId);
    void deleteByMemberIdAndNewsId(Long memberId, Long newsId);

    Page<Subscription> findAllByMemberId(Long id, Pageable pageable);
}
