package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.outbound.feature.PackagingMaterials;

public class OutboundSplitter {

    private final OutboundProductQuantityManager outboundProductQuantityManager = new OutboundProductQuantityManager();

    public Outbound splitOutbound(
            final Outbound target,
            final OutboundProducts splitOutboundProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = target.split(splitOutboundProducts);
        outboundProductQuantityManager.decreaseQuantity(splitOutboundProducts, target.getOutboundProducts());
        target.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, target));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
        return splitted;
    }

    private PackagingMaterial getOptimalPackagingMaterial(
            final PackagingMaterials packagingMaterials,
            final Outbound splitted) {
        return packagingMaterials.getOptimalPackagingMaterial(
                splitted.getOutboundProducts().totalWeight(),
                splitted.getOutboundProducts().totalVolume()
        );
    }

}
