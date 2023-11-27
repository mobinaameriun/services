package com.example.Service_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = " فیلد تکراری ")
public class DuplicateException extends RuntimeException{

    public DuplicateException(String message) {
        super(message);
    }
}