package com.nc.med.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.Supplier;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {

	//Order findByProductName(String productName);

	//Order findByProductID(Integer productID);
	
	//List<Cart> findAllByDateBetween(Date fromDate, Date toDate);

	List<PurchaseOrder> findAmountBalanceBySupplier(Supplier supplier);
}
