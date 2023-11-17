package com.example.inflearn.service;

import com.example.inflearn.common.domain.exception.ResourceNotFoundException;
import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.infrastructure.UserEntity;
import com.example.inflearn.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        final String email = "shyoon991@gmail.com";

        // when
        final UserEntity findUser = userService.getByEmail(email);

        // then
        assertThat(findUser.getNickname()).isEqualTo("soono");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저를_찾아올_수_있다() {
        // given
        final String email = "shyoon992@gmail.com";

        // when
        // then
        assertThatThrownBy(() -> userService.getByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Users에서 ID %s를 찾을 수 없습니다.".formatted(email));
    }

    @Test
    void getById_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        final Long id = 1L;

        // when
        final UserEntity findUser = userService.getById(id);

        // then
        assertThat(findUser.getNickname()).isEqualTo("soono");
    }

    @Test
    void getById는_PENDING_상태인_유저를_찾아올_수_있다() {
        // given
        final Long id = 2L;

        // when
        // then
        assertThatThrownBy(() -> userService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Users에서 ID %d를 찾을 수 없습니다.".formatted(id));
    }

    @Test
    void userCreateDto를_이용하여_유저를_생성할_수_있다() {
        // given
        final UserCreate userCreate = UserCreate.builder()
                .email("shyoon993@gmail.com")
                .address("Gyeongi")
                .nickname("soono3")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        final UserEntity createUser = userService.create(userCreate);

        // then
        assertThat(createUser.getId()).isNotNull();
        assertThat(createUser.getStatus()).isEqualTo(UserStatus.PENDING);
        // TODO : 현재는 certificationCode를 검증할 방법이 없다.
//        assertThat(createUser.getCertificationCode()).isEqualTo(???); // FIXME
    }

    @Test
    void userUpdateDto를_이용하여_유저를_수정할_수_있다() {
        // given
        final UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .address("Incheon")
                .nickname("soono3 update")
                .build();

        final Long userId = 1L;

        // when
        userService.update(userId, userUpdateDto);

        // then
        UserEntity user = userService.getById(userId);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("Incheon");
        assertThat(user.getNickname()).isEqualTo("soono3 update");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        final Long userId = 1L;
        userService.login(userId);

        // then
        UserEntity user = userService.getById(userId);
        // 마지막 로그인 시간을 가져올 수 없기 때문에 단순하게 0 이상인지만 판단
        assertThat(user.getLastLoginAt()).isGreaterThan(0L); // FIXME
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        final Long userId = 2L;
        final String certificationCode = "aaaa-aaa-aaa-aaaaab";
        userService.verifyEmail(userId, certificationCode);

        // then
        UserEntity user = userService.getById(userId);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        final Long userId = 2L;
        final String failCertificationCode = "aaaa-aaa-aaa-aaaaabddd";

        assertThatThrownBy(() -> userService.verifyEmail(userId, failCertificationCode))
                .isInstanceOf(CertificationCodeNotMatchedException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }
}
