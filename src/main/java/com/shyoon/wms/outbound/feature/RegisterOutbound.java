package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.InventoryRepository;
import com.shyoon.wms.outbound.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
    private final PackagingMaterialRepository packagingMaterialRepository;
    private final ConstructOutbound constructOutbound = new ConstructOutbound();

    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        // 주문 정보 조회
        final Order order = orderRepository.getBy(request.orderNo);
        final List<Inventories> inventoriesList = inventoriesList(order.getOrderProducts());
        final PackagingMaterials packagingMaterials = new PackagingMaterials(packagingMaterialRepository.findAll());
        final Outbound outbound = constructOutbound.create(
                inventoriesList,
                packagingMaterials,
                order,
                request.isPriorityDelivery,
                request.desiredDeliveryAt);

        // 출고 등록
        outboundRepository.save(outbound);
    }

    Outbound createOutbound(final List<Inventories> inventoriesList,
                            final List<PackagingMaterial> packagingMaterials,
                            final Order order,
                            final Boolean isPriorityDelivery,
                            final LocalDate desiredDeliveryAt) {

        return constructOutbound.create(inventoriesList, new PackagingMaterials(packagingMaterials), order, isPriorityDelivery, desiredDeliveryAt);
    }

    private List<Inventories> inventoriesList(final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> inventoryRepository.findByProductNo(orderProduct.getProductNo()))
                .map(Inventories::new)
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
    }
}
