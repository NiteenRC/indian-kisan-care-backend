package com.nc.med.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nc.med.mapper.OrderStatus;
import com.nc.med.model.Customer;
import com.nc.med.model.SalesOrder;

public interface SalesOrderRepo extends JpaRepository<SalesOrder, Long> {
	List<SalesOrder> findAmountBalanceByCustomer(Customer customer);

	@Query(value = "select CAST(created_date AS DATE), sum(total_price) from sales_order group by cast(created_date as date) order by 1", nativeQuery = true)
	Object[][] findChart();

	List<SalesOrder> findAllByCustomer(Customer customer);

	// List<SalesOrder> findByCreatedDateBetween(Date startDate, Date endDate);

	@Query(value = "select CAST(bill_date AS DATE), sum(total_profit) from sales_order where CAST(bill_date as date) between :startDate and :endDate group by cast(bill_date as date) order by 1", nativeQuery = true)
	Object[][] getByCreatedDateBetweenDates(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	@Query(value = "select CAST(so.bill_date AS DATE), p.product_name, p.qty, sum(sod.qty_ordered), sum(sod.profit) \r\n"
			+ "from sales_order so \r\n"
			+ "join SALES_ORDER_DETAIL sod on so.SALES_ORDERID=sod.SALES_ORDERID\r\n"
			+ "join product p on p.id=sod.productid\r\n"
			+ "where CAST(bill_date as date) between :startDate and :endDate\r\n"
			+ "and p.product_name =:productName\r\n"
			+ "group by cast(so.bill_date as date), p.product_name\r\n"
			+ "order by cast(so.bill_date as date) desc, sum(sod.profit) desc", nativeQuery = true)
	Object[][] getByCreatedDateBetweenDatesStock(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate, @Param("productName") String productName);
	
	@Query(value = "select CAST(so.bill_date AS DATE), p.product_name, p.qty, sum(sod.qty_ordered), sum(sod.profit) \r\n"
			+ "from sales_order so \r\n"
			+ "join SALES_ORDER_DETAIL sod on so.SALES_ORDERID=sod.SALES_ORDERID\r\n"
			+ "join product p on p.id=sod.productid\r\n"
			+ "where CAST(bill_date as date) between :startDate and :endDate\r\n"
			+ "group by cast(so.bill_date as date), p.product_name\r\n"
			+ "order by cast(so.bill_date as date) desc, sum(sod.profit) desc", nativeQuery = true)
	Object[][] getByCreatedDateBetweenDatesStockAll(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	// Writing JPQL using Spring Data Jpa @Query.
	@Query("select s from SalesOrder s where s.status = ?1")
	List<SalesOrder> getSalesOrder(String status);

	// Writing the Named Parameter @Query.
	@Query("select s from SalesOrder s where s.createdDate <= :createdDate")
	List<SalesOrder> getSalesOrderByCreateDate(LocalDateTime createdDate);

	List<SalesOrder> findByCreatedDateBetween(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	// List<SalesOrder> findByCustomerCustomerIDAndCreatedDateBetween(int
	// customerId, @Param("startDate") LocalDateTime startDate, @Param("endDate")
	// LocalDateTime endDate);
	// @Param is not required

	List<SalesOrder> findByCustomerIdAndCreatedDateBetweenAndStatus(Long customerId, LocalDateTime startDate,
			LocalDateTime endDate, OrderStatus orderStatus);

	@Query(value = "SELECT sum(current_balance) FROM SALES_ORDER where customer_id = :#{#customer.id}  group by customer_id", nativeQuery = true)
	int findCurrentSum(@Param("customer") Customer customer);
	
	
	@Query(value = "select sum(sod.qty_ordered) \r\n"
			+ "	from SALES_ORDER_DETAIL sod\r\n"
			+ "	join product p on p.id=sod.productid\r\n"
			+ "	where p.product_name = '10:26:26-MAHADHAN'\r\n"
			+ "	group by p.product_name",nativeQuery = true)
	int findSumOfQtySold();

}
