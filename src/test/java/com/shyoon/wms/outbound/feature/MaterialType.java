package com.shyoon.wms.outbound.feature;

enum MaterialType {
    CORRUGATED_BOX("완충재가 있는 골판지 상자");

    private final String description;

    MaterialType(String description) {
        this.description = description;
    }
}
