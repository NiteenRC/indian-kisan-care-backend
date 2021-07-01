package com.nc.med.service;

import com.nc.med.mapper.CustomerBalanceSheet;
import com.nc.med.mapper.ReportBar;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.mapper.StockBook;
import com.nc.med.model.SalesOrder;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface SalesOrderService {
    SalesOrder saveOrder(SalesOrder order);

    SalesOrder findByOrderID(Long orderID);

    void deleteOrder(SalesOrder orderID);

    SalesOrder findOrderByProductName(String productName);

    List<SalesOrder> findAllOrders();

    double findCustomerBalanceByCustomer(Long customerID);

    double findAllCustomersBalance();

    List<CustomerBalanceSheet> findCurrentBalanceByCustomers();

    ReportBar findBarChartModels() throws ParseException;

    List<SalesOrder> findAllByCustomer(Long customerID);

    List<SalesOrder> salesOrderDetailSearch(SalesOrderSearch salesOrderSearch);

	Set<StockBook> findStockBook(String productName);
}
