package com.shyoon.wms.location.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class RegisterLocationTest {

    private RegisterLocation registerLocation;

    @BeforeEach
    void setUp() {
        registerLocation = new RegisterLocation();
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
    }

    private class RegisterLocation {
        public void request(final Request request) {
            Location location = request.toDomain();
        }

        public record Request(
                String locationBarcode,
                StorageType storageType,
                UsagePurpose usagePurpose) {

            public Location toDomain() {
                return new Location(
                        locationBarcode,
                        storageType,
                        usagePurpose
                );
            }
        }
    }

    enum StorageType {
        TOTE("토트바구니");

        private final String description;

        StorageType(String description) {
            this.description = description;
        }
    }

    enum UsagePurpose {
        MOVE("이동 목적");

        private final String description;

        UsagePurpose(String description) {
            this.description = description;
        }
    }

    private static class Location {
        private final String locationBarcode;
        private final StorageType storageType;
        private final UsagePurpose usagePurpose;

        public Location(
                String locationBarcode,
                StorageType storageType,
                UsagePurpose usagePurpose) {
            validateConstructor(locationBarcode, storageType, usagePurpose);

            this.locationBarcode = locationBarcode;
            this.storageType = storageType;
            this.usagePurpose = usagePurpose;
        }

        private void validateConstructor(String locationBarcode, StorageType storageType, UsagePurpose usagePurpose) {
            Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
            Assert.notNull(storageType, "보관 타입은 필수입니다.");
            Assert.notNull(usagePurpose, "보관 목적은 필수입니다.");
        }
    }
}
