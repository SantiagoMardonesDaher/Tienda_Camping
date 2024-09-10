package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ImageRepository;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository ProductRepository;
    
 
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getProducts(PageRequest pageable) {
        return ProductRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long ProductId) {
        return ProductRepository.findById(ProductId);
    }


    public Product createProduct(String description, float price, int stock, Long categoryId) 
            throws ProductDuplicateException {

                List<Product> existingProducts = ProductRepository.findByDescription(description);

        if (existingProducts.isEmpty()) {

            if (categoryId == null) {
                throw new IllegalArgumentException("El ID de la categoría no debe ser nulo");
            }
       
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));

   
            Product newProduct = new Product(description, price, stock,category);
            newProduct.setCategory(category);

            return ProductRepository.save(newProduct);
        } else {
            throw new ProductDuplicateException();
        }
    }

    @Override
    public List<Product> getProductByName(String name) {
        return ProductRepository.findByName(name);
    }

    @Override
public List<Product> getProductByCategory(String categoryDescription) {
    List<Category> categories = categoryRepository.findByDescription(categoryDescription);
    
    if (categories.isEmpty()) {
        throw new EntityNotFoundException("Categoría no encontrada con descripción: " + categoryDescription);
    }

    // Asumiendo que solo trabajas con la primera categoría en caso de que haya múltiples coincidencias
    Category category = categories.get(0);
    
    return ProductRepository.findByCategory(category);
}


    @Override
    public List<Product> getProductByPrice(float minPrice, float maxPrice) {
        return ProductRepository.filterByPrice(minPrice, maxPrice);
    }

    @Override
    public Product createProduct(String description, float price, int stock, Long categoryId, Long imageId)
            throws ProductDuplicateException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }
}