package shop.mtcoding.bank.service.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountRequest.AccountSaveRequest;
import shop.mtcoding.bank.dto.account.AccountRequest.AccountTransferRequest;
import shop.mtcoding.bank.dto.account.AccountResponse.AccountListResponse;
import shop.mtcoding.bank.handler.exception.CustomApiException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static shop.mtcoding.bank.dto.account.AccountRequest.AccountDepositRequest;
import static shop.mtcoding.bank.dto.account.AccountResponse.AccountDepositResponse;
import static shop.mtcoding.bank.dto.account.AccountResponse.AccountSaveResponse;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void 계좌등록_test() {
        // given
        Long userId = 1L;

        AccountSaveRequest accountSaveRequest = new AccountSaveRequest();
        accountSaveRequest.setNumber(1111L);
        accountSaveRequest.setPassword(1234L);

        // stub
        User user = newMockUser(userId, "soono", "soono");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accountRepository.findByNumber(anyLong())).thenReturn(Optional.empty());

        Account account = newMockAccount(1L, 1111L, 1000L, user);
        when(accountRepository.save(any())).thenReturn(account);

        // when
        AccountSaveResponse accountSaveResponse = accountService.계좌등록(accountSaveRequest, userId);

        // then
        assertThat(accountSaveResponse.getId()).isEqualTo(account.getId());
        assertThat(accountSaveResponse.getNumber()).isEqualTo(account.getNumber());
        assertThat(accountSaveResponse.getBalance()).isEqualTo(account.getBalance());
    }

    @DisplayName("내 계좌만 볼 수 있다.")
    @Test
    void 계좌목록보기_유저별_test() {
        // given
        Long userId = 1L;

        // stub
        User user = newMockUser(1L, "soono", "soono");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Account userAccount1 = newMockAccount(1L, 1111L, 1000L, user);
        Account userAccount2 = newMockAccount(2L, 2222L, 1000L, user);
        List<Account> accountList = List.of(userAccount1, userAccount2);
        when(accountRepository.findByUser_id(any())).thenReturn(accountList);

        // when
        AccountListResponse accountListResponse = accountService.getAccountsByUser(userId);

        // then
        assertThat(accountListResponse.getFullname()).isEqualTo("soono");
        assertThat(accountListResponse.getAccounts().size()).isEqualTo(2);
    }

    @DisplayName("계좌 삭제 시 본인 소유의 계좌는 정상적으로 삭제할 수 있다.")
    @Test
    void deleteAccountSuccess() {
        // given
        Long userId = 1L;
        Long accountNumber = 1111L;
        User user = newMockUser(userId, "soono", "soono");

        Account userAccount = newMockAccount(1L, accountNumber, 1000L, user);
        when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.of(userAccount));

        // when & then
        accountService.deleteAccount(accountNumber, userId);
    }

    @DisplayName("계좌 삭제 시 본인 소유의 계좌가 아닐 경우 예외가 발생한다.")
    @Test
    void deleteAccountFail() {
        // given
        Long userId = 1L;
        Long accountNumber = 1111L;
        User user = newMockUser(userId, "soono", "soono");

        Account userAccount = newMockAccount(1L, accountNumber, 1000L, user);
        when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.of(userAccount));

        // when & then
        assertThrows(CustomApiException.class, () -> accountService.deleteAccount(accountNumber, 2L));
    }

    @Test
    void depositAccountSuccess() {
        // given
        AccountDepositRequest accountDepositRequest = new AccountDepositRequest();
        accountDepositRequest.setNumber(1111L);
        accountDepositRequest.setAmount(100L);
        accountDepositRequest.setGubun("DEPOSIT");
        accountDepositRequest.setTel("01011112222");

        // stub
        User soono = newMockUser(1L, "soono", "soono");
        Account soonoAccount = newMockAccount(1L, 1111L, 1000L, soono);
        when(accountRepository.findByNumber(anyLong())).thenReturn(Optional.of(soonoAccount));

        Account stubAccount = newMockAccount(1L, 1111L, 1000L, soono);
        Transaction transaction = newMockDepositTransaction(1L, stubAccount);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // when
        AccountDepositResponse accountDepositResponse = accountService.depositAccount(accountDepositRequest);

        // then
        assertThat(accountDepositResponse.getTransaction().getDepositAccountBalance()).isEqualTo(1100L);
        assertThat(soonoAccount.getBalance()).isEqualTo(1100L);
    }

    @Test
    void withdrawAccountSuccess() {
        // given
        Long amount = 100L;
        Long password = 1234L;
        Long userId = 1L;

        User user = newMockUser(1L, "soono", "soono");
        Account userAccount = newMockAccount(1L, 1111L, 1000L, user);

        // when
        userAccount.checkOwner(userId);
        userAccount.checkSamePassword(password);
        userAccount.checkBalance(amount);
        userAccount.withdraw(amount);

        // then
        assertThat(userAccount.getBalance()).isEqualTo(900L);
    }

    @Test
    public void transferAccountSuccess() {
        // given
        Long userId = 1000L;
        AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
        accountTransferRequest.setWithdrawNumber(1111L);
        accountTransferRequest.setDepositNumber(2222L);
        accountTransferRequest.setWithdrawPassword(1234L);
        accountTransferRequest.setAmount(100L);
        accountTransferRequest.setGubun("TRANSFER");

        User soono1 = newMockUser(1000L, "soono1", "soono1");
        User soono2 = newMockUser(2000L, "soono2", "soono2");
        Account withdrawAccount = newMockAccount(1L, 1111L, 1000L, soono1);
        Account depositAccount = newMockAccount(2L, 2222L, 1000L, soono2);

        // when
        // 출금계좌와 입금계좌가 동일하면 안됨
        if (accountTransferRequest.getWithdrawNumber().longValue() == accountTransferRequest.getDepositNumber()
                .longValue()) {
            throw new CustomApiException("입출금계좌가 동일할 수 없습니다");
        }

        // 0원 체크
        if (accountTransferRequest.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다");
        }

        // 출금 소유자 확인 (로그인한 사람과 동일한지)
        withdrawAccount.checkOwner(userId);

        // 출금계좌 비빌번호 확인
        withdrawAccount.checkSamePassword(accountTransferRequest.getWithdrawPassword());

        // 출금계좌 잔액 확인
        withdrawAccount.checkBalance(accountTransferRequest.getAmount());

        // 이체하기
        withdrawAccount.withdraw(accountTransferRequest.getAmount());
        depositAccount.deposit(accountTransferRequest.getAmount());

        // then
        assertThat(withdrawAccount.getBalance()).isEqualTo(900L);
        assertThat(depositAccount.getBalance()).isEqualTo(1100L);
    }

}
