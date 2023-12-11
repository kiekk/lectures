package com.shyoon.wms.outbound.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class OutboundProducts {

    @OneToMany(mappedBy = "outbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OutboundProduct> outboundProducts;

    public OutboundProducts(final List<OutboundProduct> outboundProducts) {
        this.outboundProducts = outboundProducts;
    }

    Long splitTotalQuantity() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }

    public List<OutboundProduct> toList() {
        return outboundProducts;
    }

    OutboundProduct getOutboundProductBy(final Long productNo) {
        return outboundProducts.stream()
                .filter(outboundProduct -> outboundProduct.isSameProductNo(productNo))
                .findFirst()
                .orElseThrow();
    }

    Long calculateTotalOrderQuantity() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }

    public Long totalWeight() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::calculateOutboundProductWeight)
                .sum();
    }

    public Long totalVolume() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::calculateOutboundProductVolume)
                .sum();
    }

    void removeIf() {
        outboundProducts.removeIf(OutboundProduct::isZeroQuantity);
    }

    public OutboundProduct splitOutboundProducts(final Long productNo,
                                                 final Long quantity) {
        final OutboundProduct outboundProduct = getOutboundProductBy(productNo);

        return outboundProduct.split(quantity);
    }

    public List<Picking> getPickings() {
        return outboundProducts.stream()
                .flatMap(outboundProduct -> outboundProduct.getPickings().stream())
                .toList();
    }
}
