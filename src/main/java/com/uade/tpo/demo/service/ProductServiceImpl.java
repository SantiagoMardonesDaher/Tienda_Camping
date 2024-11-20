package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ImageRepository;
import com.uade.tpo.demo.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

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

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Implementación de createProduct con categoryId e imageId
    @Override
    public Product createProduct(String description, float price, int stock, Long categoryId, Long imageId)
            throws ProductDuplicateException {

        List<Product> existingProducts = productRepository.findByDescription(description);

        if (!existingProducts.isEmpty()) {
            throw new ProductDuplicateException("El producto con esa descripción ya existe.");
        }

        // Verificar que categoryId no sea nulo y buscar la categoría
        if (categoryId == null) {
            throw new IllegalArgumentException("El ID de la categoría no debe ser nulo");
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));

        // Buscar la imagen por ID, si se proporciona
        Image image = null;
        if (imageId != null) {
            image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new EntityNotFoundException("Imagen no encontrada con ID: " + imageId));
        }

        // Crear el nuevo producto y asociarlo con la categoría e imagen
        Product newProduct = new Product(description, price, stock, category);
        newProduct.setCategory(category);
        newProduct.setImage(image);

        // Guardar el producto en la base de datos
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

        // Trabajar con la primera categoría encontrada
        Category category = categories.get(0);
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductByPrice(float minPrice, float maxPrice) {
        return productRepository.filterByPrice(minPrice, maxPrice);
    }

    @Override
    public Optional<Product> editProduct(Long productId, String description, Float price, Integer stock,
            Long categoryId,
            Long imageId) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));
        }

        Image image = null;
        if (imageId != null) {
            image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new EntityNotFoundException("Imagen no encontrada con ID: " + imageId));
        }

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (description != null)
                product.setDescription(description);
            if (price != null)
                product.setPrice(price);
            if (stock != null)
                product.setStock(stock);    
            if (categoryId != null)
                product.setCategory(category);
            if (imageId != null)
                product.setImage(image);

            productRepository.save(product);

            return Optional.of(product);
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteProductById(Long productId) {
        Optional<Product> aux = productRepository.findById(productId);
        if (aux != null) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

}