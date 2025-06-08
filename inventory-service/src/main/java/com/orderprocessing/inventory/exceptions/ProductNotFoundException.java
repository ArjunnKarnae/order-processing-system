package com.orderprocessing.inventory.exceptions;

public class ProductNotFoundException extends RuntimeException{

    private int statusCode;

    public ProductNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
