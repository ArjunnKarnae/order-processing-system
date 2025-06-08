package com.orderprocessing.inventory.dto;

public class ErrorResponseDTO {

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String message, String url, int statusCode, String status) {
        this.message = message;
        this.url = url;
        this.statusCode = statusCode;
        this.status = status;
    }

    private String message;
    private String url;
    private int statusCode;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
