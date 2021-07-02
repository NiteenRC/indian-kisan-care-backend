package com.nc.med.mapper;

import java.util.List;

public class StockBookSummary {
	private double totalProfit;
	private double totalQtySold;
	private List<StockBook> stockBooks;
	
	public StockBookSummary(double totalProfit, double totalQtySold, List<StockBook> stockBooks) {
		super();
		this.totalProfit = totalProfit;
		this.totalQtySold = totalQtySold;
		this.stockBooks = stockBooks;
	}

	public double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public double getTotalQtySold() {
		return totalQtySold;
	}

	public void setTotalQtySold(double totalQtySold) {
		this.totalQtySold = totalQtySold;
	}

	public List<StockBook> getStockBooks() {
		return stockBooks;
	}

	public void setStockBooks(List<StockBook> stockBooks) {
		this.stockBooks = stockBooks;
	}
}
