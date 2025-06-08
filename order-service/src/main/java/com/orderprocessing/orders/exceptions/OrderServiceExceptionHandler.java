package com.orderprocessing.orders.exceptions;


import com.orderprocessing.orders.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class OrderServiceExceptionHandler {

    @ExceptionHandler(exception = OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException, WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setStatusCode(orderNotFoundException.getStatusCode());
        errorResponseDTO.setStatus("ERROR");
        errorResponseDTO.setUrl(webRequest.getDescription(false).replace("uri=", ""));
        errorResponseDTO.setMessage(orderNotFoundException.getMessage());
        return  ResponseEntity.ok(errorResponseDTO);
    }
}
