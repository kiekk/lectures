package com.inflearn.toby.order;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String no, BigDecimal total) {
        Order order = Order.create(no, total);
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> createOrders(List<OrderRequest> orderRequests) {
        return orderRequests.stream()
                .map(orderRequest ->
                        createOrder(orderRequest.no(), orderRequest.total()))
                .toList();
    }
}
