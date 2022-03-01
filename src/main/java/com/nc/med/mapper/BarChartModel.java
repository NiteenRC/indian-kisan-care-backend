package com.nc.med.mapper;

public class BarChartModel implements Comparable<BarChartModel> {
    private String createdDate;
    private Double totalPrice;
    private int dueAmount;
    private double totalProfit;
    private double dueCollection;

    public BarChartModel(String createdDate, Double totalPrice, int dueAmount, double totalProfit) {
        super();
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
        this.dueAmount = dueAmount;
        this.totalProfit = totalProfit;
    }

    public BarChartModel(String createdDate, Double totalPrice, int dueAmount, double totalProfit, double dueCollection) {
        super();
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
        this.dueAmount = dueAmount;
        this.totalProfit = totalProfit;
        this.dueCollection = dueCollection;
    }

    public BarChartModel(String createdDate, Double dueCollection) {
        super();
        this.createdDate = createdDate;
        this.dueCollection = dueCollection;
    }

    public BarChartModel(String createdDate, int dueAmount) {
        super();
        this.createdDate = createdDate;
        this.dueAmount = dueAmount;
    }

    public Double getDueCollection() {
        return dueCollection;
    }

    public void setDueCollection(double dueCollection) {
        this.dueCollection = dueCollection;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
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
            return other.totalPrice == null;
        } else return totalPrice.equals(other.totalPrice);
    }

    @Override
    public String toString() {
        return "BarChartModel [createdDate=" + createdDate + ", totalPrice=" + totalPrice + "]";
    }

    @Override
    public int compareTo(BarChartModel o) {
        return o.getCreatedDate().compareTo(this.getCreatedDate());
    }
}
