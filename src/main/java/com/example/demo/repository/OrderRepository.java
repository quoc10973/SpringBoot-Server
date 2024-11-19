package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.Koi;
import com.example.demo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    List<Orders> findOrdersByCustomer(Account account);


}
