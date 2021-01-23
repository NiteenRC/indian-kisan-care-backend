package com.nc.med.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Supplier implements Serializable {
	private static final long serialVersionUID = -1000119078147252957L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String supplierName;
	@ManyToOne
	private Company company;
	@ManyToOne
	private Location location;
	private String phoneNumber;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
	private Set<PurchaseOrder> purchaseOrders;

//	public Set<PurchaseOrder> getPurchaseOrders() {
//		return purchaseOrders;
//	}
//
//	public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
//		this.purchaseOrders = purchaseOrders;
//	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Supplier() {
	}

	public Supplier(Long id, String supplierName, Company company, Location location, String phoneNumber) {
		this.id = id;
		this.supplierName = supplierName;
		this.company = company;
		this.location = location;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}