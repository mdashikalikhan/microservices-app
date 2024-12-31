package com.gateway.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex){
        return buildErrorResponse("Invalid Request",
                HttpStatus.BAD_REQUEST, "Request body is missing or invalid");
    }

    /**
     * Handle validations (e.g. from @Valid annotation of request body)
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidations(MethodArgumentNotValidException ex){

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage() + "[" + f.getRejectedValue() + "]")
                .collect(Collectors.joining(", "));
        return buildErrorResponse("Validation error(s)",
                HttpStatus.INTERNAL_SERVER_ERROR, errors);

    }

    /**
     * Handle unsupported HTTP media type
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedMedia(HttpMediaTypeNotSupportedException ex){
        return buildErrorResponse("Unsupported content type. Please send application/json.",
                HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
    }

    /**
     * Handle unsupported HTTP methods
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedHttpMethod(HttpRequestMethodNotSupportedException ex){
        return buildErrorResponse("The requested HTTP method is not supported for this URL.",
                HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    /**
     * Handle invalid end points
     * @param ex
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedEndPoint(NoHandlerFoundException ex){
        return buildErrorResponse("The requested end point is not found",
                HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handle Record or element not found exception
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleElementNotFoundException(Exception ex){
        return buildErrorResponse("Record is not available.",
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Handle unexpected errors.
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex){
        return buildErrorResponse("An unexpected error occurs.",
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            String reason, HttpStatus status, String problem
    ) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("reason", reason);
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("problem", problem);

        return new ResponseEntity<>(errorResponse, status);
    }
}
