package com.example.inflearn.user.service;

import com.example.inflearn.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.inflearn.common.domain.exception.ResourceNotFoundException;
import com.example.inflearn.mock.FakeMailSender;
import com.example.inflearn.mock.FakeUserRepository;
import com.example.inflearn.mock.TestClockHolder;
import com.example.inflearn.mock.TestUuidHolder;
import com.example.inflearn.user.controller.port.UserService;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserServiceImpl.builder()
                .uuidHolder(new TestUuidHolder("aaaa-aaa-aaaa"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .certificationService(new CertificationServiceImpl(fakeMailSender))
                .userRepository(fakeUserRepository)
                .build();

        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .certificationCode("aaaa-aaa-aaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("shyoon992@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .certificationCode("aaaa-aaa-aaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());
    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        final String email = "shyoon991@gmail.com";

        // when
        final User user = userService.getByEmail(email);

        // then
        assertThat(user.getNickname()).isEqualTo("soono");
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
        final User user = userService.getById(id);

        // then
        assertThat(user.getNickname()).isEqualTo("soono");
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

        // when
        final User createUser = userService.create(userCreate);

        // then
        assertThat(createUser.getId()).isNotNull();
        assertThat(createUser.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(createUser.getCertificationCode()).isEqualTo("aaaa-aaa-aaaa");
    }

    @Test
    void userUpdateDto를_이용하여_유저를_수정할_수_있다() {
        // given
        final UserUpdate userUpdate = UserUpdate.builder()
                .address("Incheon")
                .nickname("soono3 update")
                .build();

        final Long userId = 1L;

        // when
        userService.update(userId, userUpdate);

        // then
        final User user = userService.getById(userId);

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
        final User user = userService.getById(userId);
        // 마지막 로그인 시간을 가져올 수 없기 때문에 단순하게 0 이상인지만 판단
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        final Long userId = 2L;
        final String certificationCode = "aaaa-aaa-aaaaab";
        userService.verifyEmail(userId, certificationCode);

        // then
        final User user = userService.getById(userId);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        final Long userId = 2L;
        final String failCertificationCode = "aaaa-aaa-aaaaabddd";

        assertThatThrownBy(() -> userService.verifyEmail(userId, failCertificationCode))
                .isInstanceOf(CertificationCodeNotMatchedException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }
}
