package com.example.philzcart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

interface PhilzProductRepository extends JpaRepository<PhilzProducts, Long> {

    PhilzProducts findAllByName(String coffee);
}