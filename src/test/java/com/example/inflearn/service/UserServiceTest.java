package com.example.inflearn.service;

import com.example.inflearn.exception.ResourceNotFoundException;
import com.example.inflearn.model.UserStatus;
import com.example.inflearn.model.dto.user.UserCreateDto;
import com.example.inflearn.model.dto.user.UserUpdateDto;
import com.example.inflearn.repository.user.UserEntity;
import com.example.inflearn.service.user.UserService;
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
        final UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("shyoon993@gmail.com")
                .address("Gyeongi")
                .nickname("soono3")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        final UserEntity createUser = userService.create(userCreateDto);

        // then
        assertThat(createUser.getId()).isNotNull();
        assertThat(createUser.getStatus()).isEqualTo(UserStatus.PENDING);
        // TODO : 현재는 certificationCode를 검증할 방법이 없다.
//        assertThat(createUser.getCertificationCode()).isEqualTo(???);
    }
}
