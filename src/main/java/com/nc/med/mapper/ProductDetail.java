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

    public ProductDetail(String productName, int qtySold, double profit) {
        super();
        this.productName = productName;
        this.qtySold = qtySold;
        this.profit = profit;
    }

    public ProductDetail(Date billDate, String productName, int qtySold, double profit) {
        super();
        this.billDate = billDate;
        this.productName = productName;
        this.qtySold = qtySold;
        this.profit = profit;
    }
}
