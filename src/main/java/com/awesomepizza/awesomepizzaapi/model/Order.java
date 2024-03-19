package com.awesomepizza.awesomepizzaapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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

    @JsonFormat(pattern = "HH:mm:ss yyyy-MM-dd")
    private LocalDateTime timestamp;

    @Column
//    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[7-90]|36[680]|33[3-90]|32[89])\\d{7}$\n", message = "Italian phone numbers must be in format +39 349 1234567")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private String customerName;

    @Column
    private String customerSurname;

    @Column
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_pizzacombo",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "pizzacombo_id"))
    private List<PizzaCombo> pizzaComboList;

    @Column
    private Double price;

}
