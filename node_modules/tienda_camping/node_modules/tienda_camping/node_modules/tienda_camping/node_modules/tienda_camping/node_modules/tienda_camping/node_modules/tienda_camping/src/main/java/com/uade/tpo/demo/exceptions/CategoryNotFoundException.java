package com.uade.tpo.demo.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Category not found ";

    public CategoryNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}