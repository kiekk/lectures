package com.shyoon.wms.inbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InboundTest {

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmed() {
        // given
        final Inbound inbound = InboundFixture.anInbound().build();
        final InboundStatus beforeStatus = inbound.getStatus();

        // when
        inbound.confirmed();

        // then
        assertThat(beforeStatus).isEqualTo(InboundStatus.REQUESTED);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

    @Test
    @DisplayName("입고 승인을 실패한다. 입고의 상태가 요청이 아닌 경우 예외가 발생한다.")
    void fail_invalid_status_confirmed() {
        // given
        final Inbound confirmedInbound = InboundFixture.anInboundWithConfirmed().build();

        // when & then
        assertThatThrownBy(confirmedInbound::confirmed)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("입고 요청 상태가 아닙니다.");
    }

}
