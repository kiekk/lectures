package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    @Disabled("널 체크는 lombok의 @NonNull로 대체")
    void constructorNullCheck() {
        assertThatThrownBy(() -> Member.register(createMemberRegisterRequest(null), passwordEncoder))
                .isInstanceOf(Exception.class);
    }

    @Test
    void activate() {
        assertThat(member.getDetail().getActivatedAt()).isNull();

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        assertThat(member.getDetail().getDeactivatedAt()).isNull();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail1() {
        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivateFail2() {
        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret1234", passwordEncoder)).isTrue();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("soono");

        member.changeNickname("sooo");

        assertThat(member.getNickname()).isEqualTo("sooo");
    }

    @Test
    void changePassword() {
        assertThat(member.verifyPassword("secret1234", passwordEncoder)).isTrue();

        member.changePassword("newSecret", passwordEncoder);

        assertThat(member.verifyPassword("secret1234", passwordEncoder)).isFalse();
        assertThat(member.verifyPassword("newSecret", passwordEncoder)).isTrue();
    }

    @Test
    void shouldBeActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> Member.register(createMemberRegisterRequest("invalid-email"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validEmail() {
        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        var updateRequest = new MemberInfoUpdateRequest("sooo", "soo100", "hello!!");
        member.updateInfo(updateRequest);

        assertThat(member.getDetail().getActivatedAt()).isNotNull();
        assertThat(member.getNickname()).isEqualTo(updateRequest.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateRequest.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(updateRequest.introduction());
    }
}