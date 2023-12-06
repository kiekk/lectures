package com.shyoon.wms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

class SplitOutboundTest {

    private SplitOutbound splitOutbound;

    @BeforeEach
    void setUp() {
        splitOutbound = new SplitOutbound();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
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
        splitOutbound.request(request);
    }

    private class SplitOutbound {
        public void request(final Request request) {
        }

        public record Request(
                Long outboundNo,
                List<Product> products) {

            public Request {
                Assert.notNull(outboundNo, "출고번호는 필수입니다.");
                Assert.notEmpty(products, "상품은 필수입니다.");
            }

            public record Product(
                    Long productNo,
                    Long quantity) {
                public Product {
                    Assert.notNull(productNo, "상품번호는 필수입니다.");
                    Assert.notNull(quantity, "수량은 필수입니다.");
                    if (quantity < 1) {
                        throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
                    }
                }
            }
        }
    }
}
