package shop.mtcoding.bank.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.util.CustomDateUtil;

import java.util.List;

public class TransactionResponse {

    @Schema(title = "거래 내역 조회 응답")
    @Getter
    @Setter
    public static class TransactionListResponse {

        @Schema(title = "거래 내역 목록", description = "거래 내역 목록")
        private List<TransactionItem> transactions;

        public TransactionListResponse(List<Transaction> transactions, Account account) {
            this.transactions = transactions.stream()
                    .map((transaction) -> new TransactionItem(transaction, account.getNumber()))
                    .toList();
        }

        @Schema(title = "거래 내역", description = "거래 내역")
        @Getter
        @Setter
        public class TransactionItem {
            @Schema(title = "id", description = "id")
            private Long id;
            @Schema(title = "구분", description = "구분", example = "DEPOSIT|WITHDRAW")
            private String gubun;
            @Schema(title = "금액", description = "금액")
            private Long amount;
            @Schema(title = "송금자", description = "송금자")
            private String sender;
            @Schema(title = "수금자", description = "수금자")
            private String receiver;
            @Schema(title = "전화번호", description = "전화번호")
            private String tel;
            @Schema(title = "거래일시", description = "거래일시", example = "yyyy-MM-dd HH:mm:ss")
            private String createdAt;
            @Schema(title = "잔액", description = "잔액")
            private Long balance;

            public TransactionItem(Transaction transaction, Long accountNumber) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.amount = transaction.getAmount();
                this.sender = transaction.getSender();
                this.receiver = transaction.getReceiver();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
                this.tel = transaction.getId() == null ? "없음" : transaction.getTel();

                if (transaction.getDepositAccount() == null) {
                    this.balance = transaction.getWithdrawAccountBalance();
                } else if (transaction.getWithdrawAccount() == null) {
                    this.balance = transaction.getDepositAccountBalance();
                } else {
                    if (accountNumber.longValue() == transaction.getDepositAccount().getNumber()) {
                        this.balance = transaction.getDepositAccountBalance();
                    } else {
                        this.balance = transaction.getWithdrawAccountBalance();
                    }
                }
            }
        }
    }

}
