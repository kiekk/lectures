package com.inflearn.toby.order;

import com.inflearn.toby.OrderConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
class OrderServiceSpringTest {

    @Autowired
    OrderService orderService;

    @Test
    void createOrder() {
        // when
        Order order = orderService.createOrder("0100", BigDecimal.ONE);

        // then
        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }
}
