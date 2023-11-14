package com.shyoon.wms.outbound.domain;

import org.springframework.util.Assert;

public class PackagingMaterial {
    private Long packagingMaterialNo;
    private final String name;
    private final String code;
    private final PackagingMaterialDemension packagingMaterialDemension;
    private final Long weightInGrams;
    private final Long maxWeightInGrams;
    private final MaterialType materialType;

    public PackagingMaterial(
            final String name,
            final String code,
            final PackagingMaterialDemension packagingMaterialDemension,
            final Long weightInGrams,
            final Long maxWeightInGrams,
            final MaterialType materialType) {
        validateConstructor(name, code, packagingMaterialDemension, weightInGrams, maxWeightInGrams, materialType);

        this.name = name;
        this.code = code;
        this.packagingMaterialDemension = packagingMaterialDemension;
        this.weightInGrams = weightInGrams;
        this.maxWeightInGrams = maxWeightInGrams;
        this.materialType = materialType;
    }

    private void validateConstructor(
            final String name,
            final String code,
            final PackagingMaterialDemension packagingMaterialDemension,
            final Long weightInGrams,
            final Long maxWeightInGrams,
            final MaterialType materialType) {
        Assert.hasText(name, "포장재 이름은 필수입니다.");
        Assert.hasText(code, "포장재 코드는 필수입니다.");
        Assert.notNull(packagingMaterialDemension, "포장재 치수는 필수입니다.");
        Assert.notNull(weightInGrams, "무게는 필수입니다.");
        Assert.notNull(maxWeightInGrams, "최대 무게는 필수입니다.");
        Assert.notNull(materialType, "포장재 종류는 필수입니다.");
    }

    public void assignNo(Long packagingMaterialNo) {
        this.packagingMaterialNo = packagingMaterialNo;
    }

    public Long getPackagingMaterialNo() {
        return packagingMaterialNo;
    }
}
