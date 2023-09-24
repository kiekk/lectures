package shop.mtcoding.bank.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountResponse.AccountSaveResponse;
import shop.mtcoding.bank.handler.exception.CustomApiException;

import java.util.Optional;

import static shop.mtcoding.bank.dto.account.AccountRequest.AccountSaveRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

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
}
