package shop.mtcoding.bank.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shop.mtcoding.bank.service.user.UserService.JoinRequest;
import static shop.mtcoding.bank.service.user.UserService.JoinResponse;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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
        User user = User.builder()
                .id(1L)
                .username("soono")
                .password("1234")
                .email("shyoon991@gmail.com")
                .fullname("soono")
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(userRepository.save(any())).thenReturn(user);

        // when
        JoinResponse joinResponse = userService.signUp(joinRequest);

        // then
        assertThat(joinResponse.getId()).isEqualTo(1L);
        assertThat(joinResponse.getUsername()).isEqualTo("soono");
    }

}