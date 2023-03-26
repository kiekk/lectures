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

    // scope를 prototype으로 설정하면
    // UserA의 statefulService, UserB의 statefulService가 서로 다른 객체가 할당이 되어
    // singleton에서의 동시성 이슈는 발생하지 않는다.
    // 하지만 이런 방식은 Spring이 지향하는 것과 정반대로 가는 거라고 생각해서 옳지 않은 방법이라고 생각한다.
    @Test
    void statefulServicePrototype() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulServiceV2.class);
        StatefulServiceV2 statefulService1 = ac.getBean(StatefulServiceV2.class);
        StatefulServiceV2 statefulService2 = ac.getBean(StatefulServiceV2.class);

        //ThreadA: A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB: B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int priceOfUserA = statefulService1.getPrice();
        System.out.println("priceOfUserA = " + priceOfUserA);

        //ThreadA: 사용자A 주문 금액 조회
        int priceOfUserB = statefulService2.getPrice();
        System.out.println("priceOfUserB = " + priceOfUserB);

        // UserA의 statefulService1, UserB의 statefulService2 비교
        System.out.println("statefulService1 : " + statefulService1);
        System.out.println("statefulService2 : " + statefulService2);
        System.out.println("statefulService1 == statefulService2 : " + (statefulService1 == statefulService2));

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(10000);
        Assertions.assertThat(statefulService2.getPrice()).isEqualTo(20000);
        Assertions.assertThat(statefulService1.getPrice()).isNotEqualTo(statefulService2.getPrice());
    }

    // 위 문제를 모두 충족하려면 Bean 이 더 이상 상태를 가지지 않도록 하면 된다.
    // 상태를 가지지 않도록 하여 Spring 이 지향하는 singleton 방식도 해결하고 동시성 이슈도 해결할 수 있다.
    @Test
    void statelessService() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatelessService.class);
        StatelessService statelessService1 = ac.getBean(StatelessService.class);
        StatelessService statelessService2 = ac.getBean(StatelessService.class);

        //ThreadA: A사용자 10000원 주문
        int priceOfUserA = statelessService1.order("userA", 10000);
        //ThreadB: B사용자 20000원 주문
        int priceOfUserB = statelessService2.order("userB", 20000);

        System.out.println("priceOfUserA = " + priceOfUserA);
        System.out.println("priceOfUserB = " + priceOfUserB);

        // UserA의 statelessService1, UserB의 statelessService2 비교
        System.out.println("statelessService1 : " + statelessService1);
        System.out.println("statelessService2 : " + statelessService2);
        System.out.println("statelessService1 == statelessService2 : " + (statelessService1 == statelessService2));

        Assertions.assertThat(priceOfUserA).isEqualTo(10000);
        Assertions.assertThat(priceOfUserB).isEqualTo(20000);
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

    @Scope("prototype")
    static class StatefulServiceV2 {
        private int price; // 상태를 가지고 있는 필드

        public void order(String name, int price) {
            System.out.println("name = " + name + " price = " + price);
            this.price = price;
            // 이제 Bean 을 요청할 때마다 객체를 생성하기 떄문에
            // 더 이상 동시성 이슈는 발생하지 않는다.
            // 하지만 사용자마다 객체를 생성해주기 때문에 불필요한 객체들이 너무 많이 생성된다.
        }

        public int getPrice() {
            return price;
        }
    }

    static class StatelessService {
        public int order(String name, int price) {
            System.out.println("name = " + name + " price = " + price);
            return price;
        }
    }

}
