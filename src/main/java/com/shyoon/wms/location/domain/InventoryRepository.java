package com.shyoon.wms.location.domain;

import com.shyoon.wms.outbound.domain.OrderProduct;
import com.shyoon.wms.outbound.feature.Inventories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
                SELECT i FROM Inventory i WHERE i.productNo = :productNo
            """)
    List<Inventory> findByProductNo(Long productNo);

    default Inventories inventoriesBy(OrderProduct orderProduct) {
        return new Inventories(findByProductNo(orderProduct.getProductNo()));
    }
}
