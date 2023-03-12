package hello;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvironmentCheck {

    // Environment 를 사용하여 외부 설정을 읽는 방법을 통일
    private final Environment env;

    @PostConstruct
    public void init() {
        String url = env.getProperty("url");
        String user = env.getProperty("user");
        // window 에서는 username 값이 환경 변수의 username 을 읽어옴. *추후 확인
        String username = env.getProperty("username");
        String password = env.getProperty("password");

        log.info("env url = {}", url);
        log.info("env user = {}", user);
        log.info("env username = {}", username);
        log.info("env password = {}", password);
    }
}
