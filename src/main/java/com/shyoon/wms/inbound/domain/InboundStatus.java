package com.shyoon.wms.inbound.domain;

public enum InboundStatus {
    REQUESTED("요청됨"),
    CONFIRMED("승인됨"),
    REJECTED("취소됨");

    private final String description;

    InboundStatus(final String description) {
        this.description = description;
    }
}
