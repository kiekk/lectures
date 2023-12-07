package com.shyoon.wms.outbound.feature.api;

import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.outbound.feature.SplitOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SplitOutboundApi {

    private Long outboundNo = 1L;
    private List<SplitOutbound.Request.Product> products = List.of(new SplitOutbound.Request.Product(
            1L,
            1L
    ));

    public static SplitOutboundApi aSplitOutbound() {
        return new SplitOutboundApi();
    }

    public SplitOutboundApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public SplitOutboundApi products(final List<SplitOutbound.Request.Product> products) {
        this.products = products;
        return this;
    }

    public Scenario request() {
        final SplitOutbound.Request request = new SplitOutbound.Request(
                outboundNo,
                products
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/split")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
