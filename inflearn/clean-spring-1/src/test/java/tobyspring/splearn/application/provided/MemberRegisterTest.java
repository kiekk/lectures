package tobyspring.splearn.application.provided;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.DuplicateEmailException;
import tobyspring.splearn.domain.MemberRegisterRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberStatus.PENDING;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
// junit-platform.properties 로 대체 가능
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public record MemberRegisterTest(
        MemberRegister memberRegister,
        MemberRepository memberRepository
) {

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void register() {
        var member = memberRegister.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("soono@splearn.app", "soon", "soon1234"));
        extracted(new MemberRegisterRequest("soono@splearn.app", "soon___________________", "soon1234"));
        extracted(new MemberRegisterRequest("soono@splearn.app", "soono", "soon123"));
        extracted(new MemberRegisterRequest("soonosplearn", "soono", "soon1234"));
    }

    private void extracted(MemberRegisterRequest registerRequest) {
        assertThatThrownBy(() -> memberRegister.register(registerRequest))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
