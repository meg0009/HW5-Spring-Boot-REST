package com.chivapchichi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "commissions", nullable = false)
    private double commissions;
}
