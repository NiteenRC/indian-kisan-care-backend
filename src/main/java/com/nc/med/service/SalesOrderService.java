package com.nc.med.service;

import com.nc.med.mapper.CustomerBalance;
import com.nc.med.mapper.CustomerBalanceSheet;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.mapper.StockBookData;
import com.nc.med.model.Customer;
import com.nc.med.model.DailySummary;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface SalesOrderService {
    SalesOrder saveOrder(SalesOrder order);

    SalesOrder findByOrderID(Long orderID);

    void deleteOrder(SalesOrder orderID);

    Long findTransactionsCount();

    CustomerBalance findCustomerBalanceByCustomer(Long customerID);

    double findAllCustomersBalance();

    List<CustomerBalanceSheet> findCurrentBalanceByCustomers(Pageable pageable);

    List<DailySummary> findBarChartModels();

    List<SalesOrder> findAllByCustomer(Long customerID);

    List<SalesOrder> salesOrderDetailSearch(SalesOrderSearch salesOrderSearch);

    StockBookData findStockDataByDateAndProduct(String productName, LocalDate startDate, LocalDate endDate);

    List<SalesOrderDetail> deleteOrderDetails(SalesOrderDetail orderDetail);

    void saveProfitInDailySummary(SalesOrder order, Customer customer, double profit);

    Page<SalesOrder> findByCustomerCustomerNameIgnoreCaseContaining(String customerName, Pageable pageable);
}
