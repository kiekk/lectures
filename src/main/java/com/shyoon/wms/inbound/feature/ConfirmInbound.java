package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;

class ConfirmInbound {

    private final InboundRepository inboundRepository;

    ConfirmInbound(InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    public void request(final Long inboundNo) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.confirmed();
    }
}
