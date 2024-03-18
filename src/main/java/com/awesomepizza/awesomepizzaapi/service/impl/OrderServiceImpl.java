package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import com.awesomepizza.awesomepizzaapi.repository.OrderRepository;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order entity) {
        entity.setOrderStatus(OrderStatus.PLACED);
        entity.setTimestamp(LocalDateTime.now());
        return orderRepository.save(entity);
    }

    @Override
    public Collection<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> read(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order updateOrderStatus(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            return null;
        if (order.get().getOrderStatus() == OrderStatus.PLACED)
            order.get().setOrderStatus(OrderStatus.PREPARING);
        else if (order.get().getOrderStatus() == OrderStatus.PREPARING)
            order.get().setOrderStatus(OrderStatus.READY);
        else
            return null;
        orderRepository.save(order.get());
        return order.get();
    }

    @Override
    public OrderStatus getOrderStatus(Long id) {
        Optional<Order> order = this.orderRepository.findById(id);
        return order.map(Order::getOrderStatus).orElse(null);
    }

    @Override
    public Collection<Order> getOrdersByStatus(OrderStatus status) {
        return this.orderRepository.findAllByOrderStatus(status);
    }
}
