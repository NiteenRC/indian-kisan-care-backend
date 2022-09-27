package com.nc.med.service;

import com.nc.med.mapper.CurrentStock;
import com.nc.med.model.PurchaseOrderDetail;

public interface PurchaseOrderDetailService {
    void savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);

    PurchaseOrderDetail findById(long id);

    CurrentStock findCurrentStock(String productName);
}
