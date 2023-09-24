package shop.mtcoding.bank.dto.account;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.user.User;

import java.util.List;

public class AccountResponse {

    @Getter
    @Setter
    public static class AccountSaveResponse {
        private Long id;
        private Long number;
        private Long balance;

        public AccountSaveResponse(Account account) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }

    @Getter
    @Setter
    public static class AccountListResponse {
        private String fullname;
        private List<AccountDto> accounts;

        public AccountListResponse(User user, List<Account> accounts) {
            this.fullname = user.getFullname();
            this.accounts = accounts.stream().map(AccountDto::new).toList();
        }

        @Getter
        @Setter
        public class AccountDto {
            private Long id;
            private Long number;
            private Long balance;

            public AccountDto(Account account) {
                this.id = account.getId();
                this.number = account.getNumber();
                this.balance = account.getBalance();
            }
        }
    }

}
