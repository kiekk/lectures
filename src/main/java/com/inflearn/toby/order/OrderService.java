package com.inflearn.toby.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PlatformTransactionManager transactionManager;

    public OrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {
        Order order = Order.create(no, total);
        orderRepository.save(order);
        return order;
    }

    public List<Order> createOrders(List<OrderRequest> orderRequests) {
        return new TransactionTemplate(transactionManager).execute(status -> orderRequests.stream()
                .map(orderRequest ->
                        createOrder(orderRequest.no(), orderRequest.total()))
                .toList());
    }
}
