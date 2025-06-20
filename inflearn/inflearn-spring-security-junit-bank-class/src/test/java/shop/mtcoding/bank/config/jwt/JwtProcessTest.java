package shop.mtcoding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProcessTest {

    private String createToken() {
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        return JwtProcess.create(loginUser);
    }

    @Test
    void create_test() {
        // given

        // when
        String jwtToken = createToken();

        System.out.println(jwtToken);
        // then
        assertThat(jwtToken).startsWith(JwtVO.TOKEN_PREFIX);
    }

    @Test
    void verify_test() {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = createToken().replace(JwtVO.TOKEN_PREFIX, "");

        // when
        LoginUser verifyUser = JwtProcess.verify(jwtToken);

        // then
        assertThat(verifyUser.getUsername()).isEqualTo(loginUser.getUsername());
        assertThat(verifyUser.getUser().getId()).isEqualTo(loginUser.getUser().getId());
    }

}
