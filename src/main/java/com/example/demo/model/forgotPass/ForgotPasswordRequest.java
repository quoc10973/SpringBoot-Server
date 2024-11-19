package com.example.demo.model.forgotPass;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @Email(message = "Email is not valid")
    String email;
}
