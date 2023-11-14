package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.LPN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@Table(name = "location")
@Comment("로케이션")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_no", nullable = false)
    @Comment("로케이션 번호")
    private Long locationNo;

    @Column(name = "location_barcode", nullable = false)
    @Comment("로케이션 바코드")
    private String locationBarcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    @Comment("보관 타입")
    private StorageType storageType;

    @Enumerated(EnumType.STRING)
    @Column(name = "usage_purpose", nullable = false)
    @Comment("보관 목적")
    private UsagePurpose usagePurpose;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationLPN> locationLPNList = new ArrayList<>();

    public Location(
            String locationBarcode,
            StorageType storageType,
            UsagePurpose usagePurpose) {
        validateConstructor(locationBarcode, storageType, usagePurpose);

        this.locationBarcode = locationBarcode;
        this.storageType = storageType;
        this.usagePurpose = usagePurpose;
    }

    private void validateConstructor(String locationBarcode, StorageType storageType, UsagePurpose usagePurpose) {
        Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
        Assert.notNull(storageType, "보관 타입은 필수입니다.");
        Assert.notNull(usagePurpose, "보관 목적은 필수입니다.");
    }

    public void assignNo(final Long locationNo) {
        this.locationNo = locationNo;
    }

    public void assignLPN(final LPN lpn) {
        Assert.notNull(lpn, "LPN은 필수입니다.");

        // 1. 로케이션 LPN 목록에 등록하려는 LPN이 없으면 새로 등록한다.
        //      새로 등록한 로케이션 LPN은 재고가 1이다.
        // 2. 로케이션 LPN 목록에 등록하려는 LPN이 존재하면 재고를 1 증가시킨다.
        findLocationLPNBy(lpn)
                .ifPresentOrElse(
                        LocationLPN::increaseQuantity,
                        () -> locationLPNList.add(new LocationLPN(this, lpn))
                );
    }

    private Optional<LocationLPN> findLocationLPNBy(LPN lpn) {
        return locationLPNList.stream()
                .filter(locationLPN -> locationLPN.matchLPNToLocation(lpn))
                .findFirst();
    }

}
