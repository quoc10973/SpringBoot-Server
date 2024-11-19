package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Koi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String image;
    String name;
    String description;
    float price;

    @OneToMany(mappedBy = "koi")
    @JsonIgnore
    List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    Account account;
}
