package com.example.demo.entity;

import com.example.demo.entity.enums.PaymentEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Date createAt;

    PaymentEnum paymentMethod;

    @OneToOne
    @JoinColumn(name = "order_id")
    Orders order;

    @OneToMany(mappedBy = "payment")
    List<Transactions> transactions;
}
