package hello.newsallimi.domain.member;

import hello.newsallimi.web.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String password;
    private String email;

    @Column(name = "join_date")
    private Timestamp joinDate;

    // 소셜 로그인 제공 정보
    private String provider;
    private String providerId;

    // Member 생성자들
    public Member(String name, String password, String email, Timestamp joinDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
    }

    public Member(String name, String password, String email, Timestamp joinDate, String provider, String providerId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.provider = provider;
        this.providerId = providerId;
    }

    // 패스워드 생성 편의 메서드
    public void updatePassword(String dummyPassword) {
        this.password = dummyPassword;
    }

    // 패스워드 생성 편의 메서드
    public void updateEmail(String email) {
        this.email = email;
    }

    // member id 반환 편의 메서드
    public Long responseMemberId() {
        return this.id;
    }

    // member name 반환 편의 메서드
    public String responseMemberName() {
        return this.name;
    }

    // member password 반환 편의 메서드
    public String responseMemberPassword() {
        return this.password;
    }

    // member email 반환 편의 메서드
    public String responseMemberEmail() {
        return this.email;
    }

    // member joinDate 반환 편의 메서드
    public Timestamp responseMemberJoinDate() {
        return this.joinDate;
    }

    // member 반환 편의 메서드
    public Member responseMember() {
        return this;
    }

    // convert to memberDto
    public MemberDto convertToMemberDto(){
        return new MemberDto(this.id, this.name, this.password, this.email, this.joinDate, this.provider);
    }
}
