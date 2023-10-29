package dev.be.moduleapi.exceptionhandler;

import dev.be.moduleapi.exception.CustomException;
import dev.be.moduleapi.response.CommonResponse;
import dev.be.modulecommon.enums.CodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public CommonResponse<?> handlerCustomException(CustomException exception) {
        return CommonResponse.builder()
                .returnCode(exception.getReturnCode())
                .returnMessage(exception.getReturnMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handlerException(Exception exception) {
        return CommonResponse.builder()
                .returnCode(CodeEnum.UNKNOWN_ERROR.getCode())
                .returnMessage(CodeEnum.UNKNOWN_ERROR.getMessage())
                .build();
    }
}
