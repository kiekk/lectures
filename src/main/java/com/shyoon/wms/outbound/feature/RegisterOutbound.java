package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.InventoryRepository;
import com.shyoon.wms.outbound.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
    private final PackagingMaterialRepository packagingMaterialRepository;

    @PostMapping("/outbounds")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        // 주문 정보 조회
        final Order order = orderRepository.getBy(request.orderNo);
        final List<Inventories> inventoriesList = getInventoriesList(order.getOrderProducts());
        final List<PackagingMaterial> packagingMaterials = packagingMaterialRepository.findAll();
        final Outbound outbound = createOutbound(inventoriesList, packagingMaterials, order, request.isPriorityDelivery, request.desiredDeliveryAt);

        // 출고 등록
        outboundRepository.save(outbound);
    }

    Outbound createOutbound(final List<Inventories> inventoriesList,
                            final List<PackagingMaterial> packagingMaterials,
                            final Order order,
                            final Boolean isPriorityDelivery,
                            final LocalDate desiredDeliveryAt) {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            final Inventories inventories = getInventories(inventoriesList, orderProduct);
            inventories.validateInventory(orderProduct.getOrderQuantity());
        }

        final PackagingMaterial optimalPackagingMaterial = new PackagingMaterials(packagingMaterials)
                .getOptimalPackagingMaterial(order.totalWeight(), order.totalVolume());
        return createOutbound(
                order,
                optimalPackagingMaterial,
                isPriorityDelivery,
                desiredDeliveryAt);
    }

    private Inventories getInventories(final List<Inventories> inventoriesList,
                                       final OrderProduct orderProduct) {
        return inventoriesList.stream()
                .filter(i -> i.equalsProductNo(orderProduct.getProductNo()))
                .findFirst()
                .orElseThrow();
    }

    Outbound createOutbound(final Order order,
                            final PackagingMaterial recommendedPackagingMaterial,
                            final Boolean isPriorityDelivery,
                            final LocalDate desiredDeliveryAt) {
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
                isPriorityDelivery,
                desiredDeliveryAt,
                recommendedPackagingMaterial
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
