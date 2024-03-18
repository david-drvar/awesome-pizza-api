package com.awesomepizza.awesomepizzaapi.controller.impl;

import com.awesomepizza.awesomepizzaapi.controller.OrderController;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class OrderControllerImpl implements OrderController {

    public final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Order> save(@RequestBody Order entity) {
        return new ResponseEntity<>(orderService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<Order> update(@RequestBody Order entity) {
        if(!orderService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(value="getAll")
    public ResponseEntity<Collection<Order>> read() {
        return new ResponseEntity<>(orderService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Order>> read(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!orderService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    @PostMapping(value = "/update-status/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id) {
        if (orderService.updateOrderStatus(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
