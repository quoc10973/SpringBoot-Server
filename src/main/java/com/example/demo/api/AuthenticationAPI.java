package com.example.demo.api;


import com.example.demo.entity.Account;
import com.example.demo.model.AccountResponse;
import com.example.demo.model.forgotPass.ForgotPasswordRequest;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.model.forgotPass.ResetPasswordRequest;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*") //cho phép tất cả code truy cập
@SecurityRequirement(name = "api") //bắt buộc có, nên nhớ
public class AuthenticationAPI {

    //DI: Dependency Injection
    @Autowired
    AuthenticationService authenticationService;   //tương tự new package: AuthenticationService a = new AuthenticationService();
    @PostMapping("/api/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest) {
        //api nhận request và object từ FE, sau đó nhờ service xử lý, thông qua lớp authenticationService
        //bản chất API là chỉ nhận request và response
        AccountResponse newAccount = authenticationService.register(registerRequest);
        return ResponseEntity.ok(newAccount);
    }

    LoginRequest loginRequest;
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest)
        {
            AccountResponse a = authenticationService.login(loginRequest);
            return ResponseEntity.ok(a);
        }


    @GetMapping("/api/account")
    public ResponseEntity getAllAccounts() {
        List<Account> list = authenticationService.getAllAccounts();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/api/forgot-password")
    public ResponseEntity forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        authenticationService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping("/api/reset-password")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }
}
