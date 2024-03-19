package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Collection<Order> findAllByOrderStatus(OrderStatus status);

    Optional<Order> findFirstByOrderStatusOrderByTimestampAsc(OrderStatus orderStatus);

    Optional<Order> findFirstByTimestampBeforeOrderByTimestampDesc(LocalDateTime timestamp);
}
