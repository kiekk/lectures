package com.shyoon.wms.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("""
                SELECT l FROM Location l
                WHERE l.locationBarcode = :locationBarCode
            """)
    Optional<Location> findByLocationBarcode(String locationBarcode);
}
