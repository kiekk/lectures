package com.shyoon.wms.product.domain;

public enum Category {
    ELECTRONICS("전자 제품");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
