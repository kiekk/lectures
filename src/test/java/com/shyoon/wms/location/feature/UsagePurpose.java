package com.shyoon.wms.location.feature;

enum UsagePurpose {
    MOVE("이동 목적");

    private final String description;

    UsagePurpose(String description) {
        this.description = description;
    }
}
