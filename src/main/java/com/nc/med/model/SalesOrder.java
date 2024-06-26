package com.nc.med.model;

import com.nc.med.mapper.DeliverStatus;
import com.nc.med.mapper.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class SalesOrder extends BaseEntity<String> implements Serializable, Comparable<SalesOrder> {
    private static final long serialVersionUID = -1000219078147252957L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesOrderID;
    private double totalPrice;
    private double amountPaid;
    private double currentBalance;
    private double previousBalance;
    private int totalQty;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;
    @OneToMany(mappedBy = "salesOrder", fetch = FetchType.EAGER)
    private List<SalesOrderDetail> salesOrderDetail;
    private String vehicleNo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    private String comment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDate;
    private double totalProfit;
    private double currentDue;
    private double upiPayment;
    private double cashPayment;

    public void setUpiPayment(double upiPayment) {
        this.upiPayment = upiPayment;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public double getUpiPayment() {
        return upiPayment;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public DeliverStatus getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(DeliverStatus deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

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

    public Long getSalesOrderID() {
        return salesOrderID;
    }

    public void setSalesOrderID(Long salesOrderID) {
        this.salesOrderID = salesOrderID;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SalesOrderDetail> getSalesOrderDetail() {
        return salesOrderDetail;
    }

    public void setSalesOrderDetail(List<SalesOrderDetail> salesOrderDetail) {
        this.salesOrderDetail = salesOrderDetail;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    @Override
    public int compareTo(SalesOrder o) {
        return o.getBillDate().compareTo(this.getBillDate());
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesOrder that = (SalesOrder) o;
        return Objects.equals(billDate, that.billDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billDate);
    }

    public double getCurrentDue() {
        return currentDue;
    }

    public void setCurrentDue(double currentDue) {
        this.currentDue = currentDue;
    }
}
