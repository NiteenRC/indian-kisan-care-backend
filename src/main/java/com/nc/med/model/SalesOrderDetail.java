package com.nc.med.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SalesOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long salesOrderDetailID;
    @ManyToOne // (fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "salesOrderID")
    @JsonIgnore
    private SalesOrder salesOrder;
    @ManyToOne
    @JoinColumn(name = "productID") // , insertable = false, updatable = false
    private Product product;
    private Integer qtyOrdered;
    @JsonProperty("price")
    private double salesPrice;
    private double purchasePrice;
    private double profit;

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Long getSalesOrderDetailID() {
        return salesOrderDetailID;
    }

    public void setSalesOrderDetailID(Long salesOrderDetailID) {
        this.salesOrderDetailID = salesOrderDetailID;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(Integer qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
