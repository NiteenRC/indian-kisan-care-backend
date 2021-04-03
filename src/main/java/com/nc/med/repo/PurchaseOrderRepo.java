package com.nc.med.repo;

import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {

    //Order findByProductName(String productName);

    //Order findByProductID(Integer productID);

    //List<Cart> findAllByDateBetween(Date fromDate, Date toDate);

    List<PurchaseOrder> findAmountBalanceBySupplier(Supplier supplier);
}
