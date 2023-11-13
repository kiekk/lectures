package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;
import com.shyoon.wms.inbound.domain.InboundStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class RejectInboundTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고를 반려/거부한다.")
    void rejectInbound() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .rejectInbound().request();

        final Inbound inbound = inboundRepository.getBy(1L);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

}
