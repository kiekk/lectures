package com.shyoon.wms.product.domain;

import org.springframework.util.Assert;

public class ProductSize {
    private final Long widthInMillimeters;
    private final Long heightInMillimeters;
    private final Long lengthInMillimeters;

    public ProductSize(
            Long widthInMillimeters,
            Long heightInMillimeters,
            Long lengthInMillimeters) {
        validateProductSize(
                widthInMillimeters,
                heightInMillimeters,
                lengthInMillimeters);

        this.widthInMillimeters = widthInMillimeters;
        this.heightInMillimeters = heightInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
    }

    private void validateProductSize(
            Long widthInMillimeters,
            Long heightInMillimeters,
            Long lengthInMillimeters) {
        Assert.notNull(widthInMillimeters, "너비는 필수입니다.");
        if (0 > widthInMillimeters) {
            throw new IllegalArgumentException("너비는 0보다 작을 수 없습니다.");
        }

        Assert.notNull(heightInMillimeters, "세로길이는 필수입니다.");
        if (0 > heightInMillimeters) {
            throw new IllegalArgumentException("세로길이는 0보다 작을 수 없습니다.");
        }

        Assert.notNull(lengthInMillimeters, "세로길이는 필수입니다.");
        if (0 > lengthInMillimeters) {
            throw new IllegalArgumentException("세로길이는 0보다 작을 수 없습니다.");
        }
    }
}
