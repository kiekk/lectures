package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class RegisterLPN {

    private final InboundRepository inboundRepository;

    @Transactional
    public void request(final Request request) {
        final Inbound inbound = inboundRepository.getByInboundItemNo(request.inboundItemNo);

        inbound.registerLPN(request.inboundItemNo, request.lpnBarcode, request.expirationAt);
    }

    public static class Request {

        private final Long inboundItemNo;
        private final String lpnBarcode;
        private final LocalDateTime expirationAt;

        public Request(
                final Long inboundItemNo,
                final String lpnBarcode,
                final LocalDateTime expirationAt) {
            Assert.notNull(inboundItemNo, "LPN을 등록할 입고 상품 번호는 필수입니다.");
            Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
            Assert.notNull(inboundItemNo, "유통 기한은 필수입니다.");
            this.inboundItemNo = inboundItemNo;
            this.lpnBarcode = lpnBarcode;
            this.expirationAt = expirationAt;
        }
    }
}
