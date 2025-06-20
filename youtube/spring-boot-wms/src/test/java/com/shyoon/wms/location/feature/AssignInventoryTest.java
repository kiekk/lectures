package com.shyoon.wms.location.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssignInventoryTest extends ApiTest {

    @BeforeEach
    void assignInventorySetup() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request();
    }

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    @Transactional
    void assignInventory() {
        Scenario
                .assignInventory().request();

        final String locationBarcode = "A-1-1";
        final Location location = locationRepository.getByLocationBarcode(locationBarcode);
        final List<Inventory> inventoryList = location.getInventories();
        final Inventory inventory = inventoryList.get(0);

        assertThat(inventoryList).hasSize(1);
        assertThat(inventory.getInventoryQuantity()).isEqualTo(1L);
    }

}
