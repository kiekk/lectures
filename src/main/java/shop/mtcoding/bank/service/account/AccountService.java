package shop.mtcoding.bank.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionEnum;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountResponse.*;
import shop.mtcoding.bank.handler.exception.CustomApiException;

import java.util.List;
import java.util.Optional;

import static shop.mtcoding.bank.dto.account.AccountRequest.*;
import static shop.mtcoding.bank.dto.account.AccountResponse.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public AccountSaveResponse 계좌등록(AccountSaveRequest accountSaveRequest, Long userId) {
        // User 가 존재하는지 확인
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("유저를 찾을 수 없습니다."));

        // 해당 계좌의 중복 여부 확인
        Optional<Account> accountOptional = accountRepository.findByNumber(accountSaveRequest.getNumber());

        if (accountOptional.isPresent()) {
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        // 계좌 등록
        Account accountPS = accountRepository.save(accountSaveRequest.toEntity(userPS));

        // DTO 에 응답
        return new AccountSaveResponse(accountPS);
    }

    public AccountListResponse getAccountsByUser(Long userId) {
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("유저를 찾을 수 없습니다."));
        List<Account> accountsPS = accountRepository.findByUser_id(userId);

        return new AccountListResponse(userPS, accountsPS);
    }

    @Transactional
    public void deleteAccount(Long accountNumber, Long userId) {
        // 계좌 확인
        Account accountPS = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        // 계좌 소유자 확인
        accountPS.checkOwner(userId);

        // 계좌 삭제
        accountRepository.deleteById(accountPS.getId());
    }

    @Transactional
    public AccountDepositResponse depositAccount(AccountDepositRequest accountDepositRequest) { // ATN -> 누군가의 계좌
        // 입금액 0원 체크
        if (accountDepositRequest.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 입금계좌 확인
        Account depositAccountPS = accountRepository.findByNumber(accountDepositRequest.getNumber())
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        // 입금
        depositAccountPS.deposit(accountDepositRequest.getAmount());


        // 거래내역 남기기

        Transaction transaction = Transaction.builder()
                .depositAccount(depositAccountPS)
                .withdrawAccount(null)
                .depositAccountBalance(depositAccountPS.getBalance())
                .withdrawAccountBalance(null)
                .amount(accountDepositRequest.getAmount())
                .sender("ATM")
                .gubun(TransactionEnum.DEPOSIT)
                .receiver(depositAccountPS.getNumber() + "")
                .tel(accountDepositRequest.getTel())
                .build();
        Transaction transactionPS = transactionRepository.save(transaction);
        return new AccountDepositResponse(depositAccountPS, transactionPS);
    }

    @Transactional
    public AccountWithdrawResponse withdrawAccount(AccountWithdrawRequest accountWithdrawRequest, Long userId) {
        // 출금액 0원 확인
        if (accountWithdrawRequest.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 출금할 수 없습니다.");
        }

        // 출금계좌 확인
        Account withdrawAccountPS = accountRepository.findByNumber(accountWithdrawRequest.getNumber())
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));

        // 출금 소유주 확인
        withdrawAccountPS.checkOwner(userId);

        // 출금계좌 비밀번호 확인
        withdrawAccountPS.checkSamePassword(accountWithdrawRequest.getPassword());

        // 출금계좌 잔액 확인
        withdrawAccountPS.checkBalance(accountWithdrawRequest.getAmount());

        // 출금하기
        withdrawAccountPS.withdraw(accountWithdrawRequest.getAmount());

        // 거래내역 남기기
        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccountPS)
                .depositAccount(null)
                .withdrawAccountBalance(withdrawAccountPS.getBalance())
                .depositAccountBalance(null)
                .amount(accountWithdrawRequest.getAmount())
                .sender(accountWithdrawRequest.getNumber() + "")
                .gubun(TransactionEnum.WITHDRAW)
                .receiver("ATM")
                .build();
        Transaction transactionPS = transactionRepository.save(transaction);
        return new AccountWithdrawResponse(withdrawAccountPS, transactionPS);
    }

    @Transactional
    public AccountTransferResponse transferAccount(AccountTransferRequest accountTransferRequest, Long userId) {
        // 출금계좌와 입금계좌가 동일하면 안됨
        if (accountTransferRequest.getWithdrawNumber().longValue() == accountTransferRequest.getDepositNumber()
                .longValue()) {
            throw new CustomApiException("입출금계좌가 동일할 수 없습니다");
        }

        // 0원 체크
        if (accountTransferRequest.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다");
        }

        // 출금계좌 확인
        Account withdrawAccountPS = accountRepository.findByNumber(accountTransferRequest.getWithdrawNumber())
                .orElseThrow(
                        () -> new CustomApiException("출금계좌를 찾을 수 없습니다"));

        // 입금계좌 확인
        Account depositAccountPS = accountRepository.findByNumber(accountTransferRequest.getDepositNumber())
                .orElseThrow(
                        () -> new CustomApiException("입금계좌를 찾을 수 없습니다"));

        // 출금 소유자 확인 (로그인한 사람과 동일한지)
        withdrawAccountPS.checkOwner(userId);

        // 출금계좌 비빌번호 확인
        withdrawAccountPS.checkSamePassword(accountTransferRequest.getWithdrawPassword());

        // 출금계좌 잔액 확인
        withdrawAccountPS.checkBalance(accountTransferRequest.getAmount());

        // 이체하기
        withdrawAccountPS.withdraw(accountTransferRequest.getAmount());
        depositAccountPS.deposit(accountTransferRequest.getAmount());

        // 거래내역 남기기 (내 계좌에서 ATM으로 출금)
        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccountPS)
                .depositAccount(depositAccountPS)
                .withdrawAccountBalance(withdrawAccountPS.getBalance())
                .depositAccountBalance(depositAccountPS.getBalance())
                .amount(accountTransferRequest.getAmount())
                .gubun(TransactionEnum.TRANSFER)
                .sender(accountTransferRequest.getWithdrawNumber() + "")
                .receiver(accountTransferRequest.getDepositNumber() + "")
                .build();

        Transaction transactionPS = transactionRepository.save(transaction);

        // DTO응답
        return new AccountTransferResponse(withdrawAccountPS, transactionPS);
    }

    public AccountDetailResponse getAccountDetail(Long number, Long userId, Integer page) {
        // 1. 구분값, 페이지고정
        String gubun = "ALL";

        Account accountPS = accountRepository.findByNumber(number).orElseThrow(
                () -> new CustomApiException("계좌를 찾을 수 없습니다"));

        // 3. 계좌 소유자 확인
        accountPS.checkOwner(userId);

        // 4. 입출금목록보기
        List<Transaction> transactionList = transactionRepository.findTransactionList(accountPS.getId(), gubun, page);
        return new AccountDetailResponse(accountPS, transactionList);
    }

}
