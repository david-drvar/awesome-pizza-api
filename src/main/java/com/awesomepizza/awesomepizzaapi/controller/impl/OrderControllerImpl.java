package com.awesomepizza.awesomepizzaapi.controller.impl;

import com.awesomepizza.awesomepizzaapi.controller.OrderController;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/order")
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
    @GetMapping(value = "/getAll")
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
    @PutMapping(value = "/update-status/{id}") //todo do I even need this method
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id) {
        if (orderService.updateOrderStatus(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/get-order-status/{id}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        OrderStatus status;
        if ((status = orderService.getOrderStatus(id)) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/get-orders-by-status/{status}")
    public ResponseEntity<Collection<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return new ResponseEntity<>(orderService.getOrdersByStatus(status), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/next-order")
    public ResponseEntity<Order> getNextOrder() {
        Order order = orderService.getNextOrder();
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //todo can be empty as well
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/start-order")
    public ResponseEntity<Order> startOrder() {
        Order order = orderService.startOrder();
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //todo can be empty as well
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/finish-order")
    public ResponseEntity<Order> finishOrder() {
        Order order = orderService.finishOrder();
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //todo can be empty as well
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
