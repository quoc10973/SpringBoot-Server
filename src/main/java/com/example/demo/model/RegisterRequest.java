package com.example.demo.model;

import com.example.demo.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "not blank")
    @Pattern(regexp = "SE\\d{6}", message = "format must be |SExxxxxx| with x is numbers")
    @Column(unique = true)
    String studentCode;

    Role role;

    @Email(message = "Email not valid")
    @Column(unique = true)
    String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})", message = "Invalid phone number!")
    @Column(unique = true)
    String phone;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, message = "Password must more than 6 letter")
    String password;

}
