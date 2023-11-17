package com.example.inflearn.user.controller;

import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.domain.UserUpdate;
import com.example.inflearn.user.infrastructure.UserEntity;
import com.example.inflearn.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("사용자는 특정 유저의 정보를 전달 받을 수 있다.")
    void userTest() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("shyoon991@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("soono"))
                .andExpect(jsonPath("$.status").value(UserStatus.ACTIVE.name()));
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 유저의 아이디로 api 호출할 경우 404 응답을 받는다.")
    void userTest_fail() throws Exception {
        mockMvc.perform(get("/api/users/12345678"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Users에서 ID 12345678를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("사용자는 인증 코드로 계정을 활성화 시킬 수 있다.")
    void userVerifyCode() throws Exception {
        mockMvc.perform(get("/api/users/2/verify")
                        .queryParam("certificationCode", "aaaa-aaa-aaa-aaaaab"))
                .andExpect(status().isFound());

        final UserEntity userEntity = userJpaRepository.findById(2L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("사용자는 내 정보를 불러올 때 개인정보인 주소도 가져올 수 있다.")
    void userInfoAddressTest() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .header("EMAIL", "shyoon991@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("shyoon991@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("soono"))
                .andExpect(jsonPath("$.address").value("Seoul"))
                .andExpect(jsonPath("$.status").value(UserStatus.ACTIVE.name()));
    }

    @Test
    @DisplayName("사용자는 내 정보를 수정할 수 있다.")
    void userUpdateMeTest() throws Exception {
        final UserUpdate userUpdate = UserUpdate.builder()
                .nickname("soono update")
                .address("Pangyo")
                .build();

        mockMvc.perform(put("/api/users/me")
                        .header("EMAIL", "shyoon991@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("shyoon991@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("soono update"))
                .andExpect(jsonPath("$.address").value("Pangyo"))
                .andExpect(jsonPath("$.status").value(UserStatus.ACTIVE.name()));
    }

}
