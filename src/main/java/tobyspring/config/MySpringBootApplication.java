package tobyspring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@ComponentScan
// 매번 설정 클래스 추가 시 Import 에 해당 클래스를 추가해야 하기 때문에
// @Enable~ 애노테이션을 작성하여 관리
@EnableMyAutoConfiguration
public @interface MySpringBootApplication {
}
