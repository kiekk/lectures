package com.shyoon.wms.location.domain;

import com.shyoon.wms.outbound.feature.Inventories;

import java.util.List;

public class InventoriesFixture {
    private List<InventoryFixture> inventories = List.of(InventoryFixture.anInventory());

    public static InventoriesFixture anInventories() {
        return new InventoriesFixture();
    }

    public Inventories build() {
        return new Inventories(buildInventories());
    }

    public InventoriesFixture inventories(final InventoryFixture... inventories) {
        this.inventories = List.of(inventories);
        return this;
    }

    private List<Inventory> buildInventories() {
        return inventories.stream()
                .map(InventoryFixture::build)
                .toList();
    }
}
