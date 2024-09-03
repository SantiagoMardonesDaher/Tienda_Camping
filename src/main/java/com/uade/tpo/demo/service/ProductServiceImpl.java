package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    @Override
    public Page<Product> getProducts(PageRequest pageable) {
        return ProductRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long ProductId) {
        return ProductRepository.findById(ProductId);
    }


    public Product createProduct(String description, float price, int stock, String order)
            throws ProductDuplicateException {

        List<Product> categories = ProductRepository.findByDescription(description);
        if (categories.isEmpty())
            return ProductRepository.save(new Product(description, price, stock));
        throw new ProductDuplicateException();
    }

}
