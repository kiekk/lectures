package shop.mtcoding.bank.web.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.service.transaction.TransactionService;

import static shop.mtcoding.bank.dto.transaction.TransactionResponse.TransactionListResponse;

@Tags(value = @Tag(name = "Transaction Controller", description = "거래 내역 관리 API"))
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "입출금 내역 조회", responses = {
            @ApiResponse(responseCode = "200", description = "입출금목록보기 성공", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "입출금목록보기 성공")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = TransactionListResponse.class))
            })),
            @ApiResponse(responseCode = "Not Found", description = "Not Found", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "해당 계좌를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @GetMapping("/s/account/{number}/transaction")
    public ResponseEntity<?> findTransactionList(@Parameter(name = "number", description = "계좌 번호") @PathVariable Long number,
                                                 @Parameter(name = "gubun", description = "구분", examples = {
                                                         @ExampleObject(name = "전체", description = "ALL"),
                                                         @ExampleObject(name = "입금", description = "DEPOSIT"),
                                                         @ExampleObject(name = "출금", description = "WITHDRAW")
                                                 }) @RequestParam(value = "gubun", defaultValue = "ALL") String gubun,
                                                 @Parameter(name = "page", description = "페이지 번호") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @AuthenticationPrincipal LoginUser loginUser) {
        TransactionListResponse transactionListRespDto = transactionService.getTransactionHistories(loginUser.getUser().getId(), number,
                gubun, page);
        return new ResponseEntity<>(new ResponseDto<>(1, "입출금목록보기 성공", transactionListRespDto), HttpStatus.OK);
    }

}
