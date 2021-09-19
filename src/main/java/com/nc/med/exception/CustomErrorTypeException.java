package com.nc.med.exception;


public class CustomErrorTypeException {

    private final String errorMessage;

    public CustomErrorTypeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
