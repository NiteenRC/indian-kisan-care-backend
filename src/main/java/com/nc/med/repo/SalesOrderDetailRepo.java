package com.nc.med.repo;

import com.nc.med.model.SalesOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderDetailRepo extends JpaRepository<SalesOrderDetail, Integer> {

}
