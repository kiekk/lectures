package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.InventoryRepository;
import com.shyoon.wms.outbound.domain.Outbound;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import com.shyoon.wms.outbound.domain.PickingAllocator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AllocatePicking {

    private final PickingAllocator pickingAllocator = new PickingAllocator();
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;

    @PostMapping("/outbounds/{outboundNo}/allocate-picking")
    @Transactional
    public void request(@PathVariable final Long outboundNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final Inventories inventories = inventoryRepository.inventoriesBy(outbound.getProductNos());

        pickingAllocator.allocatePicking(outbound, inventories);
    }
}
