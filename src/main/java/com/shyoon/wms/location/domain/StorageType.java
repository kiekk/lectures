package com.shyoon.wms.location.domain;

public enum StorageType {
    TOTE("토트바구니"),
    PALLET("파레트");

    private final String description;

    StorageType(String description) {
        this.description = description;
    }
}
