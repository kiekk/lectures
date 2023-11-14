package com.shyoon.wms.inbound.domain;

import java.time.LocalDateTime;

public class LPNFixture {

    private String lpnBarcode = "LPN-1";
    private LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);
    private InboundItemFixture inboundItemFixture = InboundItemFixture.anInboundItem();

    public static LPNFixture anLPN() {
        return new LPNFixture();
    }

    public LPNFixture lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public LPNFixture expirationAt(final LocalDateTime expirationAt) {
        this.expirationAt = expirationAt;
        return this;
    }

    public LPNFixture inboundItem(final InboundItemFixture inboundItemFixture) {
        this.inboundItemFixture = inboundItemFixture;
        return this;
    }

    public LPN build() {
        return new LPN(
                lpnBarcode,
                expirationAt,
                inboundItemFixture.build()
        );
    }
}
