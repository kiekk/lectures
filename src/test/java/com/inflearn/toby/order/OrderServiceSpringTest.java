package com.inflearn.toby.order;

import com.inflearn.toby.OrderConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
class OrderServiceSpringTest {

    @Autowired
    OrderService orderService;

    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
        // when
        Order order = orderService.createOrder("0100", BigDecimal.ONE);

        // then
        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
    void createOrders() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest("0100", BigDecimal.ONE),
                new OrderRequest("0200", BigDecimal.TEN)
        );

        List<Order> orders = orderService.createOrders(orderRequests);

        Assertions.assertThat(orders).hasSize(2);
        orders.forEach(order -> Assertions.assertThat(order.getId()).isGreaterThan(0));
    }

    @Test
    void createDuplicatedOrders() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest("0300", BigDecimal.ONE),
                new OrderRequest("0300", BigDecimal.TEN)
        );

        Assertions.assertThatThrownBy(() -> orderService.createOrders(orderRequests))
                .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        Long count = client.sql("SELECT COUNT(*) FROM orders WHERE no = '0300'")
                .query(Long.class).single();
        Assertions.assertThat(count).isEqualTo(0);
    }
}
