package com.shyoon.wms.inbound.feature;


import com.shyoon.wms.product.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterInboundTest {

    private RegisterInbound registerInbound;
    private ProductRepository productRepository;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        // Mockito 를 사용하여 stubbing
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new InboundRepository();
        registerInbound = new RegisterInbound(productRepository, inboundRepository);
    }

    @Test
    @DisplayName("입고를 등록한다.")
    void registerInbound() {
        // given
        final Product product = new Product(
                "name",
                "code",
                "description",
                "brand",
                "maker",
                "origin",
                Category.ELECTRONICS,
                TemperatureZone.ROOM_TEMPERATURE,
                1000L,
                new ProductSize(
                        100L,
                        100L,
                        100L
                )
        );
        Mockito.when(productRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(product));

        final LocalDateTime orderRequestedAt = LocalDateTime.now();
        final LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        final Long productNo = 1L;
        final Long quantity = 1L;
        final Long unitPrice = 1500L;
        final RegisterInbound.Request.Item inboundItem = new RegisterInbound.Request.Item(
                productNo,
                quantity,
                unitPrice,
                "description"
        );
        List<RegisterInbound.Request.Item> inboundItems = List.of(inboundItem);
        RegisterInbound.Request request = new RegisterInbound.Request(
                "title",
                "description",
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems
        );

        registerInbound.request(request);

        assertThat(inboundRepository.findAll()).hasSize(1);
    }

    private class RegisterInbound {

        private final ProductRepository productRepository;
        private final InboundRepository inboundRepository;

        private RegisterInbound(final ProductRepository productRepository,
                                final InboundRepository inboundRepository) {
            this.productRepository = productRepository;
            this.inboundRepository = inboundRepository;
        }

        public void request(Request request) {
            // TODO: 요청을 도메인으로 변경해서 도메인을 저장
            final List<InboundItem> inboundItems = request.inboundItems.stream()
                    .map(inboundItem -> new InboundItem(
                                    productRepository.findById(inboundItem.productNo).orElseThrow(),
                                    inboundItem.quantity,
                                    inboundItem.unitPrice,
                                    inboundItem.description
                            )
                    )
                    .toList();

            Inbound inbound = new Inbound(
                    request.title,
                    request.description,
                    request.orderRequestedAt,
                    request.estimatedArrivalAt,
                    inboundItems
            );

            inboundRepository.save(inbound);
        }

        public record Request(
                String title,
                String description,
                LocalDateTime orderRequestedAt,
                LocalDateTime estimatedArrivalAt,
                List<Item> inboundItems) {

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

    private class InboundItem {
        private final Product product;
        private final Long quantity;
        private final Long unitPrice;
        private final String description;

        public InboundItem(
                final Product product,
                final Long quantity,
                final Long unitPrice,
                final String description) {
            validateConstructor(
                    product,
                    quantity,
                    unitPrice,
                    description);

            this.product = product;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.description = description;
        }

        private static void validateConstructor(
                final Product product,
                final Long quantity,
                final Long unitPrice,
                final String description) {
            Assert.notNull(product, "상품은 필수입니다.");
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

    private class Inbound {
        private Long id;
        private final String title;
        private final String description;
        private final LocalDateTime orderRequestedAt;
        private final LocalDateTime estimatedArrivalAt;
        private final List<InboundItem> inboundItems;

        public Inbound(
                final String title,
                final String description,
                final LocalDateTime orderRequestedAt,
                final LocalDateTime estimatedArrivalAt,
                final List<InboundItem> inboundItems) {
            validateConstructor(
                    title,
                    description,
                    orderRequestedAt,
                    estimatedArrivalAt,
                    inboundItems);

            this.title = title;
            this.description = description;
            this.orderRequestedAt = orderRequestedAt;
            this.estimatedArrivalAt = estimatedArrivalAt;
            this.inboundItems = inboundItems;
        }

        private static void validateConstructor(
                final String title,
                final String description,
                final LocalDateTime orderRequestedAt,
                final LocalDateTime estimatedArrivalAt,
                final List<InboundItem> inboundItems) {
            Assert.hasText(title, "입고 제목은 필수입니다.");
            Assert.hasText(description, "입고 설명은 필수입니다.");
            Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
            Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
            Assert.notEmpty(inboundItems, "입고 품목은 필수입니다.");
        }

        public void assignId(Long id) {
            this.id = id;
        }
    }

    private class InboundRepository {
        private final Map<Long, Inbound> inbounds = new HashMap<>();
        private Long sequence = 1L;

        public void save(Inbound inbound) {
            inbound.assignId(sequence++);
            inbounds.put(inbound.id, inbound);
        }

        public List<Inbound> findAll() {
            return new ArrayList<>(inbounds.values());
        }
    }
}
