package com.nc.med.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CurrentStock {
    private List<ProductDetail> productDetails;
    private double totalPurchasePrice;
    private long totalQtyPurchased;

    public CurrentStock(List<ProductDetail> productDetails, double totalPurchasePrice, long totalQtyPurchased) {
        this.productDetails = productDetails;
        this.totalPurchasePrice = totalPurchasePrice;
        this.totalQtyPurchased = totalQtyPurchased;
    }
}
