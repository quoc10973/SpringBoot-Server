package com.example.demo.model;

import lombok.Data;

@Data
public class NotificationFcm {
    String title;
    String message;
    String fcmToken;
}
