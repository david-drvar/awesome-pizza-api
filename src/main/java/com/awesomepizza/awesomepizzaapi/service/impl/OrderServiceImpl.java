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
    //todo probably can delete this
    public Order updateOrderStatus(Long id) {
        Optional<Order> order = orderRepository.findById(id); //todo check if the previous one is finished, only then you can start
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

    @Override
    public Order getNextOrder() {
        //check if there is one that is preparing
        //if there is, you cannot take next order
        //only when the previous one is ready you can take
        Optional<Order> firstPlacedOrder = orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED);
        if (firstPlacedOrder.isEmpty())
            return null; //todo there is no more placed orders, that is okay as well

        Optional<Order> previousOrder = orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(firstPlacedOrder.get().getTimestamp());
        if (previousOrder.isEmpty())
            return firstPlacedOrder.get(); //todo that is okay also because it can be the first order ever which doesnt have a previousone

        if (previousOrder.get().getOrderStatus() != OrderStatus.READY)
            return null; //todo error
        return firstPlacedOrder.get();
    }

    @Override
    public Order startOrder() {
        //find oldest placed order
        Optional<Order> firstPlacedOrder = orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED);
        if (firstPlacedOrder.isEmpty())
            return null;

        //check if the one before that one is completed
        Optional<Order> previousOrder = orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(firstPlacedOrder.get().getTimestamp());
        if (previousOrder.isEmpty()) {
            firstPlacedOrder.get().setOrderStatus(OrderStatus.PREPARING);
            orderRepository.save(firstPlacedOrder.get());
            return firstPlacedOrder.get(); //todo fix this, is empty is also okay when it is the first order ever, it wont have previous one
        }

        if (previousOrder.get().getOrderStatus() != OrderStatus.READY)
            return null; //todo return error saying that the previous order has to be finished first

        firstPlacedOrder.get().setOrderStatus(OrderStatus.PREPARING);
        orderRepository.save(firstPlacedOrder.get());
        return firstPlacedOrder.get();
    }

    @Override
    public Order finishOrder() {
        Optional<Order> firstPlacedOrder = orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PREPARING);
        if (firstPlacedOrder.isEmpty())
            return null;

        //check if the one before that one is completed
        Optional<Order> previousOrder = orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(firstPlacedOrder.get().getTimestamp());
        if (previousOrder.isEmpty()) {
            firstPlacedOrder.get().setOrderStatus(OrderStatus.READY);
            orderRepository.save(firstPlacedOrder.get());
            return firstPlacedOrder.get(); //todo fix this, is empty is also okay when it is the first order ever, it wont have previous one
        }

        if (previousOrder.get().getOrderStatus() != OrderStatus.READY)
            return null; //todo return error saying that the previous order has to be finished first

        firstPlacedOrder.get().setOrderStatus(OrderStatus.READY);
        orderRepository.save(firstPlacedOrder.get());
        return firstPlacedOrder.get();
    }
}
