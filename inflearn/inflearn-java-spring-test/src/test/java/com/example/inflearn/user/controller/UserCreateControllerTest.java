package com.example.inflearn.user.controller;

import com.example.inflearn.mock.TestContainer;
import com.example.inflearn.user.controller.response.UserResponse;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest {


    @Test
    @DisplayName("사용자를 등록한다. 회원가입 후 반환되는 사용자는 PENDING 상태이며 개인정보는 제외하고 반환된다.")
    void userCreateTest() {
        // given
        final TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaa-aaa-aaaa")
                .build();

        // when
        final UserCreate userCreate = UserCreate.builder()
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .build();
        final UserResponse user = testContainer.userCreateController.create(userCreate);

        // then
        assertThat(user.getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(user.getNickname()).isEqualTo("soono");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(testContainer.userRepository.getById(user.getId()).getCertificationCode()).isEqualTo("aaaa-aaa-aaaa");
    }

}
