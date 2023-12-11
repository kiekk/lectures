package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.location.domain.InventoryRepository;
import com.shyoon.wms.outbound.domain.Outbound;
import com.shyoon.wms.outbound.domain.OutboundProduct;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import com.shyoon.wms.outbound.domain.Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class AllocatePickingTest {

    private AllocatePicking allocatePicking;

    @BeforeEach
    void setUp() {
        allocatePicking = new AllocatePicking();
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    void allocatePicking() {
        final Long outboundNo = 1L;
        allocatePicking.request(outboundNo);
    }

    private class AllocatePicking {

        private OutboundRepository outboundRepository;
        private InventoryRepository inventoryRepository;

        public void request(final Long outboundNo) {
            final Outbound outbound = outboundRepository.getBy(outboundNo);
            final List<OutboundProduct> outboundProducts = outbound.outboundProducts.toList();
            final Inventories inventories = new Inventories(outboundProducts.stream()
                    .flatMap(op -> inventoryRepository.listBy(op.getProductNo())
                            .stream())
                    .toList());

            // 집품을 할당한다.
            outbound.allocatePicking(inventories);
            deductAllocatedInventories(outbound.getPickings(), inventories);

            // 재고 차감
        }

        private void deductAllocatedInventories(final List<Picking> pickings,
                                                final Inventories inventories) {
            for (Picking picking : pickings) {
                final Inventory inventory = inventories.getBy(picking.getInventory());
                inventory.decreaseQuantity(picking.getQuantity());
            }
        }
    }
}
