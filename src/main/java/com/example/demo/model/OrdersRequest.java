package com.example.demo.model;

import com.example.demo.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequest {
   List<OrdersDetailRequest> details;
}
