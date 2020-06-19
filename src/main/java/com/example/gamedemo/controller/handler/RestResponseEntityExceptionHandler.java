package com.example.gamedemo.controller.handler;

import com.example.gamedemo.exception.ConflictException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", new java.util.Date());
        body.put("status", status.value());

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        body.put("errors", errors);
        return new ResponseEntity<>(status);

    }

    @ExceptionHandler(value = java.lang.Throwable.class)
    public ResponseEntity<Object> handleThrowable(Throwable ex) {
        logger.error("Error", ex);


        return new ResponseEntity<>("Internal Server Error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Object> handleConflictException(Throwable ex) {
        logger.error("Error", ex);


        return new ResponseEntity<>(new HashMap<String, String>() {{
            put("error", ex.getMessage());
        }}, HttpStatus.CONFLICT);
    }
}
