package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;

import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Product {

    public Product(String description, Float price, int stock) {

    public Product() {
    }

    public Product(String descpription, Float price, int stock) {

    public Product(String description, Float price, int stock) {

        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String description;

    @Column()
    private Float price;

    @Column
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToOne
    private ProductOrder productOrder;

    // @OneToMany(mappedBy = "product")
    // private OrderItems OrderItems;

    @OneToMany(mappedBy = "product")
    private OrderItem OrderItem;

    @OneToMany
    private Order order;


}
