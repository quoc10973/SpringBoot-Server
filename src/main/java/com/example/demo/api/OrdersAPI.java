package com.example.demo.api;


import com.example.demo.entity.Orders;
import com.example.demo.model.OrdersRequest;
import com.example.demo.service.OrdersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") //cho phép tất cả code truy cập
@SecurityRequirement(name = "api") //bắt buộc có, nên nhớ
public class OrdersAPI {

    @Autowired
    OrdersService ordersService;

    @PostMapping("/api/orders")
    public ResponseEntity createOrder(@RequestBody OrdersRequest orderRequest) throws Exception {
        String vnpUrl = ordersService.createURL(orderRequest);
        return ResponseEntity.ok(vnpUrl);
    }

    @GetMapping("/api/orders")
    public ResponseEntity getOrders(){
        return ResponseEntity.ok(ordersService.getOrdersByCustomer());
    }
}
