package com.shyoon.wms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;

class RegisterOutboundTest {

    private RegisterOutbound registerOutbound;

    @BeforeEach
    void setUp() {
        registerOutbound = new RegisterOutbound();
    }

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutboundTest() {
        final Long orderNo = 1L;
        final Boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
        final RegisterOutbound.Request request = new RegisterOutbound.Request(
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );
        registerOutbound.request(request);
    }

    private class RegisterOutbound {

        public void request(final Request request) {

        }

        public static class Request {
            private final Long orderNo;
            private final Boolean isPriorityDelivery;
            private final LocalDate desiredDeliveryAt;

            public Request(
                    final Long orderNo,
                    final Boolean isPriorityDelivery,
                    final LocalDate desiredDeliveryAt) {
                Assert.notNull(orderNo, "주문번호는 필수입니다.");
                Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
                Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");

                this.orderNo = orderNo;
                this.isPriorityDelivery = isPriorityDelivery;
                this.desiredDeliveryAt = desiredDeliveryAt;
            }
        }
    }
}
