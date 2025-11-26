package tobyspring.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import tobyspring.splearn.application.member.required.EmailSender;
import tobyspring.splearn.domain.member.PasswordEncoder;

import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (to, subject, body) -> System.out.println("Send email to: " + to);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return createPasswordEncoder();
    }
}
