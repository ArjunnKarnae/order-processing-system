package com.orderprocessing.inventory.exceptions;

import com.orderprocessing.inventory.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler(exception = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException, WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(productNotFoundException.getMessage());
        errorResponseDTO.setStatus("ERROR");
        errorResponseDTO.setStatusCode(productNotFoundException.getStatusCode());
        errorResponseDTO.setUrl(webRequest.getDescription(false).replace("uri", ""));
        return ResponseEntity.ok(errorResponseDTO);
    }

}
