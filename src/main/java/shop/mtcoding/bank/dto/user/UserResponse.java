package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.user.User;

public class UserResponse {

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
