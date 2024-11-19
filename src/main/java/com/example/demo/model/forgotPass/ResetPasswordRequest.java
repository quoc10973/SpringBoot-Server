package com.example.demo.model.forgotPass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, message = "Password must more than 6 letter")
    String password;
}
