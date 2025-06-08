package com.orderprocessing.orders.dto;

public class ErrorResponseDTO {

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String status, String message, int statusCode, String url) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
        this.url = url;
    }

    private String status;
    private String message;
    private int statusCode;
    private String url;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
