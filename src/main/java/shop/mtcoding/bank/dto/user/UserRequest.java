package shop.mtcoding.bank.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class UserRequest {

    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Schema(description = "회원 가입 요청")
    @Getter
    @Setter
    public static class JoinRequest {
        @Schema(title = "사용자명", description = "영문/숫자 2~20자 이내", maxLength = 20, minLength = 2, example = "username22")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요.")
        @NotEmpty(message = "필수 값입니다.")
        @Length(min = 2, max = 20, message = "2~20자여야 합니다.")
        private String username;

        @Schema(title = "비밀번호", description = "4~20자 이내", maxLength = 20, minLength = 4, example = "password")
        @NotEmpty(message = "필수 값입니다.")
        @Length(min = 4, max = 20, message = "4~20자여야 합니다.")
        private String password;

        @Schema(title = "이메일", description = "이메일 양식", example = "test@gmail.com")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성해주세요.")
        @NotEmpty(message = "필수 값입니다.")
        private String email;

        @Schema(title = "풀네임", description = "한글/영문 1~20자 이내", maxLength = 20, minLength = 1, example = "full네임")
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요.")
        @NotEmpty(message = "필수 값입니다.")
        private String fullname;

        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }

}
