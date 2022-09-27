package com.nc.med.service;

import com.nc.med.mapper.ProductSaleSummary;
import com.nc.med.model.SalesOrderDetail;

import java.time.LocalDate;

public interface SalesOrderDetailService {
    SalesOrderDetail saveSalesOrderDetail(SalesOrderDetail salesOrderDetail) throws Exception;

    SalesOrderDetail findById(Long orderID);

    ProductSaleSummary salesOrderDetailProductWise(String productName, LocalDate start, LocalDate end);
}
