package com.classmanagement.resourceserver.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExceptionResponse {
    private LocalDate exceptionDate;
    private String message;
    private String details;
}
