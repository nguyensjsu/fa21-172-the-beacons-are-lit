package com.example.philzcart;
import org.springframework.data.repository.CrudRepository;

/**
 * for rendering the product page
 */
interface PhilzProductRepository extends CrudRepository<PhilzProducts, Long> {

    PhilzProducts findAllByName(String coffee);
}