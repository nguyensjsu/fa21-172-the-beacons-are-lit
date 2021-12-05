package com.example.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * Represents a user in the system
 */
@Entity
@Table(indexes = @Index(name = "altIndex", columnList = "email", unique = true)) //use email as secondary key
@Data
@RequiredArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 
    
    @Column (nullable = false)
    private String name; 
    private String email;
    private String password; 
    private String address; 
    private String phone; 

}
