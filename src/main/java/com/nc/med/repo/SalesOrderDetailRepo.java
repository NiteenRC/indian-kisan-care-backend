package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.SalesOrderDetail;

public interface SalesOrderDetailRepo extends JpaRepository<SalesOrderDetail, Integer> {

}
