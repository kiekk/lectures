package com.example.stateless;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

@SpringBootTest
public class StatefulServiceTest {
    // StatefulService Bean 등록 시 Singleton scope 로 등록했기 때문에
    // UserA, UserB가 각각 StatefulService 에 접근할 때마다 값이 변경되어
    // 동시성 이슈가 발생
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB: B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int priceOfUserA = statefulService1.getPrice();
        System.out.println("priceOfUserA = " + priceOfUserA);

        //ThreadA: 사용자B 주문 금액 조회
        int priceOfUserB = statefulService1.getPrice();
        System.out.println("priceOfUserB = " + priceOfUserB);

        // UserA의 statefulService1, UserB의 statefulService2 비교
        System.out.println("statefulService1 : " + statefulService1);
        System.out.println("statefulService2 : " + statefulService2);
        System.out.println("statefulService1 == statefulService2 : " + (statefulService1 == statefulService2));
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(10000); // 에러발생
    }

    static class StatefulService {
        private int price; // 상태를 가지고 있는 필드

        public void order(String name, int price) {
            System.out.println("name = " + name + " price = " + price);
            this.price = price; // 문제가 발생할 수 있는 코드
        }

        public int getPrice() {
            return price;
        }
    }

}
