package com.nc.med.mapper;

import com.nc.med.model.Supplier;

import java.util.Date;

public class SupplierBalanceSheet implements Comparable<SupplierBalanceSheet> {
    private Supplier supplier;
    private Double currentBalance;
    private Double totalPrice;
    private Double amountPaid;
    private Date billDate;
    private Date dueDate;

    public SupplierBalanceSheet(Supplier supplier, Double totalPrice, Double amountPaid, Double currentBalance,
                                Date billDate, Date dueDate) {
        this.setSupplier(supplier);
        this.setCurrentBalance(currentBalance);
        this.setAmountPaid(amountPaid);
        this.totalPrice = totalPrice;
        this.setBillDate(billDate);
        this.setDueDate(dueDate);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int compareTo(SupplierBalanceSheet o) {
        try {
            return o.billDate.compareTo(this.billDate);
        } catch (Exception e1) {
            try {
                this.billDate = o.billDate;
            } catch (Exception e2) {
                o.billDate = this.billDate;
            }
        }
        return 0;
    }
}
