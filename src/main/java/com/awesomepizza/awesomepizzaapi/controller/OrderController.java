package com.awesomepizza.awesomepizzaapi.controller;

import com.awesomepizza.awesomepizzaapi.model.Order;
import org.springframework.http.ResponseEntity;

public interface OrderController extends CRUDController<Order>{

    ResponseEntity<Order> updateOrderStatus(Long id);

}
