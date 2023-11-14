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
    }

    private class RegistetPackagingMaterial {
    }
}
