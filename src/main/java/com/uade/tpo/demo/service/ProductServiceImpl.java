package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getProducts(PageRequest pageable) {
        return ProductRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long ProductId) {
        return ProductRepository.findById(ProductId);
    }

    public Product createProduct(ProductRequest productRequest)
            throws ProductDuplicateException {

        String productcategory = productRequest.getCategory();
        String description = productRequest.getDescription();
        int stock = productRequest.getStock();
        float price = productRequest.getPrice();

        Category category = categoryRepository.findByDescription(productcategory)
                .orElseThrow(() -> new RuntimeException(productcategory));

        List<Product> categories = ProductRepository.findByDescription(description);
        if (categories.isEmpty())
            return ProductRepository.save(new Product(description, price, stock, category));
        throw new ProductDuplicateException();
    }

    @Override
    public List<Product> getProductByCategory(String categoryDescription) throws CategoryNotFoundException {
        Category category = categoryRepository.findByDescription(categoryDescription)
                .orElseThrow(() -> new RuntimeException(categoryDescription));
        return ProductRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductByName(String description) {
        List<Product> products = ProductRepository.findByName(description);
        return products;
    }

    @Override
    public List<Product> getProductByPrice(float max, float min) {
        return ProductRepository.filterByPrice(max, min);
    }

}
