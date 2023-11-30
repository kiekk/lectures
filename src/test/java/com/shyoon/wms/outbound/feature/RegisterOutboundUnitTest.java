package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.inbound.domain.LPNFixture;
import com.shyoon.wms.location.domain.Inventory;
import com.shyoon.wms.location.domain.LocationFixture;
import com.shyoon.wms.outbound.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisterOutboundUnitTest {

    private RegisterOutbound registerOutbound;

    @BeforeEach
    void setUp() {
        registerOutbound = new RegisterOutbound(null, null, null, null);
    }

    @Test
    @DisplayName("주문한 상품을 포장할 수 있는 포장재를 찾는다.")
    void getOptimalPackagingMaterial() {
        final Order order = OrderFixture.anOrder().build();
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();
        final PackagingMaterial optimalPackagingMaterial = new PackagingMaterials(List.of(packagingMaterial)).getOptimalPackagingMaterial(order.totalWeight(), order.totalVolume());

        assertThat(optimalPackagingMaterial).isNotNull();
    }

    @Test
    @DisplayName("주문한 상품을 포장할 수 있는 포장재를 찾는다.")
    void empty_getOptimalPackagingMaterial() {
        final Order order = OrderFixture.anOrder().orderProduct(
                OrderProductFixture.anOrderProduct().orderQuantity(100L)
        ).build();
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();
        final PackagingMaterial optimalPackagingMaterial = new PackagingMaterials(List.of(packagingMaterial)).getOptimalPackagingMaterial(order.totalWeight(), order.totalVolume());

        assertThat(optimalPackagingMaterial).isNull();
    }

    @Test
    void createOutbound() {
        final Inventory inventory = new Inventory(LocationFixture.aLocation().build(), LPNFixture.anLPN().build());
        final Order order = OrderFixture.anOrder().build();
        final Inventories inventories = new Inventories(List.of(inventory), 1L);
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();


        final List<Inventories> inventoriesList = List.of(inventories);
        final List<PackagingMaterial> packagingMaterials = List.of(packagingMaterial);
        final Outbound outbound = registerOutbound.createOutbound(
                inventoriesList,
                packagingMaterials,
                order,
                false,
                LocalDate.now());

        assertThat(outbound).isNotNull();
    }

    @Test
    void fail_over_quantity_createOutbound() {
        final Inventory inventory = new Inventory(LocationFixture.aLocation().build(), LPNFixture.anLPN().build());
        final Order order = OrderFixture.anOrder().build();
        final Inventories inventories = new Inventories(List.of(inventory), 3L);
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();


        final List<Inventories> inventoriesList = List.of(inventories);
        final List<PackagingMaterial> packagingMaterials = List.of(packagingMaterial);

        assertThatThrownBy(() ->
                registerOutbound.createOutbound(
                        inventoriesList,
                        packagingMaterials,
                        order,
                        false,
                        LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    void expire_createOutbound() {
        final Inventory inventory = new Inventory(LocationFixture.aLocation().build(), LPNFixture.anLPN().expirationAt(LocalDateTime.now().minusDays(1)).build());
        final Order order = OrderFixture.anOrder().build();
        final Inventories inventories = new Inventories(List.of(inventory), 1L);
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().build();


        final List<Inventories> inventoriesList = List.of(inventories);
        final List<PackagingMaterial> packagingMaterials = List.of(packagingMaterial);

        assertThatThrownBy(() ->
                registerOutbound.createOutbound(
                        inventoriesList,
                        packagingMaterials,
                        order,
                        false,
                        LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    void over_max_weight_createOutbound() {
        final Inventory inventory = new Inventory(LocationFixture.aLocation().build(), LPNFixture.anLPN().build());
        final Order order = OrderFixture.anOrder().build();
        final Inventories inventories = new Inventories(List.of(inventory), 1L);
        final PackagingMaterial packagingMaterial = PackagingMaterialFixture.aPackagingMaterial().maxWeightInGrams(1L).build();


        final List<Inventories> inventoriesList = List.of(inventories);
        final List<PackagingMaterial> packagingMaterials = List.of(packagingMaterial);

        final Outbound outbound = registerOutbound.createOutbound(
                inventoriesList,
                packagingMaterials,
                order,
                false,
                LocalDate.now());

        assertThat(outbound.getRecommendedPackagingMaterial()).isNull();
    }

}
