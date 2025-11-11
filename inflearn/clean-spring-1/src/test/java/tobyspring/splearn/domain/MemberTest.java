package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    @Test
    void createMember() {
        var member = new Member("soono@splearn.app", "soono", "secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "soono", "secret"))
                .isInstanceOf(Exception.class);
    }

    @Test
    void activate() {
        var member = new Member("soono@splearn.app", "soono", "secret");

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        var member = new Member("soono@splearn.app", "soono", "secret");

        member.activate();

        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        var member = new Member("soono@splearn.app", "soono", "secret");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail1() {
        var member = new Member("soono@splearn.app", "soono", "secret");

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivateFail2() {
        var member = new Member("soono@splearn.app", "soono", "secret");
        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

}