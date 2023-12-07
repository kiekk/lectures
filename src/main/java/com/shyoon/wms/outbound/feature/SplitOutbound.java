package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.outbound.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
@RequiredArgsConstructor
class SplitOutbound {

    private final OutboundSplitter outboundSplitter = new OutboundSplitter();
    private final OutboundRepository outboundRepository;
    private final PackagingMaterialRepository packagingMaterialRepository;

    public void request(final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.outboundNo);
        final OutboundProducts splitOutboundProducts = splitOutboundProducts(outbound, request.products);
        final PackagingMaterials packagingMaterials = new PackagingMaterials(packagingMaterialRepository.findAll());

        final Outbound splitted = outboundSplitter.splitOutbound(outbound, splitOutboundProducts, packagingMaterials);

        // 분할된 출고를 저장
        outboundRepository.save(splitted);
    }

    private OutboundProducts splitOutboundProducts(
            final Outbound outbound,
            final List<Request.Product> products) {
        return new OutboundProducts(
                products.stream()
                        .map(product -> outbound.splitOutboundProducts(product.productNo, product.quantity))
                        .toList()
        );
    }

    public record Request(
            Long outboundNo,
            List<Request.Product> products) {

        public Request {
            Assert.notNull(outboundNo, "출고번호는 필수입니다.");
            Assert.notEmpty(products, "상품은 필수입니다.");
        }

        public record Product(
                Long productNo,
                Long quantity) {
            public Product {
                Assert.notNull(productNo, "상품번호는 필수입니다.");
                Assert.notNull(quantity, "수량은 필수입니다.");
                if (quantity < 1) {
                    throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
                }
            }
        }
    }
}
