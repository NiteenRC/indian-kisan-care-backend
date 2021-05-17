package com.nc.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.Supplier;

@Transactional
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {

	// Order findByProductName(String productName);

	// Order findByProductID(Integer productID);

	// List<Cart> findAllByDateBetween(Date fromDate, Date toDate);

	List<PurchaseOrder> findAmountBalanceBySupplier(Supplier supplier);

	@Query(value = "SELECT sum(current_balance) FROM PURCHASE_ORDER where supplier_id = :#{#supplier.id}  group by supplier_id", nativeQuery = true)
	int findCurrentSum(@Param("supplier") Supplier supplier);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE PURCHASE_ORDER  c SET c.previous_balance = :currentBalance WHERE c.supplier_id = :#{#supplier.id}", nativeQuery = true)
	int updateAddress(@Param("supplier") Supplier supplier, @Param("currentBalance") double currentBalance);
}
