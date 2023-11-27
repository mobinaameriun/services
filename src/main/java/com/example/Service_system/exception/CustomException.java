package com.example.Service_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class CustomException  extends NullPointerException {
    public CustomException(String massage){
        super(massage);
    }

}
