package dev.be.moduleapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.be.modulecommon.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String returnCode;
    private String returnMessage;
    private T info;

    public CommonResponse(CodeEnum codeEnum) {
        this(codeEnum, null);
    }

    public CommonResponse(T info) {
        this(CodeEnum.SUCCESS, info);
    }

    public CommonResponse(CodeEnum codeEnum, T info) {
        this.returnCode = codeEnum.getCode();
        this.returnMessage = codeEnum.getMessage();
        this.info = info;
    }

}
