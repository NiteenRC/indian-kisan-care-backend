package com.nc.med.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class DailySummary extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = -1000219078147252957L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double dueAmount;
    private double dueCollection;
    private double profit;
    private double transaction;
    @Temporal(TemporalType.DATE)
    private Date billDate;
    @ManyToOne
    private Customer customer;

    public DailySummary() {
    }

    public DailySummary(Date billDate, double transaction, double profit, double dueAmount, double dueCollection, Customer customer) {
        super();
        this.billDate = billDate;
        this.transaction = transaction;
        this.profit = profit;
        this.dueAmount = dueAmount;
        this.dueCollection = dueCollection;
        this.customer = customer;
    }

    public DailySummary(Date billDate, double transaction, double profit, double dueAmount, double dueCollection) {
        super();
        this.billDate = billDate;
        this.transaction = transaction;
        this.profit = profit;
        this.dueAmount = dueAmount;
        this.dueCollection = dueCollection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public double getDueCollection() {
        return dueCollection;
    }

    public void setDueCollection(double dueCollection) {
        this.dueCollection = dueCollection;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getTransaction() {
        return transaction;
    }

    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
