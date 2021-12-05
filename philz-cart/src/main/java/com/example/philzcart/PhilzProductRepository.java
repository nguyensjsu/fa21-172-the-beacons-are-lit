package com.example.philzcart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * for rendering the product page
 */
interface PhilzProductRepository extends JpaRepository<PhilzProducts, Long> {

    PhilzProducts findByName(String coffee);
} 