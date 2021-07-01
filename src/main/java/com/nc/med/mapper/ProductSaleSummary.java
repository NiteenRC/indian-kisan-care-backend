package com.nc.med.mapper;

public class ProductSaleSummary implements Comparable<ProductSaleSummary> {
	private String productName;
	private int qtySold;
	private Double totalPrice;

	public ProductSaleSummary(String productName, int qtySold, double totalPrice) {
		super();
		this.productName = productName;
		this.qtySold = qtySold;
		this.totalPrice = totalPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQtySold() {
		return qtySold;
	}

	public void setQtySold(int qtySold) {
		this.qtySold = qtySold;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int compareTo(ProductSaleSummary ps) {
		return ps.totalPrice.compareTo(this.totalPrice);
	}
}
