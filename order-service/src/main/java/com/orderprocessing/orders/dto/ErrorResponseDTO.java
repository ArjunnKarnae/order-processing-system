package com.orderprocessing.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema(name = "ErrorResponse", description = "Error Response DTO")
public class ErrorResponseDTO {

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String status, String message, int statusCode, String url) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
        this.url = url;
    }

    @Schema(name = "status", description = "Error Response Status", example = "success | Failure")
    private String status;
    @Schema(name = "message", description = "Error Response Message")
    private String message;

    @Schema(name = "statuscode", description = "Error Response Status code", example = "404|500")
    private int statusCode;

    @Schema(name = "url", description = "Error Response Url")
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
