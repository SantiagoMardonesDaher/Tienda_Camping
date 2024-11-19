package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.UserRepository;
import com.uade.tpo.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository; // Agregar dependencia para obtener el usuario

@GetMapping
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Solo para administradores
public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    return new ResponseEntity<>(orders, HttpStatus.OK);
}


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
public ResponseEntity<Order> createOrder(
        @RequestBody Order order, 
        @RequestParam(required = false) Float discountPercentage, 
        Principal principal) {
    // Obtener el email del usuario autenticado desde el token JWT
    String userEmail = principal.getName();

    // Buscar el usuario en la base de datos
    User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Asignar el usuario a la orden
    order.setUser(user);

    // Asignar el porcentaje de descuento
    if (discountPercentage != null) {
        order.setDiscountPercentage(discountPercentage);

        // Calcular precio final con descuento
        float discountAmount = order.getFinalPrice() * (discountPercentage / 100);
        order.setFinalPriceWithDiscount(order.getFinalPrice() - discountAmount);
    } else {
        order.setFinalPriceWithDiscount(order.getFinalPrice());
    }

    // Guardar la nueva orden
    Order newOrder = orderService.saveOrder(order);
    return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
}


    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            order.setCount(orderDetails.getCount());
            order.setDate(orderDetails.getDate());
            order.setFinalPrice(orderDetails.getFinalPrice());
            order.setUser(orderDetails.getUser());
            Order updatedOrder = orderService.saveOrder(order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/my-orders")
public ResponseEntity<List<Order>> getOrdersByUser(Principal principal) {
    String userEmail = principal.getName(); // Obtener el email del usuario autenticado
    User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    List<Order> userOrders = orderService.getOrdersByUser(user);
    return new ResponseEntity<>(userOrders, HttpStatus.OK);
}




}
