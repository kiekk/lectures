package tobyspring.splearn.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import tobyspring.splearn.domain.AbstractEntity;
import tobyspring.splearn.domain.shard.Email;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
@ToString(callSuper = true, exclude = "detail")
public class Member extends AbstractEntity {
    @NaturalId
    private Email email;
    private String nickname;
    private String passwordHash;
    private MemberStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberDetail detail;

    public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.email = new Email(registerRequest.email());
        member.nickname = Objects.requireNonNull(registerRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(registerRequest.password()));
        member.status = MemberStatus.PENDING;
        member.detail = MemberDetail.create();
        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        status = MemberStatus.ACTIVE;
        detail.setActivatedAt();
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        status = MemberStatus.DEACTIVATED;
        detail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        nickname = Objects.requireNonNull(updateRequest.nickname());
        detail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
    }

    public Boolean isActive() {
        return status == MemberStatus.ACTIVE;
    }
}
