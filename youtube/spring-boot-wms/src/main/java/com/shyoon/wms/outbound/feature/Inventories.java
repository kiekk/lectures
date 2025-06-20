package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Inventory;
import org.springframework.util.Assert;

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

    public Inventory getBy(final Inventory inventory) {
        return inventories.stream()
                .filter(inventory1 -> inventory1.equals(inventory))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 재고가 존재하지 않습니다."));
    }

    public int size() {
        return inventories.size();
    }


    public Inventories makeEfficientInventoriesForPicking(final Long productNo, final Long orderQuantity) {
        validate(productNo, orderQuantity);
        final List<Inventory> filteredInventories = filterAvailableInventories(productNo);

        checkInventoryAvailability(orderQuantity, filteredInventories);
        final List<Inventory> sortedEfficientInventories = sortEfficientInventoriesForPicking(filteredInventories);

        return new Inventories(sortedEfficientInventories);
    }

    private void validate(final Long productNo, final Long orderQuantity) {
        Assert.notNull(productNo, "상품 번호가 없습니다.");
        Assert.notNull(orderQuantity, "주문 수량이 없습니다.");
        if (0 >= orderQuantity) {
            throw new IllegalArgumentException("주문 수량은 0보다 커야 합니다.");
        }
    }

    private List<Inventory> filterAvailableInventories(final Long productNo) {
        return inventories.stream()
                .filter(i -> i.getProductNo().equals(productNo))
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .toList();
    }

    private void checkInventoryAvailability(
            final Long orderQuantity, final List<Inventory> inventories) {
        final long totalQuantity = inventories.stream()
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
        if (totalQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d"
                            .formatted(totalQuantity, orderQuantity));
        }
    }

    private List<Inventory> sortEfficientInventoriesForPicking(
            final List<Inventory> inventories) {
        return inventories.stream()
                .sorted(Comparator.comparing(Inventory::getExpirationAt)
                        .thenComparing(Inventory::getInventoryQuantity, Comparator.reverseOrder())
                        .thenComparing(Inventory::getLocationBarcode)
                )
                .toList();
    }

    public List<Inventory> toList() {
        return inventories.stream().toList();
    }

}
