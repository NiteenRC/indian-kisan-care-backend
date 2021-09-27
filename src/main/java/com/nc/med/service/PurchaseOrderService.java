package com.nc.med.service;

import com.nc.med.mapper.BalancePayment;
import com.nc.med.mapper.SupplierBalanceSheet;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.PurchaseOrderDetail;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrder saveOrder(PurchaseOrder order);

    PurchaseOrder findByOrderID(Long orderID);

    void deleteOrder(PurchaseOrder order) throws Exception;

    List<PurchaseOrderDetail> deleteOrderDetails(PurchaseOrderDetail orderDetail) throws Exception;

    List<PurchaseOrder> findAllOrders();

    double findSupplierBalanceBySupplier(Long supplierID);

    double findAllSuppliersBalance();

    List<SupplierBalanceSheet> findCurrentBalanceBySuppliers();

    PurchaseOrder getPurchaseOrder(BalancePayment balancePayment);
}
