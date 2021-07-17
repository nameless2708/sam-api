package com.sam.api.exception;

import org.springframework.http.HttpStatus;

public class SamForbiddenException extends SamException {
    public SamForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN, null);
    }
}
