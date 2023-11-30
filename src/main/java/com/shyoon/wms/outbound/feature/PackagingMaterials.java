package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.outbound.domain.PackagingMaterial;

import java.util.Comparator;
import java.util.List;

public record PackagingMaterials(List<PackagingMaterial> packagingMaterials) {
    PackagingMaterial getOptimalPackagingMaterial(final Long totalWeight,
                                                  final Long totalVolume) {
        return packagingMaterials().stream()
                .filter(pm -> pm.isAvailable(totalWeight, totalVolume))
                .min(Comparator.comparingLong(PackagingMaterial::outerVolume))
                .orElse(null);
    }
}
