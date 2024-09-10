package com.uade.tpo.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;

public interface ProductService {
        public Page<Product> getProducts(PageRequest pageRequest);

        public Optional<Product> getProductById(Long ProductId);

        public Product createProduct(ProductRequest productRequest)
                        throws ProductDuplicateException;

        public List<Product> getProductByCategory(String category)
                        throws CategoryNotFoundException;

        public List<Product> getProductByName(String description);

        public List<Product> getProductByPrice(float max, float min);
}