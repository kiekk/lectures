package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.LocationRepository;
import com.shyoon.wms.outbound.domain.Outbound;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
class AllocatePickingTote {

    private final OutboundRepository outboundRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void request(final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.outboundNo);
        final Location tote = locationRepository.getByLocationBarcode(request.toteBarcode);
        outbound.allocatePickingTote(tote);
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
