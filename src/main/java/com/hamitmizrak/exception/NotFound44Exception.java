package com.hamitmizrak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Sistemdeki 404 hataları yakalamak
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFound44Exception extends RuntimeException{

    public NotFound44Exception(String message) {
        super(message);
    }
}
