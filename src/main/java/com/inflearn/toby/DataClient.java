package com.inflearn.toby;

import com.inflearn.toby.data.OrderRepository;
import com.inflearn.toby.order.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        try {
            // transaction begin
            new TransactionTemplate(transactionManager).execute(status -> {
                Order order = Order.create("100", BigDecimal.TEN);
                orderRepository.save(order);

                System.out.println("Order saved: " + order); // Order saved: Order{id=1, no='100', total=10}

                Order order2 = Order.create("100", BigDecimal.ONE);
                orderRepository.save(order2); // ERROR
                return null;
            });
            // transaction commit
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            /*
            Order Entity에 ID 생성 전략을 IDENTITY로 설정할 경우 예외가 발생할 때 DataAccessException으로 래핑되지 못한다.
            org.hibernate.exception.ConstraintViolationException 예외가 그대로 발생하는데, 이유는 README.md에 정리 예정.
            지금은 IDENTITY를 사용하지 않고 AUTO, 즉 H2에서는 SEQUENCE/TABLE을 사용하도록 한다.
             */
            System.out.println("주문번호 중복 복구 작업");
        }
    }
}
