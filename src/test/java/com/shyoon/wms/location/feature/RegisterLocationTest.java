package com.shyoon.wms.location.feature;

import com.shyoon.wms.location.domain.LocationRepository;
import com.shyoon.wms.location.domain.StorageType;
import com.shyoon.wms.location.domain.UsagePurpose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterLocationTest {

    private RegisterLocation registerLocation;
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        locationRepository = new LocationRepository();
        registerLocation = new RegisterLocation(locationRepository);
    }

    @Test
    @DisplayName("로케이션을 등록한다.")
    void registerLocation() {
        final String locationBarcode = "A-1-1";
        final StorageType storageType = StorageType.TOTE;
        final UsagePurpose usagePurpose = UsagePurpose.MOVE;
        RegisterLocation.Request request = new RegisterLocation.Request(
                locationBarcode,
                storageType,
                usagePurpose
        );
        registerLocation.request(request);

        assertThat(locationRepository.findAll()).hasSize(1);
    }

}
