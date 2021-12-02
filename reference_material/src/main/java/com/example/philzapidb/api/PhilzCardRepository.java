package com.example.philzapidb.api;

import org.springframework.data.jpa.repository.JpaRepository;

interface PhilzCardRepository extends JpaRepository<PhilzCard, Long> {

    // Spring will use default stuff here (JpaRepository). However will need to use
    // custom
    // findByCardNumber for this specific setup
    PhilzCard findByCardNumber(String cardNumber);
}
