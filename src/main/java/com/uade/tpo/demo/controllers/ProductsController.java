package com.uade.tpo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired; // Import necesario
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.FilterProductRequest;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.service.ProductService;

import java.net.URI;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired // Inyección de dependencias
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/filterByPrice")
    public ResponseEntity<List<Product>> getMethodName(@RequestBody FilterProductRequest filterProductRequest) {
        List<Product> products = productService.getProductByPrice(filterProductRequest.getMaxPrice(),
                filterProductRequest.getMinPrice());
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{ProductId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long ProductId) {
        Optional<Product> result = productService.getProductById(ProductId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{Category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("Category") String category) {
        List<Product> products = productService.getProductByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{Description}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable("Description") String description) {
        List<Product> products = productService.getProductByName(description);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest)
            throws ProductDuplicateException {
        Product result = productService.createProduct(
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getStock(),
                productRequest.getCategoryId(),
                productRequest.getImageId()
        );
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }

    // Método PUT para actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Optional<Product> existingProduct = productService.getProductById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setStock(productRequest.getStock());

            // Actualización de relaciones opcionales
            if (productRequest.getCategoryId() != null) {
                productService.updateProductCategory(product, productRequest.getCategoryId());
            }
            if (productRequest.getImageId() != null) {
                productService.updateProductImage(product, productRequest.getImageId());
            }

            Product updatedProduct = productService.saveProduct(product);
            return ResponseEntity.ok(updatedProduct);
        }

        return ResponseEntity.notFound().build();
    }
}
