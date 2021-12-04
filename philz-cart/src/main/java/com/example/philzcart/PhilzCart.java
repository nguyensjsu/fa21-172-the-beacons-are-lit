//This defines the storage object of the Starbucks Card
package com.example.philzcart;

import javax.persistence.*;
//import javax.persistence.Index;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "CUSTOMER_CART")
@ToString
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class PhilzCart {

    private @Id @GeneratedValue Long id;

    @OneToMany
    private List<PhilzProducts> order;

    private String userId;

    private Status status;

    private double total;

    public void addProduct(PhilzProducts product) {
        order.add(product);
    }
}
