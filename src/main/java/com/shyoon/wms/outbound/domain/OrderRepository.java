package com.shyoon.wms.outbound.domain;

public interface OrderRepository {
    Order getBy(Long orderNo);
}
