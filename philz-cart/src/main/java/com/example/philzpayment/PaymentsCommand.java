package com.example.philzpayment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Payments")
@Data
@Setter
@RequiredArgsConstructor
class PaymentsCommand {

    private @Id @GeneratedValue Long id;


    transient private String action ;
    private String firstname ;
    private String lastname ;
    private String address ;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String cardnum;
    private String cardexpmon;
    private String cardexpyear;
    private String cardcvv;
    private String email;

    
    private String orderNumber;
    private String transactionAmount;
    private String transactionCurrency;
    private String authID;
    private String authStatus;
    private String captureID;
    private String captureStatus;

    public String firstname() {return firstname;} 
    public String lastname() {return lastname;}
    public String address() {return address;}
    public String city() {return city;}
    public String state() {return state;}
    public String zip() {return zip;}
    public String phone() {return phone;}
    public String cardnum() {return cardnum;}
    public String cardexpmon() {return cardexpmon;}
    public String cardexpyear() {return cardexpyear;}
    public String cardcvv() {return cardcvv;}
    public String email() {return email;}

}
