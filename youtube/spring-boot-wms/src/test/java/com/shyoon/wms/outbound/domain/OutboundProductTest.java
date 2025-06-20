package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.outbound.feature.Inventories;
import org.junit.jupiter.api.Test;

import static com.shyoon.wms.location.domain.InventoriesFixture.anInventories;
import static com.shyoon.wms.location.domain.InventoryFixture.anInventory;
import static com.shyoon.wms.location.domain.LocationFixture.aLocation;
import static com.shyoon.wms.outbound.domain.OutboundProductFixture.anOutboundProduct;

class OutboundProductTest {

    @Test
    void allocatePicking() {
        final OutboundProduct outboundProduct = anOutboundProduct()
                .orderQuantity(3L)
                .build();
        final Inventories inventories = anInventories()
                .inventories(
                        anInventory()
                                .location(aLocation().locationBarcode("A1"))
                                .inventoryQuantity(2L),
                        anInventory()
                                .location(aLocation().locationBarcode("!2"))
                                .inventoryQuantity(1L)
                )
                .build();
    }
}
