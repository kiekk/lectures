package com.inflearn.toby;

import com.inflearn.toby.data.OrderRepository;
import com.inflearn.toby.order.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);

        Order order = Order.create("100", BigDecimal.TEN);

        orderRepository.save(order);

        System.out.println("Order saved: " + order); // Order saved: Order{id=1, no='100', total=10}

        Order order2 = Order.create("100", BigDecimal.ONE);

        orderRepository.save(order2);
        /*
            ERROR:
            org.hibernate.exception.ConstraintViolationException
             -> org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException

             = 예외 발생 케이스에 따라 다른 예외가 발생한다.
             = Exception으로 모든 예외를 처리하지 않는 이상 모든 케이스에 대한 예외를 각각 처리하는 방식은 좋지 않다.
         */
    }
}
