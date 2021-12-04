package com.example.philzcart;
import org.springframework.data.repository.CrudRepository;

interface PhilzProductRepository extends CrudRepository<PhilzProducts, Long> {

    PhilzProducts findAllByName(String coffee);
}