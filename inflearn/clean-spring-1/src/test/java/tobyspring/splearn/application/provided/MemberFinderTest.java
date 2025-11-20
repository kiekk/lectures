package tobyspring.splearn.application.provided;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister) {

    @Test
    void find() {
        var member = memberRegister.register(createMemberRegisterRequest());

        var foundMember = memberFinder.find(member.getId());

        assertThat(foundMember.getId()).isEqualTo(member.getId());
    }

    @Test
    void findFail() {
        assertThatThrownBy(() -> memberFinder.find(9999L))
                .isInstanceOf(IllegalArgumentException.class);
    }

}