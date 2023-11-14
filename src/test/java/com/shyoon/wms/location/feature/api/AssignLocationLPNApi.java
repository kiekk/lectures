package com.shyoon.wms.location.feature.api;

import com.shyoon.wms.location.feature.AssignLocationLPN;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AssignLocationLPNApi {
    public static String getString() {
        final String locationBarcode = "A-1-1";
        final String lpnBarcode = "LPN-0001";
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
        return locationBarcode;
    }
}
