package com.example.inflearn.user.domain;

import com.example.inflearn.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.inflearn.mock.TestClockHolder;
import com.example.inflearn.mock.TestUuidHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("UserCreate 객체로 생성할 수 있다.")
    void userCreate() {
        // given
        final UserCreate userCreate = UserCreate.builder()
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Pangyo")
                .build();

        // when
        final String uuid = "aaaa-aaa-aaaa";
        User user = User.from(userCreate, new TestUuidHolder(uuid));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(user.getNickname()).isEqualTo("soono");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("UserUpdate 객체로 수정할 수 있다.")
    void userUpdate() {
        // given
        final String certificationCode = "aaaa-aaa-aaaa";
        final User user = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode(certificationCode)
                .lastLoginAt(100L)
                .build();

        final UserUpdate userUpdate = UserUpdate.builder()
                .nickname("soono update")
                .address("Pangyo")
                .build();

        // when
        final User updateUser = user.update(userUpdate);

        // then
        assertThat(updateUser.getId()).isEqualTo(1L);
        assertThat(updateUser.getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(updateUser.getNickname()).isEqualTo("soono update");
        assertThat(updateUser.getAddress()).isEqualTo("Pangyo");
        assertThat(updateUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(updateUser.getLastLoginAt()).isEqualTo(100L);
        assertThat(updateUser.getCertificationCode()).isEqualTo(certificationCode);
    }

    @Test
    @DisplayName("로그인을 할 수 있고 로그인 시 마지막 로그인 시간이 변경된다.")
    void userLogin() {
        // given
        final User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        final User loginUser = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(loginUser.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("유효한 인증 코드로 계정을 활성화 할 수 있다.")
    void userCertificate() {
        // given
        final String certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
        final User user = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode(certificationCode)
                .build();

        // when
        final User certificatedUser = user.certificate(certificationCode);

        // then
        assertThat(certificatedUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("잘못된 인증 코드로 계정을 활성화 하려하면 에러를 던진다.")
    void userCertificateFail() {
        // given
        final String certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
        final String wrongCertificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab";
        final User user = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode(certificationCode)
                .build();

        // when
        // then
        assertThatThrownBy(() -> user.certificate(wrongCertificationCode))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}
