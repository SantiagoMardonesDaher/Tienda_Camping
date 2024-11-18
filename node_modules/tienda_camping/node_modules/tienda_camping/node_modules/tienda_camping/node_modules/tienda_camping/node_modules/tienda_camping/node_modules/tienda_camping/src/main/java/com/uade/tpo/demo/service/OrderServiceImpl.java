package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.User;


import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


    @Override
    public Order saveOrder(Order order) {
        if (order.getDiscountPercentage() != null) {
            float discountAmount = order.getFinalPrice() * (order.getDiscountPercentage() / 100);
            order.setFinalPriceWithDiscount(order.getFinalPrice() - discountAmount);
        } else {
            order.setFinalPriceWithDiscount(order.getFinalPrice());
        }
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
        
}
