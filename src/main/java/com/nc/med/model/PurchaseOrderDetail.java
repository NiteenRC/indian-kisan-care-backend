package com.nc.med.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PurchaseOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue // (strategy = GenerationType.IDENTITY)
	private Integer purchaseOrderDetailID;
	@ManyToOne // (fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "purchaseOrderID")
	@JsonIgnore
	private PurchaseOrder purchaseOrder;
	@ManyToOne
	@JoinColumn(name = "productID") // , insertable = false, updatable = false
	private Product product;
	private Integer qtyOrdered;
	private Integer price;

	public void setPurchaseOrderDetailID(Integer purchaseOrderDetailID) {
		this.purchaseOrderDetailID = purchaseOrderDetailID;
	}

	public Integer getPurchaseOrderDetailID() {
		return purchaseOrderDetailID;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}