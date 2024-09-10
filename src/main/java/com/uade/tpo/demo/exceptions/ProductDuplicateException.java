package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El producto que se intenta agregar esta duplicada")
public class ProductDuplicateException extends Exception {


    public ProductDuplicateException() {
        super();
    }

    public ProductDuplicateException(String message) {
        super(message);
    }
}

