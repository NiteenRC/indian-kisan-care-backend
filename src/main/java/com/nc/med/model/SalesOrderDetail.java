package com.nc.med.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SalesOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer salesOrderDetailID;
	@ManyToOne // (fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "salesOrderID")
	@JsonIgnore
	private SalesOrder salesOrder;
	@ManyToOne
	@JoinColumn(name = "productID") // , insertable = false, updatable = false
	private Product product;
	private Integer qtyOrdered;
	private double salesPrice;
	private double purchasePrice;
	private double profit;

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public Integer getSalesOrderDetailID() {
		return salesOrderDetailID;
	}

	public void setSalesOrderDetailID(Integer salesOrderDetailID) {
		this.salesOrderDetailID = salesOrderDetailID;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Integer qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
}