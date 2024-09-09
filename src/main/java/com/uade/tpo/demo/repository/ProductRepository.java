package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Filtrado de productos por nombre
    @Query(value = "select c from Product c where c.description = ?1")
    List<Product> findByDescription(java.lang.String description);

    // Filtrado de productos por minimo de precio
    @Query(value = "select c from Product c where c.price <= ?1 and c.price >= ?2")
    List<Product> filterByPrice(Float max, float min);

    // Filtrado de productos categoria
    @Query(value = "select c from Product c where c.category = ?1")
    List<Product> findByCategory(Category Category);
}