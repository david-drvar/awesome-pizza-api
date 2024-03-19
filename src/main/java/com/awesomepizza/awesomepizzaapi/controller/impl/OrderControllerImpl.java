package com.awesomepizza.awesomepizzaapi.controller.impl;

import com.awesomepizza.awesomepizzaapi.controller.OrderController;
import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/order")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper; // Assuming you're using ModelMapper for mapping


    @Autowired
    public OrderControllerImpl(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
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
    @GetMapping(value = "/get-all")
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
    @GetMapping(value = "/get-order-status/{id}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        OrderStatus status;
        if ((status = orderService.getOrderStatus(id)) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    //todo maybe I dont need this
//    @Override
//    @GetMapping(value = "/get-orders-by-status/{status}")
//    public ResponseEntity<Collection<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatus status) {
//        return new ResponseEntity<>(orderService.getOrdersByStatus(status), HttpStatus.OK);
//    }

    @Override
    @GetMapping(value = "/next-order")
    public ResponseEntity<OrderDTO> getNextOrder() {
        Order order = orderService.getNextOrder();
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //todo can be empty as well
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/start-order")
    public ResponseEntity<OrderDTO> startOrder() {
        Order order = orderService.startOrder();
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //todo can be empty as well
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/finish-order")
    public ResponseEntity<OrderDTO> finishOrder() {
        Order order = orderService.finishOrder();
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //todo can be empty as well
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}
