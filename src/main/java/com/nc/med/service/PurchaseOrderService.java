package com.nc.med.service;

import com.nc.med.mapper.BalancePayment;
import com.nc.med.mapper.SupplierBalance;
import com.nc.med.mapper.SupplierBalanceSheet;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.PurchaseOrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrder saveOrder(PurchaseOrder order);

    PurchaseOrder findByOrderID(Long orderID);

    void deleteOrder(PurchaseOrder order) throws Exception;

    List<PurchaseOrderDetail> deleteOrderDetails(PurchaseOrderDetail orderDetail) throws Exception;

    Page<PurchaseOrder> findAllOrders(Pageable pageable);

    SupplierBalance findSupplierBalanceBySupplier(Long supplierID);

    double findAllSuppliersBalance();

    List<SupplierBalanceSheet> findCurrentBalanceBySuppliers();

    PurchaseOrder getPurchaseOrder(BalancePayment balancePayment);

    List<PurchaseOrder> findBySupplier(Long supplierId);

    Page<PurchaseOrder> findBySupplierSupplierNameIgnoreCaseContaining(String supplierName, Pageable pageable);
}
