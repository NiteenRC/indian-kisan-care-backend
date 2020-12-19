package com.nc.med.service;

import java.text.ParseException;
import java.util.List;

import com.nc.med.mapper.SupplierBalanceSheet;
import com.nc.med.model.PurchaseOrder;

public interface PurchaseOrderService {
	PurchaseOrder saveOrder(PurchaseOrder order);

	PurchaseOrder findByOrderID(Integer orderID);

	void deleteOrder(PurchaseOrder orderID);

	PurchaseOrder findOrderByProductName(String productName);

	List<PurchaseOrder> findAllOrders();

	List<PurchaseOrder> findByDates(String startDate, String endDate) throws ParseException;

	double findSupplierBalanceBySupplier(Integer supplierID);

	double findAllSuppliersBalance();

	List<SupplierBalanceSheet> findCurrentBalanceBySuppliers();
}
