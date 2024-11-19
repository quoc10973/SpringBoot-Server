package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//dùng interface extend lại JpaRepository<kiểu data, kiểu data của khóa chính>
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
    Account findAccountByID(Long Id);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.role = :role")
    long countByRole(@Param("role") Role role);
}
