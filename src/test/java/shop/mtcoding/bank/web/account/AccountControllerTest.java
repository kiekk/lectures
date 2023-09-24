package shop.mtcoding.bank.web.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.mtcoding.bank.dto.account.AccountRequest.AccountSaveRequest;

@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    private final ObjectMapper objectMapper;
    private final MockMvc mvc;
    private final UserRepository userRepository;

    AccountControllerTest(@Autowired ObjectMapper objectMapper,
                          @Autowired MockMvc mvc,
                          @Autowired UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.mvc = mvc;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        userRepository.save(newUser("soono", "soono"));
    }

    // setupBefore = TestExecutionEvent.TEST_EXECUTION
    // 실제 테스트 할 메서드 실행전에 실행 -> @BeforeEach의 setUp 실행 후
    // 실제 타겟인 saveAccount_test 메서드 실행 전에 실행
    // setupBefore = TestExecutionEvent.TEST_METHOD
    // 메서드 실행 전에 실행 -> @BeforeEach 의 setUp 메서드 실행 전에 실행
    @WithUserDetails(value = "soono", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void saveAccount_test() throws Exception {
        // given
        AccountSaveRequest accountSaveRequest = new AccountSaveRequest();
        accountSaveRequest.setNumber(9999L);
        accountSaveRequest.setPassword(1234L);
        String requestBody = objectMapper.writeValueAsString(accountSaveRequest);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/s/account")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }


}