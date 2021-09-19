package com.nc.med.util;

public enum OrderStatus {
    P("PLACED"), D("DELIVERED"), C("CANCELLED");

    private final String statusCode;

    OrderStatus(String status) {
        statusCode = status;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
