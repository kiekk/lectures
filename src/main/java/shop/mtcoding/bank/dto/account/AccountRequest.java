package shop.mtcoding.bank.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.user.User;

public class AccountRequest {

    @Schema(description = "계좌 등록 요청")
    @Getter
    @Setter
    public static class AccountSaveRequest {
        @Schema(title = "계좌 번호", description = "계좌 번호", minLength = 4, maxLength = 4)
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long number;
        @Schema(title = "비밀번호", description = "비밀번호", minLength = 4, maxLength = 4)
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
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

    @Getter
    @Setter
    public static class AccountWithdrawRequest {
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;

        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long password;

        @NotNull
        private Long amount;

        @NotEmpty
        @Pattern(regexp = "WITHDRAW")
        private String gubun;
    }

    @Setter
    @Getter
    public static class AccountTransferRequest {
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long withdrawNumber;

        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long depositNumber;

        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long withdrawPassword;

        @NotNull
        private Long amount;

        @NotEmpty
        @Pattern(regexp = "TRANSFER")
        private String gubun;
    }

}
