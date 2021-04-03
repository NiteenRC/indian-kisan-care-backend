package com.nc.med.mapper;

import com.nc.med.model.Customer;

public class CustomerBalanceSheet {
    private Customer customer;
    private Double currentBalance;

    public CustomerBalanceSheet(Customer customer, Double currentBalance) {
        this.setCustomer(customer);
        this.setCurrentBalance(currentBalance);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
