package com.shyoon.wms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class AllocatePickingToteTest {

    private AllocatePickingTote allocatePickingTote;

    @BeforeEach
    void setUp() {
        allocatePickingTote = new AllocatePickingTote();
    }

    @Test
    @DisplayName("출고 상품을 집품할 토트에 할당한다.")
    void allocatePickingTote() {
        final Long outboundNo = 1L;
        final String toteBarcode = "A-1-1";
        final AllocatePickingTote.Request request = new AllocatePickingTote.Request(
                outboundNo,
                toteBarcode
        );
        allocatePickingTote.request(request);
    }

    private class AllocatePickingTote {
        public void request(final Request request) {

        }

        public record Request(
                Long outboundNo,
                String toteBarcode) {
            public Request {
                Assert.notNull(outboundNo, "출고 번호는 필수입니다.");
                Assert.hasText(toteBarcode, "토트 바코드는 필수입니다.");
            }
        }
    }
}
