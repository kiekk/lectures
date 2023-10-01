package shop.mtcoding.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "응답 결과 DTO")
@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {
    @Schema(title = "결과 코드", description = "1: 성공, -1: 실패")
    private final Integer code; // 1 성공, -1 실패
    @Schema(title = "메세지")
    private final String msg;
    @Schema(title = "응답 데이터")
    private final T data;
}
