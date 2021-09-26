package com.nc.med.service;

import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.PurchaseOrderDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderDetailServiceImpl.class);

    @Autowired
    private PurchaseOrderDetailRepo orderDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        Product product = productRepo.findById(purchaseOrderDetail.getProduct().getId()).get();

        double previousPrice = product.getPrice() * (double) product.getQty();
        double currentPrice = purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQtyOrdered();

        double avaragePrice = (previousPrice + currentPrice)
                / ((double) product.getQty() + purchaseOrderDetail.getQtyOrdered());

        //avaragePrice += avaragePrice * product.getGst() / 100;
        long avaragePriceRoundUp = Math.round(avaragePrice);
        product.setQty(product.getQty() + purchaseOrderDetail.getQtyOrdered());
        product.setPrice(avaragePriceRoundUp);
        productRepo.save(product);
        //purchaseOrderDetail.setPrice(avaragePrice);
        orderDetailRepo.save(purchaseOrderDetail);
    }

}
