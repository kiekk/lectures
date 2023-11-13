package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;

class RejectInbound {
    private final InboundRepository inboundRepository;

    RejectInbound(InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    public void request(Long inboundNo, Request request) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.reject(request.rejectionReason);
    }

    public static class Request {

        private final String rejectionReason;

        public Request(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }
    }
}
