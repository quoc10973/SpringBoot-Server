package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
// thằng này giúp tạo ra token và verify token
public class TokenService {

    public final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";

    @Autowired
    AccountRepository accountRepository;

    private SecretKey getSigninKey(){
       byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //Decoders.BASE64.decode(SECRET_KEY): giải mã chuỗi thành mảng byte
       return Keys.hmacShaKeyFor(keyBytes); //Tạo một SecretKey cho HMAC-SHA từ mảng byte
    }

    // mỗi account từ FE gửi xuống BE sẽ nhận được 1 token riêng (mã định danh)
    public String generateToken(Account account){
        String token = Jwts.builder().subject(account.getID()+"")
                .issuedAt(new Date(System.currentTimeMillis())) // thời gian khởi tạo, thời gian theo miligiay
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //thời gian hết hạn
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    public Account getAccountByToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();    //lấy info từ token, jwt giải mã với signinKey, getPayload trả về Account thông tin và lưu trong claims

        String idString = claims.getSubject();
        Long id = Long.parseLong(idString);
        return accountRepository.findAccountByID(id);
    }
}
