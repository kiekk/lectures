package com.shyoon.wms.common;

import com.shyoon.wms.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }
}
