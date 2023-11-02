package com.shyoon.wms.common;

import com.shyoon.wms.inbound.domain.ConfirmInboundApi;
import com.shyoon.wms.inbound.feature.api.RegisterInboundApi;
import com.shyoon.wms.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public static RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public static ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }
}
