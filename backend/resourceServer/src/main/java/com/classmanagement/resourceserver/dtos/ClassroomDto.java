package com.classmanagement.resourceserver.dtos;

import lombok.Data;

@Data
public class ClassroomDto {
    private Integer id;
    private String code;
    private String name;
    private boolean isEnabled;
}
