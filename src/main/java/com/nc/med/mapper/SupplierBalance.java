package com.nc.med.mapper;

import com.nc.med.model.Supplier;

public class SupplierBalance {
    private Supplier supplier;
    private Double balance;

    public SupplierBalance(Supplier supplier, Double balance) {
        this.supplier = supplier;
        this.balance = balance;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
