package com.shyoon.wms.common;

import com.shyoon.wms.inbound.feature.api.ConfirmInboundApi;
import com.shyoon.wms.inbound.feature.api.RegisterInboundApi;
import com.shyoon.wms.inbound.feature.api.RegisterLPNApi;
import com.shyoon.wms.inbound.feature.api.RejectInboundApi;
import com.shyoon.wms.location.feature.api.AssignInventoryApi;
import com.shyoon.wms.location.feature.api.RegisterLocationApi;
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

    public static RejectInboundApi rejectInbound() {
        return new RejectInboundApi();
    }

    public static RegisterLPNApi registerLPN() {
        return new RegisterLPNApi();
    }

    public static RegisterLocationApi registerLocation() {
        return new RegisterLocationApi();
    }

    public static AssignInventoryApi assignInventory() {
        return new AssignInventoryApi();
    }
}
