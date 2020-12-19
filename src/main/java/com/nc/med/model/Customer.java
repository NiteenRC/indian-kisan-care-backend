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
public class Customer implements Serializable {
	private static final long serialVersionUID = -1000119078147252957L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerID;
	private String customerName;
	@ManyToOne
	private Location location;
	private String phoneNumber;
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<SalesOrder> salesOrders;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Customer() {
	}

	public Customer(int customerID, String customerName, Location location, String phoneNumber) {
		this.customerID = customerID;
		this.customerName = customerName;
		this.location = location;
		this.phoneNumber = phoneNumber;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

//	public void setSalesOrders(Set<SalesOrder> salesOrders) {
//		this.salesOrders = salesOrders;
//	}
//
//	public Set<SalesOrder> getSalesOrders() {
//		return salesOrders;
//	}
}