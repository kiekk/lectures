package shop.mtcoding.bank.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.util.CustomDateUtil;

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

    @Getter
    @Setter
    public static class AccountDepositResponse {
        private Long id;
        private Long number;
        private TransactionDto transaction;

        public AccountDepositResponse(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.transaction = new TransactionDto(transaction);
        }

        @Getter
        @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String receiver;
            private Long amount;
            @JsonIgnore
            private Long depositAccountBalance; // 클라이언트에게 전달X -> 서비스단에서 테스트 용도로 사용
            private String tel;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.receiver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.depositAccountBalance = transaction.getDepositAccountBalance();
                this.tel = transaction.getTel();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    @Getter
    @Setter
    public static class AccountWithdrawResponse {
        private Long id;
        private Long number;
        private Long balance;
        private TransactionDto transaction;

        public AccountWithdrawResponse(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDto(transaction);
        }

        @Getter
        @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String receiver;
            private Long amount;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.receiver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    @Setter
    @Getter
    public static class AccountTransferResponse {
        private Long id; // 계좌 ID
        private Long number; // 계좌번호
        private Long balance; // 출금 계좌 잔액
        private TransactionDto transaction;

        public AccountTransferResponse(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDto(transaction);
        }

        @Setter
        @Getter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            @JsonIgnore
            private Long depositAccountBalance;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.depositAccountBalance = transaction.getDepositAccountBalance();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    @Getter
    @Setter
    public static class AccountDetailResponse {
        private Long id; // 계좌 ID
        private Long number; // 계좌번호
        private Long balance; // 그 계좌의 최종 잔액
        private List<TransactionDto> transactions;

        public AccountDetailResponse(Account account, List<Transaction> transactions) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transactions = transactions.stream()
                    .map((transaction) -> new TransactionDto(transaction, account.getNumber()))
                    .toList();
        }

        @Getter
        @Setter
        public class TransactionDto {

            private Long id;
            private String gubun;
            private Long amount;

            private String sender;
            private String receiver;

            private String tel;
            private String createdAt;
            private Long balance;

            public TransactionDto(Transaction transaction, Long accountNumber) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.amount = transaction.getAmount();
                this.sender = transaction.getSender();
                this.receiver = transaction.getReceiver();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
                this.tel = transaction.getTel() == null ? "없음" : transaction.getTel();

                if (transaction.getDepositAccount() == null) {
                    this.balance = transaction.getWithdrawAccountBalance();
                } else if (transaction.getWithdrawAccount() == null) {
                    this.balance = transaction.getDepositAccountBalance();
                } else {
                    if (accountNumber.longValue() == transaction.getDepositAccount().getNumber().longValue()) {
                        this.balance = transaction.getDepositAccountBalance();
                    } else {
                        this.balance = transaction.getWithdrawAccountBalance();
                    }
                }

            }
        }
    }

}
