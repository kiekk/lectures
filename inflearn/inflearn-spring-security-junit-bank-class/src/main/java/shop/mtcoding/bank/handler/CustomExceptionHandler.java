package shop.mtcoding.bank.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.handler.exception.CustomApiException;
import shop.mtcoding.bank.handler.exception.CustomValidationException;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                new ResponseDto<>(-1, exception.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationException(CustomValidationException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                new ResponseDto<>(-1, exception.getMessage(), exception.getErrorMap()),
                HttpStatus.BAD_REQUEST
        );
    }

}
