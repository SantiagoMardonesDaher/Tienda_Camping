package com.uade.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.OrderItem;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.OrderItemDuplicateException;
import com.uade.tpo.demo.repository.OrderItemRepository;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.entity.Product;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

     @Autowired
    private ProductRepository productRepository; 

    @Override
    public Page<OrderItem> getOrderItems(PageRequest pageRequest) {
        return orderItemRepository.findAll(pageRequest);
    }

    @Override
    public Optional<OrderItem> getOrderItemById(int orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) throws OrderItemDuplicateException, InsufficientStockException {
        List<OrderItem> existingItems = orderItemRepository.findByOrderId(orderItem.getOrder().getId());
        
         // Verificar si la cantidad es mayor que 0
    if (orderItem.getQuantity() <= 0) {
        throw new IllegalArgumentException("La cantidad del producto debe ser mayor que 0.");
    }
        // Verifica si ya existe un item similar en la orden
        for (OrderItem existingItem : existingItems) {
            if (existingItem.getProduct().getId().equals(orderItem.getProduct().getId())) {
                throw new OrderItemDuplicateException("El artículo ya existe en la orden.");
            }
        }
    
        Product product = productRepository.findById(orderItem.getProduct().getId())
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
        // Agregar un log para verificar el stock actual y la cantidad solicitada
        System.out.println("Stock actual del producto: " + product.getStock());
        System.out.println("Cantidad solicitada: " + orderItem.getQuantity());
    
        // Verificar si hay suficiente stock
        if (product.getStock() < orderItem.getQuantity()) {
            throw new InsufficientStockException("No hay suficiente stock para el producto: " + product.getDescription());
        }
    
        // Restar la cantidad del stock y actualizar el producto
        product.setStock(product.getStock() - orderItem.getQuantity());
        productRepository.save(product);  // Actualizar el stock del producto.
    
        // Guardar el OrderItem
        return orderItemRepository.save(orderItem);
    }
}    