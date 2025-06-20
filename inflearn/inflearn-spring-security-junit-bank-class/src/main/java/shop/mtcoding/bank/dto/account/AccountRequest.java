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

    @Schema(title = "계좌 등록 요청", description = "계좌 등록 요청")
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

    @Schema(title = "계좌 입금 요청", description = "계좌 입금 요청")
    @Getter
    @Setter
    public static class AccountDepositRequest {
        @Schema(title = "계좌 번호", description = "계좌 번호")
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long number;

        @Schema(title = "금액", description = "금액")
        @NotNull
        private Long amount;

        @Schema(title = "구분", description = "구분", allowableValues = "DEPOSIT")
        @NotEmpty
        @Pattern(regexp = "^(DEPOSIT)$", message = "DEPOSIT만 가능합니다.")
        private String gubun; // DEPOSIT

        @Schema(title = "전화번호", description = "전화번호", pattern = "^010[0-9]{4}[0-9]{4}|01[1|6|7|8|9][0-9]{3,4}[0-9]{4}")
        @NotEmpty
        @Pattern(regexp = "^010[0-9]{4}[0-9]{4}|01[1|6|7|8|9][0-9]{3,4}[0-9]{4}", message = "전화번호 형식이 아닙니다.")
        private String tel;
    }

    @Schema(title = "계좌 출금 요청", description = "계좌 출금 요청")
    @Getter
    @Setter
    public static class AccountWithdrawRequest {
        @Schema(title = "계좌 번호", description = "계좌 번호")
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long number;

        @Schema(title = "비밀번호", description = "비밀번호")
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long password;

        @Schema(title = "금액", description = "금액")
        @NotNull
        private Long amount;

        @Schema(title = "구분", description = "구분", allowableValues = "WITHDRAW")
        @NotEmpty
        @Pattern(regexp = "WITHDRAW")
        private String gubun;
    }

    @Schema(title = "계좌 이체 요청", description = "계좌 이체 요청")
    @Setter
    @Getter
    public static class AccountTransferRequest {
        @Schema(title = "출금 계좌 번호", description = "출금 계좌 번호")
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long withdrawNumber;

        @Schema(title = "입금 계좌 번호", description = "입금 계좌 번호")
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long depositNumber;

        @Schema(title = "출금 계좌 비밀번호", description = "출금 계좌 비밀번호")
        @NotNull
        @Digits(integer = 4, fraction = 4, message = "4자리로 입력해주세요.")
        private Long withdrawPassword;

        @Schema(title = "금액", description = "금액")
        @NotNull
        private Long amount;

        @Schema(title = "구분", description = "구분", allowableValues = "TRANSFER")
        @NotEmpty
        @Pattern(regexp = "TRANSFER")
        private String gubun;
    }

}
