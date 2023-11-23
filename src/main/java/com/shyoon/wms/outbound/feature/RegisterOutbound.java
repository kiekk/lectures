package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.outbound.domain.*;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

public class RegisterOutbound {

    private final OrderRepository orderRepository;
    private final OutboundRepository outboundRepository;

    public RegisterOutbound(OrderRepository orderRepository, OutboundRepository outboundRepository) {
        this.orderRepository = orderRepository;
        this.outboundRepository = outboundRepository;
    }

    public void request(final Request request) {
        // 주문 정보 조회
        final Order order = orderRepository.getBy(request.orderNo);

        // 주문 정보에 맞는 상품의 재고가 충분한지 확인
        // 충분하지 않으면 예외

        // 출고에 사용할 포장재를 선택

        // 출고 생성
        final List<OutboundProduct> outboundProducts = order.getOrderProducts().stream()
                .map(orderProduct -> new OutboundProduct(
                        orderProduct.getProduct(),
                        orderProduct.getOrderQuantity(),
                        orderProduct.getUnitPrice()))
                .toList();

        final Outbound outbound = new Outbound(
                order.getOrderNo(),
                order.getOrderCustomer(),
                order.getDeliveryRequirements(),
                outboundProducts,
                request.isPriorityDelivery,
                request.desiredDeliveryAt
        );

        // 출고 등록
        outboundRepository.save(outbound);
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
