package com.nc.med.service;

import com.nc.med.model.PurchaseOrderDetail;

public interface PurchaseOrderDetailService {
    void savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);

    void deletePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);

    PurchaseOrderDetail findById(long id);
}
