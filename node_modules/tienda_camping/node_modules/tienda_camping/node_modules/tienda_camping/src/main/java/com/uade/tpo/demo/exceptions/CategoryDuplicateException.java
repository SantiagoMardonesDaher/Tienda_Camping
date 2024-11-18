package com.uade.tpo.demo.exceptions;


public class CategoryDuplicateException extends Exception {
    
    public CategoryDuplicateException(String message) {
        super(message);
    }
    public CategoryDuplicateException() {
        super("La categoría ya existe.");
    }
}