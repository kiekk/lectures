package com.shyoon.wms.outbound.domain;

import com.shyoon.wms.outbound.feature.PackagingMaterials;
import com.shyoon.wms.product.domain.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OutboundSplitterTest {

    private OutboundSplitter outboundSplitter;

    @BeforeEach
    void setUp() {
        outboundSplitter = new OutboundSplitter();
    }


    @Test
    void success_split1() {
        final Outbound target = createSuccessOutbound();

        assertThat(target.getOutboundProducts().toList()).hasSize(2);

        final Outbound splittedOutbound = outboundSplitter.splitOutbound(
                target,
                OutboundProductsFixture.anOutboundProducts().build(),
                PackagingMaterialsFixture.aPackagingMaterials().build());

        assertSplit(target, splittedOutbound);
    }

    private Outbound createSuccessOutbound() {
        return OutboundFixture.anOutbound()
                .outboundProducts(
                        OutboundProductFixture.anOutboundProduct().product(ProductFixture.aProduct().productNo(1L)),
                        OutboundProductFixture.anOutboundProduct().product(ProductFixture.aProduct().productNo(2L))
                )
                .build();
    }

    private void assertSplit(Outbound target, Outbound splittedOutbound) {
        assertThat(target.getOutboundProducts().toList()).hasSize(1);
        assertThat(target.getOutboundProducts().toList().get(0).getProductNo()).isEqualTo(2L);
        assertThat(target.getRecommendedPackagingMaterial()).isNotNull();
        assertThat(splittedOutbound.getOutboundProducts().toList()).hasSize(1);
        assertThat(splittedOutbound.getOutboundProducts().toList().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splittedOutbound.getRecommendedPackagingMaterial()).isNotNull();
    }

    @Test
    void fail_split2() {
        final Outbound target = OutboundFixture.anOutbound().build();

        assertThatThrownBy(() -> {
            final Outbound splittedOutbound = outboundSplitter.splitOutbound(
                    target,
                    OutboundProductsFixture.anOutboundProducts()
                            .outboundProducts(
                                    OutboundProductFixture.anOutboundProduct().orderQuantity(2L)
                            ).build(),
                    PackagingMaterialsFixture.aPackagingMaterials().build());
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("분할할 수량이 출고 수량보다 같거나 많습니다.");

    }

    @Test
    void fail_split3() {
        final Outbound target = createSuccessOutbound();

        assertThatThrownBy(() -> {
            final Outbound splittedOutbound = outboundSplitter.splitOutbound(
                    target,
                    OutboundProductsFixture.anOutboundProducts().build(),
                    PackagingMaterialsFixture.aPackagingMaterials()
                            .packagingMaterials(
                                    PackagingMaterialFixture.aPackagingMaterial().maxWeightInGrams(1L)
                            )
                            .build());
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적합한 포장재가 없습니다.");

    }

}
