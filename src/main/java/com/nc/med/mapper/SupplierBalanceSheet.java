package com.nc.med.mapper;

import com.nc.med.model.Supplier;

public class SupplierBalanceSheet {
    private Supplier supplier;
    private Double currentBalance;

    public SupplierBalanceSheet(Supplier supplier, Double currentBalance) {
        super();
        this.setSupplier(supplier);
        this.currentBalance = currentBalance;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
