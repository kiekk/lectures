package com.example.springdatajpa.api;

import com.example.springdatajpa.domain.Order;
import com.example.springdatajpa.repository.OrderRepository;
import com.example.springdatajpa.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne (ManyToOne, OneToOne) 성능 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        all.forEach(order -> {
            order.getMember().getName();    // LAZY 강제 초기화
            order.getMember().getAddress(); // LAZY 강제 초기화
        });
        return all;
    }

}
