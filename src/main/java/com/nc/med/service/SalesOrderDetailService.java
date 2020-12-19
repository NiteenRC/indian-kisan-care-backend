package com.nc.med.service;

import java.util.List;

import com.nc.med.mapper.ProductSaleSummary;
import com.nc.med.model.SalesOrderDetail;

public interface SalesOrderDetailService {
	SalesOrderDetail saveSalesOrderDetail(SalesOrderDetail salesOrderDetail) throws Exception;

	List<SalesOrderDetail> saveSalesOrderDetail(List<SalesOrderDetail> salesOrderDetails);

	SalesOrderDetail findBySalesOrderDetailID(Integer salesOrderDetailID);

	void deleteSalesOrderDetail(SalesOrderDetail salesOrderDetailID);

	SalesOrderDetail findSalesOrderDetailByProductName(String productName);

	List<SalesOrderDetail> findAllSalesOrderDetails();
	
	List<ProductSaleSummary> salesOrderDetailProductWise();

}
