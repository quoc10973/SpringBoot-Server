package com.example.demo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrdersDetailRequest {
    UUID koiId;
    int quantity;
}
