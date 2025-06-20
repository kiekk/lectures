package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.LPNFixture;

public class InventoryFixture {

    private LocationFixture location = LocationFixture.aLocation();
    private LPNFixture lpn = LPNFixture.anLPN();
    private Long inventoryQuantity = 1L;
    private Long inventoryNo = 1L;

    public static InventoryFixture anInventory() {
        return new InventoryFixture();
    }

    public InventoryFixture inventoryNo(final Long inventoryNo) {
        this.inventoryNo = inventoryNo;
        return this;
    }

    public InventoryFixture location(final LocationFixture location) {
        this.location = location;
        return this;
    }

    public InventoryFixture lpn(final LPNFixture lpn) {
        this.lpn = lpn;
        return this;
    }

    public InventoryFixture inventoryQuantity(final Long inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
        return this;
    }

    public Inventory build() {
        return new Inventory(
                location.build(),
                lpn.build(),
                inventoryQuantity);
    }
}
