package com.nc.med.service;

import com.nc.med.mapper.ProductSaleSummary;
import com.nc.med.model.SalesOrderDetail;

import java.util.List;

public interface SalesOrderDetailService {
    SalesOrderDetail saveSalesOrderDetail(SalesOrderDetail salesOrderDetail) throws Exception;

    List<SalesOrderDetail> saveSalesOrderDetail(List<SalesOrderDetail> salesOrderDetails);

    SalesOrderDetail findBySalesOrderDetailID(Integer salesOrderDetailID);

    void deleteSalesOrderDetail(SalesOrderDetail salesOrderDetailID);

    SalesOrderDetail findSalesOrderDetailByProductName(String productName);

    List<SalesOrderDetail> findAllSalesOrderDetails();

    List<ProductSaleSummary> salesOrderDetailProductWise();

}
