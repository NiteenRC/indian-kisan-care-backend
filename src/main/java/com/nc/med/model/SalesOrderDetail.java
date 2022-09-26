package com.nc.med.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
public class SalesOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Temporal(TemporalType.DATE)
    private Date billDate;
    private String productName;
}
