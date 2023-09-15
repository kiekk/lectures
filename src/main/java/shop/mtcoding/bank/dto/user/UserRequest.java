package shop.mtcoding.bank.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinRequest {
        @NotEmpty(message = "필수 값입니다.")
        private String username;
        @NotEmpty(message = "필수 값입니다.")
        @Length(max = 20, message = "20자 이하여야 합니다.")
        private String password;
        @NotEmpty(message = "필수 값입니다.")
        private String email;
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
