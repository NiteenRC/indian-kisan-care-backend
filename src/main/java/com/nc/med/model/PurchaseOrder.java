package com.nc.med.model;

import javax.persistence.*;

import com.nc.med.mapper.OrderStatus;

import java.io.Serializable;
import java.util.List;

@Entity
public class PurchaseOrder extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = -1000119078147252957L;
    @Id
    @GeneratedValue // (strategy = GenerationType.IDENTITY)
    private int purchaseOrderID;
    @Column(name = "totalPrice", nullable = false)
    private double totalPrice;
    private double amountPaid;
    private int totalQty;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    private Supplier supplier;
    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PurchaseOrderDetail> purchaseOrderDetail;
    private double currentBalance;
    private double previousBalance;

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(int purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetail() {
        return purchaseOrderDetail;
    }

    public void setPurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetail) {
        this.purchaseOrderDetail = purchaseOrderDetail;
    }
}
