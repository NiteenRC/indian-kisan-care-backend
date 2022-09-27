package com.nc.med.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PurchaseOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderDetailID;
    @ManyToOne // (fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "purchaseOrderID")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;
    @ManyToOne
    //@JoinColumn(name = "productID") // , insertable = false, updatable = false
    private Product product;
    private String productName;
    private Integer qtyOrdered;
    private double price;
    private long currentStock;

    public PurchaseOrderDetail(String productName, double price, Integer qtyOrdered) {
        this.productName = productName;
        this.qtyOrdered = qtyOrdered;
        this.price = price;
    }
}
