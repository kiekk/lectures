package shop.mtcoding.bank.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shop.mtcoding.bank.domain.user.UserRepository;

@Configuration
@Profile("dev") // dev 환경에서만 실행됨
public class DummyDevInit extends DummyObject {
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            userRepository.save(newUser("soono", "soono"));
        };
    }
}
