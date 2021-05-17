package com.nc.med.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nc.med.mapper.OrderStatus;

@Entity
public class SalesOrder extends BaseEntity<String> implements Serializable {
	private static final long serialVersionUID = -1000219078147252957L;
	@Id
	@GeneratedValue
	private int salesOrderID;
	@Column(name = "totalPrice", nullable = false)
	private double totalPrice;
	private double amountPaid;
	private double currentBalance;
	private double previousBalance;
	private int totalQty;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy = "salesOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<SalesOrderDetail> salesOrderDetail;
	private String vehicleNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;
	private String comment;

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

	public int getSalesOrderID() {
		return salesOrderID;
	}

	public void setSalesOrderID(int salesOrderID) {
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
}
