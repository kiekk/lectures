package com.example.inflearn.service;

import com.example.inflearn.exception.ResourceNotFoundException;
import com.example.inflearn.repository.user.UserEntity;
import com.example.inflearn.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

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
}
