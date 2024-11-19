package com.example.demo.api;

import com.example.demo.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*") //cho phép tất cả code truy cập
@SecurityRequirement(name = "api") //bắt buộc có, nên nhớ
public class DashboardAPI {

    @Autowired
    AdminService adminService;

//    @GetMapping("/api/dashboard")
//    public ResponseEntity getDashboardStats(){
//        return ResponseEntity.ok(adminService.getDashboardStats());
//    }
}
