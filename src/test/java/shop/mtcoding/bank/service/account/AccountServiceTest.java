package shop.mtcoding.bank.service.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountRequest.AccountSaveRequest;
import shop.mtcoding.bank.dto.account.AccountResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static shop.mtcoding.bank.dto.account.AccountResponse.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

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
}