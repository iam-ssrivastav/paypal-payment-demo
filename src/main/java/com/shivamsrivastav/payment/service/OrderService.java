package com.shivamsrivastav.payment.service;

import com.shivamsrivastav.payment.entity.Order;
import com.shivamsrivastav.payment.entity.enums.OrderStatus;
import com.shivamsrivastav.payment.exception.PaymentException;
import com.shivamsrivastav.payment.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Order Service
 * 
 * Manages customer orders.
 * 
 * @author Shivam Srivastav
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Create a new order.
     */
    @Transactional
    public Order createOrder(String description, BigDecimal amount, String customerEmail, String customerName) {
        log.info("Creating order: description={}, amount={}", description, amount);

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .description(description)
                .total(amount)
                .subtotal(amount) // Simplified for demo
                .tax(BigDecimal.ZERO)
                .shipping(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .customerEmail(customerEmail)
                .customerName(customerName)
                .build();

        return orderRepository.save(order);
    }

    /**
     * Get order by ID.
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new PaymentException("Order not found: " + id));
    }

    /**
     * Get order by order number.
     */
    public Order getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new PaymentException("Order not found: " + orderNumber));
    }

    /**
     * Get all orders.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Update order status.
     */
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
