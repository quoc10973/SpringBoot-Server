package com.example.demo.entity;

import com.example.demo.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//implement UserDetails để Spring security hiểu mốt để dùng phân quyền
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long ID;

    @Enumerated(EnumType.STRING)
    Role role;

    @NotBlank(message = "not blank")
    @Pattern(regexp = "SE\\d{6}", message = "format must be |SExxxxxx| with x is numbers")
    @Column(unique = true)
    String studentCode;

    @Email(message = "Email not valid")
    @Column(unique = true)
    String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})", message = "Invalid phone number!")
    @Column(unique = true)
    String phone;

    Date createAt;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, message = "Password must more than 6 letter")
    String password;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Student> students;

    @OneToMany(mappedBy = "customer")
    List<Orders> orders;

    @OneToMany(mappedBy = "from")
    List<Transactions> transactionsFrom;

    @OneToMany(mappedBy = "to")
    List<Transactions> transactionsTo;

    @OneToMany(mappedBy = "account")
    List<Koi> kois;

    @OneToMany(mappedBy = "customer")
    List<Feedback> customer_feedbacks;

    @OneToMany(mappedBy = "shop")
            @JsonIgnore
    List<Feedback> shop_feedbacks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // định nghĩa những quyền hạn mà account có thể làm được
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(this.role!=null)
            authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;  //dang nhap bang email, this.phone -> dang nhap bang phone
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
