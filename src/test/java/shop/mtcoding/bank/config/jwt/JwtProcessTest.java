package shop.mtcoding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProcessTest {

    @Test
    void create_test() {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);

        System.out.println(jwtToken);
        // then
        assertThat(jwtToken).startsWith(JwtVO.TOKEN_PREFIX);
    }

    @Test
    void verify_test() {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNjk1NzIxMDEyLCJpZCI6MSwicm9sZSI6IkNVU1RPTUVSIn0.DkYAwoMsrGkXDjqCUg7XrE2_xlTc6KXbeg942bH6WL2HXlcEVG-3mjIQApsvowWjxrtywyCYeQOOiAPZlRtnsQ";

        // when
        LoginUser verifyUser = JwtProcess.verify(token);

        // then
        assertThat(verifyUser.getUsername()).isEqualTo(loginUser.getUsername());
        assertThat(verifyUser.getUser().getId()).isEqualTo(loginUser.getUser().getId());
    }

}
