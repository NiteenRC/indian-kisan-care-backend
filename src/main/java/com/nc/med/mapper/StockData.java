package com.nc.med.mapper;

import java.time.LocalDate;
import java.util.Objects;

public class StockData implements Comparable<StockData> {
    private LocalDate date;
    private StockBook stockBook;

    public StockData(LocalDate date, StockBook stockBook) {
        this.date = date;
        this.stockBook = stockBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockData stockData = (StockData) o;
        return date.equals(stockData.date) && stockBook.equals(stockData.stockBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, stockBook);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StockBook getStockBook() {
        return stockBook;
    }

    public void setStockBook(StockBook stockBook) {
        this.stockBook = stockBook;
    }

    @Override
    public int compareTo(StockData s) {
        return s.date.compareTo(this.date);
    }
}
