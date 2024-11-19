package com.example.demo.service;

import com.example.demo.entity.enums.Role;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    AccountRepository accountRepository;

    public Map<String, Object> getDashboardStats(){

        Map<String, Object> stats = new HashMap<>();

        //dem so luong san pham
        long totalProduct = koiRepository.count();

        //dem so luong customer
        long totalCustomer = accountRepository.countByRole(Role.CUSTOMER);

        //dem so luong admin
        long totalAdmin = accountRepository.countByRole(Role.ADMIN);

        //dem so luong owner
        long totalOwner = accountRepository.countByRole(Role.OWNER);

        //dem so luong student
        long totalStudent = accountRepository.countByRole(Role.STUDENT);

        stats.put("totalProduct", totalProduct);
        stats.put("totalCustomer", totalCustomer);
        stats.put("totalAdmin", totalAdmin);
        stats.put("totalOwner", totalOwner);
        stats.put("totalStudent", totalStudent);

        return stats;
    }
}
