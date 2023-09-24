package shop.mtcoding.bank.dto.account;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;

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

}
