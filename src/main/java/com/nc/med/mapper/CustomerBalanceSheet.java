package com.nc.med.mapper;

import java.util.Date;

import com.nc.med.model.Customer;

public class CustomerBalanceSheet implements Comparable<CustomerBalanceSheet>{
	private Customer customer;
	private Double currentBalance;
	private Double totalPrice;
	private Double amountPaid;
	private Date billDate;
	private Date dueDate;

	public CustomerBalanceSheet(Customer customer, Double totalPrice, Double amountPaid, Double currentBalance,
			Date billDate, Date dueDate) {
		this.setCustomer(customer);
		this.setCurrentBalance(currentBalance);
		this.setAmountPaid(amountPaid);
		this.totalPrice = totalPrice;
		this.setBillDate(billDate);
		this.setDueDate(dueDate);
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
	public int compareTo(CustomerBalanceSheet o) {
		return o.billDate.compareTo(this.billDate);
	}
}
