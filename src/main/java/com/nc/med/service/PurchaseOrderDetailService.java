package com.nc.med.service;

import com.nc.med.model.PurchaseOrderDetail;

import java.util.List;

public interface PurchaseOrderDetailService {
    PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);

    List<PurchaseOrderDetail> savePurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetails);

    PurchaseOrderDetail findByPurchaseOrderDetailID(Integer purchaseOrderDetailID);

    void deletePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetailID);

    PurchaseOrderDetail findPurchaseOrderDetailByProductName(String productName);

    List<PurchaseOrderDetail> findAllPurchaseOrderDetails();

}
