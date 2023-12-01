package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.outbound.domain.*;

import java.time.LocalDate;
import java.util.List;

public class ConstructOutbound {

    Outbound create(final List<Inventories> inventoriesList,
                    final List<PackagingMaterial> packagingMaterials,
                    final Order order,
                    final Boolean isPriorityDelivery,
                    final LocalDate desiredDeliveryAt) {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            final Inventories inventories = getInventories(inventoriesList, orderProduct.getProductNo());
            inventories.validateInventory(orderProduct.getOrderQuantity());
        }

        final PackagingMaterial optimalPackagingMaterial = new PackagingMaterials(packagingMaterials)
                .getOptimalPackagingMaterial(order.totalWeight(), order.totalVolume());
        return create(
                order,
                optimalPackagingMaterial,
                isPriorityDelivery,
                desiredDeliveryAt);
    }

    Inventories getInventories(final List<Inventories> inventoriesList,
                               final Long productNo) {
        return inventoriesList.stream()
                .filter(i -> i.equalsProductNo(productNo))
                .findFirst()
                .orElseThrow();
    }

    Outbound create(final Order order,
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
}
