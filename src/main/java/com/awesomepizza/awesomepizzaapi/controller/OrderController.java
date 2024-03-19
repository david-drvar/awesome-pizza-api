package com.awesomepizza.awesomepizzaapi.controller;

import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface OrderController extends CRUDController<Order>{

    ResponseEntity<OrderStatus> getOrderStatus(Long id);

    ResponseEntity<Collection<Order>> getOrdersByStatus(OrderStatus orderStatus);

    ResponseEntity<Order> getNextOrder();

    ResponseEntity<Order> startOrder();

    ResponseEntity<Order> finishOrder();



}
