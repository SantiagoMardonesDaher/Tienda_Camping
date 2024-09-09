package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.OrderItem;
import com.uade.tpo.demo.exceptions.OrderItemDuplicateException;
import com.uade.tpo.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.demo.exceptions.InsufficientStockException;


import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<Page<OrderItem>> getOrderItems(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(orderItemService.getOrderItems(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(orderItemService.getOrderItems(PageRequest.of(page, size)));
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable int orderItemId) {
        Optional<OrderItem> result = orderItemService.getOrderItemById(orderItemId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createOrderItem(@RequestBody OrderItem orderItemRequest) {
        try {
            OrderItem result = orderItemService.createOrderItem(orderItemRequest);
            return ResponseEntity.created(URI.create("/order-items/" + result.getId())).body(result);
        } catch (OrderItemDuplicateException | InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}