package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.ExceptionResponse;
import com.classmanagement.resourceserver.exceptions.UserCausedBackendException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserCausedBackendException.class)
    public final ResponseEntity<Object> handleUserCausedBackendException(UserCausedBackendException exception, WebRequest request) {
        ExceptionResponse expRes = new ExceptionResponse();
        expRes.setExceptionDate(LocalDate.now());
        expRes.setMessage(exception.getMessage());
        expRes.setDetails(exception.getCause() == null ? "" : exception.getCause().toString());
        return new ResponseEntity<>(expRes, HttpStatus.BAD_REQUEST);
    }
}
