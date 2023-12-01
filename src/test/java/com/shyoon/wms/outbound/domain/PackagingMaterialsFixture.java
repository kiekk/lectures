package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.outbound.feature.PackagingMaterials;

import java.util.List;

public class PackagingMaterialsFixture {

    private List<PackagingMaterialFixture> packagingMaterials = List.of(PackagingMaterialFixture.aPackagingMaterial());

    public static PackagingMaterialsFixture aPackagingMaterials() {
        return new PackagingMaterialsFixture();
    }

    public PackagingMaterialsFixture packagingMaterials(final PackagingMaterialFixture... packagingMaterials) {
        this.packagingMaterials = List.of(packagingMaterials);
        return this;
    }

    public PackagingMaterials build() {
        return new PackagingMaterials(buildPackagingMaterials());
    }

    private List<PackagingMaterial> buildPackagingMaterials() {
        return packagingMaterials.stream()
                .map(PackagingMaterialFixture::build)
                .toList();
    }
}
