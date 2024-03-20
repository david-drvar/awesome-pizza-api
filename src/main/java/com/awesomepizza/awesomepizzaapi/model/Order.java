package com.awesomepizza.awesomepizzaapi.model;

import com.awesomepizza.awesomepizzaapi.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "pizza_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "HH:mm:ss.SSSSSS yyyy-MM-dd")
    private LocalDateTime timestamp;

    @NotEmpty(message = "phone number must not be empty")
    @Pattern(regexp = "^\\+39 \\d{3} \\d{3} \\d{4}$", message = "phone numbers format must be +39 XXX XXX XXXX")
    private String phoneNumber;

    @NotNull(message = "order status must not be null")
    @Enumerated(EnumType.STRING)
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

    @Valid
    @NotEmpty(message = "pizza combo list must not be empty")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_pizzacombo",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "pizzacombo_id"))
    private List<PizzaCombo> pizzaComboList;

    @NotNull(message = "order's price must not be null")
    private Double price;

}
