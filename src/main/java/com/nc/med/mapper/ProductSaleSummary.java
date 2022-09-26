package com.nc.med.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductSaleSummary {
    private List<ProductDetail> productDetail;
    private double totalProfit;
    private int totalQtySold;

    public ProductSaleSummary(List<ProductDetail> productDetail, int totalQtySold, double totalProfit) {
        this.productDetail = productDetail;
        this.totalProfit = totalProfit;
        this.totalQtySold = totalQtySold;
    }
}
