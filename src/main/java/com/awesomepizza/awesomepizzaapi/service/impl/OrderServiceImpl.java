package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.OrderStatus;
import com.awesomepizza.awesomepizzaapi.model.PizzaCombo;
import com.awesomepizza.awesomepizzaapi.repository.IngredientRepository;
import com.awesomepizza.awesomepizzaapi.repository.OrderRepository;
import com.awesomepizza.awesomepizzaapi.repository.PizzaComboRepository;
import com.awesomepizza.awesomepizzaapi.repository.PremadePizzaRepository;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final PizzaComboRepository pizzaComboRepository;

    private final PremadePizzaRepository premadePizzaRepository;

    private final IngredientRepository ingredientRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, PizzaComboRepository pizzaComboRepository,
                            PremadePizzaRepository premadePizzaRepository, IngredientRepository ingredientRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.pizzaComboRepository = pizzaComboRepository;
        this.premadePizzaRepository = premadePizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public OrderDTO save(OrderDTO entity) {
        Order order = modelMapper.map(entity, Order.class);

        order.setId(null);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setTimestamp(LocalDateTime.now());

        for (PizzaCombo pizzaCombo : order.getPizzaComboList()) {
            pizzaCombo.setPremadePizza(this.premadePizzaRepository.findById(pizzaCombo.getPremadePizza().getId()).get());
            pizzaCombo.setPrice(calculatePizzaComboPrice(pizzaCombo));
            pizzaComboRepository.save(pizzaCombo);
        }
        order.setPrice(calculateOrderPrice(order));
        order = orderRepository.save(order);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        return orderDTO;
    }

    private Double calculatePizzaComboPrice(PizzaCombo pizzaCombo) {
        Double price = 0D;
        for (Ingredient ingredient : pizzaCombo.getExtras())
            price += ingredientRepository.findById(ingredient.getId()).get().getPrice();
        price += pizzaCombo.getPremadePizza().getPrice();
        price *= pizzaCombo.getPizzaSize().getPriceMultiplier();
        return price;
    }

    private Double calculateOrderPrice(Order order) {
        Double price = 0D;
        for (PizzaCombo pizzaCombo : order.getPizzaComboList())
            price += pizzaCombo.getPrice();
        return price;
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
