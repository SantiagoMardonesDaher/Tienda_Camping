package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order saveOrder(Order order);
    void deleteOrder(Long id);
}
