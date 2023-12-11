package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Inventory;

import java.util.Comparator;
import java.util.List;

public final class Inventories {
    private final List<Inventory> inventories;

    public Inventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public void validateInventory(Long orderQuantity) {
        final long totalInventoryQuantity = calculateTotalFreshInventory();
        // 재고가 주문한 수량보다 적을 경우 예외
        if (totalInventoryQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d".formatted(totalInventoryQuantity, orderQuantity)
            );
        }
    }

    private long calculateTotalFreshInventory() {
        return inventories.stream()
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
    }

    public boolean equalsProductNo(final Long productNo) {
        return inventories.stream()
                .anyMatch(i -> i.getProductNo().equals(productNo));
    }

    public Inventories getBy(final Long productNo) {
        return new Inventories(
                inventories.stream()
                        .filter(inventory -> inventory.getProductNo().equals(productNo))
                        .toList()
        );
    }

    public void filterFreshInventory() {
        inventories.removeIf(inventory -> !inventory.isFresh());
    }

    public int size() {
        return inventories.size();
    }

    public Inventories sortEfficientInventoriesForPicking() {
        // 필터링 된 재고 목록 중 유통 기한이 가장 짧은 물품을 먼저 집품
        // 수량이 가장 적은 로케이션으로 가서 집품
        // 로케이션 순서로 정렬

        return new Inventories(inventories.stream()
                .sorted(Comparator.comparing(Inventory::getExpirationAt)
                        .thenComparing(Inventory::getInventoryQuantity, Comparator.reverseOrder())
                        .thenComparing(Inventory::getLocationBarcode)
                ).toList()
        );
    }

    public List<Inventory> toList() {
        return inventories.stream().toList();
    }

    public Inventories makeEfficientInventoriesForPicking(final Long productNo) {
        final Inventories productInventories = getBy(productNo);
        productInventories.filterFreshInventory();
        return sortEfficientInventoriesForPicking();
    }

    public Inventory getBy(final Inventory inventory) {
        return inventories.stream()
                .filter(inventory1 -> inventory1.equals(inventory))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 재고가 존재하지 않습니다."));
    }
}
