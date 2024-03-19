package com.awesomepizza.awesomepizzaapi.controller;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;

public interface OrderController {

    ResponseEntity<OrderDTO> save(OrderDTO entity);

    ResponseEntity<OrderDTO> update(OrderDTO entity);

    ResponseEntity<Collection<OrderDTO>> read();

    ResponseEntity<Optional<Order>> read(Long id); //todo fix

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<OrderStatus> getOrderStatus(Long id);

    ResponseEntity<OrderDTO> getNextOrder();

    ResponseEntity<OrderDTO> startOrder();

    ResponseEntity<OrderDTO> finishOrder();



}
