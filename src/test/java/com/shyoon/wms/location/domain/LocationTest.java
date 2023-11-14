package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.LPN;
import com.shyoon.wms.inbound.domain.LPNFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLPN() {
        final Location location = createLocation();

        final LPN lpn = LPNFixture.anLPN().build();

        location.assignLPN(lpn);
    }

    private Location createLocation() {
        final String locationBarcode = "A-1-1";
        final StorageType storageType = StorageType.TOTE;
        final UsagePurpose usagePurpose = UsagePurpose.MOVE;
        return new Location(
                locationBarcode,
                storageType,
                usagePurpose
        );
    }
}
