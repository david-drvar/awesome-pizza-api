package com.awesomepizza.awesomepizzaapi.dto;

import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
    private LocalDateTime timestamp;
    private String phoneNumber;
    private OrderStatus orderStatus;
    private String customerName;
    private String customerSurname;
    private String address;
    private Double price;
    private List<PizzaComboDTO> pizzaComboList;


}
