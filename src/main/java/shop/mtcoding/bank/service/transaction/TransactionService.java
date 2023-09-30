package shop.mtcoding.bank.service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.dto.transaction.TransactionResponse;
import shop.mtcoding.bank.handler.exception.CustomApiException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionResponse.TransactionListResponse getTransactionHistories(Long userId, Long accountNumber, String gubun, Integer page) {
        // 계좌 확인
        Account accountPS = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new CustomApiException("해당 계좌를 찾을 수 없습니다."));

        // 계좌 소유주 확인
        accountPS.checkOwner(userId);

        // 입출금 내역 조회
        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountPS.getId(), gubun, page);
        return new TransactionResponse.TransactionListResponse(transactionListPS, accountPS);
    }

}
