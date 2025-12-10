package tobyspring.splearn.application.member.provided;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.member.MemberStatus.PENDING;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
// junit-platform.properties 로 대체 가능
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
record MemberRegisterTest(
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
        checkValidation(new MemberRegisterRequest("soono@splearn.app", "soon", "soon1234"));
        checkValidation(new MemberRegisterRequest("soono@splearn.app", "soon___________________", "soon1234"));
        checkValidation(new MemberRegisterRequest("soono@splearn.app", "soono", "soon123"));
        checkValidation(new MemberRegisterRequest("soonosplearn", "soono", "soon1234"));
    }

    @Test
    void activate() {
        var member = memberRegister.register(createMemberRegisterRequest());

        var activatedMember = memberRegister.activate(member.getId());

        assertThat(activatedMember.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void deactivate() {
        var member = memberRegister.register(createMemberRegisterRequest());

        memberRegister.activate(member.getId());

        Member deactivatedMember = memberRegister.deactivate(member.getId());

        assertThat(deactivatedMember.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(deactivatedMember.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        var member = registerMember();

        memberRegister.activate(member.getId());

        MemberInfoUpdateRequest memberInfoUpdateRequest = new MemberInfoUpdateRequest("Soono", "soooo", "Hello!!!");
        var updatedMember = memberRegister.updateInfo(member.getId(),
                memberInfoUpdateRequest);

        assertThat(updatedMember.getNickname()).isEqualTo(memberInfoUpdateRequest.nickname());
        assertThat(updatedMember.getDetail().getProfile().address()).isEqualTo(memberInfoUpdateRequest.profileAddress());
        assertThat(updatedMember.getDetail().getIntroduction()).isEqualTo(memberInfoUpdateRequest.introduction());
    }

    @Test
    void updateInfoFail() {
        var member = registerMember();
        memberRegister.activate(member.getId());
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Soooo", "soooo", "Hello!!!"));

        var member2 = registerMember("soono2@splearn.app");
        memberRegister.activate(member2.getId());
        assertThatThrownBy(() -> memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("Sooooo", "soooo", "Hello!!!")))
                .isInstanceOf(DuplicateProfileException.class);
    }

    private void checkValidation(MemberRegisterRequest registerRequest) {
        assertThatThrownBy(() -> memberRegister.register(registerRequest))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private Member registerMember() {
        return memberRegister.register(MemberFixture.createMemberRegisterRequest());
    }

    private Member registerMember(String email) {
        return memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
    }

}
