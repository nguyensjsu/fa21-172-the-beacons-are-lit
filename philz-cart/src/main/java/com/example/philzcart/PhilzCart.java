//This defines the storage object of the Starbucks Card
package com.example.philzcart;
import javax.persistence.*;
//import javax.persistence.Index;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMER_CART")
@ToString
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class PhilzCart implements Serializable {
    private @Id @GeneratedValue Long id;
    @OneToMany(fetch = FetchType.EAGER, targetEntity=PhilzProducts.class, mappedBy="id")
    private List<PhilzProducts> order = new ArrayList<>();
    private String username;
    private Status status;
    private double total;

    public void addProduct(List<PhilzProducts> products) {
        this.order.addAll(products);
    }

}