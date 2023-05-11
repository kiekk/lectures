package com.example.product.payment;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class PaymentRepository {
    private final Map<Long, Payment> persistence = new HashMap<>();
    private Long sequence = 0L;

    public void save(final Payment payment) {
        payment.assignId(++sequence);
        persistence.put(payment.getId(), payment);
    }
}
