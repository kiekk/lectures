package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;
import com.shyoon.wms.inbound.domain.InboundStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmInboundTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() {
        // given
        Scenario.registerProduct().request()
                .registerInbound().request();

        final Long inboundNo = 1L;

        // when
        RestAssured.given().log().all()
                .when()
                .post("/inbounds/{inboundNo}/confirm", inboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        // then
        final Inbound inbound = inboundRepository.getBy(inboundNo);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

}
