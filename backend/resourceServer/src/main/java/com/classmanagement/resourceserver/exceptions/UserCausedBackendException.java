package com.classmanagement.resourceserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCausedBackendException extends RuntimeException{
    public UserCausedBackendException(String message) {
        super(message);
    }

    public UserCausedBackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
