package com.nc.med.repo;

import com.nc.med.mapper.OrderStatus;
import com.nc.med.model.Customer;
import com.nc.med.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesOrderRepo extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findAmountBalanceByCustomer(Customer customer);

    List<SalesOrder> findAllByCustomer(Customer customer);

    @Query(value = "select CAST(bill_date AS DATE), sum(total_price), sum(amount_paid), sum(total_profit) from sales_order GROUP BY CAST(bill_date AS DATE) order by CAST(bill_date AS DATE) desc", nativeQuery = true)
    List<Object[]> fetchDailyTransaction();

    List<SalesOrder> findByCustomerIdAndCreatedDateBetweenAndStatus(Long customerId, LocalDateTime startDate,
                                                                    LocalDateTime endDate, OrderStatus orderStatus);

    @Query(value = "SELECT sum(current_balance) FROM SALES_ORDER where customer_id = :#{#customer.id}  group by customer_id", nativeQuery = true)
    int findCurrentSum(@Param("customer") Customer customer);


    SalesOrder findFirstByCustomerIdOrderBySalesOrderIDDesc(Long customer);

    @Query(value = "select CAST(so.bill_date AS DATE), p.product_name, sum(sod.qty_ordered), sum(sod.sales_price), sum(sod.profit)\n" +
            "from sales_order so, sales_order_detail sod, product p\n" +
            "where so.sales_orderid = sod.sales_orderid and p.id = sod.productid\n" +
            "and sod.productid = ?1\n" +
            "GROUP BY sod.productid, CAST(so.bill_date AS DATE)\n" +
            "order by CAST(so.bill_date AS DATE);", nativeQuery = true)
    List<Object[]> fetchSalesOrderStock(long productId);

    @Query(value = "select CAST(so.bill_date AS DATE), p.product_name, sum(sod.qty_ordered)\n" +
            "from purchase_order so, purchase_order_detail sod, product p\n" +
            "where so.purchase_orderid = sod.purchase_orderid and p.id = sod.product_id\n" +
            "and sod.product_id = ?1\n" +
            "GROUP BY sod.product_id, CAST(so.bill_date AS DATE)\n" +
            "order by CAST(so.bill_date AS DATE);", nativeQuery = true)
    List<Object[]> fetchPurchaseOrderStock(long productId);
}
