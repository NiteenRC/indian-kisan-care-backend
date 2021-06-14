package com.nc.med.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
// @Table(name = "Products")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {
	private static final long serialVersionUID = -1000119078147252957L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "PRODUCT_ID")
	private Long id;
	@Column(length = 255, nullable = false)
	private String productName;
	@Column(name = "Price", nullable = false)
	private double price;
	private double currentPrice;
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "CATEGORY_ID")
	// @JsonIgnore
	@NotNull(message = "Name may not be null")
	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "CATEGORY_ID")
	private Category category;
	private String productDesc;
	private int qty;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	private int gst;
	private String hsnNo;

	public Product() {
	}

	public Product(Date createdDate, double price, String productDesc, String productName, int qty, Category category) {
		super();
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.productDesc = productDesc;
		this.qty = qty;
		this.createdDate = createdDate;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 *
	 * @JoinColumn(name = "CART_ID", foreignKey = @ForeignKey(name =
	 * "PRODUCT__CART_FK"))
	 *
	 * @JsonIgnore private Cart cart;
	 *
	 * //bi-directional many-to-one association to ProductImage
	 *
	 * @OneToMany(mappedBy = "product") private List<ProductImage> productImages;
	 */

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getGst() {
		return gst;
	}

	public void setGst(int gst) {
		this.gst = gst;
	}

	public String getHsnNo() {
		return hsnNo;
	}

	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
	}
}
