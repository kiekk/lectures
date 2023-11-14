package com.shyoon.wms.location.feature;

import com.shyoon.wms.inbound.domain.LPNRepository;
import com.shyoon.wms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AssignLocationLPNTest {

    private AssignLocationLPN assignLocationLPN;
    private LocationRepository locationRepository;
    private LPNRepository lpnRepository;

    @BeforeEach
    void setUp() {
        assignLocationLPN = new AssignLocationLPN(
                locationRepository,
                lpnRepository);
    }

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLocationLPN() {
        final String locationBarcode = "A-1-1";
        final String lpnBarcode = "LPN-1";
        final AssignLocationLPN.Request request = new AssignLocationLPN.Request(
                locationBarcode,
                lpnBarcode
        );
        assignLocationLPN.request(request);


    }

}
