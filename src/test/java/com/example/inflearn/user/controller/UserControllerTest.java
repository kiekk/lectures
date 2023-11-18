package com.example.inflearn.user.controller;

import com.example.inflearn.common.domain.exception.ResourceNotFoundException;
import com.example.inflearn.mock.TestContainer;
import com.example.inflearn.user.controller.response.MyProfileResponse;
import com.example.inflearn.user.controller.response.UserResponse;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.domain.UserUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserControllerTest {

    @Test
    @DisplayName("사용자는 특정 유저의 정보를 전달 받을 수 있다.")
    void userTest() {
        // given
        final TestContainer testContainer = TestContainer.builder().build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaa-aaaa")
                .lastLoginAt(100L)
                .build());


        // when
        final UserResponse result = testContainer.userController.getUserById(1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(result.getNickname()).isEqualTo("soono");
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 유저의 아이디로 api 호출할 경우 404 응답을 받는다.")
    void userTest_fail() {
        // given
        final TestContainer testContainer = TestContainer.builder().build();

        // when
        final Long userId = 1L;
        assertThatThrownBy(() -> testContainer.userController.getUserById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Users에서 ID %s를 찾을 수 없습니다.".formatted(userId));
    }

    @Test
    @DisplayName("사용자는 인증 코드로 계정을 활성화 시킬 수 있다.")
    void userVerifyCode() {
        // given
        final TestContainer testContainer = TestContainer.builder().build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaa-aaaa")
                .lastLoginAt(100L)
                .build());

        // when
        final ResponseEntity<Void> result = testContainer.userController.verifyEmail(1L, "aaaa-aaa-aaaa");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(testContainer.userRepository.getById(1L).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("사용자는 내 정보를 불러올 때 개인정보인 주소도 가져올 수 있다.")
    void userInfoAddressTest() {
        // given
        final TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaa-aaaa")
                .lastLoginAt(100L)
                .build());

        // when
        final MyProfileResponse myProfileResponse = testContainer.userController.getMyInfo("shyoon991@gmail.com");

        // then
        assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("사용자는 내 정보를 수정할 수 있다.")
    void userUpdateMeTest() {
        // given
        final TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaa-aaaa")
                .lastLoginAt(100L)
                .build());

        // when
        final UserUpdate userUpdate = UserUpdate.builder()
                .address("Pangyo")
                .nickname("soono update")
                .build();
        final MyProfileResponse myProfileResponse = testContainer.userController.updateMyInfo("shyoon991@gmail.com", userUpdate);

        // then
        assertThat(myProfileResponse.getAddress()).isEqualTo("Pangyo");
        assertThat(myProfileResponse.getNickname()).isEqualTo("soono update");
    }

}
