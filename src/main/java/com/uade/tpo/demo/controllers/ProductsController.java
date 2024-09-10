package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.service.ProductService;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductService ProductService;

    @GetMapping
    public ResponseEntity<Page<Product>> getCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(ProductService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(ProductService.getProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/{ProductId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long ProductId) {
        Optional<Product> result = ProductService.getProductById(ProductId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest ProductRequest)
            throws ProductDuplicateException {
                Product result = ProductService.createProduct(
                    ProductRequest.getDescription(),
                    ProductRequest.getPrice(),
                    ProductRequest.getStock(),
                    ProductRequest.getCategoryId(),
                    ProductRequest.getImageId() 
            );
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }
}
