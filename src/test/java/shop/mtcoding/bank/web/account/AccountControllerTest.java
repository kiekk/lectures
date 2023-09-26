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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.mtcoding.bank.dto.account.AccountRequest.*;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    private final ObjectMapper objectMapper;
    private final MockMvc mvc;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    AccountControllerTest(@Autowired ObjectMapper objectMapper,
                          @Autowired MockMvc mvc,
                          @Autowired UserRepository userRepository,
                          @Autowired AccountRepository accountRepository,
                          @Autowired TransactionRepository transactionRepository) {
        this.objectMapper = objectMapper;
        this.mvc = mvc;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @BeforeEach
    void setUp() {
        User user1 = userRepository.save(newUser("soono", "soono"));
        User user2 = userRepository.save(newUser("soono2", "soono2"));
        User user3 = userRepository.save(newUser("soono3", "soono3"));
        User user4 = userRepository.save(newUser("soono4", "soono4"));

        Account userAccount1 = accountRepository.save(newAccount(1111L, user1));
        accountRepository.save(newAccount(1112L, user1));
        accountRepository.save(newAccount(1113L, user1));
        Account userAccount2 = accountRepository.save(newAccount(2111L, user2));
        accountRepository.save(newAccount(2112L, user2));
        Account userAccount3 = accountRepository.save(newAccount(3111L, user3));
        Account userAccount4 = accountRepository.save(newAccount(4111L, user4));

        Transaction withdrawTransaction1 = transactionRepository.save(newWithdrawTransaction(userAccount1, accountRepository));
        Transaction depositTransaction1 = transactionRepository.save(newDepositTransaction(userAccount2, accountRepository));
        Transaction transferTransaction1 = transactionRepository.save(newTransferTransaction(userAccount1, userAccount2, accountRepository));
        Transaction transferTransaction2 = transactionRepository.save(newTransferTransaction(userAccount1, userAccount4, accountRepository));
        Transaction transferTransaction3 = transactionRepository.save(newTransferTransaction(userAccount2, userAccount1, accountRepository));
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

    @WithUserDetails(value = "soono", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void findUserAccounts() throws Exception {
        // given
        String fullname = "soono";

        // when
        ResultActions resultActions = mvc.perform(get("/api/s/account/my"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("1"))
                .andExpect(jsonPath("$.msg").value("계좌목록보기_유저별 성공"))
                .andExpect(jsonPath("$.data.fullname").value(fullname))
                .andExpect(jsonPath("$.data.accounts").isArray());
    }

    // 계좌 삭제 실패 테스트는 이미 AccountService 에서 했으므로 컨트롤러에서는 성공 케이스만 테스트
    @WithUserDetails(value = "soono", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void deleteAccount() throws Exception {
        // given
        Long accountNumber = 1111L;

        // when
        ResultActions resultActions = mvc.perform(delete("/api/s/account/" + accountNumber));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void depositAccount() throws Exception {
        // given
        AccountDepositRequest accountDepositRequest = new AccountDepositRequest();
        accountDepositRequest.setNumber(1111L);
        accountDepositRequest.setAmount(100L);
        accountDepositRequest.setGubun("DEPOSIT");
        accountDepositRequest.setTel("01011112222");

        String requestBody = objectMapper.writeValueAsString(accountDepositRequest);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/account/deposit")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }

    @WithUserDetails(value = "soono", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void withdrawAccount_test() throws Exception {
        // given
        AccountWithdrawRequest accountWithdrawRequest = new AccountWithdrawRequest();
        accountWithdrawRequest.setNumber(1111L);
        accountWithdrawRequest.setPassword(1234L);
        accountWithdrawRequest.setAmount(100L);
        accountWithdrawRequest.setGubun("WITHDRAW");

        String requestBody = objectMapper.writeValueAsString(accountWithdrawRequest);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/s/account/withdraw").content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 체크 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }

    @WithUserDetails(value = "soono", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void transferAccount_test() throws Exception {
        // given
        AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
        accountTransferRequest.setWithdrawNumber(1111L);
        accountTransferRequest.setDepositNumber(2111L);
        accountTransferRequest.setWithdrawPassword(1234L);
        accountTransferRequest.setAmount(100L);
        accountTransferRequest.setGubun("TRANSFER");

        String requestBody = objectMapper.writeValueAsString(accountTransferRequest);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/s/account/transfer").content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }

}
