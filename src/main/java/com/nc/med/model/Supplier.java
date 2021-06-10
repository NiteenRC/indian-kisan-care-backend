package com.nc.med.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
    private String gstIn;

//	public Set<PurchaseOrder> getPurchaseOrders() {
//		return purchaseOrders;
//	}
//
//	public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
//		this.purchaseOrders = purchaseOrders;
//	}

    public Supplier() {
    }

    public Supplier(String supplierName) {
    	this.supplierName = supplierName;
    }
    
    public Supplier(Long id, String supplierName, Company company, Location location, String phoneNumber) {
        this.id = id;
        this.supplierName = supplierName;
        this.company = company;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

	public String getGstIn() {
		return gstIn;
	}

	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}
}
