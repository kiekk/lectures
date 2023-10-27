package dev.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 2. api 모듈의 Application 클래스의 패키지 경로를 커먼 모듈의 기본 패키지 경로와 맞춰준다.
@SpringBootApplication
public class ModuleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
