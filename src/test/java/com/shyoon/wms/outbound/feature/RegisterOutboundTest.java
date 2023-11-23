package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.product.domain.Product;
import com.shyoon.wms.product.domain.ProductRepository;
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
            // 주문 정보 조회
            final Order order = orderRepository.getBy(request.orderNo);

            // 주문 정보에 맞는 상품의 재고가 충분한지 확인
            // 충분하지 않으면 예외

            // 출고에 사용할 포장재를 선택

            // 출고 생성
            final List<OutboundProduct> outboundProducts = order.orderProducts.stream()
                    .map(orderProduct -> new OutboundProduct(
                            orderProduct.product,
                            orderProduct.orderQuantity,
                            orderProduct.unitPrice))
                    .toList();

            // 출고 등록
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

        private ProductRepository productRepository;

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
                    productRepository.getBy(productNo),
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

    }

    private class Order {
        private final Long orderNo;
        private final OrderCustomer orderCustomer;
        private final String deliveryRequirements;
        private final List<OrderProduct> orderProducts;

        public Order(
                final Long orderNo,
                final OrderCustomer orderCustomer,
                final String deliveryRequirements,
                final List<OrderProduct> orderProducts) {
            this.orderNo = orderNo;
            this.orderCustomer = orderCustomer;
            this.deliveryRequirements = deliveryRequirements;
            this.orderProducts = orderProducts;
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
        private final Product product;
        private final Long orderQuantity;
        private final Long unitPrice;

        public OrderProduct(
                final Product product,
                final Long orderQuantity,
                final Long unitPrice) {

            this.product = product;
            this.orderQuantity = orderQuantity;
            this.unitPrice = unitPrice;
        }
    }

    private class OutboundProduct {
        private final Product product;
        private final Long orderQuantity;
        private final Long unitPrice;

        public OutboundProduct(
                final Product product,
                final Long orderQuantity,
                final Long unitPrice) {
            validateConstructor(product, orderQuantity, unitPrice);

            this.product = product;
            this.orderQuantity = orderQuantity;
            this.unitPrice = unitPrice;
        }

        private void validateConstructor(Product product, Long orderQuantity, Long unitPrice) {
            Assert.notNull(product, "상품은 필수입니다.");
            Assert.notNull(product, "주문 수량은 필수입니다.");
            if (orderQuantity < 1) {
                throw new IllegalArgumentException("주문 수량은 1개 이상이어야 합니다.");
            }

            Assert.notNull(unitPrice, "단가는 필수입니다.");
            if (unitPrice < 1) {
                throw new IllegalArgumentException("단가는 1원 이상이어야 합니다.");
            }
        }
    }
}
