package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;

import java.util.Collection;
import java.util.Optional;

public interface OrderService {

    OrderDTO save(OrderDTO entity);

    Collection<Order> read();

    Optional<Order> read(Long id);

    void delete(Long id);

    boolean existsById(Long id);

    OrderStatus getOrderStatus(Long id);

    Order getNextOrder();

    Order startOrder();

    Order finishOrder();

}
