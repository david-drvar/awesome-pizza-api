package com.awesomepizza.awesomepizzaapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "pizza_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
    @SequenceGenerator(name="order_generator", sequenceName = "order_seq", allocationSize=1, initialValue = 1)
    private Long id;

    @JsonFormat(pattern = "HH:mm:ss yyyy-MM-dd")
    private LocalDateTime timestamp;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private String customerName;

    @Column
    private String customerSurname;

    @Column
    private String address;

}
