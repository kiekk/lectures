package com.shyoon.wms.product.domain;

import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "product")
@Comment("상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "product_no")
    @Comment("상품 ID")
    private Long productNo;

    @Column(name = "name", nullable = false)
    @Comment("상품명")
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    @Comment("상품코드")
    private String code;

    @Column(name = "description", nullable = false)
    @Comment("상품설명")
    private String description;

    @Column(name = "brand", nullable = false)
    @Comment("브랜드")
    private String brand;

    @Column(name = "maker", nullable = false)
    @Comment("제조사")
    private String maker;

    @Column(name = "origin", nullable = false)
    @Comment("원산지")
    private String origin;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    @Comment("카테고리")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "temperature_zone", nullable = false)
    @Comment("온도대")
    private TemperatureZone temperatureZone;

    @Column(name = "weight_in_grams", nullable = false)
    @Comment("무게(그램)")
    private Long weightInGrams;

    @Embedded
    private ProductSize productSize;

    public Product(
            final String name,
            final String code,
            final String description,
            final String brand,
            final String maker,
            final String origin,
            final Category category,
            final TemperatureZone temperatureZone,
            final Long weightInGrams,
            final ProductSize productSize) {
        validateConstructor(
                name,
                code,
                description,
                brand,
                maker,
                origin,
                category,
                temperatureZone,
                weightInGrams,
                productSize);

        this.name = name;
        this.code = code;
        this.description = description;
        this.brand = brand;
        this.maker = maker;
        this.origin = origin;
        this.category = category;
        this.temperatureZone = temperatureZone;
        this.weightInGrams = weightInGrams;
        this.productSize = productSize;
    }

    @VisibleForTesting
    Product(
            final Long productNo,
            final String name,
            final String code,
            final String description,
            final String brand,
            final String maker,
            final String origin,
            final Category category,
            final TemperatureZone temperatureZone,
            final Long weightInGrams,
            final ProductSize productSize) {
        this(name, code, description, brand, maker, origin, category, temperatureZone, weightInGrams, productSize);
        this.productNo = productNo;
    }

    private void validateConstructor(
            final String name,
            final String code,
            final String description,
            final String brand,
            final String maker,
            final String origin,
            final Category category,
            final TemperatureZone temperatureZone,
            final Long weightInGrams,
            final ProductSize productSize) {
        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.hasText(code, "상품코드는 필수입니다.");
        Assert.hasText(description, "상품설명은 필수입니다.");
        Assert.hasText(brand, "브랜드는 필수입니다.");
        Assert.hasText(maker, "제조사는 필수입니다.");
        Assert.hasText(origin, "원산지는 필수입니다.");
        Assert.notNull(category, "카테고리는 필수입니다.");
        Assert.notNull(temperatureZone, "온도대는 필수입니다.");
        Assert.notNull(weightInGrams, "무게는 필수입니다.");
        Assert.notNull(productSize, "상품크기는 필수입니다.");
    }

    public void assignProductNo(Long productNo) {
        this.productNo = productNo;
    }

    public Long getVolume() {
        return productSize.getVolume();
    }
}
