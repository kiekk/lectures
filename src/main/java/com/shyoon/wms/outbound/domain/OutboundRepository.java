package com.shyoon.wms.outbound.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundRepository extends JpaRepository<Outbound, Long> {
    default Outbound getBy(Long outboundNo) {
        return findById(outboundNo)
                .orElseThrow(() -> new IllegalArgumentException("출고가 존재하지 않습니다. 출고번호: %d".formatted(outboundNo)));
    }
}
