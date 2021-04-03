package com.nc.med.service;

import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.PurchaseOrderDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderDetailServiceImpl.class);

    @Autowired
    private PurchaseOrderDetailRepo orderDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        Product product = purchaseOrderDetail.getProduct();

        double previousPrice = product.getPrice() * Double.valueOf(product.getQty());
        double currentPrice = purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQtyOrdered();

        double avaragePrice = (previousPrice + currentPrice)
                / (Double.valueOf(product.getQty()) + purchaseOrderDetail.getQtyOrdered());

        product.setQty(product.getQty() + purchaseOrderDetail.getQtyOrdered());
        product.setPrice(avaragePrice);
        productRepo.save(product);
        return orderDetailRepo.save(purchaseOrderDetail);
    }

    @Override
    public PurchaseOrderDetail findPurchaseOrderDetailByProductName(String productName) {
        return null;// orderDetailRepo.findByProductName(productName);
    }

    @Override
    public PurchaseOrderDetail findByPurchaseOrderDetailID(Integer purchaseOrderDetailID) {
        return orderDetailRepo.findById(purchaseOrderDetailID).get();
    }

    @Override
    public void deletePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetailID) {
        orderDetailRepo.delete(purchaseOrderDetailID);
    }

    @Override
    public List<PurchaseOrderDetail> findAllPurchaseOrderDetails() {
        return orderDetailRepo.findAll();
    }

    @Override
    public List<PurchaseOrderDetail> savePurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetails) {
        return orderDetailRepo.saveAll(purchaseOrderDetails);
    }
}
