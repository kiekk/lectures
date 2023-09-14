package shop.mtcoding.bank.config;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.bank.dto.ResponseDto;

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

}