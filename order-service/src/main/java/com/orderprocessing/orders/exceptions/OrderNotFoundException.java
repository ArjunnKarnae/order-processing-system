package com.orderprocessing.orders.exceptions;

public class OrderNotFoundException extends RuntimeException{

    private int statusCode;

    public OrderNotFoundException(String message, int statusCode) {
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
