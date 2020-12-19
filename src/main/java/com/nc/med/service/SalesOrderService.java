package com.nc.med.service;

import java.text.ParseException;
import java.util.List;

import com.nc.med.mapper.CustomerBalanceSheet;
import com.nc.med.mapper.ReportBar;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.model.SalesOrder;

public interface SalesOrderService {
	SalesOrder saveOrder(SalesOrder order);

	SalesOrder findByOrderID(Integer orderID);

	void deleteOrder(SalesOrder orderID);

	SalesOrder findOrderByProductName(String productName);

	List<SalesOrder> findAllOrders();

	double findCustomerBalanceByCustomer(Integer customerID);

	double findAllCustomersBalance();

	List<CustomerBalanceSheet> findCurrentBalanceByCustomers();

	ReportBar findBarChartModels() throws ParseException;

	List<SalesOrder> findAllByCustomer(Integer customerID);

	List<SalesOrder> salesOrderDetailSearch(SalesOrderSearch salesOrderSearch);
}
