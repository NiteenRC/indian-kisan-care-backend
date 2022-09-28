package com.nc.med.service;

import com.nc.med.mapper.CurrentStock;
import com.nc.med.mapper.ProductDetail;
import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.PurchaseOrderDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        double averagePrice = (previousPrice + currentPrice)
                / ((double) product.getQty() + purchaseOrderDetail.getQtyOrdered());
        long averagePurchasePrice = Math.round(averagePrice);
        product.setQty(product.getQty() + purchaseOrderDetail.getQtyOrdered());
        product.setPrice(averagePurchasePrice);
        purchaseOrderDetail.setProductName(product.getProductName());
        purchaseOrderDetail.setCurrentStock(product.getQty() + purchaseOrderDetail.getQtyOrdered());
        productRepo.save(product);
        orderDetailRepo.save(purchaseOrderDetail);
    }

    @Override
    public PurchaseOrderDetail findById(long id) {
        return orderDetailRepo.findById(id).orElse(null);
    }

    @Override
    public CurrentStock findCurrentStock(String productName) {
        List<ProductDetail> productDetails = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        double totalPurchasePrice = 0;
        int totalQtyPurchased = 0;

        if (!Objects.equals(productName, "null")) {
            assert false;
            products.add(productRepo.findByProductName(productName));
        } else {
            products = productRepo.findAll(Sort.by("productName"));
        }

        for (Product product : products) {
            totalQtyPurchased += product.getQty();
            totalPurchasePrice += product.getQty() * product.getPurchasePrice();
            productDetails.add(new ProductDetail(product.getProductName(),
                    product.getQty(), product.getPurchasePrice(), product.getCurrentPrice() - product.getPurchasePrice()));
        }
        return new CurrentStock(productDetails, totalPurchasePrice, totalQtyPurchased);
    }
}
