package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.model.Order;
import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import com.awesomepizza.awesomepizzaapi.model.PizzaCombo;
import com.awesomepizza.awesomepizzaapi.repository.OrderRepository;
import com.awesomepizza.awesomepizzaapi.service.IngredientService;
import com.awesomepizza.awesomepizzaapi.service.OrderService;
import com.awesomepizza.awesomepizzaapi.service.PizzaComboService;
import com.awesomepizza.awesomepizzaapi.service.PremadePizzaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final PizzaComboService pizzaComboService;

    private final PremadePizzaService premadePizzaService;

    private final IngredientService ingredientService;

    private final ModelMapper modelMapper;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, PizzaComboService pizzaComboService,
                            PremadePizzaService premadePizzaService, IngredientService ingredientService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.pizzaComboService = pizzaComboService;
        this.premadePizzaService = premadePizzaService;
        this.ingredientService = ingredientService;
    }

    @Override
    public OrderDTO save(OrderDTO entity) {
        Order order = modelMapper.map(entity, Order.class);

        order.setId(null);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setTimestamp(LocalDateTime.now());

        for (PizzaCombo pizzaCombo : order.getPizzaComboList()) {
            pizzaCombo.setPremadePizza(this.premadePizzaService.read(pizzaCombo.getPremadePizza().getId()).get());
            pizzaCombo.setPrice(calculatePizzaComboPrice(pizzaCombo));
            this.pizzaComboService.save(pizzaCombo);
        }
        order.setPrice(calculateOrderPrice(order));
        order = this.orderRepository.save(order);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        return orderDTO;
    }

    private Double calculatePizzaComboPrice(PizzaCombo pizzaCombo) {
        Double price = 0D;
        for (Ingredient ingredient : pizzaCombo.getExtras())
            price += this.ingredientService.read(ingredient.getId()).get().getPrice();
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
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> read(Long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.orderRepository.existsById(id);
    }

    @Override
    public OrderStatus getOrderStatus(Long id) {
        Optional<Order> order = this.orderRepository.findById(id);
        return order.map(Order::getOrderStatus).orElse(null);
    }

    @Override
    public Order getNextOrder() {
        //check if there is one that is preparing
        //if there is, you cannot take next order
        //only when the previous one is ready you can take
        Optional<Order> firstPlacedOrder = this.orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED);
        if (firstPlacedOrder.isEmpty())
            return null; //todo there is no more placed orders, that is okay as well

        Optional<Order> previousOrder = this.orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(firstPlacedOrder.get().getTimestamp());
        if (previousOrder.isEmpty())
            return firstPlacedOrder.get(); // first order won't have previous one

        if (previousOrder.get().getOrderStatus() != OrderStatus.READY)
            return null; //todo error
        return firstPlacedOrder.get();
    }

    @Override
    public Order startOrder() {
        //find oldest placed order
        Optional<Order> firstPlacedOrder = this.orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED);
        if (firstPlacedOrder.isEmpty())
            return null;

        //check if the one before that one is completed
        Optional<Order> previousOrder = this.orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(firstPlacedOrder.get().getTimestamp());
        if (previousOrder.isEmpty()) {
            firstPlacedOrder.get().setOrderStatus(OrderStatus.PREPARING);
            this.orderRepository.save(firstPlacedOrder.get());
            return firstPlacedOrder.get(); // first order won't have previous one
        }

        if (previousOrder.get().getOrderStatus() != OrderStatus.READY)
            return null; //todo return error saying that the previous order has to be finished first

        firstPlacedOrder.get().setOrderStatus(OrderStatus.PREPARING);
        this.orderRepository.save(firstPlacedOrder.get());
        return firstPlacedOrder.get();
    }

    @Override
    public Order finishOrder() {
        Optional<Order> firstPlacedOrder = this.orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PREPARING);
        if (firstPlacedOrder.isEmpty())
            return null;

        //check if the one before that one is completed
        Optional<Order> previousOrder = this.orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(firstPlacedOrder.get().getTimestamp());
        if (previousOrder.isEmpty()) { // first order won't have previous one
            firstPlacedOrder.get().setOrderStatus(OrderStatus.READY);
            this.orderRepository.save(firstPlacedOrder.get());
            return firstPlacedOrder.get();
        }

        if (previousOrder.get().getOrderStatus() != OrderStatus.READY)
            return null; //todo return error saying that the previous order has to be finished first

        firstPlacedOrder.get().setOrderStatus(OrderStatus.READY);
        this.orderRepository.save(firstPlacedOrder.get());
        return firstPlacedOrder.get();
    }
}
