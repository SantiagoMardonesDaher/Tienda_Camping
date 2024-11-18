package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class FilterProductRequest {
    private float maxPrice = 999999999;
    private float minPrice = -1;
}
