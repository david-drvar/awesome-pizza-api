package com.awesomepizza.awesomepizzaapi.dto;

import com.awesomepizza.awesomepizzaapi.model.Address;
import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private Long id;
    private LocalDateTime timestamp;

    @NotEmpty(message = "phone number must not be empty")
    @Pattern(regexp = "^\\+39 \\d{3} \\d{3} \\d{4}$", message = "phone numbers format must be +39 XXX XXX XXXX")
    private String phoneNumber;

    private OrderStatus orderStatus;

    @NotEmpty(message = "name must not be empty")
    @Size(min = 3, max = 20, message = "name must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z'-]+(?: [a-zA-Z'-]+)*$", message = "name can contain only letters, - and '")
    private String customerName;

    @NotEmpty(message = "surname must not be empty")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z'-]+(?: [a-zA-Z'-]+)*$", message = "surname can contain only letters, - and '")
    private String customerSurname;

    @Valid
    @NotNull(message = "address must not be null")
    private Address address;

    private Double price;

    @Valid
    @NotEmpty(message = "order list must not be empty")
    private List<PizzaComboDTO> pizzaComboList;

}
