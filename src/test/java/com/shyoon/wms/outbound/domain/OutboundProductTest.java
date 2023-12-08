package com.shyoon.wms.outbound.domain;

import org.junit.jupiter.api.Test;

import static com.shyoon.wms.outbound.domain.OutboundProductFixture.*;
import static org.junit.jupiter.api.Assertions.*;

class OutboundProductTest {

    @Test
    void allocatePicking() {
        final OutboundProduct outboundProduct = anOutboundProduct().build();
    }
}
