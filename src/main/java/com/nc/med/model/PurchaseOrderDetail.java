package com.nc.med.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PurchaseOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue // (strategy = GenerationType.IDENTITY)
    private Long purchaseOrderDetailID;
    @ManyToOne // (fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "purchaseOrderID")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;
    @ManyToOne
    @JoinColumn(name = "productID") // , insertable = false, updatable = false
    private Product product;
    private Integer qtyOrdered;
    private Integer price;

    public Long getPurchaseOrderDetailID() {
        return purchaseOrderDetailID;
    }

    public void setPurchaseOrderDetailID(Long purchaseOrderDetailID) {
        this.purchaseOrderDetailID = purchaseOrderDetailID;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
