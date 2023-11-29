package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.location.domain.InventoryRepository;
import com.shyoon.wms.outbound.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegisterOutbound {

    private final OrderRepository orderRepository;
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;

    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        // 주문 정보 조회
        final Order order = orderRepository.getBy(request.orderNo);

        // 주문 정보에 맞는 상품의 재고가 충분한지 확인
        // 충분하지 않으면 예외
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            // 해당 상품의 재고를 전부 가져온다.
            final List<Inventory> inventories = inventoryRepository.findByProductNo(orderProduct.getProductNo());

            validateInventory(inventories, orderProduct.getOrderQuantity());
        }

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

    void validateInventory(final List<Inventory> inventories,
                           final Long orderQuantity) {
        final long totalInventoryQuantity = inventories.stream()
                .filter(i -> 0L < i.getInventoryQuantity())
                .filter(i -> i.getLpn().getExpirationAt().isAfter(LocalDateTime.now()))
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();

        // 재고가 주문한 수량보다 적을 경우 예외
        if (totalInventoryQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d".formatted(totalInventoryQuantity, orderQuantity)
            );
        }
    }

    public record Request(
            @NotNull(message = "주문번호는 필수입니다.")
            Long orderNo,
            @NotNull(message = "우선출고여부는 필수입니다.")
            Boolean isPriorityDelivery,
            @NotNull(message = "희망출고일은 필수입니다.")
            LocalDate desiredDeliveryAt
    ) {
        public Request {
            Assert.notNull(orderNo, "주문번호는 필수입니다.");
            Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
            Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
        }
    }
}
