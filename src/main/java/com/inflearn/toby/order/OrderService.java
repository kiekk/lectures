package com.inflearn.toby.order;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final JpaTransactionManager transactionManager;

    public OrderService(OrderRepository orderRepository, JpaTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {
        Order order = Order.create(no, total);

        new TransactionTemplate(transactionManager).execute(status -> {
            orderRepository.save(order);
            return order;
        });

        return order;
    }
}
