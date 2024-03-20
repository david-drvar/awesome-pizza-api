package com.awesomepizza.awesomepizzaapi.controller;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface OrderController {

    ResponseEntity<OrderDTO> save(OrderDTO entity);

    ResponseEntity<OrderDTO> update(OrderDTO entity);

    ResponseEntity<Collection<OrderDTO>> read();

    ResponseEntity<OrderDTO> read(Long id);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<OrderStatus> getOrderStatus(Long id);

    ResponseEntity<OrderDTO> getNextOrder();

    ResponseEntity<OrderDTO> startOrder();

    ResponseEntity<OrderDTO> finishOrder();



}
