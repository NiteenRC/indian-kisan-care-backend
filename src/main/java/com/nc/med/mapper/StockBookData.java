package com.nc.med.mapper;

import java.util.List;

public class StockBookData {
    private int totalQtySold;
    private double totalPrice;
    private double totalProfit;
    private List<StockData> stockData;
    private double totalTransaction;

    public StockBookData(List<StockData> stockData, int totalQtySold, double totalPrice, double totalProfit, double totalTransaction) {
        this.totalQtySold = totalQtySold;
        this.totalPrice = totalPrice;
        this.totalProfit = totalProfit;
        this.stockData = stockData;
        this.totalTransaction = totalTransaction;
    }

    public double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getTotalQtySold() {
        return totalQtySold;
    }

    public void setTotalQtySold(int totalQtySold) {
        this.totalQtySold = totalQtySold;
    }

    public List<StockData> getStockData() {
        return stockData;
    }

    public void setStockData(List<StockData> stockData) {
        this.stockData = stockData;
    }
}
