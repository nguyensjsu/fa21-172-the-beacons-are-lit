//This defines the storage object of the Starbucks Card
package com.example.starbucksapi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "CUSTOMER_ORDER")
@Data
@RequiredArgsConstructor
public class StarbucksOrder {

    private @Id @GeneratedValue Long id;

    @Column(nullable = false)
    private String drink;

    @Column(nullable = false)
    private String milk;

    @Column(nullable = false)
    private String size;

    private double total;

    private String status;
}
