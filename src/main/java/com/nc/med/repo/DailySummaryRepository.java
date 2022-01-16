package com.nc.med.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import com.nc.med.model.Customer;
import com.nc.med.model.DailySummary;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
    
	List<DailySummary> findByBillDate(@Temporal(TemporalType.DATE) Date billDate);
	
	List<DailySummary> findByBillDateAndCustomer(@Temporal(TemporalType.DATE) Date billDate, Customer customer);
	
	@Query("select new com.nc.med.model.DailySummary(billDate, sum(transaction), sum(profit), sum(dueAmount), sum(dueCollection)) from DailySummary GROUP BY billDate")
	List<DailySummary> findGroupByBillDate();
}
