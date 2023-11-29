package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.outbound.domain.OrderRepository;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import com.shyoon.wms.product.domain.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterOutboundTest extends ApiTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboundRepository outboundRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutboundTest() {
        // given
        Scenario.registerProduct().request();

        final Long orderNo = 1L;
        final Boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
        final RegisterOutbound.Request request = new RegisterOutbound.Request(
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
