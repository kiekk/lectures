package com.shyoon.wms.inbound.feature;


import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.inbound.domain.InboundRepository;
import com.shyoon.wms.product.domain.ProductRepository;
import com.shyoon.wms.product.fixture.ProductFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterInboundTest extends ApiTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고를 등록한다.")
    void registerInbound() {
        // given
        Mockito.when(productRepository.getBy(Mockito.anyLong()))
                .thenReturn(ProductFixture.aProduct().build());

        final LocalDateTime orderRequestedAt = LocalDateTime.now();
        final LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        final Long productNo = 1L;
        final Long quantity = 1L;
        final Long unitPrice = 1500L;
        final RegisterInbound.Request.Item inboundItem = new RegisterInbound.Request.Item(
                productNo,
                quantity,
                unitPrice,
                "description"
        );
        List<RegisterInbound.Request.Item> inboundItems = List.of(inboundItem);
        RegisterInbound.Request request = new RegisterInbound.Request(
                "title",
                "description",
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems
        );

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        assertThat(inboundRepository.findAll()).hasSize(1);
    }

}
