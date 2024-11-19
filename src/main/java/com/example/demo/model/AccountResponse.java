package com.example.demo.model;

import lombok.Data;

@Data
public class AccountResponse {
    long Id;
    String email;
    String phone;
    String studentCode;
    String token;
}
