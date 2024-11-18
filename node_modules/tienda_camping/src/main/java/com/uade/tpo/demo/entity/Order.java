package com.uade.tpo.demo.entity;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long count;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "finalPrice", nullable = false)
    private float finalPrice;

    @Column(name = "discountPercentage") 
    private Float discountPercentage;

    @Column(name = "finalPriceWithDiscount") 
    private Float finalPriceWithDiscount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relación con OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;
}
