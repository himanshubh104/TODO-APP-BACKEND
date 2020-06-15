package com.himansh.controllers;

import com.himansh.dto.ErrorMessage;
import com.himansh.exceptions.TodoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex){
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TodoException.class)
    public ResponseEntity<ErrorMessage> handleException(TodoException ex){
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), HttpStatus.OK.value()), HttpStatus.OK);
    }

    //Handler for validation failures w.r.t DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String error=ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ErrorMessage(error, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
