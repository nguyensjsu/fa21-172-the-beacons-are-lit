package com.example.testing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String email;
    String password;
    String loginToken;
    String address;
    String mobile;

    public User(String name, String email, String password, String token, String address, String mobile) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.loginToken= token;
        this.mobile = mobile;
        this.password = password;
    }
}
