package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Id;

    @NotBlank(message = "type can not be blank")
    String type;

    @NotBlank(message = "Id can not be blank")
    @Column(unique = true)
    String PetId;

    @Min(value = 0, message = "weight must be positive number")
    float weight;
}
