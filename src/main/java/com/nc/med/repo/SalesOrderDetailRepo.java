package com.nc.med.repo;

import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SalesOrderDetailRepo extends JpaRepository<SalesOrderDetail, Long> {
    List<SalesOrderDetail> findBySalesOrder(SalesOrder salesOrder);

    @Query(value = "SELECT bill_date, product_name, sum(qty_ordered) as qtySold, sum(profit) as profit " +
            "FROM sales_order_detail where bill_date between :#{#start} and :#{#end} " +
            "and product_name = :#{#productName} " +
            "group by bill_date, product_name order by bill_date desc, product_name",
            nativeQuery = true)
    List<Object[]> productSalesSummaryWithAllFields(String productName, LocalDate start, LocalDate end);

    @Query(value = "SELECT bill_date, product_name, sum(qty_ordered) as qtySold, sum(profit) as profit " +
            "FROM sales_order_detail where bill_date between :#{#start} and :#{#end} " +
            "group by bill_date, product_name order by bill_date desc, product_name",
            nativeQuery = true)
    List<Object[]> productSalesSummaryWithoutProductName(LocalDate start, LocalDate end);

    @Query(value = "SELECT bill_date, product_name, sum(qty_ordered) as qtySold, sum(profit) as profit " +
            "FROM sales_order_detail where bill_date between '2022-01-01' and '2100-01-01' " +
            "and product_name = :#{#productName} " +
            "group by bill_date, product_name order by bill_date desc, product_name",
            nativeQuery = true)
    List<Object[]> productSalesSummaryWithoutDates(String productName);

    @Query(value = "SELECT bill_date, product_name, sum(qty_ordered) as qtySold, sum(profit) as profit " +
            "FROM sales_order_detail where bill_date between '2022-01-01' and '2100-01-01' " +
            "group by bill_date, product_name order by bill_date desc, product_name",
            nativeQuery = true)
    List<Object[]> productSalesSummaryAllData();
}
