package dev.be.moduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 1. 커먼 모듈에 있는 빈들을 스캔할 수 있도록 커먼 모듈의 기본 패키지 경로를 추가
@SpringBootApplication(scanBasePackages = {"dev.be.modulecommon", "dev.be.moduleapi"})
public class ModuleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
