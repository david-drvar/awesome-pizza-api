package com.awesomepizza.awesomepizzaapi.controller;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import org.springframework.http.ResponseEntity;

public interface OrderController extends CRUDController<Order>{

    ResponseEntity<OrderStatus> getOrderStatus(Long id);

//    ResponseEntity<Collection<OrderDTO>> getOrdersByStatus(OrderStatus orderStatus);

    ResponseEntity<OrderDTO> getNextOrder();

    ResponseEntity<OrderDTO> startOrder();

    ResponseEntity<OrderDTO> finishOrder();



}
