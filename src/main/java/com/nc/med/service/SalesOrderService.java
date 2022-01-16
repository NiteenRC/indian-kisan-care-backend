package com.nc.med.service;

import com.nc.med.mapper.*;
import com.nc.med.model.Customer;
import com.nc.med.model.DailySummary;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;

import java.time.LocalDate;
import java.util.List;

public interface SalesOrderService {
    SalesOrder saveOrder(SalesOrder order);

    SalesOrder findByOrderID(Long orderID);

    void deleteOrder(SalesOrder orderID);

    List<SalesOrder> findAllOrders();

    CustomerBalance findCustomerBalanceByCustomer(Long customerID);

    double findAllCustomersBalance();

    List<CustomerBalanceSheet> findCurrentBalanceByCustomers();

    List<DailySummary> findBarChartModels();

    List<SalesOrder> findAllByCustomer(Long customerID);

    List<SalesOrder> salesOrderDetailSearch(SalesOrderSearch salesOrderSearch);

    StockBookData findStockDataByDateAndProduct(String productName, LocalDate startDate, LocalDate endDate);

    List<SalesOrderDetail> deleteOrderDetails(SalesOrderDetail orderDetail);

	void saveProfitInDailySummary(SalesOrder order, Customer customer, double profit);
}
