package com.example.product.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
