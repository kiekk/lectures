package hello.proxy;

import hello.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV1Config.class) // 컴포넌트 스캔 범위에 포함되어 있지 않을 경우 강제로 import 해줌
// 컴포넌트 스캔 범위를 임의로 지정할 경우
// hello.proxy.app 외에 범위들은 자동으로 컴포넌트 스캔이 되지 않기 때문에 주의!
// 현재는 '수동' 으로 bean 을 등록하기 위해 임의로 컴포넌트 스캔 범위를 지정함
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
//@SpringBootApplication    // default로 한다면 굳이 @Import 를 추가하지 않아도 컴포넌트 스캔이 동작함
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

}
