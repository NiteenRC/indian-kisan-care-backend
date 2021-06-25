package com.nc.med.service;

import com.nc.med.mapper.SupplierBalanceSheet;
import com.nc.med.model.PurchaseOrder;

import java.text.ParseException;
import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrder saveOrder(PurchaseOrder order);

    PurchaseOrder findByOrderID(Long orderID);

    void deleteOrder(PurchaseOrder orderID);

    PurchaseOrder findOrderByProductName(String productName);

    List<PurchaseOrder> findAllOrders();

    List<PurchaseOrder> findByDates(String startDate, String endDate) throws ParseException;

    double findSupplierBalanceBySupplier(Long supplierID);

    double findAllSuppliersBalance();

    List<SupplierBalanceSheet> findCurrentBalanceBySuppliers();
}
