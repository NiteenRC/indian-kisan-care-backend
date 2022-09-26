package com.nc.med.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDetail {
    private Date billDate;
    private String productName;
    private double profit;
    private int qtySold;

    public ProductDetail(Date billDate, String productName, int qtySold, double profit) {
        super();
        this.billDate = billDate;
        this.productName = productName;
        this.qtySold = qtySold;
        this.profit = profit;
    }
}
