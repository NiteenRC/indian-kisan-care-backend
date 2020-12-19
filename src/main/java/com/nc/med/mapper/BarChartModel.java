package com.nc.med.mapper;

public class BarChartModel implements Comparable<BarChartModel>{
	private String createdDate;
	private Double totalPrice;

	public BarChartModel(String createdDate, Double totalPrice) {
		super();
		this.createdDate = createdDate;
		this.totalPrice = totalPrice;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BarChartModel other = (BarChartModel) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BarChartModel [createdDate=" + createdDate + ", totalPrice=" + totalPrice + "]";
	}

	@Override
	public int compareTo(BarChartModel o) {
		return this.getCreatedDate().compareTo(o.getCreatedDate());
	}
}
