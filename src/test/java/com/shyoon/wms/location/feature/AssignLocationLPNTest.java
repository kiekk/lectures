package com.shyoon.wms.location.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.LocationLPN;
import com.shyoon.wms.location.domain.LocationRepository;
import com.shyoon.wms.location.feature.api.AssignLocationLPNApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssignLocationLPNTest extends ApiTest {

    @BeforeEach
    void assignLocationLPNSetup() {
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
    void assignLocationLPN() {
        final String locationBarcode = AssignLocationLPNApi.getString();

        final Location location = locationRepository.getByLocationBarcode(locationBarcode);
        final List<LocationLPN> locationLPNList = location.getLocationLPNList();
        final LocationLPN locationLPN = locationLPNList.get(0);

        assertThat(locationLPNList).hasSize(1);
        assertThat(locationLPN.getInventoryQuantity()).isEqualTo(1L);
    }

}
