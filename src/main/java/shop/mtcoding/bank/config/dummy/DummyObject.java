package shop.mtcoding.bank.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionEnum;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newUser(String username, String fullname) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encryptPassword)
                .email(username + "@gmail.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id, String username, String fullname) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptPassword = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .username(username)
                .password(encryptPassword)
                .email(username + "@gmail.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected Account newAccount(Long number, User user) {
        return Account.builder()
                .number(number)
                .password(1234L)
                .balance(1000L)
                .user(user)
                .build();
    }

    protected Account newMockAccount(Long id, Long number, Long balance, User user) {
        return Account.builder()
                .id(id)
                .number(number)
                .password(1234L)
                .balance(balance)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected static Transaction newMockDepositTransaction(Long id, Account account) {
        account.deposit(100L);
        return Transaction.builder()
                .id(id)
                .depositAccount(account)
                .withdrawAccount(null)
                .withdrawAccountBalance(null)
                .depositAccountBalance(account.getBalance())
                .amount(100L)
                .gubun(TransactionEnum.DEPOSIT)
                .sender("ATM")
                .receiver(account.getNumber() + "")
                .tel("01011112222")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
