package shop.mtcoding.bank.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static shop.mtcoding.bank.dto.user.UserRequest.JoinRequest;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest extends DummyObject {

    private final MockMvc mvc;
    private final ObjectMapper om;
    private final UserRepository userRepository;

    UserControllerTest(@Autowired MockMvc mvc,
                       @Autowired ObjectMapper om,
                       @Autowired UserRepository userRepository) {
        this.mvc = mvc;
        this.om = om;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        dateSetting();
    }

    @DisplayName("회원가입 성공 테스트")
    @Test
    void 회원가입_성공_test() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("shyoon");
        joinRequest.setPassword("1234");
        joinRequest.setEmail("shyoon@gmail.com");
        joinRequest.setFullname("shyoon");

        String requestBody = om.writeValueAsString(joinRequest);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("1"))
                .andExpect(jsonPath("$.msg").value("회원가입 성공"));
    }

    @DisplayName("각 필드 값이 유효성 검사에 실패하면 회원 가입을 할 수 없다.")
    @Test
    void 회원가입_실패_test() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("한글");
        joinRequest.setPassword("12343434334343536");
        joinRequest.setEmail("shyoon@gmail.comcc");
        joinRequest.setFullname("한글");

        String requestBody = om.writeValueAsString(joinRequest);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("-1"))
                .andExpect(jsonPath("$.msg").value("유효성검사 실패"));
    }

    @DisplayName("이미 가입된 회원은 다시 회원 가입을 할 수 없다.")
    @Test
    void 회원가입_실패_test2() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("soono");
        joinRequest.setPassword("1234");
        joinRequest.setEmail("shyoon@gmail.com");
        joinRequest.setFullname("soono");

        String requestBody = om.writeValueAsString(joinRequest);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("-1"))
                .andExpect(jsonPath("$.msg").value("동일한 username이 존재합니다."));
    }

    private void dateSetting() {
        userRepository.save(newUser("soono", "soono"));
    }

}
