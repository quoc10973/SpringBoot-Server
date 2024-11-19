package com.example.demo.repository;

import com.example.demo.entity.Koi;
import com.example.demo.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    Koi findTransactionsById(long id);
}
