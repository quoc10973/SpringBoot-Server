package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String content;

    int rating;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Account customer;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    Account shop;

}
