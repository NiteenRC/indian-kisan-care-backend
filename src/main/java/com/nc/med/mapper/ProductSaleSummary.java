package com.nc.med.mapper;

public class ProductSaleSummary {
	private String productName;
	private int qtySold;
	private double totalPrice;

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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "ProductSaleSummary [productName=" + productName + ", qtySold=" + qtySold + ", totalPrice=" + totalPrice
				+ "]";
	}
}