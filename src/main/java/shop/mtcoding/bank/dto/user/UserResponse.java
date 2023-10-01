package shop.mtcoding.bank.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.util.CustomDateUtil;

public class UserResponse {

    @Getter
    @Setter
    public static class LoginResponse {
        private Long id;
        private String username;
        private String createdAt;

        public LoginResponse(LoginUser loginUser) {
            this.id = loginUser.getUser().getId();
            this.username = loginUser.getUsername();
            this.createdAt = CustomDateUtil.toStringFormat(loginUser.getUser().getCreatedAt());
        }
    }

    @Getter
    @Setter
    @Schema(title = "회원 가입 응답")
    public static class JoinResponse {
        @Schema(title = "아이디", description = "아이디")
        private Long id;
        @Schema(title = "사용자명", description = "사용자명")
        private String username;
        @Schema(title = "풀네임", description = "풀네임")
        private String fullname;

        public JoinResponse(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }
    }

}
