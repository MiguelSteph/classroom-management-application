package com.classmanagement.resourceserver.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String createdBy;
    private String password;
    private boolean isEnabled;
    private boolean isEmailVerified;
    private boolean isDefaultPwd;
}
