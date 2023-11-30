package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Inventory;

import java.util.List;

public record Inventories(List<Inventory> inventories, Long orderQuantity) {
    void validateInventory() {
        final long totalInventoryQuantity = calculateTotalFreshInventory();
        // 재고가 주문한 수량보다 적을 경우 예외
        if (totalInventoryQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d".formatted(totalInventoryQuantity, orderQuantity)
            );
        }
    }

    private long calculateTotalFreshInventory() {
        return inventories().stream()
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
    }

    public boolean equalsProductNo(final Long productNo) {
        return inventories.stream()
                .anyMatch(i -> i.getProductNo().equals(productNo));
    }
}
