package com.example.philzproduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * for rendering the product page
 */
public interface PhilzProductRepository extends CrudRepository<PhilzProducts, Long> {

    PhilzProducts findByName(String coffee);

    PhilzProducts findByProductID(String productid);

} 