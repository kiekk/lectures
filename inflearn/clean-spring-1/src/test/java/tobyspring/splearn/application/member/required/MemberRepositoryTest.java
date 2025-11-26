package tobyspring.splearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.member.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        var member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var foundMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
        var member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member);

        var member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}