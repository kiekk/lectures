package shop.mtcoding.bank.web.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.service.transaction.TransactionService;

import static shop.mtcoding.bank.dto.transaction.TransactionResponse.TransactionListResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/s/account/{number}/transaction")
    public ResponseEntity<?> findTransactionList(@PathVariable Long number,
                                                 @RequestParam(value = "gubun", defaultValue = "ALL") String gubun,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @AuthenticationPrincipal LoginUser loginUser) {
        TransactionListResponse transactionListRespDto = transactionService.getTransactionHistories(loginUser.getUser().getId(), number,
                gubun, page);
        return new ResponseEntity<>(new ResponseDto<>(1, "입출금목록보기 성공", transactionListRespDto), HttpStatus.OK);
    }

}
