package com.shyoon.wms.inbound.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LPNRepository extends JpaRepository<LPN, Long> {
    @Query("""
                    SELECT l FROM LPN l
                    WHERE l.lpnBarcode = :lpnBarcode
            """)
    Optional<LPN> findByLPNBarcode(String lpnBarcode);

    default LPN getByLPNBarcode(String lpnBarcode) {
        return findByLPNBarcode(lpnBarcode)
                .orElseThrow(() -> new IllegalArgumentException("해당 LPN을 찾을 수 없습니다. %s".formatted(lpnBarcode)));
    }
}
