package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private int id;
    private String description;
    private float price;
    private int stock;
    private Long categoryId;  
}
