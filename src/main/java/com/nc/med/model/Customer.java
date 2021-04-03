package com.nc.med.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Customer implements Serializable {
    private static final long serialVersionUID = -1000119078147252957L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    @ManyToOne
    private Location location;
    private String phoneNumber;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<SalesOrder> salesOrders;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Customer() {
    }

    public Customer(Long id, String customerName, Location location, String phoneNumber) {
        this.id = id;
        this.customerName = customerName;
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
