package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.outbound.domain.OrderRepository;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import com.shyoon.wms.product.domain.ProductFixture;
import com.shyoon.wms.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

class RegisterOutboundTest {

    private RegisterOutbound registerOutbound;
    private OrderRepository orderRepository;
    private OutboundRepository outboundRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        orderRepository = new OrderRepository(productRepository);
        outboundRepository = new OutboundRepository();
        registerOutbound = new RegisterOutbound(orderRepository, outboundRepository);
    }

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutboundTest() {
        // given
        final Long orderNo = 1L;
        final Boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
        final RegisterOutbound.Request request = new RegisterOutbound.Request(
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );
        Mockito.when(productRepository.getBy(anyLong()))
                .thenReturn(ProductFixture.aProduct().build());

        // when
        registerOutbound.request(request);

        // then
        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
