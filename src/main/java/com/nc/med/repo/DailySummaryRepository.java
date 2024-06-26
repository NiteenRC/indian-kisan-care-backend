package com.nc.med.repo;

import com.nc.med.model.Customer;
import com.nc.med.model.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    List<DailySummary> findByBillDate(@Temporal(TemporalType.DATE) Date billDate);

    List<DailySummary> findByBillDateAndCustomer(@Temporal(TemporalType.DATE) Date billDate, Customer customer);

    @Query("select new com.nc.med.model.DailySummary(billDate, sum(transaction), sum(profit), " +
            "sum(dueAmount), sum(dueCollection), sum(cashPayment), sum(upiPayment)) " +
            "from DailySummary GROUP BY billDate order by billDate desc")
    List<DailySummary> findGroupByBillDate();
}
