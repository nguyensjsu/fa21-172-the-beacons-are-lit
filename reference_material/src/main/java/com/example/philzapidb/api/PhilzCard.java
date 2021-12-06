//This defines the storage object of the Starbucks Card
package com.example.philzapidb.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(indexes = @Index(name = "altIndex", columnList = "cardNumber", unique = true))
@Data
@RequiredArgsConstructor
public class PhilzCard {

    private @Id @GeneratedValue Long id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardCode;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private boolean activated;

    private String status;
}
