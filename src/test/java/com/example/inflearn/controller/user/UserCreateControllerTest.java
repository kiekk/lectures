package com.example.inflearn.controller.user;

import com.example.inflearn.user.domain.UserStatus;
import com.example.inflearn.user.domain.UserCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaMailSender mailSender;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("사용자를 등록한다. 회원가입 후 반환되는 사용자는 PENDING 상태이며 개인정보는 제외하고 반환된다.")
    void userCreateTest() throws Exception {
        final UserCreate userCreate = UserCreate.builder()
                .email("shyoon993@gmail.com")
                .nickname("soono333")
                .address("Pangyo")
                .build();

        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email").value("shyoon993@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("soono333"))
                .andExpect(jsonPath("$.status").value(UserStatus.PENDING.name()));
    }

}
