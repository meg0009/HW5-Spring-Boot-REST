package com.chivapchichi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "seller", nullable = false)
    private int seller;

    @Column(name = "customer", nullable = false)
    private int customer;

    @Column(name = "book", nullable = false)
    private int book;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "price", nullable = false)
    private double price;
}
