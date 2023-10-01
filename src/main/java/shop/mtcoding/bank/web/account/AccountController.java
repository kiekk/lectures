package shop.mtcoding.bank.web.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountRequest;
import shop.mtcoding.bank.dto.account.AccountRequest.AccountSaveRequest;
import shop.mtcoding.bank.service.account.AccountService;

import static shop.mtcoding.bank.dto.account.AccountRequest.AccountDepositRequest;
import static shop.mtcoding.bank.dto.account.AccountRequest.AccountWithdrawRequest;
import static shop.mtcoding.bank.dto.account.AccountResponse.*;

@Tags(value = @Tag(name = "Account Controller", description = "계좌 관리 API"))
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "계좌 등록", responses = {
            @ApiResponse(responseCode = "201", description = "계좌등록 성공", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌등록 성공")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = AccountSaveResponse.class))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "400", description = "파라미터 검증 오류", content = @Content(
                    schemaProperties = {
                            @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                            @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유효성검사 실패")),
                            @SchemaProperty(name = "data", schema = @Schema(title = "응답 데이터", type = "object", description = "응답 데이터", example = """
                                        {
                                        "number": "4자리로 입력해주세요.",
                                        "password": "4자리로 입력해주세요."
                                        }                           
                                    """))
                    })),
            @ApiResponse(responseCode = "Not Found User", description = "유저가 없을 경우", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유저를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Duplicate AccountNumber", description = "동일한 계좌 번호가 있을 경우", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "해당 계좌가 이미 존재합니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountSaveRequest accountSaveRequest,
                                         BindingResult bindingResult,
                                         @AuthenticationPrincipal LoginUser loginUser) {
        AccountSaveResponse accountSaveResponse = accountService.계좌등록(accountSaveRequest, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공", accountSaveResponse), HttpStatus.CREATED);
    }

    @Operation(summary = "계좌 목록 조회", responses = {
            @ApiResponse(responseCode = "200", description = "계좌목록보기_유저별 성공", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌목록보기_유저별 성공")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = AccountListResponse.class))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Not Found User", description = "유저가 없을 경우", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유저를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @GetMapping("/s/account/my")
    public ResponseEntity<?> findUserAccounts(@AuthenticationPrincipal LoginUser loginUser) {
        AccountListResponse accountsByUser = accountService.getAccountsByUser(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountsByUser), HttpStatus.OK);
    }

    @Operation(summary = "계좌 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "계좌 삭제 완료", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌 삭제 완료")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Not Found", description = "Not Found", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Check Account Auth Fail", description = "계좌 소유주 인증 실패", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌 소유주가 아닙니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @DeleteMapping("/s/account/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@Parameter(name = "accountNumber", description = "계좌 번호") @PathVariable Long accountNumber,
                                           @AuthenticationPrincipal LoginUser loginUser) {
        accountService.deleteAccount(accountNumber, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 삭제 완료", null), HttpStatus.OK);
    }

    @Operation(summary = "계좌 입금", responses = {
            @ApiResponse(responseCode = "201", description = "계좌 입금 완료", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌 입금 완료")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = AccountDepositResponse.class))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Validation Fail", description = "파라미터 검증 오류", content = @Content(
                    schemaProperties = {
                            @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                            @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유효성검사 실패")),
                            @SchemaProperty(name = "data", schema = @Schema(title = "응답 데이터", type = "object", description = "응답 데이터", example = """
                                        {
                                        "number": "4자리로 입력해주세요.",
                                        "gubun": "DEPOSIT만 가능합니다.",
                                        "tel": "전화번호 형식이 아닙니다."
                                        }                           
                                    """))
                    })),
            @ApiResponse(responseCode = "Not Found", description = "Not Found", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Minus Amount", description = "입금 금액 검사 실패", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "0원 이하의 금액을 입금할 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @PostMapping("/account/deposit")
    public ResponseEntity<?> depositAccount(@RequestBody @Valid AccountDepositRequest accountDepositRequest, BindingResult bindingResult) {
        AccountDepositResponse accountDepositResponse = accountService.depositAccount(accountDepositRequest);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 입금 완료", accountDepositResponse), HttpStatus.CREATED);
    }

    @Operation(summary = "계좌 출금", responses = {
            @ApiResponse(responseCode = "200", description = "계좌 출금 완료", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌 출금 완료")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = AccountWithdrawResponse.class))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Validation Fail", description = "파라미터 검증 오류", content = @Content(
                    schemaProperties = {
                            @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                            @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유효성검사 실패")),
                            @SchemaProperty(name = "data", schema = @Schema(title = "응답 데이터", type = "object", description = "응답 데이터", example = """
                                        {
                                        "number": "4자리로 입력해주세요.",
                                        "password": "4자리로 입력해주세요.",
                                        "gubun": "WITHDRAW만 가능합니다."
                                        }                           
                                    """))
                    })),
            @ApiResponse(responseCode = "Not Found", description = "Not Found", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Minus Amount", description = "출금 금액 검사 실패", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "0원 이하의 금액을 출금할 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @PostMapping("/s/account/withdraw")
    public ResponseEntity<?> withdrawAccount(@RequestBody @Valid AccountWithdrawRequest accountWithdrawRequest,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal LoginUser loginUser) {
        AccountWithdrawResponse accountWithdrawResponse = accountService.withdrawAccount(accountWithdrawRequest, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 출금 완료", accountWithdrawResponse), HttpStatus.CREATED);
    }

    @Operation(summary = "계좌 이체", responses = {
            @ApiResponse(responseCode = "200", description = "계좌 이체 완료", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌 이체 완료")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = AccountTransferResponse.class))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Validation Fail", description = "파라미터 검증 오류", content = @Content(
                    schemaProperties = {
                            @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                            @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유효성검사 실패")),
                            @SchemaProperty(name = "data", schema = @Schema(title = "응답 데이터", type = "object", description = "응답 데이터", example = """
                                        {
                                        "number": "4자리로 입력해주세요.",
                                        "password": "4자리로 입력해주세요.",
                                        "gubun": "WITHDRAW만 가능합니다."
                                        }                           
                                    """))
                    })),
            @ApiResponse(responseCode = "Not Found", description = "Not Found", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Minus Amount", description = "입금 금액 검사 실패", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "0원 이하의 금액을 입금할 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Check Sender & Receiver Account", description = "송금 계좌와 수금 계좌가 동일한 경우", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "입출금계좌가 동일할 수 없습니다")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @PostMapping("/s/account/transfer")
    public ResponseEntity<?> transferAccount(@RequestBody @Valid AccountRequest.AccountTransferRequest accountTransferRequest,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal LoginUser loginUser) {
        AccountTransferResponse accountTransferResponse = accountService.transferAccount(accountTransferRequest, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 이체 완료", accountTransferResponse), HttpStatus.CREATED);
    }

    @Operation(summary = "계좌 상세 조회", responses = {
            @ApiResponse(responseCode = "200", description = "계좌상세보기 성공", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌상세보기 성공")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = AccountDetailResponse.class))
            })),
            @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "인증안됨")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Not Found", description = "Not Found", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌를 찾을 수 없습니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            })),
            @ApiResponse(responseCode = "Check Account Auth Fail", description = "계좌 소유주 인증 실패", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "계좌 소유주가 아닙니다.")),
                    @SchemaProperty(name = "data", schema = @Schema(example = "null"))
            }))
    })
    @GetMapping("/s/account/{number}")
    public ResponseEntity<?> findDetailAccount(@Parameter(name = "number", description = "계좌 번호") @PathVariable Long number,
                                               @Parameter(name = "page", description = "페이지 번호") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @AuthenticationPrincipal LoginUser loginUser) {
        AccountDetailResponse accountDetailResponse = accountService.getAccountDetail(number, loginUser.getUser().getId(),
                page);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌상세보기 성공", accountDetailResponse), HttpStatus.OK);
    }

}
