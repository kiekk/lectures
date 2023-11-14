package com.shyoon.wms.location.feature.api;

import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.location.feature.AssignLocationLPN;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AssignLocationLPNApi {
    private String locationBarcode = "A-1-1";
    private String lpnBarcode = "LPN-0001";

    public AssignLocationLPNApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public AssignLocationLPNApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public Scenario request() {
        final AssignLocationLPN.Request request = new AssignLocationLPN.Request(
                locationBarcode,
                lpnBarcode
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/assign-lpn")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
