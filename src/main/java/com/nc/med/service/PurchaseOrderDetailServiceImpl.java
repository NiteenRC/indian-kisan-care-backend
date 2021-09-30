package com.nc.med.service;

import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.PurchaseOrderDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderDetailServiceImpl.class);
    private final PurchaseOrderDetailRepo orderDetailRepo;
    private final ProductRepo productRepo;

    public PurchaseOrderDetailServiceImpl(PurchaseOrderDetailRepo orderDetailRepo, ProductRepo productRepo) {
        this.orderDetailRepo = orderDetailRepo;
        this.productRepo = productRepo;
    }

    @Override
    public void savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        Product product = productRepo.findById(purchaseOrderDetail.getProduct().getId()).orElse(null);

        assert product != null;
        double previousPrice = product.getPrice() * (double) product.getQty();
        double currentPrice = purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQtyOrdered();

        double avaragePrice = (previousPrice + currentPrice)
                / ((double) product.getQty() + purchaseOrderDetail.getQtyOrdered());

        //avaragePrice += avaragePrice * product.getGst() / 100;
        long averagePurchasePrice = Math.round(avaragePrice);
        product.setQty(product.getQty() + purchaseOrderDetail.getQtyOrdered());
        product.setPrice(averagePurchasePrice);
        productRepo.save(product);
        //purchaseOrderDetail.setPrice(avaragePrice);
        orderDetailRepo.save(purchaseOrderDetail);
    }

    @Override
    public void deletePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        orderDetailRepo.delete(purchaseOrderDetail);
    }

    @Override
    public PurchaseOrderDetail findById(long id) {
        return orderDetailRepo.findById(id).orElse(null);
    }

}
