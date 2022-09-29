package com.nc.med.repo;

import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findAmountBalanceBySupplier(Supplier supplier);

    @Query(value = "SELECT sum(current_balance) FROM PURCHASE_ORDER where supplier_id = :#{#supplier.id}  group by supplier_id", nativeQuery = true)
    Double findCurrentSum(@Param("supplier") Supplier supplier);

    @Query(value = "SELECT previous_balance FROM PURCHASE_ORDER where supplier_id = :#{#supplier.id} order by  purchase_orderid desc limit 1", nativeQuery = true)
    Double findDueAmount(@Param("supplier") Supplier supplier);

    List<PurchaseOrder> findBySupplier(Supplier supplier);

    Page<PurchaseOrder> findBySupplierSupplierNameIgnoreCaseContaining(String supplierName, Pageable pageable);
}
