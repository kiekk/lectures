package com.shyoon.wms.inbound.feature;

import com.shyoon.wms.inbound.domain.Inbound;
import com.shyoon.wms.inbound.domain.InboundItem;
import com.shyoon.wms.inbound.domain.InboundRepository;
import com.shyoon.wms.product.domain.ProductRepository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

class RegisterInbound {

    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    RegisterInbound(final ProductRepository productRepository,
                    final InboundRepository inboundRepository) {
        this.productRepository = productRepository;
        this.inboundRepository = inboundRepository;
    }

    public void request(Request request) {
        // TODO: 요청을 도메인으로 변경해서 도메인을 저장
        final Inbound inbound = createInbound(request);

        inboundRepository.save(inbound);
    }

    private Inbound createInbound(Request request) {
        return new Inbound(
                request.title,
                request.description,
                request.orderRequestedAt,
                request.estimatedArrivalAt,
                mapToInboundItems(request)
        );
    }

    private List<InboundItem> mapToInboundItems(Request request) {
        return request.inboundItems.stream()
                .map(this::newInboundItem)
                .toList();
    }

    private InboundItem newInboundItem(Request.Item inboundItem) {
        return new InboundItem(
                productRepository.getBy(inboundItem.productNo),
                inboundItem.quantity,
                inboundItem.unitPrice,
                inboundItem.description
        );
    }

    public record Request(
            String title,
            String description,
            LocalDateTime orderRequestedAt,
            LocalDateTime estimatedArrivalAt,
            List<Request.Item> inboundItems) {

        public Request {
            Assert.hasText(title, "입고 제목은 필수입니다.");
            Assert.hasText(description, "입고 설명은 필수입니다.");
            Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
            Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
            Assert.notEmpty(inboundItems, "입고 품목은 필수입니다.");
        }

        public record Item(
                Long productNo,
                Long quantity,
                Long unitPrice,
                String description) {

            public Item {
                Assert.notNull(productNo, "상품 번호는 필수입니다.");
                Assert.notNull(quantity, "수량은 필수입니다.");
                if (quantity < 1) {
                    throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
                }

                Assert.notNull(unitPrice, "단가는 필수입니다.");
                if (unitPrice < 0) {
                    throw new IllegalArgumentException("단가는 0원 이상이어야 합니다.");
                }

                Assert.hasText(description, "품목 설명은 필수입니다.");
            }
        }
    }
}
