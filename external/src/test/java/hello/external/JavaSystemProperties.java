package hello.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class JavaSystemProperties {

    // Java System 속성 조회 & 출력
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            log.info("prop {}={}", key, System.getProperty(String.valueOf(key)));
        }

        // Custom Java System Properties 조회 & 출력
        String url = System.getProperty("url");
        String username = System.getProperty("username");
        String password = System.getProperty("password");

        log.info("url = {}", url);
        log.info("username = {}", username);
        log.info("password = {}", password);

        // Custom Java System Properties Code 에서 설정 & 출력
        System.setProperty("hello_key", "hello_value");
        String hello_key = System.getProperty("hello_key");
        log.info("hello_key = {}", hello_key);
    }

}
