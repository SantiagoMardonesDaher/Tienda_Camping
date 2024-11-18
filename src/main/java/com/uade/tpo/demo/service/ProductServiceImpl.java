package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ImageRepository;
import com.uade.tpo.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createProduct(String description, float price, int stock, Long categoryId, Long imageId) 
            throws ProductDuplicateException {
        if (!productRepository.findByDescription(description).isEmpty()) {
            throw new ProductDuplicateException("El producto con esa descripción ya existe.");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));

        Image image = null;
        if (imageId != null) {
            image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new EntityNotFoundException("Imagen no encontrada con ID: " + imageId));
        }

        Product newProduct = new Product(description, price, stock, category);
        newProduct.setCategory(category);
        newProduct.setImage(image);

        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByCategory(String categoryDescription) {
        List<Category> categories = categoryRepository.findByDescription(categoryDescription);
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Categoría no encontrada con descripción: " + categoryDescription);
        }

        Category category = categories.get(0);
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductByPrice(float minPrice, float maxPrice) {
        return productRepository.filterByPrice(minPrice, maxPrice);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void updateProductCategory(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));
        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public void updateProductImage(Product product, Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Imagen no encontrada con ID: " + imageId));
        product.setImage(image);
        productRepository.save(product);
    }
}
