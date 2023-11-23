package com.shyoon.wms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

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

        // TODO: 출고 등록 여부 검증
    }

    private class RegisterOutbound {

        private OrderRepository orderRepository;

        public void request(final Request request) {
            orderRepository.getBy(request.orderNo);
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

    private class OrderRepository {
        public Order getBy(final Long orderNo) {
            final OrderCustomer orderCustomer = new OrderCustomer(
                    "name",
                    "email",
                    "phone",
                    "zipNo",
                    "address"
            );
            final String deliveryRequirements = "배송 요구사항";

            final Long productNo = 1L;
            final Long orderQuantity = 1500L;
            final Long unitPrice = 1L;

            final OrderProduct orderProduct = new OrderProduct(
                    productNo,
                    orderQuantity,
                    unitPrice
            );
            final List<OrderProduct> orderProducts = List.of(orderProduct);

            return new Order(
                    orderNo,
                    orderCustomer,
                    deliveryRequirements,
                    orderProducts
                    );
        }

        private class Order {
            public Order(
                    final Long orderNo,
                    final OrderCustomer orderCustomer,
                    final String deliveryRequirements,
                    final List<OrderProduct> orderProducts) {
            }
        }
    }

    private class OrderCustomer {
        private final String name;
        private final String email;
        private final String phone;
        private final String zipNo;
        private final String address;

        public OrderCustomer(
                final String name,
                final String email,
                final String phone,
                final String zipNo,
                final String address) {

            this.name = name;
            this.email = email;
            this.phone = phone;
            this.zipNo = zipNo;
            this.address = address;
        }
    }

    private class OrderProduct {
        private final Long productNo;
        private final Long orderQuantity;
        private final Long unitPrice;

        public OrderProduct(
                final Long productNo,
                final Long orderQuantity,
                final Long unitPrice) {

            this.productNo = productNo;
            this.orderQuantity = orderQuantity;
            this.unitPrice = unitPrice;
        }
    }
}
