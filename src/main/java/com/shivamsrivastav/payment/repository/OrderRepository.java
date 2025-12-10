package com.shivamsrivastav.payment.repository;

import com.shivamsrivastav.payment.entity.Order;
import com.shivamsrivastav.payment.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Order Repository
 * 
 * @author Shivam Srivastav
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerEmail(String customerEmail);

    boolean existsByOrderNumber(String orderNumber);
}
