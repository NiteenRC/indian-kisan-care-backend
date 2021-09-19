package com.nc.med.repo;

import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderDetailRepo extends JpaRepository<SalesOrderDetail, Integer> {
    List<SalesOrderDetail> findBySalesOrder(SalesOrder salesOrder);
}
