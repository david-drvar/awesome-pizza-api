package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.dto.OrderDTO;
import com.awesomepizza.awesomepizzaapi.exception.NoOrdersPlacedException;
import com.awesomepizza.awesomepizzaapi.exception.OrderQueueException;
import com.awesomepizza.awesomepizzaapi.model.*;
import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import com.awesomepizza.awesomepizzaapi.model.enums.PizzaSize;
import com.awesomepizza.awesomepizzaapi.repository.OrderRepository;
import com.awesomepizza.awesomepizzaapi.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PizzaComboService pizzaComboService;

    @Mock
    private PremadePizzaService premadePizzaService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testSaveOrder() {

        OrderDTO orderDTO = new OrderDTO();

        Order order = new Order();
        PizzaCombo pizzaCombo = new PizzaCombo();
        pizzaCombo.setPizzaSize(PizzaSize.LARGE);

        PremadePizza premadePizza = new PremadePizza();
        premadePizza.setId(1L);
        premadePizza.setPrice(10D);

        pizzaCombo.setPremadePizza(premadePizza);
        List<PizzaCombo> pizzaComboList = new ArrayList<>();
        pizzaComboList.add(pizzaCombo);
        order.setPizzaComboList(pizzaComboList);

        Address address = new Address();
        address.setStreet("via Leonardo 5");
        address.setTown("Milano");
        address.setZip("20034");
        order.setAddress(address);

        order.setPhoneNumber("+39 642 445 4566");
        order.setCustomerName("Marco");
        order.setCustomerSurname("Polo");

        when(modelMapper.map(any(OrderDTO.class), Mockito.eq(Order.class)))
                .thenReturn(order);

        when(premadePizzaService.read(Mockito.anyLong()))
                .thenReturn(Optional.of(premadePizza));

        when(pizzaComboService.save(any(PizzaCombo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> orderService.save(orderDTO));

    }

    @Test
    public void testCalculatePizzaComboPrice() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        PizzaCombo pizzaCombo = new PizzaCombo();
        pizzaCombo.setPizzaSize(PizzaSize.LARGE);
        PremadePizza premadePizza = new PremadePizza();
        premadePizza.setId(1L);
        premadePizza.setPrice(10D);
        pizzaCombo.setPremadePizza(premadePizza);

        Double expectedPrice = premadePizza.getPrice() * PizzaSize.LARGE.getPriceMultiplier();

        Method method = OrderServiceImpl.class.getDeclaredMethod("calculatePizzaComboPrice", PizzaCombo.class);
        method.setAccessible(true);
        Double price = (Double) method.invoke(orderService, pizzaCombo);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void testCalculatePizzaComboPriceWithExtras() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        PizzaCombo pizzaCombo = new PizzaCombo();
        pizzaCombo.setPizzaSize(PizzaSize.LARGE);
        PremadePizza premadePizza = new PremadePizza();
        premadePizza.setId(1L);
        premadePizza.setPrice(10D);
        pizzaCombo.setPremadePizza(premadePizza);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setPrice(2D);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        pizzaCombo.setExtras(ingredientList);

        when(ingredientService.read(Mockito.anyLong()))
                .thenReturn(Optional.of(ingredient1));

        Double expectedPrice = (premadePizza.getPrice() + ingredient1.getPrice()) * PizzaSize.LARGE.getPriceMultiplier();

        Method method = OrderServiceImpl.class.getDeclaredMethod("calculatePizzaComboPrice", PizzaCombo.class);
        method.setAccessible(true);
        Double price = (Double) method.invoke(orderService, pizzaCombo);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void testCalculateOrderPrice() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Order order = new Order();
        PizzaCombo pizzaCombo1 = new PizzaCombo();
        pizzaCombo1.setPrice(10D);
        PizzaCombo pizzaCombo2 = new PizzaCombo();
        pizzaCombo2.setPrice(5D);
        PizzaCombo pizzaCombo3 = new PizzaCombo();
        pizzaCombo3.setPrice(15D);
        List<PizzaCombo> pizzaComboList = new ArrayList<>();
        pizzaComboList.add(pizzaCombo1);
        pizzaComboList.add(pizzaCombo2);
        pizzaComboList.add(pizzaCombo3);
        order.setPizzaComboList(pizzaComboList);

        double expectedPrice = order.getPizzaComboList().stream()
                .mapToDouble(PizzaCombo::getPrice)
                .sum();

        Method method = OrderServiceImpl.class.getDeclaredMethod("calculateOrderPrice", Order.class);
        method.setAccessible(true); // Override access restrictions
        Double price = (Double) method.invoke(orderService, order);

        assertEquals(expectedPrice, price);
    }

    @Test
    public void testGetNextOrderFirstOrderEmpty() {
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.empty());

        assertThrows(NoOrdersPlacedException.class, () -> orderService.getNextOrder());
    }

    @Test
    public void testGetNextOrderPreviousOrderEmpty() {
        Order placedOrder = new Order();
        placedOrder.setOrderStatus(OrderStatus.PLACED);
        placedOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.of(placedOrder));

        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.empty());

        Order nextOrder = orderService.getNextOrder();

        assertEquals(placedOrder, nextOrder);
    }

    @Test
    public void testGetNextOrderPreviousOrderNotReady() {
        Order placedOrder = new Order();
        placedOrder.setOrderStatus(OrderStatus.PLACED);
        placedOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.of(placedOrder));

        Order previousOrder = new Order();
        previousOrder.setOrderStatus(OrderStatus.PREPARING); // previous order is still getting prepared
        previousOrder.setTimestamp(LocalDateTime.now().minusHours(1));
        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.of(previousOrder));

        assertThrows(OrderQueueException.class, () -> orderService.getNextOrder());
    }

    @Test
    public void testGetNextOrderPreviousOrderReady() {
        Order placedOrder = new Order();
        placedOrder.setOrderStatus(OrderStatus.PLACED);
        placedOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.of(placedOrder));

        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.empty());

        Order nextOrder = orderService.getNextOrder();

        assertEquals(placedOrder, nextOrder);
    }

    @Test
    public void testStartOrderFirstOrderEmpty() {
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.empty());

        assertThrows(NoOrdersPlacedException.class, () -> orderService.startOrder());
    }

    @Test
    public void testStartOrderPreviousOrderEmpty() {
        Order placedOrder = new Order();
        placedOrder.setOrderStatus(OrderStatus.PLACED);
        placedOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.of(placedOrder));

        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.empty());

        Order startedOrder = orderService.startOrder();

        assertEquals(placedOrder, startedOrder);
        assertEquals(OrderStatus.PREPARING, startedOrder.getOrderStatus());

    }

    @Test
    public void testStartOrderPreviousOrderNotReady() {
        Order placedOrder = new Order();
        placedOrder.setOrderStatus(OrderStatus.PLACED);
        placedOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.of(placedOrder));

        Order previousOrder = new Order();
        previousOrder.setOrderStatus(OrderStatus.PREPARING); // previous order is still getting prepared
        previousOrder.setTimestamp(LocalDateTime.now().minusHours(1));
        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.of(previousOrder));

        assertThrows(OrderQueueException.class, () -> orderService.startOrder());
    }

    @Test
    public void testStartOrderPreviousOrderReady() {
        Order placedOrder = new Order();
        placedOrder.setOrderStatus(OrderStatus.PLACED);
        placedOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PLACED))
                .thenReturn(Optional.of(placedOrder));

        Order previousOrder = new Order();
        previousOrder.setOrderStatus(OrderStatus.READY); // previous order is still getting prepared
        previousOrder.setTimestamp(LocalDateTime.now().minusHours(1));
        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.of(previousOrder));

        Order startedOrder = orderService.startOrder();

        assertEquals(placedOrder, startedOrder);
        assertEquals(OrderStatus.PREPARING, startedOrder.getOrderStatus());
    }

    @Test
    public void testFinishOrderFirstOrderEmpty() {
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PREPARING))
                .thenReturn(Optional.empty());

        assertThrows(NoOrdersPlacedException.class, () -> orderService.finishOrder());
    }

    @Test
    public void testFinishOrderPreviousOrderEmpty() {
        Order preparingOrder = new Order();
        preparingOrder.setOrderStatus(OrderStatus.PREPARING);
        preparingOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PREPARING))
                .thenReturn(Optional.of(preparingOrder));

        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.empty());

        Order finishedOrder = orderService.finishOrder();

        assertEquals(preparingOrder, finishedOrder);
        assertEquals(OrderStatus.READY, finishedOrder.getOrderStatus());

    }

    @Test
    public void testFinishOrderPreviousOrderNotReady() {
        Order preparingOrder = new Order();
        preparingOrder.setOrderStatus(OrderStatus.PREPARING);
        preparingOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PREPARING))
                .thenReturn(Optional.of(preparingOrder));

        Order previousOrder = new Order();
        previousOrder.setOrderStatus(OrderStatus.PREPARING); // previous order is still getting prepared
        previousOrder.setTimestamp(LocalDateTime.now().minusHours(1));
        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.of(previousOrder));

        assertThrows(OrderQueueException.class, () -> orderService.finishOrder());
    }

    @Test
    public void testFinishOrderPreviousOrderReady() {
        Order preparingOrder = new Order();
        preparingOrder.setOrderStatus(OrderStatus.PREPARING);
        preparingOrder.setTimestamp(LocalDateTime.now());
        when(orderRepository.findFirstByOrderStatusOrderByTimestampAsc(OrderStatus.PREPARING))
                .thenReturn(Optional.of(preparingOrder));

        Order previousOrder = new Order();
        previousOrder.setOrderStatus(OrderStatus.READY); // previous order is still getting prepared
        previousOrder.setTimestamp(LocalDateTime.now().minusHours(1));
        when(orderRepository.findFirstByTimestampBeforeOrderByTimestampDesc(any()))
                .thenReturn(Optional.of(previousOrder));

        Order finishedOrder = orderService.finishOrder();

        assertEquals(preparingOrder, finishedOrder);
        assertEquals(OrderStatus.READY, finishedOrder.getOrderStatus());
    }

}
