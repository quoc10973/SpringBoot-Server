package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Name can not blank!")
    String name;

    @NotBlank(message = "Studen Code can not blank") //ngoài ra cần format partten SE000001 //
    @Pattern(regexp = "SE\\d{6}", message = "format must be |SExxxxxx| with x is numbers")
    @Column(unique = true) //duy nhất, không được trùng
    String studentCode;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 10, message = "Score must not be more than 10")
    float Score;
}
