package com.example.demo.model.forgotPass;

import com.example.demo.model.OrdersDetailRequest;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackRequest {
   String content;
   int rating;
   long shopId;

}
