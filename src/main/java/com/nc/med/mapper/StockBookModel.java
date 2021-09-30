package com.nc.med.mapper;

public class StockBookModel implements Comparable<StockBookModel> {
    private String date;
    private String productName;
    private Integer soldStock;
    private double price;
    private double profit;

    public StockBookModel(String date, String productName, Integer soldStock) {
        this.date = date;
        this.productName = productName;
        this.soldStock = soldStock;
    }

    public StockBookModel(String date, String productName, Integer soldStock, double price, double profit) {
        this.date = date;
        this.productName = productName;
        this.soldStock = soldStock;
        this.price = price;
        this.profit = profit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(Integer soldStock) {
        this.soldStock = soldStock;
    }

    @Override
    public int compareTo(StockBookModel o) {
        return o.date.compareTo(this.date);
    }
}
