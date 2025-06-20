package com.shyoon.wms.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("""
                SELECT l FROM Location l
                WHERE l.locationBarcode = :locationBarcode
            """)
    Optional<Location> findByLocationBarcode(String locationBarcode);

    default Location getByLocationBarcode(String locationBarcode) {
        return findByLocationBarcode(locationBarcode)
                .orElseThrow(() -> new IllegalArgumentException("해당 로케이션을 찾을 수 없습니다. %s".formatted(locationBarcode)));
    }
}
