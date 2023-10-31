package com.shyoon.wms.product.feature.api;

import com.shyoon.wms.product.domain.Category;
import com.shyoon.wms.product.domain.TemperatureZone;
import com.shyoon.wms.product.feature.RegisterProduct;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RegisterProductApi {
    private String name = "name";
    private String code = "code";
    private String description = "description";
    private String brand = "brand";
    private String maker = "maker";
    private String origin = "origin";
    private Long weightInGrams = 1000L;
    private Long widthInMillimeters = 100L;
    private Long heightInMillimeters = 100L;
    private Long lengthInMillimeters = 100L;

    public RegisterProductApi name(String name) {
        this.name = name;
        return this;
    }

    public RegisterProductApi code(String code) {
        this.code = code;
        return this;
    }

    public RegisterProductApi description(String description) {
        this.description = description;
        return this;
    }

    public RegisterProductApi brand(String brand) {
        this.brand = brand;
        return this;
    }

    public RegisterProductApi maker(String maker) {
        this.maker = maker;
        return this;
    }

    public RegisterProductApi origin(String origin) {
        this.origin = origin;
        return this;
    }

    public RegisterProductApi weightInGrams(Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public RegisterProductApi widthInMillimeters(Long widthInMillimeters) {
        this.widthInMillimeters = widthInMillimeters;
        return this;
    }

    public RegisterProductApi heightInMillimeters(Long heightInMillimeters) {
        this.heightInMillimeters = heightInMillimeters;
        return this;
    }

    public RegisterProductApi lengthInMillimeters(Long lengthInMillimeters) {
        this.lengthInMillimeters = lengthInMillimeters;
        return this;
    }

    public void request() {
        RegisterProduct.Request request = new RegisterProduct.Request(
                name,
                code,
                description,
                brand,
                maker,
                origin,
                Category.ELECTRONICS,
                TemperatureZone.ROOM_TEMPERATURE,
                weightInGrams, // gram
                widthInMillimeters, // 너비 mm
                heightInMillimeters, // 높이 mm
                lengthInMillimeters // 길이 mm
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
