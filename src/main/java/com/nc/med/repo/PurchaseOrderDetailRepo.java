package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.PurchaseOrderDetail;

public interface PurchaseOrderDetailRepo extends JpaRepository<PurchaseOrderDetail, Integer> {

}
