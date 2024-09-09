package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Product {

    public Product() {
    }

    public Product(String description, Float price, int stock, Category category) {
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}
