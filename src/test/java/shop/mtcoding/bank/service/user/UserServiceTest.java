package shop.mtcoding.bank.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shop.mtcoding.bank.dto.user.UserRequest.JoinRequest;
import static shop.mtcoding.bank.dto.user.UserResponse.JoinResponse;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void signUpTest() {
        // given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUsername("soono");
        joinRequest.setPassword("1234");
        joinRequest.setEmail("shyoon991@gmail.com");
        joinRequest.setFullname("soono");

        // stub - 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub - 2
        User user = newMockUser(1L, "soono", "soono");
        when(userRepository.save(any())).thenReturn(user);

        // when
        JoinResponse joinResponse = userService.signUp(joinRequest);

        // then
        assertThat(joinResponse.getId()).isEqualTo(1L);
        assertThat(joinResponse.getUsername()).isEqualTo("soono");
    }

}
