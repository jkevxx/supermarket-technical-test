package com.example.supermarket.techtest.exception;

import com.example.supermarket.techtest.dto.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        404,
                        ex.getMessage(),
                        null
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        null
                ));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Validation failed",
                        errors
                ));
    }

    @ExceptionHandler(TypeMismatchValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleTypeMismatchValidationException(
            TypeMismatchValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Validation failed",
                        ex.getErrors()
                ));
    }

    /*@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        Map<String, String > errors = new HashMap<>();

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            @SuppressWarnings("unckecked")
            Map<String, String> deserializationErrors = (Map<String, String>) attrs.getRequest()
                    .getAttribute("deserializationErrors");

            if (deserializationErrors != null && !deserializationErrors.isEmpty()){
                errors.putAll(deserializationErrors);
            }
        }

        //String message = ex.getMessage();

        if(errors.isEmpty()){
            if (ex.getCause() instanceof InvalidFormatException){
                InvalidFormatException ifx = (InvalidFormatException) ex.getCause();

                String fieldName = ifx.getPath().get(ifx.getPath().size() - 1).getFieldName();
                String targetType = ifx.getTargetType().getSimpleName();
                errors.put(fieldName, fieldName + " must be a valid " + targetType);
            } else {
                errors.put("error", "Invalid request format");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Validation failed",
                        errors
                ));
    }*/

    /*@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException){
            invalidFormatException.getPath().forEach(ref -> {
                String fieldName = ref.getFieldName();
                String expectedType = invalidFormatException.getTargetType().getSimpleName();
                errors.put(fieldName, fieldName + " must be a valid " + expectedType);
            });
        } else {
            errors.put("error", "Malformed JSON request");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ApiResponse<>(
                        400,
                        "Validation failed",
                        errors
                ));
    }*/
}
