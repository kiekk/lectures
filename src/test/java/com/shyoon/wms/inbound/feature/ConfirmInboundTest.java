package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfirmInboundTest {

    private ConfirmInbound confimInbound;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        confimInbound = new ConfirmInbound();
    }

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() {
        // given
        Long inboundNo = 1L;

        // when
        confimInbound.request(inboundNo);

        // then
        // TODO inbound 상태가 승인으로 변경되었는지 확인한다.
//        assertThat(inboundRepository.findById(inboundNo).get().getStatus()).isEqualTo();
    }

    private class ConfirmInbound {
        public void request(final Long inboundNo) {
            final Inbound inbound = inboundRepository.findById(inboundNo).orElseThrow();

            inbound.confirmed();
        }
    }
}
