package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.outbound.feature.PackagingMaterials;

public class OutboundSplitter {

    public Outbound splitOutbound(
            final Outbound outbound,
            final OutboundProducts splitOutboundProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = outbound.split(splitOutboundProducts);
        outbound.decreaseQuantity(splitOutboundProducts);
        outbound.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, outbound));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
        return splitted;
    }

    private PackagingMaterial getOptimalPackagingMaterial(
            final PackagingMaterials packagingMaterials,
            final Outbound splitted) {
        return packagingMaterials.getOptimalPackagingMaterial(
                splitted.totalWeight(),
                splitted.totalVolume()
        );
    }

}
