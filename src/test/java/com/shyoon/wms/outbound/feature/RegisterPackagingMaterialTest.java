package com.shyoon.wms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterPackagingMaterialTest {

    private RegistetPackagingMaterial registetPackagingMaterial;

    @BeforeEach
    void setUp() {
        registetPackagingMaterial = new RegistetPackagingMaterial();
    }

    @Test
    @DisplayName("포장재를 등록한다.")
    void registerPackagingMaterial() {
        final String name = "name";
        final String code = "code";
        final Long innerWidthInMillimeters = 1000L;
        final Long innerHeightInMillimeters = 1000L;
        final Long innerLengthInMillimeters = 1000L;
        final Long outerWidthInMillimeters = 1000L;
        final Long outerHeightInMillimeters = 1000L;
        final Long outerLengthInMillimeters = 1000L;
        final Long weightInGrams = 100L;
        final Long maxWeightInGrams = 10000L;

        final RegistetPackagingMaterial.Request request = new RegistetPackagingMaterial.Request(
                name,
                code,
                innerWidthInMillimeters,
                innerHeightInMillimeters,
                innerLengthInMillimeters,
                outerWidthInMillimeters,
                outerHeightInMillimeters,
                outerLengthInMillimeters,
                weightInGrams,
                maxWeightInGrams,
                MaterialType.CORRUGATED_BOX
        );
        registetPackagingMaterial.request(request);
    }

    private class RegistetPackagingMaterial {
        public void request(Request request) {

        }

        public record Request(
                String name,
                String code,
                Long innerWidthInMillimeters,
                Long innerHeightInMillimeters,
                Long innerLengthInMillimeters,
                Long outerWidthInMillimeters,
                Long outerHeightInMillimeters,
                Long outerLengthInMillimeters,
                Long weightInGrams,
                Long maxWeightInGrams,
                MaterialType materialType) {
        }
    }

    enum MaterialType {
        CORRUGATED_BOX("완충재가 있는 골판지 상자");

        private final String description;

        MaterialType(String description) {
            this.description = description;
        }
    }
}
