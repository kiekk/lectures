package shop.mtcoding.bank.service.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.handler.exception.CustomApiException;

import java.util.Optional;

import static shop.mtcoding.bank.dto.user.UserRequest.JoinRequest;
import static shop.mtcoding.bank.dto.user.UserResponse.JoinResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JoinResponse signUp(JoinRequest joinRequest) {
        // 1. username 중복 검사
        Optional<User> user = userRepository.findByUsername(joinRequest.getUsername());

        if (user.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        // 2. password 인코딩
        User userPS = userRepository.save(joinRequest.toEntity(passwordEncoder));

        // 3. dto 응답
        return new JoinResponse(userPS);
    }

}
