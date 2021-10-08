package com.nc.med.mapper;

import com.nc.med.model.Customer;

public class CustomerBalance {
    private Customer customer;
    private Double balance;

    public CustomerBalance(Customer customer, Double balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
