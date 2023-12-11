package com.shyoon.wms.location.domain;

import com.google.common.annotations.VisibleForTesting;
import com.shyoon.wms.inbound.domain.LPN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("재고")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_no")
    @Comment("재고 번호")
    private Long inventoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_no", nullable = false)
    @Comment("로케이션 번호")
    private Location location;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lpn_no", nullable = false)
    @Comment("LPN 번호")
    private LPN lpn;

    @Getter
    @Column(name = "inventory_quantity", nullable = false)
    @Comment("재고")
    private Long inventoryQuantity;

    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    @Getter
    private Long productNo;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public Inventory(
            final Location location,
            final LPN lpn) {
        Assert.notNull(location, "로케이션은 필수입니다.");
        Assert.notNull(lpn, "LPN은 필수입니다.");
        this.location = location;
        this.lpn = lpn;
        this.inventoryQuantity = 1L;
        this.productNo = lpn.getProductNo();
    }

    @VisibleForTesting
    Inventory(final Location location,
              final LPN lpn,
              final Long inventoryQuantity) {
        this(location, lpn);
        this.inventoryQuantity = inventoryQuantity;
    }

    void increaseQuantity() {
        inventoryQuantity++;
    }

    boolean matchLPNToLocation(LPN lpn) {
        return this.lpn.equals(lpn);
    }

    public boolean isFresh() {
        return lpn.isFresh();
    }

    public boolean hasInventory() {
        return 0L < getInventoryQuantity();
    }

    public boolean hasAvailableQuantity() {
        return inventoryQuantity > 0L;
    }

    public LocalDateTime getExpirationAt() {
        return lpn.getExpirationAt();
    }

    public String getLocationBarcode() {
        return location.getLocationBarcode();
    }

    public void decreaseInventory(final Long quantity) {
        if (inventoryQuantity < quantity) {
            throw new IllegalArgumentException(
                    "차감하려는 재고 수량이 충분하지 않습니다. 재고 수량:%d, 차감 수량:%d"
                            .formatted(inventoryQuantity, quantity));
        }
        inventoryQuantity -= quantity;
    }

}
