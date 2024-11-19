package com.example.demo.model;

import com.example.demo.entity.Account;
import lombok.Data;

@Data
public class EmailDetail {
    Account receiver;
    String subject;
    String link;

}
