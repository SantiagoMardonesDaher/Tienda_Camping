package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product WHERE o.user = :user")
    List<Order> findByUser(User user); // Cargar las órdenes junto con los ítems y productos
}
