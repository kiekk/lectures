package shop.mtcoding.bank.dto.account;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.user.User;

public class AccountRequest {

    @Getter
    @Setter
    public static class AccountSaveRequest {
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long password;

        public Account toEntity(User user) {
            return Account.builder()
                    .number(number)
                    .password(password)
                    .balance(1000L) // 기본 잔고
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class AccountDepositRequest {
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;

        @NotNull
        private Long amount;

        @NotEmpty
        @Pattern(regexp = "^(DEPOSIT)$")
        private String gubun; // DEPOSIT

        @NotEmpty
        @Pattern(regexp = "^010[0-9]{4}[0-9]{4}|01[1|6|7|8|9][0-9]{3,4}[0-9]{4}")
        private String tel;
    }

}
