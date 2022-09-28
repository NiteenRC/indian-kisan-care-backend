package com.nc.med.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetail {
    private Date billDate;
    private String productName;
    private double profit;
    private int qtySold;
    private double margin;

    public ProductDetail(String productName, int qtySold, double profit, double margin) {
        super();
        this.productName = productName;
        this.qtySold = qtySold;
        this.profit = profit;
        this.margin = margin;
    }

    public ProductDetail(Date billDate, String productName, int qtySold, double profit, double margin) {
        super();
        this.billDate = billDate;
        this.productName = productName;
        this.qtySold = qtySold;
        this.profit = profit;
        this.margin = margin;
    }
}
