package com.chivapchichi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "discount", nullable = false)
    private double discount;
}
