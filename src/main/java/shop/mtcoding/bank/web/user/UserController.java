package shop.mtcoding.bank.web.user;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "회원 가입")
    @PostMapping("join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<?> join(@RequestBody @Valid JoinRequest joinRequest, BindingResult bindingResult) {
        JoinResponse joinResponse = userService.signUp(joinRequest);
        return new ResponseDto<>(1, "회원가입 성공", joinResponse);
    }

}
