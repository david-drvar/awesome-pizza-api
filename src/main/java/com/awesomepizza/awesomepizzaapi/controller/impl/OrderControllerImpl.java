package com.awesomepizza.awesomepizzaapi.controller.impl;

import com.awesomepizza.awesomepizzaapi.controller.OrderController;
import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/order")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;


    @Autowired
    public OrderControllerImpl(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    @PostMapping(consumes = "application/json")
    @PermitAll
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody OrderDTO entity) {
        return new ResponseEntity<>(orderService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> update(@Valid @RequestBody OrderDTO entity) {
        if(!orderService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(value = "/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<OrderDTO>> read() {
        Collection<Order> orders = orderService.read();
        Collection<OrderDTO> orderDTOs = orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> read(@PathVariable Long id) {
        Optional<Order> order = orderService.read(id);
        if (order.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(modelMapper.map(order, OrderDTO.class), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!orderService.existsById(id))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/get-order-status/{id}")
    @PermitAll
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        OrderStatus status;
        if ((status = orderService.getOrderStatus(id)) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/next-order")
    @PreAuthorize("hasRole('PIZZAMAKER')")
    public ResponseEntity<OrderDTO> getNextOrder() {
        Order order = orderService.getNextOrder();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/start-order")
    @PreAuthorize("hasRole('PIZZAMAKER')")
    public ResponseEntity<OrderDTO> startOrder() {
        Order order = orderService.startOrder();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/finish-order")
    @PreAuthorize("hasRole('PIZZAMAKER')")
    public ResponseEntity<OrderDTO> finishOrder() {
        Order order = orderService.finishOrder();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}
