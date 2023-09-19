package shop.mtcoding.bank.config;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.config.jwt.JwtProcess;
import shop.mtcoding.bank.config.jwt.JwtVO;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {

    private final MockMvc mvc;

    SecurityConfigTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void authentication_test() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        // then
        assertThat(httpStatusCode).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void authorization_test() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        // when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello")
                .header(JwtVO.HEADER, jwtToken));
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        // then
        assertThat(httpStatusCode).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
    }

}
