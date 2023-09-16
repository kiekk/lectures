package shop.mtcoding.bank.dto.user;

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
    public static class JoinResponse {
        private Long id;
        private String username;
        private String fullname;

        public JoinResponse(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }
    }

}
