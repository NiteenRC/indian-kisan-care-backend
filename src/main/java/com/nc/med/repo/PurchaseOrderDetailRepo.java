package com.nc.med.repo;

import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderDetailRepo extends JpaRepository<PurchaseOrderDetail, Long> {
    List<PurchaseOrderDetail> findByPurchaseOrder(PurchaseOrder purchaseOrder);

    @Query(value = "SELECT product_name, sum(price), sum(current_stock) " +
            "FROM purchase_order_detail where product_name = :#{#productName} " +
            "group by product_name order by product_name",
            nativeQuery = true)
    List<Object[]> currentStockData(String productName);

    @Query(value = "SELECT product_name, sum(price), sum(current_stock) " +
            "FROM purchase_order_detail group by product_name order by product_name",
            nativeQuery = true)
    List<Object[]> currentStockData();
}
