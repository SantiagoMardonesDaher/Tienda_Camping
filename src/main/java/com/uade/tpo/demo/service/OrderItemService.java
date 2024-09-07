package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.OrderItem;
import com.uade.tpo.demo.exceptions.OrderItemDuplicateException;

public interface OrderItemService {
    public Page<OrderItem> getOrderItems(PageRequest pageRequest);

    public Optional<OrderItem> getOrderItemById(int orderItemId);

    public OrderItem createOrderItem(OrderItem orderItem) throws OrderItemDuplicateException;
}
