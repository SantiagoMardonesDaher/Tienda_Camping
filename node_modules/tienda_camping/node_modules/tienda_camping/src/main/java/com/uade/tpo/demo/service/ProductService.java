package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;

public interface ProductService {

    Page<Product> getProducts(PageRequest pageable);

    Optional<Product> getProductById(Long productId);

    Product createProduct(String description, float price, int stock, Long categoryId, Long imageId) throws ProductDuplicateException;

    List<Product> getProductByName(String name);

    List<Product> getProductByCategory(String category);

    List<Product> getProductByPrice(float minPrice, float maxPrice);

    Product saveProduct(Product product); // Método para guardar o actualizar productos

    void updateProductCategory(Product product, Long categoryId); // Método para actualizar categoría

    void updateProductImage(Product product, Long imageId); // Método para actualizar imagen
}
