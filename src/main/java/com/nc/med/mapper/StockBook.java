package com.nc.med.mapper;

public class StockBook implements Comparable<StockBook> {
    private String date;
    private String productName;
    private int openingStock;
    private int closingStock;
    private int soldStock;
    private double openingBalance;
    private double profit;
    private double closingBalance;

    public StockBook(String productName, int openingStock, int soldStock, int closingStock, double openingBalance, double closingBalance) {
        super();
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.productName = productName;
        this.soldStock = soldStock;
        this.openingStock = openingStock;
        this.closingStock = closingStock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(int openingStock) {
        this.openingStock = openingStock;
    }

    public int getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(int closingStock) {
        this.closingStock = closingStock;
    }

    public int getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(int soldStock) {
        this.soldStock = soldStock;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public int compareTo(StockBook o) {
        return o.date.compareTo(this.date);
    }
}
