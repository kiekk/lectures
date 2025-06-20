package hello.advanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedApplication.class, args);
    }

}

/*
    만들어볼 예제 요구사항 - 로그 추적기

    1. 모든 public 메소드의 호출과 응답 정보를 로그로 출력
    2. 애플리케이션의 흐름을 변경하면 안됨 (로그로 인해 비즈니스 로직의 동작에 영향을 주면 안됨)
    3. 메소드 호출에 걸린 시간
    4. 정상 흐름과 예외 흐름 구분 (예외 발생시 예외 정보가 남아야 함)
    5. 메소드 호출의 깊이 표현
    6. HTTP 요청을 구분
    6-1. HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함
    6-2. 트랜잭션 ID, 여기서는 하나의 HTTP 요청이 시작해서 끝날 때 까지를 하나의 트랜잭션이라고 표현
 */
