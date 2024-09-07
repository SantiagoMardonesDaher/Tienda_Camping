package com.uade.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.OrderItem;
import com.uade.tpo.demo.exceptions.OrderItemDuplicateException;
import com.uade.tpo.demo.repository.OrderItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Page<OrderItem> getOrderItems(PageRequest pageRequest) {
        return orderItemRepository.findAll(pageRequest);
    }

    @Override
    public Optional<OrderItem> getOrderItemById(int orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) throws OrderItemDuplicateException {
        List<OrderItem> existingItems = orderItemRepository.findByOrderId(orderItem.getOrder().getId());
        for (OrderItem existingItem : existingItems) {
            if (existingItem.getProduct().getId().equals(orderItem.getProduct().getId())) {
                throw new OrderItemDuplicateException("El artículo ya existe en la orden.");
            }
        }
        return orderItemRepository.save(orderItem);
    }
}
