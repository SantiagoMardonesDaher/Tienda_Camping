package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ImageRepository;
import com.uade.tpo.demo.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository ProductRepository;
    
    @Autowired
    private CategoryRepository CategoryRepository; 

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Page<Product> getProducts(PageRequest pageable) {
        return ProductRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long ProductId) {
        return ProductRepository.findById(ProductId);
    }


    public Product createProduct(String description, float price, int stock, Long categoryId, Long imageId) 
            throws ProductDuplicateException {

                List<Product> existingProducts = ProductRepository.findByDescription(description);

        if (existingProducts.isEmpty()) {

            
            if (categoryId == null) {
                throw new IllegalArgumentException("El ID de la categoría no debe ser nulo");
            }
            // Buscar la categoría por su ID
            Category category = CategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));

            // Crear el nuevo producto con la categoría asociada
            Product newProduct = new Product(description, price, stock);
            newProduct.setCategory(category);

            if (imageId != null) {
                        Image image = imageRepository.findById(imageId)
                            .orElseThrow(() -> new EntityNotFoundException("Imagen no encontrada con ID: " + imageId));
                        newProduct.setImage(image);
                    }


            // Guardar el producto
            return ProductRepository.save(newProduct);
        } else {
            throw new ProductDuplicateException();
        }
    }

}
