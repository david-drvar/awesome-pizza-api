package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Collection<Order> findAllByOrderStatus(OrderStatus status);

    //first you have to sort it, then find first
//    @Query("SELECT o FROM (SELECT o FROM Order o ORDER BY o.timestamp ASC) WHERE o.orderStatus = :orderStatus")
    Optional<Order> findFirstByOrderStatusOrderByTimestampAsc(OrderStatus orderStatus);

    Optional<Order> findFirstByTimestampBeforeOrderByTimestampDesc(LocalDateTime timestamp);


//    @Query("SELECT o FROM Order o WHERE o.orderStatus = :orderStatus ORDER BY o.timestamp ASC")
//    @Query("SELECT o FROM (SELECT o FROM Order o ORDER BY o.timestamp ASC) WHERE o.orderStatus = :orderStatus")
//    Optional<Order> findFirstOrderByOrderStatus(OrderStatus orderStatus);

//    @Query("SELECT o FROM (SELECT b FROM Order b ORDER BY b.timestamp ASC) o WHERE o.timestamp < ?1")
////    @Query("SELECT o FROM Order o WHERE o.timestamp < ?1")
//    Order findPreviousOrder(LocalDateTime timestamp);
}
