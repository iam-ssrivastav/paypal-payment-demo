package com.shivamsrivastav.payment.controller;

import com.shivamsrivastav.payment.dto.request.CreateOrderRequest;
import com.shivamsrivastav.payment.entity.Order;
import com.shivamsrivastav.payment.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order Controller
 * 
 * REST API for managing internal orders.
 * 
 * @author Shivam Srivastav
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("API: Create order request: {}", request.getDescription());
        Order order = orderService.createOrder(
                request.getDescription(),
                request.getAmount(),
                request.getCustomerEmail(),
                request.getCustomerName());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
