package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;

public interface ProductService {
    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long ProductId);


    public Product createProduct(String description, float price, int stock, Long categoryId, Long imageId)
            throws ProductDuplicateException;

   
}