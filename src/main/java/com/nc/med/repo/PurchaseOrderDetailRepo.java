package com.nc.med.repo;

import com.nc.med.model.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderDetailRepo extends JpaRepository<PurchaseOrderDetail, Integer> {

}
