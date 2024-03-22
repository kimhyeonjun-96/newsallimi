package hello.newsallimi.domain.member.repository;

import hello.newsallimi.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String username);
    Optional<Member> findByProviderAndProviderId(String provider, String providerId);

    Member findMemberById(Long id);
}
