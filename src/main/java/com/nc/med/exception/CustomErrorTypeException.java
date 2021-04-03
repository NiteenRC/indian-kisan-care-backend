package com.nc.med.exception;


public class CustomErrorTypeException {

    private String errorMessage;

    public CustomErrorTypeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
