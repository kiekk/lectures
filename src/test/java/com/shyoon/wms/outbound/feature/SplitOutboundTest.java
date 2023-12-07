package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.inbound.feature.RegisterInbound;
import com.shyoon.wms.outbound.domain.*;
import com.shyoon.wms.product.domain.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SplitOutboundTest extends ApiTest {

    @Autowired
    private SplitOutbound splitOutbound;

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void setUpSplitOutbound() {
        Scenario
                .registerProduct().code("code").request()
                .registerProduct().code("code1").request()
                .registerInbound()
                .inboundItems(
                        new RegisterInbound.Request.Item(
                                1L,
                                1L,
                                1500L,
                                "description"
                        ),
                        new RegisterInbound.Request.Item(
                                2L,
                                1L,
                                1500L,
                                "description"
                        )
                )
                .request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLPN().inboundItemNo(2L).lpnBarcode("A-1-2").request()
                .registerLocation().request()
                .registerPackagingMaterial().request()
                .assignInventory().request()
                .assignInventory().lpnBarcode("A-1-2").request()
                .registerOutbound().request();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
        final Outbound target = outboundRepository.getBy(1L);
        assertThat(target.getOutboundProducts()).hasSize(2);


        final Long outboundNo = 1L;
        final Long productNo = 1L;
        final Long quantity = 1L;
        final SplitOutbound.Request.Product product = new SplitOutbound.Request.Product(
                productNo,
                quantity
        );
        final List<SplitOutbound.Request.Product> products = List.of(product);
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

        validateSplit(outboundNo);
    }

    private void validateSplit(Long outboundNo) {
        final Outbound refresh = outboundRepository.getBy(outboundNo);
        assertThat(refresh.getOutboundProducts()).hasSize(1);
        assertThat(refresh.getOutboundProducts().get(0).getProductNo()).isEqualTo(2L);
        assertThat(refresh.getRecommendedPackagingMaterial()).isNotNull();

        final Outbound splitted = outboundRepository.getBy(2L);
        assertThat(splitted.getOutboundProducts().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splitted.getOutboundProducts()).hasSize(1);
        assertThat(splitted.getRecommendedPackagingMaterial()).isNotNull();
    }

    @TestConfiguration
    static class SplitOutboundTestConfiguration {

        @Bean
        @Primary
        public OrderRepository orderRepository(final ProductRepository productRepository) {
            return orderNo -> new Order(
                    orderNo,
                    new OrderCustomer(
                            "name",
                            "email",
                            "phone",
                            "zipNo",
                            "address"
                    ),
                    "배송 요구사항",
                    List.of(
                            new OrderProduct(
                                    productRepository.getBy(1L),
                                    1L,
                                    1500L
                            ),
                            new OrderProduct(
                                    productRepository.getBy(2L),
                                    1L,
                                    1500L
                            )
                    )
            );
        }
    }

}
