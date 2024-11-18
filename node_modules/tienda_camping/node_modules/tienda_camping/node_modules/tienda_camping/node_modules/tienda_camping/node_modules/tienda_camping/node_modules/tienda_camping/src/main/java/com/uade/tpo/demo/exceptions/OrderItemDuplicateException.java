package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El artículo de la orden que se intenta agregar está duplicado")
public class OrderItemDuplicateException extends Exception {
    public OrderItemDuplicateException(String message) {
        super(message);
    }
}
