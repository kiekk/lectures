package com.shyoon.wms.outbound.feature;

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
        final List<Inventories> inventoriesList = getInventoriesList(order.getOrderProducts());

        // 주문 정보에 맞는 상품의 재고가 충분한지 확인
        // 충분하지 않으면 예외
        inventoriesList.forEach(Inventories::validateInventory);

        // 출고 생성
        final Outbound outbound = createOutbound(request, order);

        // 출고 등록
        outboundRepository.save(outbound);
    }

    private static Outbound createOutbound(final Request request,
                                           final Order order) {
        return new Outbound(
                order.getOrderNo(),
                order.getOrderCustomer(),
                order.getDeliveryRequirements(),
                order.getOrderProducts().stream()
                        .map(orderProduct -> new OutboundProduct(
                                orderProduct.getProduct(),
                                orderProduct.getOrderQuantity(),
                                orderProduct.getUnitPrice()))
                        .toList(),
                request.isPriorityDelivery,
                request.desiredDeliveryAt
        );
    }

    private List<Inventories> getInventoriesList(final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> new Inventories(
                        inventoryRepository.findByProductNo(orderProduct.getProductNo())
                        , orderProduct.getOrderQuantity()))
                .toList();
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
