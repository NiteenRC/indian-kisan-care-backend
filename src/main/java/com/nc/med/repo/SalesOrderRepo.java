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

    @Query(value = "select CAST(created_date AS DATE), sum(total_price) from sales_order group by cast(created_date as date) order by 1", nativeQuery = true)
    Object[][] findChart();

    List<SalesOrder> findAllByCustomer(Customer customer);

    // List<SalesOrder> findByCreatedDateBetween(Date startDate, Date endDate);

    @Query(value = "select CAST(created_date AS DATE), sum(total_price) from sales_order where CAST(created_date as date) between :startDate and :endDate group by cast(created_date as date) order by 1", nativeQuery = true)
    Object[][] getByCreatedDateBetweenDates(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    // Writing JPQL using Spring Data Jpa @Query.
    @Query("select s from SalesOrder s where s.status = ?1")
    List<SalesOrder> getSalesOrder(String status);

    // Writing the Named Parameter @Query.
    @Query("select s from SalesOrder s where s.createdDate <= :createdDate")
    List<SalesOrder> getSalesOrderByCreateDate(LocalDateTime createdDate);

    List<SalesOrder> findByCreatedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //List<SalesOrder> findByCustomerCustomerIDAndCreatedDateBetween(int customerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    //@Param is not required

    List<SalesOrder> findByCustomerIdAndCreatedDateBetweenAndStatus(Long customerId, LocalDateTime startDate,
                                                                    LocalDateTime endDate, OrderStatus orderStatus);

}
