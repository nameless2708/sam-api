package com.sam.api.exception;

import org.springframework.http.HttpStatus;

public class SamNotFoundException extends SamException {

    public SamNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, null);
    }
}
