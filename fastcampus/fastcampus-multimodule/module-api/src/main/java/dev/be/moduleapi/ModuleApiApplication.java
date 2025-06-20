package dev.be.moduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 실제 현업에서는 아래와 같은 방법을 주로 사용
// 2번 방법을 사용하면 api 모듈이 의존하고 있는 모든 module에서 component scan을 하지만,
// 명시적으로 component scan 범위를 지정하게 되면 원하는 module에서 component scan을 통해
// 원하는 bean들만 의존
@SpringBootApplication(scanBasePackages = {"dev.be.modulecommon", "dev.be.moduleapi"})
@EntityScan("dev.be.modulecommon.domain")
@EnableJpaRepositories(basePackages = "dev.be.modulecommon.repositories")
public class ModuleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
