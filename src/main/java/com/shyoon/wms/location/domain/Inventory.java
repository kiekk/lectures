package com.shyoon.wms.location.domain;

import com.shyoon.wms.inbound.domain.LPN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

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

    public Inventory(
            final Location location,
            final LPN lpn) {
        Assert.notNull(location, "로케이션은 필수입니다.");
        Assert.notNull(lpn, "LPN은 필수입니다.");
        this.location = location;
        this.lpn = lpn;
        this.inventoryQuantity = 1L;
    }

    void increaseQuantity() {
        inventoryQuantity++;
    }

    boolean matchLPNToLocation(LPN lpn) {
        return this.lpn.equals(lpn);
    }
}
