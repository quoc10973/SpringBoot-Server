package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.model.forgotPass.ForgotPasswordRequest;
import com.example.demo.model.forgotPass.ResetPasswordRequest;
import com.example.demo.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service //đánh dấu cho srping boot đây là service
public class AuthenticationService implements UserDetailsService {
    //xử lý logic, xử lý nghiệp vụ
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    public AccountResponse register(RegisterRequest registerRequest){
        Account account = modelMapper.map(registerRequest, Account.class);
        try{
            String originPass = account.getPassword();
            account.setCreateAt(new Date());
            account.setPassword(passwordEncoder.encode(originPass));
            Account newAccount = accountRepository.save(account);
            //đăng ký thành công, gửi mail cho người dùng
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setReceiver(newAccount);
            emailDetail.setSubject("Welcome to KOIKICHI");
            emailDetail.setLink("https://cdn-i.doisongphapluat.com.vn/resize/th/upload/2024/05/29/chan-dung-tinh-dau-quoc-dan-bae-suzy-2-09540259.jpg");
            emailService.sendEmail(emailDetail);

            return modelMapper.map(newAccount, AccountResponse.class);
        } catch (Exception e) {
            if(e.getMessage().contains(account.getEmail()))
            {
                throw new DuplicateEntity("Duplicated Email! Created Fail");
            }
            else if(e.getMessage().contains(account.getPhone())){
                throw new DuplicateEntity("Duplicated Phone! Created Fail");
            }
            else{
                throw new DuplicateEntity("Duplicated Phone! Created Fail");
            }


        }
         // tương tự context.add C#
    }

    public AccountResponse login(LoginRequest loginRequest){
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword())); //kh có thì catch exception
            Account account = (Account) authentication.getPrincipal(); //lấy thông tin ng dùng và cast về account
            AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
            accountResponse.setToken(tokenService.generateToken(account));
            return accountResponse;
        }catch (Exception e){
            //error => throw new exception
            throw new NotFoundException("Email or Password is invalid!!");
        }

    }

    public List<Account> getAllAccounts(){
        List<Account> list = accountRepository.findAll(); // findAll() lấy tất cả account trong DB
        return list;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email);
    }

    //lấy thông tin account dc lưu từ token trong SecurityContextHolder
    public Account getCurrentAccount(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountByID(account.getID());
    }

    public void forgotPassword(ForgotPasswordRequest forgotPassword){
        Account account = accountRepository.findByEmail(forgotPassword.getEmail());
        if(account == null){
            throw new NotFoundException("Account not found");
        }
        else{
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setReceiver(account);
            emailDetail.setSubject("Reset Your Password");
            emailDetail.setLink("https://www.google.com/?token=" + tokenService.generateToken(account));
            emailService.sendEmail(emailDetail);
        }
    }

    public void resetPassword(ResetPasswordRequest request){
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);
    }
}
