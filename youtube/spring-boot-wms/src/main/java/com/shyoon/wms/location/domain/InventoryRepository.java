package com.shyoon.wms.location.domain;

import com.shyoon.wms.outbound.domain.OrderProduct;
import com.shyoon.wms.outbound.feature.Inventories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
                SELECT i FROM Inventory i WHERE i.productNo = :productNo
            """)
    List<Inventory> listBy(Long productNo);

    default Inventories inventoriesBy(OrderProduct orderProduct) {
        return new Inventories(listBy(orderProduct.getProductNo()));
    }

    @Query("SELECT i FROM Inventory i WHERE i.productNo IN :productNos")
    List<Inventory> listBy(Set<Long> productNos);

    default Inventories inventoriesBy(final Set<Long> productNos) {
        return new Inventories(listBy(productNos));
    }
}
