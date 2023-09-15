package shop.mtcoding.bank.web.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.service.user.UserService;

import java.util.HashMap;
import java.util.Map;

import static shop.mtcoding.bank.dto.user.UserRequest.JoinRequest;
import static shop.mtcoding.bank.dto.user.UserResponse.JoinResponse;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<?> join(@RequestBody @Valid JoinRequest joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseDto<>(-1, "유효성 검사 실패", errorMap);
        }
        JoinResponse joinResponse = userService.signUp(joinRequest);
        return new ResponseDto<>(1, "회원가입 성공", joinResponse);
    }

}
