//This defines the storage object of the Starbucks Card
package com.example.philzapidb.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.Index;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CUSTOMER_ORDER")
@ToString
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class PhilzOrder {

    private @Id @GeneratedValue Long id;

    @Column(nullable = false)
    private String drink;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String milk;

    @Column(nullable = false)
    private String cream;

    @Column(nullable = false)
    private String sugar;

    @Column(nullable = false)
    private String temperature;

    private double total;

    private String status;
}
