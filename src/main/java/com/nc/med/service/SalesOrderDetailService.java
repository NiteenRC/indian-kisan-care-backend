package com.nc.med.service;

import com.nc.med.mapper.ProductSaleSummary;
import com.nc.med.model.SalesOrderDetail;

import java.util.List;

public interface SalesOrderDetailService {
    SalesOrderDetail saveSalesOrderDetail(SalesOrderDetail salesOrderDetail) throws Exception;

    List<ProductSaleSummary> salesOrderDetailProductWise();

    SalesOrderDetail findById(Long orderID);
}
