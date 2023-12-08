package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AllocatePickingToteTest extends ApiTest {

    @Autowired
    private AllocatePickingTote allocatePickingTote;

    @BeforeEach
    void setUpAllocatePickingTote() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request()
                .registerLocation().locationBarcode("TOTE-1").request()
                .registerPackagingMaterial().request()
                .assignInventory().request()
                .registerOutbound().request();
    }

    @Test
    @DisplayName("출고 상품을 집품할 토트에 할당한다.")
    void allocatePickingTote() {
        final Long outboundNo = 1L;
        final String toteBarcode = "TOTE-1";
        final AllocatePickingTote.Request request = new AllocatePickingTote.Request(
                outboundNo,
                toteBarcode
        );
        allocatePickingTote.request(request);

        // 실제로 outbound 에 토트 바구니가 할당되었는지 확인
    }

}
