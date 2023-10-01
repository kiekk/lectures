package shop.mtcoding.bank.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.service.user.UserService;

import static shop.mtcoding.bank.dto.user.UserRequest.JoinRequest;
import static shop.mtcoding.bank.dto.user.UserResponse.JoinResponse;

@Tags(value = @Tag(name = "User Controller", description = "사용자 관리 API"))
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입", responses = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "1")),
                    @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "회원가입 성공")),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = JoinResponse.class))
            })),
            @ApiResponse(responseCode = "400", description = "파라미터 검증 오류", content = @Content(
                    schemaProperties = {
                            @SchemaProperty(name = "code", schema = @Schema(title = "응답 코드", type = "int", description = "응답 코드", example = "-1")),
                            @SchemaProperty(name = "msg", schema = @Schema(title = "응답 메세지", type = "string", description = "응답 메세지", example = "유효성검사 실패")),
                            @SchemaProperty(name = "data", schema = @Schema(title = "응답 데이터", type = "object", description = "응답 데이터", example = """
                                        {
                                        "username": "영문/숫자 2~20자 이내로 작성해주세요.",
                                        "password": "4~20자여야 합니다.",
                                        "email": "이메일 형식으로 작성해주세요.",
                                        "fullname": "한글/영문 1~20자 이내로 작성해주세요."
                                        }                           
                                    """))
                    }))
    })
    @PostMapping("join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<?> join(@RequestBody @Valid JoinRequest joinRequest, BindingResult bindingResult) {
        JoinResponse joinResponse = userService.signUp(joinRequest);
        return new ResponseDto<>(1, "회원가입 성공", joinResponse);
    }
}
