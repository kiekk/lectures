package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(MyConfig.class);
        annotationConfigApplicationContext.refresh();

        Bean1 bean1 = annotationConfigApplicationContext.getBean(Bean1.class);
        Bean2 bean2 = annotationConfigApplicationContext.getBean(Bean2.class);

        // bean 등록 방식으로 작성했을 때
        // bean1 과 bean2 의 common 은 같음
        // @Configuration 애노테이션의 동작 방식을 확인
        Assertions.assertThat(bean1.common).isEqualTo(bean2.common);
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }

    }

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {

    }
}
