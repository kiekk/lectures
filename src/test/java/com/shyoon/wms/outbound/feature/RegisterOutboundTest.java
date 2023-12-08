package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.common.ApiTest;
import com.shyoon.wms.common.Scenario;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterOutboundTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void setUpRegisterOutbound() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request()
                .registerPackagingMaterial().request()
                .assignInventory().request();
    }

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutboundTest() {
        Scenario
                .registerOutbound().request();
        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
