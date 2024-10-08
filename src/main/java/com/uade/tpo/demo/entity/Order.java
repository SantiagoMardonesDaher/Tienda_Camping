package com.uade.tpo.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)    
    private User user; 
}
