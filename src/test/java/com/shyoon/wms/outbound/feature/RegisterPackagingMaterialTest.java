package com.shyoon.wms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

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

            public Request {
                Assert.hasText(name, "포장재 이름은 필수입니다.");
                Assert.hasText(code, "포장재 코드는 필수입니다.");
                Assert.notNull(innerWidthInMillimeters, "내부 폭은 필수입니다.");
                if (innerWidthInMillimeters < 1) {
                    throw new IllegalArgumentException("내부 폭은 1mm 이상이어야 합니다.");
                }
                Assert.notNull(innerHeightInMillimeters, "내부 높이는 필수입니다.");
                if (innerHeightInMillimeters < 1) {
                    throw new IllegalArgumentException("내부 높이는 1mm 이상이어야 합니다.");
                }
                Assert.notNull(innerLengthInMillimeters, "내부 길이는 필수입니다.");
                if (innerLengthInMillimeters < 1) {
                    throw new IllegalArgumentException("내부 길이는 1mm 이상이어야 합니다.");
                }
                Assert.notNull(outerWidthInMillimeters, "외부 폭은 필수입니다.");
                if (outerWidthInMillimeters < 1) {
                    throw new IllegalArgumentException("외부 폭은 1mm 이상이어야 합니다.");
                }
                Assert.notNull(outerHeightInMillimeters, "외부 높이는 필수입니다.");
                if (outerHeightInMillimeters < 1) {
                    throw new IllegalArgumentException("외부 높이는 1mm 이상이어야 합니다.");
                }
                Assert.notNull(outerLengthInMillimeters, "외부 길이는 필수입니다.");
                if (outerLengthInMillimeters < 1) {
                    throw new IllegalArgumentException("외부 길이는 1mm 이상이어야 합니다.");
                }
                Assert.notNull(weightInGrams, "무게는 필수입니다.");
                if (weightInGrams < 1) {
                    throw new IllegalArgumentException("무게는 1g 이상이어야 합니다.");
                }
                Assert.notNull(maxWeightInGrams, "최대 무게는 필수입니다.");
                if (maxWeightInGrams < 1) {
                    throw new IllegalArgumentException("최대 무게는 1g 이상이어야 합니다.");
                }
                Assert.notNull(materialType, "포장재 종류는 필수입니다.");
            }
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
