package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;

import java.util.Collection;

public interface OrderService extends CRUDService<Order> {

    Order updateOrderStatus(Long id);

    OrderStatus getOrderStatus(Long id);

    Collection<Order> getOrdersByStatus(OrderStatus status);
}
