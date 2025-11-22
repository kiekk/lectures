package tobyspring.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Table(name = "member", uniqueConstraints =
@UniqueConstraint(name = "UK_MEMBER_EMAIL_ADDRESS", columnNames = "email_address")
)
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @NaturalId
    private Email email;

    @Comment("닉네임")
    @Column(length = 100, nullable = false)
    private String nickname;

    @Comment("비밀번호 해시")
    @Column(length = 200, nullable = false)
    private String passwordHash;

    @Comment("회원 상태")
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MemberStatus status;

    public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.email = new Email(registerRequest.email());
        member.nickname = Objects.requireNonNull(registerRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(registerRequest.password()));
        member.status = MemberStatus.PENDING;
        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
    }

    public Boolean isActive() {
        return status == MemberStatus.ACTIVE;
    }
}
